package com.jzd.artbeatsnature.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jzd.artbeatsnature.Adapter.RecordAdapter;
import com.jzd.artbeatsnature.Applicition.MyApp;
import com.jzd.artbeatsnature.Bean.RecordBean;
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

public class RecordActivity extends BaseActivity {
    private ListView listview;
    private XRefreshView xRefreshView;
    private List<RecordBean> list = new ArrayList<>();
    private int page = 1;
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(R.color.mainColor);
        setContentView(R.layout.activity_record);
        intView();
        showProgressBar();
        Record();
    }

    private void Record() {
        Map map = new HashMap();
        map.put("jobNumber", MyApp.getInstance().getUserbean().getJobNumber());
        map.put("page", page + "");
        LogUtil.logWrite("map", map.toString());
        get(Config.Record, map, Contants.Record);
    }

    @Override
    public void analysisErr(String e) {
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
        cancleProgressBar();
        ToastUtils.disPlayShort(RecordActivity.this, e);
    }

    @Override
    public <T> void analysis(T result, int flag) {
        switch (flag) {
            case Contants.Record:
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore();
                cancleProgressBar();
                LogUtil.logWrite("Record", result.toString());
                try {
                    JSONArray array = new JSONArray(result.toString());
                    List<RecordBean> recordbean = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        RecordBean bean = new Gson().fromJson(array.getJSONObject(i).toString(), RecordBean.class);
                        if (bean.getAbbreviation() != null) {
                            recordbean.add(bean);
                        }
                    }
                    if (recordbean.size() > 0) {
                        list.addAll(recordbean);
                    } else {
                        if (page > 1) {
                            page--;
                        }
                        return;
                    }
                    if (adapter == null) {
                        adapter = new RecordAdapter(this, list);
                        listview.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    if (page != 1) {
                        listview.smoothScrollToPosition(list.size() - recordbean.size());
                    }
                    adapter.OnItemClick(onitemclick);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
        }
        xRefreshView.stopRefresh();

    }

    private void intView() {
        findViewsetlisten(R.id.tv_back);
        xRefreshView = (XRefreshView) findViewById(R.id.xrefreshview);
        listview = (ListView) findViewById(R.id.listview);
        xRefreshView.setXRefreshViewListener(listen);
        xRefreshView.setPullLoadEnable(true);
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
            Record();
        }

        @Override
        public void onLoadMore(boolean isSilence) {
            page++;
            Record();
        }
    };
    RecordAdapter.OnItemClick onitemclick = new RecordAdapter.OnItemClick() {
        @Override
        public void OnItemClick(int position, int post, String type) {
            switch (type) {
                case RecordAdapter.EDIT:
                    startActivityForResult(new Intent(RecordActivity.this, EditActivity.class)
                            .putExtra("Record", list.get(position)), 10);
                    break;
                case RecordAdapter.PICTURE:
                    final String[] Picture = list.get(position).getPictures().split(",");
                    startActivity(new Intent(RecordActivity.this,
                            ImageDailogActivity.class)
                            .putExtra("position", post)
                            .putExtra("Picture", Picture));
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == 10) {
                    page = 1;
                    list.clear();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    Record();
                }
                break;
        }
    }
}
