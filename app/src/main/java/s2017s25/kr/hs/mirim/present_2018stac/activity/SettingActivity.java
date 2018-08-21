package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.db.DBHelper;
import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;

public class SettingActivity extends AppCompatActivity {
    TextView nextBtn, prevBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Presentation.db", null, 1);

        nextBtn = (TextView) findViewById(R.id.setting_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Script> scripts=new ArrayList<>();
                ArrayList<KeyPoint> keyPoints=new ArrayList<>();
                Presentation pt=new Presentation("test",(long)60,true,true,true,true,scripts,keyPoints);

                int lastId = dbHelper.insert(pt);
//                Toast.makeText(getApplicationContext(), "lastId = "+lastId, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SettingActivity.this, PTlistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        prevBtn = (TextView) findViewById(R.id.setting_prev_btn);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, KeypointActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
