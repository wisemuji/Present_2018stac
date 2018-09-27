package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

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
    TextView myTitle;
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

    DataClient dataClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptplay);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        osVersion = Build.VERSION.SDK_INT;

        Intent intent = getIntent();
        pt = (Presentation) intent.getSerializableExtra("presentation");
//        Toast.makeText(getApplicationContext(),pt.getPresentTime().toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"발표 중에는 화면이 꺼지지 않습니다.",Toast.LENGTH_LONG).show();
        dataClient = Wearable.getDataClient(this);

        myOutput = (TextView) findViewById(R.id.time_out);
        myRec = (TextView) findViewById(R.id.record);
        myTitle = (TextView) findViewById(R.id.sc_title);
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
                    Toast.makeText(getApplicationContext(), "잠금 모드가 활성화되었습니다.", Toast.LENGTH_LONG).show();

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
                    Toast.makeText(getApplicationContext(), "잠금 모드가 해제되었습니다.", Toast.LENGTH_LONG).show();
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
            appFinish(btnFinish);
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
                        if(pt.isVibSmartWatch()) {
                            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/play");
                            DataMap dataMap = putDataMapRequest.getDataMap();
                            dataMap.putInt("status", Init);
                            dataMap.putLong("dummy", System.currentTimeMillis()); //항상 새로운 값을 주기 위한 방법
                            // 데이터 전송
                            PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
                            Task<DataItem> task = dataClient.putDataItem(putDataRequest);
                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "스마트워치와의 연결이 불안정합니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        myTimer = new Handler(){
                            public void handleMessage(Message msg){
                                myOutput.setText(getTimeOut());
                                //sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송하는겁니다.
                                myTimer.sendEmptyMessage(0);
                            }
                        };
                        myRec.setText("");
                        myTitle.setText("");
                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime);
                        //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                        myTimer.sendEmptyMessage(0);
                        myBtnStart.setText("일시정지"); //버튼의 문자"시작"을 "멈춤"으로 변경
                        cur_Status = Run; //현재상태를 런상태로 변경
                        break;
                    case Run:
                        if(pt.isVibSmartWatch()) {
                            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/play");
                            DataMap dataMap = putDataMapRequest.getDataMap();
                            dataMap.putInt("status", Run);
                            dataMap.putLong("dummy", System.currentTimeMillis()); //항상 새로운 값을 주기 위한 방법
                            // 데이터 전송
                            PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
                            Task<DataItem> task = dataClient.putDataItem(putDataRequest);
                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "스마트워치와의 연결이 불안정합니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        myTimer.removeMessages(0); //핸들러 메세지 제거
                        myPauseTime = SystemClock.elapsedRealtime();
                        myBtnStart.setText("시작");
                        cur_Status = Pause;

                        break;
                    case Pause:
                        if(pt.isVibSmartWatch()) {
                            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/play");
                            DataMap dataMap = putDataMapRequest.getDataMap();
                            dataMap.putInt("status", Pause);
                            dataMap.putLong("dummy", System.currentTimeMillis()); //항상 새로운 값을 주기 위한 방법
                            // 데이터 전송
                            PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
                            Task<DataItem> task = dataClient.putDataItem(putDataRequest);
                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "스마트워치와의 연결이 불안정합니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now- myPauseTime);
                        myBtnStart.setText("일시정지");
                        cur_Status = Run;
                        break;
                }
                break;
            case R.id.btn_refresh: //시작버튼을 클릭했을때 현재 상태값에 따라 다른 동작을 할수있게끔 구현.
                if(pt.isVibSmartWatch()) {
                    PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/play");
                    DataMap dataMap = putDataMapRequest.getDataMap();
                    dataMap.putInt("status", -1);
                    dataMap.putLong("dummy", System.currentTimeMillis()); //항상 새로운 값을 주기 위한 방법
                    // 데이터 전송
                    PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
                    Task<DataItem> task = dataClient.putDataItem(putDataRequest);
                    task.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "스마트워치와의 연결이 불안정합니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                myTimer.removeMessages(0); //핸들러 메세지 제거
                myBtnStart.setText("시작");
                myOutput.setText("00:00");
                myRec.setText("여기에 스크립트가 표시됩니다");
                myTitle.setText("여기에 키포인트가 표시됩니다");
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
            myTimer=new Handler(){public void handleMessage(Message msg){}};
            Toast.makeText(getApplicationContext(),"PT가 종료되었습니다.",Toast.LENGTH_LONG).show();
            myBtnStart.setText("재시작");
            outTime=0;
            cur_Status=Init;
        }

        for(KeyPoint kp : pt.getKeyPoints()){
            if((outTime/100) == (kp.getVibTime()/100)){
                myTitle.setText(kp.getName());
                if(pt.isVibPhone()) {
                    vibe.vibrate(1000);
                }
            }
        }


        for(Script sc : pt.getScripts()){
            if((outTime/1000) == (sc.getStartTime()/1000)){
                myRec.setText(sc.getContent());
            } else if ((outTime/1000) == (sc.getEndTime()/1000)){
                myRec.setText("");
            }
        }
        for(Script sc : pt.getScripts()){
            if((outTime/1000) == (sc.getStartTime()/1000)){
                myRec.setText(sc.getContent());
            }
        }

        return easy_outTime;
    }

    public void appFinish(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ptPlayActivity.this);
        alertDialogBuilder.setTitle("PT 중단하기");
        alertDialogBuilder
                .setMessage("PT를 중단하시겠습니까?")
                .setPositiveButton("중단",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(pt.isVibSmartWatch()) {
                                    PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/present");
                                    DataMap dataMap = putDataMapRequest.getDataMap();
                                    dataMap.putBoolean("finish", true);
                                    dataMap.putLong("dummy", System.currentTimeMillis()); //항상 새로운 값을 주기 위한 방법
                                    // 데이터 전송
                                    PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
                                    Task<DataItem> task = dataClient.putDataItem(putDataRequest);
                                    task.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), "스마트워치와의 연결이 불안정합니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                myTimer = new Handler() {
                                    public void handleMessage(Message msg) {
                                    }
                                };
                                finish();
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