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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Point;
import ru.hexronimo.andriod.exhibition.model.Scene;
import ru.hexronimo.andriod.exhibition.model.Storage;


public class EditSceneActivity extends AppCompatActivity implements View.OnTouchListener {
    int prevX, prevY;
    private static int pointSize = 0;
    private static Exhibition exhibition;
    private static Scene scene;
    private static Integer selectedPointId; // cuz it can be null
    private static Button selectedPoint;
    private static int centerX;
    private static int centerY;
    private int imageHeight;
    private int imageWidth;

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
        //change color of previously selected point
        if (selectedPoint != null) selectedPoint.setBackgroundResource(R.drawable.pointbluepressed);

        // create new Point and place it in middle of screen
        Button btn = new Button(getApplicationContext());
        btn.setId(scene.getPoints().size());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pointSize, pointSize);
        params.setMargins(centerX, centerY,0,0);
        btn.setLayoutParams(params);
        btn.setBackgroundResource(R.drawable.pointblue);
        btn.setOnTouchListener(this);

        RelativeLayout rl = findViewById(R.id.scene_relative_layout);
        selectedPointId = null; // it's still null because point is unsaved (can be saved only after adding content)
        selectedPoint = btn;
        rl.addView(btn);
        Toast.makeText(this, R.string.drag_and_drop, Toast.LENGTH_SHORT).show();
    }

    public void onClickAddContent(View v) {
        Intent intent;
        if (selectedPointId != null) {
            intent = new Intent(v.getContext(), AddContentActivity.class);
        } else {
            intent = new Intent(v.getContext(), ChooseContentType.class);
            RelativeLayout.LayoutParams par=(RelativeLayout.LayoutParams)selectedPoint.getLayoutParams();
            intent.putExtra("X", par.leftMargin/(float)imageWidth);
            intent.putExtra("Y", par.topMargin/(float)imageHeight);
        }
        intent.putExtra("exhibition", exhibition);
        intent.putExtra("sceneId", scene.getId());
        intent.putExtra("pointId", selectedPointId);
        
        EditSceneActivity.this.finish();
        v.getContext().startActivity(intent);

    }

    public void onClickSavePosition(View view){
        Storage.getInstance().saveExhibition(exhibition, this);
        Toast.makeText(this, R.string.saved_successfully, Toast.LENGTH_SHORT).show();
    }

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
                BottomNavigationView bnv = findViewById(R.id.edit_scene_bottom);
                // change color of button when pressed and dragged, and also change color of previously selected button
                if (v.getId() != selectedPoint.getId()) {
                    if (selectedPoint != null) selectedPoint.setBackgroundResource(R.drawable.pointbluepressed); // prev
                    selectedPointId = v.getId();
                }
                selectedPoint = (Button)v;
                v.setBackgroundResource(R.drawable.pointblue); // pressed
                Button btn2 = findViewById(R.id.button7);
                if (selectedPointId != null) {
                    Button btn = findViewById(R.id.button5);
                    btn.setText(R.string.edit_content);
                    btn2.setVisibility(View.VISIBLE);
                } else {
                    btn2.setVisibility(View.GONE);
                }

                bnv.setVisibility(View.VISIBLE);
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
            imageHeight =  v.getHeight();
            imageWidth =  v.getWidth();
            pointSize = imageHeight/12;
            RelativeLayout rl = findViewById(R.id.scene_relative_layout);

            // my classes
            int i = 0;
            for (Point point : scene.getPoints()) {
                Button btn = new Button(getApplicationContext());
                btn.setId(i);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pointSize, pointSize);
                params.setMargins((int)(point.getMarginX()*imageWidth), (int)(point.getMarginY()*imageHeight),0,0);
                btn.setOnTouchListener(EditSceneActivity.this);
                btn.setLayoutParams(params);
                btn.setBackgroundResource(R.drawable.pointbluepressed);

                rl.addView(btn);
                i++;
            }

            // does it really remove listener? Anyway without it app become very slow,
            // probably adding new Points on every layout related event, even on scrolling
            v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

}

