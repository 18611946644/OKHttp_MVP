package com.bwie.okhttp_mvp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.okhttp_mvp.R;
import com.bwie.okhttp_mvp.bean.News;

import java.util.List;

/**
 * 新闻类适配器
 * Created by DELL on 2018/10/12.
 */

public class NewsAdapter extends BaseAdapter {

    private Context context;
    private List<News.DataBean> list;

    public NewsAdapter(Context context, List<News.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_news,null);
            holder = new ViewHolder();

            holder.imgLogo = convertView.findViewById(R.id.img_logo);
            holder.txtTitle = convertView.findViewById(R.id.txt_title);
            holder.txtAuthor = convertView.findViewById(R.id.txt_author);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(list.get(position).getThumbnail01()).into(holder.imgLogo);
        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtAuthor.setText(list.get(position).getAuthor_name());

        return convertView;
    }

    class ViewHolder{
        ImageView imgLogo;
        TextView txtTitle;
        TextView txtAuthor;
    }

}
