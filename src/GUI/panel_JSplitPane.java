package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import GUI_Tool.Icon_TableCellRenderer;
import GUI_Tool.MultiThread_FileTransfer;
import GUI_Tool.ProgressBar_TableCellRenderer;
import sun.swing.table.DefaultTableCellHeaderRenderer;



public class panel_JSplitPane extends menu_JFrame{
	
	private General_FTPConnect ftpConnect;
	private JPanel panelALL02;
	private JPanel panelALL03;
	private JTextField tfield_Left;
	private JTextField tfield_Right;
	private JTable jtable_left;
	private JTable jtable_right;
	private DefaultTableModel tableModel_left;
	private DefaultTableModel tableModel_right;
	private JScrollPane PanelJSP_Right;
	private JScrollPane PanelJSP_Left;
	public DefaultTableCellRenderer rightRenderer_right;
	private boolean uploadStatus = false;
	private boolean uploadQueueStatus = false;
	private JSplitPane splitPane;
	private JPanel panelQue;
	private DefaultTableModel tableModel_Que;
	private JTable jtable_Que;
	
	public ProgressBar_TableCellRenderer progressBar_upload;
	public int currentProgress;
	
	
	public boolean isUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(boolean uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public boolean isUploadQueueStatus() {
		return uploadQueueStatus;
	}
	public void setUploadQueueStatus(boolean uploadQueueStatus) {
		this.uploadQueueStatus = uploadQueueStatus;
	}



	public panel_JSplitPane()
	{	
//			jf = new menu_JFrame();										// 获取主界面的面板class
		    
		    panelALL02 = new JPanel();									// 创建 内容面板
	    	panelALL02.setLayout(new BorderLayout());					// 设置布局方式为东西南北中布局
		    JToolBar toolBar02 = new JToolBar("工具栏02");				// 创建 一个工具栏实例
	        JLabel Host_Name = new JLabel("本地站点");						// 创建 Label.
	        Host_Name.setFont(new Font("黑体", Font.PLAIN, 14));
	        tfield_Left = new JTextField(14);							// 单行文本框
	        tfield_Left.setFont(new Font(null, Font.PLAIN, 15));
		    JButton button11 = new JButton(new ImageIcon("./src/GUI/picture/站点返回.png"));
		    JButton button12 = new JButton(new ImageIcon("./src/GUI/picture/刷新.png"));
		    Host_Name.setPreferredSize(new Dimension(60,25));
		    SetFilePath_Left();
		    tfield_Left.setPreferredSize(new Dimension(60,25));
		    button11.setPreferredSize(new Dimension(25,25));
		    button12.setPreferredSize(new Dimension(25,25));
		    toolBar02.add(Host_Name);
		    toolBar02.add(tfield_Left);
		    toolBar02.add(button11);
		    toolBar02.add(button12);		    

		    panelALL03 = new JPanel();									// "远程站点"的布局设置
	    	panelALL03.setLayout(new BorderLayout());					// 设置布局方式为东西南北中布局
		    JToolBar toolBar03 = new JToolBar("工具栏03");				// 创建 一个工具栏实例
	        JLabel connect_Name = new JLabel("远程站点");					// 创建 Label.
	        connect_Name.setFont(new Font("黑体", Font.PLAIN, 14));        
	        tfield_Right = new JTextField(14);							// 单行文本框
	        tfield_Right.setFont(new Font(null, Font.PLAIN, 15));
		    JButton button_30 = new JButton(new ImageIcon("./src/GUI/picture/站点返回.png"));
		    JButton button_31 = new JButton(new ImageIcon("./src/GUI/picture/刷新.png"));
		    connect_Name.setPreferredSize(new Dimension(60,25));	    
		    tfield_Right.setPreferredSize(new Dimension(60,25));
		    button_30.setPreferredSize(new Dimension(30,25));
		    button_31.setPreferredSize(new Dimension(25,25));
		    toolBar03.add(connect_Name);
		    toolBar03.add(tfield_Right);
		    toolBar03.add(button_30);
		    toolBar03.add(button_31);

/**
 * 		左右两边 JTable初始化，Jtable_add(left),初始化只填充左边JTable信息.	    
 */
		    JTable_DefaultTableModel_LeftInit();
		    DTM_Left_addData(tableModel_left,tfield_Left.getText()); 
		    
		    Jtbal_DefaultTableModel_RightInit();

	
		    
//  JTable直接放进 PanelAll02中 表头不显示，所以加上JScrollPane，JScrollPane中再加JTable即可.
	    	PanelJSP_Left = new JScrollPane(jtable_left);
	    	panelALL02.add(toolBar02, BorderLayout.NORTH);
	    	panelALL02.add(PanelJSP_Left,BorderLayout.CENTER);
	    	
	    	PanelJSP_Right = new JScrollPane(jtable_right);
	    	panelALL03.add(toolBar03, BorderLayout.NORTH);
	    	panelALL03.add(PanelJSP_Right,BorderLayout.CENTER);
	    	
	    	panelALL02.setVisible(true);
	    	panelALL03.setVisible(true);
	    	panelALL02.setBorder(null);
	    	panelALL03.setBorder(null);
	    	PanelJSP_Right.setBorder(null);
	    	PanelJSP_Left.setBorder(null);
	    	
		    
		    splitPane = new JSplitPane();									// 分页面板
		    JScrollPane scrollPaneLift = new JScrollPane(					// 创建滚动面板,
		    		panelALL02,												// 指定滚动显示的视图组件(textArea),
		//            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,		// 垂直滚动条一直显示, 水平滚动条不显示.
		            ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
		            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
		    );
		    
		    
		    JScrollPane scrollPaneRight = new JScrollPane(					// 创建滚动面板,
		    		panelALL03,												// 指定滚动显示的视图组件(textArea),
		            ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,			// 垂直滚动条一直显示, 水平滚动条不显示.
		            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
		    );    													       
		    splitPane.setLeftComponent(scrollPaneLift);						/* 左右分屏加入文本框显示当前访问目录地址  */
		    splitPane.setRightComponent(scrollPaneRight);
		    splitPane.setOneTouchExpandable(false);							// 分隔条上显示快速 折叠/展开 两边组件的小按钮
		    splitPane.setContinuousLayout(true);							// 拖动分隔条时不连续重绘组件
		    splitPane.setBackground(Color.white);
		    splitPane.setDividerSize(1);									// 拖动分隔条组件间隔距离
		    splitPane.setDividerLocation(getJF().getWidth()/2);				// 设置分隔条的初始位置
		    splitPane.setBorder(null);
		    
		    
		    getJF().addWindowStateListener(new WindowStateListener() {
		    	@Override
	            public void windowStateChanged(WindowEvent e) {
	                
	            	int oldState = e.getOldState();		// 原状态 (正常 最大化 最小化)   
	                int newState = e.getNewState();		// 新状态 (正常 最大化 最小化)   
	                // 状态   常量符号                  常量
	                //_________________________________
	                // 正常     Frame.NORMAL              0
	                // 最大化 Frame.MAXIMIZED_BOTH      6
	                // 最小化 Frame.ICONIFIED           1
	                //_________________________________
	                									// 正常->最大化
	                if(oldState==0 && newState==6){
	                	getJF().setVisible(true);
	                	splitPane.setDividerLocation((int) (getJF().getSize().getWidth()/2));
	                }else if (oldState==6 && newState==0) {
	                	getJF().setVisible(true);
	                	splitPane.setDividerLocation(getJF().getWidth()/2);
					}          
	            }
		    });
		    
/**
 * 		判断 ftpConnect.connectStatus 是否true.
 */	 		    
		ftpConnect= new General_FTPConnect();	 							// ftpClient 连接FTP_Server端，判断是否连接成功.
//		ftpConnect.FtpInit(ftpHost, ftpUserName, ftpPassword);
		
		JButton ConnectButton = getButtonCon();
		ConnectButton.addActionListener(new ActionListener() {		 		// 设置 "连接" 按钮被点击的监听器
        @Override
        public void actionPerformed(ActionEvent e)
        {
        	ftpConnect.FtpInit(getHost_IP().getText(),getFtp_User().getText(),
        						getFtp_Passwd().getText());
        	if(ftpConnect.GetConnectStatus()){
        		System.out.println("连接成功");										// 成功连接 FTPClient.
        		String DefaultPath = ftpConnect.GetFTP_DefaultPath();				// 获取默认路径.
	            SetFilePath_Right(DefaultPath);
	    	    tableModel_right.addColumn("文件名",addDTM_Right_Name());				/* 调用函数 添加的此处表格信息   */
				tableModel_right.addColumn("文件大小",addDTM_Right_Size());
				tableModel_right.addColumn("类型",addDTM_Right_Type());
				tableModel_right.addColumn("修改时间",addDTM_Right_Time());
				tableModel_right.addColumn("文件权限",addDTM_Right_Competence());
				tableModel_right.addColumn("所有者/组",addDTM_Right_UserGroup());
		    	jtable_right.getColumnModel().getColumn(1)
		    				.setCellRenderer(rightRenderer_right);					// 设置指定'第二列'对齐方式
		    	setUploadStatus(true);
		    	setUploadQueueStatus(true);
		    	jtable_right.addMouseListener(new MouseAdapter() {					// 添加鼠标右击 菜单、监听事件.
		    		 @Override
		             public void mouseReleased(MouseEvent e) {										// 鼠标释放事件
		                 if (e.isMetaDown()) {
		                     showPopupMenu_right(e.getComponent(), e.getX(), e.getY());				// 如果是鼠标右键，则显示弹出菜单
		                 }
		             }	
		    		 protected void showPopupMenu_right(Component invoker, int x, int y) {      
		    			JPopupMenu popupMenu_right = new JPopupMenu();							// 创建 弹出菜单 对象
				        																		// 创建 一级菜单
				        JMenuItem downloadMenuItem = new JMenuItem(" 下载(D)",new ImageIcon("./src/GUI/picture/download.png"));
				        downloadMenuItem.setFont(new Font("黑体", Font.PLAIN+Font.BOLD, 14)); 	//设置字体
				        JMenuItem downloadQueue = new JMenuItem(" 添加到队列(Q)",new ImageIcon("./src/GUI/picture/downloadadd.png"));
				        downloadQueue.setFont(new Font("黑体", Font.PLAIN, 14)); 					//设置字体
				        JMenuItem catMenuItem = new JMenuItem(" 查看文本");
				        catMenuItem.setFont(new Font("黑体", Font.PLAIN, 14)); 					//设置字体
				        JMenuItem copyMenuItem = new JMenuItem(" 复制        Ctrl+C",new ImageIcon("./src/GUI/picture/copy.png"));
				        copyMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
				        JMenuItem pasteMenuItem = new JMenuItem(" 粘贴        Ctrl+V",new ImageIcon("./src/GUI/picture/paste.png"));
				        pasteMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
				        JMenuItem deleteMenuItem = new JMenuItem(" 删除(D)");
				        deleteMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
				        JMenuItem mvMenuItem = new JMenuItem(" 重命名(M)");
				        mvMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
				        JMenuItem makeDirMenuItem = new JMenuItem(" 创建目录(C)");
				        makeDirMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
				        JMenuItem flushMenuItem = new JMenuItem(" 刷新(F)");
				        flushMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));

//				        JMenu editMenu = new JMenu("编辑");   							// 需要 添加 二级子菜单 的 菜单使用 JMenu
//				        JMenuItem findMenuItem = new JMenuItem("查找");					// 创建 二级菜单
//				        JMenuItem replaceMenuItem = new JMenuItem("替换");
//				        // 添加 二级菜单 到 "编辑"一级菜单
//				        editMenu.add(findMenuItem);
//				        editMenu.add(replaceMenuItem);
				        																// 添加 一级菜单 到 弹出菜单
				        popupMenu_right.add(downloadMenuItem);
				        popupMenu_right.add(downloadQueue);
				        popupMenu_right.add(catMenuItem);
				        popupMenu_right.addSeparator();       							// 添加一条分隔符
				        popupMenu_right.add(copyMenuItem);
				        popupMenu_right.add(pasteMenuItem);
				        popupMenu_right.addSeparator();
				        popupMenu_right.add(makeDirMenuItem);
				        popupMenu_right.add(flushMenuItem);
				        popupMenu_right.addSeparator();
				        popupMenu_right.add(deleteMenuItem);
				        popupMenu_right.add(mvMenuItem);
				        

				        downloadMenuItem.addActionListener(new ActionListener() {		// 传输点击监听器
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("下载 点击监听器 被点击");
				            }
				        });
				        downloadQueue.addActionListener(new ActionListener() {			// 队列点击监听器
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("队列 点击监听器 被点击");
				            }
				        });
				        catMenuItem.addActionListener(new ActionListener() {			// 查看点击监听器
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("查看 点击监听器 被点击");
				            }
				        });
				        copyMenuItem.addActionListener(new ActionListener() {			// 复制点击监听器
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("复制 点击监听器 被点击");
				            }
				        });
				        pasteMenuItem.addActionListener(new ActionListener() {			// 粘贴点击监听器
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("粘贴 点击监听器 被点击");
				            }
				        });
				        mvMenuItem.addActionListener(new ActionListener() {				// 重命名点击监听器
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("重命名 点击监听器 被点击");
				            }
				        });
				        deleteMenuItem.addActionListener(new ActionListener() {			// 删除点击监听器
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("删除 点击监听器 被点击");
				            }
				        });
				        popupMenu_right.show(invoker, x, y);								// 在指定位置显示弹出菜单
					}
				
		    	});	
			    
        	}else{
        		JOptionPane.showMessageDialog(								// 消息对话框无返回, 仅做通知作用
            	getJF(),
                "用户 或 密码输入错误",
                "错误提示",
                JOptionPane.WARNING_MESSAGE );
        	}
        }
    });
		
		
		// splitPane, scrollPaneLift, tableModel_right					// 失败： panelALL02，splitPane
