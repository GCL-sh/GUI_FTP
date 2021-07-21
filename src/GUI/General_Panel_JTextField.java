package GUI;

import java.io.File;
import java.util.ArrayList;
import GUI_Tool.SizeConversion;

public class General_Panel_JTextField {
//	private Vector<Vector<String>> MazeList;
	private ArrayList<String> MazeList;
	
	
	public General_Panel_JTextField(){}
	
/**
 * 获取路径下的目录，文件名、大小、类型、修改时间。
 * @param 	dirPath
 * @return	ArrayList<String>
 */

//	public Vector<Vector<String>> getVectorJTable(String dirPath){
	public ArrayList<String> getVectorJTable(String dirPath){
				
		SizeConversion sizeC = new SizeConversion();
		File dir = new File(dirPath);						//将字符串dirpath封装撑文件
		System.out.println(dirPath);
		
//      String[] Names = dir.list();
//      for(String name:Names){
//			System.out.println(name);
//      }
		
		MazeList = new ArrayList <String>();

  	// 路径下存在文件 且是目录，列出目录下的文件和目录，添加到 多行文本框中.
		if(dir.exists() && dir.isDirectory()){
			String[] names = dir.list();					//列出目录下文件和目录存 放进字符串数组中
				
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
 * 	二维数组：Vector<Vector<String>> MazeList; ArrayList<ArrayList<String>> MazeList;	
 * 	发现	MazeList.add( index i,Vector<E> tmp );	添加失败， 决定放弃。
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
