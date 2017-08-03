package com.jzd.artbeatsnature.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.MyBaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Wxd on 2017-07-13.
 */

public class PicturesAdpter extends MyBaseAdapter {
    public PicturesAdpter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int itemLayoutRes() {
        return R.layout.picture_item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, ViewHolder holder) {
        ImageView image = (ImageView) holder.obtainView(convertView, R.id.image);
        Picasso.with(mContext).load(Config.P_URL + getItem(position))
                .error(R.mipmap.imagebg)
                .placeholder(R.mipmap.imagebg).into(image);
        return convertView;
    }
}
