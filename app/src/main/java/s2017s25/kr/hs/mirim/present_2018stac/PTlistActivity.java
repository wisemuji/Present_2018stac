package s2017s25.kr.hs.mirim.present_2018stac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;


public class PTlistActivity extends AppCompatActivity {

    TextView exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptlist);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Presentation.db", null, 1);

        ListView listView;
        PTListAdapter myListAdapter;
        ArrayList<ptlist_list_item> list_itemArrayList;
        ArrayList<Presentation> ptList = dbHelper.getResult();

        listView = (ListView)findViewById(R.id.my_listView);

        list_itemArrayList = new ArrayList<ptlist_list_item>();

        DateFormat df = new SimpleDateFormat("mm:ss"); // HH=24h, hh=12h

        for(Presentation pt : ptList){
            list_itemArrayList.add(new ptlist_list_item(R.drawable.start,pt.getName(),df.format(pt.getPresentTime()),R.drawable.menu));
        }

//        list_itemArrayList.add(new ptlist_list_item(R.drawable.start,"앱잼발표","5:00",R.drawable.menu));
//        list_itemArrayList.add(new ptlist_list_item(R.drawable.start,"앱 디자인 기획서 발표","3:00",R.drawable.menu));
        myListAdapter = new PTListAdapter(PTlistActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);

        exitBtn = (TextView) findViewById(R.id.exitBtn);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
