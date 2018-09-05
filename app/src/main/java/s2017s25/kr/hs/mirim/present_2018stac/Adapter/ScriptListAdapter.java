package s2017s25.kr.hs.mirim.present_2018stac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.R;
import s2017s25.kr.hs.mirim.present_2018stac.item.script_list_item;

public class ScriptListAdapter extends BaseAdapter {

    private static final int ITEM_VIEW_TYPE_Script = 0 ;
    private static final int ITEM_VIEW_TYPE_Keypoint = 1 ;
    private static final int ITEM_VIEW_TYPE_MAX = 2 ;

    ArrayList<script_list_item> list_itemArrayList = new ArrayList<script_list_item>();

    public ScriptListAdapter() { }

    @Override
    public int getCount() { return this.list_itemArrayList.size(); }

    @Override
    public Object getItem(int position) { return this.list_itemArrayList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override public int getViewTypeCount() { return ITEM_VIEW_TYPE_MAX ; }

    @Override
    public int getItemViewType(int position) { return list_itemArrayList.get(position).getType() ; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        int viewType = getItemViewType(position) ;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

            switch (viewType) {
                case ITEM_VIEW_TYPE_Script:
                convertView = inflater.inflate(R.layout.script_item, parent, false);

                    TextView startTime=(TextView)convertView.findViewById(R.id.script_start_time);
                    TextView endTime=(TextView)convertView.findViewById(R.id.script_end_time);
                    TextView content=(TextView)convertView.findViewById(R.id.script_list_content);

                    content.setText(list_itemArrayList.get(position).getStartTime());
                    content.setText(list_itemArrayList.get(position).getEndTime());
                    content.setText(list_itemArrayList.get(position).getContent());

                break;
                case ITEM_VIEW_TYPE_Keypoint:
                    convertView = inflater.inflate(R.layout.keypoint_item, parent, false);
                   TextView title=(TextView)convertView.findViewById(R.id.keypoint_list_title);
                   TextView vibTime=(TextView)convertView.findViewById(R.id.keypoint_list_vibtime);

                    title.setText(list_itemArrayList.get(position).getTitle());
                    vibTime.setText(list_itemArrayList.get(position).getVibTime());

                    break;
            }
        }

        return convertView;
    }

     public void addItem(String startTime,String endTime, String content) {
        script_list_item item = new script_list_item() ;
        item.setType(ITEM_VIEW_TYPE_Script) ;
        item.setStartTime(startTime);
        item.setEndTime(endTime);
        item.setContent(content); ;
        list_itemArrayList.add(item) ; }

    public void addItem(String title, String vibTime) {
        script_list_item item = new script_list_item() ;
        item.setType(ITEM_VIEW_TYPE_Keypoint);
        item.setTitle(title);
        item.setVibTime(vibTime); ;
        list_itemArrayList.add(item) ; }
}
