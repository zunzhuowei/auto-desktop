package com.hbs.auto;

import com.hbs.auto.utils.OpenCvUtils;
import org.apache.commons.lang3.RegExUtils;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;

import javax.swing.*;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

/**
 * Created by zun.wei on 2023/11/30.
 */
public class Img2TextApp {
    public static String filePath = System.getProperty("user.dir");

    public static void main(String[] args) {

    }


    public static void main2(String[] args) throws InterruptedException {
        Thread.sleep(2000);
        String str01 = JOptionPane.showInputDialog(null, "请输入所需内容");
        System.out.println("str01 = " + str01);
    }


}
