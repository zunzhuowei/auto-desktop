package com.hbs.auto.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 自动化工具：自动操作鼠标、键盘等
 * 包含鼠标单击、双击、右击、按键、组合按键、剪贴板、提示框、提示音、截屏、屏幕大小、坐标等等。
 *
 * @author cheng2839
 * @2018年11月16日
 */
public class MKAutoKit {

    private static Robot robot = null;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击
     *
     * @param x
     * @param y
     * @author cheng2839
     * @2018年11月16日
     */
    public static void leftClick(int x, int y) {
        robot.mouseMove(x, y);
        robot.mousePress(16);
        //InputEvent.BUTTON1_DOWN_MASK
        sleep(10L);
        robot.mouseRelease(16);
    }

    public static void leftClick(int x, int y, int clickTimes) {
        for (int i = 0; i < clickTimes; i++) {
            leftClick(x, y);
        }
    }

    public static void leftDoubleClick(int x, int y) {
        leftClick(x, y, 2);
    }

    /**
     * 右击
     *
     * @param x
     * @param y
     * @author cheng2839
     * @2018年11月16日
     */
    public static void rightClick(int x, int y) {
        robot.mouseMove(x, y);
        robot.mousePress(8);
        sleep(10L);
        robot.mouseRelease(8);
    }

    /**
     * 按键
     *
     * @param keyCode
     * @author cheng2839
     * @2018年11月16日
     */
    public static void key(int keyCode) {
        robot.keyPress(keyCode);
        sleep(10L);
        robot.keyRelease(keyCode);
    }

    /**
     * 组合按键
     *
     * @param keyCodes
     * @author cheng2839
     * @2018年11月16日
     */
    public static void keys(int[] keyCodes) {
        for (int i = 0; i < keyCodes.length; i++) {
            robot.keyPress(keyCodes[i]);
        }
        sleep(10L);
        for (int i = keyCodes.length - 1; i >= 0; i--) {
            robot.keyRelease(keyCodes[i]);
        }
    }

    public static void key(int... keyCodes) {
        keys(keyCodes);
    }

    /**
     * 获取当前鼠标的坐标
     *
     * @return
     * @author cheng2839
     * @2018年11月16日
     */
    public static Point x_y() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    /**
     * 获取剪贴板内容
     *
     * @return
     * @author cheng2839
     * @2018年11月16日
     */
    public static String getClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable content = clipboard.getContents(null);
        if (content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                return (String) content.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 提示音
     *
     * @author cheng2839
     * @2018年11月16日
     */
    public static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }

    /**
     * 设置剪贴板
     *
     * @param data
     * @author cheng2839
     * @2018年11月16日
     */
    public static void setClipboard(String data) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(data);
        clipboard.setContents(selection, null);
    }

    /**
     * 鼠标移动监听
     *
     * @param mills
     * @author cheng2839
     * @2018年11月16日
     */
    public static void mouseMove(long mills) {
        long time = System.currentTimeMillis();
        Point p = x_y();
        while (System.currentTimeMillis() - time <= mills) {
            Point newp = x_y();
            if ((newp.x != p.x) || (newp.y != p.y)) {
                System.out.println("( " + newp.x + ", " + newp.y + " )");
            }
            p = newp;
            sleep(100L);
        }
    }


    /**
     * sleep
     *
     * @param millis
     * @author cheng2839
     * @2018年11月16日
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 屏幕大小
     *
     * @return
     * @author cheng2839
     * @2018年11月16日
     */
    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * 弹框提示
     *
     * @param message
     * @author cheng2839
     * @2018年11月16日
     */
    public static void showTip(String message) {
        JOptionPane.showMessageDialog(null, message, "tip", -1);
    }

    /**
     * 截取全屏并保存为图片
     *
     * @param imgPath
     * @return
     * @author cheng2839
     * @2018年11月16日
     */
    public static File getScreenImg(String imgPath) {
        BufferedImage image = robot.createScreenCapture(new Rectangle(getScreenSize()));
        try {
            File imgFile = new File(imgPath);
            ImageIO.write(image, "JPG", imgFile);
            return imgFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
