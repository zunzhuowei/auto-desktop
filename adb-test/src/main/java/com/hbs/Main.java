package com.hbs;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zun.wei on 2024/12/24.
 */
public class Main {

    // 执行ADB命令并返回结果
    public static String adbCommandResultStr(String command) throws IOException {
        final byte[] result = adbCommand(command);
        return new String(result, StandardCharsets.UTF_8);
    }

    public static byte[] adbCommand(String command) throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ) {
            IOUtils.copy(reader, outputStream, StandardCharsets.UTF_8);
            // 等待进程结束并获取退出状态
            int exitCode = process.waitFor();
            return outputStream.toByteArray();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (process != null) {
                process.destroyForcibly();
            }
        }
    }

    // 列出所有连接的设备
    public static String listDevices() throws IOException {
        return adbCommandResultStr("adb devices");
    }

    // 使用 adb shell screencap 截图,图片保留在手机中
    public static String screenshot(String deviceId, String fileName) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " shell screencap -p /sdcard/" + fileName + ".png");
    }

    // 使用 adb shell screencap 截图并保存到本地文件, 然后删除手机上的文件
    public static void screenshot2LocalAfterDeletePhoneSide(String deviceId, String fileName, String localPath) throws IOException {
        screenshot(deviceId, fileName);
        adbCommandResultStr("adb -s " + deviceId + " pull /sdcard/" + fileName + ".png " + localPath + fileName + ".png");
        new Thread(() -> {
            try {
                adbCommandResultStr("adb -s " + deviceId + " shell rm /sdcard/" + fileName + ".png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    // 使用 adb exec-out 截图并保存到本地文件
    // 注意：得到的文件打不开（Windows PowerShell 重定向编码问题，用CMD提示符可以打开)
    // PowerShell中使用$process = Start-Process -FilePath "adb" -ArgumentList "exec-out screencap -p" -RedirectStandardOutput "aa.png" -NoNewWindow -Wait
    // 代替 adb shell screencap -p > aa.png
    public static void screenshot2Local(String deviceId, String localFilePath) throws IOException {
        // 定义命令和参数
        String[] command = {
                "adb",
                "-s",
                deviceId,
                "exec-out",
                "screencap",
                "-p"
        };
        // 定义输出文件路径
        File outputFile = new File(localFilePath + ".png");
        // 创建 ProcessBuilder
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectOutput(outputFile);
        try {
            // 启动进程
            Process process = processBuilder.start();
            // 等待进程完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("截图成功，文件保存为: " + outputFile.getAbsolutePath());
            } else {
                System.err.println("截图失败，退出码: " + exitCode);
            }
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 获取设备列表中第index台设备的设备ID
    public static String getDeviceIdByDevicesList(String devices, int index) {
        // 解析设备列表，假设设备列表格式为：
        // List of devices attached
        // 127.0.0.1:62001	device
        // emulator-5554	device
        String[] lines = devices.split("\n");
        if (lines.length > 1) {
            String firstDeviceLine = lines[index + 1].trim(); // 获取第index台设备的行
            String deviceId = firstDeviceLine.split("\\t")[0]; // 提取设备ID
            return deviceId;
        } else {
            throw new RuntimeException("没有找到连接的设备");
        }
    }

    // adb shell input tap <x> <y>
    // 点击屏幕
    //单位是像素
    //<x>：表示从屏幕左边缘到点击位置的水平距离（像素）。
    //<y>：表示从屏幕顶部到点击位置的垂直距离（像素）。
    public static String inputTap(String deviceId, int x, int y) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " shell input tap " + x + " " + y);
    }

    // adb shell input swipe x1 y1 x2 y2 [duration(ms)]
    // 滑动屏幕
    public static String inputSwipe(String deviceId, int x1, int y1, int x2, int y2, long duration) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " shell input swipe " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + duration);
    }

    // adb shell input touchscreen swipe <x1> <y1> <x2> <y2>
    // 拖拽
    public static String inputTouchScreenSwipe(String deviceId, int x1, int y1, int x2, int y2) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " shell input touchscreen swipe " + x1 + " " + y1 + " " + x2 + " " + y2);
    }

    //adb push <local_file> <remote_path>
    public static String push(String deviceId, String localFileName, String remotePath) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " push " + localFileName + " " + remotePath);
    }

    //adb install <path_to_apk>
    public static String install(String deviceId, String apkPath) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " install " + apkPath);
    }

    //adb uninstall <package_name>
    public static String uninstall(String deviceId, String packageName) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " uninstall " + packageName);
    }

    //adb shell am start -n <package_name>/<package_name>.<activity_name>
    public static String startActivity(String deviceId, String packageName, String activityName) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " shell am start -n " + packageName + "." + activityName);
    }

    //adb shell  <text>
    public static String inputText(String deviceId, String text) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " shell input text " + text);
    }

    //adb shell getevent -l
    //输出所有事件
    public static void getevent() throws IOException {
        Process process = Runtime.getRuntime().exec("adb shell getevent -l");
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        ) {
            while (reader.readLine() != null) {
                System.out.println(reader.readLine());
            }
            // 等待进程结束并获取退出状态
            int exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    // adb shell getevent -l 并将事件保存到文件中
    public static void captureEvents(String deviceId, String filePath) throws IOException {
        Process process = Runtime.getRuntime().exec("adb -s " + deviceId + " shell getevent -lt");
        // 注册关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                // 确保所有进程正确终止
                process.destroyForcibly();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
                writer.flush();
                System.out.println(line);
            }
            // 等待进程结束并获取退出状态
            int exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (process != null) {
                process.destroyForcibly();
            }
        }
    }

    //adb shell input keyevent 3
    public static String inputKeyevent(String deviceId, int keyCode) throws IOException {
        return adbCommandResultStr("adb -s " + deviceId + " shell input keyevent " + keyCode);
    }

    public static String pressHome(String deviceId) throws IOException {
        return inputKeyevent(deviceId, 3);
    }

    public static String pressBack(String deviceId) throws IOException {
        return inputKeyevent(deviceId, 4);
    }

    public static String volumeUp(String deviceId) throws IOException {
        return inputKeyevent(deviceId, 24);
    }

    public static String volumeDown(String deviceId) throws IOException {
        return inputKeyevent(deviceId, 25);
    }

    public static String powerOn(String deviceId) throws IOException {
        return inputKeyevent(deviceId, 26);
    }

    public static String cameraOn(String deviceId) throws IOException {
        return inputKeyevent(deviceId, 27);
    }

    public static String clearKey(String deviceId) throws IOException {
        return inputKeyevent(deviceId, 28);
    }

    static class TouchPoint {
        int x;
        int y;

        TouchPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TouchPoint touchPoint = (TouchPoint) o;
            return x == touchPoint.x && y == touchPoint.y;
        }
    }

    static class CommandItem {
        TouchPoint startPoint;
        TouchPoint endPoint;
        //事件持续事件，结束时间-开始时间
        long duration;
        //执行事件，什么时候执行
        long execTime;

        public long getExecTime() {
            return execTime;
        }

        public void setExecTime(long execTime) {
            this.execTime = execTime;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }


        public TouchPoint getStartPoint() {
            return startPoint;
        }

        public void setStartPoint(TouchPoint startPoint) {
            this.startPoint = startPoint;
        }

        public TouchPoint getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(TouchPoint endPoint) {
            this.endPoint = endPoint;
        }

        boolean isClick() {
            return startPoint.equals(endPoint);
        }

        boolean isScroll() {
            return !startPoint.equals(endPoint);
        }
    }

    static String xRegex =
            "\\[\\s(\\d+\\.\\d+)]\\s/dev/input/event2: EV_ABS\\s+ABS_MT_POSITION_X\\s+([A-Za-z0-9]{8})\\s+";
    static String yRegex =
            "\\[\\s(\\d+\\.\\d+)]\\s/dev/input/event2: EV_ABS\\s+ABS_MT_POSITION_Y\\s+([A-Za-z0-9]{8})\\s+";
    static String downRegex2 =
            "\\[\\s(\\d+\\.\\d+)]\\s/dev/input/event2: EV_KEY\\s+BTN_TOUCH\\s+DOWN\\s+";
    static String upRegex2 =
            "\\[\\s(\\d+\\.\\d+)]\\s/dev/input/event2: EV_KEY\\s+BTN_TOUCH\\s+UP\\s+";
    static Pattern xPattern = Pattern.compile(xRegex);
    static Pattern yPattern = Pattern.compile(yRegex);
    static Pattern downRegex2Pattern = Pattern.compile(downRegex2);
    static Pattern upRegex2Pattern = Pattern.compile(upRegex2);

    public static List<CommandItem> parseEventCommands(List<String> events) {
        LinkedList<String> eventsLinkedList = new LinkedList<>(events);
        LinkedList<CommandItem> result = new LinkedList<>();
        String event = null;
        int downX = 0, downY = 0;//按下的点
        int moveX = 0, moveY = 0;//移动的点
        int status = 0;//0未按下，1已按下
        long startTime = 0L, endTime = 0L;
        boolean isClick = true;
        while ((event = eventsLinkedList.pollFirst()) != null) {
            Matcher matcherX = xPattern.matcher(event);
            if (matcherX.find()) {
                if (status == 0) {
                    downX = Integer.parseInt(matcherX.group(2), 16);
                }
                if (status == 1) {
                    moveX = Integer.parseInt(matcherX.group(2), 16);
                }
                continue;
            }
            Matcher matcherY = yPattern.matcher(event);
            if (matcherY.find()) {
                if (status == 0) {
                    downY = Integer.parseInt(matcherY.group(2), 16);
                    double timestamp = Double.parseDouble(matcherY.group(1));
                    startTime = (long) (timestamp * 1000_000);
                    status = 1;
                    continue;
                }
                //status == 1的时候
                moveY = Integer.parseInt(matcherY.group(2), 16);
                double timestamp = Double.parseDouble(matcherY.group(1));
                endTime = (long) (timestamp * 1000_000);
            }
            Matcher matcherDown = downRegex2Pattern.matcher(event);
            if (matcherDown.find()) {
                status = 1;
            }
            Matcher matcherUp = upRegex2Pattern.matcher(event);
            if (matcherUp.find()) {
                status = 0;
                CommandItem commandItem = new CommandItem();
                commandItem.setStartPoint(new TouchPoint(downX, downY));
                commandItem.setEndPoint(new TouchPoint(moveX, moveY));
                long duration = endTime - startTime;
                commandItem.setDuration(duration);
                commandItem.setExecTime(endTime);
                result.addLast(commandItem);
            }
        }
        return result;
    }

    /**
     * 播放事件
     * @param eventFilePath
     * @param deviceId
     * @throws IOException
     * @throws InterruptedException
     */
    public static void playEvents(String eventFilePath, String deviceId) throws IOException, InterruptedException {
        List<String> events = FileUtils.readLines(new File(eventFilePath), "UTF-8");
        List<CommandItem> commandItems = parseEventCommands(events);
        long delay = 0;
        for (CommandItem commandItem : commandItems) {
            if (delay != 0) {
                delay = commandItem.getExecTime() - delay;
            }
            TouchPoint startPoint = commandItem.getStartPoint();
            TouchPoint endPoint = commandItem.getEndPoint();
            long duration = commandItem.getDuration();
            boolean click = commandItem.isClick();
            boolean scroll = commandItem.isScroll();
            System.out.println("delay:" + delay + "mics,duration:" + duration + "mics");
            TimeUnit.MICROSECONDS.sleep(delay);
            if (click) {
                System.out.println("Click from (" + startPoint.x + ", " + startPoint.y + ")");
                // 点击事件
                inputTap(deviceId, startPoint.x, startPoint.y);
            } else if (scroll) {
                System.out.println("Scroll from (" + startPoint.x + ", " + startPoint.y + ") to (" + endPoint.x + ", " + endPoint.y + ")");
                // 滑动屏幕
                inputSwipe(deviceId, startPoint.x, startPoint.y, endPoint.x, endPoint.y, duration / 1000L);
            }
            delay = commandItem.getExecTime();
        }
    }

    public static void main2(String[] args) throws IOException, InterruptedException {
        // 列出所有连接的设备
        System.out.println("连接的设备列表:");
        String devices = listDevices();
        System.out.println(devices);
        // 获取设备ID
        String deviceId = getDeviceIdByDevicesList(devices, 0);
        if (StringUtils.isBlank(deviceId)) {
            throw new RuntimeException("没有连接的设备");
        }
        // 捕获事件并保存到文件
        String eventFilePath = "events.log";
        //FileUtils.delete(new File(eventFilePath));
        //captureEvents(deviceId, eventFilePath);
        System.out.println("事件已保存到 " + eventFilePath);

        //播放事件
        playEvents(eventFilePath, deviceId);
    }

    //java -jar adb-test-1.0.0.jar [0/1:0录制1播放] [index] [events.log]
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("请输入参数，如：java -jar adb-test-1.0.0.jar [0/1:0录制1播放] [index] [events.log]");
        String line = "";
        int type = 0, index = 0;
        String eventFilePath = "events.log";
        System.out.println("enter action [type] :[0/1 -> record/play]");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        line = reader.readLine().trim();
        if (StringUtils.isNotBlank(line)) type = Integer.parseInt(line);

        System.out.println("enter action [index] :[device index start with 0]");
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
        line = reader2.readLine().trim();
        if (StringUtils.isNotBlank(line)) index = Integer.parseInt(line);

        System.out.println("enter action [logfile] :[default events.log]");
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(System.in));
        line = reader3.readLine().trim();
        if (StringUtils.isNotBlank(line)) eventFilePath = line;
        // 列出所有连接的设备
        System.out.println("连接的设备列表:");
        String devices = listDevices();
        System.out.println(devices);
        // 获取设备ID
        String deviceId = getDeviceIdByDevicesList(devices, index);
        if (StringUtils.isBlank(deviceId)) {
            throw new RuntimeException("没有连接的设备");
        }
        // 捕获事件并保存到文件
        if (type == 0) {
            FileUtils.deleteQuietly(new File(eventFilePath));
            captureEvents(deviceId, eventFilePath);
            System.out.println("事件已保存到 " + eventFilePath);
        } else {
            //播放事件
            playEvents(eventFilePath, deviceId);
        }
    }


}