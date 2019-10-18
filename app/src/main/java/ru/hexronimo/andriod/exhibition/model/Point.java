package ru.hexronimo.andriod.exhibition.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Point implements Serializable {
    float marginX;
    float marginY;
    PointIcon pointIcon;
    Content content;


    public Point(float x, float y){
        this.marginX = x;
        this.marginY = y;
    }

    public Point(float x, float y, Content content){
        this.marginX = x;
        this.marginY = y;
        this.content = content;
        if (content.getClass().isInstance(SimpleTextContent.class)) {
            pointIcon = PointIcon.TEXT;
        }
    }

    public void loadContent() {
    //TODO lazy loading on request
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
