package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;

public class PTplayActivity extends WearableActivity
        implements DataClient.OnDataChangedListener{
    Presentation pt;
    TextView ptTitle;
    TextView myOutput;
    TextView myBtnStart;
    TextView myBtnRefresh;
    TextView watingView;

    Vibrator vibe;
    long myBaseTime;
    long myPauseTime;

    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    private DataClient dataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptplay);

        ArrayList<KeyPoint> keyPoints=new ArrayList<KeyPoint>();
//        keyPoints.add(new KeyPoint(0,0,"haha",(long)2000));
//        pt = new Presentation("test", (long) 3000, true, true, true, true, new ArrayList<Script>(), keyPoints);
        pt = new Presentation();
//        Intent intent = getIntent();
//        pt = (Presentation) intent.getSerializableExtra("presentation");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        watingView = (TextView) findViewById(R.id.wating_view);

        ptTitle = (TextView) findViewById(R.id.pt_title);
        myOutput = (TextView) findViewById(R.id.time_out);
        myBtnStart = (TextView) findViewById(R.id.btn_start);
        myBtnRefresh = (TextView) findViewById(R.id.btn_refresh);

//        ptTitle.setText(pt.getName());

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
        // Enables Always-on
        setAmbientEnabled();

        dataClient = Wearable.getDataClient(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        dataClient.addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataClient.removeListener(this);
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
                vibe.vibrate(1000);
            }
        }

        return easy_outTime;
    }

    /**
     *
     * @param dataEventBuffer
     *
     * 폰으로부터 데이터를 받았을 때 호출되는 부분!!
     */
    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        // 받은 데이터가 1개 이상일 때
        if(dataEventBuffer.getCount() > 0) {
            // 첫번째 데이터를 가져옴
            DataEvent event = dataEventBuffer.get(0);
            DataItem item = event.getDataItem();

            // 데이터가 /text 일 때 문자열 데이터를 가져옴
            // uri로 데이터를 구분할 수 있음
            if(item.getUri().getPath().equals("/present")) {
                DataMap dataMap = DataMap.fromByteArray(item.getData());
                if(dataMap.getBoolean("finish")) {
                    watingView.setVisibility(View.VISIBLE);
                } else {
                    String pTName = dataMap.getString("name");
                    long pTTime = dataMap.getLong("time");
                    String[] keyPointNames = dataMap.getStringArray("keypointnames");
                    long[] keyPointTimes = dataMap.getLongArray("keypointtimes");
//                Toast.makeText(getApplicationContext(),": "+pTName+", "+pTTime, Toast.LENGTH_SHORT).show();
                    pt.setName(pTName);
                    pt.setPresentTime(pTTime);
                    myOutput.setText("00:00");
                    ptTitle.setText(pt.getName());
                    pt.setKeyPoints(makeKeyPoints(keyPointNames, keyPointTimes));
                    watingView.setVisibility(View.GONE);
                }
            }
            else if(item.getUri().getPath().equals("/play")) {
                DataMap dataMap = DataMap.fromByteArray(item.getData());
                int status = dataMap.getInt("status");
                cur_Status = status;
                if(status == -1)
                    myOnClick(myBtnRefresh);
                else
                    myOnClick(myBtnStart);

            }
        }
        dataEventBuffer.release();
    }
    public ArrayList<KeyPoint> makeKeyPoints(String[] names, long[] times){
       ArrayList<KeyPoint> keyPoints = new ArrayList<KeyPoint>();
       for(int i=0;i<names.length;i++){
           keyPoints.add(new KeyPoint(names[i], times[i]));
//           Toast.makeText(getApplicationContext(),": "+names[i]+", "+times[i], Toast.LENGTH_SHORT).show();
       }
       return keyPoints;
    }
}
