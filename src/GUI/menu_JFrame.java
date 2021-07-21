package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;


public class menu_JFrame{
	
	private JFrame jf;
	private JPanel panel;
	private static volatile JButton connect;
	private static volatile JTextField Host_IP;
	private static volatile JTextField Ftp_User;
	private static volatile JTextField Ftp_Passwd;
	
	public JFrame getJF() {
		return jf;
	}
	public JPanel getPanel() {
		return panel;
	}
	public JButton getButtonCon() {
		return connect;
	}
	public JTextField getHost_IP() {
		return Host_IP;
	}
	public JTextField getFtp_User() {
		return Ftp_User;
	}
	public JTextField getFtp_Passwd() {
		return Ftp_Passwd;
	}
	
	
	
	public menu_JFrame(){
		jf = new JFrame(" �ļ�����    V0.2");
	    jf.setSize(1200, 1020);
//	    jf.setLocationRelativeTo(null);						// ���Բ���   
	    jf.setLayout(new BorderLayout());					// ���ò��ַ�ʽΪ�����ϱ��в���
	    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    jf.setIconImage(Toolkit.getDefaultToolkit().
				getImage("./src/GUI/picture/logn.png"));    // ���ô������Ͻǵ�Сͼ��
	
	    /*
	     * ����һ���˵���
	     */
	    JMenuBar menuBar = new JMenuBar();

	    /*
	     * ����һ���˵�
	     */
	    JMenu fileMenu = new JMenu("�ļ�");
	    JMenu editMenu = new JMenu("�༭");
	    JMenu viewMenu = new JMenu("�鿴");
	    JMenu aboutMenu = new JMenu("����");
	    // һ���˵���ӵ��˵���
	    menuBar.add(fileMenu);
	    menuBar.add(editMenu);
	    menuBar.add(viewMenu);
	    menuBar.add(aboutMenu);
	
	    /*
	     * ���� "�ļ�" һ���˵����Ӳ˵�
	     */
	    JMenuItem newMenuItem = new JMenuItem("�½�");
	    JMenuItem openMenuItem = new JMenuItem("��");
	    JMenuItem exitMenuItem = new JMenuItem("�˳�");
	    // �Ӳ˵���ӵ�һ���˵�
	    fileMenu.add(newMenuItem);
	    fileMenu.add(openMenuItem);
	    fileMenu.addSeparator();       // ���һ���ָ���
	    fileMenu.add(exitMenuItem);
	
	    /*
	     * ���� "�༭" һ���˵����Ӳ˵�
	     */
	    JMenuItem copyMenuItem = new JMenuItem("����");
	    JMenuItem pasteMenuItem = new JMenuItem("ճ��");
	    // �Ӳ˵���ӵ�һ���˵�
	    editMenu.add(copyMenuItem);
	    editMenu.add(pasteMenuItem);
	
	    /*
	     * ���� "�鿴" һ���˵����Ӳ˵�
	     */
	    final JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("�������");
	    final JRadioButtonMenuItem radioButtonMenuItem01 = new JRadioButtonMenuItem("��ͼ��");
	    final JRadioButtonMenuItem radioButtonMenuItem02 = new JRadioButtonMenuItem("Сͼ��");
	    final JRadioButtonMenuItem radioButtonMenuItem03 = new JRadioButtonMenuItem("�б�");
	    final JRadioButtonMenuItem radioButtonMenuItem04 = new JRadioButtonMenuItem("��ϸ��Ϣ");
	    final JMenuItem flushMenuItem = new JMenuItem("ˢ��");
	    // �Ӳ˵���ӵ�һ���˵�
	    viewMenu.add(checkBoxMenuItem);
	    viewMenu.addSeparator();                // ���һ���ָ���
	    viewMenu.add(radioButtonMenuItem01);
	    viewMenu.add(radioButtonMenuItem02);
	    viewMenu.add(radioButtonMenuItem03);
	    viewMenu.add(radioButtonMenuItem04);
	    viewMenu.addSeparator();                // ���һ���ָ���
	    viewMenu.add(flushMenuItem);
	
	    // �����ĸ� ��ѡ��ť�Ӳ˵���Ҫʵ�ֵ�ѡ��ť��Ч������Ҫ�����Ƿŵ�һ����ť����
	    ButtonGroup btnGroup = new ButtonGroup();
	    btnGroup.add(radioButtonMenuItem01);
	    btnGroup.add(radioButtonMenuItem02);
	    btnGroup.add(radioButtonMenuItem03);
	    btnGroup.add(radioButtonMenuItem04);
	
	    // Ĭ�ϵ��ĸ���ѡ��ť�Ӳ˵�ѡ��
	    radioButtonMenuItem04.setSelected(true);
	
	    /*
	     * �˵���ĵ��/״̬�ı��¼��������¼���������ֱ�������ھ�����Ӳ˵��ϣ�����ֻ�������м�����
	     */
	    // ���� "�½�" �Ӳ˵�������ļ�����
	    newMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("�½�  �����");
	        }
	    });
	    // ���� "��" �Ӳ˵�������ļ�����
	    openMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("��  �����");
	        }
	    });
	    // ���� "�˳�" �Ӳ˵�������ļ�����
	    exitMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("�˳�  �����");
	        }
	    });
	    // ���� "ˢ��" �Ӳ˵�������ļ�����
	    flushMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("ˢ��  �����");
	        }
	    });
	    
	    // ��ѡ�򱻵���� ������
	    checkBoxMenuItem.addItemListener(new ItemListener() {	
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(checkBoxMenuItem.isSelected())
					System.out.println("������� ��ѡ��");
				else
					System.out.println("������� ��ȡ��");
			}
		});
	    
	    // ���� "��ť����" �ĸ��Ӳ˵�״̬�ı�ļ�����
	    radioButtonMenuItem01.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(radioButtonMenuItem01.isSelected())
					System.out.println("��ͼ�� ��ѡ��");
				else
					System.out.println("��ͼ�� ��ȡ��");	
			}   	
	    });
	    radioButtonMenuItem02.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(radioButtonMenuItem02.isSelected())
					System.out.println("Сͼ�� ��ѡ��");
				else
					System.out.println("Сͼ�� ��ȡ��");	
			}   	
	    });
	    radioButtonMenuItem03.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(radioButtonMenuItem03.isSelected())
					System.out.println("�б� ��ѡ��");
				else
					System.out.println("�б� ��ȡ��");	
			}   	
	    });
	    radioButtonMenuItem04.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(radioButtonMenuItem04.isSelected())
					System.out.println("��ϸ��Ϣ ��ѡ��");
				else
					System.out.println("��ϸ��Ϣ ��ȡ��");	
			}   	
	    });
	    
	    // ���� ��ѡ��ť�Ӳ˵� ״̬�ı� ������		
	    // ChangeListener �������ͷ�Ƿ��ƶ�����λ�ã�ʵʱ���о��е��ʱ��
