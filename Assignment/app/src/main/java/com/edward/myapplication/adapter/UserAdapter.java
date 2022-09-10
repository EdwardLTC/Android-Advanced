package com.edward.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edward.myapplication.R;
import com.edward.myapplication.modal.User;

import java.util.ArrayList;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.ViewHolder>{
    // TODO: 9/9/2022
    private final Context context;
    private final ArrayList<User> list;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName,userID;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.txtUserName);
            userID = itemView.findViewById(R.id.txtUserId);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View _view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_components, parent, false);
        return new ViewHolder(_view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User tempUser = list.get(position);
        if (tempUser == null){
            return;
        }
        holder.userName.setText(tempUser.get_FullName());
        holder.userID.setText(tempUser.get_ID());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
