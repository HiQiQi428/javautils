package org.luncert.simpleutils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

public class IOHelper {

    /**
     * 读输入流
     * @return 内容
     */
    public static String read(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) buffer.append(line).append("\n");
        reader.close();
        return buffer.toString();
    }

    /**
     * 读文件
     * @return 文件内容
     */
    public static String read(File file) throws IOException {
        return read(new FileInputStream(file));
    }

    /**
     * 存储数据到磁盘
     * @param inputStream 数据输入流
     * @param storePath 目标存储路径
     */
    public static void save(InputStream inputStream, String storePath) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(storePath));
        int len;
        byte[] bs = new byte[1024];
        while ((len = inputStream.read(bs)) != -1) bos.write(bs, 0, len);
        bos.flush();
        bos.close();
    }

    /**
     * 存储图片到磁盘
     * @param inputStream 图片输入流
     * @param format 存储图片的格式，默认jpeg
     * @return 图片存储时的名字，是根据时间生成的hashcode
     */
    public static String saveImage(InputStream inputStream, String format, String storePath) throws IOException {
        if (format == null || format.length() == 0) format = "jpeg";
        // generate picture name
        String picName = CipherHelper.hashcode(new Date().toString());
        // 转换图片格式，并存储
        BufferedImage source = ImageIO.read(inputStream);
        BufferedImage target = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
        target.createGraphics().drawImage(source, 0, 0, Color.WHITE, null);
        ImageIO.write(target, format, new File(storePath, picName));
        return picName;
    }

    /**
     * 写数据到HttpServletResponse
     * @param contentType 数据类型，将设置到response header中
     * @param content 数据
     * @param response HttpServletResponse
     * @param cors 是否允许跨源访问
     */
    public static void writeResponse(String contentType, String content, HttpServletResponse response, boolean cors) throws IOException {
        byte[] data = content.getBytes();
        response.reset();
        // cors
        if (cors) {
            response.setHeader("Access-Control-Allow-Origin", "*");  
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");  
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");  
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "3600");  
        }
        // content
        response.setContentType(contentType);
        response.setContentLength(data.length);
        // output
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 写数据流到HttpServletResponse
     * @param contentType 数据类型，将设置到response header中
     * @param inputStream 数据流
     * @param response HttpServletResponse
     * @param cors 是否允许跨源访问
     */
    public static void writeResponse(String contentType, InputStream inputStream, HttpServletResponse response, boolean cors) throws IOException {
        writeResponse(contentType, read(inputStream), response, cors);
    }

    /**
     * 写文件到HttpServletResponse
     * @param file 文件
     * @param response HttpServletResponse
     * @param cors 是否允许跨源访问
     */
    public static void writeResponse(File file, HttpServletResponse response, boolean cors) throws IOException {
        writeResponse(ContentType.CONTENT_TYPE_BIN + ";charset=UTF-8", read(file), response, cors);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(file.getName(), "UTF-8")));
    }
    
}