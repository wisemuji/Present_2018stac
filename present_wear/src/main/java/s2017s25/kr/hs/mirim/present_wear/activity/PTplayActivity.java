package s2017s25.kr.hs.mirim.present_wear.activity;

import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import s2017s25.kr.hs.mirim.present_wear.R;
import s2017s25.kr.hs.mirim.present_wear.model.Presentation;

public class PTplayActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptplay);

        pt = new Presentation();




    }
}
