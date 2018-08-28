package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import s2017s25.kr.hs.mirim.present_2018stac.R;

public class dialogActivity extends AppCompatActivity {
//팝업창
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        TextView closebtn = (TextView)findViewById(R.id.closeBtn);
        Button confirmBtn = (Button) findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
