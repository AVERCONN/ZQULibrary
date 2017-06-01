package com.zqu.library.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqu.library.R;

import java.util.List;

/**
 * Created by chen on 2016/3/14.
 */
public class LNewsAdapter extends ArrayAdapter<LibraryNews> {
    private int resourceId;
    public LNewsAdapter(Context context,int textViewResourceId,List<LibraryNews> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LibraryNews news = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView newsImage=(ImageView) view.findViewById(R.id.ln_image);
        TextView newsTitle = (TextView) view.findViewById(R.id.ln_title);
        TextView newsDate = (TextView) view.findViewById(R.id.ln_date);
        newsImage.setImageResource(news.getImageId());
        newsTitle.setText(news.getTitle());
        newsDate.setText(news.getDate());
        return view;
    }
}

