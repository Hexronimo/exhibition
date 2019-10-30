package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.hexronimo.andriod.exhibition.model.Exhibition;

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
        }
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

    }

}
