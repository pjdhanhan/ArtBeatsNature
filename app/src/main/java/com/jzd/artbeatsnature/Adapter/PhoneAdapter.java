package com.jzd.artbeatsnature.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.MyBaseAdapter;

import java.util.List;

/**
 * Created by yu on 2017-08-01.
 */

public class PhoneAdapter extends MyBaseAdapter {
    public PhoneAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int itemLayoutRes() {
        return R.layout.simple_list_item_2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, ViewHolder holder) {
        TextView phone= (TextView) holder.obtainView(convertView,R.id.text1);
                phone.setText((String)getItem(position));
        return convertView;
    }
}
