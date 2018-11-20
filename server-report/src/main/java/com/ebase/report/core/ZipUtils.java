package com.ebase.report.core;

import com.ebase.report.core.utils.ExportExcelUtils;
import groovy.io.FileType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private static final int DEFAULT_BUFF_SIZE = 1024;


    /**
     * 打包成zip包
     */
    public static void generateZip(OutputStream os, List<File> files) throws Exception {
        ZipOutputStream out = null;
        try {
            byte[] buffer = new byte[1024];
            //生成的ZIP文件名为Demo.zip
            out = new ZipOutputStream(os);
            //需要同时下载的两个文件result.txt ，source.txt
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                //读入需要下载的文件的内容，打包到zip文件
                while ((len = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * 下载文件
     * @param paths 多个要打包的文件地址
     * @param zipName 下载的文件名称
     * @throws IOException
     */
    public static void downLoadZipFile(List<String> paths,String zipName) throws IOException{
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename="+zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
            for(String s:paths){
                ZipUtils.doCompress(s, out);
                response.flushBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
    }

    /**
     * 解压zip格式文件
     *
     * @param path zip文件地址。
     * @param targetDir  要解压到的目标路径。
     * @return 如果目标文件不是zip文件则返回false。
     * @throws IOException 如果发生I/O错误。
     */
    public static List<String> decompressZip(String path, String targetDir) throws IOException {

        File originFile = new File(path);
        if (!targetDir.endsWith(File.separator)) {
            targetDir += File.separator;
        }
        ZipFile zipFile = new ZipFile(originFile);
        ZipEntry zipEntry;
        Enumeration entry = zipFile.entries();
//        Enumeration<ZipEntry> entry = zipFile.e();
        List<String> paths = new ArrayList<>();
        while (entry.hasMoreElements()) {
            zipEntry = (ZipEntry)entry.nextElement();
            String fileName = zipEntry.getName();

            paths.add(targetDir + fileName);
            File outputFile = new File(targetDir + fileName);

            OutputStream outputStream = new FileOutputStream(outputFile);
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            int len;
            byte[] buffer = new byte[8192];
            while (-1 != (len = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            inputStream.close();
        }
        zipFile.close();
        return paths;
    }



    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(new File(srcFile), new File(zipFile));
    }

    /**
     * 文件压缩
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File zipFile) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcFile, out);
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();//记得关闭资源
        }
    }

    public static void doCompress(String filelName, ZipOutputStream out) throws IOException{
        doCompress(new File(filelName), out);
    }

    public static void doCompress(File file, ZipOutputStream out) throws IOException{
        doCompress(file, out, "");
    }

    public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        try{
            if ( inFile.isDirectory() ) {
                File[] files = inFile.listFiles();
                if (files!=null && files.length>0) {
                    for (File file : files) {
                        String name = inFile.getName();
                        if (!"".equals(dir)) {
                            name = dir + "/" + name;
                        }
                        ZipUtils.doCompress(file, out, name);
                    }
                }
            } else {
                ZipUtils.doZip(inFile, out, dir);
            }
        }finally {
            inFile.delete();
        }

    }

    public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (!"".equals(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0 ;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

}