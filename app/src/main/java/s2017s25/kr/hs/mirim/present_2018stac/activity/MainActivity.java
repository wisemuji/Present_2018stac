package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startPT = (Button)findViewById(R.id.startPT);
        Button PTlist = (Button)findViewById(R.id.PTlist);


        startPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StopwatchActivity.class);
                startActivity(intent);
            }
        });
        PTlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PTlistActivity.class);
                startActivity(intent);
            }
        });

    }
}
