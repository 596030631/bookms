package com.stbu.bookms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stbu.bookms.databinding.ItemChangeBinding;
import com.stbu.bookms.entity.ChangeInfo;

import java.util.List;

/**
 * @Classname UserAdapter
 * @Description TODO 用户信息类适配器
 * Version 1.0
 */
public class ChangeAdapter extends RecyclerView.Adapter<VH<ItemChangeBinding>> {

    private final List<ChangeInfo> data;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public ChangeAdapter(Context context, List<ChangeInfo> data) {
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
        ChangeInfo info = data.get(position);
        holder.binding.name.setText("书名:" + info.bookName);
        holder.binding.address.setText("地址:" + info.address);
        holder.binding.user.setText("买家:" + info.buyerName);
        holder.binding.sale.setText("卖家:" + info.sale_name);
        holder.binding.price.setText("价格:" + info.price);

//        holder.binding.tvIdShow.setText("ID:"+user.getId());
//        holder.binding.tvAddress.setText("地址:"+user.getAddress());
//        holder.binding.tvPhoneNumberShow.setText("手机:"+user.getPhoneNumber());
//        holder.binding.tvPwdShow.setText("密码:"+user.getPassword());
//        holder.binding.tvNameShow.setText("姓名:"+user.getName());
//        holder.binding.getRoot().setOnClickListener(view -> {
//            if (onItemClickListener != null) {
//                onItemClickListener.click(holder.getAdapterPosition(), user);
//            }
//        });
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

