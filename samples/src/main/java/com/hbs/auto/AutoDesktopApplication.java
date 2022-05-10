package com.hbs.auto;

import com.hbs.auto.constants.ClickType;
import com.hbs.auto.enties.AutoDesktopConf;
import com.hbs.auto.enties.MouseEvent;
import com.hbs.auto.utils.AutoDesktopCfgUtils;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Created by zun.wei on 2022/5/9.
 */
public class AutoDesktopApplication {


    public static void main(String[] args) {
        final List<AutoDesktopConf> cfg = AutoDesktopCfgUtils.getCfg();
        AutoRobotHandler.handle(cfg);

        AutoRobot.create()
                .keysGroup(KeyEvent.VK_WINDOWS, KeyEvent.VK_D)
                .delay(100)
                .click(ClickType.RIGHT, MouseEvent.of("E:\\java\\IdeaProjects\\auto-desktop\\20220509210444.png", 5000), 1);

    }

}
