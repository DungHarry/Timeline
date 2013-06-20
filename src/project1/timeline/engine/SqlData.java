package project1.timeline.engine;

/*
 * �?ây là lớp mô ph�?ng kiểu cấu trúc dữ liệu dành cho việc nhập dữ liệu vào cơ sở dữ liệu MySQL
 */

public class SqlData {
	//Khai báo các loại quan hệ
	
	public static final int SIZE_ARRAY_USER = 5;
	public static final int SIZE_ARRAY_TYPE = 3;
	public static final int SIZE_ARRAY_PLACE = 4;
	public static final int SIZE_ARRAY_ACTIVITY = 5;
	public static final int SIZE_ARRAY_USER_ACTIVITY = 3;
	public static final int SIZE_ARRAY_TIME = 4;
	
	public static final int USER_NAME = 0;
	public static final int USER_PASSWORD = 1;
	public static final int USER_EMAIL = 2;
	public static final int USER_ADDRESS = 3;
	public static final int USER_ID = 4;

	public static final int PLACE_NAME = 0;
	public static final int PLACE_DESCRIPTION = 1;
	public static final int PLACE_ADDRESS = 2;
	public static final int PLACE_ID = 3;
	
	public static final int USER_ACTIVITY_USERID = 0;
	public static final int USER_ACTIVITY_ACTID = 1;
	public static final int USER_ACTIVITY_TIMEREGISTER = 2;
	
	public static final int ACTIVITY_PLACEID = 0;
	public static final int ACTIVITY_TYPEID = 1;
	public static final int ACTIVITY_NOTE = 2;
	public static final int ACTIVITY_RINGSTATUS = 3;
	public static final int ACTIVITY_ID = 4;
	
	public static final int TIME_ACTID = 0;
	public static final int TIME_TIME = 1;
	public static final int TIME_NOTE = 2;
	public static final int TIME_ID = 3;
	
	public static final int TYPE_NAME = 0;
	public static final int TYPE_DESCRIPTION = 1;
	public static final int TYPE_ID = 2;
	//Khai báo biến sử dụng trong chương trình
	
	private int type = 0;  //Biến này được sử dụng để chỉ ra loại dữ liệu được nhập vào bảng nào trong cơ sở dữ liệu
	private String[] nameProperties; //Mảng tên các thuộc tính của quan hệ
	private String[] valueProperties;//Mảng các giá trị thuộc tính ứng với từng phần tử có trong quan hệ
	//Hàm khởi tạo
	
	public SqlData(int typeTable) {
		this.initializeData(typeTable);
	}
	//Hàm khởi tạo các giá trị thuộc tính tương ứng với từng quan hệ
	
