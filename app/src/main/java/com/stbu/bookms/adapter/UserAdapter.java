package com.stbu.bookms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stbu.bookms.databinding.UserItemBinding;
import com.stbu.bookms.entity.User;

import java.util.List;

/**
 * @Classname UserAdapter
 * @Description TODO 用户信息类适配器
 * Version 1.0
 */
public class UserAdapter extends RecyclerView.Adapter<VH<UserItemBinding>> {

    private final List<User> users;
    private final Context context;
    private OnItemClickListener<User> onItemClickListener;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public VH<UserItemBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH<>(UserItemBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull VH<UserItemBinding> holder, int position) {
        User user = users.get(position);
        holder.binding.tvAmount.setText("金额:"+user.getAmount());
        holder.binding.tvIdShow.setText("ID:"+user.getId());
        holder.binding.tvAddress.setText("地址:"+user.getAddress());
        holder.binding.tvPhoneNumberShow.setText("手机:"+user.getPhoneNumber());
        holder.binding.tvPwdShow.setText("密码:"+user.getPassword());
        holder.binding.tvNameShow.setText("姓名:"+user.getName());
        holder.binding.getRoot().setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.click(holder.getAdapterPosition(), user);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setOnclickCallback(OnItemClickListener<User> listener) {
        onItemClickListener = listener;
    }
}

