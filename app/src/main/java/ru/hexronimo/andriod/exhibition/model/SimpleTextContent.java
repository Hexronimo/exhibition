package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SimpleTextContent implements Content {

    private String title;
    private String text;
    private ContentLayouts layout;

    public SimpleTextContent(ContentLayouts layout, String title, String text) {
        this.title = title;
        this.text = text;
        this.layout = layout;
    }

    @Override
    public String getTextContent() {
        text = text.replaceAll("\n","<br>");
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
    @Override
    public ContentLayouts getLayout() {
        return layout;
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
