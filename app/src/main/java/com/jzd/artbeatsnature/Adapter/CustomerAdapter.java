package com.jzd.artbeatsnature.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.artbeatsnature.Bean.KhInforationt;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Wxd on 2017-07-14.
 */

public class CustomerAdapter extends MyBaseAdapter {


    private LinearLayoutManager mLinearLayoutManager;
    public static final String MOVERIGHT = "moveright";
    public static final String MOVELEFT = "moveleft";

    public CustomerAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int itemLayoutRes() {
        return R.layout.customer_item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, ViewHolder holder) {
        View head = holder.obtainView(convertView, R.id.head);

        TextView maintain_time = (TextView) holder.obtainView(convertView, R.id.maintain_time);
        TextView salesman = (TextView) holder.obtainView(convertView, R.id.salesman);
        RecyclerView mRecycleView = (RecyclerView) holder.obtainView(convertView, R.id.recycler);

        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        KhInforationt khinforationt = (KhInforationt) getItem(position);


        RecorItemAdapter adapter = new RecorItemAdapter(khinforationt.getLsgood(), mContext);
        mRecycleView.setAdapter(adapter);


        if (position == 0) {
            head.setVisibility(View.VISIBLE);
        } else {
            head.setVisibility(View.GONE);
        }

        maintain_time.setText(khinforationt.getShipDate());
        salesman.setText(khinforationt.getSalesman());
        return convertView;
    }

}
