package com.stbu.bookms.adapter;

import static com.stbu.bookms.activity.ExchangeActivity.LOGIN_USER;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stbu.bookms.databinding.BookItemBinding;
import com.stbu.bookms.entity.Book;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
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
    private OnItemClickListener<Book> onItemClickListenerRemake;
    private OnItemClickListener<Book> onItemClickListenerRemakeDel;

    public void setOnItemClickListener(OnItemClickListener<Book> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemClickListenerRemake(OnItemClickListener<Book> onItemClickListener) {
        this.onItemClickListenerRemake = onItemClickListener;
    }

    public void setOnItemClickListenerRemakeDel(OnItemClickListener<Book> onItemClickListener) {
        this.onItemClickListenerRemakeDel = onItemClickListener;
    }

    public BookAdapter(Context context, List<Book> books) {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public VH<BookItemBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH<>(BookItemBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    private Gson gson = new Gson();

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
        holder.binding.tvJianjie.setText("图书作者:" + book.getBookContent());
        holder.binding.tvAuth.setText("所有者:" + book.getBookAuth());
        holder.binding.tvBookIdShow.setText(book.getBookId());
        holder.binding.tvBookNameShow.setText(book.getBookName());

        Type type = new TypeToken<List<Book.BookRemake>>() {
        }.getType(); // 使用 TypeToken 获取要解析的类型
        List<Book.BookRemake> personList = gson.fromJson(book.getRemakeJson(), type);

        if (personList != null) {
            StringBuilder sd = new StringBuilder();
            for (int i = 0; i < personList.size(); i++) {
                sd.append(personList.get(i).userName).append(":").append(personList.get(i).remake).append('\n');
            }
            holder.binding.tvRemakkkk.setText(sd.toString());

        }

        if (onItemClickListener != null) {
            holder.binding.btnChange.setVisibility(View.VISIBLE);
            holder.binding.btnChange.setOnClickListener(view -> onItemClickListener.click(position, book));
        } else {
            holder.binding.btnChange.setVisibility(View.GONE);
        }

        if (onItemClickListenerRemakeDel != null) {
            holder.binding.btnDelRemake.setVisibility(View.VISIBLE);
            holder.binding.btnDelRemake.setOnClickListener(view -> onItemClickListenerRemakeDel.click(position, book));
        } else {
            holder.binding.btnDelRemake.setVisibility(View.GONE);
        }


        if (onItemClickListenerOpt == null) {
            holder.binding.btnOpt.setVisibility(View.GONE);
        } else {
            holder.binding.btnOpt.setVisibility(View.VISIBLE);
            holder.binding.btnOpt.setOnClickListener(view -> onItemClickListenerOpt.click(position, book));
        }

        if (onItemClickListenerRemake == null || "admin".equals(LOGIN_USER)) {
            holder.binding.btnRemake.setVisibility(View.GONE);
            holder.binding.etRemake.setVisibility(View.GONE);
        } else {
            holder.binding.btnRemake.setVisibility(View.VISIBLE);
            holder.binding.etRemake.setVisibility(View.VISIBLE);
            Editable editable = holder.binding.etRemake.getText();
            if (editable == null) {
                Toast.makeText(context, "请先填写评论", Toast.LENGTH_SHORT).show();
            } else {


                holder.binding.btnRemake.setOnClickListener(view -> {
                    book.setAddRemake(editable.toString());
                    onItemClickListenerRemake.click(position, book);
                    holder.binding.etRemake.setText("");
                    Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                });
            }
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
