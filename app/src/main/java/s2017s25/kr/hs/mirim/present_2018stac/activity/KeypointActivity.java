package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.Adapter.KeypointListAdapter;
import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.item.keypoint_list_item;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;

public class KeypointActivity extends AppCompatActivity {
    ListView listView;
    KeypointListAdapter KeypointListAdapter;
    ArrayList<keypoint_list_item> list_itemArrayList;
    TextView nextBtn, prevBtn,exitBtn;
    Presentation pt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypoint);

        Intent intent = getIntent();
        pt = (Presentation) intent.getSerializableExtra("presentation");

        listView = (ListView)findViewById(R.id.keypoint_listview);

        list_itemArrayList = new ArrayList<keypoint_list_item>();

        list_itemArrayList.add(
                new keypoint_list_item("다음슬라이드","00:00"));
        list_itemArrayList.add(
                new keypoint_list_item("다음 슬라이드","00:00"));
        list_itemArrayList.add(
                new keypoint_list_item("다음 슬라이드","00:00"));
        list_itemArrayList.add(
                new keypoint_list_item("다음 슬라이드","00:00"));
        list_itemArrayList.add(
                new keypoint_list_item("다음 슬라이드","00:00"));

        KeypointListAdapter = new KeypointListAdapter(KeypointActivity.this,list_itemArrayList);
        listView.setAdapter(KeypointListAdapter);

        nextBtn = (TextView) findViewById(R.id.keypoint_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KeypointActivity.this, SettingActivity.class);
                intent.putExtra("presentation", pt);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
            }
        });

        prevBtn = (TextView) findViewById(R.id.keypoint_prev_btn);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KeypointActivity.this, ScriptInputActivity.class);
                intent.putExtra("presentation", pt);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.activity_slide_enter, R.anim.activity_slide_exit);
            }
        });
        exitBtn = (TextView)findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
