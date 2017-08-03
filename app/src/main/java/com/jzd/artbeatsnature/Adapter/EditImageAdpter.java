package com.jzd.artbeatsnature.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.MyBaseAdapter;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.RoundImageView.RoundImageView;
import com.liuzhen.mylibrary.Utils.XRefreshView.utils.LogUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Wxd on 2017-07-13.
 */

public class EditImageAdpter extends MyBaseAdapter {
    public final static String DELETE = "delete";
    public final static String ADD = "add";

    public EditImageAdpter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int itemLayoutRes() {
        return R.layout.editimage_item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, ViewHolder holder) {

        ImageView delete = (ImageView) holder.obtainView(convertView, R.id.delete);
        RoundImageView image = (RoundImageView) holder.obtainView(convertView, R.id.image);

        if (position != myList.size() - 1) {
            delete.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(Config.P_URL + getItem(position)).placeholder(R.mipmap.imagebg).error(R.mipmap.imagebg).into(image);
        } else {
            delete.setVisibility(View.GONE);
            Picasso.with(mContext).load(R.mipmap.addpicture).into(image);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClick != null) {
                    OnItemClick.OnItemClick(position, DELETE);
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClick != null && (myList.size() - 1 == position)) {
                    OnItemClick.OnItemClick(position, ADD);
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
