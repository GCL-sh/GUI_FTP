package GUI_Tool;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class Icon_TableCellRenderer implements TableCellRenderer{

	private boolean bool;
	public Icon_TableCellRenderer(String type){
		if(type.equals("ÉÏ´«")){
			bool = true;
		}else if(type.equals("ÏÂÔØ")){
			bool = false;
		}
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		ImageIcon icon = null;
		JLabel label = null;
		if(bool){
			icon = new ImageIcon("./src/GUI/picture/right.png");
            label = new JLabel(icon);
            label.setOpaque(false);
		}else{
			icon = new ImageIcon("./src/GUI/picture/left.png");
            label = new JLabel(icon);
            label.setOpaque(false);
		}
		return label;
	}
	
	

}
