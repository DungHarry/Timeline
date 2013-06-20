package project1.timeline.component;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import project1.timeline.engine.SqlData;
import project1.timeline.engine.SqlDataTransport;
import project1.timeline.engine.SqlProcessor;
import project1.timeline.engine.XmlProcessor;
import project1.timeline.gui.body.PanelBody;

/*
 * Lớp này là lớp tạo panel cho phép ngư�?i sử dụng nhập dữ liệu vào trong MySQL
 */
public class PanelInsertion extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_INSERTION = 720;
	public static final int HEIGHT_PANEL_INSERTION = 700;
	
	public static final Color COLOR_TITLE = Color.blue;
	public static final Color COLOR_BACKGROUND = Color.white;
	public static final Color COLOR_FOREGROUND = Color.white;
	public static final Color COLOR_BORDER = Color.blue;
	public static final Font FONT_TITLE = new Font("Times New Roman", Font.BOLD, 25);
	public static final Font FONT_NORMAL = new Font("Times New Roman", Font.PLAIN, 15);
	public static final Font FONT_STRONG = new Font("Times New Roman", Font.BOLD, 15);
	//Khai báo biến sử dụng trong lớp này
	
	SqlDataTransport data = new SqlDataTransport(SqlDataTransport.TYPE_IMPORT);
	String[][] templeArray = new String[6][];
	SqlProcessor sqlProcessor;
	JPanel[] panel = new JPanel[2];
	PanelChoice panelChoice;
	PanelActivity panelActivity;
	PanelTime panelTime;
	JButton button = new JButton("Insert to database");
	PanelBody panelBody;
	//Phương thức khởi tạo
	
	public PanelInsertion(SqlProcessor sqlProcessor, PanelBody panelBody) {
		this.sqlProcessor = sqlProcessor;
		this.initializeData();
		this.handleEvent();
		this.panelChoice = new PanelChoice(sqlProcessor, this.templeArray);
		this.panelActivity = new PanelActivity(this.templeArray);
		this.panelTime = new PanelTime(this.templeArray);
		this.panelBody = panelBody;
		
		this.initializeComponent();
		this.setLayout(new BorderLayout());
		this.setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.setBorder(BorderFactory.createLineBorder(PanelInsertion.COLOR_BORDER));
		this.add(this.panelChoice, BorderLayout.NORTH);
		this.add(this.panel[0], BorderLayout.CENTER);
		this.add(this.panel[1], BorderLayout.SOUTH);
	}
	//Phương thức khởi tạo dữ liệu ban đầu cho lớp này
	
	private void initializeData() {
		this.templeArray[SqlProcessor.TABLE_ACTIVITY] = new String[SqlData.SIZE_ARRAY_ACTIVITY];
		this.templeArray[SqlProcessor.TABLE_PLACE] = new String[SqlData.SIZE_ARRAY_PLACE];
		this.templeArray[SqlProcessor.TABLE_TIME] = new String[SqlData.SIZE_ARRAY_TIME];
		this.templeArray[SqlProcessor.TABLE_TYPE] = new String[SqlData.SIZE_ARRAY_TYPE];
		this.templeArray[SqlProcessor.TABLE_USER] = new String[SqlData.SIZE_ARRAY_USER];
		this.templeArray[SqlProcessor.TABLE_USER_ACTIVITY] = new String[SqlData.SIZE_ARRAY_USER_ACTIVITY];
		
		for(int i = 0; i < SqlData.SIZE_ARRAY_ACTIVITY; i ++) {
			this.templeArray[SqlProcessor.TABLE_ACTIVITY][i] = new String();
		}
		
		for(int i = 0; i < SqlData.SIZE_ARRAY_PLACE; i ++) {
			this.templeArray[SqlProcessor.TABLE_PLACE][i] = new String();
		}
		
		for(int i = 0; i < SqlData.SIZE_ARRAY_TIME; i ++) {
			this.templeArray[SqlProcessor.TABLE_TIME][i] = new String();
		}
		
		for(int i = 0; i < SqlData.SIZE_ARRAY_TYPE; i ++) {
			this.templeArray[SqlProcessor.TABLE_TYPE][i] = new String();
		}
		
		for(int i = 0; i < SqlData.SIZE_ARRAY_USER; i ++) {
			this.templeArray[SqlProcessor.TABLE_USER][i] = new String();
		}
		
		for(int i = 0; i < SqlData.SIZE_ARRAY_USER_ACTIVITY; i ++) {
			this.templeArray[SqlProcessor.TABLE_USER_ACTIVITY][i] = new String();
		}
	}
	//Phương thức xử lý sự kiện cho lớp này
	
	private void handleEvent() {
		this.button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				importData();
				panelChoice.updateChoicer();
				panelBody.getPanelActivityRing().updateDataArray();
			}
		});
	}
	//Phương thức khởi tạo giao diện cho đối tượng của lớp này
	
	private void initializeComponent() {
		this.panel[0] = new JPanel();
		this.panel[0].setLayout(new BoxLayout(this.panel[0], BoxLayout.Y_AXIS));
		this.panel[0].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.panel[0].setPreferredSize(new Dimension(715, 350));
		this.panel[0].add(this.panelActivity);
		this.panel[0].add(this.panelTime);
		
		this.panel[1] = new JPanel();
		this.panel[1].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.panel[1].setPreferredSize(new Dimension(715, 100));
		this.panel[1].setBorder(BorderFactory.createLineBorder(PanelInsertion.COLOR_BORDER));
		this.button.setFont(PanelInsertion.FONT_TITLE);
		this.panel[1].add(this.button);
	}
	//Phương thức nhập dữ liệu từ mảng templeArray vào mảng của đối tượng data và chuyển vào cơ sở dữ liệu MySQL
	
	private void importData() {
		
		if(this.panelChoice.setDataImport() && this.panelTime.setDataImport()) {
			this.panelActivity.setDataImport();
			
			for(int i = 0; i < this.templeArray.length; i ++) {
				this.data.getDataArray()[i].setValueProperties(this.templeArray[i]);
			}
			
			this.sqlProcessor.importFromTransport(this.data);
			
			JOptionPane.showMessageDialog(null, "Insert data sucess!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//Phương thức lấy ra PanelChoice
	
	public PanelChoice getPanelChoice() {
		return this.panelChoice;
	}
	//Phương thức lấy ra PanelChoice
	
	public void updatePanelChoice() {
		this.panelChoice.updateChoicer();
	}
}
/*
 * Khai báo lớp PanelChoice là phần ch�?n dữ liệu cho loại và nơi xảy ra hoạt động
 */

class PanelChoice extends JPanel {
	//Khai báo các hằng số được sử dụng cho lớp này
	
	public static final int WIDTH_PANEL_CHOICE = 720;
	public static final int HEIGHT_PANEL_CHOICE = 250;
	
	private final static int TYPE_CHOICER = 0;
	private final static int PLACE_CHOICER = 1;
	private final static int ID = 0;
	private final static int NAME = 1;
	private final static String STRING_PLACE = "Choose place...";
	private final static String STRING_TYPE = "Choose type...";
	
	//Khai báo các biến sử dụng trong lớp này
	
	JPanel[] panel = new JPanel[8];
	JLabel[] label = new JLabel[6];
	Choice[] choicer = new Choice[2];
	JCheckBox[] checkBox = new JCheckBox[2];
	JTextField[] textField = new JTextField[2];
	JTextArea[] textArea = new JTextArea[3];
	JScrollPane[] scrollPane = new JScrollPane[3];
	SqlProcessor sqlProcessor;
	String[][][] templeArray = new String[2][2][];
	String[][] dataImport;
	String[] templeId = new String[2];
	//Phương thức khởi tạo
	
	public PanelChoice(SqlProcessor sqlProcessor, String[][] dataImport) {
		this.sqlProcessor = sqlProcessor;
		this.dataImport = dataImport;
		
		this.initializeComponent();
		this.handleEvent();
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(PanelChoice.WIDTH_PANEL_CHOICE, PanelChoice.HEIGHT_PANEL_CHOICE));
		this.setBorder(BorderFactory.createLineBorder(PanelInsertion.COLOR_BORDER));
		this.setLayout(new BorderLayout());
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.panel[1], BorderLayout.CENTER);
	}
	//Phương thức khởi tạo các thành phần đồ h�?a cho đối tượng của lớp này
	
	private void initializeComponent() {
		this.createChoicer();
		
		this.panel[0] = new JPanel();
		this.panel[0].setBackground(PanelInsertion.COLOR_TITLE);
		this.panel[0].setLayout(new BoxLayout(this.panel[0], BoxLayout.X_AXIS));
		this.label[0] = new JLabel("Place and Type");
		this.label[0].setFont(PanelInsertion.FONT_TITLE);
		this.label[0].setForeground(PanelInsertion.COLOR_FOREGROUND);
		this.panel[0].add(this.label[0]);
		
		this.panel[1] = new JPanel();
		this.panel[1].setLayout(new BorderLayout());
		this.panel[1].setBackground(PanelInsertion.COLOR_BACKGROUND);
		
		this.panel[2] = new JPanel();
		this.panel[2].setLayout(new BorderLayout());
		this.panel[2].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.panel[2].setPreferredSize(new Dimension(350, 200));
		
		this.panel[3] = new JPanel();
		this.panel[3].setLayout(new BoxLayout(this.panel[3], BoxLayout.X_AXIS));
		this.panel[3].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.checkBox[0] = new JCheckBox();
		this.checkBox[0].setSelected(true);
		this.panel[3].add(this.checkBox[0]);
		//this.panel[3].add(this.scrollPane[3]);
		this.panel[3].add(this.choicer[PanelChoice.PLACE_CHOICER]);
		
		this.panel[4] = new JPanel();
		//this.panel[4].setLayout(new BorderLayout());
		this.panel[4].setBackground(Color.white);
		this.label[1] = new JLabel("Name         ");
		this.label[1].setFont(PanelInsertion.FONT_NORMAL);
		this.textField[0] = new JTextField(20);
		this.textField[0].setFont(PanelInsertion.FONT_NORMAL);
		this.textField[0].setEditable(false);
		this.panel[4].add(this.label[1]);
		this.panel[4].add(this.textField[0]);
		
		this.label[2] = new JLabel("Address      ");
		this.label[2].setFont(PanelInsertion.FONT_NORMAL);
		this.textArea[0] = new JTextArea(4, 20);
		this.textArea[0].setFont(PanelInsertion.FONT_NORMAL);
		this.textArea[0].setEditable(false);
		this.scrollPane[0] = new JScrollPane(this.textArea[0]);
		this.panel[4].add(this.label[2]);
		this.panel[4].add(this.scrollPane[0]);
		this.label[3] = new JLabel("Description  ");
		this.label[3].setFont(PanelInsertion.FONT_NORMAL);
		this.textArea[1] = new JTextArea(4, 20);
		this.textArea[1].setFont(PanelInsertion.FONT_NORMAL);
		this.textArea[1].setEditable(false);
		this.scrollPane[1] = new JScrollPane(this.textArea[1]);
		this.panel[4].add(this.label[3]);
		this.panel[4].add(this.scrollPane[1]);
		
		this.panel[2].add(this.panel[3], BorderLayout.NORTH);
		this.panel[2].add(this.panel[4], BorderLayout.CENTER);
		
		this.panel[5] = new JPanel();
		this.panel[5].setLayout(new BorderLayout());
		this.panel[5].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.panel[5].setPreferredSize(new Dimension(350, 200));
		
		this.panel[6] = new JPanel();
		this.panel[6].setLayout(new BoxLayout(this.panel[6], BoxLayout.X_AXIS));
		this.panel[6].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.checkBox[1] = new JCheckBox();
		this.checkBox[1].setSelected(true);
		this.panel[6].add(this.checkBox[1]);
		//this.panel[6].add(this.scrollPane[4]);
		this.panel[6].add(this.choicer[PanelChoice.TYPE_CHOICER]);
		
		this.panel[7] = new JPanel();
		this.panel[7].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.label[4] = new JLabel("Name           ");
		this.label[4].setFont(PanelInsertion.FONT_NORMAL);
		this.textField[1] = new JTextField(20);
		this.textField[1].setFont(PanelInsertion.FONT_NORMAL);
		this.textField[1].setEditable(false);
		this.panel[7].add(this.label[4]);
		this.panel[7].add(this.textField[1]);
		this.label[5] = new JLabel("Description    ");
		this.label[5].setFont(PanelInsertion.FONT_NORMAL);
		this.textArea[2] = new JTextArea(8, 20);
		this.textArea[2].setFont(PanelInsertion.FONT_NORMAL);
		this.textArea[2].setEditable(false);
		this.scrollPane[2] = new JScrollPane(this.textArea[2]);		
		this.panel[7].add(this.label[5]);
		this.panel[7].add(this.scrollPane[2]);
		
		this.panel[5].add(this.panel[6], BorderLayout.NORTH);
		this.panel[5].add(this.panel[7], BorderLayout.CENTER);
		
		this.panel[1].add(this.panel[2], BorderLayout.WEST);
		this.panel[1].add(this.panel[5], BorderLayout.EAST);
	}
	//Phương thức khởi tạo hai Choice trong lớp này
	
	private void createChoicer() {
		ArrayList<SqlData> templePlace = sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_PLACE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		ArrayList<SqlData> templeType = sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_TYPE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		
		this.createTempleArray(SqlProcessor.TABLE_PLACE, templePlace);
		this.createTempleArray(SqlProcessor.TABLE_TYPE, templeType);
		
		this.choicer[PanelChoice.PLACE_CHOICER] = new Choice();
		this.choicer[PanelChoice.TYPE_CHOICER] = new Choice();
		
		this.choicer[PanelChoice.PLACE_CHOICER].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.choicer[PanelChoice.PLACE_CHOICER].setFont(PanelInsertion.FONT_NORMAL);
		this.choicer[PanelChoice.TYPE_CHOICER].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.choicer[PanelChoice.TYPE_CHOICER].setFont(PanelInsertion.FONT_NORMAL);
		
		this.choicer[PanelChoice.PLACE_CHOICER].add(PanelChoice.STRING_PLACE);
		this.choicer[PanelChoice.TYPE_CHOICER].add(PanelChoice.STRING_TYPE);
		
		for(int i = 0; i < templePlace.size(); i ++) {
			this.choicer[PanelChoice.PLACE_CHOICER].add(this.templeArray[PanelChoice.PLACE_CHOICER][PanelChoice.NAME][i]);
		}
		
		for(int i = 0; i < templeType.size(); i ++) {
			this.choicer[PanelChoice.TYPE_CHOICER].add(this.templeArray[PanelChoice.TYPE_CHOICER][PanelChoice.NAME][i]);
		}
		
		this.templeId[PanelChoice.PLACE_CHOICER] = "";
		this.templeId[PanelChoice.TYPE_CHOICER] = "";
		
		//this.scrollPane[3] = new JScrollPane(this.choicer[PanelChoice.PLACE_CHOICER]);
		//this.scrollPane[4] = new JScrollPane(this.choicer[PanelChoice.TYPE_CHOICER]);
	}
	//Phương thức cập nhật lại choicer sau khi chèn thêm dữ liệu vào cơ sở dữ liệu
	
	public void updateChoicer() {
		this.choicer[PanelChoice.PLACE_CHOICER].removeAll();
		this.choicer[PanelChoice.TYPE_CHOICER].removeAll();
		
		ArrayList<SqlData> templePlace = sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_PLACE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		ArrayList<SqlData> templeType = sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_TYPE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		
		this.createTempleArray(SqlProcessor.TABLE_PLACE, templePlace);
		this.createTempleArray(SqlProcessor.TABLE_TYPE, templeType);
		
		this.choicer[PanelChoice.PLACE_CHOICER].add(PanelChoice.STRING_PLACE);
		this.choicer[PanelChoice.TYPE_CHOICER].add(PanelChoice.STRING_TYPE);
		
		for(int i = 0; i < templePlace.size(); i ++) {
			this.choicer[PanelChoice.PLACE_CHOICER].add(this.templeArray[PanelChoice.PLACE_CHOICER][PanelChoice.NAME][i]);
		}
		
		for(int i = 0; i < templeType.size(); i ++) {
			this.choicer[PanelChoice.TYPE_CHOICER].add(this.templeArray[PanelChoice.TYPE_CHOICER][PanelChoice.NAME][i]);
		}
	}
	//Phương thức xử lý sự kiện trong lớp này
	
	private void handleEvent() {
		this.checkBox[0].addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setCheckbox(PanelChoice.PLACE_CHOICER, checkBox[0].isSelected());
			}
		});
		
		this.checkBox[1].addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setCheckbox(PanelChoice.TYPE_CHOICER, checkBox[1].isSelected());
			}
		});
	}
	//Phương thức khởi tạo dữ liệu cho mảng ba chiểu chứa các thông tin của place hoặc type từ một ArrayList<SqlData>
	
	private void createTempleArray(int typeTable, ArrayList<SqlData> temple) {
		if(typeTable == SqlProcessor.TABLE_PLACE) {
			this.templeArray[PanelChoice.PLACE_CHOICER][PanelChoice.ID] = new String[temple.size()];
			this.templeArray[PanelChoice.PLACE_CHOICER][PanelChoice.NAME] = new String[temple.size()];
			
			for(int i = 0; i < temple.size(); i ++) {
				this.templeArray[PanelChoice.PLACE_CHOICER][PanelChoice.ID][i] = temple.get(i).getValueProperties()[SqlData.PLACE_ID];
				this.templeArray[PanelChoice.PLACE_CHOICER][PanelChoice.NAME][i] = temple.get(i).getValueProperties()[SqlData.PLACE_NAME];
			}
		}
		else if(typeTable == SqlProcessor.TABLE_TYPE) {
			this.templeArray[PanelChoice.TYPE_CHOICER][PanelChoice.ID] = new String[temple.size()];
			this.templeArray[PanelChoice.TYPE_CHOICER][PanelChoice.NAME] = new String[temple.size()];
			
			for(int i = 0; i < temple.size(); i ++) {
				this.templeArray[PanelChoice.TYPE_CHOICER][PanelChoice.ID][i] = temple.get(i).getValueProperties()[SqlData.TYPE_ID];
				this.templeArray[PanelChoice.TYPE_CHOICER][PanelChoice.NAME][i] = temple.get(i).getValueProperties()[SqlData.TYPE_NAME];
			}
		}
	}
	//Khai báo phương thức lấy ra Id của type hoặc place tương ứng với phần tử được ch�?n trong choicer
	
	private void getIdChoicer(int typeChoicer, String name) {
		for(int i = 0; i < this.templeArray[typeChoicer][PanelChoice.NAME].length; i ++) {
			if(this.templeArray[typeChoicer][PanelChoice.NAME][i].equals(name)) {
				this.templeId[typeChoicer] = this.templeArray[typeChoicer][PanelChoice.ID][i];
				
				break;
			}
		}
	}
	//Phương thức thiết lập trạng thái ch�?n của các JTextField hoặc JTextArea tương ứng với JCheckBox
	
	private void setCheckbox(int type, boolean status) {
		if(type == PanelChoice.PLACE_CHOICER) {
			if(status == true) {
				this.choicer[PanelChoice.PLACE_CHOICER].setEnabled(true);
				
				this.textField[0].setEditable(false);
				this.textArea[0].setEditable(false);
				this.textArea[1].setEditable(false);
			}
			else {
				this.choicer[PanelChoice.PLACE_CHOICER].setEnabled(false);
				
				this.textField[0].setEditable(true);
				this.textArea[0].setEditable(true);
				this.textArea[1].setEditable(true);
				this.templeId[PanelChoice.PLACE_CHOICER] = "";
			}
		}
		else if(type == PanelChoice.TYPE_CHOICER) {
			if(status == true) {
				this.choicer[PanelChoice.TYPE_CHOICER].setEnabled(true);
				
				this.textField[1].setEditable(false);
				this.textArea[2].setEditable(false);
			}
			else {
				this.choicer[PanelChoice.TYPE_CHOICER].setEnabled(false);
				
				this.textField[1].setEditable(true);
				this.textArea[2].setEditable(true);
				this.templeId[PanelChoice.TYPE_CHOICER] = "";
			}
		}
	}
	//Phương thức thiết lập giá trị của các phần tử nhập dữ liệu
	
	public boolean setDataImport() {
		boolean firstResult = false;
		boolean secondResult = false;
		
		if(this.checkBox[1].isSelected()) {
			this.getIdChoicer(PanelChoice.TYPE_CHOICER, this.choicer[PanelChoice.TYPE_CHOICER].getSelectedItem());
		}
		
		if(this.checkBox[0].isSelected()) {
			this.getIdChoicer(PanelChoice.PLACE_CHOICER, this.choicer[PanelChoice.PLACE_CHOICER].getSelectedItem());
		}
		
		if(this.choicer[PanelChoice.TYPE_CHOICER].getSelectedItem().equals(PanelChoice.STRING_TYPE) && this.checkBox[1].isSelected()) {
			JOptionPane.showMessageDialog(null, "Please choose type of avtivity again!", "Note", JOptionPane.WARNING_MESSAGE);
		}
		else if((this.dataImport[SqlProcessor.TABLE_TYPE][SqlData.TYPE_ID] = this.templeId[PanelChoice.TYPE_CHOICER]).equals("")) {
			if(this.textField[1].getText().equals("") || this.textField[1].getText().equals(null) || !this.checkValid(PanelChoice.TYPE_CHOICER)) {
				JOptionPane.showMessageDialog(null, "Please typing name's type of activity!", "Note", JOptionPane.WARNING_MESSAGE);
			}
			else {
				this.dataImport[SqlProcessor.TABLE_TYPE][SqlData.TYPE_NAME] = this.textField[1].getText();
				this.dataImport[SqlProcessor.TABLE_TYPE][SqlData.TYPE_DESCRIPTION] = this.textArea[2].getText();
				
				firstResult = true;
			}
		}
		else {	
			firstResult = true;
		}
		
		if(this.choicer[PanelChoice.PLACE_CHOICER].getSelectedItem().equals(PanelChoice.STRING_PLACE) && this.checkBox[0].isSelected()) {
			JOptionPane.showMessageDialog(null, "Please choose type's place again!", "Note", JOptionPane.WARNING_MESSAGE);
		}
		else if((this.dataImport[SqlProcessor.TABLE_PLACE][SqlData.PLACE_ID] = this.templeId[PanelChoice.PLACE_CHOICER]).equals("")) {
			if(this.textField[0].getText().equals("") || this.textField[0].getText().equals(null) || this.textArea[0].getText().equals("") || this.textArea[0].getText().equals(null) || !this.checkValid(PanelChoice.PLACE_CHOICER)) {
				JOptionPane.showMessageDialog(null, "Please check some information of place!", "Note", JOptionPane.WARNING_MESSAGE);
			}
			else {
				this.dataImport[SqlProcessor.TABLE_PLACE][SqlData.PLACE_NAME] = this.textField[0].getText();
				this.dataImport[SqlProcessor.TABLE_PLACE][SqlData.PLACE_ADDRESS] = this.textArea[0].getText();
				this.dataImport[SqlProcessor.TABLE_PLACE][SqlData.PLACE_DESCRIPTION] = this.textArea[1].getText();
				secondResult = true;
			}
		}
		else {
			secondResult = true;
		}
		
		return (firstResult && secondResult);
	}
	//Phương thức cập nhật lại trạng thái của các thành phần nhập dữ liệu như ban đầu
	
	private void updateStage() {
		this.setCheckbox(PanelChoice.PLACE_CHOICER, true);
	    this.setCheckbox(PanelChoice.TYPE_CHOICER, true);
		this.checkBox[0].setSelected(true);
		this.checkBox[1].setSelected(true);
	}
	//Phương thức kiểm tra tính hợp lệ của dữ liệu đầu vào của các trư�?ng nhập vào: không được trùng với tên của các loại đã có trước đó
	
	private boolean checkValid(int typeChoice) {
		boolean result = true;
		String templeName = "";
		
		if(typeChoice == PanelChoice.TYPE_CHOICER) {
			templeName = this.textField[1].getText();
			
			if(!this.checkBox[1].isSelected()) {
				for(int i = 0; i < this.templeArray[typeChoice][PanelChoice.NAME].length; i ++) {
					if(this.templeArray[typeChoice][PanelChoice.NAME][i].equals(templeName)) {
						result = false;
					
						break;
					}
				}
			}
		}
		else if(typeChoice == PanelChoice.PLACE_CHOICER) {
			templeName = this.textField[0].getText();
			
			if(!this.checkBox[0].isSelected()) {
				for(int i = 0; i < this.templeArray[typeChoice][PanelChoice.NAME].length; i ++) {
					if(this.templeArray[typeChoice][PanelChoice.NAME][i].equals(templeName)) {
						result = false;
					
						break;
					}
				}
			}
		}
		
		return result;
	}
}
//Khai báo lớp tạo ra thành phần nhập dữ liệu cho activity

