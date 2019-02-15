package top.xingzhaohu1212.blog.lib;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.util.HashMap;

public class ImageHandle {

    public static HashMap<String, Integer>  getWidthHeight(String path) {
        HashMap<String, Integer> resultMap = new HashMap<>();

        try {

            InputStream is = new FileInputStream(new File(path));
            BufferedImage prevImage = ImageIO.read(is);
            int width = prevImage.getWidth();
            int height = prevImage.getHeight();
            System.out.println(width + "*" + height);
            resultMap = new HashMap<>();
            resultMap.put("width", width);
            resultMap.put("height", height);
            is.close();
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static void modifyImageSize(String path, String suffix, double ratio) {

        try {
            InputStream in = new FileInputStream(new File(path));
            BufferedImage prevImage = ImageIO.read(in);
            int width = prevImage.getWidth();
            int height = prevImage.getHeight();
            System.out.println(width +  "*" + height);
            BufferedImage tag = new BufferedImage((int)(width / ratio), (int)(height / ratio), BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(prevImage, 0, 0,(int)(width / ratio), (int)(height / ratio),null);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            ImageIO.write(tag, suffix,out);
            in.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
