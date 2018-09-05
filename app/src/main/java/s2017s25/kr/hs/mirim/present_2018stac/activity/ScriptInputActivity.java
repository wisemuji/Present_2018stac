package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    ScriptListAdapter adapter;
    ListView listView;
    ScriptListAdapter ScriptListAdapter;
    ArrayList<script_list_item> list_itemArrayList;
    TextView nextBtn, prevBtn, exitBtn;
    Presentation pt;
    String keypointTitle, keypointTime, scriptStartTime, scriptEndTime, scriptContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_input);
        adapter = new ScriptListAdapter();
        listView = (ListView) findViewById(R.id.listview522);
        listView.setAdapter(adapter);

        final CharSequence[] items = {"스크립트", "키포인트"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

        Intent intent = getIntent();
        pt = (Presentation) intent.getSerializableExtra("presentation");


        /*
       adapter.addItem("00:00","00:00","안녕하십니까. 저희는 뜨거운 감자 팀입니다. 지금부터 발표 시작하겠습니다.");
        adapter.addItem("그래서 엑소 컴백 언제", "00:00");
        */


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
                                        startActivityForResult(intent,1);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case 0:
                    pt.getScripts().add((Script) data.getSerializableExtra("script"));
                    int i = pt.getScripts().size() - 1;

                    scriptStartTime = pt.getScripts().get(i).getStartTime().toString();
                    scriptEndTime = pt.getScripts().get(i).getEndTime().toString();
                    scriptContent = pt.getScripts().get(i).getContent();

                    adapter.addItem(scriptStartTime, scriptEndTime, scriptContent);
                    break;

                case 1:
                    pt.getKeyPoints().add((KeyPoint)data.getSerializableExtra("keyPoint"));

                    i = pt.getKeyPoints().size() - 1;

                    keypointTitle = pt.getKeyPoints().get(i).getName();
                    keypointTime = pt.getKeyPoints().get(i).getVibTime().toString();

                    adapter.addItem(keypointTitle, keypointTime);
                    break;

        }
    }
}
