package ru.hexronimo.andriod.exhibition;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import ru.hexronimo.andriod.exhibition.model.Point;

public class PointOnTouchListener implements View.OnTouchListener {

    private Point point;

    public void setPoint(Point point){
        this.point = point;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Button view = (Button) v;
                v.setBackgroundResource(R.drawable.pointbluepressed);
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:
                if (null != point.getContent()) {
                    Context c = v.getContext();
                    Intent intent;
                    if (null != point.getContent().getMediaPath()) {
                        intent = new Intent(c, ContentMediaActivity.class);
                    } else {
                        intent = new Intent(c, ContentActivity.class);
                    }
                    intent.putExtra("content", point.getContent());
                    c.startActivity(intent);
                }
                v.setBackgroundResource(R.drawable.pointblue);
                v.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL: {
                Button view = (Button) v;
                v.setBackgroundResource(R.drawable.pointblue);
                view.invalidate();
                break;
            }
        }
        return false;
    }
}
