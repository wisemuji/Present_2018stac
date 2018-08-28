package s2017s25.kr.hs.mirim.present_2018stac.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import s2017s25.kr.hs.mirim.present_2018stac.Adapter.PTListAdapter;
import s2017s25.kr.hs.mirim.present_2018stac.Adapter.pt_list_item;
import s2017s25.kr.hs.mirim.present_2018stac.R;

public class PTlistActivity extends AppCompatActivity {
    ListView listView;
    PTListAdapter myListAdapter;
    ArrayList<pt_list_item> list_itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptlist);
        listView =(ListView)findViewById(R.id.my_listView);
        TextView exitBtn = (TextView) findViewById(R.id.exitBtn);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        Date date = new Date();
        String today = formatter.format(date);

        list_itemArrayList = new ArrayList<pt_list_item>();

        list_itemArrayList.add(new pt_list_item("프리젠트", "5:00", today, R.drawable.option1));

        myListAdapter = new PTListAdapter(PTlistActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
