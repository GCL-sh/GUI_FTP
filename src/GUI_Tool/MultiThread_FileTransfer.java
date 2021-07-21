package GUI_Tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

import sun.net.TelnetInputStream;  
import sun.net.TelnetOutputStream; 


	/** *//**  
	* 支持断点续传的FTP实用类  
	* @version 0.1 实现基本断点上传下载  
	* @version 0.2 实现上传下载进度汇报  
	* @version 0.3 实现中文目录创建及中文文件创建，添加对于中文的支持  
	*/  
	public class MultiThread_FileTransfer  implements Runnable{   

	//枚举类UploadStatus代码 

	public enum UploadStatus { 
	Create_Directory_Fail,   			// 远程服务器目录创建失败 
	Create_Directory_Success, 			// 远程服务器目录创建成功 
	Upload_New_File_Success, 			// 上传新文件成功 
	Upload_New_File_Failed,   			// 上传新文件失败 
	File_Exits,      					// 文件已经存在 
	Remote_Bigger_Local,   				// 远程文件大于本地文件 
	Upload_From_Break_Success, 			// 断点续传成功 
	Upload_From_Break_Failed, 			// 断点续传失败 
	Delete_Remote_Success,				// 删除远程文件成功
	Delete_Remote_Faild;   				// 删除远程文件失败 
	} 

	//枚举类DownloadStatus代码 
	public enum DownloadStatus { 
	Remote_File_Noexist, 				// 远程文件不存在 
	Local_Bigger_Remote, 				// 本地文件大于远程文件 
	Download_From_Break_Success, 		// 断点下载文件成功 
	Download_From_Break_Failed,   		// 断点下载文件失败 
	Download_New_Success,    			// 全新下载文件成功 
	Download_New_Failed,    			// 全新下载文件失败 
	Delete_File_Success,				// 删除远程文件成功
	Delete_File_Failed;					// 删除远程文件失败 
	} 

	    public FTPClient ftpClient = new FTPClient();
	    private sun.net.ftp.FtpClient client=null; 
	    public ArrayList<String> DirFilelist = new ArrayList<>();
	    public ArrayList<String> Dirlist = new ArrayList<>();
	    public int INDEX0;
	    private String HostIP,UserName,Pwd,file1,file2;
	    private int Port;
	    
	    public FTPClient getFtpClient(){
	    	return ftpClient;
	    }
	    
	    public MultiThread_FileTransfer(){}								
//	    public MultiThread_FileTransfer(String _ftpURL,String _username,String _pwd,String _ftpport,String _file1,String _file2 ){   
//	        //设置将过程中使用到的命令输出到控制台   
//	     ftpURL = _ftpURL; 
//	     username = _username; 
//	     pwd = _pwd; 
//	     ftpport = _ftpport; 
//	     file1 = _file1; 
//	     file2 = _file2; 
//	        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));   
//	    }   
	       
	    /** *//**  
	     * 连接到FTP服务器  
	     * @param hostname 主机名  
	     * @param port 端口  
	     * @param username 用户名  
	     * @param password 密码  
	     * @return 是否连接成功  
	     * @throws IOException  
	     */  
	    public boolean connect(String hostIP,String userName,String password) throws IOException {
	    	this.HostIP = hostIP;
	    	this.Port = 21;
	    	this.UserName = userName;
	    	this.Pwd = password;
	        ftpClient.connect(hostIP, Port);   
	        ftpClient.setControlEncoding("GBK");   
	        if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){   
	            if(ftpClient.login(userName, password)){   
	                return true;   
	            }   
	        }   
	        disconnect();   
	        return false;   
	    }   
	       
	    /** *//**  
	     * 从FTP服务器下载文件,支持断点续传，上传百分比汇报  
	     * @param remote 远程文件路径  
	     * @param local 本地文件路径  
	     * @return 下载的状态  
	     * @throws IOException  
	     */  
	    public DownloadStatus download(String remote,String local) throws IOException{   
	        //设置被动模式   
	        ftpClient.enterLocalPassiveMode();   
	        //设置以二进制方式传输   
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
	        DownloadStatus result;   
	           
	        //检查远程文件是否存在   
	        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"),"iso-8859-1"));   
	        if(files.length != 1){   
	            System.out.println("远程文件不存在");   
	            return DownloadStatus.Remote_File_Noexist;   
	        }   
	           
	        long lRemoteSize = files[0].getSize();   
	        File f = new File(local);   
	        //本地存在文件，进行断点下载   
	        if(f.exists()){   
	            long localSize = f.length();   
	            //判断本地文件大小是否大于远程文件大小   
	            if(localSize >= lRemoteSize){   
	                System.out.println("本地文件大于远程文件，下载中止");   
	                return DownloadStatus.Local_Bigger_Remote;   
	            }   
	               
	            //进行断点续传，并记录状态   
	            FileOutputStream out = new FileOutputStream(f,true);   
	            ftpClient.setRestartOffset(localSize);   
	            InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
	            byte[] bytes = new byte[1024];   
	            long step = lRemoteSize /100;   
	            long process=localSize /step;   
	            int c;   
	            while((c = in.read(bytes))!= -1){   
	                out.write(bytes,0,c);   
	                localSize+=c;   
	                long nowProcess = localSize /step;   
	                if(nowProcess > process){   
	                    process = nowProcess;   
	                    if(process % 10 == 0)   
	                        System.out.println("下载进度："+process);   
	                    //TODO 更新文件下载进度,值存放在process变量中   
	                }   
	            }   
	            in.close();   
	            out.close();   
	            boolean isDo = ftpClient.completePendingCommand();   
	            if(isDo){   
	                result = DownloadStatus.Download_From_Break_Success;   
	            }else {   
	                result = DownloadStatus.Download_From_Break_Failed;   
	            }   
	        }else {  
	        	// 本地下无该文件，直接输入流传过来下载.
	            OutputStream out = new FileOutputStream(f);   
	            InputStream in= ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
	            byte[] bytes = new byte[1024];   
	            long step = lRemoteSize /100;   
	            long process=0;   
	            long localSize = 0L;   
	            int c;   
	            while((c = in.read(bytes))!= -1){   
	                out.write(bytes, 0, c);   
	                localSize+=c;   
	                long nowProcess = localSize /step;   
	                if(nowProcess > process){   
	                    process = nowProcess;   
	                    if(process % 10 == 0)   
	                        System.out.println("下载进度："+process);   
	                    //TODO 更新文件下载进度,值存放在process变量中   
	                }   
	            }   
	            in.close();   					// 必须先执行close(),才能completePendingCommand()得到返回值；
	            out.close();   					// 不然下一次下载文件成功后，再次获取inputStream就会返回 null。
	            boolean upNewStatus = ftpClient.completePendingCommand();   
	            if(upNewStatus){   
	                result = DownloadStatus.Download_New_Success;   
	            }else {   
	                result = DownloadStatus.Download_New_Failed;   
	            }   
	        }   
	        return result;   
	    }   
	    
	    
	    public DownloadStatus DownFtpDirFile(String remote,String local) throws IOException {
	    	// 修改默认访问FTP 路径到当前remote下.
	    	ftpClient.changeWorkingDirectory(new String(remote.getBytes("GBK"),"iso-8859-1"));
	        //设置被动模式   
	        ftpClient.enterLocalPassiveMode();   
	        //设置以二进制方式传输   
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
	        DownloadStatus result = null;   
	           
	        //检查远程文件是否存在   
	        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"),"iso-8859-1"));   
//	        if(files.length != 1){   
//	            System.out.println("远程 目录/文件 不存在");   
//	            return DownloadStatus.Remote_File_Noexist;   
//	        }   
	        
	        files = ftpClient.listDirectories();								// 判断远程FTP上的Path 是目录还是文件.
	        if(files.length<1){													// 说明此时路径下只有文件没有子目录.
	        	files = ftpClient.listFiles();									// 获取文件信息.
	        	long lRemoteSize = files[0].getSize();  						// 获取文件大小.
		        File localFile = new File(local);   							// 获取本地目录下应该生成的文件名.
		        
		        if(localFile.exists()){   										// 本地存在文件，进行断点下载   
		            long localSize = localFile.length();   
		            if(localSize >= lRemoteSize){   							// 判断本地文件大小是否大于远程文件大小   
//		                System.out.println("本地文件大于远程文件，下载中止");   
		                return DownloadStatus.Local_Bigger_Remote;   
		            }
		            FileOutputStream out = new FileOutputStream(localFile,true);   	// 进行断点续传，并记录状态   
		            ftpClient.setRestartOffset(localSize);   
		            InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
		            byte[] bytes = new byte[4096];   
		            long step = lRemoteSize /100;   
		            long process=localSize /step;   
		            int c;   
		            while((c = in.read(bytes))!= -1){   						// 循环写入
		                out.write(bytes,0,c);   
		                localSize+=c;   
		                long nowProcess = localSize /step;   
		                if(nowProcess > process){   
		                    process = nowProcess;   
		                    if(process % 10 == 0)   
		                        System.out.println("下载进度："+process);   
		                    //TODO 更新文件下载进度,值存放在process变量中   
		                }   
		            }
		            in.close();   												// 必须释放
		            out.close();   
		            boolean isDo = ftpClient.completePendingCommand();   		// 获取传输文件状态
		            if(isDo){   
		                result = DownloadStatus.Download_From_Break_Success;   
		            }else {   
		                result = DownloadStatus.Download_From_Break_Failed;   
		            }
		        }else {
		            OutputStream out = new FileOutputStream(localFile);   		// 本地下无该文件，直接输入流传过来下载.
		            InputStream in= ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
		            byte[] bytes = new byte[4096];   
		            long step = lRemoteSize /100;   
		            long process=0;   
		            long localSize = 0L;   
		            int c;   
		            while((c = in.read(bytes))!= -1){   
		                out.write(bytes, 0, c);   
		                localSize+=c;   
		                long nowProcess = localSize /step;   
		                if(nowProcess > process){   
		                    process = nowProcess;   
		                    if(process % 10 == 0)   
		                        System.out.println("下载进度："+process);   
		                    //TODO 更新文件下载进度,值存放在process变量中   
		                }   
		            }   
		            in.close();   								// 必须先执行close(),才能completePendingCommand()得到返回值；
		            out.close();   								// 不然下一次下载文件成功后，再次获取inputStream就会返回 null。
		            boolean upNewStatus = ftpClient.completePendingCommand();   
		            if(upNewStatus){   
		                result = DownloadStatus.Download_New_Success;   
		            }else {   
		                result = DownloadStatus.Download_New_Failed;   
		            }
		        }
	        }else{																// 路径下有子文件
	        	INDEX0 = remote.lastIndexOf("/");
//	        	System.out.println(remote.substring(INDEX0));
	        	ArrayList<String> Dir = FtpRemote_CreateDir(remote);			// 获取必须创建文件目录的list. 
	        	ftpClient.changeWorkingDirectory(remote);
	        	ArrayList<String> list = FtpRemote_DirFileName(remote);			// 递归调用函数 获取文件名放进 ArrayList中，
	        	ftpClient.changeWorkingDirectory(remote);
//	        	
	        	for(String name:Dir){											// 再遍历数组获取全部文件名。
	        		File dir = new File(local+name);							// 本地创建目录
//	        		System.out.println(dir);
	        		if(!dir.mkdirs())
	        			System.out.println("创建目录失败");
	        	}																// 子目录已经创建好，先普通的文件传输.
	        	for(String name:list){
	        		OutputStream out = new FileOutputStream(
	        							local+name.substring(INDEX0));   		// 本地下无该文件，直接输入流传过来下载.
		            InputStream in = ftpClient.retrieveFileStream(new String
		            				(name.getBytes("GBK"),"iso-8859-1"));
		            byte[] bytes = new byte[4096];   
//		            long step = lRemoteSize /100;   
//		            long process=0;   
//		            long localSize = 0L;   
		            int c;   
		            while((c = in.read(bytes))!= -1){   
		                out.write(bytes, 0, c);   
//		                						/ 到底需不需要进度条？ 是总体进度条？还是单个文件进度条？
//		                localSize+=c;   
//		                long nowProcess = localSize /step;   
//		                if(nowProcess > process){   
//		                    process = nowProcess;   
//		                    if(process % 10 == 0)   
//		                        System.out.println("下载进度："+process);   
		                    //TODO 更新文件下载进度,值存放在process变量中   
//		                }   
		            }   
		            in.close();   								// 必须先执行close(),才能completePendingCommand()得到返回值；
		            out.close();   								// 不然下一次下载文件成功后，再次获取inputStream就会返回 null。
		            boolean upNewStatus = ftpClient.completePendingCommand();   
		            if(upNewStatus){   
		                result = DownloadStatus.Download_New_Success;   
		            }else {   
		                result = DownloadStatus.Download_New_Failed;   
		            }
	        	}
	        }
	        return result;   
	    }
	    
	    /**
	     * 
	     * @param RemoteDirPath 	Ftp远程目录的路径
	     * @return					目录下所有的文件名
	     * @throws IOException 
	     */
	    public ArrayList<String> FtpRemote_DirFileName(String RemoteDirPath) throws IOException{
	    	String filePath = RemoteDirPath;
	    	FTPFile[] files = ftpClient.listFiles();
	    	if(files.length>0){   		
	    		for(int i=0;i<files.length;i++){											// 循环文件名判断是否存在子目录
//	    			if(Name.getName().equals(".")||Name.getName().equals(".."))
//	    				continue;
//	    			System.out.println("=="+RemoteDirPath+"/"+files[i].getName());
	    			if((files[i].isDirectory())){											// 如果是目录，则递归调用，查找里面所有文件
	    				ftpClient.changeWorkingDirectory(filePath+"/"+files[i].getName());	// 改变当前路径
	    				FtpRemote_DirFileName(filePath+"/"+files[i].getName());				// 递归调用
	    			}else{
	    				DirFilelist.add(RemoteDirPath+"/"+files[i].getName());				// 添加文件进数组
	    			}
	    		}
	    	}
	    	return DirFilelist;
	    }
	    
	    /**
	     * @param CreateDir		获取必须创建目录的子文件集合.
	     * @return				返回子文件集合.
	     * @throws IOException
	     */
	    public ArrayList<String> FtpRemote_CreateDir(String CreateDir) throws IOException{
	    	String filePath = CreateDir;
	    	FTPFile[] files = ftpClient.listDirectories();									// 是否有子目录存在
//	    	Dirlist.clear();
	    	if(files.length>0){   		
	    		for(int i=0;i<files.length;i++){											// 循环文件名判断是否存在子目录
	    			if((files[i].isDirectory())){											// 如果是目录，则递归调用
	    				ftpClient.changeWorkingDirectory(filePath+"/"+files[i].getName());	// 改变当前路径
//	    				Dirlist.add(filePath+"/"+files[i].getName());
//	    				System.out.println(filePath.substring(INDEX0));
	    				Dirlist.add((filePath+"/"+files[i].getName()).substring(INDEX0));	// 去掉多级父目录，只保留子目录以及后续目录名
	    				FtpRemote_CreateDir(filePath+"/"+files[i].getName());				// 递归调用
	    			}
	    		}
	    	}
	    	return Dirlist;
	    }
	    
	       
	    /** *//**  
	     * 上传文件到FTP服务器，支持断点续传  
	     * @param local 本地文件名称，绝对路径  
	     * @param remote 远程文件路径，使用/home/directory1/file.txt 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构  
	     * @return 上传结果  
	     * @throws IOException  
	     */  
	    public UploadStatus upload(String local,String remote) throws IOException{   
	        //设置PassiveMode传输   
	        ftpClient.enterLocalPassiveMode();   
	        //设置以二进制流的方式传输   
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
	        ftpClient.setControlEncoding("GBK");   
	        UploadStatus result;   
	        //对远程目录的处理   
	        String remoteFileName = remote;   
	        if(remote.contains("/")){   
	            remoteFileName = remote.substring(remote.lastIndexOf("/")+1);   
	            //创建服务器远程目录结构，创建失败直接返回   
	            if(CreateDirecroty(remote, ftpClient)==UploadStatus.Create_Directory_Fail){   
	                return UploadStatus.Create_Directory_Fail;   
	            }   
	        }   
	           
	        //检查远程是否存在文件   
	        FTPFile[] files = ftpClient.listFiles(new String(remoteFileName.getBytes("GBK"),"iso-8859-1"));   
	        if(files.length == 1){   
	            long remoteSize = files[0].getSize();   
	            File f = new File(local);   
	            long localSize = f.length();   
	            if(remoteSize==localSize){   
	                return UploadStatus.File_Exits;   
	            }else if(remoteSize > localSize){   
	                return UploadStatus.Remote_Bigger_Local;   
	            }   
	               
	            //尝试移动文件内读取指针,实现断点续传   
	            result = uploadFile(remoteFileName, f, ftpClient, remoteSize);   
	               
	            //如果断点续传没有成功，则删除服务器上文件，重新上传   
	            if(result == UploadStatus.Upload_From_Break_Failed){   
	                if(!ftpClient.deleteFile(remoteFileName)){   
	                    return UploadStatus.Delete_Remote_Faild;   
	                }   
	                result = uploadFile(remoteFileName, f, ftpClient, 0);   
	            }   
	        }else {   
	            result = uploadFile(remoteFileName, new File(local), ftpClient, 0);   
	        }   
	        return result;   
	    }
	    
	    
	    /** *//**  
	     * 断开与远程服务器的连接  
	     * @throws IOException  
	     */  
	    public void disconnect() throws IOException{   
	        if(ftpClient.isConnected()){   
	            ftpClient.disconnect();   
	        }   
	    }   
	       
	    /** *//**  
	     * 递归创建远程服务器目录  
	     * @param remote 远程服务器文件绝对路径  
	     * @param ftpClient FTPClient 对象  
	     * @return 目录创建是否成功  
	     * @throws IOException  
	     */  
	    public UploadStatus CreateDirecroty(String remote,FTPClient ftpClient) throws IOException{   
	        UploadStatus status = UploadStatus.Create_Directory_Success;   
	        String directory = remote.substring(0,remote.lastIndexOf("/")+1);   
	        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"),"iso-8859-1"))){   
	            //如果远程目录不存在，则递归创建远程服务器目录   
	            int start=0;   
	            int end = 0;   
	            if(directory.startsWith("/")){   
	                start = 1;   
	            }else{   
	                start = 0;   
	            }   
	            end = directory.indexOf("/",start);   
	            while(true){   
	                String subDirectory = new String(remote.substring(start,end).getBytes("GBK"),"iso-8859-1");   
	                if(!ftpClient.changeWorkingDirectory(subDirectory)){   
	                    if(ftpClient.makeDirectory(subDirectory)){   
	                        ftpClient.changeWorkingDirectory(subDirectory);   
	                    }else {   
	                        System.out.println("创建目录失败");   
	                        return UploadStatus.Create_Directory_Fail;   
	                    }   
	                }   
	                   
	                start = end + 1;   
	                end = directory.indexOf("/",start);   
	                   
	                //检查所有目录是否创建完毕   
	                if(end <= start){   
	                    break;   
	                }   
	            }   
	        }   
	        return status;   
	    }   
	       
	    /** *//**  
	     * 上传文件到服务器,新上传和断点续传  
	     * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变  
	     * @param localFile 本地文件 File句柄，绝对路径  
	     * @param processStep 需要显示的处理进度步进值  
	     * @param ftpClient FTPClient 引用  
	     * @return  
	     * @throws IOException  
	     */  
	    public UploadStatus uploadFile(String remoteFile,File localFile,FTPClient ftpClient,long remoteSize) throws IOException{   
	        UploadStatus status;   
	        //显示进度的上传   
	        long step = localFile.length() / 100;   
	        long process = 0;   
	        long localreadbytes = 0L;   
	        RandomAccessFile raf = new RandomAccessFile(localFile,"r");   
	        OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"),"iso-8859-1"));   
	        //断点续传   
	        if(remoteSize>0){   
	            ftpClient.setRestartOffset(remoteSize);   
	            process = remoteSize /step;   
	            raf.seek(remoteSize);   
	            localreadbytes = remoteSize;   
	        }   
	        byte[] bytes = new byte[1024];   
	        int c;   
	        while((c = raf.read(bytes))!= -1){   
	            out.write(bytes,0,c);   
	            localreadbytes+=c;   
	            if(localreadbytes / step != process){   
	                process = localreadbytes / step;   
	                System.out.println("上传进度:" + process);   
	                //TODO 汇报上传状态   
	            }   
	        }   
	        out.flush();   
	        raf.close();   
	        out.close();   
	        boolean result =ftpClient.completePendingCommand();   
	        if(remoteSize > 0){   
	            status = result?UploadStatus.Upload_From_Break_Success:UploadStatus.Upload_From_Break_Failed;   
	        }else {   
	            status = result?UploadStatus.Upload_New_File_Success:UploadStatus.Upload_New_File_Failed;   
	        }   
	        return status;   
	    }   
	    
	    
	    public UploadStatus uploadFile(String localFile,String remoteDir,FTPClient ftpClient) throws IOException{   
	        UploadStatus status;
	        String remoteFile = localFile.substring(localFile.lastIndexOf("/")+1);
	        long localFileSize =  new File(localFile).length();
//	        System.out.println(localFileSize);
//	        System.out.println(remoteFile);
//	        System.out.println(remoteDir);
	        ftpClient.changeWorkingDirectory(remoteDir);					// 设置上传路径
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);					// 修改上传文件格式二进制流 
	        ftpClient.enterLocalPassiveMode();								// 设置被动模式  
            
