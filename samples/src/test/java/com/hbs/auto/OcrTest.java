package com.hbs.auto;

import com.hbs.auto.utils.OpenCvUtils;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.opencv.opencv_java;
import org.bytedeco.opencv.opencv_text.OCRTesseract;
import org.bytedeco.tesseract.TessBaseAPI;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

/**
 * 识别图片中的文字
 * Created by zun.wei on 2022/5/10.
 */
public class OcrTest {


    public static void main(String[] args) {
        Loader.load(opencv_java.class);
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        //if (api.Init("D:\\chrome Downloads", "eng") != 0) {
        if (api.Init(OpenCvUtils.filePath + "/extralib/tess", "chi_sim+chi_tra+eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        PIX image = pixRead(args.length > 0 ? args[0] : "ch_ocr.png");
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        System.out.println("OCR output:\n" + outText.getString());

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
    }

}
