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
    private String id;

    private void generateDemo () {
        if (exhibition.size() == 0) {
            Scene demoScene = DemoScene.createDemo();
            this.addScene(demoScene);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int addScene(Scene scene) {
        Random rand = new Random();
        int n;
        do {
            n = rand.nextInt(20000);
        } while(exhibition.containsKey(n));
        scene.setId(n);
        exhibition.put(n, scene);
        return n;
    }

    public Map<Integer, Scene> getScenes() {
        return exhibition;
    }

    // finding a scene from which exhibition starts
    // (it will be the scene which have no left link to other scene, but have only right)
    public Scene getLeft(){
        //TODO add scene.pointsCount() != 0 to if
        Scene ifNoRightSceneFound = null;
        for (Scene scene: exhibition.values()){
            if (ifNoRightSceneFound == null) ifNoRightSceneFound = scene;
            if (scene.getLeft() == -1 && scene.getRight() != -1) {
                return scene;
            }
        }
        return ifNoRightSceneFound;
    }

}
