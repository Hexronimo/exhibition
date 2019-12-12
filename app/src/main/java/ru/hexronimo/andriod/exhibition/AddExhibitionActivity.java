package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.hexronimo.andriod.exhibition.model.Exhibition;
import ru.hexronimo.andriod.exhibition.model.Scene;
import ru.hexronimo.andriod.exhibition.model.Storage;

public class AddExhibitionActivity extends AppCompatActivity {

    Exhibition exhibition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        exhibition = (Exhibition) i.getSerializableExtra("exhibition");
        setContentView(R.layout.activity_add_exhibition);
        if (exhibition != null) {
            TextView name = (TextView)findViewById(R.id.add_exhibition_name);
            //name.setText(exhibition.getName());

            ContactAdapter ca = new ContactAdapter();
            RecyclerView recView = (RecyclerView) findViewById(R.id.scenes_rec);
            recView.setAdapter(ca);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recView.setLayoutManager(llm);

        } else {
            exhibition = new Exhibition();
        }

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

    public void onClickNewScene(View view) {

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
        Intent intent = new Intent(view.getContext(), AddSceneActivity.class);
        intent.putExtra("exhibition", exhibition);
        view.getContext().startActivity(intent);
        AddExhibitionActivity.this.finish();
    }



    public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

        private List<Scene> sceneList;

        public ContactAdapter() {
            this.sceneList = new ArrayList(exhibition.getScenes().values());
        }

        @Override
        public int getItemCount() {
            return sceneList.size();
        }

        @Override
        public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
            contactViewHolder.vName.setText(sceneList.get(i).getTitle());
            contactViewHolder.vImage.setImageURI(sceneList.get(i).getImagePath());
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.layout_linear_scene_img_name, viewGroup, false);

            return new ContactViewHolder(itemView);
        }


        public class ContactViewHolder extends RecyclerView.ViewHolder {
            protected TextView vName;
            protected ImageView vImage;


            public ContactViewHolder(View v) {
                super(v);
                vName =  (TextView) v.findViewById(R.id.card_name);
                vImage =  (ImageView) v.findViewById(R.id.card_image);

            }
        }

    }




}
