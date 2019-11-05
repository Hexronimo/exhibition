package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Point;
import ru.hexronimo.andriod.exhibition.model.Scene;


public class EditSceneActivity extends AppCompatActivity implements View.OnTouchListener {
    int prevX, prevY;
    private static int pointSize = 0;
    private static Exhibition exhibition;
    private static Scene scene;
    private static Integer selectedPointId; // cuz it can be null
    private static Button selectedPoint;
    private static int centerX;
    private static int centerY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_scene);

        Intent i = getIntent();
        exhibition = (Exhibition) i.getSerializableExtra("exhibition");
        scene = exhibition.getScenes().get(i.getSerializableExtra("sceneId"));

        ImageView imageView = findViewById(R.id.scene_image);

        //adding jpg picture to ImageView
        imageView.setImageURI(scene.getImagePath());
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalListenerClass());

        //calculate center of the screen for later use
        Display display = getWindowManager().getDefaultDisplay();
        android.graphics.Point size = new android.graphics.Point();

        display.getSize(size);
        centerX = size.x/2;
        centerY = size.y/2;
    }

    // click button "Create new Point"
    public void onClickAddPoint(View v) {
        // if one new unsaved Point already exists we need to remove it
        if (selectedPointId == null && selectedPoint != null) {
            RelativeLayout rl = findViewById(R.id.scene_relative_layout);
            rl.removeView(selectedPoint);
        }
        // create new Point and place it in middle of screen
        Button btn = new Button(getApplicationContext());
        btn.setId(scene.getPoints().size());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pointSize, pointSize);
        params.setMargins(centerX, centerY,0,0);
        btn.setLayoutParams(params);
        btn.setBackgroundResource(R.drawable.pointblue);
        btn.setOnTouchListener(this);

        RelativeLayout rl = findViewById(R.id.scene_relative_layout);
        selectedPointId = null; // it's still null cuz unsaved (can be saved after adding content)
        selectedPoint = btn;
        rl.addView(btn);
    }

    public void onClickAddContent(View v) {
        Intent intent = new Intent(v.getContext(), ChooseContentType.class);
        v.getContext().startActivity(intent);
    }

    public boolean onTouch(View v, MotionEvent event) {
        RelativeLayout.LayoutParams par=(RelativeLayout.LayoutParams)v.getLayoutParams();
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);

        switch(event.getAction()) {
            case MotionEvent.ACTION_UP: {
                // save new position of the Point
                if (selectedPointId != null) {
                    scene.getPoints().get(selectedPointId).setMarginX(par.leftMargin);
                    scene.getPoints().get(selectedPointId).setMarginY(par.topMargin);
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                par.topMargin+=(int)event.getRawY()-prevY;
                prevY=(int)event.getRawY();
                par.leftMargin+=(int)event.getRawX()-prevX;
                prevX=(int)event.getRawX();
                v.setLayoutParams(par);
                return true;
            }
            case MotionEvent.ACTION_DOWN: {
                BottomNavigationView bnv = findViewById(R.id.edit_scene_bottom);
                bnv.setVisibility(View.VISIBLE);

                // unselect previous selected and select pressed button
                if (v.getId() != selectedPoint.getId()) {
                    selectedPoint.setBackgroundResource(R.drawable.pointbluepressed);
                    v.setBackgroundResource(R.drawable.pointblue);
                    selectedPointId = v.getId();
                    selectedPoint = (Button)v;
                }
                horizontalScrollView.requestDisallowInterceptTouchEvent(true);
                prevX=(int)event.getRawX();
                prevY=(int)event.getRawY();
                par.bottomMargin=-2*v.getHeight();
                par.rightMargin=-2*v.getWidth();
                v.setLayoutParams(par);
                return true;
            }
        }
        horizontalScrollView.requestDisallowInterceptTouchEvent(false);
        return false;
    }

    public void onClickDelete(View view){
        if (selectedPointId != null) scene.deletePoint(view.getId());
        selectedPointId = null;
        RelativeLayout rl = findViewById(R.id.scene_relative_layout);
        rl.removeView(selectedPoint);
        selectedPoint = null;
        BottomNavigationView bnv = findViewById(R.id.edit_scene_bottom);
        bnv.setVisibility(View.GONE);
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

            // my classes
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

