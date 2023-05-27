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
