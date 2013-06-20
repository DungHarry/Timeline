package project1.timeline.gui.body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import project1.timeline.component.PanelActivityRing;
import project1.timeline.component.PanelInsertion;
import project1.timeline.engine.SqlProcessor;
import project1.timeline.main.TimeLine;

public class PanelBody extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_BODY = 1220;
	public static final int HEIGHT_PANEL_BODY = 700;
	
	public static final int TYPE_PANEL_TEMPLE = -1;
	public static final int TYPE_PANEL_INSERTION = 0;
	public static final int TYPE_PANEL_FILTER = 1;
	public static final int TYPE_PANEL_ACT_RING = 2;
	//Khai báo các biến được sử dụng trong lớp này
	
	SqlProcessor sqlProcessor;
	TimeLine timeLine;
	JPanel templePanel = new JPanel();
	PanelSelection panelSelection;
	PanelActivityRing panelActivityRing;
	//Phương thức khởi tạo của lớp này
	
	public PanelBody(SqlProcessor sqlProcessor, TimeLine timeLine) {
		this.sqlProcessor = sqlProcessor;
		this.timeLine = timeLine;
		this.panelSelection = new PanelSelection(this.sqlProcessor, this);
		this.panelActivityRing = new PanelActivityRing(this.sqlProcessor, this.panelSelection.getPanelOption().getRingOption(), this);
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(PanelBody.WIDTH_PANEL_BODY, PanelBody.HEIGHT_PANEL_BODY));
		this.setBackground(Color.white);
		this.initializeComponent();
	}
	//Khai báo phương thức khởi tạo giao diện lớp này
	
	private void initializeComponent() {
		this.templePanel.setPreferredSize(new Dimension(PanelInsertion.WIDTH_PANEL_INSERTION, PanelInsertion.HEIGHT_PANEL_INSERTION));
		this.templePanel.setBackground(Color.white);
		
		this.add(this.panelSelection, BorderLayout.WEST);
		//this.add(this.panelSelection.getPanelOption().getPanelInsertion(), BorderLayout.CENTER);
		//this.add(this.panelSelection.getPanelFilter(), BorderLayout.CENTER);
		this.add(this.panelActivityRing, BorderLayout.CENTER);
		this.add(this.templePanel, BorderLayout.CENTER);
	}
	//Khai báo hàm ẩn tất cả thành phần không phải thành phần có chỉ số truy�?n vào
	
	public void showComponent(int typeComponent) {
		if(typeComponent == PanelBody.TYPE_PANEL_FILTER) {
			this.panelSelection.getPanelFilter().getPanelResult().setVisible(true);
			this.panelSelection.getPanelOption().getPanelInsertion().setVisible(false);
			this.templePanel.setVisible(false);
			this.panelActivityRing.setVisible(false);
		}
		else if(typeComponent == PanelBody.TYPE_PANEL_INSERTION) {
			this.panelSelection.getPanelFilter().getPanelResult().setVisible(false);
			this.panelSelection.getPanelOption().getPanelInsertion().setVisible(true);
			this.templePanel.setVisible(false);
			this.panelActivityRing.setVisible(false);
		}
		else if(typeComponent == PanelBody.TYPE_PANEL_TEMPLE) {
			this.panelSelection.getPanelFilter().getPanelResult().setVisible(false);
			this.panelSelection.getPanelOption().getPanelInsertion().setVisible(false);
			this.panelActivityRing.setVisible(false);
			this.templePanel.setVisible(true);
		}
		else if(typeComponent == PanelBody.TYPE_PANEL_ACT_RING) {
			this.panelSelection.getPanelFilter().getPanelResult().setVisible(false);
			this.panelSelection.getPanelOption().getPanelInsertion().setVisible(false);
			this.templePanel.setVisible(false);
			this.add(this.panelActivityRing, BorderLayout.CENTER);
			this.panelActivityRing.setVisible(true);
		}
	}
	//Phương thức lấy ra panelSelection
	
	public PanelSelection getPanelSelection() {
		return this.panelSelection;
	}
	//Phương thức lấy ra timeline
	
	public TimeLine getTimeline() {
		return this.timeLine;
	}
	//Phương thức lấy ra panelActivityRing
	
	public PanelActivityRing getPanelActivityRing() {
		return this.panelActivityRing;
	}
}
