package ru.hexronimo.andriod.exhibition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.hexronimo.andriod.exhibition.model.ContentLayouts;

public class ChooseContentType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_contents);

        GridView gridView = (GridView)findViewById(R.id.gridview);
        CustomGridViewAdapter booksAdapter = new CustomGridViewAdapter(this);
        gridView.setAdapter(booksAdapter);

    }

    public class CustomGridViewAdapter extends BaseAdapter implements View.OnClickListener {

        public Integer[] imgThumbs = {
                R.drawable.l05, R.drawable.l06,
                R.drawable.l01, R.drawable.l02,
                R.drawable.l03, R.drawable.l04,
                R.drawable.l07, R.drawable.l08
        };
        public String[] imgNames = {
                "Text", "Text (also good for poetry)",
                "Image with text", "Small image with text",
                "Fullscreen image with text", "Image as background with text above it",
                "Audio with text", "Video with text"
        };

        private Context mContext;

        // Constructor
        public CustomGridViewAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return imgThumbs.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_grid_item_image_text, null);

            final ImageView imageView = (ImageView)convertView.findViewById(R.id.grid_image);
            final TextView textView = (TextView)convertView.findViewById(R.id.grid_text);
            imageView.setImageDrawable(getResources().getDrawable(imgThumbs[position]));
            imageView.setId(position);

            imageView.setOnClickListener(this);
            textView.setText(imgNames[position]);
            return convertView;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), AddContentActivity.class);
            intent.putExtras(getIntent());

            switch(view.getId()){
                case 0 : intent.putExtra("content_layout", ContentLayouts.TEXT);
                    break;
                case 1: intent.putExtra("content_layout", ContentLayouts.POETRY);
                    break;
                case 2: intent.putExtra("content_layout", ContentLayouts.IMAGE_WITH_TEXT);
                    break;
                case 3: intent.putExtra("content_layout", ContentLayouts.SMALL_IMAGE_WITH_TEXT);
                    break;
                case 4: intent.putExtra("content_layout", ContentLayouts.FULL_SCREEN_IMAGE_WITH_TEXT);
                    break;
                case 5: intent.putExtra("content_layout", ContentLayouts.IMAGE_AS_BG_WITH_TEXT);
                    break;
                case 6: intent.putExtra("content_layout", ContentLayouts.AUDIO);
                    break;
                default: intent.putExtra("content_layout", ContentLayouts.VIDEO);

            }
            mContext.startActivity(intent);
            ChooseContentType.this.finish();
        }
    }
}
