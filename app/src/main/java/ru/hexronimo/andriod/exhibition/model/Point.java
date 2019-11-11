package ru.hexronimo.andriod.exhibition.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Point implements Serializable {
    float marginX;
    float marginY;
    PointIcon pointIcon;
    private Content content;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Point(float x, float y, Content content){
        this.marginX = x;
        this.marginY = y;
        this.content = content;
        if (content.getClass().isInstance(SimpleTextContent.class)) {
            pointIcon = PointIcon.TEXT;
        }
    }

    public void setContent(Content content) {
        this.content = content;
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
