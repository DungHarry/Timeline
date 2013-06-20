package project1.timeline.engine;

import java.util.Calendar;
import java.text.SimpleDateFormat;

/*
 * Lớp này thực hiện kiểm tra th�?i gian và trả phục vụ cho việc đổ chuông báo và hiển thị dữ liệu
 */
public class TimeProcessor {
	//Khai báo các hằng số được sử dụng trong lớp này
	
	public static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
	//Khai báo các biến được sử dụng trong lớp này
	
	SqlProcessor sqlProcessor;
	
	//Phương thức lấy ra th�?i gian hệ thông và so sánh với th�?i gian tham số truy�?n vào 
	
	public static boolean checkTime(String time) {
		Calendar date = Calendar.getInstance();
		SimpleDateFormat formatDate = new SimpleDateFormat(TimeProcessor.DATA_FORMAT);
		if(formatDate.format(date.getTime()).equals(StringHandle.handleTime(time))) {
			return true;
		}
		else {
			return false;
		}
	}
}
