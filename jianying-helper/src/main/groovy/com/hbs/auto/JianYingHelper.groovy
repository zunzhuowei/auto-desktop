package com.hbs.auto

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener
import com.hbs.auto.utils.OpenCvUtils
import org.apache.commons.lang3.RegExUtils
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.leptonica.PIX
import org.bytedeco.tesseract.TessBaseAPI

import java.awt.event.KeyEvent
import java.util.concurrent.TimeUnit

import static org.bytedeco.leptonica.global.lept.pixDestroy
import static org.bytedeco.leptonica.global.lept.pixRead

/**
 * Created by zun.wei on 2023/6/19.
 *
 */
class JianYingHelper implements NativeKeyListener,
        NativeMouseInputListener,
        NativeMouseWheelListener {

    @Override
    void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        String str = nativeEvent.paramString()
        //println "str = $str"
    }

    @Override
    void nativeKeyPressed(NativeKeyEvent nativeEvent) {
    }

    @Override
    void nativeKeyReleased(NativeKeyEvent nativeEvent) {
    }

    @Override
    void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        int button = nativeEvent.button //1左，2右，3中
        int count = nativeEvent.clickCount //点击次数
        if (button == 1 && count == 2) {
            AutoRobot.create()
                    .keysGroup(KeyEvent.VK_WINDOWS, KeyEvent.VK_SHIFT, KeyEvent.VK_S);
        }
        if (button == 2 && count == 1) {
            //AutoRobot.create().keysGroup(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
            AutoRobot.create()
                    .keysGroup2(100, KeyEvent.VK_ALT, KeyEvent.VK_TAB)
        }
        if (button == 3 && count == 1) {
            //AutoRobot.create().keysGroup(KeyEvent.VK_ALT, KeyEvent.VK_TAB)
        }
    }

    @Override
    void nativeMousePressed(NativeMouseEvent nativeEvent) {

    }

    @Override
    void nativeMouseReleased(NativeMouseEvent nativeEvent) {

    }

    @Override
    void nativeMouseMoved(NativeMouseEvent nativeEvent) {
    }

    @Override
    void nativeMouseDragged(NativeMouseEvent nativeEvent) {
    }

    @Override
    void nativeMouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
    }


    static void main(String[] args) {
        GlobalScreen.registerNativeHook()
        JianYingHelper jianYingHelper = new JianYingHelper()
        GlobalScreen.addNativeKeyListener(jianYingHelper)
        GlobalScreen.addNativeMouseListener(jianYingHelper)
        GlobalScreen.addNativeMouseWheelListener(jianYingHelper)

        BytePointer outText;
        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        //if (api.Init("D:\\chrome Downloads", "eng") != 0) {
        //if (api.Init(OpenCvUtils.filePath + "/extralib/tess", "chi_sim+eng") != 0) {
        if (api.Init(OpenCvUtils.filePath  + "/extralib/tess", "chi_sim+eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        Long idx = 0L;
        for (;;) {
            File cut_image = null;
            try {
                cut_image = AutoRobot.getImageFromClipboard("cut_image.jpeg");
                if (Objects.isNull(cut_image)) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    continue;
                }
                //File file = Loader.cacheResource(cut_image.getAbsolutePath());
                PIX image = pixRead(cut_image.getAbsolutePath());
                api.SetImage(image);
                // Get OCR result
                outText = api.GetUTF8Text();
                System.out.println("OCR output:\n" + outText.getString());
                final String content = outText.getString();
                String c = RegExUtils.replaceAll(content, "[\n]", ",");
                c = c.substring(0, c.length() - 1)
                // Destroy used object and release memory
                outText.deallocate();
                pixDestroy(image);
                AutoRobot.setClipboard(c);

                if (++idx > Long.MAX_VALUE) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cut_image != null) {
                    cut_image.deleteOnExit();
                }
            }
        }
        api.End();
    }


}
