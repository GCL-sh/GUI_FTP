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
		jf = new JFrame(" 文件传输    V0.2");
	    jf.setSize(1200, 1020);
//	    jf.setLocationRelativeTo(null);						// 弹性布局   
	    jf.setLayout(new BorderLayout());					// 设置布局方式为东西南北中布局
	    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    jf.setIconImage(Toolkit.getDefaultToolkit().
				getImage("./src/GUI/picture/logn.png"));    // 设置窗口左上角的小图标
	
	    /*
	     * 创建一个菜单栏
	     */
	    JMenuBar menuBar = new JMenuBar();

	    /*
	     * 创建一级菜单
	     */
	    JMenu fileMenu = new JMenu("文件");
	    JMenu editMenu = new JMenu("编辑");
	    JMenu viewMenu = new JMenu("查看");
	    JMenu aboutMenu = new JMenu("关于");
	    // 一级菜单添加到菜单栏
	    menuBar.add(fileMenu);
	    menuBar.add(editMenu);
	    menuBar.add(viewMenu);
	    menuBar.add(aboutMenu);
	
	    /*
	     * 创建 "文件" 一级菜单的子菜单
	     */
	    JMenuItem newMenuItem = new JMenuItem("新建");
	    JMenuItem openMenuItem = new JMenuItem("打开");
	    JMenuItem exitMenuItem = new JMenuItem("退出");
	    // 子菜单添加到一级菜单
	    fileMenu.add(newMenuItem);
	    fileMenu.add(openMenuItem);
	    fileMenu.addSeparator();       // 添加一条分割线
	    fileMenu.add(exitMenuItem);
	
	    /*
	     * 创建 "编辑" 一级菜单的子菜单
	     */
	    JMenuItem copyMenuItem = new JMenuItem("复制");
	    JMenuItem pasteMenuItem = new JMenuItem("粘贴");
	    // 子菜单添加到一级菜单
	    editMenu.add(copyMenuItem);
	    editMenu.add(pasteMenuItem);
	
	    /*
	     * 创建 "查看" 一级菜单的子菜单
	     */
	    final JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("传输队列");
	    final JRadioButtonMenuItem radioButtonMenuItem01 = new JRadioButtonMenuItem("大图标");
	    final JRadioButtonMenuItem radioButtonMenuItem02 = new JRadioButtonMenuItem("小图标");
	    final JRadioButtonMenuItem radioButtonMenuItem03 = new JRadioButtonMenuItem("列表");
	    final JRadioButtonMenuItem radioButtonMenuItem04 = new JRadioButtonMenuItem("详细信息");
	    final JMenuItem flushMenuItem = new JMenuItem("刷新");
	    // 子菜单添加到一级菜单
	    viewMenu.add(checkBoxMenuItem);
	    viewMenu.addSeparator();                // 添加一个分割线
	    viewMenu.add(radioButtonMenuItem01);
	    viewMenu.add(radioButtonMenuItem02);
	    viewMenu.add(radioButtonMenuItem03);
	    viewMenu.add(radioButtonMenuItem04);
	    viewMenu.addSeparator();                // 添加一个分割线
	    viewMenu.add(flushMenuItem);
	
	    // 其中四个 单选按钮子菜单，要实现单选按钮的效果，需要将它们放到一个按钮组中
	    ButtonGroup btnGroup = new ButtonGroup();
	    btnGroup.add(radioButtonMenuItem01);
	    btnGroup.add(radioButtonMenuItem02);
	    btnGroup.add(radioButtonMenuItem03);
	    btnGroup.add(radioButtonMenuItem04);
	
	    // 默认第四个单选按钮子菜单选中
	    radioButtonMenuItem04.setSelected(true);
	
	    /*
	     * 菜单项的点击/状态改变事件监听，事件监听可以直接设置在具体的子菜单上（这里只设置其中几个）
	     */
	    // 设置 "新建" 子菜单被点击的监听器
	    newMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("新建  被点击");
	        }
	    });
	    // 设置 "打开" 子菜单被点击的监听器
	    openMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("打开  被点击");
	        }
	    });
	    // 设置 "退出" 子菜单被点击的监听器
	    exitMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("退出  被点击");
	        }
	    });
	    // 设置 "刷新" 子菜单被点击的监听器
	    flushMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("刷新  被点击");
	        }
	    });
	    
	    // 复选框被点击的 监听器
	    checkBoxMenuItem.addItemListener(new ItemListener() {	
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(checkBoxMenuItem.isSelected())
					System.out.println("传输队列 被选中");
				else
					System.out.println("传输队列 被取消");
			}
		});
	    
	    // 设置 "按钮组中" 四个子菜单状态改变的监听器
	    radioButtonMenuItem01.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(radioButtonMenuItem01.isSelected())
					System.out.println("大图标 被选中");
				else
					System.out.println("大图标 被取消");	
			}   	
	    });
	    radioButtonMenuItem02.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(radioButtonMenuItem02.isSelected())
					System.out.println("小图标 被选中");
				else
					System.out.println("小图标 被取消");	
			}   	
	    });
	    radioButtonMenuItem03.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(radioButtonMenuItem03.isSelected())
					System.out.println("列表 被选中");
				else
					System.out.println("列表 被取消");	
			}   	
	    });
	    radioButtonMenuItem04.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(radioButtonMenuItem04.isSelected())
					System.out.println("详细信息 被选中");
				else
					System.out.println("详细信息 被取消");	
			}   	
	    });
	    
	    // 设置 单选按钮子菜单 状态改变 监听器		
	    // ChangeListener 检测鼠标箭头是否移动到该位置，实时监测感觉有点费时。
