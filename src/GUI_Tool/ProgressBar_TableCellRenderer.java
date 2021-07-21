package GUI_Tool;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class ProgressBar_TableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private int number = 0;


	public ProgressBar_TableCellRenderer(){}
	
	
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, 
			int row,int column) {
		
		this.setOpaque(true);													// render是否透明
		progressBar = new JProgressBar();										// 创建一个进度条
		progressBar.setMinimum(0);												// 设置进度的 最小值 和 最大值
		progressBar.setMaximum(100);							
		progressBar.setValue(number);											// 设置当前进度值
		progressBar.setStringPainted(true);										// 绘制百分比文本（进度条中间显示的百分数）
		
		progressBar.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		progressBar.setForeground(new Color(91, 121, 66));						// 进度颜色
		progressBar.setStringPainted(true);										// 是否显示ToolTipText

		if (value != null) {
			int progressValue = ((Integer.valueOf(value.toString())) ).intValue();
			progressBar.setValue(progressValue);
																				
			if (isSelected) {
				progressBar.setBackground(new Color(206, 207, 255));			// 设置背景色
//				progressBar.setBackground(new Color(91, 121, 66));
			} else {
				progressBar.setBackground(Color.white);
			}
		}
		progressBar.setToolTipText(String.valueOf(progressBar.getValue())+ "% ");
		return progressBar;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}

