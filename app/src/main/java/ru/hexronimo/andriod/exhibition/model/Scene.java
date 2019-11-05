package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scene implements Serializable {

    private int id;
    private String imagePath;
    private int left;
    private int right;
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

    public void deletePoint(int index){
        points.remove(index);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public void LinkLeftRight(int leftSceneId, int rightSceneId) {
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

    public Uri getImagePath() {
        return Uri.parse(imagePath);
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath.toString();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
