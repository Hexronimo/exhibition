package ru.hexronimo.andriod.exhibition.model;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

// it's not static for future scaling reason: few exhibition may be made and then selected in one app
public class Exhibition implements Serializable {
    private Map<Integer, Scene> exhibition = new HashMap<>();
    private String name;
    private static String savePath;
    private int id;

    //Test
    public Exhibition(String name){
        //TODO if it deserialize and got nothing it generates demo
        this.name = name;
        Properties property = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/java/ru/hexronimo/android/exhibition/model/config.properties");
            property.load(fis);
            savePath = property.getProperty("exhibitionsavepath");
            fis.close();
        } catch(IOException e){}

        this.generateDemo();
    }

    public void save(Context context){
        try {
            FileOutputStream fos = context.openFileOutput(savePath + "/exhibition/", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this);
            fos.close();
            out.close();
        } catch (IOException e){}
    }

    private void generateDemo () {
        if (exhibition.size() == 0) {
            Scene demoScene = DemoScene.createDemo();
            this.addScene(demoScene);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addScene(Scene scene) {
        int id;
        Random random = new Random();
        do {
           id = random.nextInt(100);
        } while(exhibition.containsKey(id));
        scene.setId(id);
        exhibition.put(id, scene);
    }

    public Map<Integer, Scene> getExhibition() {
        return exhibition;
    }

    public void setExhibition(Map<Integer, Scene> exhibition) {
        this.exhibition = exhibition;
    }

    // finding a scene from which exhibition starts
    // (it will be the scene which have no left link to other scene, but have only right)
    public Scene getLeft(){
        //TODO add scene.pointsCount() != 0 to if
        Scene ifNoRightSceneFound = null;
        for (Scene scene: exhibition.values()){
            if (ifNoRightSceneFound == null) ifNoRightSceneFound = scene;
            if (scene.getLeft() == null && scene.getRight() != null) {
                return scene;
            }
        }
        return ifNoRightSceneFound;
    }

}
