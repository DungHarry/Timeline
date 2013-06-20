package project1.timeline.component;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import project1.timeline.engine.SqlData;
import project1.timeline.engine.SqlProcessor;
import project1.timeline.engine.XmlProcessor;
import project1.timeline.gui.body.PanelBody;
/*
 * Khai báo lớp tạo ra phần Panel cho phần Option filter dữ liệu trong cơ sở dữ liệu
 */
public class PanelFilter extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_FILTER = 500;
	public static final int HEIGHT_PANEL_FILTER = 700;
	public static final Font FONT_NORMAL = new Font("Times New Roman", Font.PLAIN, 15);
	public static final Font FONT_TITLE = new Font("Times New Roman", Font.BOLD, 25);
	public static final Color COLOR_BACKGROUND = Color.white;
	public static final Color COLOR_TITLE = Color.blue;
	public static final Color COLOR_FOREGROUND = Color.white;
	public static final Color COLOR_BORDER = Color.blue;
	//Khai báo các biến được sử dụng trong lớp này
	
	String[] dataFilter = new String[8];
	SqlProcessor sqlProcessor;
	PanelChooser panelChooser;
	PanelMultiTime timePanel;
	PanelMultiTime timeRegisterPanel;
	PanelRing ringPanel;
	PanelResult panelResult;
	JPanel[] panel = new JPanel[2];
	JButton[] button = new JButton[2];
	PanelBody panelContainer;
	//Phương thức khởi tạo
	
	public PanelFilter(SqlProcessor sqlProcessor, PanelBody panelContainer) {
		this.sqlProcessor = sqlProcessor;
		this.panelContainer = panelContainer;
		
		this.panelChooser = new PanelChooser(this.sqlProcessor, dataFilter);
		this.timePanel = new PanelMultiTime(PanelMultiTime.TYPE_TIME, dataFilter);
		this.timeRegisterPanel = new PanelMultiTime(PanelMultiTime.TYPE_TIME_REGISTER, dataFilter);
		this.ringPanel = new PanelRing(dataFilter);
		this.panelResult = new PanelResult(this.sqlProcessor, this, this.panelContainer);
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(PanelFilter.COLOR_BACKGROUND);
		//this.setPreferredSize(new Dimension(PanelFilter.WIDTH_PANEL_FILTER, PanelFilter.HEIGHT_PANEL_FILTER));
		this.add(this.panelChooser, BorderLayout.NORTH);
		this.add(this.panel[0], BorderLayout.CENTER);
		this.add(this.panel[1], BorderLayout.SOUTH);
		
		this.handleEvent();
	}
	//Phương thức khởi tạo các thành phần cho lớp này
	
	private void initializeComponent() {
		this.initializeData();
		
		this.panel[0] = new JPanel();
		this.panel[0].setLayout(new BorderLayout());
		this.panel[0].setBackground(PanelFilter.COLOR_BACKGROUND);
		this.panel[0].add(this.timePanel, BorderLayout.NORTH);
		this.panel[0].add(this.timeRegisterPanel, BorderLayout.CENTER);
		this.panel[0].add(this.ringPanel, BorderLayout.SOUTH);
		
		this.panel[1] = new JPanel();
		this.panel[1].setBackground(PanelFilter.COLOR_BACKGROUND);
		this.panel[1].setBorder(BorderFactory.createLineBorder(PanelFilter.COLOR_BORDER));
		this.panel[1].setPreferredSize(new Dimension(PanelFilter.WIDTH_PANEL_FILTER, 100));
		this.button[0] = new JButton("Update");
		this.button[1] = new JButton("Filter");
		this.button[0].setFont(PanelFilter.FONT_TITLE);
		this.button[1].setFont(PanelFilter.FONT_TITLE);
		this.panel[1].add(this.button[0]);
		this.panel[1].add(this.button[1]);
	}
	//Phương thức khởi tạo dữ liệu cho dataFilter
	
	private void initializeData() {
		for(int i = 0; i < this.dataFilter.length; i ++) {
			this.dataFilter[i] = new String();
		}
		
		this.dataFilter[SqlProcessor.CONDITION_USER_ID] = this.sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID];
	}
	//Phương thức xử lý sự kiện trong lớp này
	
	private void handleEvent() {
		this.button[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				panelChooser.updateChoicer();
			}
		});
		
		this.button[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(setDataFilter()) {
					panelResult.clickFirst();
					panelContainer.add(panelResult, BorderLayout.CENTER);
					panelContainer.showComponent(PanelBody.TYPE_PANEL_FILTER);
				}
				else {
					panelResult.hideAllComponent();
				}
			}
		});
	}
	//Phương thức thiết lập dữ liệu cho mảng chứa các đi�?u kiện l�?c dữ liệu 
	
	private boolean setDataFilter() {
		boolean result = false;
		
		if(this.panelChooser.setDataFilter() && this.timePanel.setDataFilter(PanelMultiTime.TYPE_TIME) && this.timeRegisterPanel.setDataFilter(PanelMultiTime.TYPE_TIME_REGISTER)) {
			this.ringPanel.setDataFilter();
			
			result = true;
		}
		
		return result;
	}
	//Phương thức lấy ra panelResult
	
	public PanelResult getPanelResult() {
		return this.panelResult;
	}
	//Khai báo phương thức update lại PanelChooser
	
	public void updatePanelChooser() {
		this.initializeData();
		this.panelChooser.updateChoicer();
	}
}
/*
 * Khai báo lớp tạo ra giao diện lựa ch�?n địa điểm(place) và thể loại laoij hoạt động(type)
 * với các thông tin được lấy trực tiếp từ trong cơ sở dư liệu
 */

