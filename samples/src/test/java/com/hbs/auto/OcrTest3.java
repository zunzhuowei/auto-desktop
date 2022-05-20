package com.hbs.auto;

import com.hbs.auto.utils.OpenCvUtils;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.opencv.opencv_java;
import org.bytedeco.tesseract.TessBaseAPI;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

/**
 * 识别图片中的文字
 * Created by zun.wei on 2022/5/10.
 */
public class OcrTest3 {


    /**
     * 截图识别文字
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Loader.load(opencv_java.class);
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        //if (api.Init("D:\\chrome Downloads", "eng") != 0) {
        if (api.Init(OpenCvUtils.filePath + "/extralib/tess", "chi_sim+eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        Set<String> set = new HashSet<>();
        Long idx = 0L;
        for (;;) {
            final File cut_image = AutoRobot.getImageFromClipboard("cut_image.jpeg");
            if (Objects.isNull(cut_image)) {
                TimeUnit.MILLISECONDS.sleep(500);
                continue;
            }
            try {
                //File file = Loader.cacheResource(cut_image.getAbsolutePath());
                PIX image = pixRead(cut_image.getAbsolutePath());
                api.SetImage(image);
                // Get OCR result
                outText = api.GetUTF8Text();
                System.out.println("OCR output:\n" + outText.getString());

                // Destroy used object and release memory
                outText.deallocate();
                pixDestroy(image);
                AutoRobot.setClipboard("-");
                if (++idx > Long.MAX_VALUE) {
                    break;
                }
            } finally {
                cut_image.deleteOnExit();
            }
        }

        api.End();
    }

}
