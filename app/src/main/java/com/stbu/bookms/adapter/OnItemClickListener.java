package com.stbu.bookms.adapter;

import com.stbu.bookms.entity.User;

public interface OnItemClickListener<T> {

    void click(int position, T user);

}
