package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.db.DBHelper;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;

public class StopwatchActivity extends AppCompatActivity {
    EditText inputTitle;
    TimePicker inputTime;
    TextView exitBtn;
    LinearLayout nextBtn;
    NumberPicker pickerHour;
    NumberPicker pickerMinute;
    NumberPicker pickerSecond;
    Presentation pt = new Presentation();
    String mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Presentation.db", null, 1);

        Intent intent = getIntent();
        if(intent.getSerializableExtra("presentation")!=null)
            pt = (Presentation) intent.getSerializableExtra("presentation");
        else {
            pt.setScripts(new ArrayList<Script>());
            pt.setKeyPoints(new ArrayList<KeyPoint>());
        }
        mode="input";
        if(intent.getStringExtra("mode")!=null) {
            mode = intent.getStringExtra("mode");
        }
        if(mode.equals("modify")){
            pt = dbHelper.getPresentation(pt.getName());
        }


        pickerHour = (NumberPicker)findViewById(R.id.picker_hour);
        pickerHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(pickerHour, 0xff6767c7);
        pickerHour.setMinValue(00);
        pickerHour.setMaxValue(99);
        if(pt.getPresentTime()!=null)
            pickerHour.setValue((int)(pt.getPresentTime()/1000/3600));
        pickerHour.setFormatter(twoDigitFormatter);
        pickerMinute = (NumberPicker)findViewById(R.id.picker_minute);
        pickerMinute.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(pickerMinute, 0xff6767c7);
        pickerMinute.setMinValue(00);
        pickerMinute.setMaxValue(59);
        if(pt.getPresentTime()!=null)
            pickerMinute.setValue((int)(((pt.getPresentTime() / 1000) % 3600) / 60));
        pickerMinute.setFormatter(twoDigitFormatter);
        pickerSecond = (NumberPicker)findViewById(R.id.picker_second);
        pickerSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(pickerSecond, 0xff6767c7);
        pickerSecond.setMinValue(0);
        pickerSecond.setMaxValue(59);
        if(pt.getPresentTime()!=null)
            pickerSecond.setValue((int)(pt.getPresentTime() / 1000 % 60));
        pickerSecond.setFormatter(twoDigitFormatter);

        inputTitle = (EditText) findViewById(R.id.input_title);
        inputTitle.setText(pt.getName());

        ConstraintLayout parentLayout=findViewById(R.id.linearLayout11);

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inputTitle.getWindowToken(),0);
            }
        });



        ImageView inforBtn = findViewById(R.id.inforBtn);
        inforBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StopwatchActivity.this, AppInfo.class);
                startActivity(intent);
            }
        });

        exitBtn = (TextView) findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        nextBtn = (LinearLayout) findViewById(R.id.stopwatch_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = inputTitle.getText().toString();
                long time=0;
                time+=pickerHour.getValue()*1000*3600;
                time+=pickerMinute.getValue()*1000*60;
                time+=pickerSecond.getValue()*1000;

                if(title.isEmpty()){
                    Toast.makeText(getApplicationContext(),"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(time==0){
                    Toast.makeText(getApplicationContext(),"시간을 설정해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(dbHelper.isDoubleExists(title)&&mode.equals("input")){
                    Toast.makeText(getApplicationContext(),"같은 제목의 PT가 이미 존재합니다.",Toast.LENGTH_SHORT).show();
                }
                else {
                    pt.setName(title);
                    pt.setPresentTime(time);

                    Intent intent = new Intent(StopwatchActivity.this, ScriptKeyPointListActivity.class);

                    intent.putExtra("presentation", pt);
                    intent.putExtra("mode", mode);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StopwatchActivity.this);
        alertDialogBuilder.setTitle("PT 생성 중단");
        alertDialogBuilder
                .setMessage("PT 생성을 중단하시겠습니까?")
                .setPositiveButton("중단",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });
        alertDialogBuilder.show();
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