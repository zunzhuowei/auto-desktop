package com.hbs.auto.enties;

import com.hbs.auto.constants.ClickPosition;

import java.awt.*;

/**
 * Created by zun.wei on 2022/5/9.
 */
public class MouseEvent {

    private MouseEvent() {
    }

    /**
     * x坐标; y坐标; time 长摁鼠标的时间长度; awaitTime 等待要点击的目标图片出现的时间毫秒数
     */
    public int x, y, time = 10, awaitTime = 0;
    /**
     * 要点击的图片所在位置，如：D://tem/aa.bmp
     */
    public String picPath;
    /**
     * 点击位置
     */
    public ClickPosition clickPosition = ClickPosition.POINT;

    public static MouseEvent of(int x, int y) {
        final MouseEvent mouseEvent = new MouseEvent();
        mouseEvent.x = x;
        mouseEvent.y = y;
        return mouseEvent;
    }

    public static MouseEvent of(Point point) {
        return MouseEvent.of(point.x, point.y);
    }

    public static MouseEvent of(int x, int y, int time) {
        final MouseEvent of = MouseEvent.of(x, y);
        of.time = time;
        return of;
    }

    /**
     * @param time 点击次数
     * @param picPath 要点击的图片所在位置，如：D://tem/aa.bmp ；不能包含中文字符
     * @return
     */
    public static MouseEvent of(int time, String picPath) {
        final MouseEvent of = MouseEvent.of(0, 0, time);
        of.picPath = picPath;
        of.clickPosition = ClickPosition.IMAGE;
        return of;
    }

    /**
     *
     * @param time 点击次数
     * @param picPath 要点击的图片所在位置，如：D://tem/aa.bmp ；不能包含中文字符
     * @param awaitTime 等待时间毫秒
     * @return
     */
    public static MouseEvent of(int time, String picPath, int awaitTime) {
        final MouseEvent of = MouseEvent.of(time, picPath);
        of.awaitTime = awaitTime;
        return of;
    }

    /**
     *
     * @param picPath 要点击的图片所在位置，如：D://tem/aa.bmp ；不能包含中文字符
     */
    public static MouseEvent of(String picPath) {
        final MouseEvent of = MouseEvent.of(0, 0);
        of.picPath = picPath;
        of.clickPosition = ClickPosition.IMAGE;
        return of;
    }

    /**
     *
     * @param picPath 要点击的图片所在位置，如：D://tem/aa.bmp ；不能包含中文字符
     * @param awaitTime 等待时间毫秒
     * @return
     */
    public static MouseEvent of(String picPath, int awaitTime) {
        final MouseEvent of = MouseEvent.of(picPath);
        of.awaitTime = awaitTime;
        return of;
    }

}