//		scrollPaneLift.addMouseListener(new MouseListener() {			// 直接在内容面板上添加鼠标监听器--失败改成在
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                // 鼠标点击（按下并抬起）
//            }
//            @Override
//            public void mousePressed(MouseEvent e) {
//                // 鼠标按下
//            }
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                // 鼠标释放
//
//                // 如果是鼠标右键，则显示弹出菜单
//                if (e.isMetaDown()) {
//                    showPopupMenu(e.getComponent(), e.getX(), e.getY());
//                }
//            }
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                // 鼠标进入组件区域
//            }
//            @Override
//            public void mouseExited(MouseEvent e) {
//                // 鼠标离开组件区域
//            }
//        });
		
		
		panelQue = new JPanel();									// 创建 上传/下载文件的过程面板
		panelQue.setLayout(new BorderLayout());						// 设置布局方式为东西南北中布局
			JTableQue_Init();										// JTableQue_Init 初始化
//			TableModel_Que_Add();
																	// PanelQue 直接添加中jtable_Que 表头不显示，
		JScrollPane JSP_Que = new JScrollPane(jtable_Que);			// 所以加上JScrollPane，JScrollPane中再加jtable_Que即可.
		panelQue.add(JSP_Que,BorderLayout.CENTER);
		panelQue.setVisible(true);
		panelQue.setBorder(null);
		

        JTabbedPane tabbedPane = new JTabbedPane();								// 创建选项卡面板
