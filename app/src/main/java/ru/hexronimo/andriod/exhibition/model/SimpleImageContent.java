package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

public class SimpleImageContent implements Content {

    private String title;
    private String text;
    private String imagePath;

    public SimpleImageContent(Uri uri){
        this.imagePath = uri.toString();
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
    @Override
    public Uri getImagePath() {
        return Uri.parse(imagePath);
    }

    public void setImagePath(Uri imgPath) {
        this.imagePath = imgPath.toString();
    }


    // interface default
    @Override
    public Uri getAudioPath() {
        return null;
    }
    @Override
    public Uri getVideoPath() {
        return null;
    }
    @Override
    public Boolean isAutoPlay() { return null; }
}
