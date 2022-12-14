package com.edward.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edward.myapplication.R;
import com.edward.myapplication.modal.User;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    // TODO: 9/9/2022
    private final Context context;
    private final ArrayList<User> list;

    public UserAdapter(Context context, ArrayList<User> list) {
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
    class ViewHolder {
        TextView txtUserName, txtUserId;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        if (view == null) {
            view = inflater.inflate(R.layout.user_components, null);
            viewHolder = new ViewHolder();
            viewHolder.txtUserName = view.findViewById(R.id.txtUserName);
            viewHolder.txtUserId = view.findViewById(R.id.txtUserId);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtUserName.setText(list.get(i).get_FullName());
        viewHolder.txtUserId.setText(list.get(i).get_ID());
        return view;
    }
}
