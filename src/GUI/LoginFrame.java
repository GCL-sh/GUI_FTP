package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

 
@SuppressWarnings("serial")
public class LoginFrame extends JFrame {  
	
	private static JFrame Login_jf;			// ��½�Ŀ��
	private String user;
	private String passwd;
	public ImageIcon iconPasswd;
	public ImageIcon iconUser;
	
	public LoginFrame() throws Exception {	

		user = "admin";
		passwd ="123"; 

		Login_jf = new JFrame(" ���԰�  V0.2");
		Login_jf.setLayout(null);
		Login_jf.setSize(700, 480);			// ��½�Ŀ�ܴ�С
		Login_jf.setResizable(false);		// ���ô���Ϊ��������
		Login_jf.setVisible(true);			// ����Ϊ���ڿɼ�
		Login_jf.setIconImage(Toolkit.getDefaultToolkit().
							getImage("./src/GUI/picture/logn.png"));    // ���ô������Ͻǵ�Сͼ��

		Login_jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// �����û��ڴ˴����Ϸ��� "close" ʱĬ��ִ�еĲ�����
		 
        SpringLayout layout = new SpringLayout();						// ����������壬ʹ�� ���Բ���
        JPanel panel = new JPanel(layout);
        Login_jf.setContentPane(panel);	
        																// �������
		ImageIcon img = new ImageIcon("./src/GUI/picture/����ͼ.png");	// Ҫ���õı���ͼƬ
		JLabel imgLabel = new JLabel(img);								// ������ͼ���ڱ�ǩ��
		imgLabel.setSize(img.getIconWidth(),img.getIconHeight());
		
		JTextField jTextUser = new JTextField();
//        JTextField jTextUser = new JTextField(){
//        	public void paintComponents(Graphics g) {
//        		super.paintComponents(g);
//        		iconUser = new ImageIcon(getClass().getResource("./src/GUI/picture/�û�1.png"));
//        		 //���ı����л���֮ǰͼƬ
//                iconUser.paintIcon(this, g, 500, 320);				// ʧ��
//        	}
//        };
        jTextUser.setFont(new Font("����", Font.PLAIN, 16));
        jTextUser.setPreferredSize(new Dimension (180, 40));
        jTextUser.addFocusListener(new General_JTextFieldListener(jTextUser, " �û�����admin"));
        
        JTextField jTextPasswd = new JTextField ();
        jTextPasswd.setPreferredSize(new Dimension (180, 40));
        jTextPasswd.setFont(new Font("����", Font.PLAIN, 16));
        jTextPasswd.addFocusListener(new General_JTextFieldListener(jTextPasswd, " ���룺123"));
//        JTextPane jTextPasswd = new JTextPane();
//        jTextPasswd.setPreferredSize(new Dimension (180, 40));
//        jTextPasswd.setFont(new Font("����", Font.PLAIN, 14));
//        jTextPasswd.addFocusListener(new General_JTextFieldListener(jTextPasswd, " ���룺123"));
//        jTextPasswd.insertIcon(new ImageIcon("./src/GUI/picture/����1.png"));
        
        JButton SignIn = new JButton("��         ¼");
        SignIn.setFont(new Font("΢���ź�", Font.PLAIN, 20));
        SignIn.setPreferredSize(new Dimension (180, 34));
        SignIn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        SignIn.setForeground(Color.white);
		
		panel.add(imgLabel);											// ���������������
		panel.add(jTextUser);
		panel.add(jTextPasswd);
		panel.add(SignIn);
				
 /*
  * 	�����Լ�����ã����Բ������õĹؼ���
  */
        // ��ǩ���Լ��: ���ñ�ǩ�����Ͻ�����Ϊ (5, 5)
        SpringLayout.Constraints labelCons = layout.getConstraints(imgLabel);  // �Ӳ����л�ȡָ�������Լ���������û�У����Զ�������
        labelCons.setX(Spring.constant(-1));
        labelCons.setY(Spring.constant(-1));
        
        // �ı����Լ��: �������Ͻ� ˮƽ����Ϊ(450,80) 
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
                        
            		Login_jf.dispose();								// ���ٵ�ǰ����
            		new panel_JSplitPane();							// ��ת����
                        
            	}else{
                    JOptionPane.showMessageDialog(					// ��Ϣ�Ի����޷���, ����֪ͨ����
               		 Login_jf,
                    "�û� �� �����������",
                    "������ʾ",
                    JOptionPane.WARNING_MESSAGE );
            	}
            }
        });
	
//		Login_jf.setSize(img.getIconWidth(),img.getIconHeight());     
//		Login_jf.setLocationRelativeTo(null);
//		Login_jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        AWTUtilities.setWindowOpaque(Login_jf,false);						// ����һ������ı�����
//        Login_jf.getRootPane().setWindowDecorationStyle(JRootPane.NONE);    
        Login_jf.setLocation(500, 150);										// ��½�Ŀ����ʼλ��
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
		
		// ���ñ����Խ��ı䴰�ڱ߿���ʽ����
//        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
/* 	ϵͳĬ�ϱ߿�		oslookandfeeldeCorated	
   	ǿ����а�͸���߿�	translucencyAppleLike
	������а�͸���߿�	translucencysmallShadow
	��ͨ��͸���߿�		generalnotranslucencyShadow
*/
        UIManager.put("RootPane.setupButtonVisible", false);      
        BeautyEyeLNFHelper.launchBeautyEyeLNF();				// ����Ƥ��
        UIManager.put("JTabbedPane.tabAreaInsets"
        	    , new javax.swing.plaf.InsetsUIResource(3,10,2,20));
		new LoginFrame();
	}


}
