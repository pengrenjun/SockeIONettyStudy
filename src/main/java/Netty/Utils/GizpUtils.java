package Netty.Utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Description:  ���ļ����н�ѹ��
 * @Author��pengrj
 * @Date : 2018/11/5 0005 22:41
 * @version:1.0
 */
public class GizpUtils {

    //ͨ���ļ����ڵ�·�����ļ�ѹ�������ֽ�����
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

    /*��ѹ�����ļ������н�ѹ ���ؽ�ѹ����*/
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


    /*��ѹ�����ļ��ֽ������н�ѹ���Ҵ��뵽ָ��·������*/
    public static void loadFileByGZIPbytesAndFilePath(String filePath,byte[] gzipBytes) throws IOException {

        byte[] bytes=unGzipByBytes(gzipBytes);

        FileOutputStream fileOutStream=new FileOutputStream(filePath);

        fileOutStream.write(bytes);

        fileOutStream.close();

    }

}
