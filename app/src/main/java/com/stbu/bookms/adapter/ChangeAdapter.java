package com.stbu.bookms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stbu.bookms.databinding.ItemChangeBinding;
import com.stbu.bookms.entity.Borrow;

import java.util.List;

/**
 * @Classname UserAdapter
 * @Description TODO 用户信息类适配器
 * Version 1.0
 */
public class ChangeAdapter extends RecyclerView.Adapter<VH<ItemChangeBinding>> {

    private final List<Borrow> data;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public ChangeAdapter(Context context, List<Borrow> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public VH<ItemChangeBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH<>(ItemChangeBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull VH<ItemChangeBinding> holder, int position) {
        Borrow info = data.get(position);
        holder.binding.name.setText("书名:" + info.getBorrowBookName());
        holder.binding.address.setText("地址:" + info.getAddress());
        holder.binding.user.setText("图书交换人:" + info.getBuyer_name());
        holder.binding.sale.setText("图书所有人:" + info.getSale_name());
        holder.binding.price.setText("价格:" + info.getPrice());
        holder.binding.datetime.setText("时间:" + info.getDatetime());
        holder.binding.remake.setText("评论:" + info.getRemake());

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

