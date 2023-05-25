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
import com.stbu.bookmanagementsystem.entity.Borrow;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname BorrowAdapter
 * @Description TODO 借阅信息类适配器
 * Version 1.0
 */
public class BorrowAdapter extends BaseAdapter {
    private ArrayList<Borrow> borrows;
    private Context context;
    private ViewHolder viewHolder;

    public BorrowAdapter(Context context, ArrayList<Borrow> borrows) {
        this.borrows = borrows;
        this.context = context;
    }

    @Override
    public int getCount() {
        return borrows.size();
    }

    @Override
    public Object getItem(int position) {
        return borrows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Borrow borrow = (Borrow) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.borrow_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tv_borrow_id_show = convertView.findViewById(R.id.tv_borrow_id_show);
            viewHolder.tv_borrow_book_id_show = convertView.findViewById(R.id.tv_borrow_book_id_show);
            viewHolder.tv_borrow_book_name_show = convertView.findViewById(R.id.tv_borrow_book_name_show);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_borrow_id_show.setText(borrow.getBorrowId());
        viewHolder.tv_borrow_book_id_show.setText(borrow.getBorrowBookId());
        viewHolder.tv_borrow_book_name_show.setText(borrow.getBorrowBookName());
        return convertView;
    }

    private static class ViewHolder {
        private TextView tv_borrow_id_show;
        private TextView tv_borrow_book_id_show;
        private TextView tv_borrow_book_name_show;
    }
}
