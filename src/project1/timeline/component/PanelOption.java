package project1.timeline.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sound.midi.Sequencer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import project1.sound.WavPlayer;
import project1.timeline.engine.SqlData;
import project1.timeline.engine.SqlProcessor;
import project1.timeline.engine.StringHandle;
import project1.timeline.engine.XmlProcessor;
import project1.timeline.gui.body.PanelBody;
import project1.timeline.main.TimeLine;
/*
 * Khai báo PanelOption, đây là panel chính của phần Option
 */

public class PanelOption extends JPanel {
	//Khai báo kích thước mặc định ban đầu dành cho thành phần này
	
	public static final int WIDTH_PANEL_OPTION = 500;
	public static final int HEIGHT_PANEL_OPTION = 700;
	public static Color COLOR_TITLE = Color.blue;
	//Khai báo các biến được sử dụng trong class này
	
	private JPanel container = new JPanel();
	private SqlProcessor sqlProcessor;
	private UserOption userOption;
	private RingOption ringOption = new RingOption();
	private DatabaseOption dbOption;
	private DataInsertionOption dbInsertion; //Có thể phải truy�?n thêm tham số
	private PanelBody panelBody;
	//Phương thức khởi tạo
	
	public PanelOption(SqlProcessor sqlProcessor, PanelBody panelContainer) {
		this.panelBody = panelContainer;
		this.sqlProcessor = sqlProcessor;
		this.userOption = new UserOption(sqlProcessor);
		this.dbOption = new DatabaseOption(sqlProcessor, this);
		this.dbInsertion = new DataInsertionOption(this.sqlProcessor, panelContainer);
		
		this.initializeComponent();
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.add(this.ringOption, BorderLayout.NORTH);
		this.add(this.container, BorderLayout.CENTER);
		this.add(this.dbInsertion, BorderLayout.SOUTH);
	}
	//Phương thức khởi tạo giao diện cho lớp này
	
	private void initializeComponent() {
		this.container.setLayout(new BorderLayout());
		this.container.setBackground(Color.white);
		this.container.add(this.userOption, BorderLayout.NORTH);
		this.container.add(this.dbOption.getDbSwitch(), BorderLayout.CENTER);
		this.container.add(this.dbOption, BorderLayout.CENTER);
	}
	//Phương thức lấy ra container
	
	public JPanel getContainer() {
		return this.container;
	}
	//Phương thức lấy ra panelInsertion
	
	public PanelInsertion getPanelInsertion() {
		return this.dbInsertion.getPanelInsertion();
	}
	//Phương thức cập nhạt lại thông tin cho panel này
	
	public void updateOption() {
		this.userOption.updateDataUser();
		this.dbOption.updateInformation();
	}
	//Phương thức lấy ra panelbody
	
	public PanelBody getPanelBody() {
		return this.panelBody;
	}
	//Phương thức lấy ra ringOption
	
	public RingOption getRingOption() {
		return this.ringOption;
	}
}
//Khai báo các thành phần con tạo ra PanelOption

/*
 * Lớp RingOption được sử dụng để hiện thi các thông tin thiết lập âm lượng của chuông báo công việc
 */

class RingOption extends JPanel {
	//Khai báo kích thước mặc định ban đầu dành cho thành phần này
	
	public static final int WIDTH_PANEL_RING = 500;
	public static final int HEIGHT_PANEL_RING = 100;
	//Khai báo các biến được sử dụng trong lớp này
	
	private JPanel[] component = new JPanel[2];
	private Font title = new Font("Times New Roman", Font.BOLD, 25);
	private Font volumnFont = new Font("Times New Roman", Font.PLAIN, 15); 
	private JLabel label = new JLabel("Ring");
	private JLabel volumnLabel = new JLabel("Volumn");
	private JSlider volumnControl = new JSlider(0, 86);
	JButton buttonPause = new JButton("  Play  ");
	boolean flag = true;
	
	private WavPlayer sounder = new WavPlayer("alarm.wav");
	//Hàm khởi tạo
	
	public RingOption() {
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.add(this.component[0], BorderLayout.NORTH);
		this.add(this.component[1], BorderLayout.CENTER);
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.setPreferredSize(new Dimension(RingOption.WIDTH_PANEL_RING, RingOption.HEIGHT_PANEL_RING));
		
		this.handleEvent();
	} 
	//Hàm khởi tạo tạo các giá trị ban đầu cho các thành phần có lớp này
	
