package com.hbs.auto;

import com.hbs.auto.constants.ClickPosition;
import com.hbs.auto.constants.ClickType;
import com.hbs.auto.enties.MouseEvent;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zun.wei on 2022/5/9.
 */
public class AutoRobot {

    /**
     * 屏幕的坐标x,y起始点（0,0）在屏幕的左上角，终点在右下角（如：1920,1080）;取决于分辨率；
     */

    private AutoRobot(Robot robot) throws AWTException {
        this.robot = robot;
    }

    private final Robot robot;

    static {
        Loader.load(opencv_java.class);
    }

    private final static int MOUSE_LEFT_BUTTON = 1 << 4;
    private final static int MOUSE_MID_BUTTON = 1 << 3;
    private final static int MOUSE_RIGHT_BUTTON = 1 << 2;

    /**
     * 创建机器人
     */
    public static AutoRobot create() {
        try {
            final Robot robot = new Robot();
            return new AutoRobot(robot);
        } catch (AWTException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 单次，延迟毫秒数
     *
     * @param time 毫秒数
     */
    public AutoRobot delay(int time) {
        this.robot.delay(time);
        return this;
    }

    /**
     * 移动数据到屏幕某个地方
     *
     * @param x x 坐标
     * @param y y 坐标；
     * @return
     */
    public AutoRobot mouseMove(int x, int y) {
        this.robot.mouseMove(x, y);
        delay(10);
        return this;
    }

    /**
     * 移动数据到屏幕某个地方，
     *
     * @param point 坐标点；屏幕的坐标x,y起始点（0,0）在屏幕的左上角，终点在右下角（如：1920,1080）;取决于分辨率；
     */
    public AutoRobot mouseMove(Point point) {
        return mouseMove(point.x, point.y);
    }

    /**
     * 鼠标点击
     *
     * @param type  点击类型，左键，中间，右键
     * @param times 点击次数
     */
    public AutoRobot click(ClickType type, MouseEvent event, int times) {
        final ClickPosition clickPosition = event.clickPosition;
        // 点击某个坐标
        if (clickPosition == ClickPosition.POINT) {
            for (int i = 0; i < times; i++) {
                click(type, event.x, event.y, event.time);
            }
        }
        // 点击图片
        else {

        }
        return this;
    }

    /**
     * 鼠标点击
     *
     * @param type 点击类型
     * @param x    x 坐标
     * @param y    y 坐标
     * @param time 鼠标按压时间
     */
    private AutoRobot click(ClickType type, int x, int y, int time) {
        if (type == ClickType.LEFT) {
            mouseMove(x, y);
            robot.mousePress(MOUSE_LEFT_BUTTON);
            delay(time);
            robot.mouseRelease(MOUSE_LEFT_BUTTON);
        } else if (type == ClickType.MIDDLE) {
            mouseMove(x, y);
            robot.mousePress(MOUSE_MID_BUTTON);
            delay(time);
            robot.mouseRelease(MOUSE_MID_BUTTON);
        } else {
            mouseMove(x, y);
            robot.mousePress(MOUSE_RIGHT_BUTTON);
            delay(time);
            robot.mouseRelease(MOUSE_RIGHT_BUTTON);
        }
        return this;
    }

    /**
     * 使用Robot的自带方式
     * 经测试
     * 执行速度平均在  300-350ms
     * 优点：比较稳定，可以连续点击
     * 具体速度跟电脑速度有关
     *
     * @param name 截图全限定文件名称
     */
    public File getScreen(String name) throws IOException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRect = new Rectangle(d);
        BufferedImage bufferedImage = robot.createScreenCapture(screenRect);
        File file = new File(name);
        ImageIO.write(bufferedImage, "bmp", file);
        return file;
    }

    /**
     * 根据提供的矩形框Rect来截取屏幕
     *
     * @param name      截图全限定文件名称
     * @param rectangle 矩形
     */
    public File getScreen(String name, Rectangle rectangle) throws IOException {
        BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
        File file = new File(name);
        ImageIO.write(bufferedImage, "bmp", file);
        return file;
    }

    /**
     * 调用系统自带截图PrintScreen
     * 然后从剪切板中拿到数据
     * 由于太快，会出现剪切板资源抢占的情况，故sleep50ms
     * 经测试
     * 执行速度平均在  190-220ms(未减50ms)
     * <p>
     * 缺点：高速率下，无法连续点击次数超过3-5次
     * <具体速度跟电脑速度有关>
     *
     * @return
     * @throws Exception
     */
    public boolean getScreenByPrintScreen(String name) throws Exception {
        robot.keyPress(KeyEvent.VK_PRINTSCREEN);
        robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
        Image image = getImageFromClipboard();
//        BufferedImage.TYPE_INT_ARGB---bmp格式存储不了
//        TYPE_3BYTE_BGR---bmp格式，png格式都可存储
//        TYPE_INT_RGB---bmp格式，png格式都可存储
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        File file = new File(name);
        return ImageIO.write(bufferedImage, "bmp", file);
    }

    /**
     * 从剪切板获得图片。
     */
    private Image getImageFromClipboard() throws Exception {
        Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
        Thread.sleep(150);
        Transferable cc = sysc.getContents(null);
        if (cc == null)
            return null;
        else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            return (Image) cc.getTransferData(DataFlavor.imageFlavor);
        }
        return null;
    }


}