class PanelChooser extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này 
	
	public static final int WIDTH_PANEL_SELECTION = 500;
	public static final int HEIGHT_PANEL_SELECTION = 100;
	
	private static final int CHOOSER_PLACE = 0;
	private static final int CHOOSER_TYPE = 1;
	private static final int ID = 0;
	private static final int NAME = 1;
	
	private static final String STRING_PLACE = "Choose place...";
	private static final String STRING_TYPE = "Choose type...";
	//Khai báo các biến được sử dụng trong lớp này
	
	JPanel[] panel = new JPanel[2];
	Choice[] choicer = new Choice[2];
	JCheckBox[] checkBox = new JCheckBox[2];
	JLabel title = new JLabel("Type and Place");
	JScrollPane[] scroll = new JScrollPane[2];
	String[] dataFilter;
	String[][][] templeArray = new String[2][2][];
	SqlProcessor sqlProcessor;
	String[] templeId = new String[2];
	//Phương thức khởi tạo
	
	public PanelChooser(SqlProcessor sqlProcessor, String[] dataFilter) {
		this.sqlProcessor = sqlProcessor;
		this.dataFilter = dataFilter;
		
		this.initializeData();
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(PanelFilter.COLOR_BORDER));
		this.setBackground(PanelFilter.COLOR_BACKGROUND);
		this.setPreferredSize(new Dimension(PanelChooser.WIDTH_PANEL_SELECTION, PanelChooser.HEIGHT_PANEL_SELECTION));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.panel[1], BorderLayout.CENTER);
		
		this.handleEvent();
	}
	//Phương thức khởi tạo cho các thành phần đồ h�?a của lớp này
	
	private void initializeComponent() {
		this.createScroll();
		
		this.panel[0] = new JPanel();
		//this.panel[0].setLayout(new BoxLayout(this.panel[0], BoxLayout.X_AXIS));
		this.panel[0].setBackground(PanelFilter.COLOR_TITLE);
		this.title.setFont(PanelFilter.FONT_TITLE);
		this.title.setForeground(PanelFilter.COLOR_FOREGROUND);
		this.panel[0].add(this.title);
		
		this.panel[1] = new JPanel();
		this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.X_AXIS));
		this.panel[1].setBorder(BorderFactory.createLineBorder(PanelFilter.COLOR_BORDER));
		this.panel[1].setBackground(PanelInsertion.COLOR_BACKGROUND);
		this.checkBox[PanelChooser.CHOOSER_PLACE] = new JCheckBox();
		this.panel[1].add(this.checkBox[PanelChooser.CHOOSER_PLACE]);
		this.panel[1].add(this.scroll[PanelChooser.CHOOSER_PLACE]);
		this.checkBox[PanelChooser.CHOOSER_TYPE] = new JCheckBox();
		this.panel[1].add(this.checkBox[PanelChooser.CHOOSER_TYPE]);
		this.panel[1].add(this.scroll[PanelChooser.CHOOSER_TYPE]);
	}
	//Phương thức khởi tạo dữ liệu cho lớp này
	
	private void initializeData() {
		this.templeId[PanelChooser.CHOOSER_PLACE] = "";
		this.templeId[PanelChooser.CHOOSER_TYPE] = "";
	}
	//Phương thức xử lý sự kiện cho các thành phần của lớp này
	
	private void handleEvent() {
		this.checkBox[PanelChooser.CHOOSER_PLACE].addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				changeCheckbox(PanelChooser.CHOOSER_PLACE);
			}
		});
		
		this.checkBox[PanelChooser.CHOOSER_TYPE].addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				changeCheckbox(PanelChooser.CHOOSER_TYPE);
			}
		});
	}
	//Phương thức thiết lập trạng thai của các button tương ứng khi ấn vào checkBox
	
	private void changeCheckbox(int typeChoicer) {
		if(this.checkBox[typeChoicer].isSelected()) {
			this.choicer[typeChoicer].setEnabled(true);
		}
		else {
			this.choicer[typeChoicer].setEnabled(false);
			this.templeId[typeChoicer] = "";
		}
	}
	//Phương thức khởi tạo cho các thành phần choice và gán cho scroll
	
	private void createScroll() {
		ArrayList<SqlData> arrayPlace = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_PLACE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		ArrayList<SqlData> arrayType = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_TYPE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		
		this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.ID] = new String[arrayPlace.size()];
		this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.NAME] = new String[arrayPlace.size()];
		this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.ID] = new String[arrayType.size()];
		this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.NAME] = new String[arrayType.size()];
		
		this.choicer[PanelChooser.CHOOSER_PLACE] = new Choice();
		this.choicer[PanelChooser.CHOOSER_TYPE] = new Choice();
		
		this.choicer[PanelChooser.CHOOSER_PLACE].setPreferredSize(new Dimension(150, 50));
		
		this.choicer[PanelChooser.CHOOSER_PLACE].setBackground(PanelFilter.COLOR_BACKGROUND);
		this.choicer[PanelChooser.CHOOSER_PLACE].setFont(PanelFilter.FONT_NORMAL);
		this.choicer[PanelChooser.CHOOSER_PLACE].setEnabled(false);
		this.choicer[PanelChooser.CHOOSER_TYPE].setBackground(PanelFilter.COLOR_BACKGROUND);
		this.choicer[PanelChooser.CHOOSER_TYPE].setFont(PanelFilter.FONT_NORMAL);
		this.choicer[PanelChooser.CHOOSER_TYPE].setEnabled(false);
		
		this.choicer[PanelChooser.CHOOSER_PLACE].add(PanelChooser.STRING_PLACE);
		this.choicer[PanelChooser.CHOOSER_TYPE].add(PanelChooser.STRING_TYPE);
		
		for(int i = 0; i < arrayPlace.size(); i ++) {
			this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.ID][i] = arrayPlace.get(i).getValueProperties()[SqlData.PLACE_ID];
			this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.NAME][i] = arrayPlace.get(i).getValueProperties()[SqlData.PLACE_NAME];
			
			this.choicer[PanelChooser.CHOOSER_PLACE].add(this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.NAME][i]);
		}
		
		for(int i = 0; i < arrayType.size(); i ++) {
			this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.ID][i] = arrayType.get(i).getValueProperties()[SqlData.TYPE_ID];
			this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.NAME][i] = arrayType.get(i).getValueProperties()[SqlData.TYPE_NAME];
			
			this.choicer[PanelChooser.CHOOSER_TYPE].add(this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.NAME][i]);
		}
		
		this.scroll[PanelChooser.CHOOSER_PLACE] = new JScrollPane(this.choicer[PanelChooser.CHOOSER_PLACE]);
		this.scroll[PanelChooser.CHOOSER_TYPE] = new JScrollPane(this.choicer[PanelChooser.CHOOSER_TYPE]);
	}
	//Phương thức cập nhật lại các giá trị của choicer
	
	public void updateChoicer() {
		ArrayList<SqlData> arrayPlace = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_PLACE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		ArrayList<SqlData> arrayType = this.sqlProcessor.exportSelectionInformation(SqlProcessor.TABLE_TYPE, sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID]);
		
		this.choicer[PanelChooser.CHOOSER_PLACE].removeAll();
		this.choicer[PanelChooser.CHOOSER_TYPE].removeAll();
		
		this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.ID] = new String[arrayPlace.size()];
		this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.NAME] = new String[arrayPlace.size()];
		this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.ID] = new String[arrayType.size()];
		this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.NAME] = new String[arrayType.size()];
		
		this.choicer[PanelChooser.CHOOSER_PLACE].add(PanelChooser.STRING_PLACE);
		this.choicer[PanelChooser.CHOOSER_TYPE].add(PanelChooser.STRING_TYPE);
		
		for(int i = 0; i < arrayPlace.size(); i ++) {
			this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.ID][i] = arrayPlace.get(i).getValueProperties()[SqlData.PLACE_ID];
			this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.NAME][i] = arrayPlace.get(i).getValueProperties()[SqlData.PLACE_NAME];
			
			this.choicer[PanelChooser.CHOOSER_PLACE].add(this.templeArray[PanelChooser.CHOOSER_PLACE][PanelChooser.NAME][i]);
		}
		
		for(int i = 0; i < arrayType.size(); i ++) {
			this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.ID][i] = arrayType.get(i).getValueProperties()[SqlData.TYPE_ID];
			this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.NAME][i] = arrayType.get(i).getValueProperties()[SqlData.TYPE_NAME];
			
			this.choicer[PanelChooser.CHOOSER_TYPE].add(this.templeArray[PanelChooser.CHOOSER_TYPE][PanelChooser.NAME][i]);
		}
	}
	//Phương thức lấy ra Id của place hoặc type tương ứng với tên tham số truy�?n vào 
	
	private void getIdChoicer(int typeChoicer, String name) {
		if(this.checkBox[typeChoicer].isSelected()) {
			for(int i = 0; i < this.templeArray[typeChoicer][PanelChooser.NAME].length; i ++) {
				if(this.templeArray[typeChoicer][PanelChooser.NAME][i].equals(name)) {
					this.templeId[typeChoicer] = this.templeArray[typeChoicer][PanelChooser.ID][i];
					
					break;
				}
			}
		}
	}
	//Phương thức thiết lập dữ liệu cho mảng dataFilter: trả lại true nếu th�?a mãn các đi�?u kiện và false nếu không th�?a mãn
	
	public boolean setDataFilter() {
		boolean firstResult = false;
		boolean secondResult = false;
		
		if(this.checkBox[PanelChooser.CHOOSER_PLACE].isSelected()) {
			this.getIdChoicer(PanelChooser.CHOOSER_PLACE, this.choicer[PanelChooser.CHOOSER_PLACE].getSelectedItem());

			if(this.choicer[PanelChooser.CHOOSER_PLACE].getSelectedItem().equals(STRING_PLACE)) {
				JOptionPane.showMessageDialog(null, "Please choose place again!", "Note", JOptionPane.WARNING_MESSAGE);
			}
			else {
				firstResult = true;
				this.dataFilter[SqlProcessor.CONDITION_PLACE_ID] = this.templeId[PanelChooser.CHOOSER_PLACE];
			}
		}
		else {
			firstResult = true;
			this.dataFilter[SqlProcessor.CONDITION_PLACE_ID] = this.templeId[PanelChooser.CHOOSER_PLACE];
		}
		
		if(this.checkBox[PanelChooser.CHOOSER_TYPE].isSelected()) {
			this.getIdChoicer(PanelChooser.CHOOSER_TYPE, this.choicer[PanelChooser.CHOOSER_TYPE].getSelectedItem());

			if(this.choicer[PanelChooser.CHOOSER_TYPE].getSelectedItem().equals(STRING_TYPE)) {
				JOptionPane.showMessageDialog(null, "Please choose place again!", "Note", JOptionPane.WARNING_MESSAGE);
			}
			else {
				secondResult = true;
				this.dataFilter[SqlProcessor.CONDITION_TYPE_ID] = this.templeId[PanelChooser.CHOOSER_TYPE];
			}
		}
		else {
			secondResult = true;
			this.dataFilter[SqlProcessor.CONDITION_TYPE_ID] = this.templeId[PanelChooser.CHOOSER_TYPE];
		}
		
		return firstResult && secondResult;
	}
}
/*
 * Khai báo lớp nhập dữ liệu cho giá trị của th�?i gian, có thể là th�?i gian đăng ký hoặc th�?i gian của hoạt động
 */

