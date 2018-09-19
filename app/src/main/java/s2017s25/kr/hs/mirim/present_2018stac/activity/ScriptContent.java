package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;

public class ScriptContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_content);
        TextView scriptEdit = (TextView) findViewById(R.id.script_edit);
        TextView scriptDelete = (TextView) findViewById(R.id.script_delete);
        TextView scriptArea = (TextView) findViewById(R.id.script_area);

        scriptDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        scriptEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScriptContent.this, ScriptContentInput.class);
//                intent.putExtra();
//                startActivity();

            }
        });

    }
}