//        tabbedPane.add("传输", new JPanel(new GridLayout(1, 1)));				// 创建第 1 个选项卡（选项卡只包含 标题）
        tabbedPane.addTab("传输文件", new ImageIcon("./src/GUI/picture/传输.png"),
        						panelQue, "上传/下载文件的过程显示");	
        tabbedPane.addTab("任务队列", new ImageIcon("./src/GUI/picture/任务.png"),
        				new JPanel(new GridLayout(1, 1)),
        				"添加文件到队列-启动队列任务才会执行");							// 创建第 2 个选项卡（选项卡包含 标题 和 图标）
        tabbedPane.addTab("失败任务", new ImageIcon("./src/GUI/picture/失败任务.png"), 
        		new JPanel(new GridLayout(1, 1)), "失败任务队列");					// 创建第 3 个选项卡（选项卡包含 标题、图标 和 tip提示）
        
        
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        tabbedPane.setSelectedIndex(0);								// 设置默认选中的选项卡  
        JSplitPane splitUpDown2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitUpDown2.setLeftComponent(splitPane);
        splitUpDown2.setRightComponent(tabbedPane);
            
        splitUpDown2.setOneTouchExpandable(false);					// 分隔条上显示快速 折叠/展开 两边组件的小按钮
        splitUpDown2.setContinuousLayout(false);					// 拖动分隔条时连续重绘组件
        splitUpDown2.setBackground(Color.white);					// 分隔面板背景颜色
        splitUpDown2.setDividerSize(1);								// 分隔条间距
        splitUpDown2.setDividerLocation(720);						// 设置分隔条的初始位置
        splitUpDown2.setBorder(null);
        
        
     
     // 获取 基类的窗口   
