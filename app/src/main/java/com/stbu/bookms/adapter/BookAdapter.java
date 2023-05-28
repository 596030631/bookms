package com.stbu.bookms.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stbu.bookms.databinding.BookItemBinding;
import com.stbu.bookms.entity.Book;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Classname BookAdapter
 * @Description TODO 图书信息类适配器
 * Version 1.0
 */
public class BookAdapter extends RecyclerView.Adapter<VH<BookItemBinding>> {
    private List<Book> books;
    private Context context;
    private OnItemClickListener<Book> onItemClickListener;
    private OnItemClickListener<Book> onItemClickListenerOpt;

    public void setOnItemClickListener(OnItemClickListener<Book> onItemClickListener) {
        Log.d("tag", "lllll");
        this.onItemClickListener = onItemClickListener;
    }

    public BookAdapter(Context context, List<Book> books) {
        Log.d("tag", "ttttttt");
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public VH<BookItemBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH<>(BookItemBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH<BookItemBinding> holder, int position) {
        final Book book = books.get(position);

        InputStream inputStream;
        try {
            inputStream = context.getAssets().open("book/" + book.getImage());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            holder.binding.image.setImageBitmap(bitmap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        holder.binding.tvCategory.setText("类别:" + book.getBookCategory());
        holder.binding.tvJianjie.setText("简介:" + book.getBookContent());
        holder.binding.tvAuth.setText("所有者:" + book.getBookAuth());
        holder.binding.tvBookIdShow.setText(book.getBookId());
        holder.binding.tvBookNameShow.setText(book.getBookName());


        if (onItemClickListener != null) {
            holder.binding.btnChange.setVisibility(View.VISIBLE);
            holder.binding.btnChange.setOnClickListener(view -> onItemClickListener.click(position, book));
        } else {
            holder.binding.btnChange.setVisibility(View.GONE);
        }


        if (onItemClickListenerOpt == null) {
            holder.binding.btnOpt.setVisibility(View.GONE);
        } else {
            holder.binding.btnOpt.setVisibility(View.VISIBLE);
            holder.binding.btnOpt.setOnClickListener(view -> onItemClickListenerOpt.click(position, book));
        }


        holder.binding.tvBookBalanceShow.setText(String.valueOf(book.getBookNumber()));

    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }


    public void setOnItemClickListenerOpt(OnItemClickListener<Book> bookOnItemClickListener) {
        this.onItemClickListenerOpt = bookOnItemClickListener;
    }
}