	private void initializeComponent() {
		this.component[0] = new JPanel();
		this.label.setFont(this.title);
		this.label.setForeground(Color.white);
		this.component[0].add(this.label);
		this.component[0].setBackground(PanelOption.COLOR_TITLE);
		this.component[0].setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		
		this.component[1] = new JPanel();
		this.component[1].setBackground(Color.white);
		this.component[1].setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.volumnLabel.setFont(volumnFont);
		this.component[1].add(this.volumnLabel);
		this.component[1].add(this.volumnControl);
		this.component[1].add(this.buttonPause);
		
		this.volumnControl.setValue(80);
	}
	//Phương thức lấy ra sounder
	
	public WavPlayer getSounder() {
		return this.sounder;
	}
	//Phương thức tạm dừng chuông báo
	
	public void pauseRing() {
		sounder.pause();
		buttonPause.setText("  Play  ");
	}
	//Phương thức tiếp tục chạy chuông báo
	
	public void playRing() {
		sounder.play(Sequencer.LOOP_CONTINUOUSLY);
		buttonPause.setText("Pause");
	}
	//Hàm sử lý các sự kiện trong lớp này
	
	private void handleEvent() {
		this.volumnControl.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				sounder.setVolume((float) (volumnControl.getValue() - 80));
			}
		});
		
		this.buttonPause.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				flag = !flag;
				if(flag) {
					pauseRing();
				}
				else {
					playRing();
				}
			}
		});
	}
}
/*
 * Lớp này được sử dụng để hiện thị và cho phép ngư�?i dùng thay đổi trực tiếp các thông tin của cá nhân mình
 */

class UserOption extends JPanel {
	//Khai báo kích thước mặc định ban đầu dành cho thành phần này
	
	public static final int WIDTH_PANEL_USER = 500;
	public static final int HEIGHT_PANEL_USER = 300;
	//Khai báo các biến được sử dụng trong lớp này
	
	private JPanel[] component = new JPanel[6];
	private Font title = new Font("Times New Roman", Font.BOLD, 25);
	private Font userFont = new Font("Times New Roman", Font.PLAIN, 15); 
	private JLabel titlePanel = new JLabel("User Information");
	private JLabel nameLabel = new JLabel("Name  ");
	private JLabel emailLabel = new JLabel("Email   ");
	private JLabel addressLabel = new JLabel("Address");
	private JButton button = new JButton("Change");
	private JTextArea[] text = new JTextArea[3];
	private JScrollPane[] pane = new JScrollPane[3];
	private SqlProcessor sqlProcessor;
	private SqlData data = null;
	private boolean flag = false;
	/*Hàm khởi tạo, hàm này có tham số truy�?n vào là dữ liệu kiểu SqlData có kiểu là user
	 *
	 */
	
	public UserOption(SqlProcessor sqlProcessor) {
		this.sqlProcessor = sqlProcessor;
		this.initializeComponent();
		this.handleEvent();
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.add(this.component[0], BorderLayout.NORTH);
		this.add(this.component[1], BorderLayout.CENTER);
		this.add(this.component[5], BorderLayout.SOUTH);
		this.setSize(new Dimension(UserOption.WIDTH_PANEL_USER, UserOption.HEIGHT_PANEL_USER));
		this.setBackground(Color.white);
	}
	//Phương thức Khởi tạo và đưa thành phần vào bên trong chính panel này
	
