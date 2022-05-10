package com.hbs.auto;

import com.hbs.auto.constants.ClickType;
import com.hbs.auto.enties.MouseEvent;
import com.hbs.auto.utils.OpenCvUtils;

import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

/**
 * Created by zun.wei on 2022/5/9.
 */
public class AutoDesktopTest {

    public static void main(String[] args) throws InterruptedException {
        AutoRobot.create()
                .keysGroup(KeyEvent.VK_WINDOWS, KeyEvent.VK_D)
                .delay(100)
                .click(ClickType.RIGHT, MouseEvent.of(OpenCvUtils.filePath + "/testfiles/20220509210444.png", 5000), 1);

//        for (int i = 0; i < 5; i++) {
//            AutoRobot.create()
//                    .keysGroup(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_W)
//                    .click(ClickType.LEFT, MouseEvent.of(120, 35))
//                    .delay(500)
//                    .keysGroup(KeyEvent.VK_CONTROL,KeyEvent.VK_A)
//                    .delay(500)
//                    .keys(KeyEvent.VK_BACK_SPACE)
//                    .delay(500)
//                    .write("Zun")
//                    .delay(500)
//                    .keys(KeyEvent.VK_ENTER)
//                    .delay(500)
//                    .write("你好呀" + i)
//                    .delay(500)
//                    .keysGroup(KeyEvent.VK_CONTROL, KeyEvent.VK_ENTER)
//                    .delay(500)
//                    .keysGroup(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_W);
//            TimeUnit.SECONDS.sleep(1);
//        }
    }

}