class PanelActivity extends JPanel {
	//Khai báo các hằng số trong lớp này
	
	public static final int WIDTH_PANEL_ACT = 720;
	public static final int HEIGHT_PANEL_ACT = 150;
	//Khai báo các biến được sử dụng trong lớp này
	
	JPanel[] panel = new JPanel[2];
	JLabel[] label = new JLabel[3];
	JTextArea noteText = new JTextArea(4, 40);
	JScrollPane scroll = new JScrollPane(noteText);
	JCheckBox checkBox = new JCheckBox();
	String[][] dataImport;
	//Phương thức khởi tạo các đối tượng của lớp này
	
	public PanelActivity(String[][] dataImport) {
		this.dataImport = dataImport;
		
		this.initializeComponent();
		this.setLayout(new BorderLayout());
		this.setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.setPreferredSize(new Dimension(PanelActivity.WIDTH_PANEL_ACT, PanelActivity.HEIGHT_PANEL_ACT));
		this.setBorder(BorderFactory.createLineBorder(PanelInsertion.COLOR_BORDER));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.panel[1], BorderLayout.CENTER);
	}
	//Phương thức khởi tạo các thành phần tạo ra giao diện của đối tượng của lớp này
	
	private void initializeComponent() {
		this.panel[0] = new JPanel();
		this.panel[0].setLayout(new BoxLayout(this.panel[0], BoxLayout.X_AXIS));
		this.panel[0].setBackground(PanelInsertion.COLOR_TITLE);
		this.label[0] = new JLabel("Activity");
		this.label[0].setFont(PanelInsertion.FONT_TITLE);
		this.label[0].setForeground(PanelInsertion.COLOR_FOREGROUND);
		this.panel[0].add(this.label[0]);
		
		this.panel[1] = new JPanel();
		this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.X_AXIS));
		this.panel[1].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.label[1] = new JLabel("Note");
		this.label[1].setFont(PanelInsertion.FONT_NORMAL);
		this.panel[1].add(this.label[1]);
		this.noteText.setFont(PanelInsertion.FONT_NORMAL);
		this.panel[1].add(this.scroll);
		this.label[2] = new JLabel("   Ring");
		this.label[2].setFont(PanelInsertion.FONT_NORMAL);
		this.panel[1].add(this.label[2]);
		this.checkBox.setSelected(false);
		this.panel[1].add(this.checkBox);
	}
	//Phương thức thiết lập dữ liệu cho lớp này
	
	public void setDataImport() {
		this.dataImport[SqlProcessor.TABLE_ACTIVITY][SqlData.ACTIVITY_NOTE] = this.noteText.getText();
		
		if(this.checkBox.isSelected()) {
			this.dataImport[SqlProcessor.TABLE_ACTIVITY][SqlData.ACTIVITY_RINGSTATUS] = "1";
		}
		else {
			this.dataImport[SqlProcessor.TABLE_ACTIVITY][SqlData.ACTIVITY_RINGSTATUS] = "0";
		}
	}
}
//Khai báo lớp tạo ra thành phần nhập dữ liệu cho th�?i gian của các hoạt động

