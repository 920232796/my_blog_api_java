package top.xingzhaohu1212.blog.controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.util.UUID;

public class test {

    public static void main(String[] args) {
//        String host = "";
//        try {
//            host = InetAddress.getLocalHost().getHostAddress();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(host);
//        try {
//            InputStream is = new FileInputStream(new File("d://11.jpg"));
//            BufferedImage prevImage = ImageIO.read(is);
//            int width = prevImage.getWidth();
//            int height = prevImage.getHeight();
//            System.out.println(width +  "*" + height);
//
//            BufferedImage tag = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_INT_RGB);
//            tag.getGraphics().drawImage(prevImage, 0, 0,width / 2, height / 2, null);
//            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("d://11.jpg"));
//            ImageIO.write(tag, "jpg",out);
//            is.close();
//            out.close();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//


        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        uuid = uuid.replace("-", "");
        System.out.println(uuid);

    }
}
