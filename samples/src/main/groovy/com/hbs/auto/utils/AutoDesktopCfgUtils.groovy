package com.hbs.auto.utils

import com.hbs.auto.AutoRobot
import com.hbs.auto.enties.AutoDesktopConf
import org.apache.commons.lang3.StringUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.core.io.ClassPathResource

import java.awt.Event
import java.awt.event.KeyEvent
import java.lang.reflect.Field
import java.util.stream.Collectors

/**
 * Created by zun.wei on 2022/5/11.
 *
 */
class AutoDesktopCfgUtils {

    static List<AutoDesktopConf> getCfg(String cfgFileName) {
        ClassPathResource classPathResource = new ClassPathResource(cfgFileName);
        try (InputStream inputStream = classPathResource.getInputStream()) {
            Document document = Jsoup.parse(inputStream, "UTF-8", "");
            Elements tasks = document.getElementsByTag("task");
            for (Element task : tasks) {
                String enable = task.attr("enable")
                if (!StringUtils.equals(enable, "true")) {
                    continue;
                }
                def autoRobot = AutoRobot.create()

                Elements actions = task.getElementsByTag("action")
                for (Element action : actions) {
                    def actionType = action.attr("type")
                    def beforeDelay = action.attr("beforeDelay")
                    if (StringUtils.isBlank(beforeDelay)) {
                        beforeDelay = 0;
                    }
                    autoRobot.delay(beforeDelay as Integer)
                    def afterDelay = action.attr("afterDelay")

                    // 组合键
                    if (actionType == "keysGroup") {
                        Elements keys = action.getElementsByTag("key")
                        if (Objects.isNull(keys)) {
                            continue
                        }

                        List<Integer> collect = keys.parallelStream()
                                .filter({ key ->
                                    def text = key.text()
                                    if (StringUtils.isBlank(text) || StringUtils.isBlank(text.trim())) {
                                        false
                                    }
                                    true
                                }).map({ key ->
                            def text = key.text()
                            getKeyEvent(text)
                        }).collect(Collectors.toList())
                        int[] array = collect.toArray(new int[0])
                        autoRobot.keysGroup(array)

                        if (StringUtils.isBlank(afterDelay)) {
                            afterDelay = 0;
                        }
                        autoRobot.delay(afterDelay as Integer)
                    }
                    // 普通按键

                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static List<AutoDesktopConf> getCfg() {
        return getCfg("auto.xml");
    }


    private static int getKeyEvent(String key) {
        def trim = key.trim()
        KeyEvent.VK_CONTROL
        return KeyEvent."VK_${trim.toUpperCase()}"
    }

}
