package GUI_Tool;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

public class JTextFieldHintListener implements FocusListener{
	private String mHixText;
	private JTextField mTextFileld;
	
	public JTextFieldHintListener(String hixText,JTextField textField) {
		this.mHixText = hixText;
		this.mTextFileld = textField;
		textField.setForeground(Color.GRAY);
	}
	public JTextFieldHintListener(String hixText,JTextField textField,Color color) {
		this.mHixText = hixText;
		this.mTextFileld = textField;
		mTextFileld.setForeground(color);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		
			String temp = mTextFileld.getText();
			if(temp.equals(mHixText)){
				mTextFileld.setText("");
				mTextFileld.setForeground(Color.BLACK);
			}
	}

	@Override
	public void focusLost(FocusEvent e) {
		String temp = mTextFileld.getText();
        if(temp.equals("")){
        	mTextFileld.setForeground(Color.GRAY);
        	mTextFileld.setText(mHixText);
        }
	}
	

}
