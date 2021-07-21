package GUI;

import java.io.File;
import java.util.ArrayList;
import GUI_Tool.SizeConversion;

public class General_Panel_JTextField {
//	private Vector<Vector<String>> MazeList;
	private ArrayList<String> MazeList;
	
	
	public General_Panel_JTextField(){}
	
/**
 * ��ȡ·���µ�Ŀ¼���ļ�������С�����͡��޸�ʱ�䡣
 * @param 	dirPath
 * @return	ArrayList<String>
 */

//	public Vector<Vector<String>> getVectorJTable(String dirPath){
	public ArrayList<String> getVectorJTable(String dirPath){
				
		SizeConversion sizeC = new SizeConversion();
		File dir = new File(dirPath);						//���ַ���dirpath��װ���ļ�
		System.out.println(dirPath);
		
//      String[] Names = dir.list();
//      for(String name:Names){
//			System.out.println(name);
//      }
		
		MazeList = new ArrayList <String>();

  	// ·���´����ļ� ����Ŀ¼���г�Ŀ¼�µ��ļ���Ŀ¼����ӵ� �����ı�����.
		if(dir.exists() && dir.isDirectory()){
			String[] names = dir.list();					//�г�Ŀ¼���ļ���Ŀ¼�� �Ž��ַ���������
				
			for(int i=0; i<names.length; i++)
			{	
				dir = new File(dirPath+"/"+names[i]);
				if(dir.isFile()){
					MazeList.add(" "+names[i].substring(0,names[i].lastIndexOf(".")));
					MazeList.add(sizeC.getFiledSize(dir.length()));
//					System.out.println("MazeList"+MazeList);
				}else{
					MazeList.add(" "+names[i]);
					MazeList.add(sizeC.getFiledSize(getFolderSize(dir)));
//					System.out.println("MazeList==="+MazeList);
				}	
				MazeList.add(sizeC.getFiledIsfile(dirPath, names[i])); 				
				MazeList.add(sizeC.getFiledLastTime(dirPath, names[i]));	
			}
//			System.out.println("MazeList"+MazeList);
		}	
/**
 * 	��ά���飺Vector<Vector<String>> MazeList; ArrayList<ArrayList<String>> MazeList;	
 * 	����	MazeList.add( index i,Vector<E> tmp );	���ʧ�ܣ� ����������
 */
		return MazeList;
	}
	
	public long getFolderSize(File folder){
		long size = 0;
		for(File file:folder.listFiles()){
			if(file.isFile())
				size += file.length();
			else
				size += getFolderSize(file);
		}
		return size;
	}

	
}
