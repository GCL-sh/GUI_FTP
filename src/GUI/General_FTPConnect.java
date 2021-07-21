package GUI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import GUI_Tool.SizeConversion;



public class General_FTPConnect{
	
	private static volatile FTPClient ftp;
	private static volatile String Path;
	private static volatile boolean connectStatus;

	public General_FTPConnect(){Path = "/home";}
	
	public FTPClient getFtp() {
		return ftp;
	}
	public String GetFTP_DefaultPath(){
		return Path;
	}
	public void SetFTP_DefaultPath(String FilePath){
		Path = FilePath;
	}
	public boolean GetConnectStatus(){
		return connectStatus;
	}
	public void SetConnectStatus(boolean bool){
		connectStatus = bool;
	}
	
	
	
	public String[] FtpInit(String ftpHost,String ftpUserName,String ftpPassword)
	{	
        ftp = new FTPClient();								// 初始化FTP客服端
        String[] data = null;
		try {	
			ftp.connect(ftpHost,21);
			ftp.login(ftpUserName, ftpPassword);   				
            ftp.setControlEncoding("UTF-8");				// 设置编码
            ftp.changeWorkingDirectory("/home");
            Path = "/home";
            data = ftp.listNames();							// 获取默认目录下文件名放进数组中
            connectStatus = true;
	    }catch(IOException e){
	            e.printStackTrace();
	    }finally{
	            try{
	                    Thread.sleep(500);
	            }catch(InterruptedException e1){
	                    data = null;
	                    connectStatus = false;
	            }
	    }
		return data;  
	}
//	public List<String> FtpPath_FileSize(){
//		SizeConversion sizesion = new SizeConversion();
//		String fileSize = "";
//		FTPFile[] files;
//		SimpleDateFormat df;
//		Long addTime;
//		try {
//			files = ftp.listFiles(Path);
//			/** 获取文件夹的属性 */
////			files = ftp.get;
//			 System.out.println("Next");
//			 System.out.println(files);
//			 
//			System.out.println(files[2].getRawListing());
//			System.out.println(files[2].getUser()+"/"+files[2].getGroup());
//			addTime = files[2].getTimestamp().getTimeInMillis() +
//    						files[2].getTimestamp().getTimeZone().getOffset(0);
//			df = new SimpleDateFormat("yyyy/MM/dd HH:mm");			//设置时间格式
//			System.out.println(df.format(new Date(addTime)));
//			if(files!=null&&files.length>0){
//				fileSize = sizesion.getFiledSize(files[0].getSize());
//				 //utc时间加上时间差
//			    addTime = files[0].getTimestamp().getTimeInMillis() +
//			    				files[0].getTimestamp().getTimeZone().getOffset(0);
//			    df = new SimpleDateFormat("yyyy/MM/dd HH:mm");			//设置时间格式
//				System.out.println(df.format(new Date(addTime)));
//				System.out.println(files[0].getUser());
//				String tmp = ftp.getStatus(Path);
//				System.out.println(tmp.substring(21,30));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return fileSize;
//	}
	
	public List<String> FtpPath_FileSize(){
		FTPFile[] files;
		List<String> list = new ArrayList<>();
		try {
			files = ftp.listFiles();
			SizeConversion SizeCon = new SizeConversion();
//			System.out.println(files[2].getRawListing());
//			System.out.println(files[2].getSize());
			for(FTPFile name:files){
				list.add(SizeCon.getFiledSize(name.getSize()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<String> FtpPath_FileType(){
		FTPFile[] files;
		StringBuffer sb = new StringBuffer();
		List<String> list = new ArrayList<>();
		try {
			FTPFile[] files2 = ftp.listDirectories();
			List<String> list2 = new ArrayList<>();
			for(FTPFile name:files2){
				list2.add(name.getName());
			}
			files = ftp.listFiles();
			for(FTPFile name:files){
				if(!list2.contains(name.getName())){
		            for (int i=name.getName().length()-1; i >=0; i--) {
		                if( !String.valueOf(name.getName().charAt(i)).equals(".") ){
		                	sb.append(name.getName().charAt(i));
		                }
		                else
		                	break;
		            }
		            list.add(" "+sb.reverse().toString().toUpperCase()+" 文件");
		            sb.delete(0, sb.length());
				}else
					list.add(" 文件夹");
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<String> FtpPath_FileTime(){
		FTPFile[] files;
		List<String> list = new ArrayList<>();
		try {
			files = ftp.listFiles();
			for(FTPFile name:files){
			    Long addTime = name.getTimestamp().getTimeInMillis() +			
			    				name.getTimestamp().getTimeZone().getOffset(0);			// utc时间加上时间差
			    SimpleDateFormat df = new SimpleDateFormat(" yyyy/MM/dd HH:mm");		// 设置时间格式
			    list.add(df.format(new Date(addTime)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<String> FtpPath_FileCompetence(){
		FTPFile[] files;
		List<String> list = new ArrayList<>();
		try {
			files = ftp.listFiles();
//			System.out.print(b);
			for(FTPFile name:files){
				list.add(" "+ftp.getStatus(Path+"/"+name.getName()).substring(21,30));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<String> FtpPath_FileUserGroup(){
		FTPFile[] files;
		List<String> list = new ArrayList<>();
		try {
			files = ftp.listFiles();
			for(FTPFile name:files){
				list.add(" "+name.getUser()+" "+name.getGroup());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<String> FtpPath_DirName(){
		FTPFile[] files;
		List<String> list = new ArrayList<>();
		try {
			files = ftp.listDirectories();
			if(files!=null&&files.length>0){
				for(FTPFile name:files){
					list.add(name.getName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<String> FtpPath_FileName(){
		FTPFile[] files;
		List<String> list = new ArrayList<>();
		try {
			files = ftp.listFiles();
			if(files.equals(null)){
				list.add("...");
			}
			if(files!=null&&files.length>0){
				for(FTPFile name:files){
					list.add(name.getName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean SetFTP_FilePath(FTPClient ftp,String Path){
		boolean status;
		try {
			ftp.changeWorkingDirectory(Path);
			status = true;
		} catch (IOException e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	
	public static void main(String[] args) {
		General_FTPConnect ftp = new General_FTPConnect();
		
		String[] data = ftp.FtpInit("192.168.10.166", "root", "centos");
		for(int i = 0; i < data.length; i++){
		    System.out.println(data[i]);
		}
		System.out.println("return size = "+ftp.FtpPath_FileName());
		System.out.println("return size = "+ftp.FtpPath_DirName());
//		System.out.println("return size = "+ftp.FTP_PathFileSize("/home/mysql-5.6.51/my.cnf"));
//		System.out.println("return size = "+ftp.FTP_PathFileSize("/root/tengine-2.1.0/configure"));
//		System.out.println("return Type = "+ftp.FTP_PathFileType("/home/CentOS-6.10-x86_64-bin-DVD1.iso"));
//		System.out.println("return Type = "+ftp.FTP_PathFileType("/home/zookpeer"));
	}

}
