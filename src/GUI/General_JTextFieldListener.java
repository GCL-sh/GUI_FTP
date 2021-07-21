package GUI;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * @author GCL
 * JTextField jTextField = new JTextField();
 * jTextField.addFocusListener(new JTextFieldHintListener(jTextField, "��ʾ����"));
 */
public class General_JTextFieldListener implements FocusListener {
	private String hintText;
	private JTextField textField;
	private JTextPane textPane;
	
	public General_JTextFieldListener(JTextField jTextPasswd,String hintText) {
		this.textField = jTextPasswd;
		this.hintText = hintText;
		jTextPasswd.setText(hintText);  //Ĭ��ֱ����ʾ
		jTextPasswd.setForeground(Color.GRAY);
	}
	public General_JTextFieldListener(JTextPane jTextPasswd,String hintText) {
		this.textPane = jTextPasswd;
		this.hintText = hintText;
		jTextPasswd.setText(hintText);  //Ĭ��ֱ����ʾ
		jTextPasswd.setForeground(Color.GRAY);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		//��ȡ����ʱ�������ʾ����
		String temp = textField.getText();
		if(temp.equals(hintText)) {
			textField.setText("");
			textField.setForeground(Color.BLACK);
		}	
	}
	@Override
	public void focusLost(FocusEvent e) {	
		//ʧȥ����ʱ��û���������ݣ���ʾ��ʾ����
		String temp = textField.getText();
		if(temp.equals("")) {
			textField.setForeground(Color.GRAY);
			textField.setText(hintText);
		}	
	}

}
