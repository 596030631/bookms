package com.stbu.bookms.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class VH<V extends ViewBinding> extends RecyclerView.ViewHolder {
    final V binding;
    public VH(@NonNull V itemView) {
        super(itemView.getRoot());
        binding = itemView;
    }
}
//它使得 Adapter 可以使用相同的代码来处理所有 ViewHolder，从而简化代码并提高了可维护性。
//这是一个 RecyclerView 的 ViewHolder 的泛型实现，包含了一个使用 ViewBinding 进行布局绑定的 ViewHolder 的基本实现。