//	        // 断点续传,先不考虑。   
//	        if(remoteSize>0){   
//	            ftpClient.setRestartOffset(remoteSize);   
//	            process = remoteSize /step;   
//	            raf.seek(remoteSize);   
//	            localreadbytes = remoteSize;   
//	        }   
            
            long localreadbytes = 0L; 										// 已上传文件大小
            RandomAccessFile raf = new RandomAccessFile(localFile, "r");
            OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"), "iso-8859-1"));
            byte[] bytes = new byte[4096];									// buff区设置大小
            int c;

            long start = System.currentTimeMillis();
            while ((c = raf.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                localreadbytes += c;
                System.out.println("啥玩意？-04");
                if(localreadbytes<localFileSize){							//计算上传进度
                	System.out.println("readBytes ="+localreadbytes+"\tlocalFileSize ="+localFileSize);
                }
            }
            System.out.println("it consumes " +(System.currentTimeMillis() - start) + "ms");
            out.flush();
	        raf.close();   
	        out.close();   
	        boolean result =ftpClient.completePendingCommand();   
	        if(result){   
	            status = UploadStatus.Upload_New_File_Success;   
	        }else {   
	            status = UploadStatus.Upload_New_File_Failed;   
	        }   
	        return status;   
	    }   
	       
	    
	@Test
	public void run() { 
	  // TODO Auto-generated method stub 
	    
		 try {
			 MultiThread_FileTransfer FtpCon = new MultiThread_FileTransfer();
			 if(FtpCon.connect("192.168.10.166", "root", "centos")){
				System.out.println("连接成功..."); 
			 }else
				 System.out.println("连接失败..."); 
				 
//			 FtpCon.ftpClient.makeDirectory(new String("电视剧".getBytes("GBK"),"iso-8859-1"));   
//			 FtpCon.ftpClient.changeWorkingDirectory(new String("电视剧".getBytes("GBK"),"iso-8859-1"));   
//			 FtpCon.ftpClient.makeDirectory(new String("MyDir".getBytes("GBK"),"iso-8859-1"));   
//	           System.out.println(FtpCon.upload("E:/成人本科课程/java程序设计复习课.mp4", "java程序设计复习课.mp4"));   
//	           System.out.println(FtpCon.upload("E:/成人本科课程/java程序设计复习课.mp4","/MyDir/java程序设计复习课.mp4"));   
//	           System.out.println(FtpCon.download("/电视剧/MyDir/java程序设计复习课.mp4", "E:/java复习课.mp4")); 
//			   System.out.println("测试 : "+FtpCon.DownFtpDirFile("/opt/kafka", "E:/迅雷下载")); 
			   System.out.println("测试 : "+FtpCon.uploadFile("C:/Users/Administrator/Desktop/jdk api 1.8_google.CHM",
					   "/home", FtpCon.getFtpClient())); 
	         FtpCon.disconnect();   
		 } catch (IOException e) {   
		     System.out.println("连接FTP出错："+e.getMessage());   
		 }   
	}   
	
	
	
}
