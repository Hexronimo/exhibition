package ru.hexronimo.andriod.exhibition;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.hexronimo.andriod.exhibition.model.Storage;

public class ListExhibitionsActivity extends AppCompatActivity {
    private int cardSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        setContentView(R.layout.activity_exhibitions_list);
        ContactAdapter ca = new ContactAdapter();
        RecyclerView recView = (RecyclerView) findViewById(R.id.scenes_rec);
        recView.setAdapter(ca);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(llm);

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
        Intent intent = new Intent(view.getContext(), AddExhibitionActivity.class);
        view.getContext().startActivity(intent);
        ListExhibitionsActivity.this.finish();
    }

    public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

        private Map<String, String[]> exhs;
        private List<String> ids;

        public ContactAdapter() {
            this.exhs = new HashMap();
            exhs = Storage.getAllExhibitions();
            System.out.println(exhs.size() + " exhibitions found.");
            ids = new ArrayList<>();
            ids.addAll(exhs.keySet());
        }

        @Override
        public int getItemCount() {
            TextView textView = findViewById(R.id.exh_info_text);
            textView.setText(getString(R.string.text_exh_info_part_1, exhs.size()));
            return exhs.size();
        }

        @Override
        public void onBindViewHolder(final ContactViewHolder contactViewHolder, final int i) {
            contactViewHolder.vName.setText(exhs.get(ids.get(i))[0]);
            contactViewHolder.vBar.setId(i);
            try {
                contactViewHolder.vImage.setImageURI(Uri.parse(exhs.get(ids.get(i))[1]));
            } catch (Exception e) {e.printStackTrace();}
            contactViewHolder.vMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contactViewHolder.vBar.getVisibility() == View.GONE) {
                        contactViewHolder.vBar.setVisibility(View.VISIBLE);
                    } else {
                        contactViewHolder.vBar.setVisibility(View.GONE);
                    }
                    if (cardSelected != -1 & cardSelected != i) {
                        LinearLayout l = findViewById(cardSelected);
                        l.setVisibility(View.GONE);
                    }
                    cardSelected = i;
                }
            });

            contactViewHolder.vDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Deleted " + exhs.get(ids.get(i))[0]);
                    Storage.deleteExhibition(ids.get(i));
                    recreate();
                }
            });
            contactViewHolder.vEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AddExhibitionActivity.class);
                    intent.putExtra("exhibition", Storage.getExhibition(ids.get(i)));
                    view.getContext().startActivity(intent);
                    ListExhibitionsActivity.this.finish();
                }
            });
            contactViewHolder.vRun.setVisibility(View.VISIBLE);
            contactViewHolder.vRun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), SceneActivity.class);
                    intent.putExtra("exhibition", Storage.getExhibition(ids.get(i)));
                    view.getContext().startActivity(intent);
                    ListExhibitionsActivity.this.finish();
                }
            });
            contactViewHolder.countScenes.setText(getString(R.string.count_scenes, exhs.get(ids.get(i))[2]));
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            final int j = i;
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.layout_linear_scene_img_name, viewGroup, false);
            return new ContactViewHolder(itemView);
        }


        public class ContactViewHolder extends RecyclerView.ViewHolder {
            protected TextView vName;
            protected ImageView vImage;
            protected TextView countScenes;
            protected Button vEdit;
            protected Button vRun;
            protected Button vDelete;
            protected Button vMenu;
            protected LinearLayout vBar;


            public ContactViewHolder(View v) {
                super(v);
                vName =  (TextView) v.findViewById(R.id.card_name);
                vImage =  (ImageView) v.findViewById(R.id.card_image);
                vDelete = v.findViewById(R.id.card_delete);
                countScenes = v.findViewById(R.id.count_scenes_txt);
                vEdit = v.findViewById(R.id.exh_edit_btn);
                vRun = v.findViewById(R.id.exh_run_btn);
                vMenu = v.findViewById(R.id.card_button_menu);
                vBar = v.findViewById(R.id.bar_with_buttons);
            }
        }

    }




}
