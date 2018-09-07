package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.Adapter.ScriptListAdapter;
import s2017s25.kr.hs.mirim.present_2018stac.item.script_list_item;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;

public class ScriptInputActivity extends AppCompatActivity {
    ArrayList<KeyPoint> keyPoints;
    ArrayList<Script> scripts;

    ScriptListAdapter adapter;
    ListView listView;
    ScriptListAdapter ScriptListAdapter;
    ArrayList<script_list_item> list_itemArrayList;
    TextView nextBtn, prevBtn, exitBtn;
    Presentation pt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_input);

        scripts = new ArrayList<Script>();
        keyPoints = new ArrayList<KeyPoint>();
        adapter = new ScriptListAdapter();
        listView = (ListView) findViewById(R.id.listview522);
        listView.setAdapter(adapter);

        final CharSequence[] items = {"스크립트", "키포인트"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

        Intent intent = getIntent();

        pt = (Presentation) intent.getSerializableExtra("presentation");

        ArrayList<KeyPoint> key = pt.getKeyPoints();
        ArrayList<Script> sc = pt.getScripts();

       if(key != null && key.size() != 0) {

               for (int i = 0; i < key.size(); i++) {
                   adapter.addItem(key.get(i).getName(), key.get(i).getVibTime().toString());
               }
           }

            if (sc != null && sc.size() != 0) {
                for (int i = 0; i < sc.size(); i++) {
                    adapter.addItem(sc.get(i).getStartTime().toString(), sc.get(i).getEndTime().toString(),
                            sc.get(i).getContent());
                }
            }

        ImageView itemSet = (ImageView) findViewById(R.id.item_set);
        itemSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("항목을 선택하세요")// 제목 설정
                        .setItems(items, new DialogInterface.OnClickListener(){    // 목록 클릭시 설정
                            public void onClick(DialogInterface dialog, int index){
                                switch (index){
                                    case 0:
                                        Intent intent = new Intent(ScriptInputActivity.this, ScriptContentInput.class);
                                        startActivityForResult(intent,0);
                                        break;
                                    case 1:
                                        intent = new Intent(ScriptInputActivity.this, keypointInputActivity.class);
                                        startActivityForResult(intent, 1);
                                        break;
                                }
                            }
                        });
                AlertDialog dialog = builder.create();    // 알림창 객체 생성

                dialog.show();    // 알림창 띄우기
            }
        });


        nextBtn = (TextView) findViewById(R.id.script_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScriptInputActivity.this, SettingActivity.class);
                pt.setKeyPoints(keyPoints);
                pt.setScripts(scripts);
                intent.putExtra("presentation", pt);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
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
                overridePendingTransition(R.anim.activity_slide_enter, R.anim.activity_slide_exit);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 0:
                    Script sc = (Script) data.getSerializableExtra("script");
                    scripts.add(sc);

                    adapter.addItem(sc.getStartTime().toString(), sc.getEndTime().toString(), sc.getContent());
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    KeyPoint key = (KeyPoint) data.getSerializableExtra("key");
                    Log.e("pttest", key.getName());
                        keyPoints.add(key);

                        adapter.addItem(key.getName(), key.getVibTime().toString());
                        adapter.notifyDataSetChanged();

                    break;
            }
        }
    }
}
