package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import ru.hexronimo.andriod.exhibition.model.Content;

public class ContentVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private static Content content;
    private static boolean playPosition = false;
    private static Button playStop;
    private static Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_video);

        Intent i = getIntent();
        content = (Content) i.getSerializableExtra("content");

        TextView title = findViewById(R.id.content_title);
        TextView body = findViewById(R.id.content_body);
        VideoView videoView = findViewById(R.id.video);
        videoView.setVideoURI(content.getVideoPath());



        if (content.getTitle() != null) title.setText(content.getTitle());
        if (content.getTextContent() != null) body.setText(Html.fromHtml(content.getTextContent()));

    }

    public void onClick(View v) {
        content = null;
        ContentVideoActivity.this.finish();
    }
}
