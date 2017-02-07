package com.sea.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;

public class FileUtils {
   	private FileInputStream inputStream =null;
   	private FileOutputStream outputStream = null;
   	private String newpath = "";
    private static Properties props = null;
    private final static String file_CONFIG="file.properties";
    private static final int BUFFER_SIZE = 16 * 1024 ;
    private String basePath = ServletActionContext.getServletContext().getRealPath("/");
    
     //唯一实例
    private static FileUtils m_instance = new FileUtils();
     // 私有构造函数
    private FileUtils() {
    	try {
            props = new Properties();
            InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(file_CONFIG);
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("ConfigurationSettings initialize failed.", e);
        }	
    }
    public synchronized static FileUtils getInstance() {
        return m_instance;
    }

   public String getProperty(String propertyName){
	   	// 判断文件夹是否存在,如果不存在则创建文件夹
       return props.getProperty(propertyName);
   }
   
   /**
    * 文件上传类
    * 参数：file,上传的文件对象
    * 参数：fileFileName，上传的文件名称
    * 参数：path，配置文件对应的路径配置属性
    * 参数：newFIleName，要保存的文件名称
    * **/
   public String fileUpload (File file, String fileFileName,String path,String newFIleName) {
	    String savePath= this.getProperty(path);
	   	File upload = new File(basePath+savePath); 
	   	// 判断文件夹是否存在,如果不存在则创建文件夹
	   	if (!upload.exists()&& !file .isDirectory()) {
	   		upload.mkdir();
	   	}
	   	try {
	    	if (file != null) {
		    	File f = file;
		    	inputStream = new FileInputStream(f);
		    	outputStream = new FileOutputStream(basePath+savePath+ "/" + newFIleName);
		    	byte[] buf = new byte[1024];
		    	int length = 0;
		    	while ((length = inputStream.read(buf)) != -1) {
		    		outputStream.write(buf, 0, length);
		    	}
		    	inputStream.close();
		    	outputStream.flush();
		    	newpath =basePath+savePath+"/"+newFIleName;
		    	
	    	}
	   	} catch (Exception e) {
	   		e.printStackTrace();
		}
	return newpath;
   }
   
   public boolean checkIsImage(String imgStr) {  
       boolean flag = false;  
       if (imgStr != null) {  
           if (imgStr.endsWith(".gif")  
        		   || imgStr.endsWith(".GIF") 
                   || imgStr.endsWith(".jpg")  
                   || imgStr.endsWith(".JPG") 
                   || imgStr.endsWith(".jpeg") 
                   || imgStr.endsWith(".JPEG") 
                   || imgStr.endsWith(".png")
                   || imgStr.endsWith(".PNG") ) {  
               flag = true;  
           }  
       }  
       return flag;  
   }
 
   public String cutImage(String srcPath, int width, int height)
			throws IOException {
		File srcFile = new File(srcPath);
		BufferedImage image = ImageIO.read(srcFile);
		int srcWidth = image.getWidth(null);
		int srcHeight = image.getHeight(null);
		int newWidth = 0, newHeight = 0;
		int x = 0, y = 0;
		double scale_w = (double) width / srcWidth;
		double scale_h = (double) height / srcHeight;
		System.out.println("scale_w=" + scale_w + ",scale_h=" + scale_h);
		// 按原比例缩放图片
		if (scale_w < scale_h) {
			newHeight = height;
			newWidth = (int) (srcWidth * scale_h);
			x = (newWidth - width) / 2;
		} else {
			newHeight = (int) (srcHeight * scale_w);
			newWidth = width;
			y = (newHeight - height) / 2;
		}
		BufferedImage newImage = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_RGB);
		newImage.getGraphics()
				.drawImage(
						image.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
		// 保存缩放后的图片
		String fileSufix = srcFile.getName().substring(
				srcFile.getName().lastIndexOf(".") + 1);
		String aa = UUID.randomUUID().toString() + "." + fileSufix;
		File destFile = new File(srcFile.getParent(), aa);
		// ImageIO.write(newImage, fileSufix, destFile);
		// 保存裁剪后的图片
		System.out.println("fileSufix====" + fileSufix);
		System.out.println("image=====" + srcPath + aa);
		ImageIO.write(newImage.getSubimage(x, y, width, height), fileSufix,
				destFile);
		return aa;
	}
}