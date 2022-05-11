package com.hbs.auto;

import com.hbs.auto.utils.OpenCvUtils;

/**
 * Created by zun.wei on 2022/5/9.
 */
public class AutoDesktopApplication {


    public static void main(String[] args) {
        String cfg = OpenCvUtils.filePath + "/" + "auto.xml";
        if (args.length > 1) {
            cfg = args[0];
        }
        AutoDesktopCfgHandler.handle(cfg);
    }

}