class PanelMultiTime extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_TIME = 500;
	public static final int HEIGHT_PANEL_TIME = 200;
	
	public static final int TYPE_TIME = 0;
	public static final int TYPE_TIME_REGISTER = 1;
	//Khai báo các biến được sử dụng trong lớp này
	
	int type;
	JPanel[] panel = new JPanel[6];
	JCheckBox checkBox = new JCheckBox();
	JLabel[] label = new JLabel[4];
	Choice[] choicer = new Choice[12];
	JScrollPane[] scroll = new JScrollPane[12];
	String[] dataFilter;
	//Phương thức khởi tạo đối tượng của lớp này
	
	public PanelMultiTime(int type, String[] dataFilter) {
		this.type = type;
		this.dataFilter = dataFilter;
		
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(PanelFilter.COLOR_BACKGROUND);
		this.setBorder(BorderFactory.createLineBorder(PanelFilter.COLOR_BORDER));
		this.setPreferredSize(new Dimension(PanelMultiTime.WIDTH_PANEL_TIME, PanelMultiTime.HEIGHT_PANEL_TIME));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.panel[1], BorderLayout.CENTER);
		
		this.handleEvent();
	}
	//Phương thức khởi tạo cho các đối tượng đồ h�?a của lớp này
	
	private void initializeComponent() {
		this.createChoicer();
		
		this.panel[0] = new JPanel();
		this.panel[0].setBackground(PanelFilter.COLOR_TITLE);
		if(this.type == PanelMultiTime.TYPE_TIME) {
			this.label[0] = new JLabel("Time");
			this.label[1] = new JLabel("You want filter follow time of activity ");
		}
		else {
			this.label[0] = new JLabel("Time Register");
			this.label[1] = new JLabel("You want filter follow time register ");
		}
		this.label[0].setForeground(PanelFilter.COLOR_FOREGROUND);
		this.label[0].setFont(PanelFilter.FONT_TITLE);
		this.panel[0].add(this.label[0]);
		
		this.panel[1] = new JPanel();
		this.panel[1].setLayout(new BorderLayout());
		this.panel[1].setBackground(PanelFilter.COLOR_BACKGROUND);
		this.panel[1].setBorder(BorderFactory.createLineBorder(PanelFilter.COLOR_BORDER));
		
		this.panel[2] = new JPanel();
		this.panel[2].setLayout(new BoxLayout(this.panel[2], BoxLayout.X_AXIS));
		this.panel[2].setBackground(PanelFilter.COLOR_BACKGROUND);
		this.label[1].setFont(PanelFilter.FONT_NORMAL);
		this.panel[2].add(this.label[1]);
		this.panel[2].add(this.checkBox);
		this.checkBox.setSelected(true);
		
		this.panel[5] = new JPanel();
		this.panel[5].setLayout(new BoxLayout(this.panel[5], BoxLayout.Y_AXIS));
		this.panel[5].setBackground(PanelFilter.COLOR_BACKGROUND);
		
		this.panel[3] = new JPanel();
		this.panel[3].setLayout(new BoxLayout(this.panel[3], BoxLayout.X_AXIS));
		this.panel[3].setBackground(PanelFilter.COLOR_BACKGROUND);
		this.label[2] = new JLabel("From");
		this.label[2].setFont(PanelFilter.FONT_NORMAL);
		this.panel[3].add(this.label[2]);
		for(int i = 0; i < 6; i ++) {
			this.panel[3].add(this.scroll[i]);
		}
		
		this.panel[4] = new JPanel();
		this.panel[4].setLayout(new BoxLayout(this.panel[4], BoxLayout.X_AXIS));
		this.panel[4].setBackground(PanelFilter.COLOR_BACKGROUND);
		this.label[3] = new JLabel("To    ");
		this.label[3].setFont(PanelFilter.FONT_NORMAL);
		this.panel[4].add(this.label[3]);
		for(int i = 6; i < 12; i ++) {
			this.panel[4].add(this.scroll[i]);
		}
		
		this.panel[5].add(this.panel[3]);
		this.panel[5].add(this.panel[4]);
		
		this.panel[1].add(this.panel[2], BorderLayout.NORTH);
		this.panel[1].add(this.panel[5], BorderLayout.CENTER);
	}
	//Phương thức khởi tạo các choicer và thêm chúng vào các scroll
	
	private void createChoicer() {
		for(int i = 0; i < this.choicer.length; i ++) {
			this.choicer[i] = new Choice();
			this.scroll[i] = new JScrollPane(this.choicer[i]);
		}
		
		this.choicer[0].add("Year");
		this.choicer[1].add("Month");
		this.choicer[2].add("Day");
		this.choicer[3].add("Hour");
		this.choicer[4].add("Minute");
		this.choicer[5].add("Seconds");
		
		this.choicer[6].add("Year");
		this.choicer[7].add("Month");
		this.choicer[8].add("Day");
		this.choicer[9].add("Hour");
		this.choicer[10].add("Minute");
		this.choicer[11].add("Seconds");
		
		for(int i = 1992; i < 2051; i ++) {
			this.choicer[0].add(Integer.toString(i));
			this.choicer[6].add(Integer.toString(i));
		}
		
		for(int i = 1; i < 13; i ++) {
			this.choicer[1].add(Integer.toString(i));
			this.choicer[7].add(Integer.toString(i));
		}
		
		for(int i = 1; i < 32; i ++) {
			this.choicer[2].add(Integer.toString(i));
			this.choicer[8].add(Integer.toString(i));
		}
		
		for(int i = 0; i < 24; i ++) {
			this.choicer[3].add(Integer.toString(i));
			this.choicer[9].add(Integer.toString(i));
		}
		
		for(int i = 0; i < 60; i ++) {
			this.choicer[4].add(Integer.toString(i));
			this.choicer[10].add(Integer.toString(i));
			this.choicer[5].add(Integer.toString(i));
			this.choicer[11].add(Integer.toString(i));
		}
	}
	//Hàm xử lý sự kiện cho các đối tượng của lớp này
	
	private void handleEvent() {
		this.checkBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				changeCheckbox(checkBox.isSelected());
			}
			
		});
	}
	//Phương thức xử lý khi ngư�?i dùng thay đổi trạng thái của checkBox
	
	private void changeCheckbox(boolean status) {
		for(int i = 0; i < this.choicer.length; i ++) {
			this.choicer[i].setEnabled(status);
		}
		
		if(this.type == PanelMultiTime.TYPE_TIME) {
			this.dataFilter[SqlProcessor.CONDITION_TIME_BEGIN] = "";
			this.dataFilter[SqlProcessor.CONDITION_TIME_END] = "";
		}
		else if(this.type == PanelMultiTime.TYPE_TIME_REGISTER) {
			this.dataFilter[SqlProcessor.CONDITION_TIMEREGISTER_BEGIN] = "";
			this.dataFilter[SqlProcessor.CONDITION_TIMEREGISTER_END] = "";
		}
	}
	//Phương thức thiết lập dữ liệu cho mảng dataFilter
	
	public boolean setDataFilter(int type) {
		boolean result = false;
		
		if(this.checkBox.isSelected()) {
			if(this.choicer[0].getSelectedItem().equals("Year") || this.choicer[1].getSelectedItem().equals("Month") || this.choicer[2].getSelectedItem().equals("Day")) {
				JOptionPane.showMessageDialog(null, "Please choose source time again!", "Note", JOptionPane.WARNING_MESSAGE);
			}
			else if(this.choicer[6].getSelectedItem().equals("Year") || this.choicer[7].getSelectedItem().equals("Month") || this.choicer[8].getSelectedItem().equals("Day")) {
				JOptionPane.showMessageDialog(null, "Please choose destination time again!", "Note", JOptionPane.WARNING_MESSAGE);
			}
			else {
				result = true;
				
				this.moveDataFilter(type);
			}
		}
		else {
			result = true;
		}
		
		return result;
	}
	//Phương thức chuyển dữ liệu vào mảng dataFilter
	
	private void moveDataFilter(int type) {
		String templeSource = "";
		String templeDetination = "";
		
		if(this.checkBox.isSelected()) {
		
			templeSource = this.choicer[0].getSelectedItem() + "-" + this.choicer[1].getSelectedItem() + "-" + this.choicer[2].getSelectedItem() + " "; 
		
			if(this.choicer[3].getSelectedItem().equals("Hour")) {
				templeSource += "0:";
			}
			else {
				templeSource += this.choicer[3].getSelectedItem() + ":";
			}
		
			if(this.choicer[4].getSelectedItem().equals("Minute")) {
				templeSource += "0:";
			}
			else {
				templeSource += this.choicer[4].getSelectedItem() + ":";
			}
		
			if(this.choicer[5].getSelectedItem().equals("Seconds")) {
				templeSource += "0";
			}
			else {
				templeSource += this.choicer[5].getSelectedItem();
			}
			
			templeDetination = this.choicer[6].getSelectedItem() + "-" + this.choicer[7].getSelectedItem() + "-" + this.choicer[8].getSelectedItem() + " "; 
		
			if(this.choicer[9].getSelectedItem().equals("Hour")) {
				templeDetination += "0:";
			}
			else {
				templeDetination += this.choicer[9].getSelectedItem() + ":";
			}
		
			if(this.choicer[10].getSelectedItem().equals("Minute")) {
				templeDetination += "0:";
			}
			else {
				templeDetination += this.choicer[10].getSelectedItem() + ":";
			}
		
			if(this.choicer[11].getSelectedItem().equals("Seconds")) {
				templeDetination += "0";
			}
			else {
				templeDetination += this.choicer[11].getSelectedItem();
			}
		
			if(type == PanelMultiTime.TYPE_TIME) {
				this.dataFilter[SqlProcessor.CONDITION_TIME_BEGIN] = templeSource;
				this.dataFilter[SqlProcessor.CONDITION_TIME_END] = templeDetination;
			}
		
			if(type == PanelMultiTime.TYPE_TIME_REGISTER) {
				this.dataFilter[SqlProcessor.CONDITION_TIMEREGISTER_BEGIN] = templeSource;
				this.dataFilter[SqlProcessor.CONDITION_TIMEREGISTER_END] = templeDetination;
			}
		}
	}
}
/*
 * Khai báo lớp tạo ra giao diện phần ch�?n ring cho Option filter 
 */

