package s2017s25.kr.hs.mirim.present_2018stac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.item.keypoint_list_item;

public class KeypointListAdapter extends BaseAdapter {
    Context context;
    ArrayList<keypoint_list_item> list_itemArrayList;

    TextView title;
    TextView vibTime;
    TextView vibLen;

    public KeypointListAdapter(Context context, ArrayList<keypoint_list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.keypoint_item,null);

            title=(TextView)convertView.findViewById(R.id.keypoint_list_title);
            vibTime=(TextView)convertView.findViewById(R.id.keypoint_list_vibtime);
//            vibLen=(TextView)convertView.findViewById(R.id.keypoint_list_viblen);

            title.setText(list_itemArrayList.get(position).getTitle());
            vibTime.setText(list_itemArrayList.get(position).getVibTime());
//            vibLen.setText(list_itemArrayList.get(position).getViblen());
        }

        return convertView;
    }
}
