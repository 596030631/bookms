package com.stbu.bookms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stbu.bookms.databinding.ItemFriendBinding;
import com.stbu.bookms.entity.ExchangeInfo;

import java.util.List;

/**
 * @Classname UserAdapter
 * @Description TODO 用户信息类适配器
 * Version 1.0
 */
public class FriendsAdapter extends RecyclerView.Adapter<VH<ItemFriendBinding>> {

    private final List<ExchangeInfo> data;
    private final Context context;
    private OnItemClickListener<ExchangeInfo> onItemClickListener;

    public FriendsAdapter(Context context, List<ExchangeInfo> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public VH<ItemFriendBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH<>(ItemFriendBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull VH<ItemFriendBinding> holder, int position) {
        ExchangeInfo info = data.get(position);

        holder.binding.user.setText(info.loginUser);
        holder.binding.message.setText(info.message);


        holder.binding.getRoot().setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.click(holder.getAdapterPosition(), info);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnclickCallback(OnItemClickListener<ExchangeInfo> listener) {
        onItemClickListener = listener;
    }
}