class PanelRing extends JPanel {
	//Khai báo các hằng số sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_RING = 500;
	public static final int HEIGHT_PANEL_RING = 100;
	//Khai báo các biến được sử dụng trong lớp này
	
	JLabel[] label = new JLabel[2];
	JCheckBox[] checkBox = new JCheckBox[2];
	JPanel[] panel = new JPanel[2];
	String[] dataFilter;
	//Hàm khởi tạo của lớp này
	
	public PanelRing(String[] dataFilter) {
		this.dataFilter = dataFilter;
		
		this.createComponent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(PanelFilter.COLOR_BACKGROUND);
		this.setBorder(BorderFactory.createLineBorder(PanelFilter.COLOR_BORDER));
		this.setPreferredSize(new Dimension(PanelRing.WIDTH_PANEL_RING, PanelRing.HEIGHT_PANEL_RING));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.panel[1], BorderLayout.CENTER);
		
		this.handleEvent();
	}
	//Phương thức khởi tạo các thành phần cho đối tượng của lớp này
	
	private void createComponent() {
		this.panel[0] = new JPanel();
		this.panel[0].setBackground(PanelFilter.COLOR_TITLE);
		this.panel[0].setBorder(BorderFactory.createLineBorder(PanelFilter.COLOR_BORDER));
		this.label[0] = new JLabel("Ring");
		this.label[0].setFont(PanelFilter.FONT_TITLE);
		this.label[0].setForeground(PanelFilter.COLOR_FOREGROUND);
		this.panel[0].add(this.label[0]);
		
		this.panel[1] = new JPanel();
		this.panel[1].setBackground(PanelFilter.COLOR_BACKGROUND);
		this.panel[1].setBorder(BorderFactory.createLineBorder(PanelFilter.COLOR_BORDER));
		this.label[1] = new JLabel("                        ");
		this.checkBox[0] = new JCheckBox("Both");
		this.checkBox[1] = new JCheckBox("Ring");
		this.label[1].setFont(PanelFilter.FONT_NORMAL);
		this.panel[1].add(this.checkBox[0]);
		this.panel[1].add(this.label[1]);
		this.panel[1].add(this.checkBox[1]);
	}
	//Hàm thiết lập mảng dataFilter
	
	public void setDataFilter() {
		if(!this.checkBox[0].isSelected()) {
			if(this.checkBox[1].isSelected()) {
				this.dataFilter[SqlProcessor.CONDITION_RINGSTATUS] = "1";
			}
			else {
				this.dataFilter[SqlProcessor.CONDITION_RINGSTATUS] = "0";
			}
		}
		else {
			this.dataFilter[SqlProcessor.CONDITION_RINGSTATUS] = "";
		}
	}
	//Hàm xử lý sự kiện cho lớp này
	
	private void handleEvent() {
		this.checkBox[0].addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(checkBox[0].isSelected()) {
					checkBox[1].setEnabled(false);
				}
				else {
					checkBox[1].setEnabled(true);
				}
			}
			
		});
	}
}

