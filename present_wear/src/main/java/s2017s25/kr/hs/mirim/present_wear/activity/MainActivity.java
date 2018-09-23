package s2017s25.kr.hs.mirim.present_wear.activity;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_wear.R;
import s2017s25.kr.hs.mirim.present_wear.adapter.MainListAdapter;

public class MainActivity extends WearableActivity {
    ArrayList<String> PTtileList;
    MainListAdapter mainListAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.PTlist_view);

        PTtileList = new ArrayList<String>();

        PTtileList.add("앱잼 발표");
        PTtileList.add("STAC 발표!");

        mainListAdapter = new MainListAdapter(MainActivity.this, PTtileList);
        listView.setAdapter(mainListAdapter);

        setAmbientEnabled();

    }
}
