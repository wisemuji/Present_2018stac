package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import s2017s25.kr.hs.mirim.present_2018stac.db.DBHelper;
import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;


public class SettingActivity extends AppCompatActivity {
    TextView nextBtn, prevBtn, exitBtn;
    LinearLayout settingTime,settingScript,settingVib,settingWatch;
    CheckBox settingCheck1, settingCheck2, settingCheck3, settingCheck4;
    Presentation pt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        pt = (Presentation) intent.getSerializableExtra("presentation");


        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Presentation.db", null, 1);

        nextBtn = (TextView) findViewById(R.id.setting_next_btn);

        settingTime=(LinearLayout)findViewById(R.id.setting_time);
        settingScript=(LinearLayout)findViewById(R.id.setting_script);
        settingVib=(LinearLayout)findViewById(R.id.setting_vib);
        settingWatch=(LinearLayout)findViewById(R.id.setting_watch);

        settingCheck1=(CheckBox)findViewById(R.id.setting_check1);
        settingCheck2=(CheckBox)findViewById(R.id.setting_check2);
        settingCheck3=(CheckBox)findViewById(R.id.setting_check3);
        settingCheck4=(CheckBox)findViewById(R.id.setting_check4);

        if(pt.isDisplayTime()) settingCheck1.setChecked(true);
        if(pt.isDisplayScript()) settingCheck2.setChecked(true);
        if(pt.isVibPhone()) settingCheck3.setChecked(true);
        if(pt.isVibSmartWatch()) settingCheck4.setChecked(true);

        settingTime.setOnClickListener(setCheck);
        settingScript.setOnClickListener(setCheck);
        settingVib.setOnClickListener(setCheck);
        settingWatch.setOnClickListener(setCheck);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Script> scripts=new ArrayList<>();
                ArrayList<KeyPoint> keyPoints=new ArrayList<>();
                Date date=new Date();
//              Presentation pt=new Presentation("test2",(long)120000,true,true,true,true,scripts,keyPoints);

                pt.setScripts(scripts);
                pt.setKeyPoints(keyPoints);
                pt.setDisplayTime(settingCheck1.isChecked());
                pt.setDisplayScript(settingCheck2.isChecked());
                pt.setVibPhone(settingCheck3.isChecked());
                pt.setVibSmartWatch(settingCheck4.isChecked());
                pt.setModifiedDate(date.getTime());

                int lastId = dbHelper.insert(pt);
//                Toast.makeText(getApplicationContext(), "lastId = "+lastId, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SettingActivity.this, PTlistActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
            }
        });

        prevBtn = (TextView) findViewById(R.id.setting_prev_btn);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pt.setDisplayTime(settingCheck1.isChecked());
                pt.setDisplayScript(settingCheck2.isChecked());
                pt.setVibPhone(settingCheck3.isChecked());
                pt.setVibSmartWatch(settingCheck4.isChecked());
                Intent intent = new Intent(SettingActivity.this, ScriptInputActivity.class);
                intent.putExtra("presentation", pt);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.activity_slide_enter, R.anim.activity_slide_exit);
            }
        });
        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });
    }


    View.OnClickListener setCheck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setting_time:
                    if(settingCheck1.isChecked())
                        settingCheck1.setChecked(false);
                    else
                        settingCheck1.setChecked(true);
                    break;
                case R.id.setting_script:
                    if(settingCheck2.isChecked())
                        settingCheck2.setChecked(false);
                    else
                        settingCheck2.setChecked(true);
                    break;
                case R.id.setting_vib:
                    if(settingCheck3.isChecked())
                        settingCheck3.setChecked(false);
                    else
                        settingCheck3.setChecked(true);
                    break;
                case R.id.setting_watch:
                    if(settingCheck4.isChecked())
                        settingCheck4.setChecked(false);
                    else
                        settingCheck4.setChecked(true);
                    break;
            }
        }
    };

}