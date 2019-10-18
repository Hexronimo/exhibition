package ru.hexronimo.andriod.exhibition.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SimpleTextContent implements Content {

    private String title;
    private String text;

    @Override
    public String getTextContent() {
        return this.text;
    }

    @Override
    public void setTextContent(String text) {
        this.text = text;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
