package com.jzd.artbeatsnature.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzd.artbeatsnature.Bean.RecordLsBean;
import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.MyBaseAdapter;
import com.liuzhen.mylibrary.Utils.NoScrollView.NoScrollGridView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wxd on 2017-07-14.
 */

public class MainTainDetailsAdapter extends MyBaseAdapter {
    public final static String EDIT = "edit";
    public final static String PICTURE = "picture";
    private static String TAG="MainTainDetailsAdapter";

    public MainTainDetailsAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int itemLayoutRes() {
        return R.layout.naintaindetails_item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, ViewHolder holder) {
        NoScrollGridView pictures = (NoScrollGridView) holder.obtainView(convertView, R.id.pictures);

        TextView maintaintime = (TextView) holder.obtainView(convertView, R.id.maintaintime);
        TextView maintainpeople = (TextView) holder.obtainView(convertView, R.id.maintainpeople);
        ImageView handsign = (ImageView) holder.obtainView(convertView, R.id.handsign);
        TextView summary = (TextView) holder.obtainView(convertView, R.id.summary);
        RecordLsBean recordlsbean = (RecordLsBean) getItem(position);


        maintaintime.setText(recordlsbean.getMaintenTime().replace(",", "\n"));
        maintainpeople.setText(recordlsbean.getMaintenPerson().replace(",", "\n"));
        Picasso.with(mContext).load(Config.P_URL + recordlsbean.getSignFile()).into(handsign);
        summary.setText(recordlsbean.getSummary());
        Log.d(TAG, "getView: 位置:"+position);
        if (!recordlsbean.getPictures().equals("")){
            String[] pics = recordlsbean.getPictures().split(",");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < pics.length; i++) {
                Log.d(TAG, "getView: "+pics[i]);
                list.add(pics[i]);
            }
            PicturesAdpter adpter = new PicturesAdpter(mContext, list);
            pictures.setAdapter(adpter);
            pictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int posi, long id) {
                    if (OnItemClick != null) {
                        OnItemClick.OnItemClick(position, posi, PICTURE);
                    }
                }
            });
        }else{
            List<String> list = new ArrayList<>();
            list.clear();
            PicturesAdpter adpter = new PicturesAdpter(mContext, list);
            pictures.setAdapter(adpter);
        }
        return convertView;
    }

    private OnItemClick OnItemClick;

    public interface OnItemClick {
        void OnItemClick(int position1, int position2, String type);
    }

    public void OnItemClick(OnItemClick OnItemClick) {
        this.OnItemClick = OnItemClick;
    }
}
