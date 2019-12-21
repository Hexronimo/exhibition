package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
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
import ru.hexronimo.andriod.exhibition.model.ContentLayouts;

public class ContentAudioActivity extends AppCompatActivity implements View.OnClickListener {

    private static Content content;
    private static MediaPlayer mediaPlayer;
    private static boolean playPosition;
    private static SeekBar seekbar;
    private static Button playStop;
    private static Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent i = getIntent();
        content = (Content) i.getSerializableExtra("content");
        setContentView(ContentLayouts.getLayoutId(content.getLayout()));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);
        int width = size.x;

        int globalwidth;
        if (width > 2000) globalwidth = width/3*2;
        else if (width < 1000) globalwidth = width;
        else globalwidth = width/4*3;

        LinearLayout linearLayout = findViewById(R.id.linear_for_all);
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        params.width = globalwidth;

        mediaPlayer = MediaPlayer.create(getApplicationContext(), content.getAudioPath());

        seekbar = findViewById(R.id.seekBar);

        seekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.mainactive), PorterDuff.Mode.SRC_ATOP);
        seekbar.getThumb().setColorFilter(getResources().getColor(R.color.mainactivesec), PorterDuff.Mode.SRC_ATOP);

        seekbar.setMax(mediaPlayer.getDuration() / 1000);

        TextView title = findViewById(R.id.content_title);
        TextView body = findViewById(R.id.content_body);
        ImageView imageView = findViewById(R.id.content_image);

        // if it's not only text
        if (content.getImagePath() != null) imageView.setVisibility(View.VISIBLE);

        // if is't layout with small image and text at right, we just make image 1/3 of screen
        if (content.getImagePath() != null) {
            ViewGroup.LayoutParams paramsimg = imageView.getLayoutParams();
            paramsimg.width = globalwidth/3;
        }

        seekbar.setOnSeekBarChangeListener(new MySeekBarListener());
        if (content.getTitle() != null) title.setText(content.getTitle());
        if (content.getTextContent() != null) body.setText(Html.fromHtml(content.getTextContent()));
        if (content.getImagePath() != null) imageView.setImageURI(content.getImagePath());

        playStop = findViewById(R.id.play_pause);


        playPosition = false;
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                if (content.isAutoPlay()) {
                    mediaPlayer.start();
                    playPosition = true;
                    playStop.setText(R.string.pause);
                }
                mHandler = new Handler();
                ContentAudioActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                            seekbar.setProgress(mCurrentPosition);
                        }
                        mHandler.postDelayed(this, 100);
                    }
                });
            }
        });


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

        mediaPlayer.stop();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        mediaPlayer.release();
        mediaPlayer = null;
        content = null;
        ContentAudioActivity.this.finish();
    }

    // changes seekbar position when user drags seekbar's circle
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
