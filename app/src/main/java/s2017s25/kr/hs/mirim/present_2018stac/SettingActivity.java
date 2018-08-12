package s2017s25.kr.hs.mirim.stac2018_present;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    TextView nextBtn, prevBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        nextBtn = (TextView) findViewById(R.id.setting_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
