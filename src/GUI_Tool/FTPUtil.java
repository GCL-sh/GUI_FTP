package GUI_Tool;

import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

 
 
public class FTPUtil {
	private static Logger logger = Logger.getLogger(FTPUtil.class);
 
	/**
	 * ��ȡFTPClient����
	 * @param ftpHost FTP����������
	 * @param ftpPassword FTP ��¼����
	 * @param ftpUserName FTP��¼�û���
	 * @param ftpPort FTP�˿� Ĭ��Ϊ21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, String ftpPassword,
			String ftpUserName, int ftpPort) {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);			// ����FTP������
			ftpClient.login(ftpUserName, ftpPassword);		// ��½FTP������
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("δ���ӵ�FTP���û������������");
				ftpClient.disconnect();
			} else {
				logger.info("FTP���ӳɹ���");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			logger.info("FTP��IP��ַ���ܴ�������ȷ���á�");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("FTP�Ķ˿ڴ���,����ȷ���á�");
		}
		return ftpClient;
	}
}