package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;

public class keypointInputActivity extends AppCompatActivity {

    EditText keyContent;
    NumberPicker pickerHour, pickerMinute, pickerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypt_input);

        Intent intent = getIntent();
        final Presentation pt= (Presentation) intent.getSerializableExtra("presentation");

        TextView OKbtn = (TextView) findViewById(R.id.kpt_ok_btn);

        keyContent = findViewById(R.id.keypoint_content);
        int color = Color.parseColor("#ffffff");
        keyContent.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        //밑줄 흰색 만들기

        pickerHour = (NumberPicker) findViewById(R.id.key_picker_hour);
        pickerHour.setMinValue(0);
        pickerHour.setMaxValue(99);
        pickerMinute = (NumberPicker) findViewById(R.id.key_picker_minute);
        pickerMinute.setMinValue(0);
        pickerMinute.setMaxValue(59);
        pickerSecond = (NumberPicker) findViewById(R.id.key_picker_second);
        pickerSecond.setMinValue(0);
        pickerSecond.setMaxValue(59);

        OKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(keypointInputActivity.this, ScriptInputActivity.class);
                String content = keyContent.getText().toString();
                long keyptTime = 0;

                keyptTime += pickerHour.getValue() * 1000 * 3600;
                keyptTime += pickerMinute.getValue() * 1000 * 60;
                keyptTime += pickerSecond.getValue() * 1000;

                KeyPoint dataKey = new KeyPoint(content,keyptTime);

                intent.putExtra("key", dataKey);

                setResult(RESULT_OK,intent);
                finish();
            }
        });

        TextView exitBtn = (TextView) findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
