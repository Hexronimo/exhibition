package ru.hexronimo.andriod.exhibition.model;

import ru.hexronimo.andriod.exhibition.R;

public enum ContentLayouts {
    TEXT,
    POETRY,
    IMAGE_WITH_TEXT,
    SMALL_IMAGE_WITH_TEXT,
    FULL_SCREEN_IMAGE_WITH_TEXT,
    IMAGE_AS_BG_WITH_TEXT,
    AUDIO,
    VIDEO;

    public static int getLayoutId(ContentLayouts c){
        switch(c){
            case TEXT: return R.layout.activity_content_ver1;
            case POETRY: return R.layout.activity_content_ver2;
            case SMALL_IMAGE_WITH_TEXT: return R.layout.activity_content_ver2;
            case IMAGE_AS_BG_WITH_TEXT: return R.layout.activity_content_ver4;
            case FULL_SCREEN_IMAGE_WITH_TEXT: return R.layout.activity_content_ver3;
            case IMAGE_WITH_TEXT: return R.layout.activity_content_ver1;
            case AUDIO: return R.layout.activity_content_audio;
            case VIDEO: return R.layout.activity_content_video;
            default: return R.layout.activity_content_ver1;
        }
    }
}
