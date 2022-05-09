package com.hbs.auto.test;

import com.hbs.auto.SuperPatrickLibrary;
import com.sun.jna.Native;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

/**
 * Created by zun.wei on 2022/5/8.
 */
public class SuperPatrickTest {


    public static void main(String[] args) throws InterruptedException {

        System.setProperty("jna.encoding", "GBK");


        ClassPathResource classPathResource = new ClassPathResource("SuperPatrickLibrary.dll");
        final String path = classPathResource.getPath();
        String dllPath = path;//"C:/1/SuperPatrickLibrary.dll";
        SuperPatrickLibrary superpatrick = (SuperPatrickLibrary) Native.loadLibrary(dllPath, SuperPatrickLibrary.class);

        superpatrick.findElement("307", "显示桌面", "", "Button");

        superpatrick.sendShortCutKeys("{WIN}r");

        Thread.sleep(2000);

        superpatrick.sendKeys("notepad");

        superpatrick.sendShortCutKeys("{Return}");

        Thread.sleep(500);

        superpatrick.sendKeys("欢迎使用来到AutoTestOps测试开发专业网站！！！");

        Thread.sleep(500);

        superpatrick.findElement("", "文件(F)", "", "MenuItem");

        superpatrick.findElement("3","保存(S)	Ctrl+S","","MenuItem");

        Thread.sleep(500);

        superpatrick.sendKeys("test.txt");

        superpatrick.findElement("1", "", "Button", "Button");

        superpatrick.findElement("CommandButton_6", "是(Y)", "CCPushButton", "Button");

        superpatrick.findElement("", "关闭", "", "Button");


    }

}
