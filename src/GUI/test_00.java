package GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class test_00 {

    private static final int MIN_PROGRESS = 0;
    private static final int MAX_PROGRESS = 100;

    private static int currentProgress = MIN_PROGRESS;

    public static void main(String[] args) throws Exception {
        JFrame jf = new JFrame("���Դ���");
        jf.setSize(250, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
     // ���ñ����Խ��ı䴰�ڱ߿���ʽ����
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
        UIManager.put("RootPane.setupButtonVisible", false);
        // ����Ƥ��
        BeautyEyeLNFHelper.launchBeautyEyeLNF();
     

        JPanel panel = new JPanel();

        // ����һ��������
        final JProgressBar progressBar = new JProgressBar();

        // ���ý��ȵ� ��Сֵ �� ���ֵ
        progressBar.setMinimum(MIN_PROGRESS);
        progressBar.setMaximum(MAX_PROGRESS);

        // ���õ�ǰ����ֵ
        progressBar.setValue(currentProgress);

        // ���ưٷֱ��ı����������м���ʾ�İٷ�����
        progressBar.setStringPainted(true);

//        // ��ӽ��ȸı�֪ͨ
//        progressBar.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                System.out.println("��ǰ����ֵ: " + progressBar.getValue() + "; " +
//                        "���Ȱٷֱ�: " + progressBar.getPercentComplete());
//            }
//        });

        // ��ӵ��������
        panel.add(progressBar);

        jf.setContentPane(panel);
        jf.setVisible(true);

        // ģ����ʱ��������, ÿ�� 0.5 ����½���
        new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProgress++;
                if (currentProgress > MAX_PROGRESS) {
                    currentProgress = MIN_PROGRESS;
                }
                progressBar.setValue(currentProgress);
            }
        }).start();
    }

	
	
//		private static JLabel label;
//		private static JTextArea textArea;
//		public static SwingWorker<String, Integer> workTask;
//
//	    public static void main(String[] args) {
////	        SwingUtilities.invokeLater(
////	                new Runnable() {
////	                    @Override
////	                    public void run() {
////	                        createGUI();
////	                    }
////	                }
////	        );
//	    	createGUI();
//	    	workThreand();
//	    	getWorkTask().execute();
//	    }
//
//	    public static void createGUI() {
//	        JFrame jf = new JFrame("���Դ���");
//	        jf.setSize(300, 300);
//	        jf.setLocationRelativeTo(null);
//	        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//	        JPanel panel = new JPanel(new BorderLayout());
//
//	        label = new JLabel("��������: 0%");
//	        panel.add(label, BorderLayout.NORTH);
//
//	        textArea = new JTextArea();
//	        panel.add(textArea, BorderLayout.CENTER);
//
//	        jf.setContentPane(panel);
//	        jf.setVisible(true);
//	    }
//	    
//	    
//	    public static void workThreand(){
//	     // ������̨����
////	        SwingWorker<String, Integer> workTask = new SwingWorker<String, Integer>() {
//	        workTask = new SwingWorker<String, Integer>() {
//	            @Override
//	            protected String doInBackground() throws Exception {
//	                for (int i = 0; i < 100; i += 10) {
//	                    // ��ʱģ���ʱ����
//	                    Thread.sleep(1000);
//	
//	                    // ���� progress ���Ե�ֵ��ͨ�����Ըı�������������ݵ��¼������̣߳�
//	                    setProgress(i);
//	
//	                    // ͨ�� SwingWorker �ڲ����ƴ������ݵ��¼������߳�
//	                    publish(i);
//	                }
//	                // ���ؼ�����
//	                return "�������";
//	            }
//	
//	            @Override
//	            protected void process(List<Integer> chunks) {
//	                // �˷����� ���� doInBackground ���� public ���������¼������߳��б��ص�
//	                Integer progressValue = chunks.get(0);
//	                textArea.append("������: " + progressValue + "%\n");
//	            }
//	
//	            @Override
//	            protected void done() {
//	                // �˷������ں�̨������ɺ����¼������߳��б��ص�
//	                String result = null;
//	                try {
//	                    // ��ȡ������
//	                    result = get();
//	                } catch (Exception e) {
//	                    e.printStackTrace();
//	                }
//	                label.setText(result);
//	                textArea.append(result);
//	            }
//	        };
//	        
//		    // ������Ըı������
//		    workTask.addPropertyChangeListener(new PropertyChangeListener() {
//		        @Override
//		        public void propertyChange(PropertyChangeEvent evt) {
//		            if ("progress".equals(evt.getPropertyName())) {
//		                Object progressValue = evt.getNewValue();
//		                label.setText("��������: " + progressValue + "%");
//		            }
//		        }
//		    });
//		}
//	    
//	    public static SwingWorker<String, Integer> getWorkTask() {
//			return workTask;
//		}

}
