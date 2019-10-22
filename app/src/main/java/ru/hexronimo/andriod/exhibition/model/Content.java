package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

import java.io.Serializable;

public interface Content extends Serializable{

    public String getTextContent();
    public void setTextContent(String text);
    public void setTitle(String title);
    public String getTitle();

    public Uri getImagePath();
    public Uri getAudioPath();
    public Uri getVideoPath();

    public Boolean isAutoPlay();
}
