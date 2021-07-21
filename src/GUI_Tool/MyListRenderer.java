package GUI_Tool;

import java.awt.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class MyListRenderer extends DefaultListCellRenderer {

	public Component getListCellRendererComponent(JList<? extends Object> list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		setText(value.toString());		//��������
		if(value.toString().equals("a")) {		//�ж�value���б�ֵ������������ò�ͬͼ��
			ImageIcon ico=new ImageIcon("res\\testicon.png");		//ʵ����һ��ImageIcon����
			Image img=ico.getImage();		//ʵ����Image�����ȡico��������� 
			img=img.getScaledInstance(25,25,Image.SCALE_DEFAULT);		//��ͼƬȫ������Ϊ25x25
			ico.setImage(img);		//ImageIcon�������»�ȡ���ź�ͼ��
			setIcon(ico);		//����ͼ��
		} else {
			ImageIcon ico=new ImageIcon("res\\testicon-2.png");		//ʵ����һ��ImageIcon����
			Image img=ico.getImage();		//ʵ����Image�����ȡico��������� 
			img=img.getScaledInstance(25,25,Image.SCALE_DEFAULT);		//��ͼƬȫ������Ϊ25x25
			ico.setImage(img);		//ImageIcon�������»�ȡ���ź�ͼ��
			setIcon(ico);
		}
		if(isSelected) {		//��ĳ��Ԫ�ر�ѡ��ʱ
			setForeground(Color.WHITE);		//����ǰ��ɫ��������ɫ��Ϊ��ɫ
			setBackground(Color.BLUE);		//���ñ���ɫΪ��ɫ
			System.out.println(index+"��ѡ��");
		} else {		//ĳ��Ԫ��δ��ѡ��ʱ��ȡ��ѡ�У�
			setForeground(Color.BLACK);		//����ǰ��ɫ��������ɫ��Ϊ��ɫ
			setBackground(Color.WHITE);		//���ñ���ɫΪ��ɫ
		}
		return this;
	}
}