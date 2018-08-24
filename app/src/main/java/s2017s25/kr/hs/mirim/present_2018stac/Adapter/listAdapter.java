package s2017s25.kr.hs.mirim.present_2018stac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.item.list_item;

public class listAdapter extends BaseAdapter {

    Context context;
    ArrayList<list_item> list_itemArrayList;
    ViewHolder viewHolder;

    class ViewHolder{
        TextView title_textView;
        TextView PTtime_textView;
        ImageView menu_imageView;
        ImageView start_icon_imageYiew;

    }


    public listAdapter(Context context, ArrayList<list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

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

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);

            viewHolder = new ViewHolder();
            viewHolder.title_textView = (TextView)convertView.findViewById(R.id.title_textView);
            viewHolder.PTtime_textView = (TextView)convertView.findViewById(R.id.PTtime_textView);
            viewHolder.start_icon_imageYiew = (ImageView)convertView.findViewById(R.id.start_icon_imageYiew);
            viewHolder.menu_imageView = (ImageView)convertView.findViewById(R.id.menu_imageView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.title_textView.setText(list_itemArrayList.get(position).getTitle());
        viewHolder.PTtime_textView.setText(list_itemArrayList.get(position).getPTtime().toString());
        viewHolder.start_icon_imageYiew.setImageResource(list_itemArrayList.get(position).getStart_icon());
        viewHolder.menu_imageView.setImageResource(list_itemArrayList.get(position).getMenu());
        return convertView;
    }




    }
