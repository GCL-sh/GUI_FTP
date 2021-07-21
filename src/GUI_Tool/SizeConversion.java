package GUI_Tool;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SizeConversion {

	public SizeConversion(){}
	
	
	
	public String getFiledSize(long size){	
//		System.out.println(size);
		// 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
		double value = (double) size;
		if (value < 1024) {
			return String.valueOf((int)value) + " Bytes  ";
		} else {
			value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		}
		// 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		// 因为还没有到达要使用另一个单位的时候
		// 接下去以此类推
		if (value < 1024) {
			return String.valueOf(value) + " KB  ";
		} else {
			value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		}
		if (value < 1024) {
			return String.valueOf(value) + " MB  ";
		} else {
			// 否则如果要以GB为单位的，先除于1024再作同样的处理
			value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
			return String.valueOf(value) + " GB  ";
		}
	}
//	文件前缀名获取，文件后缀名获取。
//	System.out.println(arrayList[0].substring(0,arrayList[0].lastIndexOf(".")));
//	System.out.println(arrayList[0].substring(arrayList[0].lastIndexOf(".")+1,arrayList[0].length()));
	public String getFiledIsfile( String dirPath, String file){
		String Name = dirPath+"/"+file;
		File dirPathFile = new File(Name);
//		System.out.println(dirPathFile);
		if(dirPathFile.isFile()){
			return Name.substring(Name.lastIndexOf(".")+1,Name.length());
		}
		else {
//			System.out.println("wenjianjia");
			return "文件夹";
		}
	}
	public String getFiledLastTime( String dirPath,String file){
		String Name = dirPath+"/"+file;
		File dirPathFile = new File(Name);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");			//设置时间格式
		Date date = new Date(dirPathFile.lastModified());						//获取此文件的最后修改时间
		
		return df.format(date);
	}
}
