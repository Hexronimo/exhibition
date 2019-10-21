package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

@SuppressWarnings("serial")
public class SimpleMediaContent implements Content {

    private String title;
    private String text;
    private String imgPath;
    private String mediaPath;
    private boolean autoPlay = false;

    public SimpleMediaContent(Uri media){
        this.mediaPath = media.toString();
    }
    public SimpleMediaContent(Uri media, boolean autoplay){
        this.mediaPath = media.toString();
        this.autoPlay = autoplay;
    }
    public SimpleMediaContent(Uri media, Uri image){
        this.mediaPath = media.toString();
        this.imgPath = image.toString();
    }
    public SimpleMediaContent(Uri media, Uri image, boolean autoplay){
        this.mediaPath = media.toString();
        this.imgPath = image.toString();
        this.autoPlay = autoplay;
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

    public void setImagePath(Uri uri){
        this.imgPath = uri.toString();
    }

    @Override
    public Uri getImagePath(){
        if (this.imgPath == null) return null;
        return Uri.parse(this.imgPath);
    }

    public Uri getMediaPath() {
        return Uri.parse(mediaPath);
    }

    public void setMediaPath(Uri mediaPath) {
        this.mediaPath = mediaPath.toString();
    }

    public Boolean isAutoPlay() {
        return autoPlay;
    }

}
