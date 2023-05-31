package com.stbu.bookms.adapter;

import com.stbu.bookms.entity.User;

public interface OnItemClickListener<T> {

    void click(int position, T user);

}
//接口的泛型类型 T 表示要与点击的列表项关联的数据类型，这里使用泛型的好处在于可以使用相同的接口处理不同的数据类型。方法的第一个参数 position 表示被点击的列表项的位置，第二个参数 user 表示被点击项所关联的数据对象