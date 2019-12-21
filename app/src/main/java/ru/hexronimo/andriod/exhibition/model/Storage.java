package ru.hexronimo.andriod.exhibition.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;


public class Storage {
    private static Storage instance;
    private static String pathExhibition;
    private static String pathScenes;
    private static String pathContents;
    private static String mainPath;
    private static Context context;

    private Storage(Context context){
        //load dir paths from properties
        Properties property = new Properties();
        InputStream fis;
        try {
            AssetManager assetManager = context.getAssets();
            fis = assetManager.open("config.properties");
            property.load(fis);
            mainPath = property.getProperty("mainpath");
            System.out.println("Main path is: " + mainPath);
            fis.close();
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("Can't read files in App folder");
        }
        pathExhibition = "exhibition";
        pathScenes = "scenes";
        pathContents = "contents";
    }

    // make it singleton
    public static Storage getInstance(Context c) {
        if (instance == null) {
            instance = new Storage(c);
            context = c;
        }
        return instance;
    }

    public static Map<String, String[]> getAllExhibitions() {

        Map<String, String[]> map = new HashMap<>();File dir = new File(context.getExternalFilesDir(null), instance.mainPath + "/" + instance.pathExhibition );
        System.out.println(dir.toString());
        List<File> files = new ArrayList<>();
        try {
            files.addAll(Arrays.asList(dir.listFiles()));
        } catch(NullPointerException e){}

        System.out.println("There are " + files.size() + " files (probably exhibitions). Reading start..." );

            for (File f: files) {
                FileInputStream fis = null;
                ObjectInputStream ois = null;
                try {
                    fis = new FileInputStream(f);
                    ois = new ObjectInputStream(fis);
                    try {
                        Exhibition e = (Exhibition) ois.readObject();
                        String[] params = new String[3];
                        params[0] = e.getName();
                        if (e.getScenes().size() > 0) params[1] = e.getLeft().getImagePath().toString();
                        if (e.getScenes() != null) params[2] = e.getScenes().size() + "";
                        else params[2] = "0";

                        map.put(e.getId(), params);
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error when reading object.");
                        continue;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error when reading file " + f.getName());
                    continue;
                } finally {
                    if (fis != null) {
                        try {
                            ois.close();
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return map;
    }

    // save Exhibition object
    public static String saveExhibition(Exhibition exhibition){
        File file;
        String id = null;
        if (null == exhibition.getId()) {
            do {
                id = generateRandomName();
                file = new File(context.getExternalFilesDir(null), instance.mainPath + "/" + instance.pathExhibition + "/exh_" + id + ".ser");
            } while (file.exists());
            exhibition.setId(id);
            file.getParentFile().mkdirs();
        } else {
            id = exhibition.getId();
            file = new File(context.getExternalFilesDir(null), instance.mainPath + "/" + instance.pathExhibition + "/exh_" + exhibition.getId() + ".ser");
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(exhibition);
            objectOutputStream.close();
            fos.close();
        } catch(IOException e){}

        return id;
    }

    public static Exhibition getExhibition(String id) {
        Exhibition exhibition = null;
        File file = new File (context.getExternalFilesDir(null), instance.mainPath + "/" + instance.pathExhibition + "/exh_" + id + ".ser" );
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            try {
                exhibition = (Exhibition) ois.readObject();
            } catch(ClassNotFoundException e){}

        } catch (IOException e) {

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return exhibition;
    }

    public static void deleteExhibition(String id){
        File file = new File (context.getExternalFilesDir(null), instance.mainPath + "/" + instance.pathExhibition + "/exh_" + id + ".ser" );
        if (file.exists()) file.delete();
    }


    //save Point with content
    public void savePoint(Point point, String exhId, String sceneId, Context context){
        File file;
        if (null == point.getId()) {
            String id;
            do {
                id = generateRandomName();
                file = new File(context.getExternalFilesDir(null), mainPath + "/" + pathExhibition + "/exh_" + exhId + "/scenes/sc_" + sceneId + "/" + id + ".ser");
            } while (file.exists());
            point.setId(id);
            file.getParentFile().mkdirs();
        } else {
            file = new File(context.getExternalFilesDir(null), mainPath + "/" + pathExhibition + "/exh_" + exhId + "/scenes/sc_" + sceneId +"/" + point.getId() + ".ser");
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(point);
            objectOutputStream.close();
            fos.close();
        } catch(IOException e){}
    }

    // load Exhibition object

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

    private static  String generateRandomName(){
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
