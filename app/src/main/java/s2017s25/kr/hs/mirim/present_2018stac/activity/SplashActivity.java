package s2017s25.kr.hs.mirim.present_2018stac.activity;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import s2017s25.kr.hs.mirim.present_2018stac.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView splash = (ImageView) findViewById(R.id.splash_image);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(splash);
        Glide.with(this).load(R.drawable.splash).into(gifImage);


        startLoading();

    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 4500);
    }
}