package project1.timeline.engine;

/*
 * �?ây là class chứa các phương thức xử lý sql trong chương trình
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.Driver;
public class SqlProcessor {
	//Khai báo các hằng số đối với lớp này
	
	public static final int TABLE_USER = 0;
	public static final int TABLE_TYPE = 1;
	public static final int TABLE_TIME = 2;
	public static final int TABLE_ACTIVITY = 3;
	public static final int TABLE_PLACE = 4;
	public static final int TABLE_USER_ACTIVITY = 5;
	
	public static final int TYPE_SELECT_QUERY_COUNT = 0;
	public static final int TYPE_SELECT_QUERY_TUBLE = 1;
	public static final int CONDITION_USER_ID = 0;
	public static final int CONDITION_TIMEREGISTER_BEGIN = 1;
	public static final int CONDITION_TIMEREGISTER_END = 2;
	public static final int CONDITION_TIME_BEGIN = 3;
	public static final int CONDITION_TIME_END = 4;
	public static final int CONDITION_RINGSTATUS = 5;
	public static final int CONDITION_TYPE_ID = 6;
	public static final int CONDITION_PLACE_ID = 7;
	
	public static final int SIZE_ARRAY_CONDITION = 8;
	public static final int SIZE_RESULT_SET = 10;
	
	public static final String AS_PLACE_ID = "place.Id";
	public static final String AS_PLACE_NAME = "place.Name";
	public static final String AS_PLACE_DESCRIPTION = "place.Description";
	public static final String AS_PLACE_ADDRESS = "place.Address";
	public static final String AS_USER_ID = "user.Id";
	public static final String AS_USER_NAME = "user.Name";
	public static final String AS_USER_PASSWORD = "user.Password";
	public static final String AS_USER_EMAIL = "user.Email";
	public static final String AS_USER_ADDRESS = "user.Address";
	public static final String AS_USER_ACTIVITY_USERID = "user_activity.UserId";
	public static final String AS_USER_ACTIVITY_ACTID = "user_activity.ActId";
	public static final String AS_USER_ACTIVITY_TIMEREGISTER = "user_activity.TimeRegister";
	public static final String AS_ACTIVITY_ID = "activity.Id";
	public static final String AS_ACTIVITY_PLACEID = "activity.PlaceId";
	public static final String AS_ACTIVITY_TYPEID = "activity.TypeId";
	public static final String AS_ACTIVITY_NOTE = "activity.Note";
	public static final String AS_ACTIVITY_RINGSTATUS = "activity.RingStatus";
	public static final String AS_TIME_ID = "time.Id";
	public static final String AS_TIME_ACTID = "time.ActId";
	public static final String AS_TIME_TIME = "time.Time";
	public static final String AS_TIME_NOTE = "time.Note";
	public static final String AS_TYPE_ID = "type.Id";
	public static final String AS_TYPE_NAME = "type.Name";
	public static final String AS_TYPE_DESCRIPTION = "type.Description";
	
	public static final int URL_CONNECTION = 0;
	public static final int USER_CONNECTION = 1;
	public static final int PASSWORD_CONNECTION = 2;
	//Khai báo các biến sử dụng trong lớp này
	
	private Connection connector = null;
	private String userName;
	private String userPassword;
	private String url;
	private Statement statement;
	private SqlData data = null;
	private ResultSet resultSql;
	private PreparedStatement preStatement;
	//Các hàm khởi tạo
	
	public SqlProcessor(String url, String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.url = url;
		
		this.createConnection();
	}
	//Hàm khởi tạo với địa chỉ MySQL và tên cơ sở dữ liệu để truy cập cơ sở dữ liệu
	
	public SqlProcessor(String ipAddress, String databaseName, String userName, String userPassword) {
		this.url = "jdbc:mysql://" + ipAddress + "/" + databaseName;
		this.userName = userName;
		this.userPassword = userPassword;
		
		this.createConnection(ipAddress, databaseName, this.userName, this.userPassword);
	}
	//Phương thức sử dụng để tạo ra kết nối tới cơ sở dữ liệu
	
	private boolean createConnection() {
		boolean result = false;
		
		try {
			if(this.connector != null) {
				this.connector.close();
			}
			
			this.connector = DriverManager.getConnection(this.url, this.userName, this.userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if((this.connector != null) && (this.connector.isValid(1))) {
				System.out.println("Connect success!");
				
				System.out.println("Connect success!");
				String ipAddress = "";
				String databaseName = "";
				String[] temple = url.split("/");
				
				for(int i = 0; i < temple.length; i ++) {
					if(i == temple.length - 2) {
						ipAddress = temple[i];
					}
					else if(i == temple.length - 1) {
						databaseName = temple[i];
					}
				}
				
				result = true;
			}
			else {
				System.out.println("Connect false!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	//Phương thức tạo kết nối tới cơ sở dữ liệu tại một cổng và tên cơ sở dữ liệu xác định
	
	public boolean createConnection(String ipAddress, String databaseName, String userName, String userPassword) {
		boolean result = false;
		
		try {
			if(this.connector != null) {
				this.connector.close();
			}
			
			this.connector = DriverManager.getConnection("jdbc:mysql://" + ipAddress + "/" + databaseName, userName, userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if((this.connector != null) && this.connector.isValid(1)) {
				System.out.println("Connect success!");
				
				this.url = "jdbc:mysql://" + ipAddress + "/" + databaseName;
				this.userName = userName;
				this.userPassword = userPassword;
				XmlProcessor.updateMySQL("jdbc:mysql://" + ipAddress + "/" + databaseName, ipAddress, databaseName, userName, userPassword);
				
				result = true;
			}
			else {
				System.out.println("Connect false!");
				
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	//Phương thức tạo kết nối sử dụng đương dẫn url
	
	public boolean createConnection(String url, String userName, String password) {
		boolean result = false;
		
		try {
			if(this.connector != null) {
				this.connector.close();
			}
			
			this.connector = DriverManager.getConnection(url, userName, password);
			
			if((this.connector != null) && (this.connector.isValid(1))) {
				System.out.println("Connect success!");
				String ipAddress = "";
				String databaseName = "";
				String[] temple = url.split("/");
				
				for(int i = 0; i < temple.length; i ++) {
					if(i == temple.length - 2) {
						ipAddress = temple[i];
					}
					else if(i == temple.length - 1) {
						databaseName = temple[i];
					}
				}
				
				this.url = url;
				this.userName = userName;
				this.userPassword = password;
				XmlProcessor.updateMySQL(url, ipAddress, databaseName, userName, password);
				
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	//Phương thức kiểm tra tệp xml và lấy dữ liệu của ngư�?i dùng phục vụ cho quá trình tự đăng nhập khi khởi đông chương trình
	
	public boolean loginAuto() {
		String[] userDataXml = null;
		
		if((userDataXml = XmlProcessor.getInformation(XmlProcessor.TYPE_USER)) == null) {
			return false;
		}
		else return this.checkAccount(userDataXml[XmlProcessor.USER_NAME], userDataXml[XmlProcessor.USER_PASSWORD]);
	}
	//Phương thức chuyển đổi từ một loại bảng sang tên của bảng đó
	
	private String convertNameTable(int typeTable) {
		String result = "";
			
		switch(typeTable) {
			case SqlProcessor.TABLE_USER: result = "user";
				break;
			case SqlProcessor.TABLE_PLACE: result = "place";
				break;
			case SqlProcessor.TABLE_ACTIVITY: result = "activity";
				break;
			case SqlProcessor.TABLE_TIME: result = "time";
				break;
			case SqlProcessor.TABLE_TYPE: result = "type";
				break;
			case SqlProcessor.TABLE_USER_ACTIVITY: result = "user_activity";
				break;
			default: System.out.println("Lỗi tham số!");
				break;
		}
			
		return result;
	}
	//Phương thức chuyển đổi từ tên của bảng sang loại bảng
		
	private int convertTypeTable(String nameTable) {
		int result = -1;
		
		switch(nameTable) {
			case "user": result = SqlProcessor.TABLE_USER;
				break;
			case "place": result = SqlProcessor.TABLE_PLACE;
				break;
			case "activity": result = SqlProcessor.TABLE_ACTIVITY;
				break;
			case "time": result = SqlProcessor.TABLE_TIME;
				break;
			case "type": result = SqlProcessor.TABLE_TYPE;
				break;
			case "user_activity": result = SqlProcessor.TABLE_USER_ACTIVITY;
				break;
			default: System.out.println("Lỗi tham số!");
		}
		
		return result;
	}
	//Phương thức nhập vào một bảng các dữ liệu, tham số truy�?n vào là một mảng String và kiểu bảng
	
	public boolean importTable(int tableType, String[] valueData) {
		//Tạo đối tượng đệm cho quá trình chuyển dữ liệu từ mảng vào trong cơ sở dữ liệu MySQL
		
		if(data == null) {
			data = new SqlData(tableType); 
		}
		else {
			data.convertDataType(tableType);
		}
		//Chuyển dữ liệu vào mảng của đối tượng data và chuyển vào cơ sở dữ liệu
			
		if(data.setValueProperties(valueData) == true) {
			try {
				this.statement = this.connector.createStatement();
				
				this.statement.executeUpdate(data.convertIntoInsertSql());
				
				this.statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Chuyển dữ liệu vào trong đối tượng kiểu SqlData thành công");
				
			return true;
		}
		else {
			System.out.println("Chuyển dữ liệu không thành công do tham số không đúng");
			
			return false;
		}
	}
	//Phương thức nhập dữ liệu từ bảng hai chi�?u vào trong cơ sở dữ liệu
	
	public boolean importFromTransport (SqlDataTransport transport) {
		boolean result = true;
		
		if(transport.getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_ID].equals(null) || transport.getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_ID].equals("")) {
			result = result && this.importTable(SqlProcessor.TABLE_PLACE, transport.getDataArray()[SqlProcessor.TABLE_PLACE].toDataImport());
			transport.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_PLACEID] = this.getLastId(SqlProcessor.TABLE_PLACE);
		}
		else {
			transport.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_PLACEID] = transport.getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_ID];
		}
		
		if(transport.getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_ID].equals(null) || transport.getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_ID].equals("")) {
			result = result && this.importTable(SqlProcessor.TABLE_TYPE, transport.getDataArray()[SqlProcessor.TABLE_TYPE].toDataImport());
			transport.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_TYPEID] = this.getLastId(SqlProcessor.TABLE_TYPE);
		}
		else {
			transport.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_TYPEID] = transport.getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_ID];
		}
		
		result = result && this.importTable(SqlProcessor.TABLE_ACTIVITY, transport.getDataArray()[SqlProcessor.TABLE_ACTIVITY].toDataImport());
		
		transport.getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_ACTID] = this.getLastId(SqlProcessor.TABLE_ACTIVITY);
		transport.getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_USERID] = this.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID];
		result = result && this.importTable(SqlProcessor.TABLE_USER_ACTIVITY, transport.getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].toDataImport());
		
		transport.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_ACTID] = this.getLastId(SqlProcessor.TABLE_ACTIVITY);
		result = result && this.importTable(SqlProcessor.TABLE_TIME, transport.getDataArray()[SqlProcessor.TABLE_TIME].toDataImport());
		
		return result;
	}
	//Phương thức lấy ra Id của bộ vừa thêm vào một bảng trong MySQL
	
	private String getLastId(int typeTable) {
		String nameTable = this.convertNameTable(typeTable);
		int temple = 0;
		String firstSql = "SELECT count(Id) FROM " + nameTable;
		String secondSql = new String();
		
		try {
			this.preStatement = this.connector.prepareStatement(firstSql);
			this.resultSql = this.preStatement.executeQuery();
			
			while(this.resultSql.next()) {
				temple = Integer.parseInt(this.resultSql.getString("count(Id)"));
			}
			
			secondSql = "SELECT * FROM " + nameTable + " LIMIT " + Integer.toString(temple - 1) + ", " + Integer.toString(temple);
			this.preStatement = connector.prepareStatement(secondSql);
			this.resultSql = this.preStatement.executeQuery();
			
			while(this.resultSql.next()) {
				nameTable = this.resultSql.getString("Id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nameTable;
	}
	//Phương thức tạo ra phần đi�?u kiện cho truy vấn SELECT
	
	private String createConditionClause(String[] condition) {
		String result = "";
		
		if(condition.length != SqlProcessor.SIZE_ARRAY_CONDITION) {
			System.out.println("Lỗi tham số truy�?n vào hàm tạo đi�?u kiện cho phần đi�?u kiện của truy vấn SELECT!");
		}
		else {
			for(int i = 0; i < condition.length; i ++) {
				switch(i) {
					case SqlProcessor.CONDITION_USER_ID: {
						if((!condition[SqlProcessor.CONDITION_USER_ID].equals("")) && (!condition[SqlProcessor.CONDITION_USER_ID].equals(null))) {
							if(result.equals("")) {
								result = result + " WHERE user.Id = \"" + condition[SqlProcessor.CONDITION_USER_ID] + "\"";
							}
							else {
								result = result + "AND user.Id = \"" + condition[SqlProcessor.CONDITION_USER_ID] + "\"";
							}
						}
					}
						break;
					case SqlProcessor.CONDITION_TYPE_ID: {
						if((!condition[SqlProcessor.CONDITION_TYPE_ID].equals("")) && (!condition[SqlProcessor.CONDITION_TYPE_ID].equals(null))) {
							if(result.equals("")) {
								result = result + " WHERE type.Id = \"" + condition[SqlProcessor.CONDITION_TYPE_ID] + "\"";
							}
							else {
								result = result + " AND type.Id = \"" + condition[SqlProcessor.CONDITION_TYPE_ID] + "\"";
							}
						}
					}
						break;
					case SqlProcessor.CONDITION_PLACE_ID: {
						if((!condition[SqlProcessor.CONDITION_PLACE_ID].equals("")) && (!condition[SqlProcessor.CONDITION_PLACE_ID].equals(null))) {
							if(result.equals("")) {
								result = result + " WHERE place.Id = \"" + condition[SqlProcessor.CONDITION_PLACE_ID] + "\"";
							}
							else {
								result = result + " AND place.Id = \"" + condition[SqlProcessor.CONDITION_PLACE_ID] + "\"";
							}
						}
					}
						break;
					case SqlProcessor.CONDITION_RINGSTATUS: {
						if((!condition[SqlProcessor.CONDITION_RINGSTATUS].equals("")) && (!condition[SqlProcessor.CONDITION_RINGSTATUS].equals(null))) {
							if(result.equals("")) {
								result = result + " WHERE activity.RingStatus = " + condition[SqlProcessor.CONDITION_RINGSTATUS];
							}
							else {
								result = result + " AND activity.RingStatus = " + condition[SqlProcessor.CONDITION_RINGSTATUS];
							}
						}
					}
						break;
					case SqlProcessor.CONDITION_TIME_BEGIN: {
						if((!condition[SqlProcessor.CONDITION_TIME_BEGIN].equals("")) && (!condition[SqlProcessor.CONDITION_TIME_BEGIN].equals(null))) {
							if(result.equals("")) {
								result = result + " WHERE time.Time >= \"" + condition[SqlProcessor.CONDITION_TIME_BEGIN] + "\"";
							}
							else {
								result = result + " AND time.Time >= \"" + condition[SqlProcessor.CONDITION_TIME_BEGIN] + "\"";
							}
						}
					}
						break;
					case SqlProcessor.CONDITION_TIME_END: {
						if((!condition[SqlProcessor.CONDITION_TIME_END].equals("")) && (!condition[SqlProcessor.CONDITION_TIME_END].equals(null))) {
							if(result.equals("")) {
								result = result + " WHERE time.Time <= \"" + condition[SqlProcessor.CONDITION_TIME_END] + "\"";
							}
							else {
								result = result + " AND time.Time <= \"" + condition[SqlProcessor.CONDITION_TIME_END] + "\"";
							}
						}
					}
						break;
					case SqlProcessor.CONDITION_TIMEREGISTER_BEGIN: {
						if((!condition[SqlProcessor.CONDITION_TIMEREGISTER_BEGIN].equals("")) && (!condition[SqlProcessor.CONDITION_TIMEREGISTER_BEGIN].equals(null))) {
							if(result.equals("")) {
								result = result + " WHERE user_activity.TimeRegister >= \"" + condition[SqlProcessor.CONDITION_TIMEREGISTER_BEGIN] + "\"";
							}
							else {
								result = result + " AND user_activity.TimeRegister >= \"" + condition[SqlProcessor.CONDITION_TIMEREGISTER_BEGIN] + "\"";
							}
						}
					}
						break;
					case SqlProcessor.CONDITION_TIMEREGISTER_END: {
						if((!condition[SqlProcessor.CONDITION_TIMEREGISTER_BEGIN].equals("")) && (!condition[SqlProcessor.CONDITION_TIMEREGISTER_END].equals(null))) {
							if(result.equals("")) {
								result = result + " WHERE user_activity.TimeRegister <= \"" + condition[SqlProcessor.CONDITION_TIMEREGISTER_END] + "\"";
							}
							else {
								result = result + " AND user_activity.TimeRegister <= \"" + condition[SqlProcessor.CONDITION_TIMEREGISTER_END] + "\"";
							}
						}
					}
						break;
				}
			}
		}
		
		return result;
	}
	//Khai báo phương thức tạo ra truy vấn l�?c dữ liệu từ tất cả các đi�?u kiện đầu vào
	
	private String createSelectQuery(int typeQuery, String[] condition) {
		String sql = "";
		String conditionClause = "";
		
		if(condition.length != SqlProcessor.SIZE_ARRAY_CONDITION) {
			System.out.println("Lỗi tạo truy vấn Select: kích thước mảng đi�?u kiện không th�?a mãn!");
		}
		else {
			if(typeQuery == SqlProcessor.TYPE_SELECT_QUERY_COUNT) {
				sql = "SELECT count(*) FROM type INNER JOIN activity ON activity.TypeId = type.Id " +
						"INNER JOIN place ON activity.PlaceId = place.Id " +
						"INNER JOIN time ON time.ActId = activity.Id " +
						"INNER JOIN user_activity ON user_activity.ActId = activity.Id " +
						"INNER JOIN user ON user.Id = user_activity.UserId";
				sql = sql + this.createConditionClause(condition);
			}
			else if(typeQuery == SqlProcessor.TYPE_SELECT_QUERY_TUBLE) {
				sql = "SELECT type.Id AS \"" + SqlProcessor.AS_TYPE_ID + "\", type.Name AS \"" + SqlProcessor.AS_TYPE_NAME + "\", type.Description AS \"" + SqlProcessor.AS_TYPE_DESCRIPTION + "\", " +
						"place.Id AS \"" + SqlProcessor.AS_PLACE_ID + "\", place.Name AS \"" + SqlProcessor.AS_PLACE_NAME + "\", place.Address AS \"" + SqlProcessor.AS_PLACE_ADDRESS + "\", place.Description AS \"" + SqlProcessor.AS_PLACE_DESCRIPTION + "\", " +
						"time.Id AS \"" + SqlProcessor.AS_TIME_ID + "\", time.ActId AS \"" + SqlProcessor.AS_TIME_ACTID + "\", time.Time AS \"" + SqlProcessor.AS_TIME_TIME + "\", time.Note AS \"" + SqlProcessor.AS_TIME_NOTE + "\", " +
						"user_activity.UserId \"" + SqlProcessor.AS_USER_ACTIVITY_USERID + "\", user_activity.ActId AS \"" + SqlProcessor.AS_USER_ACTIVITY_ACTID + "\", user_activity.TimeRegister \"" + SqlProcessor.AS_USER_ACTIVITY_TIMEREGISTER + "\", " +
						"activity.Id AS \"" + SqlProcessor.AS_ACTIVITY_ID + "\", activity.TypeId AS \"" + SqlProcessor.AS_ACTIVITY_TYPEID + "\", activity.PlaceId AS \"" + SqlProcessor.AS_ACTIVITY_PLACEID + "\", activity.Note AS \"" + SqlProcessor.AS_ACTIVITY_NOTE + "\", activity.RingStatus AS \"" + SqlProcessor.AS_ACTIVITY_RINGSTATUS + "\", " +
						"user.Id AS \"" + SqlProcessor.AS_USER_ID + "\", user.Name AS \"" + SqlProcessor.AS_USER_NAME + "\", user.Password AS \"" + SqlProcessor.AS_USER_PASSWORD + "\", user.Email AS \"" + SqlProcessor.AS_USER_EMAIL + "\", user.Address AS \"" + SqlProcessor.AS_USER_ADDRESS + "\" FROM type "  + 
						"INNER JOIN activity ON activity.TypeId = type.Id " +
						"INNER JOIN place ON activity.PlaceId = place.Id " +
						"INNER JOIN time ON time.ActId = activity.Id " +
						"INNER JOIN user_activity ON user_activity.ActId = activity.Id " +
						"INNER JOIN user ON user.Id = user_activity.UserId";
				sql = sql + this.createConditionClause(condition);
			}
		}
		
		return sql;
	}
	//Khai báo phương thức đếm tất cả các bộ th�?a mãn đi�?u kiện đầu vào của phương thức l�?c
	
	public int countTupleSelectClause(String[] condition) {
		int result = -1;
		
		if(this.connector != null) {
			try {
				this.preStatement = this.connector.prepareStatement(this.createSelectQuery(SqlProcessor.TYPE_SELECT_QUERY_COUNT, condition));
				this.preStatement.clearParameters();
				this.resultSql = this.preStatement.executeQuery();
				
				this.resultSql.next();
				result = Integer.parseInt(this.resultSql.getString("count(*)"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/*
	 * Khai báo phương thức để l�?c ra tất cả các bộ th�?a mãn đi�?u kiện l�?c ra các bộ th�?a mãn với các đi�?u kiện đầu vào
	 * và trả v�? các kết quả là một mảng hai chi�?u các phần tử theo số lương giới hạn của các phần tử và tối đa là 10 phần tử kiểu SqlDataTransport
	 */
	
	public SqlDataTransport[] exportFromMySQL(int elementBegin, String[] condition) {
		String sql = "";
		SqlDataTransport[] result = null;
		SqlDataTransport[] templeArray = new SqlDataTransport[SqlProcessor.SIZE_RESULT_SET];
		int temple = 0;
		
		if(elementBegin >= 0) {
			sql = this.createSelectQuery(SqlProcessor.TYPE_SELECT_QUERY_TUBLE, condition) + " LIMIT " + Integer.toString(elementBegin) + ", " + SqlProcessor.SIZE_RESULT_SET;
			
			try {
				for(int i = 0; i < templeArray.length; i ++) {
					templeArray[i] = new SqlDataTransport(SqlDataTransport.TYPE_EXPORT);
				}
				
				this.preStatement = this.connector.prepareStatement(sql);
				
				this.resultSql = this.preStatement.executeQuery();
				
				while(this.resultSql.next()) {
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_NAME] = resultSql.getString(SqlProcessor.AS_USER_NAME);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_ID] = resultSql.getString(SqlProcessor.AS_USER_ID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_PASSWORD] = resultSql.getString(SqlProcessor.AS_USER_PASSWORD);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_EMAIL] = resultSql.getString(SqlProcessor.AS_USER_EMAIL);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_ADDRESS] = resultSql.getString(SqlProcessor.AS_USER_ADDRESS);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_NAME] = resultSql.getString(SqlProcessor.AS_PLACE_NAME);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_ADDRESS] = resultSql.getString(SqlProcessor.AS_PLACE_ADDRESS);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_DESCRIPTION] = resultSql.getString(SqlProcessor.AS_PLACE_DESCRIPTION);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_ID] = resultSql.getString(SqlProcessor.AS_PLACE_ID);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_ACTID] = resultSql.getString(SqlProcessor.AS_USER_ACTIVITY_ACTID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_USERID] = resultSql.getString(SqlProcessor.AS_USER_ACTIVITY_USERID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_TIMEREGISTER] = resultSql.getString(SqlProcessor.AS_USER_ACTIVITY_TIMEREGISTER);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_ACTID] = resultSql.getString(SqlProcessor.AS_TIME_ACTID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_ID] = resultSql.getString(SqlProcessor.AS_TIME_ID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_TIME] = resultSql.getString(SqlProcessor.AS_TIME_TIME);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_NOTE] = resultSql.getString(SqlProcessor.AS_TIME_NOTE);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_ID] = resultSql.getString(SqlProcessor.AS_ACTIVITY_ID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_TYPEID] = resultSql.getString(SqlProcessor.AS_ACTIVITY_TYPEID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_PLACEID] = resultSql.getString(SqlProcessor.AS_ACTIVITY_PLACEID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_NOTE] = resultSql.getString(SqlProcessor.AS_ACTIVITY_NOTE);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_RINGSTATUS] = resultSql.getString(SqlProcessor.AS_ACTIVITY_RINGSTATUS);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_ID] = resultSql.getString(SqlProcessor.AS_TYPE_ID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_DESCRIPTION] = resultSql.getString(SqlProcessor.AS_TYPE_DESCRIPTION);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_NAME] = resultSql.getString(SqlProcessor.AS_TYPE_NAME);
					
					temple ++;
				}
				
				result = new SqlDataTransport[this.countRealElement(templeArray)];
				
				for(int i = 0; i < result.length; i ++) {
					result[i] = templeArray[i];
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
		}
		else {
			return null;
		}
	}
	//Khai báo phương thức đếm tất cả các hoạt động có đặt chuông và có th�?i gian lớn hơn th�?i điểm truy vấn
	
	public int countTupleRing(String[] condition) {
		int result = -1;
		
		if(this.connector != null) {
			try {
				this.preStatement = this.connector.prepareStatement(this.createSelectQuery(SqlProcessor.TYPE_SELECT_QUERY_COUNT, condition)  + " AND time.Time > current_timestamp");
				
				this.preStatement.clearParameters();
				this.resultSql = this.preStatement.executeQuery();
				
				this.resultSql.next();
				result = Integer.parseInt(this.resultSql.getString("count(*)"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//Phương thức lấy ra các hoạt động có Ring và có ngày thực hiện lớn hơn th�?i điểm hiện tại
	
	public SqlDataTransport[] exportActivityRing(String[] condition) {
		String sql = "";
		SqlDataTransport[] templeArray;
		int temple = 0;
		
		if((temple = this.countTupleRing(condition)) > 0) {
			sql = this.createSelectQuery(SqlProcessor.TYPE_SELECT_QUERY_TUBLE, condition)  + " AND time.Time > current_timestamp";
			templeArray = new SqlDataTransport[temple];
			System.out.println(sql);
			temple = 0;
			
			try {
				for(int i = 0; i < templeArray.length; i ++) {
					templeArray[i] = new SqlDataTransport(SqlDataTransport.TYPE_EXPORT);
				}
				
				this.preStatement = this.connector.prepareStatement(sql);
				
				this.resultSql = this.preStatement.executeQuery();
				
				while(this.resultSql.next()) {
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_NAME] = resultSql.getString(SqlProcessor.AS_USER_NAME);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_ID] = resultSql.getString(SqlProcessor.AS_USER_ID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_PASSWORD] = resultSql.getString(SqlProcessor.AS_USER_PASSWORD);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_EMAIL] = resultSql.getString(SqlProcessor.AS_USER_EMAIL);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_ADDRESS] = resultSql.getString(SqlProcessor.AS_USER_ADDRESS);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_NAME] = resultSql.getString(SqlProcessor.AS_PLACE_NAME);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_ADDRESS] = resultSql.getString(SqlProcessor.AS_PLACE_ADDRESS);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_DESCRIPTION] = resultSql.getString(SqlProcessor.AS_PLACE_DESCRIPTION);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_ID] = resultSql.getString(SqlProcessor.AS_PLACE_ID);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_ACTID] = resultSql.getString(SqlProcessor.AS_USER_ACTIVITY_ACTID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_USERID] = resultSql.getString(SqlProcessor.AS_USER_ACTIVITY_USERID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_TIMEREGISTER] = resultSql.getString(SqlProcessor.AS_USER_ACTIVITY_TIMEREGISTER);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_ACTID] = resultSql.getString(SqlProcessor.AS_TIME_ACTID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_ID] = resultSql.getString(SqlProcessor.AS_TIME_ID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_TIME] = resultSql.getString(SqlProcessor.AS_TIME_TIME);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_NOTE] = resultSql.getString(SqlProcessor.AS_TIME_NOTE);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_ID] = resultSql.getString(SqlProcessor.AS_ACTIVITY_ID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_TYPEID] = resultSql.getString(SqlProcessor.AS_ACTIVITY_TYPEID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_PLACEID] = resultSql.getString(SqlProcessor.AS_ACTIVITY_PLACEID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_NOTE] = resultSql.getString(SqlProcessor.AS_ACTIVITY_NOTE);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_RINGSTATUS] = resultSql.getString(SqlProcessor.AS_ACTIVITY_RINGSTATUS);
					
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_ID] = resultSql.getString(SqlProcessor.AS_TYPE_ID);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_DESCRIPTION] = resultSql.getString(SqlProcessor.AS_TYPE_DESCRIPTION);
					templeArray[temple].getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_NAME] = resultSql.getString(SqlProcessor.AS_TYPE_NAME);
					
					temple ++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return templeArray;
		}
		else {
			templeArray = new SqlDataTransport[1];
			templeArray[0] = new SqlDataTransport(SqlDataTransport.TYPE_EXPORT);
			templeArray[0].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_TIME] = "Dung Harry.0";
			
			return templeArray;
		}
	}
	//Phương thức đếm số phần tử thực sự tồn tại trong một kết quả trả v�? của phương thức exportFromMySQL(int, String[])
	
	public int countRealElement(SqlDataTransport[] arrayResultTransport) {
		int result = 0;
		
		for(int i = 0; i < arrayResultTransport.length; i ++) {
			if((arrayResultTransport[i].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_ID].equals("")) || (arrayResultTransport[i].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[SqlData.USER_ID].equals(null))) {
				break;
			}
			result ++;
		}
			
		return result;
	}
	//Phương thức tạo ra mảng String phục vụ cho quá trình cập nhật vào MySQL
	
	public String[] createArrayString(int typeTable) {
		String[] result = new String[this.convertIntoNumProperties(typeTable)];
		
		for(int i = 0; i < result.length; i ++) {
			result[i] = "";
		}
		
		return result;
	}
	//Khai báo phương thức cập nhật thông tin của các bảng trong cơ sở dữ liệu
	
	public boolean updateTable(int typeTable, String[] value, String conditionId) {
		boolean flag = true;
		
		for(int i = 0; i < value.length; i ++) {
			if((value[i].equals("")) || (value[i] == null)) {
				if(i == value.length - 1) {
					flag = false;
				}
			}
			else {
				break;
			}
		}
		
		if(flag) {
			if(value.length == this.convertIntoNumProperties(typeTable)) {
				if(this.data == null) {
					this.data = new SqlData(typeTable);
				}
				else {
					this.data.convertDataType(typeTable);
				}
				String sql = this.data.convertIntoUpdateSql(typeTable, value, conditionId);
				
				try {
					this.preStatement = connector.prepareStatement(sql);
					this.preStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
				if(typeTable == SqlProcessor.TABLE_USER) {
					if((!value[SqlData.USER_NAME].equals("")) && (!value[SqlData.USER_NAME].equals(null))) {
						XmlProcessor.updateUser(XmlProcessor.USER_NAME, value[SqlData.USER_NAME]);
					}
				
					if((!value[SqlData.USER_PASSWORD].equals("")) && (!value[SqlData.USER_PASSWORD].equals(null))) {
						XmlProcessor.updateUser(XmlProcessor.USER_PASSWORD, value[SqlData.USER_PASSWORD]);
					}
				}
			
				return true;
			}
			else {
				System.out.println("Update information on table false because parameter don't correct!");
			
				return false;
			}
		}
		return false;
	}
	/*Phương thức xóa một bộ xác định trong cơ sở dữ liệu, có thể dẫn tới việc xóa nhi�?u bộ trong các bảng khác mà 
	  khác mà định nghĩa cơ sở dữ liệu không thể tự động cập nhật hoặc xóa b�? theo khóa ngoài
	*/
	public boolean deleteTuple(int typeTable, String conditionId) {
		ArrayList<String> result = null;
		ArrayList<String> result1 = this.getIdFromUser(SqlProcessor.TABLE_PLACE, conditionId);
		
		if(this.data == null) {
			this.data = new SqlData(typeTable);
		}
		else {
			this.data.convertDataType(typeTable);
		}
		String sql = this.data.convertIntoDeleteSql(typeTable, conditionId);
		
		if(typeTable == SqlProcessor.TABLE_PLACE) {
			result = this.getActivityId(conditionId);
			
			this.removeTubleFromArray(SqlProcessor.TABLE_ACTIVITY, result);
		}
		else if(typeTable == SqlProcessor.TABLE_USER) {
			if((result = this.getIdFromUser(SqlProcessor.TABLE_TYPE, conditionId)) != null) {
				this.removeTubleFromArray(SqlProcessor.TABLE_TYPE, result);
			}
			if(result1 != null) {
				this.removeTubleFromArray(SqlProcessor.TABLE_PLACE, result1);
			}
		}
	
		try {
			this.preStatement = connector.prepareStatement(sql);
			
			this.preStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	/*Khai báo phương thức xóa tất cả các các bộ trong cơ sở dữ liệu tương ứng với một bảng và 
	  có giá trị Id của bộ cần xóa trong một ArrayList<String>
	*/
	
	private void removeTubleFromArray(int typeTable, ArrayList<String> array) {
		for(int i = 0; i < array.size(); i ++) {
			this.deleteTuple(typeTable, array.get(i));
		}
	}
	//Khai báo phương thức trả v�? mảng các Id của các bộ activity tương ứng với Id của một bộ Place
	
	public ArrayList<String> getActivityId(String placeId) {
		ArrayList<String> result = new ArrayList<String>();
		
		try {
			String sql = "SELECT DISTINCT activity.Id FROM activity INNER JOIN place on place.Id = activity.PlaceId WHERE place.Id = ?"; 
			
			this.preStatement = this.connector.prepareStatement(sql);
			this.preStatement.setString(1, placeId);
			this.resultSql = this.preStatement.executeQuery();
			
			while(this.resultSql.next()) {
				result.add(this.resultSql.getString("Id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	//Phương thức trả v�? mảng các array của place hoặc type tương ứng với một user
	
	public ArrayList<String> getIdFromUser(int typeTable, String userId) {
		ArrayList<String> result = new ArrayList<String>();
		String nameTable = this.convertNameTable(typeTable);
		
		if(typeTable == SqlProcessor.TABLE_PLACE || typeTable == SqlProcessor.TABLE_TYPE) {
			try {
				String sql = "SELECT DISTINCT " + nameTable + ".Id FROM type INNER JOIN activity ON type.Id = activity.TypeId INNER JOIN place ON place.Id = activity.PlaceId INNER JOIN user_activity ON user_activity.ActId = activity.Id WHERE user_activity.UserId = \"" + userId + "\"";
				this.preStatement = this.connector.prepareStatement(sql);
				this.resultSql = this.preStatement.executeQuery();
				
				while(this.resultSql.next()) {
					result.add(this.resultSql.getString("Id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			result = null;
		}
		
		return result;
	}
	//Khai báo phương thức lấy ra các thông tin v�? bảng type hoặc place tương ứng với một ngư�?i sử dụng
	
	public ArrayList<SqlData> exportSelectionInformation(int typeTable, String userId) {
		ArrayList<SqlData> result = new ArrayList<SqlData>();
		SqlData[] temple; 
		String[] parameter = new String[this.convertIntoNumProperties(typeTable)];
		String nameTable = this.convertNameTable(typeTable);
		int index = 0;
		String sqlPlace = "select distinct place.Id, place.Name, place.Address, place.Description from type inner join activity on type.Id = activity.TypeId inner join place on place.Id = activity.PlaceId inner join user_activity on activity.Id = user_activity.ActId where user_activity.UserId = " + userId;
		String sqlType = "select distinct type.Id, type.Name, type.Description from type inner join activity on type.Id = activity.TypeId inner join place on place.Id = activity.PlaceId inner join user_activity on activity.Id = user_activity.ActId where user_activity.UserId = " + userId;
		String extraSql = "select count(" + nameTable + ".Id) from type inner join activity on type.Id = activity.TypeId inner join place on place.Id = activity.PlaceId inner join user_activity on activity.Id = user_activity.ActId where user_activity.UserId = " + userId;
		
		try {
			this.preStatement = this.connector.prepareStatement(extraSql);
			this.resultSql = this.preStatement.executeQuery();
			
			this.resultSql.next();
			
			temple = new SqlData[Integer.parseInt(this.resultSql.getString("count(" + nameTable + ".Id)"))];
			
			if(typeTable == SqlProcessor.TABLE_PLACE) {
				for(int i = 0; i < temple.length; i ++) {
					temple[i] = new SqlData(typeTable);
				}
				
				this.preStatement = connector.prepareStatement(sqlPlace);
				this.resultSql = this.preStatement.executeQuery();
				
				while(this.resultSql.next()) {
					parameter[SqlData.PLACE_ADDRESS] = this.resultSql.getString("Address");
					parameter[SqlData.PLACE_DESCRIPTION] = this.resultSql.getString("Description");
					parameter[SqlData.PLACE_ID] = this.resultSql.getString("Id");
					parameter[SqlData.PLACE_NAME] = this.resultSql.getString("Name");
					
					temple[index].setValueProperties(parameter);
					result.add(temple[index]);
					index ++;
				}
			}
			else if(typeTable == SqlProcessor.TABLE_TYPE) {
				for(int i = 0; i < temple.length; i ++) {
					temple[i] = new SqlData(typeTable);
				}
				
				this.preStatement = connector.prepareStatement(sqlType);
				this.resultSql = this.preStatement.executeQuery();
				
				while(this.resultSql.next()) {
					parameter[SqlData.TYPE_ID] = this.resultSql.getString("Id");
					parameter[SqlData.TYPE_DESCRIPTION] = this.resultSql.getString("Description");
					parameter[SqlData.TYPE_NAME] = this.resultSql.getString("Name");
					
					temple[index].setValueProperties(parameter);
					result.add(temple[index]);
					
					index ++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	//Khai báo phương thức cho phép lấy ra thông tin của ngư�?i sử dụng khi đăng nhập vào hệ thống
	
	public SqlData getUserInformaion(String name, String password) {
		try {
			String sql = "SELECT * FROM user WHERE Name = ? AND Password = ?";
		
			this.preStatement = connector.prepareStatement(sql);
			this.preStatement.setString(1, name);
			this.preStatement.setString(2, password);
			
			this.resultSql = this.preStatement.executeQuery();
		
			if(this.data == null) {
				this.data = new SqlData(SqlProcessor.TABLE_USER);
			}
			else {
				this.data.convertDataType(SqlProcessor.TABLE_USER);
			}
			
			this.resultSql.next();
			
			this.data.getValueProperties()[SqlData.USER_ID] = this.resultSql.getString("Id");
			this.data.getValueProperties()[SqlData.USER_NAME] = this.resultSql.getString("Name");
			this.data.getValueProperties()[SqlData.USER_PASSWORD] = this.resultSql.getString("Password");
			this.data.getValueProperties()[SqlData.USER_EMAIL] = this.resultSql.getString("Email");
			this.data.getValueProperties()[SqlData.USER_ADDRESS] = this.resultSql.getString("Address");
			
			XmlProcessor.updateUser(this.data.getValueProperties()[SqlData.USER_NAME], this.data.getValueProperties()[SqlData.USER_PASSWORD]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.data;
	}
	/*
	 * Khai báo phương thức tạo ra thông tin của một ngư�?i sử dụng nhập vào 
	 * có th�?a mãn yêu cầu đăng nhập hay không! Không tồn tại cả tên ngư�?i sử dụng và mật khẩu hợp nhau
	 * nếu th�?a mãn thì sẽ tự động cập nhật lại các thông tin v�? ngư�?i dùng và tại khoản trong file xml
	 */
	
	public boolean checkAccount(String name, String password) {
		boolean result = false;
		
		try {
			String sql = "SELECT count(Id) FROM user WHERE Name = ? AND Password = ?";
			
			this.preStatement = connector.prepareStatement(sql);
			this.preStatement.setString(1, name);
			this.preStatement.setString(2, password);
		
			this.resultSql = this.preStatement.executeQuery();
			
			if(this.resultSql.next() && Integer.parseInt(this.resultSql.getString("count(Id)")) == 1) {
				XmlProcessor.updateUser(name, password);
				result = true;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	//Phương thức thức chuyển đổi từ loại bảng ra số lượng thuộc tính của bảng
	
	private int convertIntoNumProperties(int typeTable) {
		int result = 0;
		
		switch(typeTable) {
			case SqlProcessor.TABLE_ACTIVITY: result = SqlData.SIZE_ARRAY_ACTIVITY;
				break;
			case SqlProcessor.TABLE_PLACE: result = SqlData.SIZE_ARRAY_PLACE;
				break;
			case SqlProcessor.TABLE_TIME: result = SqlData.SIZE_ARRAY_TIME;
				break;
			case SqlProcessor.TABLE_TYPE: result = SqlData.SIZE_ARRAY_TYPE;
				break;
			case SqlProcessor.TABLE_USER: result = SqlData.SIZE_ARRAY_USER;
				break;
			case SqlProcessor.TABLE_USER_ACTIVITY: result = SqlData.SIZE_ARRAY_USER_ACTIVITY;
				break;
			default: result = -1;
				break;
		}
		
		return result;
	}
	//Phương thức đ�?c dữ liệu từ file SQL và trả v�? một ArrayList của các truy vấn có trong file SQL
	
	private ArrayList<String> readSqlFile(String pathFile) {
		String queryLine = new String();
		StringBuffer bufString = new StringBuffer();
		ArrayList<String> result = new ArrayList<String>();
		
		try {
			FileReader reader = new FileReader(pathFile);
			BufferedReader buffer = new BufferedReader(reader);
			int indexCharacter = 0;
			
			while((queryLine = buffer.readLine()) != null) {
				indexCharacter = queryLine.indexOf("/*");
				
				if(indexCharacter != -1) {
					
					while(!queryLine.contains("*/")) {
						queryLine = buffer.readLine();
					}
					
					indexCharacter = queryLine.indexOf("*/");
					
					if(indexCharacter != -1) {
						if(queryLine.endsWith("*/")) {
							queryLine = "";
						}
						else {
							queryLine = new String(queryLine.substring(indexCharacter + 2, queryLine.length() - 1));
						}
					}
				}
				
				if(queryLine != null) {
					bufString.append(queryLine + " ");
				}
			}
			
			buffer.close();
			
			String[] splitIntoQuery = bufString.toString().split(";");

			for(int i = 0; i < splitIntoQuery.length; i ++) {
				if(!splitIntoQuery[i].trim().equals("") && !splitIntoQuery[i].trim().equals("\t")) {
					result.add(splitIntoQuery[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	//Phương thức tạo mới hoàn toàn một cơ sở dữ liệu với tên xác định
	
	public boolean createNewDatabase(String ipAddress, String databaseName, String userName, String userPassword) {
		String firstSQL = "CREATE DATABASE " + databaseName;
		ArrayList<String> arrayQuery = this.readSqlFile("resources/data/sql/CreateRelations.sql");
		boolean result = false;
		
		try {
			if(this.connector == null) {
				if(this.createConnection(ipAddress, "mysql", userName, userPassword)) {
					result = true;
				}
			}
			else {
				this.connector.close();
				if(this.createConnection(ipAddress, "mysql", userName, userPassword)) {
					result = true;
				}
			}
			
			if(result) {
				this.statement = connector.createStatement();
				this.statement.executeUpdate(firstSQL);
			
				this.connector.close();
				this.createConnection(ipAddress, databaseName, userName, userPassword);
			
				this.statement = connector.createStatement();
			
				for(int i = 0; i < arrayQuery.size(); i ++) {
					this.statement.executeUpdate(arrayQuery.get(i));
			 	}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
		
		return result;
	}
	//Phương thức lấy ra kết nối tới cơ sở dữ liệu
	
	public Connection getConnector() {
		return this.connector;
	}
	//Phương thức in ra thông tin lấy được từ cơ sở dữ liệu
	
	public void printDataExport(int numberBegin, String[] clause) {
		SqlDataTransport[] a = this.exportFromMySQL(numberBegin, clause);
		
		System.out.println(this.countRealElement(a));
		
		for(int i = 0; i < this.countRealElement(a); i ++) {
			System.out.println("------------------------------------------------");
			
			System.out.println("Thông tin v�? user \n");
			for(int k = 0; k < SqlData.SIZE_ARRAY_USER; k ++) {
				System.out.println(a[i].getDataArray()[SqlProcessor.TABLE_USER].getNameProperties()[k] + ": " + a[i].getDataArray()[SqlProcessor.TABLE_USER].getValueProperties()[k]);
			}
				
			System.out.println("Thông tin v�? user_activity \n");
			for(int k = 0; k < SqlData.SIZE_ARRAY_USER_ACTIVITY; k ++) {
				System.out.println(a[i].getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getNameProperties()[k] + ": " + a[i].getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[k]);
			}
				
			System.out.println("Thông tin v�? activity \n");
			for(int k = 0; k < SqlData.SIZE_ARRAY_ACTIVITY; k ++) {
				System.out.println(a[i].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getNameProperties()[k] + ": " + a[i].getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[k]);
			}
				
			System.out.println("Thông tin v�? time \n");
			for(int k = 0; k < SqlData.SIZE_ARRAY_TIME; k ++) {
				System.out.println(a[i].getDataArray()[SqlProcessor.TABLE_TIME].getNameProperties()[k] + ": " + a[i].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[k]);
			}
				
			System.out.println("Thông tin v�? type \n");
			for(int k = 0; k < SqlData.SIZE_ARRAY_TYPE; k ++) {
				System.out.println(a[i].getDataArray()[SqlProcessor.TABLE_TYPE].getNameProperties()[k] + ": " + a[i].getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[k]);
			}
				
			System.out.println("Thông tin v�? place \n");
			for(int k = 0; k < SqlData.SIZE_ARRAY_PLACE; k ++) {
				System.out.println(a[i].getDataArray()[SqlProcessor.TABLE_PLACE].getNameProperties()[k] + ": " + a[i].getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[k]);
			}			
		}
	}
}
