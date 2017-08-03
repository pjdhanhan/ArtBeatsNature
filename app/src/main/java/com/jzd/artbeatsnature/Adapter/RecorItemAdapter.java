package com.jzd.artbeatsnature.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzd.artbeatsnature.Applicition.MyApp;
import com.jzd.artbeatsnature.Bean.KhInforationt;
import com.jzd.artbeatsnature.R;

import java.util.List;

/**
 * Created by Wxd on 2017-06-13.
 */
public class RecorItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
private  static String TAG="RecorItemAdapter";

    private List<KhInforationt.LsgoodBean> mDatas;
    private Context context;

    public RecorItemAdapter(List<KhInforationt.LsgoodBean> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recoritem, null);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LinearLayout item = (LinearLayout) holder.itemView.findViewById(R.id.item);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams
                (MyApp.getInstance().getPhonewidth() / 3, (int) context.getResources().getDimension(R.dimen.listHight));
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.listHight));
        switch (position) {
            case 0:
                TextView tetle1 = new TextView(context);
                tetle1.setText(context.getString(R.string.code));
                tetle1.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                tetle1.setGravity(Gravity.CENTER);
                tetle1.setTextColor(context.getResources().getColor(R.color.mainColor));
                tetle1.setBackgroundColor(context.getResources().getColor(R.color.hintColor));
                tetle1.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                tetle1.setPadding(5, 5, 5, 5);
                item.addView(tetle1, params1);
                for (int i = 0; i < mDatas.size(); i++) {
                    TextView item1 = new TextView(context);
                    item1.setText(mDatas.get(i).getInventoryCode());
                    item1.setGravity(Gravity.CENTER);
                    item1.setTextColor(context.getResources().getColor(R.color.black));
                    item1.setPadding(5, 5, 5, 5);
                    item1.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                    item.addView(item1, params1);
                }
                break;
            case 1:
                TextView tetle2 = new TextView(context);
                tetle2.setText(context.getString(R.string.inventory_name));
                tetle2.setTextColor(context.getResources().getColor(R.color.mainColor));
                tetle2.setBackgroundColor(context.getResources().getColor(R.color.hintColor));
                tetle2.setPadding(5, 5, 5, 5);
                tetle2.setGravity(Gravity.CENTER);
                tetle2.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                item.addView(tetle2, params1);
                for (int i = 0; i < mDatas.size(); i++) {
                    TextView item1 = new TextView(context);
                    item1.setText(mDatas.get(i).getInventoryName());
                    item1.setGravity(Gravity.CENTER);
                    item1.setTextColor(context.getResources().getColor(R.color.black));
                    item1.setPadding(5, 5, 5, 5);
                    item1.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                    item.addView(item1, params1);
                }
                break;
            case 2:
                TextView tetle3 = new TextView(context);
                tetle3.setText(context.getString(R.string.model));
                tetle3.setTextColor(context.getResources().getColor(R.color.mainColor));
                tetle3.setBackgroundColor(context.getResources().getColor(R.color.hintColor));
                tetle3.setPadding(5, 5, 5, 5);
                tetle3.setGravity(Gravity.CENTER);
                tetle3.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                item.addView(tetle3, params1);
                for (int i = 0; i < mDatas.size(); i++) {
                    TextView item1 = new TextView(context);
                    item1.setText(mDatas.get(i).getSpecification());
                    item1.setGravity(Gravity.CENTER);
                    item1.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                    item1.setTextColor(context.getResources().getColor(R.color.black));
                    item1.setPadding(5, 5, 5, 5);
                    item.addView(item1, params1);
                }
                break;
            case 3:
                TextView tetle4 = new TextView(context);
                tetle4.setText(context.getString(R.string.number));
                tetle4.setTextColor(context.getResources().getColor(R.color.mainColor));
                tetle4.setBackgroundColor(context.getResources().getColor(R.color.hintColor));
                tetle4.setPadding(5, 5, 5, 5);
                tetle4.setGravity(Gravity.CENTER);
                tetle4.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                item.addView(tetle4, params1);
                for (int i = 0; i < mDatas.size(); i++) {
                    TextView item1 = new TextView(context);
                    item1.setText(mDatas.get(i).getInventoryNumber());
                    item1.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                    item1.setGravity(Gravity.CENTER);
                    item1.setTextColor(context.getResources().getColor(R.color.black));
                    item1.setPadding(5, 5, 5, 5);
                    item.addView(item1, params1);
                }
                break;
            case 4:
                TextView tetle5 = new TextView(context);
                tetle5.setText(context.getString(R.string.total));
                tetle5.setTextColor(context.getResources().getColor(R.color.mainColor));
                tetle5.setBackgroundColor(context.getResources().getColor(R.color.hintColor));
                tetle5.setPadding(5, 5, 5, 5);
                tetle5.setGravity(Gravity.CENTER);
                tetle5.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                item.addView(tetle5, params1);
                for (int i = 0; i < mDatas.size(); i++) {
                    TextView item1 = new TextView(context);
                    item1.setText(mDatas.get(i).getTaxPrice() + "");
                    item1.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                    item1.setGravity(Gravity.CENTER);
                    item1.setTextColor(context.getResources().getColor(R.color.black));
                    item1.setPadding(5, 5, 5, 5);
                    item1.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                    item.addView(item1, params1);
                }
                break;
            case 5:
                TextView tetle6 = new TextView(context);
                tetle6.setText(context.getString(R.string.maintaintime));
                tetle6.setTextColor(context.getResources().getColor(R.color.mainColor));
                tetle6.setBackgroundColor(context.getResources().getColor(R.color.hintColor));
                tetle6.setPadding(5, 5, 5, 5);
                tetle6.setGravity(Gravity.CENTER);
                tetle6.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                item.addView(tetle6, params2);
                for (int i = 0; i < mDatas.size(); i++) {
                    TextView item1 = new TextView(context);
                    item1.setText(DateDuplicateRemoval(mDatas.get(i).getMaintenTime()));
                    item1.setGravity(Gravity.CENTER);
                    item1.setMinWidth(MyApp.getInstance().getPhonewidth() / 3);
                    item1.setTextColor(context.getResources().getColor(R.color.black));
                    item1.setPadding(10, 5, 10, 5);
                    item1.setTextSize(context.getResources().getDimension(R.dimen.listSize));
                    item.addView(item1, params2);
                }
                break;
        }

    }
    private String DateDuplicateRemoval(String s) {
        String result="";
        String[] sArray= s.split(",");
        for (int i=0;i<sArray.length;i++){
            if (!result.contains(sArray[i])){
                result+=sArray[i]+",";
            }
        }
        if (result.substring(result.length()-1).equals(",")){
            Log.d(TAG, "DateDuplicateRemoval: "+result);
            result=result.substring(0,result.length()-1);
        }
        return result;
    }
    @Override
    public int getItemCount() {
        return 6;
    }


}
