package com.edward.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edward.myapplication.R;
import com.edward.myapplication.modal.Course;
import com.edward.myapplication.modal.User;

import java.util.ArrayList;

public class CourseAdapterLv extends BaseAdapter {
    private final Context context;
    private final ArrayList<Course> list;

    public CourseAdapterLv(Context context, ArrayList<Course> list) {
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
        TextView txtCourseName, txtContent;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        if (view == null) {
            view = inflater.inflate(R.layout.course_components_lv, null);
            viewHolder = new ViewHolder();
            viewHolder.txtCourseName = view.findViewById(R.id.txtCourseName);
            viewHolder.txtContent = view.findViewById(R.id.txtContent);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String content = "case " + list.get(i).get_Schedule() + " || " + list.get(i).get_testSchedule();
        viewHolder.txtCourseName.setText(list.get(i).get_Name());
        viewHolder.txtContent.setText(content);
        return view;
    }
}
