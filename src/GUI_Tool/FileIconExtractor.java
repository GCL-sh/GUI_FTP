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
	
		Frame frame=new Frame("�����ϱ��в���");
	    frame.setVisible(true);
	    frame.setSize(500,500);
	    frame.setResizable(true);//ʹ���ڲ���������Ŵ�
	    
	    //���ò��ַ�ʽΪ�����ϱ��в���
	    frame.setLayout(new BorderLayout());
	   
	   
	    
	    //���ü����¼���ʹ�ô��ڿɹر�
	    frame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            System.exit(0);
	        }
	    });
	    
	    // ����һ���ڲ�����
	    JInternalFrame internalFrame = new JInternalFrame();
	
	    // ���ô��ڵĿ��
	    internalFrame.setSize(200, 200);
	    // ���ô��ڵ���ʾλ��
	    internalFrame.setLocation(50, 50);
	    // �ڲ����ڵĹرհ�ť����Ĭ�Ͼ������ٴ��ڣ����в�������
	    internalFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    
	    internalFrame.setVisible(true);
	    internalFrame.setPreferredSize(new Dimension(1300,200));
	    
	    //new���button
	    Button east=new Button("East");
	    Button west=new Button("West");
	    Button south=new Button("South");
	    Button north=new Button("North");
	    Button center=new Button("Center");
	    
	    //�����button���뵽frame������
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
		File file = new File( "C:\\Users\\Administrator\\Desktop\\��ʱ�ļ�\\LinkList");
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
		
		jf = new Frame("Galesaur");				//�����������
		jf.setBounds(300,100,600,500);			//���ô���λ�úʹ�С
		jf.setLayout(new FlowLayout()); 		//���ô��岼��Ϊ��ʽ����
		jf.setVisible(true); 					//���ô���ɼ�
		
		textField = new TextField(60);					//���������ı�����60���ȴ�С�ַ�
		button = new Button("ת��");  					//������ť���� 
		jsp = new JScrollPane(25,70);					//���������ı�����25�� 70��
		dflm = new DefaultListModel();					// Ĭ�ϵ� list�б���ʾ��ʽ
		
		jf.add(textField); 		// �����ı���ӵ�������
		jf.add(button);			// ��ť��ӵ�������
		jf.add(jsp); 			// �����ı���ӵ�������
		
		myEvent();				// �����¼�����
		
		jlist.setCellRenderer(new MyListRenderer());	//������Ⱦ��Ϊ�����Լ���
		jsp.setViewportView(jlist);
		
		
	}
	
	/**
	 * 	�ؼ����� :	�� ϵͳ·���µ�Ŀ¼���ļ� ͼ��������� 
	 *  		File file = new File( "C:\\Users\\Administrator\\Desktop\\��ʱ�ļ�\\LinkList");
	 *	jFrame.setIconImage(((ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file)).getImage());
	 *	
	 *	File dir = new File(dirPath);			//���ַ���dirpath��װ���ļ�	
	 *	// ·���´����ļ� ����Ŀ¼���г�Ŀ¼�µ��ļ���Ŀ¼����ӵ� �����ı�����.
		if(dir.exists() && dir.isDirectory()){
			ta.setText(null);					//û��һ��Ŀ¼ǰ��ն����ı�����
			String[] names = dir.list();		//�г�Ŀ¼���ļ���Ŀ¼�� �Ž��ַ���������
			for(String name:names)				// ��������
				ta.append(name + "\r\n"); 		//׷���ı����ݲ����� 
		}
	 * �������������ﲻ�� ������Ҫ�Ľ���� -- Ŀ���б� ϵͳͼ��+�ļ�����
	 *	
	 	DefaultListModel dflm = new DefaultListModel();
		dfl.addElement("��ӵ��ļ��� ");
		
		// ��дĬ����Ⱦ��
		JList jl = new JList(dflm);
		jl.setCellRenderer(new CRTest());		//������Ⱦ��Ϊ�����Լ���
	 *	
	 *	
	 *	public class CRTest extends DefaultListCellRenderer {
	 *		public Component getListCellRendererComponent(JList<? extends Object> list,
	 *						Object value,int index,boolean isSelected,boolean cellHasFocus) {
				setText(value.toString());								//��������
				if(value.toString().equals("a")) {						//�ж�value���б�ֵ������������ò�ͬͼ��
					ImageIcon ico=new ImageIcon("res\\testicon.png");	//ʵ����һ��ImageIcon����
			....
	 */
	private void myEvent() {
		//��ť�¼�������
		button.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e) {
				String dirPath  = textField.getText();		//��ȡ�����ı�����
				File dir = new File(dirPath);				//���ַ���dirpath��װ���ļ�
				
				// ·���´����ļ� ����Ŀ¼���г�Ŀ¼�µ��ļ���Ŀ¼����ӵ� �����ı�����.
				if(dir.exists() && dir.isDirectory()){
					jsp.setToolTipText(null);				//û��һ��Ŀ¼ǰ��ն����ı�����
					String[] names = dir.list();			//�г�Ŀ¼���ļ���Ŀ¼�� �Ž��ַ���������
					for(String name:names){					// ��������
						dflm.addElement(name); 				//׷���ı����ݲ����� 
					}
					jlist  = new JList(dflm);
					
				}
			}
		});
		
		//����رռ�����
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
