package com.edward.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edward.myapplication.R;
import com.edward.myapplication.modal.RSS;

import java.util.ArrayList;

public class RSSAdapter extends BaseAdapter {
    // TODO: 9/8/2022  
    private Context context;
    private ArrayList<RSS> list ;

    public RSSAdapter(Context context, ArrayList<RSS> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            view = layoutInflater.inflate(R.layout.rss_components,viewGroup,false);
        }
        TextView title =view.findViewById(R.id.txtTitle);
        TextView des =view.findViewById(R.id.txtContent);

        title.setText(list.get(i).getTitle());
        des.setText(list.get(i).getDes());
        return view;
    }
}