//        JFrame jFrame  = super.getJf();
        JFrame jFrame  = getJF();
        
     // 东西南北中布局 添加到窗口并显示
        jFrame.add(getPanel(),BorderLayout.NORTH);
//        jFrame.add(splitPane,BorderLayout.CENTER);
        jFrame.add(splitUpDown2,BorderLayout.CENTER);
//        jFrame.add(splitUpDown,BorderLayout.SOUTH);    
//        jFrame.setContentPane(panel);
        
//        jFrame.setContentPane(panelALL02);
	    jFrame.setVisible(true);
	}
	
	
	
	
	
	
	public void SetFilePath_Left(){
	      String dirPath = "C:/Users/Administrator/Desktop";
	      tfield_Left.setText(dirPath);
//	      System.out.println(dirPath);
	}
	public void SetFilePath_Left(String Path){
	      tfield_Left.setText(Path);
	}
	public String getFilePath_Left(){
		  return tfield_Left.getText();
	}
	
	public void SetFilePath_Right(){
	      String dirPath = "C:/Users/Administrator/Desktop";
	      tfield_Right.setText(dirPath);
	}
	public void SetFilePath_Right(String Path){
	      tfield_Right.setText(Path);
	}
	public String getFilePath_Right(){
		  return tfield_Right.getText();
	}
	
	

