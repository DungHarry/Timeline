package project1.timeline.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import project1.timeline.engine.SqlData;
import project1.timeline.engine.SqlDataTransport;
import project1.timeline.engine.SqlProcessor;
import project1.timeline.engine.TimeProcessor;
import project1.timeline.engine.XmlProcessor;
import project1.timeline.gui.body.PanelBody;

/*
 * Khai báo lớp tạo ra giao diện cho phép ngư�?i dùng có thể nhìn thấy được các hoạt động mà chuông đã báo
 */
public class PanelActivityRing extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_ACT_RING = 720;
	public static final int HEIGHT_PANEL_ACT_RING = 700;
	//Khai báo các biến được sử dụng trong lớp này
	
	SqlProcessor sqlProcessor;
	JPanel[] panel = new JPanel[2];
	JScrollPane scroll;
	JLabel label = new JLabel("Activity For Ring");
	SqlDataTransport[] dataArray;
	RingOption ringOption;
	String[] condition = new String[SqlProcessor.SIZE_ARRAY_CONDITION];
	PiecePanel piecePanel;
	boolean flag = true;
	PanelBody panelBody;
	//Phương thức khởi tạo của lớp này
	
	public PanelActivityRing(SqlProcessor sqlProcessor, RingOption ringOption, PanelBody panelBody) {
		this.sqlProcessor = sqlProcessor;
		this.ringOption = ringOption;
		this.panelBody = panelBody;
		
		this.initializeDataArray();
		this.initializeComponent();
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(PanelActivityRing.WIDTH_PANEL_ACT_RING, PanelActivityRing.HEIGHT_PANEL_ACT_RING));
		this.add(this.panel[0], BorderLayout.NORTH);
		this.add(this.scroll, BorderLayout.CENTER);
	}
	//Phương thức khởi tạo giao diện cho đối tượng của lớp này
	
	private void initializeComponent() {
		this.panel[0] = new JPanel();
		this.panel[0].setBackground(Color.blue);
		this.label.setForeground(Color.white);
		this.label.setFont(new Font("Times New Roman", Font.BOLD, 25));
		this.panel[0].add(this.label);
		
		this.panel[1] = new JPanel();
		this.panel[1].setBackground(Color.white);
		this.panel[1].setLayout(new BoxLayout(this.panel[1], BoxLayout.Y_AXIS));
		this.scroll = new JScrollPane(this.panel[1]);
	}
	//Phương thức khởi tạo dữ liệu cho lớp này
	
	private void initializeDataArray() {
		for(int  i = 0; i < this.condition.length; i ++) {
			if(i == SqlProcessor.CONDITION_USER_ID) {
				this.condition[i] = this.sqlProcessor.getUserInformaion(XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_NAME], XmlProcessor.getInformation(XmlProcessor.TYPE_USER)[XmlProcessor.USER_PASSWORD]).getValueProperties()[SqlData.USER_ID];
			}
			else if(i == SqlProcessor.CONDITION_RINGSTATUS) {
				this.condition[i] = "1";
			}
			else {
				this.condition[i] = "";
			}
		}
		
		this.dataArray = this.sqlProcessor.exportActivityRing(this.condition);
	}
	//Phương thức cập nhật lại cơ sở dữ liệu
	
	public void updateDataArray() {
		this.dataArray = this.sqlProcessor.exportActivityRing(this.condition);
	}
	//Phương thức kiểm tra lại các phần tử có trong mảng dataArray có th�?a mãn
	
	public void checkPanel() {
		while(this.flag) {
			for(int i = 0; i < this.dataArray.length; i ++) {
				if(TimeProcessor.checkTime(this.dataArray[i].getDataArray()[SqlProcessor.TABLE_TIME].getValueProperties()[SqlData.TIME_TIME])) {
					this.piecePanel = new PiecePanel(this.sqlProcessor, this.dataArray[i], this.panelBody);
					this.panel[1].add(this.piecePanel);
					this.ringOption.playRing();
					this.panelBody.showComponent(PanelBody.TYPE_PANEL_ACT_RING);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	//Phương thức kiểm tra dataArray có bằng null hay không?
	
	public boolean checkDataArray() {
		if(this.dataArray != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
