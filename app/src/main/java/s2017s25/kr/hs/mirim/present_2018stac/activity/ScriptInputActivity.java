package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.Adapter.ScriptListAdapter;
import s2017s25.kr.hs.mirim.present_2018stac.item.script_list_item;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;

public class ScriptInputActivity extends AppCompatActivity {

    ListView listView;
    ScriptListAdapter ScriptListAdapter;
    ArrayList<script_list_item> list_itemArrayList;
    TextView nextBtn, prevBtn, exitBtn;
    Presentation pt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_script_input);

        Intent intent = getIntent();
        pt = (Presentation) intent.getSerializableExtra("presentation");

        listView = (ListView)findViewById(R.id.script_listview);

        list_itemArrayList = new ArrayList<script_list_item>();

        list_itemArrayList.add(
                new script_list_item("00:00 ~ 00:00","안녕하십니까. 저희는 뜨거운 감자 팀입니다. 지금부터 발표 시작하겠습니다."));

        list_itemArrayList.add(
                new script_list_item("00:10 ~ 01:00","안녕하십니까. 저희는 뜨거운 감자 팀입니다. 지금부터 발표 시작하겠습니다."));

        list_itemArrayList.add(
                new script_list_item("00:10 ~ 01:00","안녕하십니까. 저희는 뜨거운 감자 팀입니다. 지금부터 발표 시작하겠습니다."));

        list_itemArrayList.add(
                new script_list_item("00:10 ~ 01:00","안녕하십니까. 저희는 뜨거운 감자 팀입니다. 지금부터 발표 시작하겠습니다."));

        list_itemArrayList.add(
                new script_list_item("00:10 ~ 01:00","안녕하십니까. 저희는 뜨거운 감자 팀입니다. 지금부터 발표 시작하겠습니다."));

        list_itemArrayList.add(
                new script_list_item("00:10 ~ 01:00","안녕하십니까. 저희는 뜨거운 감자 팀입니다. 지금부터 발표 시작하겠습니다."));

        list_itemArrayList.add(
                new script_list_item("00:10 ~ 01:00","안녕하십니까. 저희는 뜨거운 감자 팀입니다. 지금부터 발표 시작하겠습니다."));
        //ListAdapter.notifyDataSetChanged();

        ScriptListAdapter = new ScriptListAdapter(ScriptInputActivity.this,list_itemArrayList);
        listView.setAdapter(ScriptListAdapter);


        nextBtn = (TextView) findViewById(R.id.script_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScriptInputActivity.this, KeypointActivity.class);
                intent.putExtra("presentation", pt);
                startActivity(intent);
                finish();
            }
        });

        prevBtn = (TextView) findViewById(R.id.script_prev_btn);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScriptInputActivity.this, StopwatchActivity.class);
                intent.putExtra("presentation", pt);
                startActivity(intent);
                finish();
            }
        });

        exitBtn = (TextView) findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
