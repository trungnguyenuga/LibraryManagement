package com.aehanoidz123.librarymanagement.Services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenerateQRCodeService {
    public static void generateQRCode(String studentId, String studentName, String studentPhone, String studentEmail) throws IOException, WriterException {
        Map<String, String> studentInfo = new HashMap<String, String>();
        studentInfo.put("studentId", studentId);
        studentInfo.put("studentName", studentName);
        studentInfo.put("studentPhone", studentPhone);
        studentInfo.put("studentEmail", studentEmail);
        Gson gson = new Gson();
        String jsonString = gson.toJson(studentInfo);
        String path = "qrcodes" + File.separator + "qr_code_of_" + studentId + ".png";
        BitMatrix bitMatrix = new MultiFormatWriter().encode(jsonString,BarcodeFormat.QR_CODE, 500, 500);
        MatrixToImageWriter.writeToPath(bitMatrix, "png", new File(path).toPath());
    }
}