/**
 * 	两边“本地访问界面初始化”和“远程访问界面初始化”	
 */
	public void JTable_DefaultTableModel_LeftInit(){	

		tableModel_left = new DefaultTableModel(){									// 创造一个无数据的 jTable.
			private static final long serialVersionUID = 1L;
			@Override  																// 重写 isCellEditable(),使
	        public boolean isCellEditable(int row,int column){  					// jTable设置单元格不可编辑.
	            return false;  
	        }  
		};
//		tableModel_left = (DefaultTableModel) new JTable().getModel();
		
		tableModel_left.addColumn("名称");											// 添加列名
		tableModel_left.addColumn("大小");
		tableModel_left.addColumn("类型");
		tableModel_left.addColumn("修改时间");
//		jtable_left.getColumnModel().getColumn(3).disableResizedPosting(); //鼠标选中一行一样
	    
	    jtable_left = new JTable(tableModel_left);									// 使用 表格模型 创建 表格
        // 使用 表格模型 创建 行排序器（TableRowSorter 实现了 RowSorter）
        RowSorter<TableModel> rowSorter_left = new TableRowSorter<TableModel>(tableModel_left); 
        jtable_left.setRowSorter(rowSorter_left);									// 给 表格 设置 行排序器  
        jtable_left.getTableHeader().setResizingAllowed(true);						// 设置用户是否可以通过在头间拖动来调整各列的大小
        jtable_left.getTableHeader().setReorderingAllowed(false);					// 设置用户是否可以拖动列头，以重新排序各列
        jtable_left.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        jtable_left.getTableHeader().setFont( 
        		new Font("微软雅黑", Font.PLAIN, 13));
//    			new Font(null, Font.PLAIN+Font.BOLD, 12));							// 设置表头名称字体样式
        jtable_left.getTableHeader().setForeground(Color.BLACK);					// 设置表头名称字体颜色
        jtable_left.getTableHeader().setBackground(Color.white);
        jtable_left.getTableHeader().setPreferredSize(
				new Dimension(getJF().getWidth(), 25));								// 表头宽度
        jtable_left.setRowHeight(22);												// 设置行的宽度
        jtable_left.getColumnModel().getColumn(0).setPreferredWidth(100);
        jtable_left.getColumnModel().getColumn(1).setPreferredWidth(10);			// 设置列的宽度
        jtable_left.getColumnModel().getColumn(2).setPreferredWidth(10);			// 设置列的宽度
        jtable_left.getColumnModel().getColumn(3).setPreferredWidth(40);

//			    		    													设置JTbale_Left的 “大小”列 为右对齐
    	DefaultTableCellRenderer rightRenderer_left = new DefaultTableCellRenderer();		// 设置监听器
    	rightRenderer_left.setHorizontalAlignment( JLabel.RIGHT );							// 右对齐
    	jtable_left.getColumnModel().getColumn(1).setCellRenderer(rightRenderer_left);		// 设置指定列对齐方式   	
    	jtable_left.setGridColor(Color.white);												// 设置网格颜色
//			    		jtable.setShowGrid(false);											// 设置是否显示网格
//			    		void setShowHorizontalLines(boolean showHorizontalLines)			// 水平方向网格线是否显示
//			    		void setShowVerticalLines(boolean showVerticalLines)				// 竖直方向网格线是否显示
    	jtable_left.setShowHorizontalLines(false);
    	jtable_left.setBorder(null);
    	
    	jtable_left.addMouseListener(new MouseAdapter() {
    		 @Override
             public void mouseReleased(MouseEvent e) {										// 鼠标释放事件
                 if (e.isMetaDown()) {
                     showPopupMenu_left(e.getComponent(), e.getX(), e.getY());				// 如果是鼠标右键，则显示弹出菜单
                 }
             }
			
			protected void showPopupMenu_left(Component invoker, int x, int y) {     
				JPopupMenu popupMenu_left = new JPopupMenu();							// 创建 弹出菜单 对象
		        																		// 创建 一级菜单
				JMenuItem uploadMenuItem = new JMenuItem(" 上传(U)",new ImageIcon("./src/GUI/picture/upload.png"));
		        uploadMenuItem.setFont(new Font("黑体", Font.PLAIN+Font.BOLD, 14)); 		// 设置字体
		        JMenuItem uploadQueue = new JMenuItem(" 添加到队列(Q)",new ImageIcon("./src/GUI/picture/uploadadd.png"));
		        uploadQueue.setFont(new Font("黑体", Font.PLAIN, 14)); 					// 设置字体
		        JMenuItem catMenuItem = new JMenuItem(" 查看文本");
		        catMenuItem.setFont(new Font("黑体", Font.PLAIN, 14)); 					// 设置字体
		        JMenuItem copyMenuItem = new JMenuItem(" 复制        Ctrl+C",new ImageIcon("./src/GUI/picture/copy.png"));
		        copyMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
		        JMenuItem pasteMenuItem = new JMenuItem(" 粘贴        Ctrl+V",new ImageIcon("./src/GUI/picture/paste.png"));
		        pasteMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
		        JMenuItem deleteMenuItem = new JMenuItem(" 删除(D)");
		        deleteMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
		        JMenuItem mvMenuItem = new JMenuItem(" 重命名(M)");
		        mvMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
		        JMenuItem makeDirMenuItem = new JMenuItem(" 创建目录(C)");
		        makeDirMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));
		        JMenuItem flushMenuItem = new JMenuItem(" 刷新(F)");
		        flushMenuItem.setFont(new Font("黑体", Font.PLAIN, 14));

//		        JMenu editMenu = new JMenu("编辑");   							// 需要 添加 二级子菜单 的 菜单使用 JMenu
//		        JMenuItem findMenuItem = new JMenuItem("查找");					// 创建 二级菜单
//		        JMenuItem replaceMenuItem = new JMenuItem("替换");
//		        // 添加 二级菜单 到 "编辑"一级菜单
//		        editMenu.add(findMenuItem);
//		        editMenu.add(replaceMenuItem);
		        																// 添加 一级菜单 到 弹出菜单
		        popupMenu_left.add(uploadMenuItem);
		        popupMenu_left.add(uploadQueue);
		        popupMenu_left.add(catMenuItem);
		        popupMenu_left.addSeparator();       								// 添加一条分隔符
		        popupMenu_left.add(copyMenuItem);
		        popupMenu_left.add(pasteMenuItem);
		        popupMenu_left.addSeparator();
		        popupMenu_left.add(makeDirMenuItem);
		        popupMenu_left.add(flushMenuItem);
		        popupMenu_left.addSeparator();
		        popupMenu_left.add(deleteMenuItem);
		        popupMenu_left.add(mvMenuItem);
		        
		        uploadMenuItem.setEnabled(uploadStatus);						// 初始状态设置
		        uploadQueue.setEnabled(uploadQueueStatus);		        

		        uploadMenuItem.addActionListener(new ActionListener() {			// 上传点击监听器
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                System.out.println("上传 点击监听器 被点击");
		                
						Upload_jtable_Que();									// 上传初始化界面
						
		     
		                // 模拟延时操作进度, 每隔 0.5 秒更新进度
		                new Timer(500, new ActionListener() {
		                    @Override
		                    public void actionPerformed(ActionEvent e) {
		                    	progressBar_upload.setNumber(progressBar_upload.getNumber()+1);
		                        if (progressBar_upload.getNumber() > 100) {
		                        	progressBar_upload.setNumber(0);
		                        }
		                        progressBar_upload.getProgressBar().setValue(
		                        		progressBar_upload.getNumber());

						    	jtable_Que.validate();
						    	jtable_Que.updateUI();
		                    }
		                }).start();
		                
		                
//		                int selectedRow = jtable_left.getSelectedRow();			// 获取 left被选中行的索引
//		                if (selectedRow != -1) {								// 存在被选中行
//		                	MultiThread_FileTransfer MultiThread = new MultiThread_FileTransfer();
//		                	try {
//								MultiThread.connect(getHost_IP().getText(),getFtp_User().getText(),getFtp_Passwd().getText());
//							} catch (IOException e2) {
//								e2.printStackTrace();
//							}
//		                	if(jtable_left.getValueAt(selectedRow,2).equals("文件夹")){
//		                		String Name = tfield_Left.getText()+"/"+
//		                				jtable_left.getValueAt(selectedRow,0).
//		                				toString().substring(1);
//		                		System.out.println(Name);
//		                		System.out.println(tfield_Right.getText());		// 获取 tfield_Right的Path
////		                		try {
////		                			System.out.println( MultiThread.uploadFile(
////		                					Name,tfield_Right.getText(),
////											MultiThread.getFtpClient())
////		                					);
////								} catch (IOException e1) {
////									e1.printStackTrace();
////								}
//		                	}else{												// 否则获得纯文件
//		                		String Name = tfield_Left.getText()+"/"+
//		                				jtable_left.getValueAt(selectedRow,0).
//		                				toString().substring(1)+"."+
//		                				jtable_left.getValueAt(selectedRow,2);
//		                		System.out.println(Name);
//		                		System.out.println(tfield_Right.getText());		// 获取 tfield_Right的Path
//		                		try {
//		                			System.out.println( MultiThread.uploadFile(
//			            					Name,tfield_Right.getText(),
//											MultiThread.getFtpClient())
//			            					);
//								} catch (IOException e1) {
//									e1.printStackTrace();
//								}
//		                	}
//		                }
		            }
		        });
		        uploadQueue.addActionListener(new ActionListener() {			// 队列点击监听器
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                System.out.println("队列点击监听器 被点击");
		            }
		        });
		        catMenuItem.addActionListener(new ActionListener() {			// 查看点击监听器
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                System.out.println("查看点击监听器 被点击");
		            }
		        });
		        copyMenuItem.addActionListener(new ActionListener() {			// 复制点击监听器
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                System.out.println("复制点击监听器 被点击");
		            }
		        });
		        pasteMenuItem.addActionListener(new ActionListener() {			// 粘贴点击监听器
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                System.out.println("粘贴点击监听器 被点击");
		            }
		        });
		        mvMenuItem.addActionListener(new ActionListener() {				// 重命名点击监听器
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                System.out.println("重命名点击监听器 被点击");
		            }
		        });
		        deleteMenuItem.addActionListener(new ActionListener() {			// 删除点击监听器
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                System.out.println("删除点击监听器 被点击");
		            }
		        });
		        popupMenu_left.show(invoker, x, y);								// 在指定位置显示弹出菜单
			}
		
    	});	
	}
	
	public void Jtbal_DefaultTableModel_RightInit(){
		
		tableModel_right = new DefaultTableModel(){									// 创造一个无数据的 jTable.

			private static final long serialVersionUID = 1L;
			@Override  																// 重写 isCellEditable(),使
	        public boolean isCellEditable(int row,int column){  					// jTable设置单元格不可编辑.
	            return false;  
	        }  
		};
//		tableModel_right = (DefaultTableModel) new JTable().getModel();
		
		jtable_right = new JTable(tableModel_right);									// 使用 表格模型 创建 表格
        															// 使用 表格模型 创建 行排序器（TableRowSorter 实现了 RowSorter）
        RowSorter<TableModel> rowSorter_right = new TableRowSorter<TableModel>(tableModel_right); 
        jtable_right.setRowSorter(rowSorter_right);									// 给 表格 设置 行排序器  
        jtable_right.getTableHeader().setResizingAllowed(true);						// 设置用户是否可以通过在头间拖动来调整各列的大小
        jtable_right.getTableHeader().setReorderingAllowed(false);					// 设置用户是否可以拖动列头，以重新排序各列
        jtable_right.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        jtable_right.getTableHeader().setFont( 
        		new Font("微软雅黑", Font.PLAIN, 13));
//		    			new Font(null, Font.PLAIN+Font.BOLD, 12));					// 设置表头名称字体样式
        jtable_right.getTableHeader().setForeground(Color.BLACK);					// 设置表头名称字体颜色
        jtable_right.getTableHeader().setBackground(Color.white);
        jtable_right.getTableHeader().setPreferredSize(
				new Dimension(getJF().getWidth(), 25));								// 表头宽度
        jtable_right.setRowHeight(22);												// 设置行的宽度

    	rightRenderer_right = new DefaultTableCellRenderer();						// 设置监听器
    	rightRenderer_right.setHorizontalAlignment( JLabel.RIGHT );							// '大小'列右对齐
//    	jtable_right.getColumnModel().getColumn(1).setCellRenderer(rightRenderer_right);	// 设置指定列对齐方式   	
    	jtable_right.setGridColor(Color.white);												// 设置网格颜色
//					   jtable.setShowGrid(false);											// 设置是否显示网格
//					   	void setShowHorizontalLines(boolean showHorizontalLines)			// 水平方向网格线是否显示
//					   	void setShowVerticalLines(boolean showVerticalLines)				// 竖直方向网格线是否显示
    	jtable_right.setShowHorizontalLines(false);
    	jtable_right.setBorder(null);
	}
	
