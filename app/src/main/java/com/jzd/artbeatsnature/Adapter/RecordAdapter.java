package com.jzd.artbeatsnature.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzd.artbeatsnature.Bean.RecordBean;
import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.MyBaseAdapter;
import com.liuzhen.mylibrary.Utils.NoScrollView.NoScrollGridView;
import com.liuzhen.mylibrary.Utils.RoundImageView.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wxd on 2017-07-13.
 */

public class RecordAdapter extends MyBaseAdapter {

    public final static String EDIT = "edit";
    public final static String PICTURE = "picture";


    public RecordAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int itemLayoutRes() {
        return R.layout.record_item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, ViewHolder holder) {
        TextView edit = (TextView) holder.obtainView(convertView, R.id.edit);
        TextView auditstatus = (TextView) holder.obtainView(convertView, R.id.auditstatus);
        TextView maintaintime = (TextView) holder.obtainView(convertView, R.id.maintaintime);
        TextView maintainpeople = (TextView) holder.obtainView(convertView, R.id.maintainpeople);
        TextView abbreviation = (TextView) holder.obtainView(convertView, R.id.abbreviation);
        TextView address = (TextView) holder.obtainView(convertView, R.id.address);
        TextView summary = (TextView) holder.obtainView(convertView, R.id.summary);
        ImageView handsign = (ImageView) holder.obtainView(convertView, R.id.handsign);

        NoScrollGridView pictures = (NoScrollGridView) holder.obtainView(convertView, R.id.pictures);

        RecordBean bean = (RecordBean) getItem(position);
        summary.setText(bean.getSummary());
        Picasso.with(mContext).load(Config.P_URL + bean.getSignFile()).into(handsign);


        abbreviation.setText(bean.getAbbreviation());
        address.setText(bean.getShipAddress());
        maintaintime.setText(bean.getMaintenTime().replace(",", "\n"));
        maintainpeople.setText(bean.getMaintenPerson().replace(",", "\n"));

        if (bean.getAuditStatus().equals("0")) {//0 未审核      1 已审核
            auditstatus.setText("未审核");
            auditstatus.setTextColor(mContext.getResources().getColor(R.color.mainColor));
            edit.setVisibility(View.VISIBLE);
        } else if (bean.getAuditStatus().equals("1")) {
            auditstatus.setText("已审核");
            auditstatus.setTextColor(Color.GREEN);
            edit.setVisibility(View.GONE);
        } else {
            auditstatus.setText("待审核");
            auditstatus.setTextColor(mContext.getResources().getColor(R.color.mainColor));
            edit.setVisibility(View.VISIBLE);
        }

        List<String> list = new ArrayList<>();

        if (!bean.getPictures().equals("")) {
            String[] pic = bean.getPictures().split(",");
            for (int i = 0; i < pic.length; i++) {
                list.add(pic[i]);
            }
            PicturesAdpter adpter = new PicturesAdpter(mContext, list);
            pictures.setAdapter(adpter);
            pictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int posit, long id) {
                    if (OnItemClick != null) {
                        OnItemClick.OnItemClick(position, posit, PICTURE);
                    }
                }
            });
        }else{
            list.clear();
            PicturesAdpter adpter = new PicturesAdpter(mContext, list);
            pictures.setAdapter(adpter);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClick != null) {
                    OnItemClick.OnItemClick(position, 0, EDIT);
                }
            }
        });
        return convertView;
    }

    private OnItemClick OnItemClick;

    public interface OnItemClick {
        void OnItemClick(int position, int position2, String type);
    }

    public void OnItemClick(OnItemClick OnItemClick) {
        this.OnItemClick = OnItemClick;
    }
}
