package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

@SuppressWarnings("serial")
public class SimpleVideoContent implements Content {

    private String title;
    private String text;
    private String mediaPath;
    private boolean autoplay = false;
    private ContentLayouts layout;

    public SimpleVideoContent(ContentLayouts layout, Uri uri, boolean autoplay, String title, String text){
        if (uri != null && !"".equals(uri)) this.mediaPath = uri.toString();
        this.autoplay = autoplay;
        this.layout = layout;
        this.title = title;
        this.text = text;
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

    @Override
    public ContentLayouts getLayout() {
        return layout;
    }

    // interface default
    @Override
    public Uri getImagePath(){ return null; }

    @Override
    public Uri getAudioPath(){ return null; }

}
