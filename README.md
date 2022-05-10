## AUTO-DESKTOP
This is an automated desktop tool library


https://github.com/bytedeco/javacpp-presets

## Use Samples
* Position according to coordinates
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