package project1.timeline.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.XMLFormatter;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/*
 * Lớp này được sử dụng để thao tác với tệp xml và xử lý các dữ liệu có trong tệp xml
 */

public class XmlProcessor {
	//Khai báo các hằng số được sử dụng trong lớp này
	public static final String PATH_REMEMBER_FILE = "resources/data/xml/Remember.xml";
	public static final int TYPE_USER = 0;
	public static final int TYPE_MYSQL = 1;
	
	public static final int USER_NAME = 0;
	public static final int USER_PASSWORD = 1;
	
	public static final int MYSQL_URL = 0;
	public static final int MYSQL_ADDESS = 1;
	public static final int MYSQL_DATABASE = 2;
	public static final int MYSQL_NAME = 3;
	public static final int MYSQL_PASSWORD = 4;
	
	public static final int SIZE_USER = 2;
	public static final int SIZE_MYSQL = 5;
	//Khai báo biến được sử dụng trong chương trình
	
	private static Document document;
	private static String[] user;
	private static String[] mySQL;
	
	//Hàm khởi tạo
	
	
	public XmlProcessor() {
		this.initializeValue();
		this.createFile();
	}
	//Khai báo phương thức khởi tạo giá trị ban đầu cho tất các biến được sử dụng cho lớp này
	
	public static void initializeValue() {
		document = new Document();
		user = new String[XmlProcessor.SIZE_USER];
		mySQL = new String[XmlProcessor.SIZE_MYSQL];
		
		for(int i = 0; i < mySQL.length; i ++) {
			if((i >= 0) && (i <= 1)) {
				user[i] = "";
				mySQL[i] = "";
			}
			else {
				mySQL[i] = "";
			}
		}
	}
	
	//Phương thức được sử dụng để tạo ra file nếu như file chưa tồn tại và khởi tạo giá trị cho biến document
	
	public static void createFile() {
		File file = new File(XmlProcessor.PATH_REMEMBER_FILE);
		
		if(!file.exists()) {
			document.setRootElement(new Element("remembers"));
			Element[] element = new Element[9];
			element[0]= new Element("user");
			element[1] = new Element("MySQL");
			
			element[2]= new Element("name");
			element[3] = new Element("password");
			element[4] = new Element("url");
			element[5] = new Element("address");
			element[6] = new Element("database");
			element[7] = new Element("name");
			element[8] = new Element("password");
			
			element[0].addContent(element[2]);
			element[0].addContent(element[3]);
			
			element[1].addContent(element[4]);
			element[1].addContent(element[5]);
			element[1].addContent(element[6]);
			element[1].addContent(element[7]);
			element[1].addContent(element[8]);
			
			document.getRootElement().addContent(element[0]);
			document.getRootElement().addContent(element[1]);
			
			updateDocument(document, XmlProcessor.PATH_REMEMBER_FILE);
		}
	}
	//Khai báo phương thức phục vụ cho các phương thức khác trong lớp này
	
