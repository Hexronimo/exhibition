package ru.hexronimo.andriod.exhibition.model;

import java.io.Serializable;

public interface Content extends Serializable{

    public String getTextContent();
    public void setTextContent(String text);
    public void setTitle(String title);
    public String getTitle();
}
