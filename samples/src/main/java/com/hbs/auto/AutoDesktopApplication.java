package com.hbs.auto;

import com.hbs.auto.enties.AutoDesktopConf;
import com.hbs.auto.utils.AutoDesktopCfgUtils;

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
