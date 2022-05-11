package com.hbs.auto


import com.hbs.auto.constants.ClickType
import com.hbs.auto.enties.MouseEvent
import org.apache.commons.lang3.StringUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.awt.event.KeyEvent
import java.util.stream.Collectors

/**
 * Created by zun.wei on 2022/5/11.
 *
 */
class AutoDesktopCfgHandler {

    static void handle(String cfgFileName) {
        try (FileInputStream inputStream = new FileInputStream(new File(cfgFileName))) {
            Document document = Jsoup.parse(inputStream, "UTF-8", "");
            Elements rootTasks = document.getElementsByTag("tasks");
            String loop = rootTasks.attr("loop")
            Long loopTimes = rootTasks.attr("times") ? rootTasks.attr("times") as Long : 1L
            do {
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

                        // 延迟
                        autoRobot.delay(beforeDelay as Integer)
                        String afterDelay = action.attr("afterDelay")

                        // 组合键
                        if (actionType == "keysGroup") {
                            Elements keys = action.getElementsByTag("key")
                            if (Objects.isNull(keys)) {
                                continue
                            }

                            List<Integer> collect = keys.parallelStream()
                                    .filter({ key ->
                                        def text = key.text()
                                        if (StringUtils.isEmpty(text)) {
                                            false
                                        }
                                        true
                                    }).map({ key ->
                                def text = key.text()
                                getKeyEvent(text)
                            }).collect(Collectors.toList())
                            int[] array = collect.toArray(new int[0])
                            autoRobot.keysGroup(array)
                        }
                        // 普通按键
                        if (actionType == "keys") {
                            Elements keys = action.getElementsByTag("key")
                            if (Objects.isNull(keys)) {
                                continue
                            }

                            List<Integer> collect = keys.parallelStream()
                                    .filter({ key ->
                                        def text = key.text()
                                        if (StringUtils.isEmpty(text)) {
                                            false
                                        }
                                        true
                                    }).map({ key ->
                                def text = key.text()
                                getKeyEvent(text)
                            }).collect(Collectors.toList())
                            int[] array = collect.toArray(new int[0])
                            autoRobot.keys(array)
                        }
                        // 输入文本
                        if (actionType == "write") {
                            def text = action.text()
                            autoRobot.write(text)
                        }
                        // 鼠标单击
                        if (actionType == "click") {
                            Elements type = action.getElementsByTag("type")
                            Elements times = action.getElementsByTag("times")
                            Elements position = action.getElementsByTag("position")
                            Elements awaitTime = action.getElementsByTag("awaitTime")
                            String typeText = type.text()
                            String timesText = times.text()
                            String positionText = position.text()
                            String awaitTimeText = awaitTime.text()

                            Integer times2 = StringUtils.isBlank(timesText) ? 1 : timesText as Integer

                            ClickType clickType = "1" == typeText ? ClickType.LEFT : "2" == typeText ? ClickType.MIDDLE : ClickType.RIGHT
                            boolean b = StringUtils.startsWithAny(positionText, "(", "（", "[", "【")
                                    && StringUtils.endsWithAny(positionText, ")", "）", "]", "】")
                            MouseEvent mouseEvent = null;
                            if (b) {
                                String xyStr = StringUtils.replaceEach(positionText,
                                        ["(", "（", "[", "【", ")", "）", "]", "】"] as String[],
                                        ["", "", "", "", "", "", "", ""] as String[])
                                String[] xys = xyStr.split(",")
                                mouseEvent = MouseEvent.of(xys[0] as Integer, xys[1] as Integer)
                            } else {
                                boolean b1 = StringUtils.isNotBlank(awaitTimeText)
                                if (b1) {
                                    mouseEvent = MouseEvent.of(positionText, awaitTimeText as Integer)
                                } else {
                                    mouseEvent = MouseEvent.of(positionText)
                                }
                            }
                            autoRobot.click(clickType, mouseEvent, times2)
                        }

                        // 延迟
                        if (StringUtils.isBlank(afterDelay)) {
                            afterDelay = 0;
                        }
                        autoRobot.delay(afterDelay as Integer)
                    }
                }
                loopTimes--
            } while (loop == "true" && loopTimes > 0)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static int getKeyEvent(String key) {
        if (StringUtils.isBlank(key) && StringUtils.isNotEmpty(key)) {
            return KeyEvent.VK_SPACE
        }
        def trim = key.trim()
        return KeyEvent."VK_${trim.toUpperCase()}"
    }

}
