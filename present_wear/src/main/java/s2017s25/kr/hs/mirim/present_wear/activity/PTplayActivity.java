package s2017s25.kr.hs.mirim.present_wear.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import s2017s25.kr.hs.mirim.present_wear.R;
import s2017s25.kr.hs.mirim.present_wear.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_wear.model.Presentation;

public class PTplayActivity extends WearableActivity {
    Presentation pt;
    TextView ptTitle;
    TextView myOutput;
    TextView myBtnStart;
    TextView myBtnRefresh;

    Vibrator vibe;
    long myBaseTime;
    long myPauseTime;

    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptplay);

        pt = new Presentation();
//        Intent intent = getIntent();
//        pt = (Presentation) intent.getSerializableExtra("presentation");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        Toast.makeText(getApplicationContext(),"발표 중에는 화면이 꺼지지 않습니다.",Toast.LENGTH_LONG).show();

        ptTitle = (TextView) findViewById(R.id.pt_title);
        myOutput = (TextView) findViewById(R.id.time_out);
        myBtnStart = (TextView) findViewById(R.id.btn_start);
        myBtnRefresh = (TextView) findViewById(R.id.btn_refresh);

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
            myTimer=new Handler(){public void handleMessage(Message msg){}};
            Toast.makeText(getApplicationContext(),"PT가 종료되었습니다.",Toast.LENGTH_LONG).show();
            myBtnStart.setText("재시작");
            outTime=0;
            cur_Status=Init;
        }

        for(KeyPoint kp : pt.getKeyPoints()){
            if((outTime/100) == (kp.getVibTime()/100)){
                if(pt.isVibPhone()) {
                    vibe.vibrate(1000);
                }
            }
        }

        return easy_outTime;
    }
}
