package com.stbu.bookmanagementsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.stbu.bookmanagementsystem.R;
import com.stbu.bookmanagementsystem.entity.User;

import java.util.ArrayList;

/**
 * @Classname UserAdapter
 * @Description TODO 用户信息类适配器
 * Version 1.0
 */
public class UserAdapter extends BaseAdapter {
    private ArrayList<User> users;
    private Context context;
    private ViewHolder viewHolder;


    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User user = (User) this.getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tv_id_show = convertView.findViewById(R.id.tv_id_show);
            viewHolder.tv_name_show = convertView.findViewById(R.id.tv_name_show);
            viewHolder.tv_class_name_show = convertView.findViewById(R.id.tv_class_name_show);
            viewHolder.tv_password_show = convertView.findViewById(R.id.tv_pwd_show);
            viewHolder.tv_phone_number_show = convertView.findViewById(R.id.tv_phone_number_show);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_id_show.setText(user.getId());
        viewHolder.tv_name_show.setText(user.getName());
        viewHolder.tv_class_name_show.setText(user.getClassName());
        viewHolder.tv_password_show.setText(user.getPassword());
        viewHolder.tv_phone_number_show.setText(user.getPhoneNumber());

        return convertView;
    }

    private static class ViewHolder {
        private TextView tv_id_show;
        private TextView tv_name_show;
        private TextView tv_class_name_show;
        private TextView tv_password_show;
        private TextView tv_phone_number_show;
    }
}

