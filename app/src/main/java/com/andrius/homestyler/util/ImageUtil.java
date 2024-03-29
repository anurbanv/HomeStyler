package com.andrius.homestyler.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ImageUtil {

    public static Bitmap getBitmap(String imageBase64) {
        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static String getModelStringBase64(File file) {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static File writeToFile(String base64) {
        try {
            byte[] data = Base64.decode(base64, Base64.DEFAULT);
            File file = new File(Environment.getExternalStorageDirectory() + "/temp.sfb");
            FileOutputStream out = new FileOutputStream(file);
            out.write(data);
            out.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
