/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:21
 */
public class FileZip {
    /**
     * @param inputFileName 你要压缩的文件夹(整个完整路径)
     * @param zipFileName 压缩后的文件(整个完整路径)
     */
    public static void zip(String inputFileName, String zipFileName) throws Exception {
        zip(zipFileName, new File(inputFileName));
    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName));
        zip(out, inputFile, "");
        out.flush();
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            //System.out.println(base);
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }

    public static void main(String [] temp){
        try {
            zip("E:\\ftl","E:\\test.zip");//你要压缩的文件夹      和  压缩后的文件
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
