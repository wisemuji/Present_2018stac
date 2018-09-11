package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;

public class ptPlayActivity extends AppCompatActivity {
    private TimerTask mTask;
    private Timer mTimer;
    TextView myOutput;
    TextView ptTitle;
    TextView myRec;
    ImageButton btnLock;
    TextView myBtnStart;
    TextView myBtnRefresh;
    TextView btnFinish;
    Button myBtnRec;
    Vibrator vibe;
    ConstraintLayout layoutPlay;

    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    int myCount=1;
    long myBaseTime;
    long myPauseTime;
    int i=1;
    String str;
    Presentation pt;
    int osVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptplay);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        osVersion = Build.VERSION.SDK_INT;

        Intent intent = getIntent();
        pt = (Presentation) intent.getSerializableExtra("presentation");
//        Toast.makeText(getApplicationContext(),pt.getPresentTime().toString(),Toast.LENGTH_LONG).show();

        //테스트
//        ArrayList<Script> scripts=new ArrayList<>();
//        ArrayList<KeyPoint> keyPoints=new ArrayList<>();
//        keyPoints.add(0, new KeyPoint("테스트 키포인트", (long)2000));
//        pt = new Presentation("test",(long)60,true,true,true,true,scripts,keyPoints);

        myOutput = (TextView) findViewById(R.id.time_out);
        myRec = (TextView) findViewById(R.id.record);
        myBtnStart = (TextView) findViewById(R.id.btn_start);
        myBtnRefresh = (TextView) findViewById(R.id.btn_refresh);
        btnFinish = (TextView) findViewById(R.id.btn_destroy);
        ptTitle = (TextView) findViewById(R.id.pt_title);

        if(!pt.isDisplayTime()){
            myOutput.setVisibility(View.INVISIBLE);
        }
        if(!pt.isDisplayScript()){
            myRec.setVisibility(View.INVISIBLE);
        }

        ptTitle.setText(pt.getName());

        myBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnClick(v);
            }
        });

        myBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnClick(v);
            }
        });

        layoutPlay=findViewById(R.id.layout_play);
        btnLock=findViewById(R.id.btn_lock);
        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //잠그기
                if(btnFinish.isClickable()) {
                    btnFinish.setClickable(false);
                    myBtnStart.setClickable(false);
                    myBtnRefresh.setClickable(false);
                    btnLock.setImageResource(R.drawable.lock_closed);
                    Toast.makeText(getApplicationContext(), "잠금 모드가 활성화되었습니다.", Toast.LENGTH_LONG);

                    //Immersive full screen mode[[
                    if (osVersion >= 19){
                        try {
                            hideSystemUi();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(
                                new View.OnSystemUiVisibilityChangeListener(){
                                    @Override
                                    public void onSystemUiVisibilityChange(int visibility){
                                        if (visibility == 0){
                                            mHideHandler.postDelayed(mHideRunnable, 3000);
                                        }
                                    }
                                });
                    }
                    //Immersive full screen mode]]
                }
                //잠금 풀기
                else {
                    btnFinish.setClickable(true);
                    myBtnStart.setClickable(true);
                    myBtnRefresh.setClickable(true);
                    btnLock.setImageResource(R.drawable.lock_opened);
                    Toast.makeText(getApplicationContext(), "잠금 모드가 해제되었습니다.", Toast.LENGTH_LONG);
                    //Immersive full screen mode[[
                    if (osVersion >= 19){
                        try {
                            hideSystemUi();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    //Immersive full screen mode]]
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(btnFinish.isClickable()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        if(btnFinish.isClickable()) {
            super.onUserLeaveHint();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void myOnClick(View v){
        switch(v.getId()){
            case R.id.btn_start: //시작버튼을 클릭했을때 현재 상태값에 따라 다른 동작을 할수있게끔 구현.
                switch(cur_Status){
                    case Init:
                        myTimer = new Handler(){
                            public void handleMessage(Message msg){
                                myOutput.setText(getTimeOut());
                                //sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송하는겁니다.
                                myTimer.sendEmptyMessage(0);
                            }
                        };
                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime);
                        //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                        myTimer.sendEmptyMessage(0);
                        myBtnStart.setText("일시정지"); //버튼의 문자"시작"을 "멈춤"으로 변경
                        cur_Status = Run; //현재상태를 런상태로 변경
                        break;
                    case Run:
                        myTimer.removeMessages(0); //핸들러 메세지 제거
                        myPauseTime = SystemClock.elapsedRealtime();
                        myBtnStart.setText("시작");
                        cur_Status = Pause;

                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now- myPauseTime);
                        myBtnStart.setText("일시정지");
                        cur_Status = Run;
                        break;
                }
                break;
            case R.id.btn_refresh: //시작버튼을 클릭했을때 현재 상태값에 따라 다른 동작을 할수있게끔 구현.
                myTimer.removeMessages(0); //핸들러 메세지 제거
                myBtnStart.setText("시작");
                myOutput.setText("00:00");
//                myRec.setText("STAC 발표\nPRE-SENT");
                cur_Status = Init;
                break;

        }
    }

    Handler myTimer = new Handler(){
        public void handleMessage(Message msg){
            myOutput.setText(getTimeOut());
            //sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송하는겁니다.
            myTimer.sendEmptyMessage(0);
        }
    };

    //현재시간을 계속 구해서 출력하는 메소드
    String getTimeOut(){
        vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long now = SystemClock.elapsedRealtime(); //애플리케이션이 실행되고나서 실제로 경과된 시간(??)^^;
        long outTime = now - myBaseTime;
        String easy_outTime;
        if(outTime >= 3600000)
            easy_outTime = String.format("%02d:%02d:%02d", (outTime/1000) / 3600, ((outTime/1000) % 3600) / 60, (outTime/1000) % 60);
        else
            easy_outTime = String.format("%02d:%02d", outTime/1000 / 60, (outTime/1000)%60);
        String minute = String.format("%02d", outTime/1000 / 60);
        String second = String.format("%02d", (outTime/1000)%60);

        if(outTime/1000 == pt.getPresentTime()/1000){
            myTimer=new Handler(){
                public void handleMessage(Message msg){

                }
            };
            Toast.makeText(getApplicationContext(),"PT가 종료되었습니다.",Toast.LENGTH_LONG).show();
            myBtnStart.setText("재시작");
            outTime=0;
            cur_Status=Init;
        }

        for(KeyPoint kp : pt.getKeyPoints()){
            if((outTime/1000) == (kp.getVibTime()/1000)){
                myRec.setText(kp.getName());
                if(pt.isVibPhone()) {
                    vibe.vibrate(1000);
                }
            }
        }
//
//        if(minute.equals("04") && Integer.parseInt(second)>19 && Integer.parseInt(second)<40) {
//            myRec.setText("큰일!!!!!!!마무리!!!");
//            if (Integer.parseInt(second) == 20) {
//                vibe.vibrate(1000);
//            }
//        }

//        ArrayList<Script> scr= new ArrayList<>();
//        scr.add(new Script((long)1000, (long)4000, "안녕 테스트"));
//        pt.setScripts(scr);

        for(Script sc : pt.getScripts()){
            if((outTime/1000) >= (sc.getStartTime()/1000) && (outTime/1000) <= (sc.getEndTime()/1000)){
                myRec.setText(sc.getContent());
            } else {
                myRec.setText("");
            }
        }

        return easy_outTime;
    }

    public void appFinish(View v){
        finish();
    }

    //Immersive full screen mode[[
    private void hideSystemUi() {
        if(!btnFinish.isClickable()) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            try {
                hideSystemUi();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };
}