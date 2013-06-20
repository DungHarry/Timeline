package project1.timeline.component;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import project1.timeline.engine.SqlData;
import project1.timeline.engine.SqlProcessor;
import project1.timeline.engine.StringHandle;
import project1.timeline.engine.XmlProcessor;

/*
 * Khai báo lớp cho phép người dùng thay đổi thông tin với type và place
 */
public class PlaceAndType extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PLACE_TYPE = 500;
	public static final int HEIGHT_PLACE_TYPE = 700;
	public static final Color COLOR_BACKGROUND = Color.white;
	public static final Color COLOR_TITLE_BACKGROUND = Color.blue;
	public static final Color COLOR_FOREGROUND = Color.white;
	public static final Color COLOR_BORDER = Color.blue;
	public static final Font FONT_NORMAL = new Font("Times New Roman", Font.PLAIN, 15);
	public static final Font FONT_TITLE = new Font("Times New Roman", Font.BOLD, 25);
	//Khai báo các biến được sử dụng trong lớp này
	
	PlaceObject placeObject;
	TypeObject typeObject;
	SqlProcessor sqlProcessor;
	//Phương thức khởi tạo
	
	public PlaceAndType(SqlProcessor sqlProcessor) {
		this.sqlProcessor = sqlProcessor;
		
		this.typeObject = new TypeObject(this.sqlProcessor);
		this.placeObject = new PlaceObject(this.sqlProcessor);
		
		this.setLayout(new BorderLayout());
		this.setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.setBorder(BorderFactory.createLineBorder(PlaceAndType.COLOR_BORDER));
		this.setPreferredSize(new Dimension(PlaceAndType.WIDTH_PLACE_TYPE, PlaceAndType.HEIGHT_PLACE_TYPE));
		this.add(this.placeObject, BorderLayout.NORTH);
		this.add(this.typeObject, BorderLayout.CENTER);
	}
	//Khai báo phương thức cập nhật lại choicer cho cả Place và Type
	
	public void updateChoicer() {
		this.placeObject.updateChoicer();
		this.typeObject.updateChoicer();
	}
}
//Khai báo lớp tạo ra giao diện cho lớp này

