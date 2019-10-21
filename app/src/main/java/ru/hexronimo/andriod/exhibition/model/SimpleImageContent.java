package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

public class SimpleImageContent implements Content {

    private String title;
    private String text;
    private String imgPath;

    public SimpleImageContent(Uri uri){
        this.imgPath = uri.toString();
    }

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

    public Uri getImagePath() {
        return Uri.parse(imgPath);
    }

    public void setImagePath(Uri imgPath) {
        this.imgPath = imgPath.toString();
    }

    @Override
    public Uri getMediaPath() {
        return null;
    }

    @Override
    public Boolean isAutoPlay() {
        return null;
    }
}