/**
 * 	还可以优化 + 加上系统图标  或者+本地图标.
 * @param tableModel	DefaultTableModel
 * @param Path			本地文件路径
 * @return				true/false
 */
	public boolean DTM_Left_addData(DefaultTableModel tableModel, String Path)
	{	
		General_Panel_JTextField textField = new General_Panel_JTextField();			// 访问本地目录下的文件信息
		ArrayList<String> arrList = textField.getVectorJTable(Path);					// 一维数组接受 返回的一维数组.
		int j =0;
		
		for(int i=1;i<(arrList.size()+1);i++)
		{
			if( (i&3)==0 ){	/* 判断是3的倍数 */
//				坑：无论是 data.add(i,Verctor<E>); 还是 data.set(0, arrList.get(i-4));
//					tableModel.insertRow(j, add); 还是 tableModel.addRow(j, data);
//					都不行，加不进去。
				String[] rowValues = {arrList.get(i-4),
						arrList.get(i-3),arrList.get(i-2),arrList.get(i-1)};
				tableModel.insertRow(j, rowValues);
				j++;
			}
		}
//		获取 tableModel行数>0,添加成功.
		if(tableModel.getRowCount()>0)
			return true;
		else
			return false;
	}
	public boolean isCellEditable(int row, int column)
	{
	return false;
	}
	
	public String[] addDTM_Right_Name()
	{	
		List<String> DirANDFilelist = ftpConnect.FtpPath_FileName();
		String[] column = new String[DirANDFilelist.size()];
		DirANDFilelist.toArray(column);
//    	System.out.println(column.length);
		return column;
	}
	public String[] addDTM_Right_Size()
	{	
		List<String> DirANDFilelist = ftpConnect.FtpPath_FileSize();
		String[] column = new String[DirANDFilelist.size()];
		DirANDFilelist.toArray(column);
//    	System.out.println(column.length);
		return column;
	}
	public String[] addDTM_Right_Type()
	{	
		List<String> DirANDFilelist = ftpConnect.FtpPath_FileType();
		String[] column = new String[DirANDFilelist.size()];
		DirANDFilelist.toArray(column);
//    	System.out.println(column.length);
		return column;
	}
	public String[] addDTM_Right_Time()
	{	
		List<String> DirANDFilelist = ftpConnect.FtpPath_FileTime();
		String[] column = new String[DirANDFilelist.size()];
		DirANDFilelist.toArray(column);
//    	System.out.println(column.length);
		return column;
	}
	public String[] addDTM_Right_Competence()
	{	
		List<String> DirANDFilelist = ftpConnect.FtpPath_FileCompetence();
		String[] column = new String[DirANDFilelist.size()];
		DirANDFilelist.toArray(column);
//    	System.out.println(column.length);
		return column;
	}
	public String[] addDTM_Right_UserGroup()
	{	
		List<String> DirANDFilelist = ftpConnect.FtpPath_FileUserGroup();
		String[] column = new String[DirANDFilelist.size()];
		DirANDFilelist.toArray(column);
//    	System.out.println(column.length);
		return column;
	}
	
	