class PlaceObject extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PLACE_OBJECT = 500;
	public static final int HEIGHT_PLACE_OBJECT = 400;
	private static final int TYPE_ID = 0;
	private static final int TYPE_NAME = 1;
	private static final String STRING_PLACE = "New place...";
	//Khai báo các biến được sử dụng lớp này
	
	JPanel[] panel = new JPanel[7];
	JLabel[] label = new JLabel[4];
	JTextArea[] text = new JTextArea[3];
	JScrollPane[] scroll = new JScrollPane[5];
	JButton[] button = new JButton[4];
	Choice choicer = new Choice();
	String[][] dataArray = new String[2][];
	String[] temple = new String[SqlData.SIZE_ARRAY_PLACE];
	SqlProcessor sqlProcessor;
	//Phương thức khởi tạo
	
	public PlaceObject(SqlProcessor sqlProcessor) {
		this.sqlProcessor = sqlProcessor;
		
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(PlaceAndType.COLOR_BORDER));
		this.setPreferredSize(new Dimension(PlaceObject.WIDTH_PLACE_OBJECT, PlaceObject.HEIGHT_PLACE_OBJECT));
		this.setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.scroll[4], BorderLayout.CENTER);
		this.add(this.panel[6], BorderLayout.SOUTH);
		
		this.handleEvent();
	}
	//Khai báo phương thức khởi tạo các thành phần của lớp này
	
	private void initializeComponent() {
		this.createChoicer();
		
		for(int i = 0; i < this.temple.length; i ++) {
			this.temple[i] = new String();
		}
		
		this.panel[0] = new JPanel();
		this.panel[0].setLayout(new BoxLayout(this.panel[0], BoxLayout.X_AXIS));
		this.panel[0].setBackground(PlaceAndType.COLOR_TITLE_BACKGROUND);
		this.label[0] = new JLabel("Place");
		this.label[0].setFont(PlaceAndType.FONT_TITLE);
		this.label[0].setForeground(PlaceAndType.COLOR_FOREGROUND);
		this.panel[0].add(this.label[0]);
		
		this.panel[1] = new JPanel();
		this.panel[1].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.Y_AXIS));
		this.panel[1].setBorder(BorderFactory.createLineBorder(PlaceAndType.COLOR_BORDER));
		
		this.panel[2] = new JPanel();
		this.panel[2].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.panel[2].setLayout(new BoxLayout(this.panel[2], BoxLayout.X_AXIS));
		this.panel[2].add(this.scroll[3]);
		
		this.panel[3] = new JPanel();
		this.panel[3].setLayout(new BoxLayout(this.panel[3], BoxLayout.X_AXIS));
		this.panel[3].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.label[1] = new JLabel("Name        ");
		this.label[1].setFont(PlaceAndType.FONT_NORMAL);
		this.text[0] = new JTextArea(3, 20);
		this.text[0].setFont(PlaceAndType.FONT_NORMAL);
		this.text[0].setEditable(false);
		this.scroll[0] = new JScrollPane(this.text[0]);
		this.panel[3].add(this.label[1]);
		this.panel[3].add(this.scroll[0]);
		
		this.panel[4] = new JPanel();
		this.panel[4].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.panel[4].setLayout(new BoxLayout(this.panel[4], BoxLayout.X_AXIS));
		this.label[2] = new JLabel("Address     ");
		this.label[2].setFont(PlaceAndType.FONT_NORMAL);
		this.text[1] = new JTextArea(3, 20);
		this.text[1].setFont(PlaceAndType.FONT_NORMAL);
		this.text[1].setEditable(false);
		this.scroll[1] = new JScrollPane(this.text[1]);
		this.panel[4].add(this.label[2]);
		this.panel[4].add(this.scroll[1]);
		
		this.panel[5] = new JPanel();
		this.panel[5].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.panel[5].setLayout(new BoxLayout(this.panel[5], BoxLayout.X_AXIS));
		this.label[3] = new JLabel("Description ");
		this.label[3].setFont(PlaceAndType.FONT_NORMAL);
		this.text[2] = new JTextArea(3, 20);
		this.text[2].setFont(PlaceAndType.FONT_NORMAL);
		this.text[2].setEditable(false);
		this.scroll[2] = new JScrollPane(this.text[2]);
		this.panel[5].add(this.label[3]);
		this.panel[5].add(this.scroll[2]);
		
		this.panel[1].add(this.panel[2]);
		this.panel[1].add(this.panel[3]);
		this.panel[1].add(this.panel[4]);
		this.panel[1].add(this.panel[5]);
		this.scroll[4] = new JScrollPane(this.panel[1]);
		
		this.panel[6] = new JPanel();
		this.setBorder(BorderFactory.createLineBorder(PlaceAndType.COLOR_BORDER));
		this.panel[6].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.button[0] = new JButton("Update");
		this.button[0].setFont(PlaceAndType.FONT_NORMAL);
		this.button[1] = new JButton("Get Information");
		this.button[1].setFont(PlaceAndType.FONT_NORMAL);
		this.button[2] = new JButton("Change");
		this.button[2].setFont(PlaceAndType.FONT_NORMAL);
		this.button[3] = new JButton("Delete");
		this.button[3].setFont(PlaceAndType.FONT_NORMAL);
		//this.button[4] = new JButton("Update");
		//this.panel[6].add(this.button[4]);
		this.panel[6].add(this.button[0]);
		this.panel[6].add(this.button[1]);
		this.panel[6].add(this.button[2]);
		this.panel[6].add(this.button[3]);
		this.button[2].setVisible(false);
		this.button[3].setVisible(false);
	}
	//Phương thức xử lý sự kiện của các thành phần lớp này
	
	private void handleEvent() {
		/*
		this.button[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(button[0].getText().equals("Create")) {
					if(choicer.getSelectedItem().equals(PlaceObject.STRING_PLACE)) {
						System.out.println(choicer.getSelectedItem());
						setStage(true);
						choicer.setEnabled(false);
						button[0].setText("Insert");
						button[1].setVisible(false);
						button[4].setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please choose " + PlaceObject.STRING_PLACE + " to create new place!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					if(text[0].getText().equals("") || text[0].getText().equals(null) || text[1].getText().equals("") || text[1].getText().equals(null)) {
						JOptionPane.showMessageDialog(null, "Please typing on 'Name' and 'Address' field to create new place!", "Note", JOptionPane.WARNING_MESSAGE);
					}
					else {
						if(checkNameValid(StringHandle.changeIntoSpace(text[0].getText()))) {
							setDataTemple();
							if(sqlProcessor.importTable(SqlProcessor.TABLE_PLACE, temple)) {
								JOptionPane.showMessageDialog(null, "Create place success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
							
								updateChoicer();
								button[0].setText("Create");
								button[1].setVisible(true);
								button[4].setVisible(true);
								choicer.setEnabled(true);
								setStage(false);
							}
							else {
								JOptionPane.showMessageDialog(null, "An error occurred!", "Error Message", JOptionPane.ERROR_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Please use another name for this place!", "Note", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});
		*/
		this.button[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(button[1].getText().equals("Get Information")) {
					if(choicer.getSelectedItem().equals(PlaceObject.STRING_PLACE)) {
						JOptionPane.showMessageDialog(null, "Please choose again!", "Note", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						SqlData data = getSqlData(getId(choicer.getSelectedItem()));
						
						button[1].setText("Apply");
						//button[0].setVisible(false);
						choicer.setEnabled(false);
						button[2].setVisible(true);
						button[3].setVisible(true);
						
						text[0].setText(StringHandle.convertString(text[0].getFont(), data.getValueProperties()[SqlData.PLACE_NAME], PlaceObject.WIDTH_PLACE_OBJECT - 80));
						text[1].setText(StringHandle.convertString(text[1].getFont(), data.getValueProperties()[SqlData.PLACE_ADDRESS], PlaceObject.WIDTH_PLACE_OBJECT - 80));
						text[2].setText(StringHandle.convertString(text[2].getFont(), data.getValueProperties()[SqlData.PLACE_DESCRIPTION], PlaceObject.WIDTH_PLACE_OBJECT - 80));
					}
				}
				else {
					button[1].setText("Get Information");
					//button[0].setVisible(true);
					choicer.setEnabled(true);
					
					if(button[2].getText().equals("Update")) {
						button[2].setText("Change");
						setStage(false);
					}
					button[2].setVisible(false);
					button[3].setVisible(false);
					
					updateChoicer();
				}
			}
		});
		
		this.button[2].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(button[2].getText().equals("Change")) {
					if(choicer.getSelectedItem().equals(PlaceObject.STRING_PLACE)) {
						JOptionPane.showMessageDialog(null, "Please choose again!", "Note", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						setStage(true);
						choicer.setEnabled(false);
						button[2].setText("Update");
					}
				}
				else {
					if(text[0].getText().equals("") || text[0].getText().equals(null) || text[1].getText().equals("") || text[1].getText().equals(null)) {
						JOptionPane.showMessageDialog(null, "Please typing fully in 'Name' and 'Address' field to update place!", "Note", JOptionPane.WARNING_MESSAGE);
					}
					else {
						if(checkNameValid(text[0].getText())) {
							setDataTemple();
						
							if(sqlProcessor.updateTable(SqlProcessor.TABLE_PLACE, temple, getId(choicer.getSelectedItem()))) {
								JOptionPane.showMessageDialog(null, "Update place success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
							
								button[2].setText("Change");
								setStage(false);
							}
							else {
								JOptionPane.showMessageDialog(null, "An error occurred!", "Error Message", JOptionPane.ERROR_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "This name of place invalid. Please try again!", "Note", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});
		
		this.button[3].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(choicer.getSelectedItem().equals(PlaceObject.STRING_PLACE)) {
					JOptionPane.showMessageDialog(null, "Please try again!", "Note", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int result = JOptionPane.showConfirmDialog(null, "Do you want delete this place?", "Dialog Confirm", JOptionPane.OK_CANCEL_OPTION);
					
					if(result == JOptionPane.OK_OPTION) {
						if(sqlProcessor.deleteTuple(SqlProcessor.TABLE_PLACE, getId(choicer.getSelectedItem()))) {
							JOptionPane.showMessageDialog(null, "Delete place success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
							updateChoicer();
						}
						else {
							JOptionPane.showMessageDialog(null, "An error occurred!", "Error Message", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		this.button[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				updateChoicer();
			}
		});
	}
	//Phương thức tạo ra choicer
	
	private void createChoicer() {
		ArrayList<SqlData> data = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_PLACE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		
		this.dataArray[PlaceObject.TYPE_ID] = new String[data.size()];
		this.dataArray[PlaceObject.TYPE_NAME] = new String[data.size()];
		
		for(int i = 0; i < data.size(); i ++) {
			this.dataArray[PlaceObject.TYPE_ID][i] = new String(data.get(i).getValueProperties()[SqlData.PLACE_ID]);
			this.dataArray[PlaceObject.TYPE_NAME][i] = new String(data.get(i).getValueProperties()[SqlData.PLACE_NAME]);
		}
		
		this.choicer.add(PlaceObject.STRING_PLACE);
		
		for(int i = 0; i < this.dataArray[PlaceObject.TYPE_NAME].length; i ++) {
			this.choicer.add(this.dataArray[PlaceObject.TYPE_NAME][i]);
		}
		
		this.scroll[3] = new JScrollPane(this.choicer);
	}
	//Phương thức cập nhật lại choicer
	
	public void updateChoicer() {
		ArrayList<SqlData> data = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_PLACE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		
		this.dataArray[PlaceObject.TYPE_ID] = new String[data.size()];
		this.dataArray[PlaceObject.TYPE_NAME] = new String[data.size()];
		
		for(int i = 0; i < data.size(); i ++) {
			this.dataArray[PlaceObject.TYPE_ID][i] = new String(data.get(i).getValueProperties()[SqlData.PLACE_ID]);
			this.dataArray[PlaceObject.TYPE_NAME][i] = new String(data.get(i).getValueProperties()[SqlData.PLACE_NAME]);
		}
		
		this.choicer.removeAll();
		
		this.choicer.add(PlaceObject.STRING_PLACE);
		
		for(int i = 0; i < this.dataArray[PlaceObject.TYPE_NAME].length; i ++) {
			this.choicer.add(this.dataArray[PlaceObject.TYPE_NAME][i]);
		}
	}
	//Phương thức lấy ra Id từ Name
	
	private String getId(String namePlace) {
		String result = "";
		
		for(int i = 0; i < this.dataArray[PlaceObject.TYPE_NAME].length; i ++) {
			if(namePlace.equals(this.dataArray[PlaceObject.TYPE_NAME][i])) {
				result = this.dataArray[PlaceObject.TYPE_ID][i];
				
				break;
			}
		}
		
		return result;
	}
	//Phương thức kiểm tra tính hợp lệ của tên khi ngư�?i dùng muốn thêm vào cơ sở dữ liệu
	
	private boolean checkNameValid(String namePlace) {
		boolean result = true;
		
		for(int i = 0; i < this.dataArray[PlaceObject.TYPE_NAME].length; i ++) {
			if(namePlace.equals(this.choicer.getSelectedItem())) {
				result =  true;
				
				break;
			}
			else if(namePlace.equals(this.dataArray[PlaceObject.TYPE_NAME][i])) {
				result = false;
				
				break;
			}
		}
		
		return result;
	}
	//Phương thức thiết lập dữ liệu cho mảng phụ temple cho việc update, create place
	
	private void setDataTemple() {
		if(!this.text[0].getText().equals("") && !this.text[0].getText().equals(null)) {
			this.temple[SqlData.PLACE_NAME] = StringHandle.changeIntoSpace(this.text[0].getText());
		}
		
		if(!this.text[1].getText().equals("") && !this.text[1].getText().equals(null)) {
			this.temple[SqlData.PLACE_ADDRESS] = StringHandle.changeIntoSpace(this.text[1].getText());
		}
		
		this.temple[SqlData.PLACE_DESCRIPTION] = StringHandle.changeIntoSpace(this.text[2].getText());
	}
	//Phương thức thiết lập trạng thái cho các JTextArea trong lớp này
	
	private void setStage(boolean stage) {
		for(int i = 0; i < this.text.length; i ++) {
			this.text[i].setEditable(stage);
		}
	}
	//Phương thức lấy ra dữ liệu SqlpData từ một Id
	
	private SqlData getSqlData(String IdPlace) {
		SqlData result = null;
		ArrayList<SqlData> data = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_PLACE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		
		for(int i = 0; i < data.size(); i ++) {
			if(data.get(i).getValueProperties()[SqlData.PLACE_ID].equals(IdPlace)) {
				result = data.get(i);
				
				break;
			}
		}
		
		return result;
	}
}
/*
 * Khai báo lớp tạo ra giao diện cho phần thay đổi cho Type của activity
 */

class TypeObject extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_TYPE_OBJECT = 500;
	public static final int HEIGHT_TYPE_OBJECT = 300;
	private static final int TYPE_ID = 0;
	private static final int TYPE_NAME = 1;
	
	private static final String STRING_TYPE = "New type...";
	//Khai báo các biến được sử dụng trong lớp này
	
	JPanel[] panel = new JPanel[6];
	JLabel[] label = new JLabel[3];
	JScrollPane[] scroll = new JScrollPane[4];
	Choice choicer = new Choice();
	JTextArea[] text = new JTextArea[2];
	JButton[] button = new JButton[4];
	SqlProcessor sqlProcessor;
	String[][] temple = new String[2][];
	String[] dataArray = new String[SqlData.SIZE_ARRAY_TYPE];
	//Phương thức khởi tạo
	
	public TypeObject(SqlProcessor sqlProcessor) {
		this.sqlProcessor = sqlProcessor;
		
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.setBorder(BorderFactory.createLineBorder(PlaceAndType.COLOR_BORDER));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.scroll[2], BorderLayout.CENTER);
		this.add(this.panel[5], BorderLayout.SOUTH);
		
		this.handleEvent();
	}
	//Phương thức khởi tạo các thành phần của lớp này
	
	private void initializeComponent() {
		this.initializeChoicer();
		
		this.panel[0] = new JPanel();
		this.panel[0].setLayout(new BoxLayout(this.panel[0], BoxLayout.X_AXIS));
		this.panel[0].setBackground(PlaceAndType.COLOR_TITLE_BACKGROUND);
		this.label[0] = new JLabel("Type");
		this.label[0].setFont(PlaceAndType.FONT_TITLE);
		this.label[0].setForeground(PlaceAndType.COLOR_FOREGROUND);
		this.panel[0].add(this.label[0]);
		
		this.panel[1] = new JPanel();
		this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.Y_AXIS));
		this.panel[1].setBorder(BorderFactory.createLineBorder(PlaceAndType.COLOR_BORDER));
		this.panel[1].setBackground(PlaceAndType.COLOR_BACKGROUND);
		
		this.panel[2] = new JPanel();
		this.panel[2].setLayout(new BoxLayout(this.panel[2], BoxLayout.X_AXIS));
		this.panel[2].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.panel[2].add(this.scroll[3]);
		
		this.panel[3] = new JPanel();
		this.panel[3].setLayout(new BoxLayout(this.panel[3], BoxLayout.X_AXIS));
		this.panel[3].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.label[1] = new JLabel("Name         ");
		this.label[1].setFont(PlaceAndType.FONT_NORMAL);
		this.text[0] = new JTextArea(3, 30);
		this.text[0].setFont(PlaceAndType.FONT_NORMAL);
		this.text[0].setEditable(false);
		this.scroll[0] = new JScrollPane(this.text[0]);
		this.panel[3].add(this.label[1]);
		this.panel[3].add(this.scroll[0]);
		
		this.panel[4] = new JPanel();
		this.panel[4].setLayout(new BoxLayout(this.panel[4], BoxLayout.X_AXIS));
		this.panel[4].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.label[2] = new JLabel("Description  ");
		this.label[2].setFont(PlaceAndType.FONT_NORMAL);
		this.text[1] = new JTextArea(3, 30);
		this.text[1].setFont(PlaceAndType.FONT_NORMAL);
		this.text[1].setEditable(false);
		this.scroll[1] = new JScrollPane(this.text[1]);
		this.panel[4].add(this.label[2]);
		this.panel[4].add(this.scroll[1]);
		
		this.panel[1].add(this.panel[2]);
		this.panel[1].add(this.panel[3]);
		this.panel[1].add(this.panel[4]);
		this.scroll[2] = new JScrollPane(this.panel[1]);
		
		this.panel[5] = new JPanel();
		this.setBorder(BorderFactory.createLineBorder(PlaceAndType.COLOR_BORDER));
		this.panel[5].setBackground(PlaceAndType.COLOR_BACKGROUND);
		this.button[0] = new JButton("Update");
		this.button[0].setFont(PlaceAndType.FONT_NORMAL);
		//this.button[1] = new JButton("Create");
		//this.button[1].setFont(PlaceAndType.FONT_NORMAL);
		this.button[1] = new JButton("Get Information");
		this.button[1].setFont(PlaceAndType.FONT_NORMAL);
		this.button[2] = new JButton("Change");
		this.button[2].setFont(PlaceAndType.FONT_NORMAL);
		this.button[3] = new JButton("Delete");
		this.button[3].setFont(PlaceAndType.FONT_NORMAL);
		for(int i = 0; i < this.button.length; i ++) {
			this.panel[5].add(this.button[i]);
		}
		
		this.button[2].setVisible(false);
		this.button[3].setVisible(false);
	}
	//Phương thức xử lý sự kiện của lớp này
	
	private void handleEvent() {
		this.button[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				updateChoicer();
			}
		});
		/*
		this.button[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(button[1].getText().equals("Create")) {
					if(!choicer.getSelectedItem().equals(TypeObject.STRING_TYPE)) {
						choicer.setEnabled(false);
						setStatus(true);
						button[1].setText("Insert");
						button[2].setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please choose '" + TypeObject.STRING_TYPE + "' to create new type!", "Note", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					if(text[0].getText().equals("") || text[0].getText().equals(null)) {
						JOptionPane.showMessageDialog(null, "Please typing name of type!", "Note", JOptionPane.WARNING_MESSAGE);
					}
					else {
						if(checkNameValid(text[0].getText())) {
							setDataArray();
							if(sqlProcessor.importTable(SqlProcessor.TABLE_TYPE, dataArray)) {
								JOptionPane.showMessageDialog(null, "Create new type success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
								
								button[1].setText("Create");
								setStatus(false);
								updateChoicer();
								choicer.setEnabled(true);
								button[2].setVisible(true);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Please use another name for type!", "Note", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});
		*/
		this.button[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(button[1].getText().equals("Get Information")) {
					if(choicer.getSelectedItem().equals(TypeObject.STRING_TYPE)) {
						JOptionPane.showMessageDialog(null, "Please choose again!", "Note", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						SqlData data = getSqlData(getId(choicer.getSelectedItem()));
						
						button[1].setText("Apply");
						//button[1].setVisible(false);
						choicer.setEnabled(false);
						button[2].setVisible(true);
						button[3].setVisible(true);
						
						text[0].setText(StringHandle.convertString(PlaceAndType.FONT_NORMAL, data.getValueProperties()[SqlData.TYPE_NAME], PlaceObject.WIDTH_PLACE_OBJECT - 80));
						text[1].setText(StringHandle.convertString(PlaceAndType.FONT_NORMAL, data.getValueProperties()[SqlData.TYPE_DESCRIPTION], PlaceObject.WIDTH_PLACE_OBJECT - 80));
					}
				}
				else {
					button[1].setText("Get Information");
					//button[1].setVisible(true);
					choicer.setEnabled(true);
					
					if(button[2].getText().equals("Update")) {
						button[2].setText("Change");
						setStatus(false);
					}
					button[2].setVisible(false);
					button[3].setVisible(false);
					
					updateChoicer();
				}
			}
		});
		
		this.button[2].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(button[2].getText().equals("Change")) {
					if(choicer.getSelectedItem().equals(TypeObject.STRING_TYPE)) {
						JOptionPane.showMessageDialog(null, "Please choose type again!", "Note", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						setStatus(true);
						choicer.setEnabled(false);
						button[2].setText("Update");
					}
				}
				else {
					if(text[0].getText().equals("") || text[0].getText().equals(null)) {
						JOptionPane.showMessageDialog(null, "Please typing fully in 'Name' field to update type!", "Note", JOptionPane.WARNING_MESSAGE);
					}
					else {
						if(checkNameValid(text[0].getText())) {
							setDataArray();
						
							if(sqlProcessor.updateTable(SqlProcessor.TABLE_TYPE, dataArray, getId(choicer.getSelectedItem()))) {
								JOptionPane.showMessageDialog(null, "Update type success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
							
								//updateChoicer();
								//choicer.setEnabled(true);
								button[2].setText("Change");
								setStatus(false);
							}
							else {
								JOptionPane.showMessageDialog(null, "An error occurred!", "Error Message", JOptionPane.ERROR_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "This name of type invalid. Please try again!", "Note", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});
		
		this.button[3].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(choicer.getSelectedItem().equals(TypeObject.STRING_TYPE)) {
					JOptionPane.showMessageDialog(null, "Please try again!", "Note", JOptionPane.WARNING_MESSAGE);
				}
				else {
					int result = JOptionPane.showConfirmDialog(null, "Do you want delete this type?", "Dialog Confirm", JOptionPane.OK_CANCEL_OPTION);
					
					if(result == JOptionPane.OK_OPTION) {
						if(sqlProcessor.deleteTuple(SqlProcessor.TABLE_TYPE, getId(choicer.getSelectedItem()))) {
							JOptionPane.showMessageDialog(null, "Delete type success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
							updateChoicer();
						}
						else {
							JOptionPane.showMessageDialog(null, "An error occurred!", "Error Message", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
	}
	//Phương thức khởi tạo choicer
	
	private void initializeChoicer() {
		ArrayList<SqlData> data = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_TYPE, this.sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		
		this.temple[TypeObject.TYPE_ID] = new String[data.size()];
		this.temple[TypeObject.TYPE_NAME] = new String[data.size()];
		
		this.choicer.add(TypeObject.STRING_TYPE);
		
		for(int i = 0; i < data.size(); i ++) {
			this.temple[TypeObject.TYPE_ID][i] = new String(StringHandle.changeIntoSpace(data.get(i).getValueProperties()[SqlData.TYPE_ID]));
			this.temple[TypeObject.TYPE_NAME][i] = new String(StringHandle.changeIntoSpace(data.get(i).getValueProperties()[SqlData.TYPE_NAME]));
			this.choicer.add(this.temple[TypeObject.TYPE_NAME][i]);
		}
		
		this.scroll[3] = new JScrollPane(this.choicer);
	}
	//Phương thức cập nhạt lại choicer
	
	public void updateChoicer() {
		ArrayList<SqlData> data = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_TYPE, this.sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		
		this.temple[TypeObject.TYPE_ID] = new String[data.size()];
		this.temple[TypeObject.TYPE_NAME] = new String[data.size()];
		
		this.choicer.removeAll();
		
		this.choicer.add(TypeObject.STRING_TYPE);
		
		for(int i = 0; i < data.size(); i ++) {
			this.temple[TypeObject.TYPE_ID][i] = new String(StringHandle.changeIntoSpace(data.get(i).getValueProperties()[SqlData.TYPE_ID]));
			this.temple[TypeObject.TYPE_NAME][i] = new String(StringHandle.changeIntoSpace(data.get(i).getValueProperties()[SqlData.TYPE_NAME]));
			this.choicer.add(this.temple[TypeObject.TYPE_NAME][i]);
		}
	}
	//Phương thức chuyển từ tên sang Id của một type
	
	private String getId(String nameType) {
		String result = "";
		
		for(int i = 0; i < this.temple[TypeObject.TYPE_NAME].length; i ++) {
			if(nameType.equals(this.temple[TypeObject.TYPE_NAME][i])) {
				result = this.temple[TypeObject.TYPE_ID][i];
				
				break;
			}
		}
		
		return result;
	}
	//Phương thức kiểm tra tính hợp lệ của tên của một loại mới
	
	private boolean checkNameValid(String nameType) {
		boolean result = true;
		
		for(int i = 0; i < this.temple[TypeObject.TYPE_NAME].length; i ++) {
			if(nameType.equals(this.choicer.getSelectedItem())) {
				break;
			}
			if(nameType.equals(this.temple[TypeObject.TYPE_NAME][i])) {
				result = false;
			}
		}
		
		return result;
	}
	//Phương thức thiết lập trạng thái của các JTextArea
	
	private void setStatus(boolean status) {
		for(int i = 0; i < this.text.length; i ++) {
			this.text[i].setEditable(status);
		}
	}
	//Phương thức thiết lập mảng dataArray 
	
	private void setDataArray() {
		if(!this.text[0].getText().equals("")) {
			this.dataArray[SqlData.TYPE_NAME] = this.text[0].getText();
			this.dataArray[SqlData.TYPE_DESCRIPTION] = this.text[1].getText();
		}
	}
	//Phương thức lấy ra dữ liệu có kiểu SqlData từ tên của type
	
	private SqlData getSqlData(String idType) {
		ArrayList<SqlData> data = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_TYPE, this.sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		SqlData result = null;
		
		for(int i = 0; i < data.size(); i ++) {
			if(data.get(i).getValueProperties()[SqlData.TYPE_ID].equals(idType)) {
				result = data.get(i);
				
				break;
			}
		}
		
		return result;
	}
}