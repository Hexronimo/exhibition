package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

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


    // interface default
    @Override
    public Uri getImagePath(){
        return null;
    }

    @Override
    public Uri getAudioPath() {
        return null;
    }

    @Override
    public Uri getVideoPath() {
        return null;
    }

    @Override
    public Boolean isAutoPlay() {
        return null;
    }
}
