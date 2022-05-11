package com.hbs.auto;

import com.hbs.auto.constants.ClickType;
import com.hbs.auto.enties.MouseEvent;
import com.hbs.auto.utils.OpenCvUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by zun.wei on 2022/5/9.
 */
public class AutoDesktopTest2 {

    public static void main2(String[] args) throws InterruptedException {
        AutoRobot.create()
                .keysGroup(KeyEvent.VK_WINDOWS, KeyEvent.VK_D)
                .delay(1000)
                .click(ClickType.LEFT, MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\gj.png"), 2)
                .delay(2000)
                .click(ClickType.LEFT, MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\tj.png"))
                .delay(500)
                .click(ClickType.LEFT,MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\hd.png",60000))
                .delay(1000)
                .click(ClickType.LEFT,MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\qllj.png"))
                .delay(1000)
                .click(ClickType.LEFT,MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\smlj.png"));
    }

    public static void main(String[] args) throws IOException {
        AutoRobot.create()
                .keysGroup(KeyEvent.VK_WINDOWS, KeyEvent.VK_D)
                .delay(1000);
        //final File screen = AutoRobot.create().getScreen("dekAutoPic.bmp", new Rectangle(500, 100, 200, 200));
        final File screen = AutoRobot.create().getScreen("dekAutoPic.bmp");
        final String path = screen.getPath();
        OpenCvUtils.findSmallInLarger("C:\\Users\\hui\\Desktop\\temp\\wx_search.png", path);
        //OpenCvUtils.findSmallInLarger("E:\\java\\IdeaProjects\\DekAuto4J\\test.png", path);

    }

}