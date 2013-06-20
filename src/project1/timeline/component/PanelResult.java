package project1.timeline.component;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import project1.timeline.engine.SqlData;
import project1.timeline.engine.SqlDataTransport;
import project1.timeline.engine.SqlProcessor;
import project1.timeline.engine.StringHandle;
import project1.timeline.gui.body.PanelBody;

/*
 * Khai báo lớp chứa tất cả các thông tin v�? kết quả của quá trình l�?c dữ liệu từ cơ dữ liệu 
 */
public class PanelResult extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_RESULT = 720;
	public static final int HEIGHT_PANEL_RESULT = 700;
	public static final Color COLOR_BACKGROUND = Color.white;
	public static final Color COLOR_FOREGROUND = Color.blue;
	public static final Color COLOR_TITLE = Color.white;
	public static final Color COLOR_BORDER = Color.blue;
	public static final Font FONT_TITLE = new Font("Times New Roman", Font.BOLD, 25);
	public static final Font FONT_NORMAL = new Font("Times New Roman", Font.PLAIN, 15);
	public static final Font FONT_SUB_TITLE = new Font("Times New Roman", Font.PLAIN, 20);
	//Khai báo các biến được sử dụng trong lớp này
	
	SqlDataTransport[] dataArray;
	PanelFilter panelFilter;
	JButton[] button;
	JPanel[] panel = new JPanel[2];
	PiecePanel[] pieceStatic = new PiecePanel[SqlProcessor.SIZE_RESULT_SET];
	PiecePanel[] pieceDynamic;
	SqlProcessor sqlProcessor;
	JScrollPane[] scroll = new JScrollPane[2];
	PanelBody panelBody;
	//Phương thức khởi tạo của lớp PanelResult
	
	public PanelResult(SqlProcessor sqlProcessor, PanelFilter panelFilter, PanelBody panelBody) {
		this.sqlProcessor = sqlProcessor;
		this.panelFilter = panelFilter;
		this.panelBody = panelBody;
		
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.setBackground(PanelResult.COLOR_BACKGROUND);
		//this.setPreferredSize(new Dimension(PanelResult.WIDTH_PANEL_RESULT, PanelResult.HEIGHT_PANEL_RESULT));
		this.add(this.scroll[0], BorderLayout.CENTER);
		this.add(this.scroll[1], BorderLayout.SOUTH);
	}
	//Phương thức khởi tạo các thành phần của lớp này
	
	private void initializeComponent() {
		this.panel[0] = new JPanel();
		this.panel[0].setLayout(new BoxLayout(this.panel[0], BoxLayout.Y_AXIS));
		this.panel[0].setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.panel[0].setBackground(PanelResult.COLOR_BACKGROUND);
		this.scroll[0] = new JScrollPane(this.panel[0]);
		
		this.panel[1] = new JPanel();
		this.panel[1].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[1].setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.X_AXIS));
		this.scroll[1] = new JScrollPane(this.panel[1]);
	}
	//Phương thức kiểm tra kết quả một số có phải bội của value hay không
	
	private boolean checkNumber(int number, int value) {
		boolean result = false;
		
		if((double) (number / value) == ((int) (number / value))) {
			result = true;
		}
		
		return result;
	}
	//Phương thức tính toán tạo ra một mảng các button
	
	private void createButton() {
		this.button = new JButton[(int) (this.sqlProcessor.countTupleSelectClause(this.panelFilter.dataFilter) / SqlProcessor.SIZE_RESULT_SET) + 1];
		
		for(int i = 0; i < this.button.length; i ++) {
			this.button[i] = new JButton(Integer.toString(i + 1));
			this.button[i].setBackground(Color.gray);
			this.button[i].setForeground(Color.black);
			this.button[i].setFont(PanelResult.FONT_NORMAL);
			this.button[i].setVisible(true);
			this.panel[1].add(this.button[i]);
		}
	}
	//Phương thức xử lý sự kiện của lớp này
	
	private void handleEvent() {
		
		for(int i = 0; i < this.button.length; i ++) {
			this.button[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					doSomething(Integer.parseInt(((JButton) e.getSource()).getText()) - 1);
					createEffect(Integer.parseInt(((JButton) e.getSource()).getText()) - 1);
				}
			});
		}
	}
	//Khai báo phương thức cho để giao tiếp với ngư�?i dùng khi lần đầu click vào nút filter trong PanelFilter
	
	public void clickFirst() {
		if(this.button == null) {
			this.createButton();
		}
		else {
			for(int i = 0; i < this.button.length; i ++) {
				this.button[i].setVisible(false);
			}
			this.createButton();
		}
		
		this.doSomething(0);
		this.handleEvent();
	}
	//Phương thức tạo hiệu ứng khi click vào một button
	
	private void createEffect(int index) {
		for(int i = 0; i < this.button.length; i ++) {
			if(i == index) {
				this.button[i].setBackground(Color.blue);
			}
			else {
				this.button[i].setBackground(Color.gray);
			}
		}
	}
	//Phương thức xử lý khi một nút bấm được click
	
	public void doSomething(int index) {
		this.dataArray = this.sqlProcessor.exportFromMySQL(index * 10, this.panelFilter.dataFilter);
		System.out.println(this.dataArray.length);
		if(this.dataArray.length == SqlProcessor.SIZE_RESULT_SET) {
			if(this.pieceStatic[0] == null) {
				if(this.pieceDynamic != null) {
					for(int i = 0; i < this.pieceDynamic.length; i ++) {
						this.pieceDynamic[i].setVisible(false);
					}
				}
				
				for(int i = 0; i < this.dataArray.length; i ++) {
					this.pieceStatic[i] = new PiecePanel(this.sqlProcessor, this.dataArray[i], this.panelBody);
					this.panel[0].add(this.pieceStatic[i]);
					this.pieceStatic[i].setVisible(true);
				}
			}
			else {
				if(!(this.pieceDynamic == null)) {
					for(int i = 0; i < this.pieceDynamic.length; i ++) {
						this.pieceDynamic[i].setVisible(false);
					}
				}
				for(int i = 0; i < this.dataArray.length; i ++) {
					this.pieceStatic[i].updatePieceResult(this.dataArray[i]);
				}
			}
		}
		else {
			if(!(this.pieceStatic[0] == null)) {
				this.hidePieceStatic();
			}
			if(!(this.pieceDynamic == null)) {
				for(int i = 0; i < this.pieceDynamic.length; i ++) {
					this.pieceDynamic[i].setVisible(false);
				}
			}
			this.pieceDynamic = new PiecePanel[this.dataArray.length];
			
			for(int i = 0; i < this.pieceDynamic.length; i ++) {
				this.pieceDynamic[i] = new PiecePanel(this.sqlProcessor, this.dataArray[i], this.panelBody);
				this.panel[0].add(this.pieceDynamic[i]);
				this.pieceDynamic[i].setVisible(true);
			}
		}
		
		this.setVisible(true);
	}
	//Phương thức ẩn đi tất cả các PiecePanel có trong PanelResult
	
	private void hidePieceStatic() {
		for(int i = 0; i < this.pieceStatic.length; i ++) {
			this.pieceStatic[i].setVisible(false);
		}
	}
	//Phương thức ẩn đi toàn bộ các tành phần có trong cả hai panel: panel[0] và panel[1]
	
	public void hideAllComponent() {
		if(this.button != null) {
			for(int  i = 0; i < this.button.length; i ++) {
				this.button[i].setVisible(false);
			}
		}
		if(this.pieceDynamic != null) {
			for(int i = 0; i < this.button.length; i ++) {
				this.pieceDynamic[i].setVisible(false);
			}
		}
		if(this.pieceStatic[0] != null) {
			for(int i = 0; i < this.pieceStatic.length; i ++) {
				this.pieceStatic[i].setVisible(false);
			}
		}
	}
}
/*
 * Khai báo lớp tạo ra giao diện cho một thẻ trình bày thông tin của kết quả l�?c được từ cơ sở dữ liệu
 */

