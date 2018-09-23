package s2017s25.kr.hs.mirim.present_wear.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_wear.R;

public class MainListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> PTtitle;

    public MainListAdapter(Context context, ArrayList<String> PTtitle) {
        this.context = context;
        this.PTtitle = PTtitle;
    }

    TextView title_textView;

    @Override
    public int getCount() {
        return this.PTtitle.size();
    }

    @Override
    public Object getItem(int position) {
        return PTtitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pt_list_item, null);
            title_textView = (TextView) convertView.findViewById(R.id.PTtitle);
        }
        title_textView.setText(PTtitle.get(position));

        return convertView;
    }

}
