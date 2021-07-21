package GUI_Tool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileSystemView;

import sun.awt.shell.ShellFolder;

@SuppressWarnings("serial")
public class FileIconExtractor extends JFrame implements ActionListener{
	
	private JButton getIconBtn = new JButton("get Icon");
	
	private JPanel iconPanel = new JPanel();
	
	private JTextField extField = new JTextField();
	
	private JLabel smallIconLabel = new JLabel("small Icon here");
	
	private JLabel bigIconLabel = new JLabel("big Icon here");

	
	public FileIconExtractor() {
		
		this.setSize(600, 450);	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		getIconBtn.setActionCommand("GETICON");
		
		getIconBtn.addActionListener(this);
		
		iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.Y_AXIS));
		
		iconPanel.add(smallIconLabel);
		
		iconPanel.add(bigIconLabel);
		
		this.add(extField, BorderLayout.NORTH);	
		this.add(iconPanel, BorderLayout.CENTER);	
		this.add(getIconBtn, BorderLayout.SOUTH);
		
		
		this.setVisible(true);
}

public void actionPerformed(ActionEvent e) {
	
	if (e.getActionCommand().equals("GETICON")) {
		String ext = extField.getText();
		File file;
		try
		{
			file = File.createTempFile("icon", "." + ext);		
			FileSystemView view = FileSystemView.getFileSystemView();		
			Icon smallIcon = view.getSystemIcon(file);	
			ShellFolder shellFolder = ShellFolder.getShellFolder(file);		
			Icon bigIcon = new ImageIcon(shellFolder.getIcon(true));		
			setIconLabel(smallIcon, bigIcon);		
			file.delete();		
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

	}
}

private void setIconLabel(Icon smallIcon, Icon bigIcon) {
	
	smallIconLabel.setIcon(smallIcon);
	bigIconLabel.setIcon(bigIcon);
}


public static class Test_test {
	
	public Test_test(){
	
		Frame frame=new Frame("东西南北中布局");
	    frame.setVisible(true);
	    frame.setSize(500,500);
	    frame.setResizable(true);//使窗口不可拉动或放大
	    
	    //设置布局方式为东西南北中布局
	    frame.setLayout(new BorderLayout());
	   
	   
	    
	    //设置监听事件，使得窗口可关闭
	    frame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            System.exit(0);
	        }
	    });
	    
	    // 创建一个内部窗口
	    JInternalFrame internalFrame = new JInternalFrame();
	
	    // 设置窗口的宽高
	    internalFrame.setSize(200, 200);
	    // 设置窗口的显示位置
	    internalFrame.setLocation(50, 50);
	    // 内部窗口的关闭按钮动作默认就是销毁窗口，所有不用设置
	    internalFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    
	    internalFrame.setVisible(true);
	    internalFrame.setPreferredSize(new Dimension(1300,200));
	    
	    //new五个button
	    Button east=new Button("East");
	    Button west=new Button("West");
	    Button south=new Button("South");
	    Button north=new Button("North");
	    Button center=new Button("Center");
	    
	    //将五个button加入到frame窗口中
	    frame.add(east,BorderLayout.EAST);
	    frame.add(west,BorderLayout.WEST);
	    frame.add(south,BorderLayout.SOUTH);
	    frame.add(north,BorderLayout.NORTH);
	    frame.add(center,BorderLayout.CENTER);
	}
}


public static class test_Iccon{
	
	public test_Iccon(){
		
		JFrame jFrame = new JFrame();
		jFrame.setBounds( 200, 200, 200, 200);
		File file = new File( "C:\\Users\\Administrator\\Desktop\\临时文件\\LinkList");
		jFrame.setIconImage(((ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file)).getImage());
		jFrame.setVisible(true);

	}
}


static class GUI9 {
	private Frame jf;
	private TextField textField;
	private Button button;
	private JScrollPane jsp;
	private DefaultListModel dflm;
	private JPanel jpanel;
	private JList jlist;
	
	GUI9(){
		init();
	}
	public void init()
	{
		jpanel = new JPanel();
		
		jf = new Frame("Galesaur");				//创建窗体对象
		jf.setBounds(300,100,600,500);			//设置窗体位置和大小
		jf.setLayout(new FlowLayout()); 		//设置窗体布局为流式布局
		jf.setVisible(true); 					//设置窗体可见
		
		textField = new TextField(60);					//创建单行文本对象60长度大小字符
		button = new Button("转到");  					//创建按钮对象 
		jsp = new JScrollPane(25,70);					//创建滚动文本对象25行 70列
		dflm = new DefaultListModel();					// 默认的 list列表显示方式
		
		jf.add(textField); 		// 单行文本添加到窗体上
		jf.add(button);			// 按钮添加到窗体上
		jf.add(jsp); 			// 滚动文本添加到窗体上
		
		myEvent();				// 加载事件处理
		
		jlist.setCellRenderer(new MyListRenderer());	//设置渲染器为我们自己的
		jsp.setViewportView(jlist);
		
		
	}
	
	/**
	 * 	关键代码 :	将 系统路径下的目录或文件 图表提出来。 
	 *  		File file = new File( "C:\\Users\\Administrator\\Desktop\\临时文件\\LinkList");
	 *	jFrame.setIconImage(((ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file)).getImage());
	 *	
	 *	File dir = new File(dirPath);			//将字符串dirpath封装撑文件	
	 *	// 路径下存在文件 且是目录，列出目录下的文件和目录，添加到 多行文本框中.
		if(dir.exists() && dir.isDirectory()){
			ta.setText(null);					//没打开一个目录前清空多行文本内容
			String[] names = dir.list();		//列出目录下文件和目录存 放进字符串数组中
			for(String name:names)				// 遍历数组
				ta.append(name + "\r\n"); 		//追加文本内容并换行 
		}
	 * 基础的这样还达不到 我们想要的结果！ -- 目标列表： 系统图标+文件名。
	 *	
	 	DefaultListModel dflm = new DefaultListModel();
		dfl.addElement("添加的文件名 ");
		
		// 重写默认渲染器
		JList jl = new JList(dflm);
		jl.setCellRenderer(new CRTest());		//设置渲染器为我们自己的
	 *	
	 *	
	 *	public class CRTest extends DefaultListCellRenderer {
	 *		public Component getListCellRendererComponent(JList<? extends Object> list,
	 *						Object value,int index,boolean isSelected,boolean cellHasFocus) {
				setText(value.toString());								//设置文字
				if(value.toString().equals("a")) {						//判断value（列表值）来分情况设置不同图标
					ImageIcon ico=new ImageIcon("res\\testicon.png");	//实例化一个ImageIcon对象
			....
	 */
	private void myEvent() {
		//按钮事件监听器
		button.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) {
				String dirPath  = textField.getText();		//获取单行文本内容
				File dir = new File(dirPath);				//将字符串dirpath封装撑文件
				
				// 路径下存在文件 且是目录，列出目录下的文件和目录，添加到 多行文本框中.
				if(dir.exists() && dir.isDirectory()){
					jsp.setToolTipText(null);				//没打开一个目录前清空多行文本内容
					String[] names = dir.list();			//列出目录下文件和目录存 放进字符串数组中
					for(String name:names){					// 遍历数组
						dflm.addElement(name); 				//追加文本内容并换行 
					}
					jlist  = new JList(dflm);
					
				}
			}
		});
		
		//窗体关闭监听器
        jf.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });	
	}

}

	
	 public static void main(String[] args){
//		 new FileIconExtractor();
//		 new Test_test();
//		 new test_Iccon();
//		 new GUI9();
	 }
}
