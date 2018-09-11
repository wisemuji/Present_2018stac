package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.app.Activity;
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
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;

public class ScriptContentInput extends AppCompatActivity {

    NumberPicker startPickerHour, startPickerMinute, startPickerSecond,
    endPickerHour, endPickerMinute, endPickerSecond;
    Presentation pt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_content_input);
        Intent intent = getIntent();
        pt = (Presentation) intent.getSerializableExtra("presentation");

        final EditText scriptContent = findViewById(R.id.script_content);
        int color = Color.parseColor("#ffffff");
        scriptContent.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        startPickerHour = (NumberPicker)findViewById(R.id.picker_hour_start);
        startPickerHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(startPickerHour, 0xffffffff);
        startPickerHour.setMinValue(0);
        startPickerHour.setMaxValue(99);
        startPickerHour.setFormatter(twoDigitFormatter);
        startPickerMinute = (NumberPicker)findViewById(R.id.picker_minute_start);
        startPickerMinute.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(startPickerMinute, 0xffffffff);
        startPickerMinute.setMinValue(0);
        startPickerMinute.setMaxValue(59);
        startPickerMinute.setFormatter(twoDigitFormatter);
        startPickerSecond = (NumberPicker)findViewById(R.id.picker_second_start);
        startPickerSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(startPickerSecond, 0xffffffff);
        startPickerSecond.setMinValue(0);
        startPickerSecond.setMaxValue(59);
        startPickerSecond.setFormatter(twoDigitFormatter);

        endPickerHour = (NumberPicker)findViewById(R.id.picker_hour_end);
        endPickerHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(endPickerHour, 0xffffffff);
        endPickerHour.setMinValue(0);
        endPickerHour.setMaxValue(99);
        endPickerHour.setFormatter(twoDigitFormatter);
        endPickerMinute = (NumberPicker)findViewById(R.id.picker_minute_end);
        endPickerMinute.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(endPickerMinute, 0xffffffff);
        endPickerMinute.setMinValue(0);
        endPickerMinute.setMaxValue(59);
        endPickerMinute.setFormatter(twoDigitFormatter);
        endPickerSecond = (NumberPicker)findViewById(R.id.picker_second_end);
        endPickerSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(endPickerSecond, 0xffffffff);
        endPickerSecond.setMinValue(0);
        endPickerSecond.setMaxValue(59);
        endPickerSecond.setFormatter(twoDigitFormatter);


        TextView OKbtn = findViewById(R.id.sc_ok_btn);
        OKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScriptContentInput.this, ScriptInputActivity.class);
                String content = scriptContent.getText().toString();
                long startTime = 0, endTime = 0;

                startTime += startPickerHour.getValue() * 1000 * 3600;
                startTime += startPickerMinute.getValue() * 1000 * 60;
                startTime += startPickerSecond.getValue() * 1000;

                endTime += endPickerHour.getValue()*1000*3600;
                endTime += endPickerMinute.getValue() * 1000 * 60;
                endTime += endPickerSecond.getValue() * 1000;

                if(content.isEmpty()){
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(endTime==0){
                    Toast.makeText(getApplicationContext(),"종료 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(endTime>pt.getPresentTime()||startTime>pt.getPresentTime()){
                    Toast.makeText(getApplicationContext(),"발표 시간을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(endTime==startTime){
                    Toast.makeText(getApplicationContext(),"시작 시간이 종료 시간과 같습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(startTime>endTime){
                    Toast.makeText(getApplicationContext(),"시작 시간이 종료 시간보다 뒤에 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Script script = new Script(startTime, endTime, content);
                intent.putExtra("script", script);
                setResult(Activity.RESULT_OK, intent);
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
