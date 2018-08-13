package s2017s25.kr.hs.mirim.present_2018stac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button startPT;
    Button PTlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        File filesDir = getFilesDir();
//        File recordDir = new File(filesDir, "record/" + pt.id);
//        File[] mp3FIles = recordDir.listFiles();
//        mp3FIles[0].getName()
        setContentView(R.layout.activity_main);

        startPT = (Button)findViewById(R.id.startPT);
        PTlist = (Button)findViewById(R.id.PTlist);

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