	private void initializeComponent() {
		this.component[0] = new JPanel();
		this.component[0].setBackground(PanelOption.COLOR_TITLE);
		this.titlePanel.setFont(this.title);
		this.titlePanel.setForeground(Color.white);
		this.component[0].add(titlePanel);
		
		this.component[1] = new JPanel();
		this.component[1].setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.component[1].setBackground(Color.white);
		this.component[1].setLayout(new BorderLayout());
		this.data = sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]);
		this.text[0] = new JTextArea(3, 20);
		this.text[0].setFont(this.userFont);
		this.text[0].setText(StringHandle.convertString(userFont, this.data.getValueProperties()[SqlData.USER_NAME], 214));
		this.text[0].setEditable(false);
		this.pane[0] = new JScrollPane(this.text[0]);
		this.text[1] = new JTextArea(2, 20);
		this.text[1].setFont(this.userFont);
		this.text[1].setText(StringHandle.convertString(userFont, this.data.getValueProperties()[SqlData.USER_EMAIL], 214));
		this.text[1].setEditable(false);
		this.pane[1]= new JScrollPane(this.text[1]);
		this.text[2] = new JTextArea(3, 20);
		this.text[2].setFont(userFont);
		this.text[2].setText(StringHandle.convertString(userFont, this.data.getValueProperties()[SqlData.USER_ADDRESS], 214));
		this.text[2].setEditable(false);
		this.pane[2] = new JScrollPane(this.text[2]);
		this.nameLabel.setFont(userFont);
		this.emailLabel.setFont(userFont);
		this.addressLabel.setFont(userFont);
		this.component[2] = new JPanel();
		this.component[2].setBackground(Color.white);
		this.component[2].add(nameLabel);
		this.component[2].add(this.pane[0]);
		this.component[3] = new JPanel();
		this.component[3].setBackground(Color.white);
		this.component[3].add(emailLabel);
		this.component[3].add(this.pane[1]);
		this.component[4] = new JPanel();
		this.component[4].setBackground(Color.white);
		this.component[4].add(addressLabel);
		this.component[4].add(this.pane[2]);
		
		this.component[1].add(this.component[2], BorderLayout.NORTH);
		this.component[1].add(this.component[3], BorderLayout.CENTER);
		this.component[1].add(this.component[4], BorderLayout.SOUTH);
		
		this.component[5] = new JPanel();
		this.component[5].setBackground(Color.white);
		this.component[5].add(this.button);
	}
	//Phương thức sử lý các sự kiện của phương thức này
	
	private void handleEvent() {
		this.button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				String[] dataImport = sqlProcessor.createArrayString(SqlProcessor.TABLE_USER); 
				flag = !flag;
				
				if(flag) {
					button.setText("Update");
					changeStatus(flag);
				}
				else {
					button.setText("Change");
					dataImport[SqlData.USER_NAME] = StringHandle.changeIntoSpace(text[0].getText());
					dataImport[SqlData.USER_EMAIL] = StringHandle.changeIntoSpace(text[1].getText());
					dataImport[SqlData.USER_ADDRESS] = StringHandle.changeIntoSpace(text[2].getText());
					
					text[0].setText(StringHandle.convertString(userFont, data.getValueProperties()[SqlData.USER_NAME], 214));
					text[1].setText(StringHandle.convertString(userFont, data.getValueProperties()[SqlData.USER_EMAIL], 214));
					text[2].setText(StringHandle.convertString(userFont, data.getValueProperties()[SqlData.USER_ADDRESS], 214));
					
					sqlProcessor.updateTable(SqlProcessor.TABLE_USER, dataImport, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
					updateDataUser();
					changeStatus(flag);
					JOptionPane.showMessageDialog(null, "Update account success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	//Phương thức thay đổi trạng thái các text area có trong lớp này
	
	private void changeStatus(boolean isEnable) {
		for(int i = 0; i < this.text.length; i ++) {
			this.text[i].setEditable(isEnable);
		}
	}
	//Phương thức cập nhật lại thông tin của ngư�?i dùng
	
	public void updateDataUser() {
		this.data = sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]);
		
		this.text[0].setText(StringHandle.convertString(userFont, this.data.getValueProperties()[SqlData.USER_NAME], 214));
		this.text[0].setEditable(false);
		
		this.text[1].setText(StringHandle.convertString(userFont, this.data.getValueProperties()[SqlData.USER_EMAIL], 214));
		this.text[1].setEditable(false);
		
		this.text[2].setText(StringHandle.convertString(userFont, this.data.getValueProperties()[SqlData.USER_ADDRESS], 214));
		this.text[2].setEditable(false);
	}
}
/*
 * Lớp DatabaseOption được sử dụng để hiển thị thông tin v�? tên của cơ sở dữ liệu đang sử dụng, tên của user đối với cơ sở dữ liệu
 */

class DatabaseOption extends JPanel {
	//Khai báo kích thước mặc định ban đầu dành cho thành phần này
	
	public static final int WIDTH_PANEL_DATABASE = 500;
	public static final int HEIGHT_PANEL_DATABASE = 250;
	//Khai báo các biến được sử dụng trong lớp này
	
	private JPanel[] component = new JPanel[5];
	private JLabel[] label = new JLabel[3];
	private JButton changeDatabase = new JButton("Change Database");
	private Font titleFont = new Font("Times New Roman", Font.BOLD, 25);
	private Font normal = new Font("Times New Roman", Font.PLAIN, 15);
	private SqlProcessor sqlProcessor; 
	private PanelOption panelOption;
	private DatabaseSwitch dbSwitch;
	//Phương thức khởi tạo
	
	public DatabaseOption(SqlProcessor sqlProcessor, PanelOption panelOption) {
		this.sqlProcessor = sqlProcessor;
		this.panelOption = panelOption;
		this.dbSwitch = new DatabaseSwitch(sqlProcessor, this, panelOption);
		
		this.initializeComponent();
		this.handleEvent();
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(DatabaseOption.WIDTH_PANEL_DATABASE, DatabaseOption.HEIGHT_PANEL_DATABASE));
		this.setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.add(this.component[0], BorderLayout.NORTH);
		this.add(this.component[1], BorderLayout.CENTER);
		this.add(this.component[4], BorderLayout.SOUTH);
	}
	//Phương thức tạo dữ liệu cho phần đồ h�?a
	
	private void initializeComponent() {
		this.component[0] = new JPanel();
		this.component[0].setBackground(PanelOption.COLOR_TITLE);
		this.label[0] = new JLabel("Database Information");
		this.label[0].setFont(titleFont);
		this.label[0].setForeground(Color.white);
		this.component[0].add(this.label[0]);
		
		this.component[1] = new JPanel();
		this.setBackground(Color.white);
		this.component[1].setLayout(new BorderLayout());
		
		this.component[2] = new JPanel();
		this.component[2].setBackground(Color.white);
		this.label[1] = new JLabel();
		this.label[1].setText("Name:   " + XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_DATABASE]);
		this.label[1].setFont(this.normal);
		this.component[2].add(this.label[1]);
		
		this.component[3] = new JPanel();
		this.component[3].setBackground(Color.white);
		this.label[2] = new JLabel();
		this.label[2].setText("User:   " + XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_NAME]);
		this.label[2].setFont(this.normal);
		this.component[3].add(this.label[2]);
		
		this.component[1].add(this.component[2], BorderLayout.NORTH);
		this.component[1].add(this.component[3], BorderLayout.CENTER);
		
		this.component[4] = new JPanel();
		this.component[4].setBackground(Color.white);
		this.component[4].setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.changeDatabase.setFont(this.normal);
		this.component[4].add(this.changeDatabase);
	}
	//Phương thức lấy ra DataSwitch
	
	public DatabaseSwitch getDbSwitch() {
		return this.dbSwitch;
	}
	//Phương thức xử lý sự kiện cho các thành phần của lớp này 
	
	private void handleEvent() {
		this.changeDatabase.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				dbSwitch.setVisible(true);
				panelOption.getContainer().add(dbSwitch, BorderLayout.CENTER);
				setVisible(false);
			}
		});
	}
	//Phương thức cập nhật lại thông tin cho database
	
	public void updateInformation() {
		this.label[1].setText("Name:   " + XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_DATABASE]);
		this.label[2].setText("User:   " + XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_NAME]);
	}

}
/*
 * Lớp DatabaseSwitch được sử dụng để thay đổi thông tin chi tiết của cơ sở dữ liệu: 
 * + Tên của database 
 * + �?ịa chỉ databse hoặc chuyển hẳn ra phần tạo một cơ sở dữ liệu mới 
 * *Chú ý: Các thông tin này là thông tin của MySQL nên nếu muốn thay đổi thành công các thông tin này phải tồn tại trong cơ sở dữ liệu MySQL
 */

