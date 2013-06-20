package project1.timeline.gui.body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import project1.timeline.component.PanelFilter;
import project1.timeline.component.PanelOption;
import project1.timeline.component.PlaceAndType;
import project1.timeline.engine.SqlProcessor;

/*
 * Khai báo panel sử dụng kiểu giao diện tab, hiển thị các thành phần được lựa ch�?n như: option, insert, filter
 */
public class PanelSelection extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_PANEL_SELECTION = 500;
	public static final int HEIGHT_PANEL_SELECTION = 700;
	public static final String TITLE_OPTION = "Option";
	public static final String TITLE_FILTER = "Filter";
	public static final String TITLE_PLACE_TYPE = "Place & Type";
	public static final Font FONT_NORMAL = new Font("Times New Roman", Font.PLAIN, 15);
	//Khai báo các biến được sử dụng trong phần này
	
	private PanelOption panelOption;
	private PanelFilter panelFilter;
	private SqlProcessor sqlProcessor;
	private PanelBody panelContainer;
	private PlaceAndType placeAndType;
	JTabbedPane tabPane = new JTabbedPane();
	//Hàm khởi tạo
	
	public PanelSelection(SqlProcessor sqlProcessor, PanelBody panelContainer) {
		this.sqlProcessor = sqlProcessor;
		this.panelContainer = panelContainer;
		
		this.panelOption = new PanelOption(this.sqlProcessor, this.panelContainer);
		this.panelFilter = new PanelFilter(this.sqlProcessor, this.panelContainer);
		this.placeAndType = new PlaceAndType(this.sqlProcessor);
		this.makeTab();
		this.setLayout(new BorderLayout());
		this.add(this.tabPane, BorderLayout.CENTER);
	}
	//Phương thức tạo giao diện cho lớp này
	
	private void makeTab() {
		this.tabPane.setBackground(Color.white);
		this.tabPane.setFont(PanelSelection.FONT_NORMAL);
		this.tabPane.add(PanelSelection.TITLE_OPTION, this.panelOption);
		this.tabPane.add(PanelSelection.TITLE_FILTER, this.panelFilter);
		this.tabPane.add(PanelSelection.TITLE_PLACE_TYPE, this.placeAndType);
	}
	//Phương thức lấy ra panelOption
	
	public PanelOption getPanelOption() {
		return this.panelOption;
	}
	//Phương thức lấy ra panelFilter
	
	public PanelFilter getPanelFilter() {
		return this.panelFilter;
	}
	//Phương thức lấy ra panelBody
	public PanelBody getPanelBody() {
		return this.panelContainer;
	}
	//Phương thức lấy ra placeAndType
	
	public PlaceAndType getPlaceAndType() {
		return this.placeAndType;
	}
}
