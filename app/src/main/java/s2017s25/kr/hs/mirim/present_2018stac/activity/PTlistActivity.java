package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import s2017s25.kr.hs.mirim.present_2018stac.db.DBHelper;
import s2017s25.kr.hs.mirim.present_2018stac.Adapter.PTListAdapter;
import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.item.pt_list_item;

public class PTlistActivity extends AppCompatActivity {
    private Animation animShow, animHide, animSlideUp;
    TextView exitBtn;
    ListView listView;
    PTListAdapter myListAdapter;
    ArrayList<pt_list_item> list_itemArrayList;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptlist);

//        ImageView plusbtn = (ImageView) findViewById(R.id.plusbtn);
        dbHelper = new DBHelper(getApplicationContext(), "Presentation.db", null, 1);

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
            }
            list_itemArrayList.add(new pt_list_item(ptTmp.getName(),df.format(ptTmp.getPresentTime()),formatter.format(ptTmp.getModifiedDate()),R.drawable.option2,R.drawable.edit1,R.drawable.play1,R.drawable.delete1));
        }

//        list_itemArrayList.add(new ptlist_list_item(R.drawable.start,"앱잼발표","5:00",R.drawable.menu));
//        list_itemArrayList.add(new ptlist_list_item(R.drawable.start,"앱 디자인 기획서 발표","3:00",R.drawable.menu));
        myListAdapter = new PTListAdapter(PTlistActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);
    }

}
