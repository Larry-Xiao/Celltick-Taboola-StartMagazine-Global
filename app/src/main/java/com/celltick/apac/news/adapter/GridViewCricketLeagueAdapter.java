package com.celltick.apac.news.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.celltick.apac.news.R;

public class GridViewCricketLeagueAdapter extends BaseAdapter {
    private Context mContext;
    private int lastPosition = -1;   //记录上一次选中的图片位置，默认不选中
    private String[] str = null;    //放问题内容文字的数组
    private String league_name = "CWC";

    public GridViewCricketLeagueAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setStr(String[] str){  //在activity中调用此方法传入问题的数组
        this.str = str;
    }

    public void setSelection(int position) { //在activity中GridView的onItemClickListener中调用此方法，来设置选中位置
        lastPosition = position;
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_cricket_option, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_choice);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(str[position]);
        if (lastPosition == position){ //判断是否为选中项，选中项与非选中项设置不同的样式
            switch (position){  //选中状态下设置样式
                case 0:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 1:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 2:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 3:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 4:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 5:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 6:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 7:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 8:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 9:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 10:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 11:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 12:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 13:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
                case 14:
                    viewHolder.textView.setBackgroundColor(Color.RED);
                    break;
            }
            league_name = viewHolder.textView.getText().toString();
        }else {  //非选中状态下设置样式
            switch (position){
                case 0:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 1:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 2:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 3:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 4:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 5:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 6:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 7:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 8:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 9:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 10:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 11:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 12:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 13:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
                case 14:
                    viewHolder.textView.setBackgroundColor(Color.WHITE);
                    break;
            }
        }
        return convertView;
    }

    class ViewHolder{
        private TextView textView;
    }

    public String getLeague_name(){
        return league_name;
    }
}