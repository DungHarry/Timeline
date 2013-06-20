/*
 * Lớp này được sử dụng để kiểm tra độ dài của một String và xử lý chuỗi đó để tạo ra các
 * JLabel phù hợp trợ giúp quá trình trình bày dữ liệu lấyproject1.timeline.engineo ngư�?i dùng
 */

package project1.timeline.engine;

import java.awt.Font;
import java.awt.FontMetrics;
import java.io.ObjectInputStream.GetField;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class StringHandle {
	//Phương thức tính toán và xử lý chuỗi tham số và trả lại một mảng các JLabel có độ dài không vượt quá độ dài Panel
	
	public static JLabel[] checkString(Font font, String str, int panelWidth) {
		JLabel temple = new JLabel(str);
		JLabel[] result;		
		FontMetrics fontMetrics = temple.getFontMetrics(font);
		String[] templeString = splitString(fontMetrics, str, panelWidth);
		int i = 0;
		
		for(i = 0; i < templeString.length; i ++) {
			if((templeString[i].equals("")) || (templeString[i].equals(null))) {
				break;
			}
		}
		
		result = new JLabel[i];
		
		for(int j = 0; j < result.length; j ++) {
			result[j] = new JLabel();
			result[j].setText(templeString[j]);
			result[j].setFont(font);
		}
		
		return result;
	}
	/*Phương thức kiểm tra và chèn vào bên trong của chuỗi các kí tự \n để xuông dòng khi trình bày
	 * văn bản trong một JTextArea
	 */
	
	public static String convertString(Font font, String str, int areaWidth) {
		JLabel temple = new JLabel(str);
		String result = "";
		FontMetrics fontMetrics = temple.getFontMetrics(font);
		String[] templeString = splitString(fontMetrics, str, areaWidth);
		
		for(int i = 0; i < templeString.length; i ++) {
			if(templeString[i].equals("") || templeString[i].equals(null)) {
				break;
			} 
			else {
				if(i == 0) {
					result = templeString[i];
				}
				else {
					result = result + "\n" + templeString[i];
				}
			}
		}
		
		return result;
	}
	//Phương thức chia một String thành các subString có độ dài không vượt quá độ rộng của panel
	
	private static String[] splitString(FontMetrics fontMetrics, String str, int panelWidth) {
		String[] temple = str.split(" ");
		String[] result;
		int length = 0;
		int indexArrayString = 0;
		int sumPixel = 0;
		boolean flag = true;
		
		length = ((int) (SwingUtilities.computeStringWidth(fontMetrics, str) / panelWidth)) + 5;
		
		result = new String[length];
		
		for(int i = 0; i < length; i ++) {
			result[i] = "";
		}
		
		for(int i = 0; i < temple.length; i ++) {
			sumPixel += computePixelsString(fontMetrics, " " + temple[i]);
			
			if(sumPixel > panelWidth) {
				i --;
				indexArrayString ++;
				sumPixel = 0;
				flag = true;
			}
			else {
				if(flag) {
					result[indexArrayString] = result[indexArrayString] + temple[i];
					flag = false;
				}
				else {
					result[indexArrayString] = result[indexArrayString] + " " + temple[i];
				}
			}
		}
		
		return result;
	}
	//Phương thức thay thế tất cả các kí tự xuống dòng(\n) có trong chuỗi bằng một khoảng trắng
	
	public static String changeIntoSpace(String str) {
		return str.replaceAll("\n", " ");
	}
	//Phương thức đo độ dài của một chuỗi bất kì 
	
	private static int computePixelsString(FontMetrics fontMetrics, String str) {
		return SwingUtilities.computeStringWidth(fontMetrics, str);
	}
	//Phương thức xử lý th�?i gian được in ra từ cơ sở dữ liệu: loại b�? phần ".0" ở cuối của dữ liệu
	
	public static String handleTime(String time) {
		return time.substring(0, time.lastIndexOf(".0"));
	}
}
