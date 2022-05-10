package com.hbs.auto;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * Created by zun.wei on 2022/5/10.
 */
public class FileInJarDeleteTest {


    public static void main(String[] args) throws Exception {
        String jarFile = "E:\\java\\IdeaProjects\\auto-desktop\\samples\\target\\samples-1.0.0.jar";
        // 只保留 windows-x86_64 平台的 opencv 依赖包
        final List<String> list = Arrays.asList(
                "android-",
                "linux-",
                "macosx-",
                "ios-",
                "windows-x86.jar"
        );
        delete(jarFile, list);
    }

    /**
     * 删除jar 包中包含某些字符串的文件
     *
     * @param jarName jar
     * @param deletes 包含的文件集合
     */
    private static void delete(String jarName, List<String> deletes) throws Exception {
        //先备份
        File oriFile = new File(jarName);
        if (!oriFile.exists()) {
            System.out.println("######Not Find File:" + jarName);
            return;
        }
        //将文件名命名成备份文件
        String bakJarName = jarName.substring(0, jarName.length() - 3) + "bak" + ".jar";
        File bakFile = new File(bakJarName);
        boolean isOK = oriFile.renameTo(bakFile);
        if (!isOK) {
            System.out.println("######Remame ERR..........");
            return;
        }


        //创建文件（根据备份文件并删除部分）
        JarFile bakJarFile = new JarFile(bakJarName);
        JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarName));
        Enumeration<JarEntry> entries = bakJarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            boolean b = StringUtils.containsAny(entry.getName(), deletes.toArray(new String[0]));
            if (!b) {
                InputStream inputStream = bakJarFile.getInputStream(entry);
                jos.putNextEntry(entry);
                byte[] bytes = readStream(inputStream);
                jos.write(bytes, 0, bytes.length);
            } else {
                System.out.println("Delete:-------" + entry.getName());
            }
        }

        jos.flush();
        jos.finish();
        jos.close();
        bakJarFile.close();

    }

    private static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

}
