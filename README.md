## AUTO-DESKTOP
This is an automated desktop tool library


https://github.com/bytedeco/javacpp-presets

## Sample Api usage
* Position according to coordinates
> com.hbs.auto.AutoDesktopTest
```html
        for (int i = 0; i < 5; i++) {
            AutoRobot.create()
                    .keysGroup(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_W)
                    .click(ClickType.LEFT, MouseEvent.of(120, 35))
                    .delay(500)
                    .keysGroup(KeyEvent.VK_CONTROL,KeyEvent.VK_A)
                    .delay(500)
                    .keys(KeyEvent.VK_BACK_SPACE)
                    .delay(500)
                    .write("Zun")
                    .delay(500)
                    .keys(KeyEvent.VK_ENTER)
                    .delay(500)
                    .write("你好呀" + i)
                    .delay(500)
                    .keysGroup(KeyEvent.VK_CONTROL, KeyEvent.VK_ENTER)
                    .delay(500)
                    .keysGroup(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_W);
            TimeUnit.SECONDS.sleep(1);
        }
```

* Position according to the picture
> com.hbs.auto.AutoDesktopTest2
```html
 AutoRobot.create()
                .keysGroup(KeyEvent.VK_WINDOWS, KeyEvent.VK_D)
                .delay(1000)
                .click(ClickType.LEFT, MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\gj.png"), 2)
                .delay(2000)
                .click(ClickType.LEFT, MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\tj.png"))
                .delay(500)
                .click(ClickType.LEFT,MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\hd.png",60000))
                .delay(1000)
                .click(ClickType.LEFT,MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\qllj.png"))
                .delay(1000)
                .click(ClickType.LEFT,MouseEvent.of("C:\\Users\\hui\\Desktop\\temp\\smlj.png"));
```

## Use the configuration example
> auto.xml
```html
<tasks loop="true" times="5">
    <task id="1" enable="true">
        <action type="keysGroup" beforeDelay="" afterDelay="500">
            <key>windows</key>
            <key>d</key>
        </action>
        <action type="click" beforeDelay="" afterDelay="2000">
            <type>1</type>
            <times>2</times>
            <position>(100,30)</position>
        </action>
        <action type="click" beforeDelay="" afterDelay="500">
            <type>1</type>
            <times>1</times>
            <awaitTime>10000</awaitTime>
            <position>C:\\Users\\hui\\Desktop\\temp\\wx_search.png</position>
        </action>
        <action type="keysGroup" beforeDelay="" afterDelay="1000">
            <key>CONTROL</key>
            <key>a</key>
        </action>
        <action type="keys" beforeDelay="" afterDelay="500">
            <key>back_space</key>
        </action>
        <action type="keys" beforeDelay="" afterDelay="500">
            <key>a</key>
            <key>SPACE</key>
            <key>b</key>
            <key>SPACE</key>
            <key>c</key>
            <key>SPACE</key>
        </action>
        <action type="write" beforeDelay="" afterDelay="500">
            <!-- 读取 excel 优先，如果没有 excel 标签，则读取 action 的文本-->
            <excel>
                <!-- excel 所在目录 -->
                <path>C:\Users\hui\Desktop\ads_master.xlsx</path>
                <!-- excel 第几个工作表 -->
                <sheetNum>1</sheetNum>
                <!-- excel 读取哪一列 -->
                <columnNum>2</columnNum>
                <!-- excel 读取起始行，如：从第一行开始，则值为1 -->
                <startRowNum>1</startRowNum>
                <!-- 读取步进数，如：起始行2，步进为2，则读取的行数为,（2,4,6...）；
                起始行2，步进为1，则读取的行数为,（2,3,4...）；
                起始行2，步进为0，则读取的行数为,（2,2,2...）-->
                <stepOverNum>1</stepOverNum>
            </excel>
        </action>
        <action type="write" beforeDelay="" afterDelay="500">
             读取 excel 优先，如果没有 excel 标签，则读取 action 的文本
        </action>
    </task>
</tasks>

        <!--
         keysGroup -> 多个按键都按下后，再弹出；
         keys -> 按键按下后即弹出；
         click -> 鼠标点击；type(1,2,3) -> 左，中，右键；times 点击次数；position 点击坐标（x,y）/图片所在地址目录;awaitTime 等待图片出现的时间（毫秒）
         write -> 输入的文本
         beforeDelay -> 延时多久才开始执行这个动作（单位毫秒）
         afterDelay ->  执行这个动作之后延时多久再继续（单位毫秒）

         win -> WINDOWS
         删除 -> BACK_SPACE
         回车 -> ENTER
         空格 -> SPACE
         ctrl -> CONTROL
         -->
```