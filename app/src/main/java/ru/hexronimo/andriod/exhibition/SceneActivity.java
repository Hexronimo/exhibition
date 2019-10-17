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

import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Point;
import ru.hexronimo.andriod.exhibition.model.Scene;


public class SceneActivity extends AppCompatActivity implements View.OnTouchListener {
    int prevX, prevY;
    private static int pointSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        ImageView imageView = findViewById(R.id.scene_image);

        //adding actual jpg picture to ImageView which already made in XML
        imageView.setImageResource(R.drawable.testscene);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalListenerClass());
    }


    @Override
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
    }

    // declare the layout listener
    class MyGlobalListenerClass implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            View v = (View) findViewById(R.id.scene_image);
            pointSize = v.getHeight()/10;
            RelativeLayout rl = findViewById(R.id.scene_relative_layout);

            //my classes
            Exhibition exhibition = new Exhibition("DEMO");
            Scene scene = exhibition.getLeft();

            int i = 0;
            for (Point point : scene.getPoints()) {
                Button btn = new Button(getApplicationContext());
                btn.setId(i);
                final int id_ = btn.getId();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pointSize, pointSize);
                btn.setBackgroundResource(R.drawable.pointblue);
                params.setMargins((int)point.getMarginX(), (int)point.getMarginY(),0,0);
                btn.setLayoutParams(params);
                rl.addView(btn);
                i++;

/*            btn1 = ((Button) findViewById(id_));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                            .show();
        }*/

            }
            // does it really remove listener? Anyway without it app become very slow,
            // probably adding new Points on every layout related event, even on scrolling
            v.getViewTreeObserver().removeOnGlobalLayoutListener(this);


        }
    }

}

