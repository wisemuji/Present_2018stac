package s2017s25.kr.hs.mirim.present_2018stac.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.R;

public class PTListAdapter extends BaseAdapter{
    Context context;
    ArrayList<pt_list_item> list_itemArrayList;

    public PTListAdapter(Context context, ArrayList<pt_list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    TextView title_textView;
    TextView PTtime_textView;
    TextView PTdate_textView;
    ImageView option_ImageView;

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
            title_textView = (TextView)convertView.findViewById(R.id.title_textView);
            PTtime_textView = (TextView)convertView.findViewById(R.id.PTtime_textView);
            PTdate_textView = (TextView)convertView.findViewById(R.id.PTdate);
            option_ImageView = (ImageView)convertView.findViewById(R.id.option);
        }
      title_textView.setText(list_itemArrayList.get(position).getTitle());
        PTtime_textView.setText(list_itemArrayList.get(position).getPTtime());
        PTdate_textView.setText(list_itemArrayList.get(position).getPTdate());
        option_ImageView.setImageResource(list_itemArrayList.get(position).getOption());
        return convertView;

    }
}
