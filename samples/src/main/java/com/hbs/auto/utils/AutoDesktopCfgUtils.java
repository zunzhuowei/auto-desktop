package com.hbs.auto.utils;

import com.hbs.auto.enties.AutoDesktopConf;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
            final Document document = Jsoup.parse(inputStream, "UTF-8", "");

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<AutoDesktopConf> getCfg() {
        return getCfg("auto.xml");
    }
}