//	    radioButtonMenuItem01.addChangeListener(new ChangeListener() {
//	        @Override
//	        public void stateChanged(ChangeEvent e) {
//	            System.out.println("��ѡ��ť01 �Ƿ�ѡ��: " + radioButtonMenuItem01.isSelected());
//	        }
//	    });
	    
	    
	    
	    
	    panel = new JPanel(new BorderLayout(0,0));				// ���� ������壬ʹ�� �߽粼��
	    JToolBar toolBar = new JToolBar("������");				// ���� һ��������ʵ��
/**
 *  ���ﲻ������ʾ����Ҫ�������ʾ. ϣ���ĳɿ�ʼ����ʾ.	    
 */
        // �����ı���.
        Host_IP = new JTextField(100);
        Host_IP.setFont(new Font("����", Font.PLAIN, 14));
        Host_IP.addFocusListener(new General_JTextFieldListener(Host_IP, "   ����������IP��ַ"));
	    Ftp_User = new JTextField(17);
	    Ftp_User.setFont(new Font("����", Font.PLAIN, 14));
	    Ftp_User.addFocusListener(new General_JTextFieldListener(Ftp_User, " �û���"));
	    Ftp_Passwd = new JTextField(16);
	    Ftp_Passwd.setFont(new Font("����", Font.PLAIN, 14));
	    Ftp_Passwd.addFocusListener(new General_JTextFieldListener(Ftp_Passwd, " ����"));
	    
	    connect = new JButton(" ����", new ImageIcon("./src/GUI/picture/����.png"));
	    connect.setPreferredSize(new Dimension(80,30));
	    connect.setFont(new Font("����", Font.PLAIN, 14));
//	    connect.setOpaque(true);								// ���ÿؼ��Ƿ�͸����trueΪ��͸����falseΪ͸��
//	    connect.setContentAreaFilled(false);					// ����ͼƬ������ť���ڵ�����
	    connect.setMargin(new Insets(0, 0, 0, 0));				// ���ð�ť�߿�ͱ�ǩ����֮��ľ���
	    connect.setFocusPainted(true);							// ���������ť�ǲ��ǻ�ý���
	    connect.setBorderPainted(false);							// �����Ƿ���Ʊ߿�
	    
	    toolBar.add(Host_IP);
	    toolBar.add(Ftp_User);
	    toolBar.add(Ftp_Passwd);
	    toolBar.add(connect);    
	    panel.add(toolBar, BorderLayout.PAGE_START);			// ��������ӵ� panel�����.
	    panel.setVisible(true);
	    
	 
 

	    /*
	     * ��� �Ѳ˵������õ�����
	     */
	    jf.setJMenuBar(menuBar);
	    
	   
//	    jf.pack();			��������Զ����ڴ��ڴ�С
	    jf.setLocation(300, -10);
	    jf.setVisible(true);
	}
	
	
}