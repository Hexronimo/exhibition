package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import ru.hexronimo.andriod.exhibition.model.Content;

public class ContentMediaActivity extends AppCompatActivity implements View.OnClickListener {

    private static Content content;
    private static MediaPlayer mediaPlayer;
    private static boolean playPosition = false;
    private static Button playStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_media);

        Intent i = getIntent();
        content = (Content)i.getSerializableExtra("content");

        mediaPlayer = MediaPlayer.create(getApplicationContext(), content.getMediaPath());

        TextView title = findViewById(R.id.content_title);
        TextView body = findViewById(R.id.content_body);
        ImageView imageView = findViewById(R.id.content_image);


        if (content.getTitle() != null) title.setText(content.getTitle());
        if (content.getTextContent() != null) body.setText(Html.fromHtml(content.getTextContent()));
        if (content.getImagePath() != null) imageView.setImageURI(content.getImagePath());

        playStop = findViewById(R.id.play_pause);

        if (content.isAutoPlay()) {
            mediaPlayer.start();
            playPosition = true;
            playStop.setText(R.string.pause);
        }


    }

    public void onClick(View v) {
        if (playPosition == false) {        // not playing
            if (mediaPlayer == null) {      // for some reason, didn't initialized at onCreate?
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.audio_sample);
                mediaPlayer.start();
                playPosition = true;
                playStop.setText(R.string.pause);
            } else if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                playPosition = true;
                playStop.setText(R.string.pause);
            }
        } else {                            // playing
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                playPosition = false;
                playStop.setText(R.string.play);
            }
        }
    }

    public void onClickClose(View v){
        content = null;
        mediaPlayer.stop();
        mediaPlayer.release();

        ContentMediaActivity.this.finish();
    }
}
