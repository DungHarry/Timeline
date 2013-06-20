package project1.timeline.engine;

public class SqlDataTransport {
	//Khai báo hằng số được sử dụng trong lớp này
	
	public static final int TYPE_IMPORT = 1;
	public static final int TYPE_EXPORT = 2;
	//Khai báo các biến được sử dụng trong lớp này
	
	private SqlData[] dataArray = new SqlData[6];
	int type = 0;
	
	public SqlDataTransport(int type) {
		this.type = type;
		this.initialize();
	}
	//Hàm khởi tạo dữ liệu ban đầu
	
	private void initialize() {
		//Khởi tạo dữ liệu cho mảng chứa thông tin v�? các quan hệ
		
		this.dataArray[SqlProcessor.TABLE_USER] = new SqlData(SqlProcessor.TABLE_USER);
		this.dataArray[SqlProcessor.TABLE_PLACE] = new SqlData(SqlProcessor.TABLE_PLACE);
		this.dataArray[SqlProcessor.TABLE_ACTIVITY] = new SqlData(SqlProcessor.TABLE_ACTIVITY);
		this.dataArray[SqlProcessor.TABLE_TIME] = new SqlData(SqlProcessor.TABLE_TIME);
		this.dataArray[SqlProcessor.TABLE_TYPE] = new SqlData(SqlProcessor.TABLE_TYPE);
		this.dataArray[SqlProcessor.TABLE_USER_ACTIVITY] = new SqlData(SqlProcessor.TABLE_USER_ACTIVITY);
		
		//this.disableFlag();
		//Khởi tạo dữ liệu cho mảng các String đệm trong việc chuyển tiếp dữ liệu giữa ngư�?i dùng và MySQL
		/*
		this.dataTransfer[SqlProcessor.TABLE_USER] = new String[SqlData.SIZE_ARRAY_USER];
		this.dataTransfer[SqlProcessor.TABLE_ACTIVITY] = new String[SqlData.SIZE_ARRAY_ACTIVITY];
		this.dataTransfer[SqlProcessor.TABLE_PLACE] = new String[SqlData.SIZE_ARRAY_PLACE];
		this.dataTransfer[SqlProcessor.TABLE_TIME] = new String[SqlData.SIZE_ARRAY_TIME];
		this.dataTransfer[SqlProcessor.TABLE_TYPE] = new String[SqlData.SIZE_ARRAY_TYPE];
		this.dataTransfer[SqlProcessor.TABLE_USER_ACTIVITY] = new String[SqlData.SIZE_ARRAY_USER_ACTIVITY];
		
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_ACTIVITY], SqlData.SIZE_ARRAY_ACTIVITY, "");
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_PLACE], SqlData.SIZE_ARRAY_PLACE, "");
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_TIME], SqlData.SIZE_ARRAY_TIME, "");
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_TYPE], SqlData.SIZE_ARRAY_TYPE, "");
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_USER], SqlData.SIZE_ARRAY_USER, "");
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_USER_ACTIVITY], SqlData.SIZE_ARRAY_USER_ACTIVITY, "");*/
	}
	//Phương thức khởi tạo dữ liệu cho một mảng String với số lượng phần tử cho trước
	
	private void initializeArrayString(String array[], int length, String valueBegin) {
		for(int i = 0; i < length; i ++) {
			array[i] = "";
		}
	}
	//Phương thức thiết lập tất cả dữ liệu của mảng dataTranssfer thành null
	/*
	private void emptyDataTransfer() {
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_ACTIVITY], SqlData.SIZE_ARRAY_ACTIVITY, null);
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_PLACE], SqlData.SIZE_ARRAY_PLACE, null);
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_TIME], SqlData.SIZE_ARRAY_TIME, null);
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_TYPE], SqlData.SIZE_ARRAY_TYPE, null);
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_USER], SqlData.SIZE_ARRAY_USER, null);
		this.initializeArrayString(this.dataTransfer[SqlProcessor.TABLE_USER_ACTIVITY], SqlData.SIZE_ARRAY_USER_ACTIVITY, null);
	}*/
	/*Phương thức tạo ra một cơ bộ đệm cơ sở dữ liệu và chuyển tất cả các thông tin v�? cơ sở dữ liệu đó vào trong mảng dataArray
	  để phục vụ sẵn sàng cho quá trình chuyển đổi sang 
	*/
	
