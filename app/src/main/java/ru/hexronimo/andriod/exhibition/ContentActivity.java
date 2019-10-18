package ru.hexronimo.andriod.exhibition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ru.hexronimo.andriod.exhibition.model.Content;

public class ContentActivity extends AppCompatActivity {

    private static Content content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent i = getIntent();
        content = (Content)i.getSerializableExtra("content");

        TextView title = findViewById(R.id.content_title);
        TextView body = findViewById(R.id.content_body);

        title.setText(content.getTitle());
        body.setText(content.getTextContent());
    }
}
