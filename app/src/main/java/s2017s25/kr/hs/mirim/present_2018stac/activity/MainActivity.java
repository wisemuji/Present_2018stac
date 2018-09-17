package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
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

        Intent splash = new Intent(this, SplashActivity.class);
        startActivity(splash);

        ///해시코드
//
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("s2017s25.kr.hs.mirim.present_2018stac", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Toast.makeText(getApplicationContext(),"no1",Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            Toast.makeText(getApplicationContext(),"no2",Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }

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
