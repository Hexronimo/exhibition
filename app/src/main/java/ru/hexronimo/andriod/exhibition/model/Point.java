package ru.hexronimo.andriod.exhibition.model;

public class Point {
    float marginX;
    float marginY;
    int contentId;
    PointIcon pointIcon;
    Content content;


    public Point(float x, float y){
        this.marginX = x;
        this.marginY = y;
    }

    public void loadContent() {

    }

    public Content getContent(){
        return this.content;
    }

    public float getMarginX() {
        return marginX;
    }

    public void setMarginX(float marginX) {
        this.marginX = marginX;
    }

    public float getMarginY() {
        return marginY;
    }

    public void setMarginY(float marginY) {
        this.marginY = marginY;
    }

}
