package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.Adapter.KeypointListAdapter;
import s2017s25.kr.hs.mirim.present_2018stac.Adapter.keypoint_list_item;
import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;

public class KeypointActivity extends AppCompatActivity {
    ListView listView;
    s2017s25.kr.hs.mirim.present_2018stac.Adapter.KeypointListAdapter KeypointListAdapter;
    ArrayList<keypoint_list_item> list_itemArrayList;
    TextView nextBtn, prevBtn;
    Presentation presentation = new Presentation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypoint);

        Intent inforIntent = getIntent();
        presentation = (Presentation) inforIntent.getSerializableExtra("ScriptInputInfor");

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
                startActivity(intent);
                finish();
            }
        });

        prevBtn = (TextView) findViewById(R.id.keypoint_prev_btn);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KeypointActivity.this, ScriptInputActivity.class);
                intent.putExtra("KeypointInfor", presentation);
                startActivity(intent);
                finish();
            }
        });

    }
}
