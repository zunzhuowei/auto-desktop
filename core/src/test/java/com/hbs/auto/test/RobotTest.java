package com.hbs.auto.test;

import com.hbs.auto.MKAutoKit;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by zun.wei on 2022/5/8.
 */
public class RobotTest {

    public static void main2(String[] args) {
        MKAutoKit.key(KeyEvent.VK_WINDOWS, KeyEvent.VK_R);
        MKAutoKit.setClipboard("notepad");
        MKAutoKit.key(KeyEvent.VK_ENTER);
        MKAutoKit.setClipboard("Created by zun.wei on 2022/5/8.");
        final String clipboard = MKAutoKit.getClipboard();
        MKAutoKit.sleep(500);
        MKAutoKit.key(KeyEvent.VK_CONTROL, KeyEvent.VK_V);//粘贴
    }

    public static void main3(String[] args) {
        final Point point = MKAutoKit.x_y();
        final int x = point.x;
        final int y = point.y;
        MKAutoKit.sleep(3000);
        MKAutoKit.leftDoubleClick(x, y);
    }

    public static void main4(String[] args) {
        //MKAutoKit.key(KeyEvent.VK_WINDOWS, KeyEvent.VK_D);
        //MKAutoKit.sleep(1000);
        MKAutoKit.key(KeyEvent.VK_WINDOWS, KeyEvent.VK_S);
        MKAutoKit.sleep(1000);
        MKAutoKit.setClipboard("微信");
        MKAutoKit.sleep(100);
        MKAutoKit.key(KeyEvent.VK_CONTROL, KeyEvent.VK_V);//粘贴
        MKAutoKit.sleep(1000);
        MKAutoKit.key(KeyEvent.VK_ENTER);
    }


    public static void main(String[] args) {
        MKAutoKit.key(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_W);
        MKAutoKit.sleep(1000);
        MKAutoKit.leftClick(1000, 160);
        MKAutoKit.sleep(1000);
        MKAutoKit.setClipboard("Zun");
        MKAutoKit.sleep(500);
        MKAutoKit.key(KeyEvent.VK_CONTROL, KeyEvent.VK_V);//粘贴
        MKAutoKit.sleep(500);
        MKAutoKit.key(KeyEvent.VK_ENTER);
        MKAutoKit.setClipboard("I'm robot");
        MKAutoKit.sleep(500);
        MKAutoKit.key(KeyEvent.VK_CONTROL, KeyEvent.VK_V);//粘贴
        MKAutoKit.sleep(500);
        MKAutoKit.key(KeyEvent.VK_CONTROL, KeyEvent.VK_ENTER);//粘贴
    }

}