class DatabaseSwitch extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_DATABASE_SWITCH = 500;
	public static final int HEIGHT_PANEL_DATABASE_SWITCH = 250;
	//Khai báo các biến được sử dụng trong lớp này
	
	private JPanel[] component = new JPanel[7];
	private JLabel[] label = new JLabel[9];
	private JTextField[] field = new JTextField[3];
	private JPasswordField password = new JPasswordField(20);
	private JButton[] button = new JButton[2];
	private DatabaseOption dbOption;
	private PanelOption panelOption;
	private Font title = new Font("Times New Roman", Font.PLAIN, 25);
	private Font normal = new Font("Times New Roman", Font.PLAIN, 15);
	private SqlProcessor sqlProcessor;
	//Phương thức khởi tạo
	
	public DatabaseSwitch(SqlProcessor processor, DatabaseOption dbOption, PanelOption panelOption) {
		this.sqlProcessor = processor;
		this.dbOption = dbOption;
		this.panelOption = panelOption;
		
		this.initializeComponent();
		this.handleEvent();
		
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.add(this.component[0], BorderLayout.NORTH);
		this.add(this.component[1], BorderLayout.CENTER);
		this.add(this.component[2], BorderLayout.SOUTH);
	}
	//Phương thức tạo ra giao diện cho lớp này
	
	private void initializeComponent() {
		this.component[0] = new JPanel();
		this.component[0].setBackground(PanelOption.COLOR_TITLE);
		this.component[0].setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.label[0] = new JLabel("Change Database");
		this.label[0].setFont(this.title);
		this.label[0].setForeground(Color.white);
		this.component[0].add(this.label[0]);
		
		this.component[1] = new JPanel();
		this.component[1].setLayout(new BoxLayout(this.component[1], BoxLayout.Y_AXIS));
		this.component[1].setBackground(Color.white);
		
		this.label[1] = new JLabel("       Database   ");
		this.label[5] = new JLabel("       ");
		this.field[0] = new JTextField(20);
		this.label[1].setFont(normal);
		this.field[0].setFont(normal);
		
		this.component[3] = new JPanel();
		this.component[3].setLayout(new BoxLayout(this.component[3], BoxLayout.X_AXIS));
		this.component[3].setPreferredSize(new Dimension(DatabaseSwitch.WIDTH_PANEL_DATABASE_SWITCH, (int) (DatabaseSwitch.HEIGHT_PANEL_DATABASE_SWITCH / 4)));
		this.component[3].setBackground(Color.white);
		
		this.component[3].add(this.label[1]);
		this.component[3].add(this.field[0]);
		this.component[3].add(this.label[5]);
		
		this.label[2] = new JLabel("       Address     ");
		this.label[6] = new JLabel("       ");
		this.field[1] = new JTextField(20);
		this.label[2].setFont(normal);
		this.field[1].setFont(normal);
		
		this.component[4] = new JPanel();
		this.component[4].setLayout(new BoxLayout(this.component[4], BoxLayout.X_AXIS));
		this.component[4].setPreferredSize(new Dimension(DatabaseSwitch.WIDTH_PANEL_DATABASE_SWITCH, (int) (DatabaseSwitch.HEIGHT_PANEL_DATABASE_SWITCH / 4)));
		this.component[4].setBackground(Color.white);
		
		this.component[4].add(this.label[2]);
		this.component[4].add(this.field[1]);
		this.component[4].add(this.label[6]);
		
		this.label[3] = new JLabel("       User Name");
		this.label[7] = new JLabel("       ");
		this.field[2] = new JTextField(20);
		this.label[3].setFont(normal);
		this.field[2].setFont(normal);
		
		this.component[5] = new JPanel();
		this.component[5].setLayout(new BoxLayout(this.component[5], BoxLayout.X_AXIS));
		this.component[5].setPreferredSize(new Dimension(DatabaseSwitch.WIDTH_PANEL_DATABASE_SWITCH, (int) (DatabaseSwitch.HEIGHT_PANEL_DATABASE_SWITCH / 4)));
		this.component[5].setBackground(Color.white);
		
		this.component[5].add(this.label[3]);
		this.component[5].add(this.field[2]);
		this.component[5].add(this.label[7]);
		
		this.label[4] = new JLabel("       Password   ");
		this.label[8] = new JLabel("       ");
		this.label[4].setFont(normal);
		this.password.setFont(normal);
		
		this.component[6] = new JPanel();
		this.component[6].setLayout(new BoxLayout(this.component[6], BoxLayout.X_AXIS));
		this.component[6].setPreferredSize(new Dimension(DatabaseSwitch.WIDTH_PANEL_DATABASE_SWITCH, (int) (DatabaseSwitch.HEIGHT_PANEL_DATABASE_SWITCH / 4)));
		this.component[6].setBackground(Color.white);
		
		this.component[6].add(this.label[4]);
		this.component[6].add(this.password);
		this.component[6].add(this.label[8]);
		
		this.component[1].add(this.component[3]);
		this.component[1].add(this.component[4]);
		this.component[1].add(this.component[5]);
		this.component[1].add(this.component[6]);
		
		this.component[2] = new JPanel();
		this.component[2].setBackground(Color.white);
		this.component[2].setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.button[0] = new JButton("Apply");
		this.button[1] = new JButton("Create Anything");
		this.button[0].setFont(normal);
		this.button[1].setFont(normal);
		this.component[2].add(this.button[0]);
		this.component[2].add(this.button[1]);
	}
	//Phương thức xử lý các sự kiện của lớp này
	
	private void handleEvent() {
		this.button[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				String urlRemember = XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_URL];
				String userRemember = XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_NAME];
				String passwordRemember = XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_PASSWORD];
				
				if(sqlProcessor.createConnection(field[1].getText(), field[0].getText(), field[2].getText(), password.getText())) {
					if(JOptionPane.showConfirmDialog(null, "Connect sucess!", "Dialog Message", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						dbOption.setVisible(true);
						panelOption.getContainer().add(dbOption, BorderLayout.CENTER);
						setVisible(false);
					}
				}
				else {
					sqlProcessor.createConnection(urlRemember, userRemember, passwordRemember);
					if(JOptionPane.showConfirmDialog(null, "Connect fail!", "Dialog Message", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						dbOption.setVisible(true);
						panelOption.getContainer().add(dbOption, BorderLayout.CENTER);
						setVisible(false);
					}
				}
			}
		});
		
		this.button[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				panelOption.getPanelBody().getTimeline().showPanel(TimeLine.TYPE_BEGIN);
			}
		});
	}
}