	private static void updateDocument(Document doc, String path) {
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		
		try {
			xmlOutput.output(doc, new FileOutputStream(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Khai báo phương thức lấy ra Document từ một tệp xml có săn trong hệ thống
	
	private static Document getDocument(String path) {
		SAXBuilder buider = new SAXBuilder();
		
		try {
			return buider.build(new File(path));
		} catch (JDOMException e) {
			e.printStackTrace();
			
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	//Khai báo phương thức cập nhật thông tin v�? ngư�?i sử dụng đối với chương trình
	
	public static void updateUser(String[] user) {
		Document document = getDocument(XmlProcessor.PATH_REMEMBER_FILE);
		Element userElement = document.getRootElement().getChild("user");
		
		for(int i = 0; i < user.length; i ++) {
			userElement.getChild(convertToName(XmlProcessor.TYPE_USER, i)).setContent(new Text(user[i]));
		}
		
		updateDocument(document, XmlProcessor.PATH_REMEMBER_FILE);
	}
	
	public static void updateUser(String userName, String userPassword) {
		Document document = getDocument(XmlProcessor.PATH_REMEMBER_FILE);
		Element userElement = document.getRootElement().getChild("user");
		
		userElement.getChild("name").setContent(new Text(userName));
		userElement.getChild("password").setContent(new Text(userPassword));
		
		updateDocument(document, XmlProcessor.PATH_REMEMBER_FILE);
	}
	
	public static void updateUser(int typeInformation, String value) {
		Document document = getDocument(XmlProcessor.PATH_REMEMBER_FILE);
		Element userElement = document.getRootElement().getChild("user");
		
		if(typeInformation == XmlProcessor.USER_NAME) {
			userElement.getChild("name").setContent(new Text(value));
		}
		else if(typeInformation == XmlProcessor.USER_PASSWORD) {
			userElement.getChild("password").setContent(new Text(value));
		}
		
		updateDocument(document, XmlProcessor.PATH_REMEMBER_FILE);
	}
	//Khai báo phương thức cập nhật thông tin v�? cơ sở dữ liệu Mysql
	
	public static void updateMySQL(String url, String address, String database, String name, String password) {
		Document document = getDocument(XmlProcessor.PATH_REMEMBER_FILE);
		Element sqlElement = document.getRootElement().getChild("MySQL");
		
		sqlElement.getChild("url").setContent(new Text(url));
		sqlElement.getChild("address").setContent(new Text(address));
		sqlElement.getChild("database").setContent(new Text(database));
		sqlElement.getChild("name").setContent(new Text(name));
		sqlElement.getChild("password").setContent(new Text(password));
		
		updateDocument(document, XmlProcessor.PATH_REMEMBER_FILE);
	}
	
	public static void updateMySQL(String[] mySQL) {
		Document document = getDocument(XmlProcessor.PATH_REMEMBER_FILE);
		Element sqlElement = document.getRootElement().getChild("MySQL");
		
		for(int i = 0; i < mySQL.length; i ++) {
			sqlElement.getChild(convertToName(XmlProcessor.TYPE_MYSQL, i)).setContent(new Text(mySQL[i]));
		}
		
		updateDocument(document, XmlProcessor.PATH_REMEMBER_FILE);
	}
	//Phương thức chuyển đổi từ số của loại của dữ liệu ra tên của node trong tệp xml
	
	private static String convertToName(int type, int format) {
		String result = "";
		
		switch(type) {
			case XmlProcessor.TYPE_USER: {
				switch(format) {
					case XmlProcessor.USER_NAME: result = "name";
						break;
					case XmlProcessor.USER_PASSWORD: result = "password";
						break;
				}
			}
				break;
			case XmlProcessor.TYPE_MYSQL: {
				switch(format) {
					case XmlProcessor.MYSQL_URL: result = "url";
						break;
					case XmlProcessor.MYSQL_ADDESS: result = "address";
						break;
					case XmlProcessor.MYSQL_DATABASE: result = "database";
						break;
					case XmlProcessor.MYSQL_NAME: result = "name";
					 	break;
					case XmlProcessor.MYSQL_PASSWORD: result = "password";
						break;
				}
			}
				break;
		}
		
		return result;
	}
	//Phương thức trả v�? mảng các thông tin của các node có trong tệp xml
	
	public static String[] getInformation(int type) {
		Document document = getDocument(XmlProcessor.PATH_REMEMBER_FILE);
		String[] result = null;
		initializeValue();
		switch(type) {
			case XmlProcessor.TYPE_USER: {
				Element userElement = document.getRootElement().getChild("user");
				for(int i = 0; i < user.length; i ++) {
					 user[i] = userElement.getChildText(convertToName(type, i));
				 }
				
				result = user;
			}
				break;
			case XmlProcessor.TYPE_MYSQL: {
				Element sqlElement = document.getRootElement().getChild("MySQL");
				for(int i = 0; i < mySQL.length; i ++) {
					mySQL[i] = sqlElement.getChildText(convertToName(type, i));
				}
				
				result = mySQL;
			}
				break;
		}
		
		return result;
	}
}
