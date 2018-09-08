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

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;

public class ScriptContentInput extends AppCompatActivity {

    NumberPicker startPickerHour, startPickerMinute, startPickerSecond,
    endPickerHour, endPickerMinute, endPickerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_content_input);

        final EditText scriptContent = findViewById(R.id.script_content);
        int color = Color.parseColor("#ffffff");
        scriptContent.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        startPickerHour = (NumberPicker)findViewById(R.id.picker_hour_start);
        startPickerHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(startPickerHour, 0xffffffff);
        startPickerHour.setMinValue(0);
        startPickerHour.setMaxValue(99);
        startPickerMinute = (NumberPicker)findViewById(R.id.picker_minute_start);
        startPickerMinute.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(startPickerMinute, 0xffffffff);
        startPickerMinute.setMinValue(0);
        startPickerMinute.setMaxValue(59);
        startPickerSecond = (NumberPicker)findViewById(R.id.picker_second_start);
        startPickerSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(startPickerSecond, 0xffffffff);
        startPickerSecond.setMinValue(0);
        startPickerSecond.setMaxValue(59);

        endPickerHour = (NumberPicker)findViewById(R.id.picker_hour_end);
        endPickerHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(endPickerHour, 0xffffffff);
        endPickerHour.setMinValue(0);
        endPickerHour.setMaxValue(99);
        endPickerMinute = (NumberPicker)findViewById(R.id.picker_minute_end);
        endPickerMinute.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(endPickerMinute, 0xffffffff);
        endPickerMinute.setMinValue(0);
        endPickerMinute.setMaxValue(59);
        endPickerSecond = (NumberPicker)findViewById(R.id.picker_second_end);
        endPickerSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(endPickerSecond, 0xffffffff);
        endPickerSecond.setMinValue(0);
        endPickerSecond.setMaxValue(59);


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
