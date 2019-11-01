package ru.hexronimo.andriod.exhibition.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scene implements Serializable {

    private String id;
    private String imagePath;
    private String left;
    private String right;
    private String info;
    private String title;
    private String desc;


    private List<Point> points;

    public Scene(String image, String title, String desc) {
        points = new ArrayList<>();
        if(image != null) {
            this.imagePath = image;
        }
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public void LinkLeftRight(String leftSceneId, String rightSceneId) {
        this.left = leftSceneId;
        this.right = rightSceneId;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point){
        this.points.add(point);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