class PiecePanel extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	private static final int WIDTH_PANEL_PIECE = 720;
	private static final int HEIGHT_PANEL_PIECE = 400;
	private static final int PADDING_HORIZONTAL = 60;
	private static final int PADDING_VERTICALLY = 25;
	private static final int HEIGHT_MAIN_PANEL = HEIGHT_PANEL_PIECE - 2 * PADDING_VERTICALLY;
	private static final int WIDTH_MAIN_PANEL = WIDTH_PANEL_PIECE - 2 * PADDING_HORIZONTAL;
	//Khai báo các hằng số được sử dụng trong lớp này
	
	SqlProcessor sqlProcessor;
	SqlDataTransport data;
	JPanel[] panel = new JPanel[16];
	JLabel[] label = new JLabel[9];
	JButton[] button = new JButton[3];
	String[] multiString = new String[5];
	JCheckBox checkBox = new JCheckBox("Ring");
	JTextArea[] textArea = new JTextArea[7];
	Choice[] choicer = new Choice[6];
	JScrollPane[] scroll = new JScrollPane[14];
	boolean flag = true;
	PanelBody panelBody;
	//Phương thức khởi tạo
	
	public PiecePanel(SqlProcessor sqlProcessor, SqlDataTransport data, PanelBody panelBody) {
		this.sqlProcessor = sqlProcessor;
		this.data = data;
		this.panelBody = panelBody;
		
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(PanelResult.COLOR_BACKGROUND);
		this.setPreferredSize(new Dimension(PiecePanel.WIDTH_PANEL_PIECE, PiecePanel.HEIGHT_PANEL_PIECE));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.panel[1], BorderLayout.SOUTH);
		this.add(this.panel[2], BorderLayout.WEST);
		this.add(this.panel[3], BorderLayout.EAST);
		this.add(this.panel[4], BorderLayout.CENTER);
		
		this.handleEvent();
	}
	//Phương thức khởi tạo các thành phần của giao diện chương trình
	
	private void initializeComponent() {
		this.createChoicer();
		
		this.panel[0] = new JPanel();
		this.panel[0].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[0].setPreferredSize(new Dimension(PiecePanel.WIDTH_PANEL_PIECE, PiecePanel.PADDING_VERTICALLY));
		
		this.panel[1] = new JPanel();
		this.panel[1].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[1].setPreferredSize(new Dimension(PiecePanel.WIDTH_PANEL_PIECE, PiecePanel.PADDING_VERTICALLY));
		
		this.panel[2] = new JPanel();
		this.panel[2].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[2].setPreferredSize(new Dimension(PiecePanel.PADDING_HORIZONTAL, PiecePanel.HEIGHT_MAIN_PANEL));
		
		this.panel[3] = new JPanel();
		this.panel[3].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[3].setPreferredSize(new Dimension(PiecePanel.PADDING_HORIZONTAL, PiecePanel.HEIGHT_MAIN_PANEL));
		
		this.panel[4] = new JPanel();
		this.panel[4].setLayout(new BorderLayout());
		this.panel[4].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[4].setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.panel[4].setPreferredSize(new Dimension(PiecePanel.WIDTH_MAIN_PANEL, PiecePanel.HEIGHT_MAIN_PANEL));
		
		this.panel[5] = new JPanel();
		this.panel[5].setLayout(new BoxLayout(this.panel[5], BoxLayout.X_AXIS));
		this.panel[5].setBackground(PanelResult.COLOR_FOREGROUND);
		this.label[0] = new JLabel("Result");
		this.label[0].setFont(PanelResult.FONT_TITLE);
		this.label[0].setForeground(PanelResult.COLOR_TITLE);
		this.panel[5].add(this.label[0]);
		
		this.panel[6] = new JPanel();
		this.panel[6].setLayout(new BoxLayout(this.panel[6], BoxLayout.Y_AXIS));
		this.panel[6].setBackground(PanelResult.COLOR_BACKGROUND);
		this.scroll[8] = new JScrollPane(this.panel[6]);
		
		this.panel[7] = new JPanel();
		//this.panel[7].setLayout(new BoxLayout(this.panel[7], BoxLayout.X_AXIS));
		this.panel[7].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[7].setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.panel[7].setPreferredSize(new Dimension(PiecePanel.WIDTH_MAIN_PANEL, 50));
		this.label[1] = new JLabel("Time Register: ");
		this.label[1].setFont(PanelResult.FONT_SUB_TITLE);
		this.label[2] = new JLabel(this.data.getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_TIMEREGISTER]);
		this.label[2].setFont(PanelResult.FONT_NORMAL);
		this.panel[7].add(this.label[1]);
		this.panel[7].add(this.label[2]);
		
		this.panel[8] = new JPanel();
		this.panel[8].setLayout(new BoxLayout(this.panel[8], BoxLayout.Y_AXIS));
		this.panel[8].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[8].setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.label[3] = new JLabel("Type");
		this.label[3].setFont(PanelResult.FONT_SUB_TITLE);
		this.multiString[0] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Name: " + this.data.getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_NAME], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.multiString[1] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Description: " + this.data.getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_DESCRIPTION], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.textArea[2] = new JTextArea(2, 30);
		this.textArea[2].setFont(PanelResult.FONT_NORMAL);
		this.textArea[2].setText(this.multiString[0]);
		this.textArea[2].setEditable(false);
		this.scroll[9] = new JScrollPane(this.textArea[2]);
		this.textArea[3] = new JTextArea(3, 30);
		this.textArea[3].setFont(PanelResult.FONT_NORMAL);
		this.textArea[3].setText(this.multiString[1]);
		this.textArea[3].setEditable(false);
		this.scroll[10] = new JScrollPane(this.textArea[3]);
		this.panel[8].add(this.label[3]);
		this.panel[8].add(this.scroll[9]);
		this.panel[8].add(this.scroll[10]);
		
		
		this.panel[9] = new JPanel();
		this.panel[9].setLayout(new BoxLayout(this.panel[9], BoxLayout.Y_AXIS));
		this.panel[9].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[9].setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.label[4] = new JLabel("Place");
		this.label[4].setFont(PanelResult.FONT_SUB_TITLE);
		this.multiString[2] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Name: " + this.data.getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_NAME], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.multiString[3] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Address: " + this.data.getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_ADDRESS], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.multiString[4] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Description: " + this.data.getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_DESCRIPTION], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.textArea[4] = new JTextArea(2, 30);
		this.textArea[4].setFont(PanelResult.FONT_NORMAL);
		this.textArea[4].setText(this.multiString[2]);
		this.textArea[4].setEditable(false);
		this.scroll[11] = new JScrollPane(this.textArea[4]);
		this.textArea[5] = new JTextArea(3, 30);
		this.textArea[5].setFont(PanelResult.FONT_NORMAL);
		this.textArea[5].setText(this.multiString[3]);
		this.textArea[5].setEditable(false);
		this.scroll[12] = new JScrollPane(this.textArea[5]);
		this.textArea[6] = new JTextArea(3, 30);
		this.textArea[6].setFont(PanelResult.FONT_NORMAL);
		this.textArea[6].setText(this.multiString[4]);
		this.textArea[6].setEditable(false);
		this.scroll[13] = new JScrollPane(this.textArea[6]);
		this.panel[9].add(this.label[4]);
		this.panel[9].add(this.scroll[11]);
		this.panel[9].add(this.scroll[12]);
		this.panel[9].add(this.scroll[13]);
		
		this.panel[10] = new JPanel();
		this.panel[10].setLayout(new BoxLayout(this.panel[10], BoxLayout.Y_AXIS));
		this.panel[10].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[10].setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.label[5] = new JLabel("Time: " + this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_TIME]);
		this.label[5].setFont(PanelResult.FONT_SUB_TITLE);
		this.panel[13] = new JPanel();
		this.panel[13].setLayout(new BoxLayout(this.panel[13], BoxLayout.X_AXIS));
		this.panel[13].setBackground(PanelResult.COLOR_BACKGROUND);
		for(int i = 0; i < 6; i ++) {
			this.panel[13].add(this.scroll[i]);
		}
		//this.multiLabel[5] = StringHandle.checkString(PanelResult.FONT_NORMAL, "Note: " + this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_NOTE], PiecePanel.WIDTH_MAIN_PANEL);
		this.panel[13].setVisible(false);
		this.panel[14] = new JPanel();
		this.panel[14].setLayout(new BoxLayout(this.panel[14], BoxLayout.X_AXIS));
		this.panel[14].setBackground(PanelResult.COLOR_BACKGROUND);
		this.label[7] = new JLabel("Note ");
		this.label[7].setFont(PanelResult.FONT_NORMAL);
		this.textArea[0] = new JTextArea(3, 30);
		this.textArea[0].setBackground(PanelResult.COLOR_BACKGROUND);
		this.textArea[0].setFont(PanelResult.FONT_NORMAL);
		this.textArea[0].setText(StringHandle.convertString(PanelResult.FONT_NORMAL, this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_NOTE], PiecePanel.WIDTH_MAIN_PANEL - 80));
		this.textArea[0].setEditable(false);
		this.scroll[6] = new JScrollPane(this.textArea[0]);
		this.panel[14].add(this.label[7]);
		this.panel[14].add(this.scroll[6]);
		this.panel[10].add(this.label[5]);
		this.panel[10].add(this.panel[13]);
		this.panel[10].add(this.panel[14]);
		
		this.panel[11] = new JPanel();
		this.panel[11].setLayout(new BoxLayout(this.panel[11], BoxLayout.Y_AXIS));
		this.panel[11].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[11].setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.label[6] = new JLabel("Information of activity");
		this.label[6].setFont(PanelResult.FONT_SUB_TITLE);
		this.panel[15] = new JPanel();
		this.panel[15].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[15].setLayout(new BoxLayout(this.panel[15], BoxLayout.X_AXIS));
		this.textArea[1] = new JTextArea(3, 30);
		this.textArea[1].setBackground(PanelResult.COLOR_BACKGROUND);
		this.textArea[1].setFont(PanelResult.FONT_NORMAL);
		this.textArea[1].setEditable(false);
		this.textArea[1].setText(StringHandle.convertString(PanelResult.FONT_NORMAL, this.data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_NOTE], PiecePanel.WIDTH_MAIN_PANEL - 80));
		this.scroll[7] = new JScrollPane(this.textArea[1]);
		this.label[8] = new JLabel("Note ");
		this.label[8].setFont(PanelResult.FONT_NORMAL);
		this.panel[15].add(this.label[8]);
		this.panel[15].add(this.scroll[7]);
		if(this.data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_RINGSTATUS].equals("1")) {
			this.checkBox.setSelected(true);
		}
		this.checkBox.setEnabled(false);
		this.panel[11].add(this.label[6]);
		this.panel[11].add(this.panel[15]);
		this.panel[11].add(this.checkBox);
		
		this.panel[6].add(this.panel[7]);
		this.panel[6].add(this.panel[8]);
		this.panel[6].add(this.panel[9]);
		this.panel[6].add(this.panel[10]);
		this.panel[6].add(this.panel[11]);
		
		this.panel[12] = new JPanel();
		this.panel[12].setBackground(PanelResult.COLOR_BACKGROUND);
		this.panel[12].setBorder(BorderFactory.createLineBorder(PanelResult.COLOR_BORDER));
		this.button[0] = new JButton("Hide");
		this.button[0].setFont(PanelResult.FONT_SUB_TITLE);
		this.button[1] = new JButton("Change");
		this.button[1].setFont(PanelResult.FONT_SUB_TITLE);
		this.button[2] = new JButton("Remove");
		this.button[2].setFont(PanelResult.FONT_SUB_TITLE);
		this.panel[12].add(this.button[0]);
		this.panel[12].add(this.button[1]);
		this.panel[12].add(this.button[2]);
		
		this.panel[4].add(this.panel[5], BorderLayout.NORTH);
		this.panel[4].add(this.scroll[8], BorderLayout.CENTER);
		this.panel[4].add(this.panel[12], BorderLayout.SOUTH);
	}
	//Phương thức xử lý sự kiện trong lớp này
	
	private void handleEvent() {
		this.button[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setVisible(false);
			}
		});
		
		this.button[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setDataSql();
			}
		});
		
		this.button[2].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int type = JOptionPane.showConfirmDialog(null, "Do you want delete this activity?", "Confirm Dialog", JOptionPane.OK_CANCEL_OPTION);
				
				if(type == JOptionPane.OK_OPTION) {
					sqlProcessor.deleteTuple(SqlProcessor.TABLE_ACTIVITY, data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_ID]);
					setEnabled(false);
					setVisible(false);
					panelBody.getPanelActivityRing().updateDataArray();
				}
			}
		});
	}	
	//Phương thức khởi tạo cho các thành phần choicer trong lớp này
	
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
		
		for(int i = 1992; i < 2051; i ++) {
			this.choicer[0].add(Integer.toString(i));
		}
		
		for(int i = 1; i < 13; i ++) {
			this.choicer[1].add(Integer.toString(i));
		}
		
		for(int i = 1; i < 32; i ++) {
			this.choicer[2].add(Integer.toString(i));
		}
		
		for(int i = 0; i < 24; i ++) {
			this.choicer[3].add(Integer.toString(i));
		}
		
		for(int i = 0; i < 60; i ++) {
			this.choicer[4].add(Integer.toString(i));
			this.choicer[5].add(Integer.toString(i));
		}
	}
	//Khai báo phương thức cập nhật lại trạng thái của các thành phần và dữ liệu có liên quan tới cơ sở dữ liệu
	
	private void setDataSql() {
		if(this.flag) {
			this.flag = ! this.flag;
			this.button[1].setText("Update");
			this.label[5].setText("Time");
			this.panel[13].setVisible(true);
			this.textArea[0].setEditable(true);
			this.textArea[1].setEditable(true);
			this.checkBox.setEnabled(true);
		}
		else {
			if(this.setDataArray()) {
				this.flag = ! this.flag;
				this.button[1].setText("Change");
				this.label[5].setText("Time: " + this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_TIME]);
				this.panel[13].setVisible(false);
				this.textArea[0].setEditable(false);
				this.textArea[1].setEditable(false);
				this.checkBox.setEnabled(false);
				
				this.sqlProcessor.updateTable(SqlProcessor.TABLE_TIME, this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties(), this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_ID]);
				this.sqlProcessor.updateTable(SqlProcessor.TABLE_ACTIVITY, this.data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties(), this.data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_ID]);
				this.panelBody.getPanelActivityRing().updateDataArray();
				
				JOptionPane.showMessageDialog(null, "Update success!", "Dialog Message", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	//Phương thức thiết lập dữ liệu có trong mảng data
	
	private boolean setDataArray() {
		boolean result = false;
		String temple = "";
		
		if(!this.flag) {
			if(this.choicer[0].getSelectedItem().equals("Year") || this.choicer[1].getSelectedItem().equals("Month") || this.choicer[2].getSelectedItem().equals("Day")) {
				JOptionPane.showMessageDialog(null, "Please choose time again!", "Note", JOptionPane.WARNING_MESSAGE);
			}
			else {
				temple = this.setTime();
				this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_TIME] = temple;
				this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_NOTE] = StringHandle.changeIntoSpace(this.textArea[0].getText());
				this.data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_NOTE] = StringHandle.changeIntoSpace(this.textArea[1].getText());
				if(this.checkBox.isSelected()) {
					this.data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_RINGSTATUS] = "1";
				}
				else {
					this.data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_RINGSTATUS] = "0";
				}
				
				result = true;
			}
		}
		
		return result;
	}
	//Phương thức thiết lập dữ liệu v�? th�?i gian của hoạt động có trong mảng data

	private String setTime() {
		String templeTime = "";
		
		templeTime = this.choicer[0].getSelectedItem() + "-" + this.choicer[1].getSelectedItem() + "-" + this.choicer[2].getSelectedItem() + " ";
		
		if(this.choicer[3].getSelectedItem().equals("Hour")) {
			templeTime += "0:";
		}
		else {
			templeTime += this.choicer[3].getSelectedItem() + ":";
		}
		
		if(this.choicer[4].getSelectedItem().equals("Minute")) {
			templeTime += "0:";
		}
		else {
			templeTime += this.choicer[4].getSelectedItem() + ":";
		}
		
		if(this.choicer[5].getSelectedItem().equals("Seconds")) {
			templeTime += "0";
		}
		else {
			templeTime += this.choicer[5].getSelectedItem();
		}
		
		return templeTime;
	}
	//Khai báo hàm cập nhật lại nội dung của thẻ kết quả
	
	public void updatePieceResult(SqlDataTransport newData) {
		this.data = newData;
		this.label[2].setText(this.data.getDataArray()[SqlProcessor.TABLE_USER_ACTIVITY].getValueProperties()[SqlData.USER_ACTIVITY_TIMEREGISTER]);
		
		this.multiString[0] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Name: " + this.data.getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_NAME], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.multiString[1] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Description: " + this.data.getDataArray()[SqlProcessor.TABLE_TYPE].getValueProperties()[SqlData.TYPE_DESCRIPTION], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.textArea[2].setText(this.multiString[0]);
		this.textArea[3].setText(this.multiString[1]);
		
		this.multiString[2] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Name: " + this.data.getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_NAME], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.multiString[3] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Address: " + this.data.getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_ADDRESS], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.multiString[4] = StringHandle.convertString(PanelResult.FONT_NORMAL, "Description: " + this.data.getDataArray()[SqlProcessor.TABLE_PLACE].getValueProperties()[SqlData.PLACE_DESCRIPTION], PiecePanel.WIDTH_MAIN_PANEL - 80);
		this.textArea[4].setText(this.multiString[2]);
		this.textArea[5].setText(this.multiString[3]);
		this.textArea[6].setText(this.multiString[4]);
		
		this.label[5].setText("Time: " + this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_TIME]);
		
		this.textArea[0].setText(StringHandle.convertString(PanelResult.FONT_NORMAL, this.data.getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_NOTE], PiecePanel.WIDTH_MAIN_PANEL - 100));
		
		this.textArea[1].setText(StringHandle.convertString(PanelResult.FONT_NORMAL, this.data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_NOTE], PiecePanel.WIDTH_MAIN_PANEL - 100));
		
		if(this.data.getDataArray()[SqlProcessor.TABLE_ACTIVITY].getValueProperties()[SqlData.ACTIVITY_RINGSTATUS].equals("1")) {
			this.checkBox.setSelected(true);
		}
		
		this.checkBox.setEnabled(false);
		this.textArea[0].setEditable(false);
		this.textArea[1].setEditable(false);
		this.setVisible(true);
	}
}