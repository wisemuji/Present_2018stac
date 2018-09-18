package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import s2017s25.kr.hs.mirim.present_2018stac.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences prefs = getSharedPreferences("Pref", Activity.MODE_PRIVATE);

        boolean isFirstRun = prefs.getBoolean("isFirstRun",false);

        if(!isFirstRun){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstRun", true);
            editor.commit();
          Intent intent = new Intent(MainActivity.this, AppInfo.class);
          startActivity(intent);
        }


        Intent splash = new Intent(this, SplashActivity.class);
        startActivity(splash);


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
