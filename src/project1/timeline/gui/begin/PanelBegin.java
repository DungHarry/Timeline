package project1.timeline.gui.begin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import project1.timeline.engine.SqlData;
import project1.timeline.engine.SqlProcessor;
import project1.timeline.engine.StringHandle;
import project1.timeline.engine.XmlProcessor;
import project1.timeline.main.TimeLine;
/*
 * Khai báo lớp tạo giao diện cho phần mở đầu: Phần này sẽ được hiển thị nếu khi tự động đăng nhập gặp sự cố
 * hoặc khi ngư�?i dùng muốn thoát ra phần này
 */
public class PanelBegin extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_BEGIN = 1220;
	public static final int HEIGHT_PANEL_BEGIN = 700;
	public static final Color COLOR_BACKGROUND = Color.white;
	public static final Color COLOR_FOREGROUND = Color.white;
	public static final Color COLOR_TITLE = Color.white;
	public static final Color COLOR_TITLE_BACKGROUND = Color.blue;
	public static final Color COLOR_BORDER = Color.blue;
	public static final Color COLOR_TITLE_SUB_BACKGROUND = Color.gray;
	public static final Font FONT_TITLE = new Font("Times New Roman", Font.BOLD, 60);
	public static final Font FONT_SUB_TITLE = new Font("Times New Roman", Font.BOLD, 20);
	public static final Font FONT_NORMAL = new Font("Times New Roman", Font.PLAIN, 15);
	//Khai báo các biến được sử dụng trong lớp này
	
	JPanel[] panel = new JPanel[3];
	JLabel label = new JLabel("Timeline");
	JButton button = new JButton("Apply");
	SqlProcessor sqlProcessor;
	UserPanel userPanel;
	DatabasePanel databasePanel;
	TimeLine timeline;
	//Phương thức khởi tạo
	
	public PanelBegin(SqlProcessor sqlProcessor, TimeLine timeline) {
		this.timeline = timeline;
		this.sqlProcessor = sqlProcessor;
		this.userPanel = new UserPanel(this.sqlProcessor);
		this.databasePanel = new DatabasePanel(this.sqlProcessor);
		
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(PanelBegin.COLOR_BACKGROUND);
		this.setPreferredSize(new Dimension(PanelBegin.WIDTH_PANEL_BEGIN, PanelBegin.HEIGHT_PANEL_BEGIN));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.panel[1], BorderLayout.CENTER);
		this.add(this.panel[2], BorderLayout.SOUTH);
		
		this.handleEvent();
	}
	//Phương thức khởi tạo dữ liệu cho các thành phần của lớp này
	
	private void initializeComponent() {
		this.panel[0] = new JPanel();
		this.panel[0].setBackground(PanelBegin.COLOR_TITLE_BACKGROUND);
		this.panel[0].setBorder(BorderFactory.createLineBorder(PanelBegin.COLOR_BORDER));
		this.label.setFont(PanelBegin.FONT_TITLE);
		this.label.setForeground(PanelBegin.COLOR_FOREGROUND);
		this.panel[0].add(this.label);
		
		this.panel[1] = new JPanel();
		this.panel[1].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.panel[1].setBorder(BorderFactory.createLineBorder(PanelBegin.COLOR_BORDER));
		this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.X_AXIS));
		this.panel[1].add(this.userPanel);
		this.panel[1].add(this.databasePanel);
		
		this.panel[2] = new JPanel();
		this.panel[2].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.panel[2].setBorder(BorderFactory.createLineBorder(PanelBegin.COLOR_BORDER));
		this.button.setFont(PanelBegin.FONT_SUB_TITLE);
		this.panel[2].add(this.button);
	}
	//Khai báo phương thức xử lý các sự kiện cho lớp này
	
	private void handleEvent() {
		this.button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(sqlProcessor.createConnection(XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_ADDESS], XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_DATABASE], XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_PASSWORD])) {
					if(userPanel.checkUser()) {
						timeline.getPanelBody().getPanelSelection().getPanelOption().getPanelInsertion().updatePanelChoice();
						timeline.getPanelBody().getPanelSelection().getPanelFilter().updatePanelChooser();
						timeline.getPanelBody().getPanelSelection().getPlaceAndType().updateChoicer();
						
						timeline.showPanel(TimeLine.TYPE_BODY);
						timeline.getPanelBody().getPanelSelection().getPanelOption().updateOption();
						timeline.getPanelBody().getPanelActivityRing().updateDataArray();
					}
					else {
						JOptionPane.showMessageDialog(null, "Please check account again!", "Note", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please check connection to database again!", "note", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
/*
 * Khai báo lớp tạo ra phần tương ứng với user của chương trình Timeline
 * Phần này sẽ cho phép ngư�?i dùng tạo ra một tài khoản mới và thiết lập một tài khoản có sẵn
 */

class UserPanel extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_USER = 610;
	public static final int HEIGHT_PANEL_USER = 500;
	//Khai báo các biến được sử dụng trong lớp này
	
	JLabel[] label = new JLabel[7]; 
	JTextField textAccess = new JTextField("User Name ");
	JTextField[] textCreate = new JTextField[2];
	JPasswordField[] passwordField = new JPasswordField[2];
	JTextArea textAddress = new JTextArea(3, 30);
	JScrollPane[] scroll = new JScrollPane[3];
	JPanel[] panel = new JPanel[11];
	JButton[] button = new JButton[2];
	SqlProcessor sqlProcessor;
	String[] templeString;
	//Khai báo phương thức khởi tạo
	
	public UserPanel(SqlProcessor sqlProcessor) {
		this.sqlProcessor = sqlProcessor;
		
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(PanelBegin.COLOR_BACKGROUND);
		this.setBorder(LineBorder.createBlackLineBorder());
		this.setPreferredSize(new Dimension(UserPanel.WIDTH_PANEL_USER, UserPanel.HEIGHT_PANEL_USER));
		this.add(this.panel[10], BorderLayout.NORTH);
		this.add(this.scroll[2], BorderLayout.CENTER);
		this.add(this.scroll[1], BorderLayout.SOUTH);
		
		this.handleEvent();
	}
	//Phương thức khởi tạo các thành phần của lớp này
	
	private void initializeComponent() {
		this.panel[0] = new JPanel();
		this.panel[0].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.panel[0].setLayout(new BoxLayout(this.panel[0], BoxLayout.Y_AXIS));
		this.panel[0].setBorder(LineBorder.createGrayLineBorder());
		
		this.panel[1] = new JPanel();
		this.panel[1].setBackground(PanelBegin.COLOR_BACKGROUND);
		//this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.X_AXIS));
		this.label[0] = new JLabel("User Name ");
		this.label[0].setFont(PanelBegin.FONT_NORMAL);
		this.textAccess = new JTextField(30);
		this.textAccess.setFont(PanelBegin.FONT_NORMAL);
		this.panel[1].add(this.label[0]);
		this.panel[1].add(this.textAccess);
		
		this.panel[2] = new JPanel();
		this.panel[2].setBackground(PanelBegin.COLOR_BACKGROUND);
		//this.panel[2].setLayout(new BoxLayout(this.panel[2], BoxLayout.X_AXIS));
		this.label[1] = new JLabel("Password   ");
		this.label[1].setFont(PanelBegin.FONT_NORMAL);
		this.passwordField[0] = new JPasswordField(30);
		this.passwordField[0].setFont(PanelBegin.FONT_NORMAL);
		this.panel[2].add(this.label[1]);
		this.panel[2].add(this.passwordField[0]);
		
		this.panel[3] = new JPanel();
		this.panel[3].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.panel[3].setBorder(LineBorder.createGrayLineBorder());
		this.button[0] = new JButton("Set User");
		this.button[0].setFont(PanelBegin.FONT_NORMAL);
		this.panel[3].add(this.button[0]);
		
		this.panel[0].add(this.panel[1]);
		this.panel[0].add(this.panel[2]);
		this.panel[0].add(this.panel[3]);
		this.scroll[2] = new JScrollPane(this.panel[0]);
		
		this.panel[4] = new JPanel();
		this.panel[4].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.panel[4].setLayout(new BoxLayout(this.panel[4], BoxLayout.Y_AXIS));
		this.panel[4].setBorder(LineBorder.createGrayLineBorder());
		this.panel[4].setPreferredSize(new Dimension(UserPanel.WIDTH_PANEL_USER, 350));
	
		this.panel[5] = new JPanel();
		this.panel[5].setBackground(PanelBegin.COLOR_BACKGROUND);
		//this.panel[5].setLayout(new BoxLayout(this.panel[5], BoxLayout.X_AXIS));
		this.label[2] = new JLabel("User Name ");
		this.label[2].setFont(PanelBegin.FONT_NORMAL);
		this.textCreate[0] = new JTextField(30);
		this.textCreate[0].setFont(PanelBegin.FONT_NORMAL);
		this.panel[5].add(this.label[2]);
		this.panel[5].add(this.textCreate[0]);
		
		this.panel[6] = new JPanel();
		this.panel[6].setBackground(PanelBegin.COLOR_BACKGROUND);
		//this.panel[6].setLayout(new BoxLayout(this.panel[6], BoxLayout.X_AXIS));
		this.label[3] = new JLabel("Password   ");
		this.label[3].setFont(PanelBegin.FONT_NORMAL);
		this.passwordField[1] = new JPasswordField(30);
		this.passwordField[1].setFont(PanelBegin.FONT_NORMAL);
		this.panel[6].add(this.label[3]);
		this.panel[6].add(this.passwordField[1]);
		
		this.panel[7] = new JPanel();
		this.panel[7].setBackground(PanelBegin.COLOR_BACKGROUND);
		//this.panel[7].setLayout(new BoxLayout(this.panel[7], BoxLayout.X_AXIS));
		this.label[4] = new JLabel("Email       ");
		this.label[4].setFont(PanelBegin.FONT_NORMAL);
		this.textCreate[1] = new JTextField(30);
		this.textCreate[1].setFont(PanelBegin.FONT_NORMAL);
		this.panel[7].add(this.label[4]);
		this.panel[7].add(this.textCreate[1]);
		
		this.panel[8] = new JPanel();
		this.panel[8].setBackground(PanelBegin.COLOR_BACKGROUND);
		//this.panel[8].setLayout(new BoxLayout(this.panel[8], BoxLayout.X_AXIS));
		this.label[5] = new JLabel("Address    ");
		this.label[5].setFont(PanelBegin.FONT_NORMAL);
		this.textAddress.setFont(PanelBegin.FONT_NORMAL);
		this.scroll[0] = new JScrollPane(this.textAddress);
		this.panel[8].add(this.label[5]);
		this.panel[8].add(this.scroll[0]);
		
		this.panel[9] = new JPanel();
		this.panel[9].setBorder(LineBorder.createGrayLineBorder());
		this.panel[9].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.button[1] = new JButton("Create Account");
		this.button[1].setFont(PanelBegin.FONT_NORMAL);
		this.panel[9].add(this.button[1]);
		
		this.panel[4].add(this.panel[5]);
		this.panel[4].add(this.panel[6]);
		this.panel[4].add(this.panel[7]);
		this.panel[4].add(this.panel[8]);
		this.panel[4].add(this.panel[9]);
		this.scroll[1] = new JScrollPane(this.panel[4]);
		
		this.panel[10] = new JPanel();
		this.panel[10].setBackground(PanelBegin.COLOR_TITLE_SUB_BACKGROUND);
		this.panel[10].setBorder(LineBorder.createGrayLineBorder());
		this.label[6] = new JLabel("Account");
		this.label[6].setFont(PanelBegin.FONT_SUB_TITLE);
		this.panel[10].add(this.label[6]);
	}
	/*
	 * Khai báo phương thức xử lý sự kiện trong lớp này
	 */
	
	private void handleEvent() {
		this.button[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Do you want set this account?", "Confirm Dialog", JOptionPane.OK_CANCEL_OPTION);
				
				if(result == JOptionPane.OK_OPTION) {
					if(sqlProcessor.checkAccount(textAccess.getText(), passwordField[0].getText())) {
						JOptionPane.showMessageDialog(null, "This account has been set!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "Account invalid. Please try again!", "Note", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		this.button[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(textCreate[0].getText().equals("") || textCreate[0].getText().equals(null) || passwordField[1].getText().equals("") || passwordField[1].getText().equals(null) || textCreate[1].getText().equals("") || textCreate[1].getText().equals(null) || textAddress.getText().equals("") || textAddress.getText().equals(null)) {
					JOptionPane.showMessageDialog(null, "Please typing fully in field!", "Note", JOptionPane.WARNING_MESSAGE);
				}
				else if(sqlProcessor.checkAccount(textCreate[0].getText(), passwordField[1].getText())) {
					JOptionPane.showMessageDialog(null, "This account invalid. Please try again!", "Note", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String[] temple = new String[SqlData.SIZE_ARRAY_USER];
					for(int i = 0; i < temple.length; i ++) {
						temple[i] = new String();
					}
					
					temple[SqlData.USER_NAME] = textCreate[0].getText();
					temple[SqlData.USER_PASSWORD] = passwordField[1].getText();
					temple[SqlData.USER_EMAIL] = textCreate[1].getText();
					temple[SqlData.USER_ADDRESS] = StringHandle.changeIntoSpace(textAddress.getText());
					
					if(sqlProcessor.importTable(SqlProcessor.TABLE_USER, temple)) {
						JOptionPane.showMessageDialog(null, "Create account success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "An error has occurred!", "Note", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	//Khai báo phương thức thiết lập kiểm tra thông tin ngư�?i dùng có hợp lệ không
	
	public boolean checkUser() {
		return this.sqlProcessor.loginAuto();
	}
}
/*
 * Khai báo lớp tạo ra giao diện cho phần chỉnh sửa thông tin của cơ sở dữ liệu
 */

class DatabasePanel extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_DATABASE = 610;
	public static final int HEIGHT_PANEL_DATABASE = 500;
	//Khai báo các biến được sử dụng trong lớp này
	
	JPanel[] panel = new JPanel[7];
	JLabel[] label = new JLabel[5];
	JTextField[] textField = new JTextField[3];
	JPasswordField password = new JPasswordField(30);
	JButton[] button = new JButton[2];
	SqlProcessor sqlProcessor;
	JScrollPane scroll;
	//Khai báo phương thức khởi tạo
	
	public DatabasePanel(SqlProcessor sqlProcessor) {
		this.sqlProcessor = sqlProcessor;
		
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(PanelBegin.COLOR_BACKGROUND);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(DatabasePanel.WIDTH_PANEL_DATABASE, DatabasePanel.HEIGHT_PANEL_DATABASE));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.scroll, BorderLayout.CENTER);
		this.add(this.panel[6], BorderLayout.SOUTH);
		
		this.handleEvent();
	}
	//Phương thức tạo giao diện cho phần database
	
	private void initializeComponent() {
		this.panel[0] = new JPanel();
		this.panel[0].setBackground(PanelBegin.COLOR_TITLE_SUB_BACKGROUND);
		this.panel[0].setBorder(LineBorder.createGrayLineBorder());
		this.label[0] = new JLabel("Database");
		this.label[0].setFont(PanelBegin.FONT_SUB_TITLE);
		this.panel[0].add(this.label[0]);
		
		this.panel[1] = new JPanel();
		this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.Y_AXIS));
		this.panel[1].setBackground(PanelBegin.COLOR_BACKGROUND);
		
		this.panel[2] = new JPanel();
		this.panel[2].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.label[1] = new JLabel("Database   ");
		this.label[1].setFont(PanelBegin.FONT_NORMAL);
		this.textField[0] = new JTextField(30);
		this.textField[0].setFont(PanelBegin.FONT_NORMAL);
		this.panel[2].add(this.label[1]);
		this.panel[2].add(this.textField[0]);
		
		this.panel[3] = new JPanel();
		this.panel[3].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.label[2] = new JLabel("Address   ");
		this.label[2].setFont(PanelBegin.FONT_NORMAL);
		this.textField[1] = new JTextField(30);
		this.textField[1].setFont(PanelBegin.FONT_NORMAL);
		this.panel[3].add(this.label[2]);
		this.panel[3].add(this.textField[1]);
		
		this.panel[4] = new JPanel();
		this.panel[4].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.label[3] = new JLabel("User      ");
		this.label[3].setFont(PanelBegin.FONT_NORMAL);
		this.textField[2] = new JTextField(30);
		this.textField[2].setFont(PanelBegin.FONT_NORMAL);
		this.panel[4].add(this.label[3]);
		this.panel[4].add(this.textField[2]);
		
		this.panel[5] = new JPanel();
		this.panel[5].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.label[4] = new JLabel("Password    ");
		this.label[4].setFont(PanelBegin.FONT_NORMAL);
		this.password.setFont(PanelBegin.FONT_NORMAL);
		this.panel[5].add(this.label[4]);
		this.panel[5].add(this.password);
		
		this.panel[1].add(this.panel[2]);
		this.panel[1].add(this.panel[3]);
		this.panel[1].add(this.panel[4]);
		this.panel[1].add(this.panel[5]);
		this.scroll = new JScrollPane(this.panel[1]);
		
		this.panel[6] = new JPanel();
		this.panel[6].setBorder(BorderFactory.createLineBorder(Color.gray));
		this.panel[6].setBackground(PanelBegin.COLOR_BACKGROUND);
		this.button[0] = new JButton("Access");
		this.button[0].setFont(PanelBegin.FONT_NORMAL);
		this.button[1] = new JButton("Create Database");
		this.button[1].setFont(PanelBegin.FONT_NORMAL);
		this.panel[6].add(this.button[0]);
		this.panel[6].add(this.button[1]);
	}
	//Phương thức xử lý sự kiện cho lớp này
	
	private void handleEvent() {
		this.button[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(textField[0].getText().equals("") || textField[0].getText().equals(null) || textField[1].getText().equals("") || textField[1].getText().equals(null) || textField[2].getText().equals("") || textField[2].getText().equals(null) || password.getText().equals("") || password.getText().equals(null)) {
					JOptionPane.showMessageDialog(null, "Please typing fully in text field!", "Note", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if(setConnection()) {
						JOptionPane.showMessageDialog(null, "Access this database success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
					}
					else if(sqlProcessor.createConnection(XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_URL], XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_PASSWORD])) {
						JOptionPane.showMessageDialog(null, "Use old connection!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "You don't have any connection or you don't setup MySQL Server. Please try again!", "Note", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		this.button[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(textField[0].getText().equals("") || textField[0].getText().equals(null) || textField[1].getText().equals("") || textField[1].getText().equals(null) || textField[2].getText().equals("") || textField[2].getText().equals(null) || password.getText().equals("") || password.getText().equals(null)) {
					JOptionPane.showMessageDialog(null, "Please typing fully in text field!", "Note", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if(sqlProcessor.createNewDatabase(textField[1].getText(), textField[0].getText(), textField[2].getText(), password.getText())) {
						JOptionPane.showMessageDialog(null, "Create new database success. Please create new account!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
					}
					else if(sqlProcessor.createConnection(XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_URL], XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_PASSWORD])) {
						JOptionPane.showMessageDialog(null, "Use old connection!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "You don't have any connection or you don't setup MySQL Server. Please try again!", "Note", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
	}
	//Phương thức thiết lập kết nối tới cơ sở dữ liệu 
	
	public boolean setConnection() {
		return this.sqlProcessor.createConnection(this.textField[1].getText(), this.textField[0].getText(), this.textField[2].getText(), this.password.getText());
	}
}

