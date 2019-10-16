package ru.hexronimo.andriod.exhibition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class SceneActivity extends AppCompatActivity implements View.OnTouchListener {

    private float oldXvalue;
    private float oldYvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        //Test

        ImageView imageView = findViewById(R.id.scene_image);
        imageView.setImageResource(R.drawable.testscene);
        Button button1 = (Button)findViewById(R.id.button);
        button1.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent me) {
        HorizontalScrollView scrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            scrollView.requestDisallowInterceptTouchEvent(true);;
                oldXvalue = me.getX();
            oldYvalue = me.getY();
        } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.scene_relative_layout);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
            params.leftMargin = (int) (me.getRawX() - (v.getWidth() / 2));
            params.topMargin = (int) (me.getRawY() - (v.getHeight()));

            v.setLayoutParams(params);
        }
        return true;
    }

}

