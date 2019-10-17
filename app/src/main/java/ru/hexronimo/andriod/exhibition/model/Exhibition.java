package ru.hexronimo.andriod.exhibition.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// it's not static for future scaling reason: few exhibition may be made and then selected in one app
public class Exhibition {
    private Map<Integer, Scene> exhibition = new HashMap<>();
    String name;

    //Test
    public Exhibition(String name){
        //TODO if it deserialize and got nothing it generates demo
        this.name = name;
        this.generateDemo();
    }

    private void generateDemo () {
        if (exhibition.size() == 0) {
            Scene demoScene = new Scene(null, "Демонстрационная сцена", "Демонстрационная сцена", "Демонстрационная сцена исчезнет как только в выставку будут добавлены реальные сцены");

            // because those sizes where first taken on Nexus 10 which sceen size is 2560x1600 and image was near 3080x1450 on it
            demoScene.addPoint(new Point(1452/3080f, 617/1450f));
            demoScene.addPoint(new Point(820/3080f, 951/1450f));
            demoScene.addPoint(new Point(2060/3080f, 969/1450f));
            demoScene.addPoint(new Point(1276/3080f, 414/1450f));
            demoScene.addPoint(new Point(2510/3080f, 618/1450f));
            demoScene.addPoint(new Point(283/3080f, 807/1450f));
            demoScene.addPoint(new Point(1291/3080f, 1053/1450f));
            exhibition.put(0, demoScene);
        }
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

    public Scene getLeft(){
        //TODO add scene.pointsCount() != 0 to if
        for (Scene scene: exhibition.values()){
            if (scene.getLeft() == null && scene.getRight() != null) {
                return scene;
            }
        }
        return exhibition.get(0);
    }

}
