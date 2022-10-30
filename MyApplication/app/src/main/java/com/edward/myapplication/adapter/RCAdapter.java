package com.edward.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.edward.myapplication.R;
import com.edward.myapplication.databinding.ItemBinding;
import com.edward.myapplication.models.User;

import java.util.List;

public class RCAdapter extends RecyclerView.Adapter<RCAdapter.ViewHolder> {


    public static Context context;
    public static List<User> ls;
    public static RecycleCallBack recycleCallBack;

    public RCAdapter(Context context, List<User> ls, RecycleCallBack recycleCallBack) {
        RCAdapter.context = context;
        RCAdapter.ls = ls;
        RCAdapter.recycleCallBack =recycleCallBack;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBinding itemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ls.get(position));
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemBinding itemBinding;

        public ViewHolder(ItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            itemBinding.getRoot().setOnClickListener(view -> recycleCallBack.onClickComputer(ls.get(getAdapterPosition())));
        }

        public void bind(User user) {
            itemBinding.setUser(user);
            itemBinding.executePendingBindings();
        }
    }


}