//	public boolean TableModel_Que(String Path)
//	{	
		//（在后台任务线程中）发布指定属性的值改变事件，通知监听器回调
//		void firePropertyChange(String propertyName, Object oldValue, Object newValue);
//	}
	
	public void JTableQue_Init(){
		
		tableModel_Que = new DefaultTableModel(){									// 创造一个无数据的 jTable.
			private static final long serialVersionUID = 1L;
			@Override  																// 重写 isCellEditable(),使
	        public boolean isCellEditable(int row,int column){  					// jTable设置单元格不可编辑.
	            return false;  
	        }  
		};
		tableModel_Que.addColumn(" 本地文件");											// 添加列名
		tableModel_Que.addColumn("           方向");
		tableModel_Que.addColumn(" 远程文件");
		tableModel_Que.addColumn("           方式");
		tableModel_Que.addColumn("          大小");
		tableModel_Que.addColumn("               进度");
	    
	    jtable_Que = new JTable(tableModel_Que);									// 使用 表格模型 创建 表格
        // 使用 表格模型 创建 行排序器（TableRowSorter 实现了 RowSorter）
        RowSorter<TableModel> rowSorter_Que = new TableRowSorter<TableModel>(tableModel_Que); 
        jtable_Que.setRowSorter(rowSorter_Que);										// 给 表格 设置 行排序器  
        jtable_Que.getTableHeader().setResizingAllowed(true);						// 设置用户是否可以通过在头间拖动来调整各列的大小
        jtable_Que.getTableHeader().setReorderingAllowed(false);					// 设置用户是否可以拖动列头，以重新排序各列
        jtable_Que.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        jtable_Que.getTableHeader().setFont( 
        		new Font("微软雅黑", Font.PLAIN, 13));
//    			new Font(null, Font.PLAIN+Font.BOLD, 12));							// 设置表头名称字体样式
        jtable_Que.getTableHeader().setForeground(Color.BLACK);						// 设置表头名称字体颜色
        jtable_Que.getTableHeader().setBackground(Color.white);
        jtable_Que.getTableHeader().setPreferredSize(
        				new Dimension(getJF().getWidth(), 22)); 					// 设置表头长宽
        jtable_Que.setRowHeight(20);												// 设置行的宽度
        jtable_Que.getColumnModel().getColumn(0).setPreferredWidth(200);
        jtable_Que.getColumnModel().getColumn(1).setPreferredWidth(5);				// 设置列的宽度
        jtable_Que.getColumnModel().getColumn(2).setPreferredWidth(200);			// 设置列的宽度
        jtable_Que.getColumnModel().getColumn(3).setPreferredWidth(5);
        jtable_Que.getColumnModel().getColumn(4).setPreferredWidth(5);
        jtable_Que.getColumnModel().getColumn(5).setPreferredWidth(50);

//			    		    													设置JTbale_Left的 “大小”列 为右对齐
//        DefaultTableCellHeaderRenderer header = new DefaultTableCellHeaderRenderer();
//        header.setHorizontalAlignment(JLabel.CENTER);
//        Border border = BorderFactory.createEtchedBorder();								// 蚀刻边框效果
//        jtable_Que.getTableHeader().setBorder(border);									// 不美观取消
//        jtable_Que.getTableHeader().setDefaultRenderer(header);;							// 表头设置居中对齐
    	DefaultTableCellRenderer rightRenderer_Que = new DefaultTableCellRenderer();		// 设置监听器
    	rightRenderer_Que.setHorizontalAlignment( JLabel.CENTER );							// 居中对齐
    	jtable_Que.getColumnModel().getColumn(4).setCellRenderer(rightRenderer_Que);		// 设置指定列对齐方式   
    	jtable_Que.getColumnModel().getColumn(3).setCellRenderer(rightRenderer_Que);
    	jtable_Que.setGridColor(Color.white);												// 设置网格颜色
//			    		jtable.setShowGrid(false);											// 设置是否显示网格
//			    		void setShowHorizontalLines(boolean showHorizontalLines)			// 水平方向网格线是否显示
//			    		void setShowVerticalLines(boolean showVerticalLines)				// 竖直方向网格线是否显示
    	jtable_Que.setShowHorizontalLines(false);
  
	}

	
	public void Upload_jtable_Que(){														// jtable_Que上传任务初始化显示.
				
		Vector<Object> upload_file = new Vector<>();
		int selectedRow = jtable_left.getSelectedRow();										// 获取 left被选中行的索引
		
		if (selectedRow != -1) {															// 存在被选中行
			if(!jtable_left.getValueAt(selectedRow,2).equals("文件夹")){
				upload_file.add(getFilePath_Left()+jtable_left.getValueAt(selectedRow,0)
						+"."+jtable_left.getValueAt(selectedRow,2));
				upload_file.add(null);							// 直接添加方向图标，失败重写getTableCellRendererComponent()
				upload_file.add(" "+getFilePath_Right()+jtable_left.getValueAt(selectedRow,0)
						+"."+jtable_left.getValueAt(selectedRow,2));
				upload_file.add(" 上传");
				upload_file.add(" "+jtable_left.getValueAt(selectedRow,1));
				upload_file.add(null);
//				upload_file.add(progressBar_upload);										// 怎么添加进度条？
				// 重写getTableCellRendererComponent()方法				
				progressBar_upload = new ProgressBar_TableCellRenderer();
				jtable_Que.getColumnModel().getColumn(5).setCellRenderer(progressBar_upload);			
				Icon_TableCellRenderer icon_upload = new Icon_TableCellRenderer("上传");
//				Icon_TableCellRenderer icon_upload = new Icon_TableCellRenderer("下载");
				jtable_Que.getColumnModel().getColumn(1).setCellRenderer(icon_upload);		// 设置第二列单元格格式.
				
			}else{																			// 就是文件夹遍历展示
				
			}
			
		}
		tableModel_Que.insertRow(0, upload_file);											// 上传初始化显示.
			
	}
	
	
//	@Test
//	public void run() {
//			
//		new panel_JSplitPane();
//		
//	}
	public static void main(String[] args) {
		new panel_JSplitPane();
	}
	
}
