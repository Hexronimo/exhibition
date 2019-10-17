package ru.hexronimo.andriod.exhibition;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class PointOnTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Button view = (Button) v;
                v.setBackgroundResource(R.drawable.pointblue);
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:
                // Your action here on button click
            case MotionEvent.ACTION_CANCEL: {
                Button view = (Button) v;
                v.setBackgroundResource(R.drawable.pointbluepressed);
                view.invalidate();
                break;
            }
        }
        return false;
    }
}
