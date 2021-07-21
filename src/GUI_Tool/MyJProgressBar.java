package GUI_Tool;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MyJProgressBar extends JComponent{
 
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
    private short img_w;
    private short img_h;
     
    private BufferedImage imgbg;
    private short imgbg_w;
    private short imgbg_h;
     
    private BufferedImage imgend;
    private short imgend_w;
    private short imgend_h;
     
    private BufferedImage imgl;
    private short imgl_w;
    private short imgl_h;
     
    private BufferedImage imgr;
    private short imgr_w;
    private short imgr_h;
    /**
     * 显示的最大/小  宽度
     */
    private Integer minsize;
    private Integer maxsize=880;
    private Integer value=1;
    /**
     * 进度的大小
     */
    private int max=100;
     
    public MyJProgressBar(){
        super();
        LoadImage(new File("./src/GUI/picture/xiangsu.png"), 
        		new File("./src/GUI/picture/progreess_BackGround"),
        		new File("./src/GUI/picture/guang.png"), 
        		new File("./src/GUI/picture/progreess_l.png"),
        		new File("./src/GUI/picture/progress_r.png"));
//        LoadImage(new File(UrlBean.progreess_url),new File(UrlBean.progreess_BackGround_url),
//        new File(UrlBean.progreess_end_url)
//        ,new File(UrlBean.progreess_l_url),new File(UrlBean.progreess_r_url));
    }
     
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        g.drawImage(imgbg, 0, 0, imgbg_w, imgbg_h, null);
        for(int i=0;i<=value;i++){
            if(i==0){
                g.drawImage(imgl, 15, 14, imgl_w, imgl_h, null);
                g.drawImage(img, i+22, 14, img_w, img_h, null);
            }else if(i==value){
                g.drawImage(imgr, value+22, 14, imgr_w, imgr_h, null);
                g.drawImage(imgend, (value+22)-(imgend_w-1)/2, 14, imgend_w, imgend_h, null);
            }else{
                g.drawImage(img, i+22, 14, img_w, img_h, null);
            }
        }
    }
     
    public void LoadImage(File file,File filebg,File fileend,File filel,File filer){
        try {
            img = ImageIO.read(file);
            img_w = (short) img.getWidth();
            img_h = (short) img.getHeight();
             
            imgbg = ImageIO.read(filebg);
            imgbg_w = (short) imgbg.getWidth();
            imgbg_h = (short) imgbg.getHeight();
             
            imgend = ImageIO.read(fileend);
            imgend_w = (short) imgend.getWidth();
            imgend_h = (short) imgend.getHeight();
             
            imgl = ImageIO.read(filel);
            imgl_w = (short) imgl.getWidth();
            imgl_h = (short) imgl.getHeight();
             
            imgr = ImageIO.read(filer);
            imgr_w = (short) imgr.getWidth();
            imgr_h = (short) imgr.getHeight();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    public BufferedImage getImg() {
        return img;
    }
 
    public void setImg(BufferedImage img) {
        this.img = img;
    }
 
    public short getImg_w() {
        return img_w;
    }
 
    public void setImg_w(short img_w) {
        this.img_w = img_w;
    }
 
    public short getImg_h() {
        return img_h;
    }
 
    public void setImg_h(short img_h) {
        this.img_h = img_h;
    }
 
    public BufferedImage getImgbg() {
        return imgbg;
    }
 
    public void setImgbg(BufferedImage imgbg) {
        this.imgbg = imgbg;
    }
 
    public short getImgbg_w() {
        return imgbg_w;
    }
 
    public void setImgbg_w(short imgbg_w) {
        this.imgbg_w = imgbg_w;
    }
 
    public short getImgbg_h() {
        return imgbg_h;
    }
 
    public void setImgbg_h(short imgbg_h) {
        this.imgbg_h = imgbg_h;
    }
 
    public BufferedImage getImgend() {
        return imgend;
    }
 
    public void setImgend(BufferedImage imgend) {
        this.imgend = imgend;
    }
 
    public short getImgend_w() {
        return imgend_w;
    }
 
    public void setImgend_w(short imgend_w) {
        this.imgend_w = imgend_w;
    }
 
    public short getImgend_h() {
        return imgend_h;
    }
 
    public void setImgend_h(short imgend_h) {
        this.imgend_h = imgend_h;
    }
 
    public BufferedImage getImgl() {
        return imgl;
    }
 
    public void setImgl(BufferedImage imgl) {
        this.imgl = imgl;
    }
 
    public short getImgl_w() {
        return imgl_w;
    }
 
    public void setImgl_w(short imgl_w) {
        this.imgl_w = imgl_w;
    }
 
    public short getImgl_h() {
        return imgl_h;
    }
 
    public void setImgl_h(short imgl_h) {
        this.imgl_h = imgl_h;
    }
 
    public BufferedImage getImgr() {
        return imgr;
    }
 
    public void setImgr(BufferedImage imgr) {
        this.imgr = imgr;
    }
 
    public short getImgr_w() {
        return imgr_w;
    }
 
    public void setImgr_w(short imgr_w) {
        this.imgr_w = imgr_w;
    }
 
    public short getImgr_h() {
        return imgr_h;
    }
 
    public void setImgr_h(short imgr_h) {
        this.imgr_h = imgr_h;
    }
 
 
    public Integer getValue() {
        return value;
    }
 
    public Integer getMinsize() {
        return minsize;
    }
 
    public void setMinsize(Integer minsize) {
        this.minsize = minsize;
    }
 
    public Integer getMaxsize() {
        return maxsize;
    }
 
    public void setMaxsize(Integer maxsize) {
        this.maxsize = maxsize;
    }
 
    public int getMax() {
        return max;
    }
 
    public void setMax(int max) {
        this.max = max;
    }
 
    public void setValue(Integer value) {
        if(value==0){
            return;
        }
        double maxx = Double.parseDouble(max+"");
        double valuee = Double.parseDouble(value+"");
        double maxxx = Double.parseDouble(maxsize+"");
        value = (int) (valuee/maxx*maxxx);
        if(value<=maxsize){
            this.value = value;
            repaint();
        }
    }
}
