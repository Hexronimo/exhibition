package ru.hexronimo.andriod.exhibition;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Scene;
import ru.hexronimo.andriod.exhibition.model.Storage;

import static java.security.AccessController.getContext;

public class AddSceneActivity extends AppCompatActivity {
    private static final int READ_REQUEST_CODE_IMG = 2;
    private static final int READ_REQUEST_CODE_TXT = 1;
    private Uri image = null;
    private Exhibition exhibition;
    private List<Scene> scenes;
    private Scene scene;
    private int left;
    private int right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        exhibition = (Exhibition) i.getSerializableExtra("exhibition");
        scene = (Scene) i.getSerializableExtra("scene");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_add_scene);

        scenes = new ArrayList<>();
        if (null != exhibition.getScenes()) scenes.addAll(exhibition.getScenes().values());
        if (scene != null) {
            image = scene.getImagePath();
            int iToRemove = 0;
            for (Scene s : scenes){
                System.out.println(scene.getId() + "      -     " + s.getId());
                if (scene.getId().equals(s.getId())) break;
                iToRemove++;
            }
            scenes.remove(iToRemove);
        }


        // for Spinners
        ArrayList<String> scenesNames = new ArrayList<>();
        scenesNames.add(getString(R.string.no));

        if (exhibition != null) {
            for (Scene s : scenes) {
                scenesNames.add(s.getTitle());
            }
        }
        //fill form if it's editing existing scene
        if (scene != null) {
            TextView name = findViewById(R.id.scene_name);
            name.setText(scene.getTitle());
            TextView desc = findViewById(R.id.scene_desc);
            desc.setText(scene.getInfo());
            ImageView scenePic = findViewById(R.id.scene_pic);
            scenePic.setImageURI(scene.getImagePath());
        }


        Spinner spinnerLeft = findViewById(R.id.spinner_left);
        Spinner spinnerRight = findViewById(R.id.spinner_right);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this,   android.R.layout.simple_spinner_item, scenesNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerLeft.setAdapter(spinnerArrayAdapter);
        spinnerRight.setAdapter(spinnerArrayAdapter);
        if (scene != null) {
            if (scene.getLeft() != -1) {
                int spinnerPosition = spinnerArrayAdapter.getPosition(exhibition.getScenes().get(scene.getLeft()).getTitle());
                spinnerLeft.setSelection(spinnerPosition);
            }
            if (scene.getRight() != -1){
                int spinnerPosition = spinnerArrayAdapter.getPosition(exhibition.getScenes().get(scene.getRight()).getTitle());
                spinnerRight.setSelection(spinnerPosition);
            }
        }

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

    public void onClickClose(View v){
        Intent intent = new Intent(this, AddExhibitionActivity.class);
        intent.putExtras(getIntent());
        this.startActivity(intent);
        AddSceneActivity.this.finish();
    }

    public void onClickLoadText(View v) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE_TXT);
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

        startActivityForResult(intent, READ_REQUEST_CODE_IMG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==READ_REQUEST_CODE_TXT && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            TextView textView = findViewById(R.id.scene_desc);
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(uri);
                StringBuilder dataText = new StringBuilder();
                while(is.available() > 0) {
                    dataText.append((char)is.read());
                }
                textView.setText(dataText.toString());
            } catch(IOException e) {

            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if(requestCode==READ_REQUEST_CODE_IMG && resultCode == Activity.RESULT_OK) {
            image = data.getData();
            //System.out.println(image.toString());
            ImageView imageView = findViewById(R.id.scene_pic);
            imageView.setImageURI(image);
        }
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

        if (scene == null) {
            scene = new Scene(image.toString(), title, desc);
        } else {
            scene.setInfo(desc);
            scene.setTitle(title);
            scene.setImagePath(image);
        }

        scene.setLeft(left);
        scene.setRight(right);
        int sceneId  = exhibition.addScene(scene);

        if (left != -1) {
            exhibition.getScenes().get(left).setRight(sceneId);
        }
        if (right != -1) {
            exhibition.getScenes().get(right).setLeft(sceneId);
        }
        Storage.saveExhibition(exhibition);
        Toast.makeText(this, R.string.saved_successfully, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(view.getContext(), EditSceneActivity.class);
        intent.putExtra("exhibition", exhibition);
        intent.putExtra("sceneId", sceneId);
        view.getContext().startActivity(intent);
        AddSceneActivity.this.finish();
    }

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (parent.getId() == R.id.spinner_left) {
                if (id == 0) left = -1;
                else {
                    left = scenes.get((int)id-1).getId(); // id-1 because 0 is occupied by "NO"
                }
            }

            if (parent.getId() == R.id.spinner_right) {
                if (id == 0) right = -1;
                else {
                    right = scenes.get((int)id-1).getId();
                }
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
                if (parent.getId() == R.id.spinner_right) {
                    if (scene != null) right = scene.getRight(); else right = -1;
                }
                if (parent.getId() == R.id.spinner_left) {
                    if (scene != null) left = scene.getLeft(); else left = -1;
                }
        }
    }


}
