package GUI_Tool;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SizeConversion {

	public SizeConversion(){}
	
	
	
	public String getFiledSize(long size){	
//		System.out.println(size);
		// ����ֽ�������1024����ֱ����BΪ��λ�������ȳ���1024����3λ��̫��������
		double value = (double) size;
		if (value < 1024) {
			return String.valueOf((int)value) + " Bytes  ";
		} else {
			value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		}
		// ���ԭ�ֽ�������1024֮������1024�������ֱ����KB��Ϊ��λ
		// ��Ϊ��û�е���Ҫʹ����һ����λ��ʱ��
		// ����ȥ�Դ�����
		if (value < 1024) {
			return String.valueOf(value) + " KB  ";
		} else {
			value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		}
		if (value < 1024) {
			return String.valueOf(value) + " MB  ";
		} else {
			// �������Ҫ��GBΪ��λ�ģ��ȳ���1024����ͬ���Ĵ���
			value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
			return String.valueOf(value) + " GB  ";
		}
	}
//	�ļ�ǰ׺����ȡ���ļ���׺����ȡ��
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
			return "�ļ���";
		}
	}
	public String getFiledLastTime( String dirPath,String file){
		String Name = dirPath+"/"+file;
		File dirPathFile = new File(Name);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");			//����ʱ���ʽ
		Date date = new Date(dirPathFile.lastModified());						//��ȡ���ļ�������޸�ʱ��
		
		return df.format(date);
	}
}
