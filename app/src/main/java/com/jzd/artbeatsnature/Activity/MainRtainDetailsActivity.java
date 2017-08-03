package com.jzd.artbeatsnature.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jzd.artbeatsnature.Adapter.MainTainDetailsAdapter;
import com.jzd.artbeatsnature.Adapter.RecordAdapter;
import com.jzd.artbeatsnature.Bean.ByBean;
import com.jzd.artbeatsnature.Bean.RecordLsBean;
import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.Comment.Contants;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.ToastUtils.ToastUtils;
import com.liuzhen.mylibrary.Utils.XRefreshView.XRefreshView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainRtainDetailsActivity extends BaseActivity {
    private ListView listview;
    private XRefreshView xRefreshView;

    private List<RecordLsBean> list = new ArrayList<>();
    private int page = 1;
    private MainTainDetailsAdapter adapter;
    private ByBean CustomerDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(R.color.mainColor);
        setContentView(R.layout.activity_mainrtaindetails);
        if (getIntent().hasExtra("CustomerDetail")) {
            CustomerDetail = (ByBean) getIntent().getSerializableExtra("CustomerDetail");
        }
        intView();
        RecordLsjl();
    }

    private void RecordLsjl() {
        showProgressBar();
        Map map = new HashMap();
        map.put("page", "" + page);
        map.put("id", CustomerDetail.getId());
        LogUtil.logWrite("map", map.toString());
        get(Config.RecordLsjl, map, Contants.RecordLsjl);
    }


    @Override
    public void analysisErr(String e) {

        ToastUtils.disPlayShort(this, e);
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
        cancleProgressBar();
    }

    @Override
    public <T> void analysis(T result, int flag) {
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
        cancleProgressBar();
        switch (flag) {
            case Contants.RecordLsjl:
                LogUtil.logWrite("map", (String) result);
                try {
                    JSONArray array = new JSONArray(result.toString());
                    List<RecordLsBean> recordls = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        RecordLsBean recordl = new Gson().fromJson(array.getJSONObject(i).toString(), RecordLsBean.class);
                        if (recordl.getAbbreviation() != null) {
                            recordls.add(recordl);
                        }
                    }
                    if (recordls.size() > 0) {
                        list.addAll(recordls);
                    } else {
                        if (page > 1) {
                            page--;
                        }
                        return;
                    }
                    if (adapter == null) {
                        adapter = new MainTainDetailsAdapter(this, list);
                        listview.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    listview.smoothScrollToPosition(list.size() - recordls.size());
                    adapter.OnItemClick(onitemclick);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void intView() {
        findViewsetlisten(R.id.tv_back);
        xRefreshView = (XRefreshView) findViewById(R.id.xrefreshview);
        listview = (ListView) findViewById(R.id.listview);
        xRefreshView.setXRefreshViewListener(listen);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    private XRefreshView.SimpleXRefreshListener listen = new XRefreshView.SimpleXRefreshListener() {

        @Override
        public void onRefresh() {
            page = 1;
            list.clear();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            RecordLsjl();
        }

        @Override
        public void onLoadMore(boolean isSilence) {
            page++;
            RecordLsjl();
        }
    };

    MainTainDetailsAdapter.OnItemClick onitemclick = new MainTainDetailsAdapter.OnItemClick() {
        @Override
        public void OnItemClick(int position, int pos, String type) {
            switch (type) {

                case RecordAdapter.PICTURE:
                    final String[] Picture = list.get(position).getPictures().split(",");
                    startActivityForResult(new Intent(MainRtainDetailsActivity.this,
                            ImageDailogActivity.class)
                            .putExtra("position", pos)
                            .putExtra("Picture", Picture), 10);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                break;
        }
    }
}

