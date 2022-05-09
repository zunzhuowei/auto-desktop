package com.hbs.auto.utils;

import com.alibaba.fastjson.JSON;
import com.hbs.auto.enties.AutoDesktopConf;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by zun.wei on 2022/5/9.
 */
public class AutoDesktopCfgUtils {


    public static List<AutoDesktopConf> getCfg(String cfgFileName) {
        ClassPathResource classPathResource = new ClassPathResource(cfgFileName);
        try (InputStream inputStream = classPathResource.getInputStream()) {
            final String string = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return JSON.parseArray(string, AutoDesktopConf.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<AutoDesktopConf> getCfg() {
        return getCfg("auto.json");
    }
}
