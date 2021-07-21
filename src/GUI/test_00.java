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
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(250, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
     // 设置本属性将改变窗口边框样式定义
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
        UIManager.put("RootPane.setupButtonVisible", false);
        // 启用皮肤
        BeautyEyeLNFHelper.launchBeautyEyeLNF();
     

        JPanel panel = new JPanel();

        // 创建一个进度条
        final JProgressBar progressBar = new JProgressBar();

        // 设置进度的 最小值 和 最大值
        progressBar.setMinimum(MIN_PROGRESS);
        progressBar.setMaximum(MAX_PROGRESS);

        // 设置当前进度值
        progressBar.setValue(currentProgress);

        // 绘制百分比文本（进度条中间显示的百分数）
        progressBar.setStringPainted(true);

//        // 添加进度改变通知
//        progressBar.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                System.out.println("当前进度值: " + progressBar.getValue() + "; " +
//                        "进度百分比: " + progressBar.getPercentComplete());
//            }
//        });

        // 添加到内容面板
        panel.add(progressBar);

        jf.setContentPane(panel);
        jf.setVisible(true);

        // 模拟延时操作进度, 每隔 0.5 秒更新进度
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
//	        JFrame jf = new JFrame("测试窗口");
//	        jf.setSize(300, 300);
//	        jf.setLocationRelativeTo(null);
//	        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//	        JPanel panel = new JPanel(new BorderLayout());
//
//	        label = new JLabel("正在下载: 0%");
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
//	     // 创建后台任务
////	        SwingWorker<String, Integer> workTask = new SwingWorker<String, Integer>() {
//	        workTask = new SwingWorker<String, Integer>() {
//	            @Override
//	            protected String doInBackground() throws Exception {
//	                for (int i = 0; i < 100; i += 10) {
//	                    // 延时模拟耗时操作
//	                    Thread.sleep(1000);
//	
//	                    // 设置 progress 属性的值（通过属性改变监听器传递数据到事件调度线程）
//	                    setProgress(i);
//	
//	                    // 通过 SwingWorker 内部机制传递数据到事件调度线程
//	                    publish(i);
//	                }
//	                // 返回计算结果
//	                return "下载完成";
//	            }
//	
//	            @Override
//	            protected void process(List<Integer> chunks) {
//	                // 此方法在 调用 doInBackground 调用 public 方法后在事件调度线程中被回调
//	                Integer progressValue = chunks.get(0);
//	                textArea.append("已下载: " + progressValue + "%\n");
//	            }
//	
//	            @Override
//	            protected void done() {
//	                // 此方法将在后台任务完成后在事件调度线程中被回调
//	                String result = null;
//	                try {
//	                    // 获取计算结果
//	                    result = get();
//	                } catch (Exception e) {
//	                    e.printStackTrace();
//	                }
//	                label.setText(result);
//	                textArea.append(result);
//	            }
//	        };
//	        
//		    // 添加属性改变监听器
//		    workTask.addPropertyChangeListener(new PropertyChangeListener() {
//		        @Override
//		        public void propertyChange(PropertyChangeEvent evt) {
//		            if ("progress".equals(evt.getPropertyName())) {
//		                Object progressValue = evt.getNewValue();
//		                label.setText("正在下载: " + progressValue + "%");
//		            }
//		        }
//		    });
//		}
//	    
//	    public static SwingWorker<String, Integer> getWorkTask() {
//			return workTask;
//		}

}
