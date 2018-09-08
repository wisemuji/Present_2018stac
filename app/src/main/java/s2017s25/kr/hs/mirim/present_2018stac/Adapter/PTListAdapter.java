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
import s2017s25.kr.hs.mirim.present_2018stac.item.pt_list_item;

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
    ImageView edit_ImageView;
    ImageView play_ImageView;
    ImageView delete_ImageView;

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
            edit_ImageView = (ImageView)convertView.findViewById(R.id.edit);
            play_ImageView = (ImageView)convertView.findViewById(R.id.play);
            delete_ImageView = (ImageView)convertView.findViewById(R.id.delete);
        }
      edit_ImageView.setImageResource(list_itemArrayList.get(position).getPTedit());
        play_ImageView.setImageResource(list_itemArrayList.get(position).getPTplay());
        delete_ImageView.setImageResource(list_itemArrayList.get(position).getPTdelete());
        title_textView.setText(list_itemArrayList.get(position).getTitle());
        PTtime_textView.setText(list_itemArrayList.get(position).getPTtime());
        PTdate_textView.setText(list_itemArrayList.get(position).getPTdate());
        option_ImageView.setImageResource(list_itemArrayList.get(position).getOption());

        return convertView;

//=======
//
//            viewHolder = new ViewHolder();
//            viewHolder.title_textView = (TextView)convertView.findViewById(R.id.title_textView);
//            viewHolder.PTtime_textView = (TextView)convertView.findViewById(R.id.PTtime_textView);
//            viewHolder.start_icon_imageYiew = (ImageView)convertView.findViewById(R.id.start_pt_btn);
//            viewHolder.menu_imageView = (ImageView)convertView.findViewById(R.id.menu_imageView);
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder)convertView.getTag();
//        }
//        viewHolder.title_textView.setText(list_itemArrayList.get(position).getTitle());
//        viewHolder.PTtime_textView.setText(list_itemArrayList.get(position).getPTtime().toString());
//        viewHolder.start_icon_imageYiew.setImageResource(list_itemArrayList.get(position).getStart_icon());
//        viewHolder.menu_imageView.setImageResource(list_itemArrayList.get(position).getMenu());
//        return convertView;
//>>>>>>> master
    }
}