class DataInsertionOption extends JPanel {
	//Khai báo kích thước mặc định ban đầu dành cho thành phần này
	
	public static final int WIDTH_PANEL_INSERTION = 500;
	public static final int HEIGHT_PANEL_INSERTION = 150;
	//Khai báo các biến sử dụng trong lớp này
	
	private JPanel[] component = new JPanel[2];
	private JLabel title = new JLabel("Activity");
	private JButton activityCreation = new JButton("Create Activity");
	private Font titleFont = new Font("Times New Roman", Font.BOLD, 25);
	private Font normal = new Font("Times New Roman", Font.PLAIN, 15);
	private PanelInsertion panelInsertion;
	PanelBody panelContainer;
	//Phương thức khởi tạo
	
	public DataInsertionOption(SqlProcessor sqlProcessor, PanelBody panelContainer) {
		this.panelContainer = panelContainer;
		this.panelInsertion = new PanelInsertion(sqlProcessor, this.panelContainer);
		
		this.initializeComponent();
		this.handleEvent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createLineBorder(PanelOption.COLOR_TITLE));
		this.add(this.component[0], BorderLayout.NORTH);
		this.add(this.component[1], BorderLayout.CENTER);
	}
	//Phương thức khởi tạo các thành phần của lớp này 
	
	private void initializeComponent() {
		this.component[0] = new JPanel();
		this.component[0].setBackground(PanelOption.COLOR_TITLE);
		this.title.setFont(titleFont);
		this.title.setForeground(Color.white);
		this.component[0].add(title);
		
		this.component[1] = new JPanel();
		this.component[1].setBackground(Color.white);
		this.activityCreation.setFont(normal);
		this.component[1].add(activityCreation);
	}
	//Phương thức xử lý sự kiện của lớp này
	
	private void handleEvent() {
		this.activityCreation.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				panelContainer.add(panelInsertion, BorderLayout.CENTER);
				panelContainer.showComponent(PanelBody.TYPE_PANEL_INSERTION);
			}
		});
	}
	//Phương thức lấy ra panelInsertion
	
	public PanelInsertion getPanelInsertion() {
		return this.panelInsertion;
	}
}