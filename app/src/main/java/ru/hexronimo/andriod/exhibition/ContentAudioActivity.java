package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.hexronimo.andriod.exhibition.model.Content;

public class ContentAudioActivity extends AppCompatActivity implements View.OnClickListener {

    private static Content content;
    private static MediaPlayer mediaPlayer;
    private static boolean playPosition = false;
    private static Button playStop;
    private static Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_audio);

        Intent i = getIntent();
        content = (Content) i.getSerializableExtra("content");




    }

    public void onResume(){

        super.onResume();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), content.getAudioPath());

        final SeekBar seekbar = findViewById(R.id.seekBar);
        seekbar.setMax(mediaPlayer.getDuration() / 1000);

        TextView title = findViewById(R.id.content_title);
        TextView body = findViewById(R.id.content_body);
        ImageView imageView = findViewById(R.id.content_image);


        seekbar.setOnSeekBarChangeListener(new MySeekBarListener());
        if (content.getTitle() != null) title.setText(content.getTitle());
        if (content.getTextContent() != null) body.setText(Html.fromHtml(content.getTextContent()));
        if (content.getImagePath() != null) imageView.setImageURI(content.getImagePath());

        playStop = findViewById(R.id.play_pause);

        ContentAudioActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekbar.setProgress(mediaPlayer.getCurrentPosition());
                }
                mHandler.postDelayed(this, 1000);
            }
        });

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

    public void onClickClose(View v) {
        content = null;
        mediaPlayer.stop();
        mediaPlayer.release();

        ContentAudioActivity.this.finish();
    }

    //chenge seekbar position when user drags seekbar's circle
    class MySeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (mediaPlayer != null && fromUser) {
                mediaPlayer.seekTo(progress * 1000);
            }
        }

    }
}
