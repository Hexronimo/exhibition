package ru.hexronimo.andriod.exhibition;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import ru.hexronimo.andriod.exhibition.model.Content;

public class AddSceneActivity extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 6;
    Uri image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scene);
        String colors[] = {"Red","Blue","White","Yellow","Black", "Green","Purple","Orange","Grey"};


        Spinner spinnerLeft = (Spinner) findViewById(R.id.spinner_left);
        Spinner spinnerRight = (Spinner) findViewById(R.id.spinner_right);
    }

    public void onClick(View view){
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            image = data.getData();
            //System.out.println(image.toString());
            ImageView imageView = findViewById(R.id.scene_pic);
            imageView.setImageURI(image);


        }
    }


    }
