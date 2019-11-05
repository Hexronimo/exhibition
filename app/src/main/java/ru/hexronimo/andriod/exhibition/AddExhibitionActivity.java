package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Storage;

public class AddExhibitionActivity extends AppCompatActivity {

    Exhibition exhibition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        exhibition = (Exhibition) i.getSerializableExtra("exhibition");
        if (exhibition != null) {
            TextView name = findViewById(R.id.exhibition_name);
            name.setText(exhibition.getName());
        } else {
            exhibition = new Exhibition();
        }
        setContentView(R.layout.activity_add_exhibition);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);
        int width = size.x;

        int globalwidth;
        if (width > 2000) globalwidth = width/3*2;
        else if (width < 1000) globalwidth = width;
        else globalwidth = width/4*3;

        LinearLayout linearLayout = findViewById(R.id.linear_for_all);
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        params.width = globalwidth;
    }

    public void onClick(View view) {
        EditText name = findViewById(R.id.add_exhibition_name);
        String nametxt = name.getText().toString();
        if (null == nametxt || nametxt.trim().length() == 0) {
            TextView validation = findViewById(R.id.validation_exh_name);
            validation.setVisibility(View.VISIBLE);
            return;
        } else {
            exhibition.setName(nametxt);

        }
        exhibition.setId(Storage.getInstance().saveExhibition(exhibition, this));
        Toast.makeText(this, R.string.saved_successfully, Toast.LENGTH_SHORT).show();
    }

    public void onClickNewScene(View view) {
        onClick(view);
        Intent intent = new Intent(view.getContext(), AddSceneActivity.class);
        intent.putExtra("exhibition", exhibition);
        view.getContext().startActivity(intent);
        AddExhibitionActivity.this.finish();
    }

}
