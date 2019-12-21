package ru.hexronimo.andriod.exhibition;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ru.hexronimo.andriod.exhibition.model.Content;
import ru.hexronimo.andriod.exhibition.model.ContentLayouts;
import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Point;
import ru.hexronimo.andriod.exhibition.model.Scene;
import ru.hexronimo.andriod.exhibition.model.SimpleAudioContent;
import ru.hexronimo.andriod.exhibition.model.SimpleImageContent;
import ru.hexronimo.andriod.exhibition.model.SimpleTextContent;
import ru.hexronimo.andriod.exhibition.model.SimpleVideoContent;
import ru.hexronimo.andriod.exhibition.model.Storage;

public class AddContentActivity extends AppCompatActivity {
    private Uri image;
    private Uri video;
    private Uri audio;
    private Content content;
    private Integer pointId;

    private static Integer layout;
    private static ContentLayouts layoutType;
    private static Exhibition exhibition;
    private static Scene scene;
    private static Point point;

    private static final int READ_REQUEST_CODE_TXT = 1;
    private static final int READ_REQUEST_CODE_IMG = 2;
    private static final int READ_REQUEST_CODE_AUDIO = 3;
    private static final int READ_REQUEST_CODE_VIDEO = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_content);
        Intent i = getIntent();

        exhibition = (Exhibition) i.getSerializableExtra("exhibition");
        scene = exhibition.getScenes().get(i.getSerializableExtra("sceneId"));
        pointId = (Integer) i.getSerializableExtra("pointId");

        // check if edit existing or create new one
        if (pointId != null) {
            layoutType = scene.getPoints().get(pointId).getContent().getLayout();
            point = scene.getPoints().get(pointId);
        } else {
            layoutType = (ContentLayouts) i.getSerializableExtra("content_layout");
        }
        layout = ContentLayouts.getLayoutId(layoutType);

        // change screen size
        Display display = getWindowManager().getDefaultDisplay();
        android.graphics.Point size = new android.graphics.Point();

        display.getSize(size);
        int width = size.x;

        // setting main container width
        LinearLayout linearLayout = findViewById(R.id.linear_for_all);
        if (linearLayout != null) {
            int globalwidth;
            if (width > 2000) globalwidth = width/3*2;
            else if (width < 1000) globalwidth = width;
            else globalwidth = width/4*3;
            ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
            params.width = globalwidth;
            linearLayout.setLayoutParams(params);
        }

        // fill form for existing Point to edit content
        if (point != null) {
            TextView title = findViewById(R.id.content_title);
            TextView text = findViewById(R.id.content_text);
            if (point.getContent().getTitle() != null) title.setText(point.getContent().getTitle());
            if (point.getContent().getTextContent() != null) text.setText(point.getContent().getTextContent());
            if (point.getContent().isAutoPlay() != null) {
                Switch autoplay = findViewById(R.id.autoplay);
                autoplay.setChecked(point.getContent().isAutoPlay());
            }
            if (point.getContent().getImagePath() != null) {
                image = point.getContent().getImagePath();
                ImageView imageView = findViewById(R.id.scene_pic);
                imageView.setImageURI(image);
            }
            if (point.getContent().getAudioPath() != null) {
                audio = point.getContent().getAudioPath();
                TextView textView = findViewById(R.id.textView11);
                textView.setText(audio.toString());
            }
            if (point.getContent().getVideoPath() != null) {
                video = point.getContent().getVideoPath();
                TextView textView = findViewById(R.id.text_video);
                textView.setText(video.toString());
            }
        }

        if (layoutType == ContentLayouts.VIDEO) {
            findViewById(R.id.if_video).setVisibility(View.VISIBLE);
            findViewById(R.id.if_video_txt).setVisibility(View.VISIBLE);
            findViewById(R.id.autoplay).setVisibility(View.VISIBLE);

        }
        else if (layoutType == ContentLayouts.AUDIO) {
            findViewById(R.id.if_audio).setVisibility(View.VISIBLE);
            findViewById(R.id.if_audio_txt).setVisibility(View.VISIBLE);
            findViewById(R.id.if_picture).setVisibility(View.VISIBLE);
            findViewById(R.id.if_picture_txt).setVisibility(View.VISIBLE);
            findViewById(R.id.autoplay).setVisibility(View.VISIBLE);
        }
        else if (layoutType == ContentLayouts.IMAGE_WITH_TEXT ||
                layoutType == ContentLayouts.IMAGE_AS_BG_WITH_TEXT ||
                layoutType == ContentLayouts.FULL_SCREEN_IMAGE_WITH_TEXT ||
                layoutType == ContentLayouts.SMALL_IMAGE_WITH_TEXT) {
            findViewById(R.id.if_picture).setVisibility(View.VISIBLE);
            findViewById(R.id.if_picture_txt).setVisibility(View.VISIBLE);
        }
    }
    public void onClickLoad (View view) {
        switch(view.getId()){
            case(R.id.btn_text):{
                System.out.println("Choosing text...");
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/*");
                startActivityForResult(intent, READ_REQUEST_CODE_TXT);
                break;
            }
            case(R.id.btn_img):{
                System.out.println("Choosing image...");
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE_IMG);
                break;
            }
            case(R.id.btn_audio):{
                System.out.println("Choosing audio file...");
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("audio/*");
                startActivityForResult(intent, READ_REQUEST_CODE_AUDIO);
                break;
            }
            case(R.id.btn_video): {
                System.out.println("Choosing video file...");
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("video/*");
                startActivityForResult(intent, READ_REQUEST_CODE_VIDEO);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==READ_REQUEST_CODE_TXT && resultCode == Activity.RESULT_OK) {
            System.out.println("Setting URI of selected file");
            Uri textUri = data.getData();
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(textUri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int i;
                try {
                    i = is.read();
                    while (i != -1) {
                        byteArrayOutputStream.write(i);
                        i = is.read();
                    }

                    TextView textView = findViewById(R.id.content_text);
                    textView.setText(byteArrayOutputStream.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        if(requestCode==READ_REQUEST_CODE_IMG && resultCode == Activity.RESULT_OK) {
            image = data.getData();
            ImageView imageView = findViewById(R.id.scene_pic);
            imageView.setImageURI(image);
        }

        if(requestCode==READ_REQUEST_CODE_AUDIO && resultCode == Activity.RESULT_OK) {
            audio = data.getData();
            TextView textView = findViewById(R.id.textView11);
            textView.setText(audio.toString());
        }

        if(requestCode==READ_REQUEST_CODE_VIDEO && resultCode == Activity.RESULT_OK) {
            video = data.getData();
            TextView textView = findViewById(R.id.text_video);
            textView.setText(video.toString());
        }
    }

    public void onClick (View view){
        TextView title = findViewById(R.id.content_title);
        TextView text = findViewById(R.id.content_text);

        String tTitle = title.getText().toString();
        if (tTitle.trim().length() == 0) tTitle = null;
        String tText = text.getText().toString();
        if (tText.trim().length() == 0) tText = null;

        Switch autoplay = findViewById(R.id.autoplay);
        boolean isAutoplay = autoplay.isChecked();

        switch(layoutType){
            case IMAGE_WITH_TEXT: {
                content = new SimpleImageContent(layoutType, image, tTitle, tText);
                break;
            }
            case SMALL_IMAGE_WITH_TEXT: {
                content = new SimpleImageContent(layoutType, image, tTitle, tText);
                break;
            }
            case AUDIO: {
                content = new SimpleAudioContent(layoutType, audio, image, isAutoplay, tTitle, tText);
                break;
            }
            case VIDEO: {
                content = new SimpleVideoContent(layoutType, video, isAutoplay, tTitle, tText);
                break;
            }
            case IMAGE_AS_BG_WITH_TEXT: {
                content = new SimpleImageContent(layoutType, image, tTitle, tText);
                break;
            }
            case FULL_SCREEN_IMAGE_WITH_TEXT: {
                content = new SimpleImageContent(layoutType, image, tTitle, tText);
                break;
            }
            default: {
                content = new SimpleTextContent(layoutType, tTitle, tText);
                break;
            }
        }

        if (point == null) {
            point = new Point((Float) getIntent().getSerializableExtra("X"), (Float) getIntent().getSerializableExtra("Y"), content);
            scene.addPoint(point);
        } else {
            point = scene.getPoints().get(pointId);
            point.setContent(content);
            scene.changePoint(pointId, point);
        }
        exhibition.addScene(scene);

        Storage.saveExhibition(exhibition);
        onClickClose(view);
    }


    public void onClickClose(View v) {
        image = null;
        video = null;
        audio = null;
        content = null;
        pointId = null;
        point = null;

        Intent intent = new Intent(v.getContext(), EditSceneActivity.class);
        intent.putExtra("exhibition", exhibition);
        intent.putExtra("sceneId", scene.getId());
        v.getContext().startActivity(intent);
        AddContentActivity.this.finish();
    }

}
