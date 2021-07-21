package GUI;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * @author GCL
 * JTextField jTextField = new JTextField();
 * jTextField.addFocusListener(new JTextFieldHintListener(jTextField, "提示内容"));
 */
public class General_JTextFieldListener implements FocusListener {
	private String hintText;
	private JTextField textField;
	private JTextPane textPane;
	
	public General_JTextFieldListener(JTextField jTextPasswd,String hintText) {
		this.textField = jTextPasswd;
		this.hintText = hintText;
		jTextPasswd.setText(hintText);  //默认直接显示
		jTextPasswd.setForeground(Color.GRAY);
	}
	public General_JTextFieldListener(JTextPane jTextPasswd,String hintText) {
		this.textPane = jTextPasswd;
		this.hintText = hintText;
		jTextPasswd.setText(hintText);  //默认直接显示
		jTextPasswd.setForeground(Color.GRAY);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		//获取焦点时，清空提示内容
		String temp = textField.getText();
		if(temp.equals(hintText)) {
			textField.setText("");
			textField.setForeground(Color.BLACK);
		}	
	}
	@Override
	public void focusLost(FocusEvent e) {	
		//失去焦点时，没有输入内容，显示提示内容
		String temp = textField.getText();
		if(temp.equals("")) {
			textField.setForeground(Color.GRAY);
			textField.setText(hintText);
		}	
	}

}
