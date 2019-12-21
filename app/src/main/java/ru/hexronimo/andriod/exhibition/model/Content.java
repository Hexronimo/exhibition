package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

import java.io.Serializable;

public interface Content extends Serializable{

    // every content type have these
    public String getTextContent();
    public void setTextContent(String text);
    public void setTitle(String title);
    public String getTitle();

    // not every content type have these
    // sadly there is no default methods for intenface in this version, so I need to implement them all
    public Uri getImagePath();
    public Uri getAudioPath();
    public Uri getVideoPath();
    public Boolean isAutoPlay();
    public ContentLayouts getLayout();

}
