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
	* ֧�ֶϵ�������FTPʵ����  
	* @version 0.1 ʵ�ֻ����ϵ��ϴ�����  
	* @version 0.2 ʵ���ϴ����ؽ��Ȼ㱨  
	* @version 0.3 ʵ������Ŀ¼�����������ļ���������Ӷ������ĵ�֧��  
	*/  
	public class MultiThread_FileTransfer  implements Runnable{   

	//ö����UploadStatus���� 

	public enum UploadStatus { 
	Create_Directory_Fail,   			// Զ�̷�����Ŀ¼����ʧ�� 
	Create_Directory_Success, 			// Զ�̷�����Ŀ¼�����ɹ� 
	Upload_New_File_Success, 			// �ϴ����ļ��ɹ� 
	Upload_New_File_Failed,   			// �ϴ����ļ�ʧ�� 
	File_Exits,      					// �ļ��Ѿ����� 
	Remote_Bigger_Local,   				// Զ���ļ����ڱ����ļ� 
	Upload_From_Break_Success, 			// �ϵ������ɹ� 
	Upload_From_Break_Failed, 			// �ϵ�����ʧ�� 
	Delete_Remote_Success,				// ɾ��Զ���ļ��ɹ�
	Delete_Remote_Faild;   				// ɾ��Զ���ļ�ʧ�� 
	} 

	//ö����DownloadStatus���� 
	public enum DownloadStatus { 
	Remote_File_Noexist, 				// Զ���ļ������� 
	Local_Bigger_Remote, 				// �����ļ�����Զ���ļ� 
	Download_From_Break_Success, 		// �ϵ������ļ��ɹ� 
	Download_From_Break_Failed,   		// �ϵ������ļ�ʧ�� 
	Download_New_Success,    			// ȫ�������ļ��ɹ� 
	Download_New_Failed,    			// ȫ�������ļ�ʧ�� 
	Delete_File_Success,				// ɾ��Զ���ļ��ɹ�
	Delete_File_Failed;					// ɾ��Զ���ļ�ʧ�� 
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
//	        //���ý�������ʹ�õ����������������̨   
//	     ftpURL = _ftpURL; 
//	     username = _username; 
//	     pwd = _pwd; 
//	     ftpport = _ftpport; 
//	     file1 = _file1; 
//	     file2 = _file2; 
//	        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));   
//	    }   
	       
	    /** *//**  
	     * ���ӵ�FTP������  
	     * @param hostname ������  
	     * @param port �˿�  
	     * @param username �û���  
	     * @param password ����  
	     * @return �Ƿ����ӳɹ�  
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
	     * ��FTP�����������ļ�,֧�ֶϵ��������ϴ��ٷֱȻ㱨  
	     * @param remote Զ���ļ�·��  
	     * @param local �����ļ�·��  
	     * @return ���ص�״̬  
	     * @throws IOException  
	     */  
	    public DownloadStatus download(String remote,String local) throws IOException{   
	        //���ñ���ģʽ   
	        ftpClient.enterLocalPassiveMode();   
	        //�����Զ����Ʒ�ʽ����   
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
	        DownloadStatus result;   
	           
	        //���Զ���ļ��Ƿ����   
	        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"),"iso-8859-1"));   
	        if(files.length != 1){   
	            System.out.println("Զ���ļ�������");   
	            return DownloadStatus.Remote_File_Noexist;   
	        }   
	           
	        long lRemoteSize = files[0].getSize();   
	        File f = new File(local);   
	        //���ش����ļ������жϵ�����   
	        if(f.exists()){   
	            long localSize = f.length();   
	            //�жϱ����ļ���С�Ƿ����Զ���ļ���С   
	            if(localSize >= lRemoteSize){   
	                System.out.println("�����ļ�����Զ���ļ���������ֹ");   
	                return DownloadStatus.Local_Bigger_Remote;   
	            }   
	               
	            //���жϵ�����������¼״̬   
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
	                        System.out.println("���ؽ��ȣ�"+process);   
	                    //TODO �����ļ����ؽ���,ֵ�����process������   
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
	        	// �������޸��ļ���ֱ������������������.
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
	                        System.out.println("���ؽ��ȣ�"+process);   
	                    //TODO �����ļ����ؽ���,ֵ�����process������   
	                }   
	            }   
	            in.close();   					// ������ִ��close(),����completePendingCommand()�õ�����ֵ��
	            out.close();   					// ��Ȼ��һ�������ļ��ɹ����ٴλ�ȡinputStream�ͻ᷵�� null��
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
	    	// �޸�Ĭ�Ϸ���FTP ·������ǰremote��.
	    	ftpClient.changeWorkingDirectory(new String(remote.getBytes("GBK"),"iso-8859-1"));
	        //���ñ���ģʽ   
	        ftpClient.enterLocalPassiveMode();   
	        //�����Զ����Ʒ�ʽ����   
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
	        DownloadStatus result = null;   
	           
	        //���Զ���ļ��Ƿ����   
	        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"),"iso-8859-1"));   
//	        if(files.length != 1){   
//	            System.out.println("Զ�� Ŀ¼/�ļ� ������");   
//	            return DownloadStatus.Remote_File_Noexist;   
//	        }   
	        
	        files = ftpClient.listDirectories();								// �ж�Զ��FTP�ϵ�Path ��Ŀ¼�����ļ�.
	        if(files.length<1){													// ˵����ʱ·����ֻ���ļ�û����Ŀ¼.
	        	files = ftpClient.listFiles();									// ��ȡ�ļ���Ϣ.
	        	long lRemoteSize = files[0].getSize();  						// ��ȡ�ļ���С.
		        File localFile = new File(local);   							// ��ȡ����Ŀ¼��Ӧ�����ɵ��ļ���.
		        
		        if(localFile.exists()){   										// ���ش����ļ������жϵ�����   
		            long localSize = localFile.length();   
		            if(localSize >= lRemoteSize){   							// �жϱ����ļ���С�Ƿ����Զ���ļ���С   
//		                System.out.println("�����ļ�����Զ���ļ���������ֹ");   
		                return DownloadStatus.Local_Bigger_Remote;   
		            }
		            FileOutputStream out = new FileOutputStream(localFile,true);   	// ���жϵ�����������¼״̬   
		            ftpClient.setRestartOffset(localSize);   
		            InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
		            byte[] bytes = new byte[4096];   
		            long step = lRemoteSize /100;   
		            long process=localSize /step;   
		            int c;   
		            while((c = in.read(bytes))!= -1){   						// ѭ��д��
		                out.write(bytes,0,c);   
		                localSize+=c;   
		                long nowProcess = localSize /step;   
		                if(nowProcess > process){   
		                    process = nowProcess;   
		                    if(process % 10 == 0)   
		                        System.out.println("���ؽ��ȣ�"+process);   
		                    //TODO �����ļ����ؽ���,ֵ�����process������   
		                }   
		            }
		            in.close();   												// �����ͷ�
		            out.close();   
		            boolean isDo = ftpClient.completePendingCommand();   		// ��ȡ�����ļ�״̬
		            if(isDo){   
		                result = DownloadStatus.Download_From_Break_Success;   
		            }else {   
		                result = DownloadStatus.Download_From_Break_Failed;   
		            }
		        }else {
		            OutputStream out = new FileOutputStream(localFile);   		// �������޸��ļ���ֱ������������������.
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
		                        System.out.println("���ؽ��ȣ�"+process);   
		                    //TODO �����ļ����ؽ���,ֵ�����process������   
		                }   
		            }   
		            in.close();   								// ������ִ��close(),����completePendingCommand()�õ�����ֵ��
		            out.close();   								// ��Ȼ��һ�������ļ��ɹ����ٴλ�ȡinputStream�ͻ᷵�� null��
		            boolean upNewStatus = ftpClient.completePendingCommand();   
		            if(upNewStatus){   
		                result = DownloadStatus.Download_New_Success;   
		            }else {   
		                result = DownloadStatus.Download_New_Failed;   
		            }
		        }
	        }else{																// ·���������ļ�
	        	INDEX0 = remote.lastIndexOf("/");
//	        	System.out.println(remote.substring(INDEX0));
	        	ArrayList<String> Dir = FtpRemote_CreateDir(remote);			// ��ȡ���봴���ļ�Ŀ¼��list. 
	        	ftpClient.changeWorkingDirectory(remote);
	        	ArrayList<String> list = FtpRemote_DirFileName(remote);			// �ݹ���ú��� ��ȡ�ļ����Ž� ArrayList�У�
	        	ftpClient.changeWorkingDirectory(remote);
//	        	
	        	for(String name:Dir){											// �ٱ��������ȡȫ���ļ�����
	        		File dir = new File(local+name);							// ���ش���Ŀ¼
//	        		System.out.println(dir);
	        		if(!dir.mkdirs())
	        			System.out.println("����Ŀ¼ʧ��");
	        	}																// ��Ŀ¼�Ѿ������ã�����ͨ���ļ�����.
	        	for(String name:list){
	        		OutputStream out = new FileOutputStream(
	        							local+name.substring(INDEX0));   		// �������޸��ļ���ֱ������������������.
		            InputStream in = ftpClient.retrieveFileStream(new String
		            				(name.getBytes("GBK"),"iso-8859-1"));
		            byte[] bytes = new byte[4096];   
//		            long step = lRemoteSize /100;   
//		            long process=0;   
//		            long localSize = 0L;   
		            int c;   
		            while((c = in.read(bytes))!= -1){   
		                out.write(bytes, 0, c);   
//		                						/ �����費��Ҫ�������� ����������������ǵ����ļ���������
//		                localSize+=c;   
//		                long nowProcess = localSize /step;   
//		                if(nowProcess > process){   
//		                    process = nowProcess;   
//		                    if(process % 10 == 0)   
//		                        System.out.println("���ؽ��ȣ�"+process);   
		                    //TODO �����ļ����ؽ���,ֵ�����process������   
//		                }   
		            }   
		            in.close();   								// ������ִ��close(),����completePendingCommand()�õ�����ֵ��
		            out.close();   								// ��Ȼ��һ�������ļ��ɹ����ٴλ�ȡinputStream�ͻ᷵�� null��
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
	     * @param RemoteDirPath 	FtpԶ��Ŀ¼��·��
	     * @return					Ŀ¼�����е��ļ���
	     * @throws IOException 
	     */
	    public ArrayList<String> FtpRemote_DirFileName(String RemoteDirPath) throws IOException{
	    	String filePath = RemoteDirPath;
	    	FTPFile[] files = ftpClient.listFiles();
	    	if(files.length>0){   		
	    		for(int i=0;i<files.length;i++){											// ѭ���ļ����ж��Ƿ������Ŀ¼
//	    			if(Name.getName().equals(".")||Name.getName().equals(".."))
//	    				continue;
//	    			System.out.println("=="+RemoteDirPath+"/"+files[i].getName());
	    			if((files[i].isDirectory())){											// �����Ŀ¼����ݹ���ã��������������ļ�
	    				ftpClient.changeWorkingDirectory(filePath+"/"+files[i].getName());	// �ı䵱ǰ·��
	    				FtpRemote_DirFileName(filePath+"/"+files[i].getName());				// �ݹ����
	    			}else{
	    				DirFilelist.add(RemoteDirPath+"/"+files[i].getName());				// ����ļ�������
	    			}
	    		}
	    	}
	    	return DirFilelist;
	    }
	    
	    /**
	     * @param CreateDir		��ȡ���봴��Ŀ¼�����ļ�����.
	     * @return				�������ļ�����.
	     * @throws IOException
	     */
	    public ArrayList<String> FtpRemote_CreateDir(String CreateDir) throws IOException{
	    	String filePath = CreateDir;
	    	FTPFile[] files = ftpClient.listDirectories();									// �Ƿ�����Ŀ¼����
//	    	Dirlist.clear();
	    	if(files.length>0){   		
	    		for(int i=0;i<files.length;i++){											// ѭ���ļ����ж��Ƿ������Ŀ¼
	    			if((files[i].isDirectory())){											// �����Ŀ¼����ݹ����
	    				ftpClient.changeWorkingDirectory(filePath+"/"+files[i].getName());	// �ı䵱ǰ·��
//	    				Dirlist.add(filePath+"/"+files[i].getName());
//	    				System.out.println(filePath.substring(INDEX0));
	    				Dirlist.add((filePath+"/"+files[i].getName()).substring(INDEX0));	// ȥ���༶��Ŀ¼��ֻ������Ŀ¼�Լ�����Ŀ¼��
	    				FtpRemote_CreateDir(filePath+"/"+files[i].getName());				// �ݹ����
	    			}
	    		}
	    	}
	    	return Dirlist;
	    }
	    
	       
	    /** *//**  
	     * �ϴ��ļ���FTP��������֧�ֶϵ�����  
	     * @param local �����ļ����ƣ�����·��  
	     * @param remote Զ���ļ�·����ʹ��/home/directory1/file.txt ����Linux�ϵ�·��ָ����ʽ��֧�ֶ༶Ŀ¼Ƕ�ף�֧�ֵݹ鴴�������ڵ�Ŀ¼�ṹ  
	     * @return �ϴ����  
	     * @throws IOException  
	     */  
	    public UploadStatus upload(String local,String remote) throws IOException{   
	        //����PassiveMode����   
	        ftpClient.enterLocalPassiveMode();   
	        //�����Զ��������ķ�ʽ����   
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
	        ftpClient.setControlEncoding("GBK");   
	        UploadStatus result;   
	        //��Զ��Ŀ¼�Ĵ���   
	        String remoteFileName = remote;   
	        if(remote.contains("/")){   
	            remoteFileName = remote.substring(remote.lastIndexOf("/")+1);   
	            //����������Զ��Ŀ¼�ṹ������ʧ��ֱ�ӷ���   
	            if(CreateDirecroty(remote, ftpClient)==UploadStatus.Create_Directory_Fail){   
	                return UploadStatus.Create_Directory_Fail;   
	            }   
	        }   
	           
	        //���Զ���Ƿ�����ļ�   
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
	               
	            //�����ƶ��ļ��ڶ�ȡָ��,ʵ�ֶϵ�����   
	            result = uploadFile(remoteFileName, f, ftpClient, remoteSize);   
	               
	            //����ϵ�����û�гɹ�����ɾ�����������ļ��������ϴ�   
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
	     * �Ͽ���Զ�̷�����������  
	     * @throws IOException  
	     */  
	    public void disconnect() throws IOException{   
	        if(ftpClient.isConnected()){   
	            ftpClient.disconnect();   
	        }   
	    }   
	       
	    /** *//**  
	     * �ݹ鴴��Զ�̷�����Ŀ¼  
	     * @param remote Զ�̷������ļ�����·��  
	     * @param ftpClient FTPClient ����  
	     * @return Ŀ¼�����Ƿ�ɹ�  
	     * @throws IOException  
	     */  
	    public UploadStatus CreateDirecroty(String remote,FTPClient ftpClient) throws IOException{   
	        UploadStatus status = UploadStatus.Create_Directory_Success;   
	        String directory = remote.substring(0,remote.lastIndexOf("/")+1);   
	        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"),"iso-8859-1"))){   
	            //���Զ��Ŀ¼�����ڣ���ݹ鴴��Զ�̷�����Ŀ¼   
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
	                        System.out.println("����Ŀ¼ʧ��");   
	                        return UploadStatus.Create_Directory_Fail;   
	                    }   
	                }   
	                   
	                start = end + 1;   
	                end = directory.indexOf("/",start);   
	                   
	                //�������Ŀ¼�Ƿ񴴽����   
	                if(end <= start){   
	                    break;   
	                }   
	            }   
	        }   
	        return status;   
	    }   
	       
	    /** *//**  
	     * �ϴ��ļ���������,���ϴ��Ͷϵ�����  
	     * @param remoteFile Զ���ļ��������ϴ�֮ǰ�Ѿ�������������Ŀ¼���˸ı�  
	     * @param localFile �����ļ� File���������·��  
	     * @param processStep ��Ҫ��ʾ�Ĵ�����Ȳ���ֵ  
	     * @param ftpClient FTPClient ����  
	     * @return  
	     * @throws IOException  
	     */  
	    public UploadStatus uploadFile(String remoteFile,File localFile,FTPClient ftpClient,long remoteSize) throws IOException{   
	        UploadStatus status;   
	        //��ʾ���ȵ��ϴ�   
	        long step = localFile.length() / 100;   
	        long process = 0;   
	        long localreadbytes = 0L;   
	        RandomAccessFile raf = new RandomAccessFile(localFile,"r");   
	        OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"),"iso-8859-1"));   
	        //�ϵ�����   
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
	                System.out.println("�ϴ�����:" + process);   
	                //TODO �㱨�ϴ�״̬   
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
	        ftpClient.changeWorkingDirectory(remoteDir);					// �����ϴ�·��
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);					// �޸��ϴ��ļ���ʽ�������� 
	        ftpClient.enterLocalPassiveMode();								// ���ñ���ģʽ  
            
