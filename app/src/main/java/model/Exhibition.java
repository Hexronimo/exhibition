package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// it's not static for future scaling reason: few exhibition may be made and then selected in one app
public class Exhibition {
    private Map<Integer, Scene> exhibition = new HashMap<>();
    String name;

    //Test
    public Exhibition(String name){
        this.name = name;
        this.generateDemo();
    }

    private void generateDemo () {
        if (exhibition.size() == 0) {
            Scene demoScene = new Scene(null, "Демонстрационная сцена", "Демонстрационная сцена", "Демонстрационная сцена исчезнет как только в выставку будут добавлены реальные сцены");
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
