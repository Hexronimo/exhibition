package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

@SuppressWarnings("serial")
public class SimpleVideoContent implements Content {

    private String title;
    private String text;
    private String mediaPath;
    private boolean autoplay = false;

    public SimpleVideoContent(Uri media, boolean autoplay){
        this.mediaPath = media.toString();
        this.autoplay = autoplay;
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
    public Uri getVideoPath() {
        return Uri.parse(mediaPath);
    }

    public void setVideoPath(Uri mediaPath) {
        this.mediaPath = mediaPath.toString();
    }

    @Override
    public Boolean isAutoPlay() {
        return autoplay;
    }

    // interface default
    @Override
    public Uri getImagePath(){ return null; }

    @Override
    public Uri getAudioPath(){ return null; }

}
