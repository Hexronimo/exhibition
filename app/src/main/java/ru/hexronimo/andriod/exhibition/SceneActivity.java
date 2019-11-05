package ru.hexronimo.andriod.exhibition;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Point;
import ru.hexronimo.andriod.exhibition.model.Scene;


public class SceneActivity extends AppCompatActivity {
    int prevX, prevY;
    private static int pointSize = 0;
    private static Exhibition exhibition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        ImageView imageView = findViewById(R.id.scene_image);

        //adding actual jpg picture to ImageView which already made in XML
        imageView.setImageResource(R.drawable.testscene);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalListenerClass());

        TextView infoView = findViewById(R.id.scene_info);
        infoView.setText(exhibition.getLeft().getInfo());
    }

    public void onClick(View v) {
        ScrollView scrollView = findViewById(R.id.scene_parent_info);
        if (scrollView.getVisibility() == View.GONE){
            scrollView.setVisibility(View.VISIBLE);
        } else {
            scrollView.setVisibility(View.GONE);
        }

    }


/*    @Override
    public boolean onTouch(View v, MotionEvent event) {
        RelativeLayout.LayoutParams par=(RelativeLayout.LayoutParams)v.getLayoutParams();
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);

        switch(event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                par.topMargin+=(int)event.getRawY()-prevY;
                prevY=(int)event.getRawY();
                par.leftMargin+=(int)event.getRawX()-prevX;
                prevX=(int)event.getRawX();
                v.setLayoutParams(par);
                return true;
            }
            case MotionEvent.ACTION_DOWN: {
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
    }*/

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

            Scene scene = exhibition.getLeft();
            System.out.println(scene);

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

