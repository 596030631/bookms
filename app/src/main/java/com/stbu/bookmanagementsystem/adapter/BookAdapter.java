package com.stbu.bookmanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stbu.bookmanagementsystem.R;
import com.stbu.bookmanagementsystem.entity.Book;
import com.stbu.bookmanagementsystem.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname BookAdapter
 * @Description TODO 图书信息类适配器
 * Version 1.0
 */
public class BookAdapter extends BaseAdapter {
    private ArrayList<Book> books;
    private Context context;
    private ViewHolder viewHolder;

    public BookAdapter(Context context, ArrayList<Book> books) {
        this.books = books;
        this.context = context;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Book book = (Book) this.getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tv_book_id_show = convertView.findViewById(R.id.tv_book_id_show);
            viewHolder.tv_book_name_show = convertView.findViewById(R.id.tv_book_name_show);
            viewHolder.tv_book_balance_show = convertView.findViewById(R.id.tv_book_balance_show);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_book_id_show.setText(book.getBookId());
        viewHolder.tv_book_name_show.setText(book.getBookName());
        viewHolder.tv_book_balance_show.setText(String.valueOf(book.getBookNumber()));
        return convertView;
    }

    private static class ViewHolder {
        private TextView tv_book_id_show;
        private TextView tv_book_name_show;
        private TextView tv_book_balance_show;
    }
}
