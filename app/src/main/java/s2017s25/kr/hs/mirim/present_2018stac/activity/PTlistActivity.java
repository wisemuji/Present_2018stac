package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import s2017s25.kr.hs.mirim.present_2018stac.db.DBHelper;
import s2017s25.kr.hs.mirim.present_2018stac.Adapter.PTListAdapter;
import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.item.pt_list_item;

public class PTlistActivity extends AppCompatActivity {
    private Animation animShow, animHide, animSlideUp;
    TextView exitBtn;
    ListView listView;
    PTListAdapter myListAdapter;
    ArrayList<pt_list_item> list_itemArrayList;
    DBHelper dbHelper;
    DataClient dataClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptlist);

//        ImageView plusbtn = (ImageView) findViewById(R.id.plusbtn);
        dbHelper = new DBHelper(getApplicationContext(), "Presentation.db", null, 1);

        dataClient = Wearable.getDataClient(this);
        listView = (ListView)findViewById(R.id.my_listView);

        listRefresh();

        animShow = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.view_hide);
        animSlideUp = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.view_slide_up);

        exitBtn = (TextView) findViewById(R.id.exitBtn);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> arg0, final View view, int position, long id) {
                        final View menu=view.findViewById(R.id.item_expend);
                        ImageView option=view.findViewById(R.id.option);

                        view.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView t=(TextView)view.findViewById(R.id.title_textView);
                                Presentation pt = dbHelper.getPresentation(t.getText().toString());
                                Intent intent = new Intent(PTlistActivity.this, StopwatchActivity.class);
                                intent.putExtra("presentation", pt);
                                intent.putExtra("mode", "modify");
                                finish();
                                startActivity(intent);
                            }
                        });

                        view.findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView t=(TextView)view.findViewById(R.id.title_textView);
                                Presentation pt = dbHelper.getPresentation(t.getText().toString());
                                Intent intent = new Intent(PTlistActivity.this, ptPlayActivity.class);
                                intent.putExtra("presentation", pt);

                                // 워치랑 연결

                                // 보낼 데이터 생성
                                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/present");
                                DataMap dataMap = putDataMapRequest.getDataMap();
                                dataMap.putString("name", pt.getName());
                                dataMap.putLong("time",pt.getPresentTime());
                                ArrayList<KeyPoint> keyPoints=pt.getKeyPoints();

                                int keyPointsSize = keyPoints.size();
                                String[] keyPointNames = new String[keyPointsSize];
                                long[] keyPointTimes = new long[keyPointsSize];
                                for(int i=0;i<keyPointsSize;i++){
                                    keyPointNames[i]=keyPoints.get(i).getName();
                                    keyPointTimes[i]=keyPoints.get(i).getVibTime();
                                }
                                dataMap.putStringArray("keypointnames", keyPointNames);
                                dataMap.putLongArray("keypointtimes", keyPointTimes);

                                // 데이터 전송
                                PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
                                Task<DataItem> task = dataClient.putDataItem(putDataRequest);

                                // 리스너 등록(안해도됨)
                                task.addOnCompleteListener(new OnCompleteListener<DataItem>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataItem> task) {
                                        Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                task.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "전송 실패!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                startActivity(intent);
                            }
                        });

                        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PTlistActivity.this);
                                alertDialogBuilder.setTitle("PT삭제");
                                alertDialogBuilder
                                        .setMessage("선택한 PT를 삭제하시겠습니까?")
                                        .setPositiveButton("삭제",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        TextView v=(TextView)view.findViewById(R.id.title_textView);
                                                        Presentation pt = dbHelper.getPresentation(v.getText().toString());
                                                        dbHelper.delete(pt.getId());
                                                        listRefresh();
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
                            }
                        });

                        if(menu.getVisibility() == View.GONE) {
                            option.setImageResource(R.drawable.option1);
                            menu.setVisibility(View.VISIBLE);
                            menu.startAnimation(animShow);
                            while(listView.getChildAt(++position)!=null){
                                listView.getChildAt(position).startAnimation(animShow);
                            }

                        }
                        else {
                            option.setImageResource(R.drawable.option2);
                            animHide.setAnimationListener(new Animation.AnimationListener(){
                                @Override
                                public void onAnimationStart(Animation arg0) {
                                }
                                @Override
                                public void onAnimationRepeat(Animation arg0) {
                                }
                                @Override
                                public void onAnimationEnd(Animation arg0) {
                                    menu.setVisibility(View.GONE);
                                }
                            });
                            menu.startAnimation( animHide );
                            while(listView.getChildAt(++position)!=null){
                                listView.getChildAt(position).startAnimation(animHide);
                            }
                        }
                    }
                }
        );
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PTlistActivity.this);
                alertDialogBuilder.setTitle("PT삭제");
                alertDialogBuilder
                        .setMessage("선택한 PT를 삭제하시겠습니까?")
                        .setPositiveButton("삭제",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        TextView v=(TextView)view.findViewById(R.id.title_textView);
                                        Presentation pt = dbHelper.getPresentation(v.getText().toString());
                                        dbHelper.delete(pt.getId());
                                        listRefresh();
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

        ImageView inforBtn = findViewById(R.id.inforBtn);
        inforBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PTlistActivity.this, AppInfo.class);
                startActivity(intent);
            }
        });
    }

    void listRefresh(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);

        ArrayList<Presentation> ptList;
        ptList = dbHelper.getResult();
        list_itemArrayList = new ArrayList<pt_list_item>();

        DateFormat df = new SimpleDateFormat("mm:ss"); // HH=24h, hh=12h
        for(Presentation ptTmp : ptList){
            if(ptTmp.getPresentTime()>=3600000){
                df = new SimpleDateFormat("hh:mm:ss");
            } else {
                df = new SimpleDateFormat("mm:ss");
            }
            list_itemArrayList.add(new pt_list_item(ptTmp.getName(),df.format(ptTmp.getPresentTime()),formatter.format(ptTmp.getModifiedDate()),R.drawable.option2,R.drawable.edit1,R.drawable.play1,R.drawable.delete1));
        }

        myListAdapter = new PTListAdapter(PTlistActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);
    }

}
