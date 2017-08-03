package com.jzd.artbeatsnature.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Wxd on 2017-07-13.
 */

public class AddMainAdpter extends MyBaseAdapter {
    public final static String DELETE = "delete";
    public final static String ADD = "add";

    public AddMainAdpter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int itemLayoutRes() {
        return R.layout.addmain_item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, ViewHolder holder) {
        TextView userName = (TextView) holder.obtainView(convertView, R.id.userName);

        ImageView delete = (ImageView) holder.obtainView(convertView, R.id.delete);
        ImageView add = (ImageView) holder.obtainView(convertView, R.id.add);

        if (position != myList.size() - 1) {
            add.setVisibility(View.GONE);
            userName.setText((String) getItem(position));
            delete.setVisibility(View.VISIBLE);
        } else {
            add.setVisibility(View.VISIBLE);
            userName.setText("");
            delete.setVisibility(View.GONE);
        }
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClick != null && (position == myList.size() - 1)) {
                    OnItemClick.OnItemClick(position, ADD);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClick != null) {
                    OnItemClick.OnItemClick(position, ADD);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClick != null) {
                    OnItemClick.OnItemClick(position, DELETE);
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
