package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

 
@SuppressWarnings("serial")
public class LoginFrame extends JFrame {  
	
	private static JFrame Login_jf;			// 登陆的框架
	private String user;
	private String passwd;
	public ImageIcon iconPasswd;
	public ImageIcon iconUser;
	
	public LoginFrame() throws Exception {	

		user = "admin";
		passwd ="123"; 

		Login_jf = new JFrame(" 测试版  V0.2");
		Login_jf.setLayout(null);
		Login_jf.setSize(700, 480);			// 登陆的框架大小
		Login_jf.setResizable(false);		// 设置窗口为不可缩放
		Login_jf.setVisible(true);			// 设置为窗口可见
		Login_jf.setIconImage(Toolkit.getDefaultToolkit().
							getImage("./src/GUI/picture/logn.png"));    // 设置窗口左上角的小图标

		Login_jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// 设置用户在此窗体上发起 "close" 时默认执行的操作。
		 
        SpringLayout layout = new SpringLayout();						// 创建内容面板，使用 弹性布局
        JPanel panel = new JPanel(layout);
        Login_jf.setContentPane(panel);	
        																// 创建组件
		ImageIcon img = new ImageIcon("./src/GUI/picture/背景图.png");	// 要设置的背景图片
		JLabel imgLabel = new JLabel(img);								// 将背景图放在标签里
		imgLabel.setSize(img.getIconWidth(),img.getIconHeight());
		
		JTextField jTextUser = new JTextField();
//        JTextField jTextUser = new JTextField(){
//        	public void paintComponents(Graphics g) {
//        		super.paintComponents(g);
//        		iconUser = new ImageIcon(getClass().getResource("./src/GUI/picture/用户1.png"));
//        		 //在文本框中画上之前图片
//                iconUser.paintIcon(this, g, 500, 320);				// 失败
//        	}
//        };
        jTextUser.setFont(new Font("黑体", Font.PLAIN, 16));
        jTextUser.setPreferredSize(new Dimension (180, 40));
        jTextUser.addFocusListener(new General_JTextFieldListener(jTextUser, " 用户名：admin"));
        
        JTextField jTextPasswd = new JTextField ();
        jTextPasswd.setPreferredSize(new Dimension (180, 40));
        jTextPasswd.setFont(new Font("黑体", Font.PLAIN, 16));
        jTextPasswd.addFocusListener(new General_JTextFieldListener(jTextPasswd, " 密码：123"));
//        JTextPane jTextPasswd = new JTextPane();
//        jTextPasswd.setPreferredSize(new Dimension (180, 40));
//        jTextPasswd.setFont(new Font("黑体", Font.PLAIN, 14));
//        jTextPasswd.addFocusListener(new General_JTextFieldListener(jTextPasswd, " 密码：123"));
//        jTextPasswd.insertIcon(new ImageIcon("./src/GUI/picture/密码1.png"));
        
        JButton SignIn = new JButton("登         录");
        SignIn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        SignIn.setPreferredSize(new Dimension (180, 34));
        SignIn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        SignIn.setForeground(Color.white);
		
		panel.add(imgLabel);											// 添加组件到内容面板
		panel.add(jTextUser);
		panel.add(jTextPasswd);
		panel.add(SignIn);
				
 /*
  * 	组件的约束设置（弹性布局设置的关键）
  */
        // 标签组件约束: 设置标签的左上角坐标为 (5, 5)
        SpringLayout.Constraints labelCons = layout.getConstraints(imgLabel);  // 从布局中获取指定组件的约束对象（如果没有，会自动创建）
        labelCons.setX(Spring.constant(-1));
        labelCons.setY(Spring.constant(-1));
        
        // 文本组件约束: 设置左上角 水平坐标为(450,80) 
        SpringLayout.Constraints User = layout.getConstraints(jTextUser);
        User.setX(Spring.constant(430));
        User.setY(Spring.constant(120));
        
        SpringLayout.Constraints Passwd = layout.getConstraints(jTextPasswd);
        Passwd.setX(Spring.constant(430));
        Passwd.setY(Spring.constant(200));
        
        SpringLayout.Constraints logOn = layout.getConstraints(SignIn);
        logOn.setX(Spring.constant(430));
        logOn.setY(Spring.constant(280));


        SignIn.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
            	if( jTextUser.getText().equals(getUser())&&
            			jTextPasswd.getText().equals(getPasswd()) ){
                        
            		Login_jf.dispose();								// 销毁当前窗口
            		new panel_JSplitPane();							// 跳转界面
                        
            	}else{
                    JOptionPane.showMessageDialog(					// 消息对话框无返回, 仅做通知作用
               		 Login_jf,
                    "用户 或 密码输入错误",
                    "错误提示",
                    JOptionPane.WARNING_MESSAGE );
            	}
            }
        });
	
//		Login_jf.setSize(img.getIconWidth(),img.getIconHeight());     
//		Login_jf.setLocationRelativeTo(null);
//		Login_jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        AWTUtilities.setWindowOpaque(Login_jf,false);						// 隐藏一个窗体的标题栏
//        Login_jf.getRootPane().setWindowDecorationStyle(JRootPane.NONE);    
        Login_jf.setLocation(500, 150);										// 登陆的框架起始位置
		Login_jf.setVisible(true);
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
	public static void main(String[] args) throws Exception {
		
		// 设置本属性将改变窗口边框样式定义
//        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
/* 	系统默认边框		oslookandfeeldeCorated	
   	强立体感半透明边框	translucencyAppleLike
	弱立体感半透明边框	translucencysmallShadow
	普通不透明边框		generalnotranslucencyShadow
*/
        UIManager.put("RootPane.setupButtonVisible", false);      
        BeautyEyeLNFHelper.launchBeautyEyeLNF();				// 启用皮肤
        UIManager.put("JTabbedPane.tabAreaInsets"
        	    , new javax.swing.plaf.InsetsUIResource(3,10,2,20));
		new LoginFrame();
	}


}
