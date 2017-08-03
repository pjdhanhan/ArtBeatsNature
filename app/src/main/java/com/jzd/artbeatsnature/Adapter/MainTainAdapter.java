package com.jzd.artbeatsnature.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.artbeatsnature.Bean.ByBean;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Wxd on 2017-07-14.
 */

public class MainTainAdapter extends MyBaseAdapter {
    private static String TAG="MainTainAdapter";
    public static final String MAP = "MAP";
    public static final String PHONE = "PHONE";
    public static final String MESSAGE = "MESSAGE";
    private List list;

    public MainTainAdapter(Context context, List list) {

        super(context, list);
        this.list = list;
    }

    @Override
    public int itemLayoutRes() {
        return R.layout.maintain_item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, ViewHolder holder) {
        View map = holder.obtainView(convertView, R.id.map);
        View phone = holder.obtainView(convertView, R.id.phone);
        View message = holder.obtainView(convertView, R.id.message);

        TextView maintain_customer = (TextView) holder.obtainView(convertView, R.id.maintain_customer);
        TextView maintain_phone = (TextView) holder.obtainView(convertView, R.id.maintain_phone);
        TextView maintain_address = (TextView) holder.obtainView(convertView, R.id.maintain_address);
        TextView deliverytime = (TextView) holder.obtainView(convertView, R.id.deliverytime);

        TextView maintaintime = (TextView) holder.obtainView(convertView, R.id.maintaintime);
        ByBean item = (ByBean) list.get(position);

        maintain_customer.setText(item.getAbbreviation());
        maintain_phone.setText(item.getContactNumber());
        maintain_address.setText(item.getShipAddress());
        deliverytime.setText(DateDuplicateRemoval(item.getShipDate()));
        maintaintime.setText(DateDuplicateRemoval(item.getMaintenTime()));
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClick != null) {
                    OnItemClick.OnItemClick(position, MAP);
                }
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClick != null) {
                    OnItemClick.OnItemClick(position, PHONE);
                }
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClick != null) {
                    OnItemClick.OnItemClick(position, MESSAGE);
                }
            }
        });
        return convertView;
    }


    private OnItemClick OnItemClick;

    public interface OnItemClick {
        void OnItemClick(int position, String type);
    }

    public void OnItemClick(OnItemClick OnItemClick) {
        this.OnItemClick = OnItemClick;
    }
}