	public boolean importDataArray(String[][] data) {
		if(data.length == this.dataArray.length) {
			for(int i = 0; i< data.length; i ++) {
				this.dataArray[i].setValueProperties(data[i]);
			}
			
			return true;
		}
		else return false;
	}
	//Các phương thức getter và setter
	
	public SqlData[] getDataArray() {
		return this.dataArray;
	}
	//Phương thức thiết lập mảng dataArray trong lớp này bằng một mảng khác có cấu trúc tương đương
	
	public boolean setDataArray(SqlData[] data) {
		if(this.dataArray.length == data.length) {
			this.emptyDataArray();
			
			this.dataArray = data;
			
			return true;
		}
		else return false;
	}
	//Phương thức hủy b�? toàn bộ dữ liệu có trong mảng dataArray
	
	private void emptyDataArray() {
		for(int i = 0; i < this.dataArray.length; i ++) {
			this.dataArray[i] = null;
		}
	}
	/*
	public String[][] getDataTransfer() {
		return this.dataTransfer;
	}
	
	public void setDataTransfer(String[][] data) {
		this.emptyDataTransfer();
		this.dataTransfer = data;
	}*/
	/*
	//Các phương thức thao tác với giá trị c�? của 
	
	public boolean[] getFlag() {
		return this.flag;
	}
	
	public void enableFlag(int typeTable) {
		if((typeTable <= 5) || (typeTable >= 0)) {
			this.flag[typeTable] = true;
		}
	}
	
	public void disableFlag() {
		for(int i = 0; i < this.flag.length; i ++) {
			this.flag[i] = false;
			this.flagBackup[i] = false;
		}
	}
	//Các phương thức kiểm tra sự kích hoạt từng phần
	/*
	private boolean checkEnableFirstPart() {
		if((dataTransfer[SqlProcessor.TABLE_PLACE][SqlData.PLACE_NAME] != null) && (dataTransfer[SqlProcessor.TABLE_PLACE][SqlData.PLACE_ADDRESS] != null)) {
			if((dataTransfer[SqlProcessor.TABLE_PLACE][SqlData.PLACE_NAME] != "") && (dataTransfer[SqlProcessor.TABLE_PLACE][SqlData.PLACE_ADDRESS] != null)) {
				this.flag[SqlProcessor.TABLE_PLACE] = true;
			}
		}
		if(dataTransfer[SqlProcessor.TABLE_TYPE][SqlData.TYPE_NAME] != null) {
			if(dataTransfer[SqlProcessor.TABLE_TYPE][SqlData.TYPE_NAME] != "") {
				this.flag[SqlProcessor.TABLE_TYPE] = true;
			}
		}
		
		
		return this.flag[SqlProcessor.TABLE_PLACE] && this.flag[SqlProcessor.TABLE_TYPE];
	}
	
	private boolean checkEnableSecondPart() {
		if(dataTransfer[SqlProcessor.TABLE_ACTIVITY][SqlData.ACTIVITY_NOTE] != null) {
			if(dataTransfer[SqlProcessor.TABLE_ACTIVITY][SqlData.ACTIVITY_NOTE] != "") {
				this.flag[SqlProcessor.TABLE_ACTIVITY] = true;
			}
		}
		
		return this.flag[SqlProcessor.TABLE_ACTIVITY];
	}
	
	private boolean checkEnableFinallyPart() {
		if(dataTransfer[SqlProcessor.TABLE_TIME][SqlData.TIME_TIME] != null) {
			if(dataTransfer[SqlProcessor.TABLE_TIME][SqlData.TIME_TIME] != "") {
				this.flag[SqlProcessor.TABLE_TIME] = true;
			}
		}
		
		return this.flag[SqlProcessor.TABLE_TIME];
	}
	
	public boolean isReadyToImport() {
		return (this.checkEnableFirstPart() && this.checkEnableSecondPart() && this.checkEnableFinallyPart());
	}
	//Phương thức backup giá trị của c�? phục vụ cho giao diện ch�? của chương trình
	
	public boolean getWaiting() {
		return this.isWaiting;
	}
	
	public void backupFlag() {
		for(int i = 0; i < this.flag.length; i ++) {
			this.flagBackup[i] = this.flag[i];
			this.flag[i] = false;
		}
	}
	
	public void restoreFlag() {
		for(int i = 0; i < this.flag.length; i ++) {
			this.flag[i] = this.flagBackup[i];
			this.flagBackup[i] = false;
		}
	}*/
} 
