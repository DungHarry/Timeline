package project1.timeline.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import project1.timeline.engine.SqlProcessor;
import project1.timeline.engine.XmlProcessor;
import project1.timeline.gui.begin.PanelBegin;
import project1.timeline.gui.body.PanelBody;

public class TimeLine extends JPanel {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final int WIDTH_TIMELINE = 1220;
	public static final int HEIGHT_TIMELINE = 700;
	public static final int TYPE_BEGIN = 0;
	public static final int TYPE_BODY = 1;
	//Khai báo các biến được sử dụng trong lớp này
	
	SqlProcessor sqlProcessor;
	PanelBegin panelBegin;
	PanelBody panelBody;
	//Phương thức khởi tạo
	
	public TimeLine() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(TimeLine.WIDTH_TIMELINE, TimeLine.HEIGHT_TIMELINE));
		
		if(this.autoMakeConnection()) {
			this.panelBegin = new PanelBegin(this.sqlProcessor, this);
			this.panelBody = new PanelBody(this.sqlProcessor, this);
			this.add(this.panelBegin, BorderLayout.CENTER);
			this.add(this.panelBody, BorderLayout.CENTER);
			
			this.showPanel(TimeLine.TYPE_BODY);
		}
		else {
			this.panelBegin = new PanelBegin(this.sqlProcessor, this);
			this.add(this.panelBegin, BorderLayout.CENTER);
			
			this.showPanel(TimeLine.TYPE_BEGIN);
		}
	}
	//Phương thức kiểm tra kết nối tới cơ sở dữ liệu mặc định
	
	private boolean autoMakeConnection() {
		boolean result = false;
		File file = null;
		
		try {
			file = new File(XmlProcessor.PATH_REMEMBER_FILE);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(!file.exists()) {
			XmlProcessor.initializeValue();
			XmlProcessor.createFile();
			
			result = false;
		}
		else {
			String address = XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_ADDESS];
			String dbName = XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_DATABASE];
			String name = XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_NAME];
			String password = XmlProcessor.getInformation(XmlProcessor.TYPE_MYSQL)[XmlProcessor.MYSQL_PASSWORD];
			
			this.sqlProcessor = new SqlProcessor(address, dbName, name, password);
			
			if(this.sqlProcessor.createConnection(address, dbName, name, password)) {
				if(this.sqlProcessor.loginAuto()) {
					result = true;
				}
				else {
					JOptionPane.showMessageDialog(null, "An error has occurred with your account. Please set account again!", "Note", JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "An error has occurred. Please set some information again!", "Note", JOptionPane.ERROR_MESSAGE);
				
				result = false;
			}
		}
		
		return result;
	}
	//Phương thức tạo sự ẩn hiện của các panel
	
	public void showPanel(int typePanel) {
		switch(typePanel) {
			case TimeLine.TYPE_BEGIN: {
				this.panelBegin.setVisible(true);
				this.add(this.panelBegin, BorderLayout.CENTER);
				if(this.panelBody != null) {
					this.panelBody.setVisible(false);
				}
			}
				break;
			case TimeLine.TYPE_BODY: {
				if(this.panelBody == null) {
					this.panelBody = new PanelBody(this.sqlProcessor, this);
					this.add(this.panelBody, BorderLayout.CENTER);
					this.panelBody.setVisible(true);
					this.panelBegin.setVisible(false);
				}
				else {
					this.add(this.panelBody, BorderLayout.CENTER);
					this.panelBody.setVisible(true);
					this.panelBegin.setVisible(false);
				}
			}
				break;
		}
	}
	//Phương thức lấy ra panelBegin
	
	public PanelBegin getPanelBegin() {
		return this.panelBegin;
	}
	//Phương thức lấy ra panelBody
	
	public PanelBody getPanelBody() {
		return this.panelBody;
	}
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame("Timeline");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/data/image/Timeline.png"));
		TimeLine timeLine = new TimeLine();
		frame.setSize(new Dimension(TimeLine.WIDTH_TIMELINE, TimeLine.HEIGHT_TIMELINE));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(timeLine);
		frame.setVisible(true);
		timeLine.getPanelBody().getPanelActivityRing().checkPanel();
	}
}