	private void initializeData(int type) {
		this.type = type;
		
		switch(type) {
			case SqlProcessor.TABLE_USER: {
				//Khai báo kich thước của hai mảng được sử dụng trong lớp này
				
				this.nameProperties = new String[SqlData.SIZE_ARRAY_USER];
				this.valueProperties = new String[SqlData.SIZE_ARRAY_USER];
				//Khởi tạo các giá trị của mảng
				
				this.nameProperties[0] = "Name";
				this.nameProperties[1] = "Password";
				this.nameProperties[2] = "Email";
				this.nameProperties[3] = "Address";
				this.nameProperties[4] = "Id";
				
				for(int i = 0; i < SqlData.SIZE_ARRAY_USER; i ++) {
					this.valueProperties[i] = "";
				}
			} break;
			
			case SqlProcessor.TABLE_TYPE: {
				//Khai báo kich thước của hai mảng được sử dụng trong lớp này
				
				this.nameProperties = new String[SqlData.SIZE_ARRAY_TYPE];
				this.valueProperties = new String[SqlData.SIZE_ARRAY_TYPE];
				//Khởi tạo các giá trị của mảng
				
				this.nameProperties[0] = "Name";
				this.nameProperties[1] = "Description";
				this.nameProperties[2] = "Id";
				
				for(int i = 0; i < SqlData.SIZE_ARRAY_TYPE; i ++) {
					this.valueProperties[i] = "";
				}
			} break;
			
			case SqlProcessor.TABLE_PLACE: {
				//Khai báo kich thước của hai mảng được sử dụng trong lớp này
				
				this.nameProperties = new String[SqlData.SIZE_ARRAY_PLACE];
				this.valueProperties = new String[SqlData.SIZE_ARRAY_PLACE];
				//Khởi tạo các giá trị của mảng
				
				this.nameProperties[0] = "Name";
				this.nameProperties[1] = "Address";
				this.nameProperties[2] = "Description";
				this.nameProperties[3] = "Id";
				
				for(int i = 0; i < SqlData.SIZE_ARRAY_PLACE; i ++) {
					this.valueProperties[i] = "";
				}
			} break;
			
			case SqlProcessor.TABLE_ACTIVITY: {
				//Khai báo kich thước của hai mảng được sử dụng trong lớp này
				
				this.nameProperties = new String[SqlData.SIZE_ARRAY_ACTIVITY];
				this.valueProperties = new String[SqlData.SIZE_ARRAY_ACTIVITY];
				//Khởi tạo các giá trị của mảng
				
				this.nameProperties[0] = "PlaceId";
				this.nameProperties[1] = "TypeId";
				this.nameProperties[2] = "Note";
				this.nameProperties[3] = "RingStatus";
				this.nameProperties[4] = "Id";
				
				for(int i = 0; i < SqlData.SIZE_ARRAY_ACTIVITY; i ++) {
					this.valueProperties[i] = "";
				}
			} break;
			
			case SqlProcessor.TABLE_USER_ACTIVITY: {
				//Khai báo kich thước của hai mảng được sử dụng trong lớp này
				
				this.nameProperties = new String[SqlData.SIZE_ARRAY_USER_ACTIVITY];
				this.valueProperties = new String[SqlData.SIZE_ARRAY_USER_ACTIVITY];
				//Khởi tạo các giá trị của mảng
				
				this.nameProperties[0] = "UserId";
				this.nameProperties[1] = "ActId";
				this.nameProperties[2] = "TimeRegister";
				
				for(int i = 0; i < SqlData.SIZE_ARRAY_USER_ACTIVITY; i ++) {
					this.valueProperties[i] = "";
				}
			} break;
			
			case SqlProcessor.TABLE_TIME: {
				//Khai báo kich thước của hai mảng được sử dụng trong lớp này
				
				this.nameProperties = new String[SqlData.SIZE_ARRAY_TIME];
				this.valueProperties = new String[SqlData.SIZE_ARRAY_TIME];
				//Khởi tạo các giá trị của mảng
				
				this.nameProperties[0] = "ActId";
				this.nameProperties[1] = "Time";
				this.nameProperties[2] = "Note";
				this.nameProperties[3] = "Id";
				
				for(int i = 0; i < SqlData.SIZE_ARRAY_TIME; i ++) {
					this.valueProperties[i] = "";
				}
			} break;
			
			default: System.out.println("Giá trị tham số truy�?n vào không ứng với bảng nào trong cơ sở dữ liệu");
				break;
		}
	}
	//Khai báo phương thức lấy ra flag của lớp này
	
	public int getType() {
		return this.type;
	}
	//Khai báo phương thức thiết lập giá trị các thuộc tính của mảng valueProperties và trả và true nếu thành công và flase nếu như tham số truy�?n vào không th�?a mãn yêu cầu của mảng
	
