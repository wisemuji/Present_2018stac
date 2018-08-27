package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.R;

public class StopwatchActivity extends AppCompatActivity {
    EditText inputTitle;
    TimePicker inputTime;
    TextView nextBtn;
    Presentation pt = new Presentation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        Intent intent = getIntent();
        if(intent.getSerializableExtra("presentation")!=null)
            pt = (Presentation) intent.getSerializableExtra("presentation");

        inputTitle = (EditText) findViewById(R.id.input_title);
        inputTitle.setText(pt.getName());
        nextBtn = (TextView) findViewById(R.id.stopwatch_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputTitle.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                } else {
                    String title = inputTitle.getText().toString();
                    pt.setName(title);
                    long time = 120000; //더미데이터
                    /*inputTime.getHour()*/
                    pt.setPresentTime(time);

                    Intent intent = new Intent(StopwatchActivity.this, ScriptInputActivity.class);
                    intent.putExtra("presentation", pt);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
