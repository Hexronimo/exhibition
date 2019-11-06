package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.hexronimo.andriod.exhibition.model.Content;
import ru.hexronimo.andriod.exhibition.model.SimpleAudioContent;
import ru.hexronimo.andriod.exhibition.model.SimpleImageContent;
import ru.hexronimo.andriod.exhibition.model.SimpleTextContent;
import ru.hexronimo.andriod.exhibition.model.SimpleVideoContent;

public class AddContentActivity extends AppCompatActivity {
    private Uri image;
    private Uri video;
    private Uri audio;
    private Content content;
    private Integer layout;
    private boolean autoplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_content);
        Intent i = getIntent();
        layout = (Integer) i.getSerializableExtra("content_layout");

        // change screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

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

        if (layout == R.layout.activity_content_video) {
            findViewById(R.id.if_video).setVisibility(View.VISIBLE);
            findViewById(R.id.if_video_txt).setVisibility(View.VISIBLE);
            findViewById(R.id.autoplay).setVisibility(View.VISIBLE);
        }
        else if (layout == R.layout.activity_content_audio) {
            findViewById(R.id.if_audio).setVisibility(View.VISIBLE);
            findViewById(R.id.if_audio_txt).setVisibility(View.VISIBLE);
            findViewById(R.id.if_picture).setVisibility(View.VISIBLE);
            findViewById(R.id.if_picture_txt).setVisibility(View.VISIBLE);
            findViewById(R.id.autoplay).setVisibility(View.VISIBLE);
        }
        else if (layout == R.layout.activity_content_ver1 || layout == R.layout.activity_content_ver3) {
            findViewById(R.id.if_picture).setVisibility(View.VISIBLE);
            findViewById(R.id.if_picture_txt).setVisibility(View.VISIBLE);
        }
    }


    public void onClick (View view){
        TextView title = findViewById(R.id.content_title);
        TextView text = findViewById(R.id.content_text);

        if (layout == R.layout.activity_content_video) content = new SimpleVideoContent(layout, video, autoplay, title.getText().toString(), text.getText().toString());
        else if (layout == R.layout.activity_content_audio) content = new SimpleAudioContent(layout, audio, image, autoplay ,title.getText().toString(), text.getText().toString());
        else if (layout == R.layout.activity_content_ver1 || layout == R.layout.activity_content_ver3) content = new SimpleTextContent(layout, title.getText().toString(), text.getText().toString());
        else content = new SimpleImageContent(layout, image, title.getText().toString(), text.getText().toString());

    }

}
