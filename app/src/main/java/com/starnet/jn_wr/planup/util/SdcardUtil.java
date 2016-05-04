package com.starnet.jn_wr.planup.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class SdcardUtil {

    /**
     * 将指定文件目录中的文件名显示在ListView里面
     */
    public static File[] fileExplorer(Context context, String fistName, String secondName) {
        // 获取SD卡的路径
        String pathString = Environment.getExternalStorageDirectory().getPath();
        File file = new File(pathString + "/" + fistName + "/" + secondName
                + "/");
        File[] files = null;
        if (file.exists()) {
            files = file.listFiles();
        } else {
//            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
        }
        return files;
    }

    /**
     * 创建二级目录
     *
     * @param context
     */
    public static void creatFile(Context context, String FirstFolder, String SecondFolder) {
        /* ALBUM_PATH取得机器的SD卡位置，File.separator为分隔符“/” */
        String ALBUM_PATH = Environment
                .getExternalStorageDirectory()
                + File.separator
                + FirstFolder
                + File.separator;

        String Second_PATH = ALBUM_PATH + SecondFolder
                + File.separator;
        // 检查手机上是否有外部存储卡
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {// 如果不存在SD卡，进行提示
//         Toast.makeText(context, "请插入您的ＳＤＣＡＲＤ", 100).show();
        } else {// 如果存在SD卡，判断文件夹目录是否存在
            // 一级目录和二级目录必须分开创建
            File dirFirstFile = new File(ALBUM_PATH);// 新建一级主目录
            if (!dirFirstFile.exists()) {// 判断文件夹目录是否存在
                dirFirstFile.mkdir();// 如果不存在则创建
            }
            File dirSecondFile = new File(Second_PATH);// 新建二级主目录
            if (!dirSecondFile.exists()) {// 判断文件夹目录是否存在
                dirSecondFile.mkdir();// 如果不存在则创建
            }
        }
    }

    /**
     * 将文本存入sdcard，二级目录下面
     *
     * @param context
     * @param filename
     */
    public static String saveBitmapToSDcard(Context context, Bitmap bitmap, String firstFolder,
                                            String secondFolder, String filename) {
        BufferedOutputStream os = null;

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 获取SD卡的路径
            String pathString = Environment.getExternalStorageDirectory()
                    .getPath();
//			File f = Environment.getExternalStorageDirectory();
            File file = new File(pathString + "/" + firstFolder + "/"
                    + secondFolder + "/" + filename);
            // 判断文件是否存在，如果不存在创建
            if (file.exists()) {
//				file.delete();
                try {
                    file.createNewFile();
                    os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

                    return file.getPath();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "";
    }

    /**
     * 将文本存入sdcard，二级目录下面
     *
     * @param context
     * @param filename
     * @param content
     */
    public static void writeIntoSDcard(Context context, String firstFolder,
                                       String secondFolder, String filename,
                                       String content) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 获取SD卡的路径
            String pathString = Environment.getExternalStorageDirectory()
                    .getPath();
            File file = new File(pathString + "/" + firstFolder + "/"
                    + secondFolder + "/" + filename);
            FileOutputStream outStream = null;
            // 判断文件是否存在，如果不存在创建
            if (file.exists()) {
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // 写数据到文件newfile.txt
            // 创建一个文件输出流
            try {
                outStream = new FileOutputStream(file, false);// true表示在文件末尾添加
                String msg = new String(content); // 写入文件的内容
                outStream.write(msg.getBytes("UTF-8"));

//				Toast.makeText(context, "保存成功", 100).show();
            } catch (Exception e) {
//				Toast.makeText(context, "保存失败", 100).show();
            } finally {
                if (outStream != null) {
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 返回指定文本文件的内容
     *
     * @param context
     * @param filename
     * @return
     */
    public static String readFromSdCard(Context context, String firstFolder,
                                        String secondFolder, String filename) {
        String data = null;
        String pathString = Environment.getExternalStorageDirectory()
                .getPath();
        File file = new File(pathString + "/" + firstFolder + "/"
                + secondFolder + "/" + filename);

        FileInputStream in = null;
        ByteArrayOutputStream outputStream = null;

        try {
            byte Buffer[] = new byte[1024];// 在内存中开辟一段缓冲区
            in = new FileInputStream(file);// 得到文件输入流
            int len = in.read(Buffer);// 读出来的数据首先放入缓冲区，满了之后再写到字符输出流中
            outputStream = new ByteArrayOutputStream();// 创建一个字节数组输出流
            outputStream.write(Buffer, 0, len);// 把缓冲区的数据写入内存
            data = new String(outputStream.toByteArray());// 从内存中把数据读出来

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();// 关闭输出流
                }

                if (in != null) {
                    in.close();// 关闭输入流
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static String readBufferFile(Context context, String firstFolder,
                                        String secondFolder, String filename) {

        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            String data = null;
            String pathString = Environment.getExternalStorageDirectory()
                    .getPath();
            File file = new File(pathString + "/" + firstFolder + "/"
                    + secondFolder + "/" + filename);
            br = new BufferedReader(new FileReader(file));
            String readline = "";

            while ((readline = br.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 根据一级目录二级目录文件名返回文件
     *
     * @param context
     * @param firstFolder
     * @param secondFolder
     * @param filename
     * @return
     */
    public static File getFile(Context context, String firstFolder,
                               String secondFolder, String filename) {
        String pathString = Environment.getExternalStorageDirectory()
                .getPath();
        File file = new File(pathString + "/" + firstFolder + "/"
                + secondFolder + "/" + filename);
        return file;
    }

    /**
     * 使用文件通道的方式复制文件
     *
     * @param orgfile    源文件
     * @param targetfile 复制到的新文件
     */
    public void fileChannelCopy(File orgfile, File targetfile) {

        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(orgfile);
            fo = new FileOutputStream(targetfile);

            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道

            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fi!=null){
                    fi.close();
                }

                if(in!=null){
                    in.close();
                }

                if(fo!=null){
                    fo.close();
                }

                if(out!=null){
                    out.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件
     */
    public static boolean deleteFoder(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                if (files != null) {
                    for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                        deleteFoder(files[i]); // 把每个文件 用这个方法进行迭代
                    }
                }
            }
            boolean isSuccess = file.delete();
            if (!isSuccess) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据文件路径 删除文件
     */
    public static boolean deleteFoder(Context context, String firstFolder,
                                      String secondFolder, String filename) {
        String pathString = Environment.getExternalStorageDirectory()
                .getPath();
        File file = new File(pathString + "/" + firstFolder + "/"
                + secondFolder + "/" + filename);

        if(file!=null){
            if (file.exists()) { // 判断文件是否存在
                if (file.isFile()) { // 判断是否是文件
                    file.delete(); // delete()方法 你应该知道 是删除的意思;
                } else if (file.isDirectory()) { // 否则如果它是一个目录
                    File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                    if (files != null) {
                        for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                            deleteFoder(files[i]); // 把每个文件 用这个方法进行迭代
                        }
                    }
                }
                boolean isSuccess = file.delete();
                if (!isSuccess) {
                    return false;
                }
            }
        }
        return true;
    }

}