//	        // �ϵ�����,�Ȳ����ǡ�   
//	        if(remoteSize>0){   
//	            ftpClient.setRestartOffset(remoteSize);   
//	            process = remoteSize /step;   
//	            raf.seek(remoteSize);   
//	            localreadbytes = remoteSize;   
//	        }   
            
            long localreadbytes = 0L; 										// ���ϴ��ļ���С
            RandomAccessFile raf = new RandomAccessFile(localFile, "r");
            OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"), "iso-8859-1"));
            byte[] bytes = new byte[4096];									// buff�����ô�С
            int c;

            long start = System.currentTimeMillis();
            while ((c = raf.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                localreadbytes += c;
                System.out.println("ɶ���⣿-04");
                if(localreadbytes<localFileSize){							//�����ϴ�����
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
				System.out.println("���ӳɹ�..."); 
			 }else
				 System.out.println("����ʧ��..."); 
				 
//			 FtpCon.ftpClient.makeDirectory(new String("���Ӿ�".getBytes("GBK"),"iso-8859-1"));   
//			 FtpCon.ftpClient.changeWorkingDirectory(new String("���Ӿ�".getBytes("GBK"),"iso-8859-1"));   
//			 FtpCon.ftpClient.makeDirectory(new String("MyDir".getBytes("GBK"),"iso-8859-1"));   
//	           System.out.println(FtpCon.upload("E:/���˱��ƿγ�/java������Ƹ�ϰ��.mp4", "java������Ƹ�ϰ��.mp4"));   
//	           System.out.println(FtpCon.upload("E:/���˱��ƿγ�/java������Ƹ�ϰ��.mp4","/MyDir/java������Ƹ�ϰ��.mp4"));   
//	           System.out.println(FtpCon.download("/���Ӿ�/MyDir/java������Ƹ�ϰ��.mp4", "E:/java��ϰ��.mp4")); 
//			   System.out.println("���� : "+FtpCon.DownFtpDirFile("/opt/kafka", "E:/Ѹ������")); 
			   System.out.println("���� : "+FtpCon.uploadFile("C:/Users/Administrator/Desktop/jdk api 1.8_google.CHM",
					   "/home", FtpCon.getFtpClient())); 
	         FtpCon.disconnect();   
		 } catch (IOException e) {   
		     System.out.println("����FTP����"+e.getMessage());   
		 }   
	}   
	
	
	
}
