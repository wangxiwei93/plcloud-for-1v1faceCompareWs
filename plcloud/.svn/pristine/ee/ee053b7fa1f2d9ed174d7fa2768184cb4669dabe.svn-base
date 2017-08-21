package com.routon.idr.tools;

import java.awt.image.BufferedImage;
import java.awt.image.ComponentSampleModel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * 
 * @author wangxiwei93
 *
 */
public class ConvertBGR {

	   /**
     * @param image
     * @param bandOffset 用于判断通道顺序
     * @return
     */
    private static boolean equalBandOffsetWith3Byte(BufferedImage image,int[] bandOffset){
        if(image.getType()==BufferedImage.TYPE_3BYTE_BGR){
            if(image.getData().getSampleModel() instanceof ComponentSampleModel){
                ComponentSampleModel sampleModel = (ComponentSampleModel)image.getData().getSampleModel();
                if(Arrays.equals(sampleModel.getBandOffsets(), bandOffset)){
                    return true;
                }
            }
        }
        return false;       
    }	
    
    /**
     * 判断图像是否为BGR格式
     * @return 
     */
    public static boolean isBGR3Byte(BufferedImage image){
        return equalBandOffsetWith3Byte(image,new int[]{0, 1, 2});
    }
    /**
     * 判断图像是否为RGB格式
     * @return 
     */
    public static boolean isRGB3Byte(BufferedImage image){
        return equalBandOffsetWith3Byte(image,new int[]{2, 1, 0});
    }
    
    /**
     * 对图像解码返回BGR格式矩阵数据
     * @param image
     * @return
     */
    public static byte[] getMatrixBGR(BufferedImage image){
        if(null==image)
            throw new NullPointerException();
        byte[] matrixBGR;
        if(isBGR3Byte(image)){
            matrixBGR= (byte[]) image.getData().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
        }else{          
            // ARGB格式图像数据
            int intrgb[]=image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            matrixBGR=new byte[image.getWidth() * image.getHeight()*3];
            // ARGB转BGR格式
            for(int i=0,j=0;i<intrgb.length;++i,j+=3){
                matrixBGR[j]=(byte) (intrgb[i]&0xff);
                matrixBGR[j+1]=(byte) ((intrgb[i]>>8)&0xff);
                matrixBGR[j+2]=(byte) ((intrgb[i]>>16)&0xff);
            }
        } 
        return matrixBGR;
    }
    
	public static void main(String[] args) {
		String imgPath = "C:\\Users\\wangxiwei93\\Desktop\\789.png";
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(imgPath));
			getMatrixBGR(image);
			System.out.println(isBGR3Byte(image));
			System.out.println(isRGB3Byte(image));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

}
