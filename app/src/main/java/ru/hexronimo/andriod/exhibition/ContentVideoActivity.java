package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import ru.hexronimo.andriod.exhibition.model.Content;

public class ContentVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private static Content content;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_video);

        Intent i = getIntent();
        content = (Content) i.getSerializableExtra("content");

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


        final MediaController mediaController = new MediaController(this, false);
        TextView title = findViewById(R.id.content_title);
        TextView body = findViewById(R.id.content_body);
        final VideoView videoView = findViewById(R.id.video);

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(content.getVideoPath());
        mediaController.setAnchorView(videoView);

        ScrollView scrollView = findViewById(R.id.scroll_video);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                mediaController.hide();
                mediaController.show(7000);
            }
        });


        if (content.isAutoPlay()) videoView.start();
        else {
            videoView.start();
            videoView.seekTo( 1 );
            videoView.pause();
        }
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        mediaController.show(7000);
                    }},
                100);

        if (content.getTitle() != null) title.setText(content.getTitle());
        if (content.getTextContent() != null) body.setText(Html.fromHtml(content.getTextContent()));

    }

    public void onClick(View v) {
        content = null;
        ContentVideoActivity.this.finish();
    }

}
