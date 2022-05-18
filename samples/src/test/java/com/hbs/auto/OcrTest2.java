package com.hbs.auto;

import com.hbs.auto.utils.OpenCvUtils;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.opencv.opencv_java;
import org.bytedeco.tesseract.TessBaseAPI;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

/**
 * 识别图片中的文字
 * Created by zun.wei on 2022/5/10.
 */
public class OcrTest2 {


    public static void main(String[] args) {
        Loader.load(opencv_java.class);
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        //if (api.Init("D:\\chrome Downloads", "eng") != 0) {
        if (api.Init(OpenCvUtils.filePath + "/extralib/tess", "chi_sim") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        String s = "image-%s.jpeg";
        for (int i = 0; i < 687; i++) {
            int idx = (i + 1);
            String rep = idx < 10 ? "00" + idx :
                    idx < 100 ? "0" + idx :
                            idx < 1000 ? "" + idx : "idx";
            String fileName = String.format(s, rep);
            PIX image = pixRead("H:\\disk\\test\\" + fileName);
            api.SetImage(image);
            // Get OCR result
            outText = api.GetUTF8Text();
            System.out.println("OCR output:\n" + outText.getString());

            // Destroy used object and release memory
            outText.deallocate();
            pixDestroy(image);
        }

        api.End();
    }

}
