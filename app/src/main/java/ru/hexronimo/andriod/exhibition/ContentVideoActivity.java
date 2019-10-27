package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
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
import android.widget.RelativeLayout;
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


        Intent i = getIntent();
        content = (Content) i.getSerializableExtra("content");
        setContentView(content.getLayout());

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);
        int width = size.x;

        final int globalwidth;
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
        mediaController.setAnchorView(findViewById(R.id.rel_video_view));
        ScrollView scrollView = findViewById(R.id.scroll_video);


        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                mediaController.hide();
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                int width = mp.getVideoWidth();
                int height = mp.getVideoHeight();

                RelativeLayout.LayoutParams vparams = (RelativeLayout.LayoutParams)videoView.getLayoutParams();
                vparams.width = globalwidth;
                vparams.height = (int)(globalwidth/(float)width * height);
                styleMediaController(mediaController);
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
                        mediaController.show(6000);
                    }},
                100);

        if (content.getTitle() != null) title.setText(content.getTitle());
        if (content.getTextContent() != null) body.setText(Html.fromHtml(content.getTextContent()));

    }

    public void onClick(View v) {
        content = null;
        ContentVideoActivity.this.finish();
    }

    private void styleMediaController(View view) {
        if (view instanceof MediaController) {
            MediaController v = (MediaController) view;
            for(int i = 0; i < v.getChildCount(); i++) {
                styleMediaController(v.getChildAt(i));
            }
        } else
        if (view instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) view;
            for(int i = 0; i < ll.getChildCount(); i++) {
                styleMediaController(ll.getChildAt(i));
            }
        } else if (view instanceof SeekBar) {
            ((SeekBar) view).getProgressDrawable().setColorFilter(getResources().getColor(R.color.mainactive), PorterDuff.Mode.SRC_ATOP);
            ((SeekBar) view).getThumb().setColorFilter(getResources().getColor(R.color.mainactivesec), PorterDuff.Mode.SRC_ATOP);
        }
    }

}
