package s2017s25.kr.hs.mirim.present_2018stac;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ptPlayActivity extends AppCompatActivity {
    private TimerTask mTask;
    private Timer mTimer;
    TextView myOutput;
    TextView myRec;
    Button myBtnStart;
    Button myBtnRec;
    Vibrator vibe;


    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    int myCount=1;
    long myBaseTime;
    long myPauseTime;
    int i=1;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptplay);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        myOutput = (TextView) findViewById(R.id.time_out);
        myRec = (TextView) findViewById(R.id.record);
        myBtnStart = (Button) findViewById(R.id.btn_start);
//
//        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
//        }
//
//// Output yes if can vibrate, no otherwise
//        if (v.hasVibrator()) {
//            Log.v("Can Vibrate", "YES");
//        } else {
//            Log.v("Can Vibrate", "NO");
//        }
//

//       Handler handler = new Handler();
//       handler.postDelayed(new Runnable() {
//           public void run() {
//               vibe.vibrate(1000);
//               myRec.setText((i++)+"분 경과");
//           }
//       }, 1000*3);  // 2000은 2초를 의미합니다.
//        str = "0";
//        mTask = new TimerTask() {
//            @Override
//            public void run() {
//                str=myOutput.getText().toString().substring(1,2);
//                myRec.setText(str + "분 경과");
//                vibe.vibrate(1000);
//            }
//        };
//
//        mTimer = new Timer();
//
//        mTimer.schedule(mTask, 1000 * 5, 100);


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
                myRec.setText("STAC 발표\nPRE-SENT");
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
        String easy_outTime = String.format("%02d:%02d", outTime/1000 / 60, (outTime/1000)%60);
        String minute = String.format("%02d", outTime/1000 / 60);
        String second = String.format("%02d", (outTime/1000)%60);
        if(minute.equals("04") && Integer.parseInt(second)>19 && Integer.parseInt(second)<40) {
            myRec.setText("큰일!!!!!!!마무리!!!");
            if (Integer.parseInt(second) == 20) {
                vibe.vibrate(1000);
            }
        }
        else if(minute.equals("04") && Integer.parseInt(second)>39 && Integer.parseInt(second)<=59){
            myRec.setText("큰일!!!!!!!마무리!!!");
            if (Integer.parseInt(second)==40) {
                vibe.vibrate(1000);
            }
        }
        else if(minute.equals("05")){
            myTimer.removeMessages(0); //핸들러 메세지 제거
            myPauseTime = SystemClock.elapsedRealtime();
            myBtnStart.setText("다시 시작");
            cur_Status = Init;
        }
        else if(!minute.equals("00")){
            myRec.setText(minute.substring(1) + "분 경과");
            if (minute.equals("0"+myCount)) {
                vibe.vibrate(1000);
                myCount++;
            }
        }
        return easy_outTime;
    }

    public void appFinish(View v){
        finish();
    }

}