//	    radioButtonMenuItem01.addChangeListener(new ChangeListener() {
//	        @Override
//	        public void stateChanged(ChangeEvent e) {
//	            System.out.println("单选按钮01 是否被选中: " + radioButtonMenuItem01.isSelected());
//	        }
//	    });
	    
	    
	    
	    
	    panel = new JPanel(new BorderLayout(0,0));				// 创建 内容面板，使用 边界布局
	    JToolBar toolBar = new JToolBar("工具栏");				// 创建 一个工具栏实例
/**
 *  这里不主动显示，需要点击才显示. 希望改成开始就显示.	    
 */
        // 创建文本框.
        Host_IP = new JTextField(100);
        Host_IP.setFont(new Font("黑体", Font.PLAIN, 14));
        Host_IP.addFocusListener(new General_JTextFieldListener(Host_IP, "   主机名或者IP地址"));
	    Ftp_User = new JTextField(17);
	    Ftp_User.setFont(new Font("黑体", Font.PLAIN, 14));
	    Ftp_User.addFocusListener(new General_JTextFieldListener(Ftp_User, " 用户名"));
	    Ftp_Passwd = new JTextField(16);
	    Ftp_Passwd.setFont(new Font("黑体", Font.PLAIN, 14));
	    Ftp_Passwd.addFocusListener(new General_JTextFieldListener(Ftp_Passwd, " 密码"));
	    
	    connect = new JButton(" 连接", new ImageIcon("./src/GUI/picture/连接.png"));
	    connect.setPreferredSize(new Dimension(80,30));
	    connect.setFont(new Font("黑体", Font.PLAIN, 14));
//	    connect.setOpaque(true);								// 设置控件是否透明，true为不透明，false为透明
//	    connect.setContentAreaFilled(false);					// 设置图片填满按钮所在的区域
	    connect.setMargin(new Insets(0, 0, 0, 0));				// 设置按钮边框和标签文字之间的距离
	    connect.setFocusPainted(true);							// 设置这个按钮是不是获得焦点
	    connect.setBorderPainted(false);							// 设置是否绘制边框
	    
	    toolBar.add(Host_IP);
	    toolBar.add(Ftp_User);
	    toolBar.add(Ftp_Passwd);
	    toolBar.add(connect);    
	    panel.add(toolBar, BorderLayout.PAGE_START);			// 工具栏添加到 panel面板上.
	    panel.setVisible(true);
	    
	 
 

	    /*
	     * 最后 把菜单栏设置到窗口
	     */
	    jf.setJMenuBar(menuBar);
	    
	   
//	    jf.pack();			根据组件自动调节窗口大小
	    jf.setLocation(300, -10);
	    jf.setVisible(true);
	}
	
	
}