	public boolean setValueProperties(String[] data) {
		boolean result = false;
		
		switch(this.type) {
			case SqlProcessor.TABLE_USER: {
				if((data.length == SqlData.SIZE_ARRAY_USER - 1) || (data.length == SqlData.SIZE_ARRAY_USER)) {
					for(int i = 0; i < data.length; i ++) {
						this.valueProperties[i] = data[i];
					}
					
					result = true;
				}
				else {
					System.out.println("Giá trị tham số truyền vào không mãn với đối tượng!");
					
					result = false;
				}
			}	break;
			
			case SqlProcessor.TABLE_TYPE: {
				if((data.length == SqlData.SIZE_ARRAY_TYPE - 1) || (data.length == SqlData.SIZE_ARRAY_TYPE)) {
					for(int i = 0; i < data.length; i ++) {
						this.valueProperties[i] = data[i];
					}
					
					result = true;
				}
				else {
					System.out.println("Giá trị tham số truyền vào không mãn với đối tượng!");
					
					result = false;
				}
			}	break;
			
			case SqlProcessor.TABLE_PLACE: {
				if((data.length == SqlData.SIZE_ARRAY_PLACE) || (data.length == SqlData.SIZE_ARRAY_PLACE - 1)) {
					for(int i = 0; i < data.length; i ++) {
						this.valueProperties[i] = data[i];
					}
					
					result = true;
				}
				else {
					System.out.println("Giá trị tham số truyền vào không mãn với đối tượng!");
					
					result = false;
				}
			} 	break;
			
			case SqlProcessor.TABLE_ACTIVITY: {
				if((data.length == SqlData.SIZE_ARRAY_ACTIVITY - 1) || (data.length == SqlData.SIZE_ARRAY_ACTIVITY)) {
					for(int i = 0; i < data.length; i ++) {
						this.valueProperties[i] = data[i];
					}
					
					result = true;
				}
				else {
					System.out.println("Giá trị tham số truyền vào không mãn với đối tượng!");
					
					result = false;
				}
			}	break;
			
			case SqlProcessor.TABLE_USER_ACTIVITY: {
				if((data.length == SqlData.SIZE_ARRAY_USER_ACTIVITY - 1) || (data.length == SqlData.SIZE_ARRAY_USER_ACTIVITY)) {
					for(int i = 0; i < data.length; i ++) {
						this.valueProperties[i] = data[i];
					}
					
					result = true;
				}
				else {
					System.out.println("Giá trị tham số truyền vào không mãn với đối tượng!");
					
					result = false;
				}
			}	break;
			
			case SqlProcessor.TABLE_TIME: {
				if((data.length != SqlData.SIZE_ARRAY_TIME - 1) || (data.length != SqlData.SIZE_ARRAY_TIME)) {
					for(int i = 0; i < data.length; i ++) {
						this.valueProperties[i] = data[i];
					}
					
					result = true;
				}
				else {
					System.out.println("Giá trị tham số truyền vào không mãn với đối tượng!");
					
					result = false;
				}
			}	break;
			
			default: System.out.println("Lỗi tham số!");
		}
		
		return result;
	}
	//Khai báo phương thức chuyển đổi từ từ mảng dữ liệu này sang loại dữ liệu khác phục vụ cho việc nhập vào bảng khác
	
