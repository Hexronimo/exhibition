package ru.hexronimo.andriod.exhibition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import ru.hexronimo.andriod.exhibition.model.Storage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Storage.getInstance(this);
        Intent intent = new Intent(this, ListExhibitionsActivity.class);
        startActivity(intent);

    }
}
