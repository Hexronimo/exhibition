package ru.hexronimo.andriod.exhibition;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Point;
import ru.hexronimo.andriod.exhibition.model.Scene;


public class SceneActivity extends AppCompatActivity {

    private static int pointSize = 0;
    private static Exhibition exhibition;
    private static Scene scene;
    private int currentApiVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
        setContentView(R.layout.activity_scene);


        Intent i = getIntent();
        exhibition = (Exhibition) i.getSerializableExtra("exhibition");
        scene = (Scene) i.getSerializableExtra("scene");
        ImageView imageView = findViewById(R.id.scene_image);

        if (scene == null) scene = exhibition.getLeft();

        if (scene.getLeft() != -1) {
            TextView textViewLeft = findViewById(R.id.scene_left_name);
            System.out.println(scene.getLeft());
            System.out.println((exhibition.getScenes().get(scene.getLeft())).getTitle());
            textViewLeft.setText(exhibition.getScenes().get(scene.getLeft()).getTitle());
        } else {
            Button left = findViewById(R.id.scene_go_left);
            left.setVisibility(View.GONE);
        }
        if (scene.getRight() != -1) {
            TextView textViewLeft = findViewById(R.id.scene_right_name);
            textViewLeft.setText(exhibition.getScenes().get(scene.getRight()).getTitle());
        } else {
            Button right = findViewById(R.id.scene_go_right);
            right.setVisibility(View.GONE);
        }

        imageView.setImageURI(scene.getImagePath());
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalListenerClass());

        TextView infoView = findViewById(R.id.scene_info_txt);
        infoView.setText(Html.fromHtml(scene.getInfo()));
        if (scene.getInfo().length() == 0) {
            Button infoButton = findViewById(R.id.button);
            infoButton.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

     public void onClick(View v) {
        ScrollView scrollView = findViewById(R.id.scene_parent_info);
        Button closeButton = findViewById(R.id.button2);
        if (scrollView.getVisibility() == View.GONE){
            closeButton.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
        } else {
            closeButton.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
        }

    }

    public void onClickLeft(View view){
        Intent i = getIntent();
        i.putExtra("scene",exhibition.getScenes().get(scene.getLeft()));
        recreate();
    }

    public void onClickRight(View view){
        Intent i = getIntent();
        i.putExtra("scene",exhibition.getScenes().get(scene.getRight()));
        recreate();
    }

    // declare the layout listener
    class MyGlobalListenerClass implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            View v = (View) findViewById(R.id.scene_image);
            int imageHeight =  v.getHeight();
            int imageWidth =  v.getWidth();
            pointSize = imageHeight/12;
            RelativeLayout rl = findViewById(R.id.scene_relative_layout);

            //my classes
            int i = 0;
            for (Point point : scene.getPoints()) {

                Button btn = new Button(getApplicationContext());
                btn.setId(i);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pointSize, pointSize);

                params.setMargins((int)(imageWidth*point.getMarginX()), (int)(imageHeight*point.getMarginY()),0,0);
                btn.setLayoutParams(params);
                btn.setBackgroundResource(R.drawable.pointbluepressed);
                PointOnTouchListener listener = new PointOnTouchListener();
                listener.setPoint(point);
                btn.setOnTouchListener(listener);
                rl.addView(btn);
                i++;

            }
            // does it really remove listener? Anyway without it app become very slow,
            // probably adding new Points on every layout related event, even on scrolling
            v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

}

