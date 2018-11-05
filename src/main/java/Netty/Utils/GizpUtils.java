package Netty.Utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Description:  对文件进行解压缩
 * @Author：pengrj
 * @Date : 2018/11/5 0005 22:41
 * @version:1.0
 */
public class GizpUtils {

    //通过文件所在的路径将文件压缩返回字节数组
    public static byte[] gizpByFilePath(String filePath) throws IOException {

        File file=new File(filePath);

        FileInputStream fileInputStream=new FileInputStream(file);

        byte[] bytes=new byte[fileInputStream.available()];

        fileInputStream.read(bytes);

        fileInputStream.close();

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        GZIPOutputStream gzipOutputStream=new GZIPOutputStream(byteArrayOutputStream);

        gzipOutputStream.write(bytes);

        gzipOutputStream.finish();

        gzipOutputStream.close();

        byte[] gzipbytes=byteArrayOutputStream.toByteArray();

        byteArrayOutputStream.close();

        return gzipbytes;

    }

    /*将压缩的文件流进行解压 返回解压的流*/
    public static byte[]  unGzipByBytes(byte[] bytes) throws IOException {

        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);

        GZIPInputStream gzipInputStream=new GZIPInputStream(byteArrayInputStream);

        int readposition=-1;

        byte[] readBytes=new byte[bytes.length];

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        while((readposition=gzipInputStream.read(readBytes,0,readBytes.length))!=-1){

            byteArrayOutputStream.write(readBytes,0,readposition);

        }
        byteArrayInputStream.close();
        gzipInputStream.close();

        byte[] outbytes=byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();


        return outbytes;
    }


    /*将压缩的文件字节流进行解压并且存入到指定路径里面*/
    public static void loadFileByGZIPbytesAndFilePath(String filePath,byte[] gzipBytes) throws IOException {

        byte[] bytes=unGzipByBytes(gzipBytes);

        FileOutputStream fileOutStream=new FileOutputStream(filePath);

        fileOutStream.write(bytes);

        fileOutStream.close();

    }

}
