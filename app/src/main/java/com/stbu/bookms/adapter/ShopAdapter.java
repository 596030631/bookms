package com.stbu.bookms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stbu.bookms.databinding.ItemShopBinding;
import com.stbu.bookms.entity.Book;

import java.util.List;

/**
 * @Classname UserAdapter
 * @Description TODO 用户信息类适配器
 * Version 1.0
 */
public class ShopAdapter extends RecyclerView.Adapter<VH<ItemShopBinding>> {

    private final List<Book> data;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public ShopAdapter(Context context, List<Book> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public VH<ItemShopBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH<>(ItemShopBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull VH<ItemShopBinding> holder, int position) {
        Book info = data.get(position);
        holder.binding.name.setText("书名:" + info.getBookName());
        holder.binding.id.setText(String.format("No.%d", position + 1));
        holder.binding.price.setText(String.format("价格:" + info.getPrice()));

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

