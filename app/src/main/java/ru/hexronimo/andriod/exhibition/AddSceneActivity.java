package ru.hexronimo.andriod.exhibition;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ru.hexronimo.andriod.exhibition.model.Content;
import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Scene;

public class AddSceneActivity extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 6;
    private Uri image = null;
    private Exhibition exhibition;
    private List<Scene> scenes;
    private Integer left;
    private Integer right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        exhibition = (Exhibition) i.getSerializableExtra("exhibition");

        setContentView(R.layout.activity_add_scene);

        scenes = new ArrayList<>();
        if (null != exhibition.getExhibition()) scenes.addAll(exhibition.getExhibition().values());

        // for Spinners
        ArrayList<String> scenesNames = new ArrayList<>();
        scenesNames.add(getString(R.string.no));

        if (exhibition != null) {
            for (Scene s : scenes) {
                scenesNames.add(s.getTitle());
            }
        }

        Spinner spinnerLeft = findViewById(R.id.spinner_left);
        Spinner spinnerRight = findViewById(R.id.spinner_right);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this,   android.R.layout.simple_spinner_item, scenesNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerLeft.setAdapter(spinnerArrayAdapter);
        spinnerRight.setAdapter(spinnerArrayAdapter);

        spinnerLeft.setOnItemSelectedListener(new SpinnerActivity());
        spinnerRight.setOnItemSelectedListener(new SpinnerActivity());

        TextView exhibitionName = findViewById(R.id.exhibition_name);
        exhibitionName.setText(exhibition.getName());

        // change screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);
        int width = size.x;

        // setting main container width
        LinearLayout linearLayout = findViewById(R.id.linear_for_all);
        if (linearLayout != null) {
            int globalwidth;
            if (width > 2000) globalwidth = width/3*2;
            else if (width < 1000) globalwidth = width;
            else globalwidth = width/4*3;
            ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
            params.width = globalwidth;
            linearLayout.setLayoutParams(params);
        }
    }



    public void onClick(View view){
        //it was copied from Android sdk site, I left comments to not forget how to use it later

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

    public void onClickSubmit(View view){
        TextView titleTV = findViewById(R.id.scene_name);
        String title = titleTV.getText().toString();
        TextView validation1 = findViewById(R.id.validate_name);
        TextView validation2 = findViewById(R.id.validate_picture);
        if (null == title || title.trim().length() == 0) {
            validation1.setVisibility(View.VISIBLE);
            return;
        } else validation1.setVisibility(View.GONE);
        if (null == image || image.toString().trim().length() == 0) {
            validation2.setVisibility(View.VISIBLE);
            return;
        } else validation1.setVisibility(View.GONE);
        TextView descTV = findViewById(R.id.scene_desc);
        String desc = descTV.getText().toString();
        Scene scene = new Scene(image.toString(), title, desc);

        scene.setLeft(left);
        scene.setRight(right);

        exhibition.addScene(scene);
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


    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            if (parent.getId() == R.id.spinner_left) {
                if (id == 0) left = null;
                else {
                    left = scenes.get((int)id-1).getId(); // id-1 because 0 is occupied by "NO"
                }
            }

            if (parent.getId() == R.id.spinner_right) {
                if (id == 0) right = null;
                else {
                    right = scenes.get((int)id-1).getId();
                }
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
            if (parent.getId() == R.id.spinner_right) right = null;
            if (parent.getId() == R.id.spinner_left) left = null;
        }
    }


}