class PanelTime extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_TIME_PANEL = 720;
	public static final int HEIGHT_TIME_PANEL = 200;
	
	private static final int FIELD_YEAR = 0;
	private static final int FIELD_MONTH = 1;
	private static final int FIELD_DAY = 2;
	//Khai báo các biến được sử dụng trong lớp này
	
	String[][] dataImport;
	JPanel[] panel = new JPanel[3];
	JLabel[] label = new JLabel[3];
	Choice[] choicer = new Choice[6];
	JTextArea note = new JTextArea(3, 40);
	JScrollPane[] scroll = new JScrollPane[7];
	//Phương thức khởi tạo
	
	public PanelTime(String[][] dataImport) {
		this.dataImport = dataImport;
		this.initialize();
		
		this.setLayout(new BorderLayout());
		this.setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.setPreferredSize(new Dimension(PanelTime.WIDTH_TIME_PANEL, PanelTime.HEIGHT_TIME_PANEL));
		this.setBorder(BorderFactory.createLineBorder(PanelInsertion.COLOR_BORDER));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.panel[1], BorderLayout.CENTER);
		this.add(this.panel[2], BorderLayout.SOUTH);
	}
	//Phương thức khởi tạo các thành phần cho đối tượng của lớp này
	
	private void initialize() {
		this.createScroll();
		
		this.panel[0] = new JPanel();
		this.panel[0].setLayout(new BoxLayout(this.panel[0], BoxLayout.X_AXIS));
		this.panel[0].setBackground(PanelInsertion.COLOR_TITLE);
		this.label[0] = new JLabel("Time");
		this.label[0].setFont(PanelInsertion.FONT_TITLE);
		this.label[0].setForeground(PanelInsertion.COLOR_FOREGROUND);
		this.panel[0].add(this.label[0]);
		
		this.panel[1] = new JPanel();
		this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.X_AXIS));
		this.panel[1].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.label[1] = new JLabel("Time:   ");
		this.label[1].setFont(PanelInsertion.FONT_NORMAL);
		this.panel[1].add(this.label[1]);
		
		for(int i = 0; i < 6; i ++) {
			this.panel[1].add(this.scroll[i]);
		}
		
		this.panel[2] = new JPanel();
		this.panel[2].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.label[2] = new JLabel("Note:    ");
		this.label[2].setFont(PanelInsertion.FONT_NORMAL);
		this.note.setFont(PanelInsertion.FONT_NORMAL);
		this.panel[2].add(this.label[2]);
		this.scroll[6] = new JScrollPane(this.note);
		this.panel[2].add(this.scroll[6]);
	}
	//Phương thức khởi tạo cho các thành phần Choice và JScrollPane trong lớp này
	
	private void createScroll() {
		for(int i = 0; i < this.choicer.length; i ++) {
			this.choicer[i] = new Choice();
			this.choicer[i].setBackground(PanelInsertion.COLOR_BACKGROUND);
			this.choicer[i].setFont(PanelInsertion.FONT_NORMAL);
			this.scroll[i] = new JScrollPane(this.choicer[i]);
		}
		
		this.choicer[0].add("Year");
		
		for(int i = 1992; i <= 2050; i ++) {
			this.choicer[0].add(Integer.toString(i));
		}
		
		this.choicer[1].add("Month");
		
		for(int i = 1; i <= 12; i ++) {
			this.choicer[1].add(Integer.toString(i));
		}
		
		this.choicer[2].add("Day");
		
		for(int i = 1; i <= 31; i ++) {
			this.choicer[2].add(Integer.toString(i));
		}
		
		this.choicer[3].add("Hour");
		
		for(int i = 0; i <= 23; i ++) {
			this.choicer[3].add(Integer.toString(i));
		}
		
		this.choicer[4].add("Minute");
		
		for(int i = 0; i < 60; i ++) {
			this.choicer[4].add(Integer.toString(i));
		}
		
		this.choicer[5].add("Seconds");
		
		for(int i = 0; i < 60; i ++) {
			this.choicer[5].add(Integer.toString(i));
		}
	}
	//Khai báo phương thức kiểm tra các trư�?ng dữ liệu gi�?, phút và giây
	
	private boolean checkField(int typeField) {
		boolean result = true;
		
		switch(typeField) {
			case PanelTime.FIELD_YEAR: {
				if(this.choicer[0].getSelectedItem().equals("Year")) {
					result = false;
				}
			}
				break;
			case PanelTime.FIELD_MONTH: {
				if(this.choicer[1].getSelectedItem().equals("Month")) {
					result = false;
				}
			}
				break;
			case PanelTime.FIELD_DAY: {
				if(this.choicer[2].getSelectedItem().equals("Day")) {
					result = false;
				}
			}
				break;
		}
		
		return result;
	}
	/*Khai báo phương thức thiết lập giá trị th�?i gian cho mảng dataImport
	 * Ngư�?i dùng bắt buộc phải lựa ch�?n ngày, tháng, năm và có thể không ch�?n gi�?, phút và giây
	 */
	
	public boolean setDataImport() {
		String time = "";
		boolean result = true;
		System.out.println(this.note.getText());
		this.dataImport[SqlProcessor.TABLE_TIME][SqlData.TIME_NOTE] = this.note.getText();
		System.out.println(this.note.getText());
		if(!this.checkField(PanelTime.FIELD_DAY) || !this.checkField(PanelTime.FIELD_MONTH) || !this.checkField(PanelTime.FIELD_YEAR)) {
			JOptionPane.showMessageDialog(null, "Please choose full 'Year', 'Month' and 'Day'!", "Note", JOptionPane.WARNING_MESSAGE);
			result = false;
		}
		else {
			time = this.choicer[0].getSelectedItem() + "-" + this.choicer[1].getSelectedItem() + "-" + this.choicer[2].getSelectedItem() + " ";
			
			if(this.choicer[3].getSelectedItem().equals("Hour")) {
				time = time + "0:";
			} else {
				time = time + this.choicer[3].getSelectedItem() + ":";
			}
			
			if(this.choicer[4].getSelectedItem().equals("Minute")) {
				time = time + "0:";
			}
			else {
				time = time + this.choicer[4].getSelectedItem() + ":";
			}
			
			if(this.choicer[5].getSelectedItem().equals("Seconds")) {
				time = time + "0";
			}
			else {
				time = time + this.choicer[5].getSelectedItem();
			}
			
			this.dataImport[SqlProcessor.TABLE_TIME][SqlData.TIME_TIME] = time;
		}
		
		return result;
	}
}