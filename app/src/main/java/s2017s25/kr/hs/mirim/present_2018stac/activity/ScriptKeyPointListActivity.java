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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.Adapter.ScriptListAdapter;

import s2017s25.kr.hs.mirim.present_2018stac.db.DBHelper;

import s2017s25.kr.hs.mirim.present_2018stac.item.script_list_item;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;


public class ScriptKeyPointListActivity extends AppCompatActivity {
    ArrayList<KeyPoint> keyPoints;
    ArrayList<Script> scripts;
    ArrayList<Object> list_item;

    ScriptListAdapter adapter;
    ListView listView;
    ScriptListAdapter ScriptListAdapter;
    ArrayList<script_list_item> list_itemArrayList;
    TextView nextBtn, prevBtn, exitBtn;
    Presentation pt;
    String mode;

    DBHelper dbHelper;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_keypoint);

        dbHelper = new DBHelper(getApplicationContext(), "Presentation.db", null, 1);


        Intent intent = getIntent();
        pt = (Presentation) intent.getSerializableExtra("presentation");
        if(intent.getStringExtra("mode")!=null) {
            mode = intent.getStringExtra("mode");
        }


        list_item = new ArrayList<Object>();
        scripts=new ArrayList<Script>();
        keyPoints=new ArrayList<KeyPoint>();
        if(pt.getKeyPoints().size()!=0) {
            keyPoints = pt.getKeyPoints();
        }
        if(pt.getScripts().size()!=0) {
            scripts = pt.getScripts();
        }
        adapter = new ScriptListAdapter();
        listView = (ListView) findViewById(R.id.listview522);


        final CharSequence[] items = {"스크립트", "키포인트"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

        refresh();
        listView.setAdapter(adapter);


        ImageView itemSet = (ImageView) findViewById(R.id.item_set);
        itemSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("항목을 선택하세요")// 제목 설정
                        .setItems(items, new DialogInterface.OnClickListener(){    // 목록 클릭시 설정
                            public void onClick(DialogInterface dialog, int index){
                                switch (index){
                                    case 0:

                                        Intent intent = new Intent(ScriptKeyPointListActivity.this, ScriptContentInput.class);
                                        intent.putExtra("presentation", pt);
                                        startActivityForResult(intent,0);
                                        break;
                                    case 1:

                                        intent = new Intent(ScriptKeyPointListActivity.this, keypointInputActivity.class);
                                        intent.putExtra("presentation", pt);
                                        startActivityForResult(intent, 1);
                                        break;
                                }
                            }
                        });
                AlertDialog dialog = builder.create();    // 알림창 객체 생성

                dialog.show();    // 알림창 띄우기
            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScriptInputActivity.this);
                alertDialogBuilder.setTitle("항목 삭제");
                alertDialogBuilder
                        .setMessage("선택한 항목을 삭제하시겠습니까?")
                        .setPositiveButton("삭제",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        TextView v=(TextView)view.findViewById(R.id.title_textView);
                                        Presentation pt = dbHelper.getPresentation(v.getText().toString());
                                        dbHelper.delete(pt.getId());
                                        refresh();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });
                alertDialogBuilder.show();
                return true;
            }
        });



        nextBtn = (TextView) findViewById(R.id.script_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScriptKeyPointListActivity.this, SettingActivity.class);
                if(keyPoints.size()!=0) {
                    pt.setKeyPoints(keyPoints);
                }
                if(scripts.size()!=0) {
                    pt.setScripts(scripts);
                }
                intent.putExtra("presentation", pt);
                intent.putExtra("mode", mode);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
            }
        });

        prevBtn = (TextView) findViewById(R.id.script_prev_btn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScriptKeyPointListActivity.this, StopwatchActivity.class);
                if(keyPoints.size()!=0) {
                    pt.setKeyPoints(keyPoints);
                }
                if(scripts.size()!=0) {
                    pt.setScripts(scripts);
                }

                intent.putExtra("presentation", pt);
                intent.putExtra("mode", mode);
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
                    long StartSecond = (sc.getStartTime() / 1000) % 60;
                    long StartMinute = (sc.getStartTime() / (1000 * 60)) % 60;
                    long StartHour = (sc.getStartTime() / (1000 * 60*60)) % 100;

                    long endSecond = (sc.getEndTime() / 1000) % 60;
                    long endMinute = (sc.getEndTime() / (1000 * 60)) % 60;
                    long endHour = (sc.getEndTime() / (1000 * 60 * 60)) % 100;

                    String startTime = String.format("%02d:%02d:%02d",StartHour, StartMinute, StartSecond);
                    String endTime = String.format("%02d:%02d:%02d",endHour, endMinute, endSecond);

                    adapter=new ScriptListAdapter();
                    pt.setScripts(scripts);

                    refresh();
                    listView.setAdapter(adapter);
                    break;
                case 1:
                    KeyPoint key = (KeyPoint) data.getSerializableExtra("key");
                    keyPoints.add(key);

                    long keypointSecond = (key.getVibTime() / 1000) % 60;
                    long keypointMinute = (key.getVibTime() / (1000 * 60)) % 60;
                    long keypointHour = (key.getVibTime() / 1000*60*60) % 100;

                    String keypointTime = String.format("%02d:%02d:%02d",keypointHour, keypointMinute, keypointSecond);

                    adapter=new ScriptListAdapter();
                    pt.setKeyPoints(keyPoints);
                    refresh();
                    listView.setAdapter(adapter);

                    break;
            }
        }
    }

    public void refresh(){
        ArrayList<KeyPoint> key = pt.getKeyPoints();

        ArrayList<Script> sc = pt.getScripts();
        list_item=new ArrayList<>();
        if(key != null && key.size() != 0) {


            for (int i = 0; i < key.size(); i++) {

                list_item.add(key.get(i));
                //adapter.addItem(key.get(i).getName(), key.get(i).getVibTime().toString());
            }
        }

        if (sc != null && sc.size() != 0) {
            for (int i = 0; i < sc.size(); i++) {
                list_item.add(sc.get(i));
            }
        }

        if(list_item != null) {
            Collections.sort(list_item, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    long num1 = 0, num2 = 0;
                    if (o1.getClass().getSimpleName().contains("KeyPoint")) {
                        KeyPoint k = (KeyPoint) o1;
                        num1 = k.getVibTime();
                    } else if (o1.getClass().getSimpleName().contains("Script")) {
                        Script s = (Script) o1;
                        num1 = s.getStartTime();
                    } else {
                    }

                    if (o2.getClass().getSimpleName().contains("KeyPoint")) {
                        KeyPoint k = (KeyPoint) o2;
                        num2 = k.getVibTime();
                    } else if (o2.getClass().getSimpleName().contains("Script")) {
                        Script s = (Script) o2;
                        num2 = s.getStartTime();
                    } else {
                    }

                    return (int) (num1 - num2);
                }
            }); // 정렬

            for (int i = 0; i < list_item.size(); i++) {
                if (list_item.get(i).getClass().getSimpleName().contains("KeyPoint")) {
                    KeyPoint kk = (KeyPoint) list_item.get(i);

                    long keypointSecond = (kk.getVibTime() / 1000) % 60;
                    long keypointMinute = (kk.getVibTime() / (1000 * 60)) % 60;
                    long keypointHour = (kk.getVibTime() / (1000 * 60 * 60)) % 100;

                    String keypointTime = String.format("%02d:%02d:%02d", keypointHour, keypointMinute, keypointSecond);

                    adapter.addItem(kk.getName(), keypointTime);
                } else if (list_item.get(i).getClass().getSimpleName().contains("Script")) {
                    Script ss = (Script) list_item.get(i);

                    long StartSecond = (ss.getStartTime() / 1000) % 60;
                    long StartMinute = (ss.getStartTime() / (1000 * 60)) % 60;
                    long StartHour = (ss.getStartTime() / (1000 * 60 * 60)) % 100;

                    long endHour = (ss.getStartTime() / (1000 * 60 * 60)) % 100;
                    long endSecond = (ss.getEndTime() / 1000) % 60;
                    long endMinute = (ss.getEndTime() / (1000 * 60)) % 60;

                    String startTime = String.format("%02d:%02d:%02d", StartHour, StartMinute, StartSecond);
                    String endTime = String.format("%02d:%02d:%02d", endHour, endMinute, endSecond);

                    adapter.addItem(startTime, endTime, ss.getContent());
                }
            }
        }
    }
}
