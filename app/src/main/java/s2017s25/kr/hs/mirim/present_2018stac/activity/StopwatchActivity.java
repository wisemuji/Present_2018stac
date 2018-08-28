package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;

public class StopwatchActivity extends AppCompatActivity {
    TextView nextBtn;
    Presentation presentation = new Presentation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);


        nextBtn = (TextView) findViewById(R.id.stopwatch_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StopwatchActivity.this, ScriptInputActivity.class);
                intent.putExtra("StopwatchInfor", presentation);
                startActivity(intent);
                finish();
            }
        });

    }
}
