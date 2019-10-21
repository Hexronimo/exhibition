package ru.hexronimo.andriod.exhibition.model;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scene implements Serializable {

    private int id;
    private String imagePath;
    private Integer left;
    private Integer right;
    private String info;
    private String title;
    private String adminTitle;
    private String adminDesc;


    private List<Point> points;

    public Scene(String image, String title, String adminTitle, String adminDesc) {
        points = new ArrayList<>();
        if(image != null) {
            this.imagePath = image;
        }
        this.title = title;
        if (adminTitle != null && adminTitle.trim().length() > 0) {
            this.adminTitle = adminTitle;
        } else {
            this.adminTitle = title;
        }
        this.adminDesc = adminDesc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getRight() {
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
