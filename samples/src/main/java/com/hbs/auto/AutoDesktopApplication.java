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

    }

}
