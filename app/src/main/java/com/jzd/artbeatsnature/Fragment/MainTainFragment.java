package com.jzd.artbeatsnature.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzd.artbeatsnature.Activity.MainTainActivity;
import com.jzd.artbeatsnature.Bean.ByBean;
import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.Comment.Contants;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.BaseFragment;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.ToastUtils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wxd on 2017-07-13.
 */

public class MainTainFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private int year, month, day;
    private DatePicker datePicker;
    private TextView deliverytime1, deliverytime2, maintaintime1, maintaintime2, time, search, Cancle;
    private EditText abbreviation, address, phone;
    private View view2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maintainfragment, null);
        intView();
        return view;

    }


    private void intView() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        View titel = view.findViewById(R.id.titel);
        view2 = view.findViewById(R.id.view2);
        setMaginTop(titel);
        datePicker = (DatePicker) view.findViewById(R.id.datepicker);
        abbreviation = (EditText) view.findViewById(R.id.abbreviation);
        address = (EditText) view.findViewById(R.id.address);
        phone = (EditText) view.findViewById(R.id.phone);

        deliverytime1 = (TextView) view.findViewById(R.id.deliverytime1);
        deliverytime2 = (TextView) view.findViewById(R.id.deliverytime2);
        maintaintime1 = (TextView) view.findViewById(R.id.maintaintime1);
        maintaintime2 = (TextView) view.findViewById(R.id.maintaintime2);
        search = (TextView) view.findViewById(R.id.search);
        Cancle = (TextView) view.findViewById(R.id.Cancle);
        deliverytime1.setOnClickListener(this);
        deliverytime2.setOnClickListener(this);
        maintaintime1.setOnClickListener(this);
        maintaintime2.setOnClickListener(this);
        Cancle.setOnClickListener(this);
        search.setOnClickListener(this);
        view2.setOnClickListener(this);
        datePicker.init(year, month, day, datechangedlistener);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view2:
                view2.setVisibility(View.GONE);
                break;
            case R.id.Cancle:
                time.setText("");
                view2.setVisibility(View.GONE);
                break;
            case R.id.search:
                BYList();
                break;
            case R.id.deliverytime1:
                time = deliverytime1;
                view2.setVisibility(View.VISIBLE);
                break;
            case R.id.deliverytime2:
                time = deliverytime2;
                view2.setVisibility(View.VISIBLE);
                break;
            case R.id.maintaintime1:
                time = maintaintime1;
                view2.setVisibility(View.VISIBLE);
                break;

            case R.id.maintaintime2:
                time = maintaintime2;
                view2.setVisibility(View.VISIBLE);
                break;
        }
    }

    /***
     * 参数 page=1
     khjc=客户简称
     fhdz=发货地址
     lxdh=联系电话
     fhsj1=发货起始时间
     fhsj2=发货截止时间
     bysj1=发货开始时间
     bysj2=发货截止时间
     */
    private void BYList() {

        if (CheckTime(deliverytime1.getText().toString(), deliverytime2.getText().toString()) &&
                CheckTime(maintaintime1.getText().toString(), maintaintime2.getText().toString())) {
            Map map = new HashMap();
            map.put("khjc", abbreviation.getText().toString());
            map.put("fhdz", address.getText().toString());
            map.put("lxdh", phone.getText().toString());
            map.put("fhsj1", deliverytime1.getText().toString());
            map.put("fhsj2", deliverytime2.getText().toString());
            map.put("bysj1", maintaintime1.getText().toString());
            map.put("bysj2", maintaintime2.getText().toString());
            startActivity(new Intent(getActivity(), MainTainActivity.class).putExtra("map", (Serializable) map));
        }
    }


    private boolean CheckTime(String time1, String time2) {
        if (time1.equals("") || time2.equals("")) {
            return true;
        } else {
            time1 = time1.replace("-", "0");
            time2 = time2.replace("-", "0");

            if (Integer.parseInt(time2) - Integer.parseInt(time1) >= 0) {
                return true;
            } else {
                ToastUtils.disPlayShort(getActivity(), "结束时间必须大于开始时间");
                return false;
            }

        }
    }

    DatePicker.OnDateChangedListener datechangedlistener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker arg0, int year, int month, int day) {
            view2.setVisibility(View.GONE);
            time.setText(year + "-" + (month + 1) + "-" + day);
        }
    };

    public boolean getVisibility() {
        if (view2.getVisibility() == View.VISIBLE) {
            view2.setVisibility(View.GONE);
            return true;
        } else {
            return false;
        }
    }
}