	public boolean convertDataType(int typeTable) {
		if(typeTable == this.type) {
			return false;
		}
		else {
			for(int i = 0; i < this.valueProperties.length; i ++) {
				this.nameProperties[i] = null;
				this.valueProperties[i] = null;
			}
			this.initializeData(typeTable);
			
			return true;
		}
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
		int result = 0;
		
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
	//Phương thức tạo ra câu lệnh sql chèn vào cơ dữ liệu tương ứng MySQL
	
	public String convertIntoInsertSql() {
		String name = this.convertNameTable(this.type) + "(";
		String value = "values(\"";
		
		for(int i = 0; i < this.nameProperties.length - 1; i ++) {
			if(i != this.nameProperties.length - 2) {
				name = name + this.nameProperties[i] + ", ";
				
				if((this.type == SqlProcessor.TABLE_ACTIVITY) && (i == (this.nameProperties.length - 3))) {
					value = value + StringHandle.changeIntoSpace(this.valueProperties[i]) + "\", ";
				} 
				else {
					value = value + this.valueProperties[i] + "\", \"";
				}
			}
			else {
				name = name + this.nameProperties[i] + ")";
				
				if(this.type == SqlProcessor.TABLE_ACTIVITY) {
					value = value + StringHandle.changeIntoSpace(this.valueProperties[i]) + ")";
				}
				else {
					value = value + StringHandle.changeIntoSpace(this.valueProperties[i]) + "\")";
				}
			}
		}
		System.out.println("INSERT INTO " + name + " " + value);
		return "INSERT INTO " + name + " " + value;
	}
	//Phương thức sinh ra câu truy vấn cập nhật thông tin của các bộ dữ liệu 
	
	public String convertIntoUpdateSql(int typeTable, String[] value, String conditionId) {
		StringBuffer temple = new StringBuffer("UPDATE " + this.convertNameTable(typeTable) + " SET ");
		
		for(int i = 0; i < value.length; i ++) {
			if((value[i] != null) && (! value[i].equals(""))) {				
				if((typeTable == SqlProcessor.TABLE_ACTIVITY) && (i == SqlData.ACTIVITY_RINGSTATUS)) {
					temple = temple.append(this.convertToNameProperty(typeTable, i) + " = " + value[i] + ", ");
				}
				else {
					temple = temple.append(this.convertToNameProperty(typeTable, i) + " = \"" + value[i] + "\", ");
				}
			}
		}
		temple = new StringBuffer(temple.substring(0, temple.length() - 2));
		
		temple.append(" WHERE Id = \"" + conditionId + "\"");
		System.out.println(temple.toString());
		return temple.toString();
	}
	//Phương thức sinh ra câu truy vấn xóa một bộ trong bảng xác định
	
	public String convertIntoDeleteSql(int typeTable, String conditionId) {
		return "DELETE FROM " + this.convertNameTable(typeTable) + " WHERE Id = \"" + conditionId + "\""; 
	}
	//Phương thức lấy ra mảng tên các thuộc tính
	
	public String[] getNameProperties() {
		return this.nameProperties;
	}
	//Phương thức lấy ra mảng giá trị của thuộc tính
	
	public String[] getValueProperties() {
		return this.valueProperties;
	}
	//Phương thức sao chép mảng valueProperties sang một mảng khác và b�? đi phần tử cuối cùng tương ứng với giá trị của Id để phục vụ cho quá trình nhập dữ liệu vào trong cơ sở dữ liệu
	
	public String[] toDataImport() {
		String[] result = new String[this.valueProperties.length - 1];
		
		for(int i = 0; i < result.length; i ++) {
			result[i] = new String(this.valueProperties[i]);
		}
		
		return result;
	}
	//Phương thức chuyển đổi từ loại bảng và loại thuộc tính sang tên bảng tương ứng
	
	public String convertToNameProperty(int type, int index) {
		String result = "";
		
		switch(type) {
			case SqlProcessor.TABLE_USER: {
				switch(index) {
					case SqlData.USER_NAME: result = "Name";
						break;
					case SqlData.USER_ID: result = "Id";
						break;
					case SqlData.USER_PASSWORD: result = "Password";
						break;
					case SqlData.USER_EMAIL: result = "Email";
						break;
					case SqlData.USER_ADDRESS: result = "Address";
						break;
				}
			}
				break;
			case SqlProcessor.TABLE_ACTIVITY: {
				switch(index) {
					case SqlData.ACTIVITY_ID: result = "Id";
						break;
					case SqlData.ACTIVITY_PLACEID: result = "PlaceId";
						break;
					case SqlData.ACTIVITY_TYPEID:  result = "TypeId";
						break;
					case SqlData.ACTIVITY_RINGSTATUS: result = "RingStatus";
						break;
					case SqlData.ACTIVITY_NOTE: result = "Note";
						break;
				}
			}
				break;
			case SqlProcessor.TABLE_PLACE: {
				switch(index) {
					case SqlData.PLACE_ID: result = "Id";
						break;
					case SqlData.PLACE_NAME: result = "Name";
						break;
					case SqlData.PLACE_ADDRESS: result = "Address";
						break;
					case SqlData.PLACE_DESCRIPTION: result = "Description";
						break;
				}
			}
				break;
			case SqlProcessor.TABLE_TIME: {
				switch(index) {
					case SqlData.TIME_ID: result = "Id";
						break;
					case SqlData.TIME_ACTID: result = "ActId";
						break;
					case SqlData.TIME_TIME: result = "Time";
						break;
					case SqlData.TIME_NOTE: result = "Note";
						break;
				}
			}
				break;
			case SqlProcessor.TABLE_TYPE: {
				switch(index) {
					case SqlData.TYPE_ID: result = "Id";
						break;
					case SqlData.TYPE_NAME: result = "Name";
						break;
					case SqlData.TYPE_DESCRIPTION: result = "Description";
						break;
				}
			}
				break;
			case SqlProcessor.TABLE_USER_ACTIVITY: {
				switch(index) {
					case SqlData.USER_ACTIVITY_ACTID: result = "ActId";
						break;
					case SqlData.USER_ACTIVITY_USERID: result = "UserId";
						break;
					case SqlData.USER_ACTIVITY_TIMEREGISTER: result = "TimeRegister";
						break;
				}
			}
				break;
		}
		
		return result;
	}
}
