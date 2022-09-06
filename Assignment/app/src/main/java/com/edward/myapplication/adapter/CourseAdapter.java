package com.edward.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edward.myapplication.R;
import com.edward.myapplication.modal.Course;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Course> list;

    public CourseAdapter(Context context, ArrayList<Course> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseName;
        private final TextView content;
        private final Button register;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.txtCourseName);
            content = itemView.findViewById(R.id.txtContent);
            register = itemView.findViewById(R.id.btnRegister);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View _view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_course_components, parent, false);
        return new ViewHolder(_view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course tempCourse = list.get(position);
        if (tempCourse == null){
            return;
        }
        String content = "case " + tempCourse.get_Schedule() + " || " + tempCourse.get_testSchedule();
        holder.courseName.setText(tempCourse.get_Name());
        holder.content.setText(content);

        if (tempCourse.isRegister()){
            holder.register.setText("UnRegister");
            holder.register.setBackgroundColor(Color.parseColor("#D65418"));
        }
        else {
            holder.register.setText("Register");
            holder.register.setBackgroundColor(Color.parseColor("#1884D6"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
