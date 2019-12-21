package ru.hexronimo.andriod.exhibition.model;

import android.net.Uri;

public class SimpleImageContent implements Content {

    private String title;
    private String text;
    private String imagePath;
    private ContentLayouts layout;

    public SimpleImageContent(ContentLayouts layout, Uri uri, String title, String text){
        if (uri != null && uri.toString().trim().length() != 0) this.imagePath = uri.toString();
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
    public Uri getImagePath() {
        return Uri.parse(imagePath);
    }

    public void setImagePath(Uri imgPath) {
        this.imagePath = imgPath.toString();
    }

    @Override
    public ContentLayouts getLayout() {
        return layout;
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
