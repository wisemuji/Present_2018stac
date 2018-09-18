package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;

public class keypointInputActivity extends AppCompatActivity {

    EditText keyContent;
    NumberPicker pickerHour, pickerMinute, pickerSecond;
    Presentation pt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypt_input);
        Intent intent = getIntent();
        pt = (Presentation) intent.getSerializableExtra("presentation");

        TextView OKbtn = (TextView) findViewById(R.id.kpt_ok_btn);

        keyContent = findViewById(R.id.keypoint_content);
        int color = Color.parseColor("#ffffff");
        keyContent.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        //밑줄 흰색 만들기

        pickerHour = (NumberPicker) findViewById(R.id.picker_hour);
        pickerHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(pickerHour, 0xffffffff);
        pickerHour.setMinValue(0);
        pickerHour.setMaxValue(99);
        pickerHour.setFormatter(twoDigitFormatter);
        pickerMinute = (NumberPicker) findViewById(R.id.picker_minute);
        pickerMinute.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(pickerMinute, 0xffffffff);
        pickerMinute.setMinValue(0);
        pickerMinute.setMaxValue(59);
        pickerMinute.setFormatter(twoDigitFormatter);
        pickerSecond = (NumberPicker) findViewById(R.id.picker_second);
        pickerSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(pickerSecond, 0xffffffff);
        pickerSecond.setMinValue(0);
        pickerSecond.setMaxValue(59);
        pickerSecond.setFormatter(twoDigitFormatter);

        OKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(keypointInputActivity.this, ScriptKeyPointListActivity.class);
                String content = keyContent.getText().toString();
                long keyptTime = 0;



                keyptTime += pickerHour.getValue() * 1000 * 3600;
                keyptTime += pickerMinute.getValue() * 1000 * 60;
                keyptTime += pickerSecond.getValue() * 1000;
                if(keyptTime>pt.getPresentTime()){
                    Toast.makeText(getApplicationContext(),"발표 시간을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

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

    private void setDividerColor(NumberPicker picker, int color){
        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for(java.lang.reflect.Field pf : pickerFields){
            if(pf.getName().equals("mSelectionDivider")){
                pf.setAccessible(true);
                try{
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e){
                    e.printStackTrace();
                } catch (Resources.NotFoundException e){
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    NumberPicker.Formatter twoDigitFormatter = new NumberPicker.Formatter() {
        @Override
        public String format(int value) {
            if(value < 10)
                return String.format("0%d",value);
            else
                return String.format("%d",value);
        }
    };

}
