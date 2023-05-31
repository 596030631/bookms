package com.stbu.bookms.adapter;

import static com.stbu.bookms.activity.ExchangeActivity.LOGIN_USER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stbu.bookms.databinding.ItemExchangeBinding;
import com.stbu.bookms.entity.ExchangeInfo;

import java.util.List;

/**
 * @Classname UserAdapter
 * @Description TODO 用户信息类适配器
 * Version 1.0
 */
public class ExchangeAdapter extends RecyclerView.Adapter<VH<ItemExchangeBinding>> {

    private final List<ExchangeInfo> data;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public ExchangeAdapter(Context context, List<ExchangeInfo> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public VH<ItemExchangeBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH<>(ItemExchangeBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull VH<ItemExchangeBinding> holder, int position) {//viewgone不可见
        ExchangeInfo info = data.get(position);
        if (LOGIN_USER.equals(info.loginUser)) {
            holder.binding.tv1.setVisibility(View.GONE);
            holder.binding.userLeft.setVisibility(View.GONE);
            holder.binding.tv2.setVisibility(View.VISIBLE);
            holder.binding.userRight.setVisibility(View.VISIBLE);
            holder.binding.tv2.setText(info.message);
        } else {
            holder.binding.tv2.setVisibility(View.GONE);
            holder.binding.userRight.setVisibility(View.GONE);
            holder.binding.tv1.setVisibility(View.VISIBLE);
            holder.binding.userLeft.setVisibility(View.VISIBLE);
            holder.binding.tv1.setText(info.message);
        }

//
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnclickCallback(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}

