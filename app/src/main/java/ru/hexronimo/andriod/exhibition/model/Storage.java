package ru.hexronimo.andriod.exhibition.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;


public class Storage {

    public String writeContentImage(Context c, Uri uri, boolean withTransparency) {
        if (isExternalWritable()) {
            StringBuilder sb = new StringBuilder("/exhibitionApp/contents/cont_");
            sb.append(generateRandomName());
            sb.append(withTransparency?".png":".jpg");

            File outPutFile = new File(c.getExternalFilesDir(null) + sb.toString());
            outPutFile.getParentFile().mkdirs();
            try {
                outPutFile.createNewFile();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(c.getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (withTransparency) {
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                }
                FileOutputStream fileOutputStream = new FileOutputStream(outPutFile);
                fileOutputStream.write(stream.toByteArray());
                fileOutputStream.close();
                return outPutFile.getAbsolutePath();
            } catch (IOException e) {
                // Unable to create file, likely because external storage is
                // not currently mounted.
                e.printStackTrace();
            }
        }
        return null;
    }

    public void deleteExternalStoragePrivateFile(Context c, String fileName) {
        File file = new File(c.getExternalFilesDir(null), fileName);
        if (file.exists() && isExternalWritable()) {
            file.delete();
        }
    }

    private String generateRandomName(){
        final String alphabet = "0123456789abcdefgjklmnopqstvuwxyz";
        final int N = alphabet.length();

        Random r = new Random();
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 10; i++) {
            sb.append(alphabet.charAt(r.nextInt(N)));
        }
        return sb.toString();
    }

    public boolean hasExternalStoragePrivateFile(Context c, String fileName) {
        // Get path for the file on external storage.  If external
        // storage is not currently mounted this will fail.
        File file = new File(c.getExternalFilesDir(null), fileName);
        return file.exists();
    }

    public static boolean isExternalWritable() {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return false;
        }
        return true;
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
