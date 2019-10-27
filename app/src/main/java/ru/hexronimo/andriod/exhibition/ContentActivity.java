package ru.hexronimo.andriod.exhibition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.hexronimo.andriod.exhibition.model.Content;

public class ContentActivity extends AppCompatActivity {

    private static Content content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent i = getIntent();
        content = (Content)i.getSerializableExtra("content");
        setContentView(content.getLayout());

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


            TextView title = findViewById(R.id.content_title);
            TextView body = findViewById(R.id.content_body);
            ImageView imageView = findViewById(R.id.content_image);

        // if it's not only text
        if (content.getImagePath() != null) imageView.setVisibility(View.VISIBLE);

        // if is't layout with small image and text at right, we just make image 1/3 of screen
        if (content.getImagePath() != null && content.getLayout() == R.layout.activity_content_ver2) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = width/3;
        }

        if (content.getTitle() != null) {
            title.setVisibility(View.VISIBLE);
            title.setText(content.getTitle());
        }
        if (content.getTextContent() != null) body.setText(Html.fromHtml(content.getTextContent()));
        if (content.getImagePath() != null) imageView.setImageURI(content.getImagePath());

    }

    public void onClick(View v){
        content = null;
        ContentActivity.this.finish();
    }
}
