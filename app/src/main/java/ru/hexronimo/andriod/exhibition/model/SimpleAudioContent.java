package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

@SuppressWarnings("serial")
public class SimpleAudioContent implements Content {

    private String title;
    private String text;
    private String imgPath;
    private String mediaPath;
    private boolean autoPlay;
    private int layout;

    public SimpleAudioContent(int layout, Uri media, Uri image, boolean autoplay, String title, String text){
        this.mediaPath = media.toString();
        if (image != null && !"".equals(image.toString().trim())) this.imgPath = image.toString();
        this.autoPlay = autoplay;
        this.layout = layout;
        this.title = title;
        this.text = text;
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
    @Override
    public Uri getAudioPath() {
        return Uri.parse(mediaPath);
    }

    public void setAudioPath(Uri mediaPath) {
        this.mediaPath = mediaPath.toString();
    }
    @Override
    public Boolean isAutoPlay() {
        return autoPlay;
    }

    @Override
    public int getLayout() {
        return layout;
    }

    // interface default
    public Uri getVideoPath() {
        return null;
    }

}
