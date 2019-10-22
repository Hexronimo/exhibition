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

        final SeekBar seekbar = findViewById(R.id.seekBar);
        seekbar.setMax(mediaPlayer.getDuration() / 1000);

        TextView title = findViewById(R.id.content_title);
        TextView body = findViewById(R.id.content_body);
        VideoView videoView = findViewById(R.id.video);
        videoView.setVideoURI(content.getVideoPath());

        seekbar.setOnSeekBarChangeListener(new MySeekBarListener());


        if (content.getTitle() != null) title.setText(content.getTitle());
        if (content.getTextContent() != null) body.setText(Html.fromHtml(content.getTextContent()));

        playStop = findViewById(R.id.play_pause);

        ContentVideoActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekbar.setProgress(0);
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

        ContentVideoActivity.this.finish();
    }

    //chenge seekbar position when user drags seekbar's circle
    class MySeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (mediaPlayer != null && fromUser) {
                mediaPlayer.seekTo(progress * 1000);
            }
        }

    }
}
