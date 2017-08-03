package com.jzd.artbeatsnature.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.jzd.artbeatsnature.Adapter.EditAdpter;
import com.jzd.artbeatsnature.Adapter.EditImageAdpter;
import com.jzd.artbeatsnature.Bean.RecordBean;
import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.Comment.Contants;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.Utils.DialogList.Dialoglist;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.NoScrollView.NoScrollGridView;
import com.liuzhen.mylibrary.Utils.ToastUtils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditActivity extends BaseActivity {

    private NoScrollGridView peoples, pictures;
    EditAdpter adpter;
    List<String> people_list2 = new ArrayList<>();
    List<String> people_list = new ArrayList<>();
    List<String> picture_list = new ArrayList<>();
    private EditImageAdpter editimage;
    private RecordBean Record;
    private EditText summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(R.color.mainColor);
        setContentView(R.layout.activity_edit);
        if (getIntent().hasExtra("Record")) {
            Record = (RecordBean) getIntent().getSerializableExtra("Record");
        }
        intView();
        JobNumberList();
    }

    //获取工号列表
    private void JobNumberList() {
        Map map = new HashMap();
        get(Config.JobNumberList, map, Contants.JobNumberList);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == Dialoglist.DATA) {
                if (((int) msg.obj) == 0) {
                    return;
                }
                if (people_list.contains(people_list2.get((int) msg.obj))) {
                    return;
                }
                people_list.remove(people_list.get(people_list.size() - 1));
                people_list.add(people_list2.get((int) msg.obj));
                people_list.add("");
                adpter.notifyDataSetChanged();
            }

        }
    };

    @Override
    public void analysisErr(String e) {
        ToastUtils.disPlayShort(EditActivity.this,e);
    }

    @Override
    public <T> void analysis(T result, int flag) {
        switch (flag) {
            case Contants.Update:
                if (result.toString().equals("1")) {
                    setResult(10);
                    finish();
                }
                LogUtil.logWrite("Update", result.toString());
                break;
            case Contants.UploadFile:
                picture_list.remove("");
                picture_list.add(result.toString());
                picture_list.add("");
                editimage.notifyDataSetChanged();
                break;
            case Contants.TakePhone:
                LogUtil.logWrite("TakePhone", result.toString());
                UploadFile((File) result);
                break;
            case Contants.JobNumberList:
                try {
                    JSONArray array = new JSONArray(result.toString());
                    for (int i = 0; i < array.length(); i++) {
                        if (array.getString(i) != null) {
                            people_list2.add(array.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void intView() {
        findViewsetlisten(R.id.confirm);
        findViewsetlisten(R.id.tv_back);

        summary = (EditText) findViewById(R.id.summary);
        summary.setText(Record.getSummary());

        String[] old_peoples = Record.getMaintenPerson().split(",");
        for (int i = 0; i < old_peoples.length; i++) {
            people_list.add(old_peoples[i]);
        }
        String[] old_pic = Record.getPictures().split(",");
        for (int i = 0; i < old_pic.length; i++) {
            picture_list.add(old_pic[i]);
        }
        picture_list.add("");
        people_list.add("");
        peoples = (NoScrollGridView) findViewById(R.id.peoples);

        adpter = new EditAdpter(this, people_list);
        peoples.setAdapter(adpter);
        adpter.OnItemClick(new EditAdpter.OnItemClick() {
            @Override
            public void OnItemClick(int position, String type) {
                switch (type) {
                    case EditAdpter.DELETE:
                        people_list.remove(position);
                        adpter.notifyDataSetChanged();
                        break;
                    case EditAdpter.ADD:
                        Dialoglist dialoglist = new Dialoglist(people_list2, EditActivity.this, peoples.getChildAt(position));
                        dialoglist.showWindow(handler);
                        break;
                }
            }
        });

        pictures = (NoScrollGridView) findViewById(R.id.pictures);
        editimage = new EditImageAdpter(this, picture_list);
        pictures.setAdapter(editimage);
        editimage.OnItemClick(new EditImageAdpter.OnItemClick() {
            @Override
            public void OnItemClick(int position, String type) {
                switch (type) {
                    case EditAdpter.DELETE:
                        picture_list.remove(position);
                        editimage.notifyDataSetChanged();
                        break;
                    case EditAdpter.ADD:
                        TakePhotoSettings(findViewById(R.id.view), Contants.TakePhone);
                        break;
                }
            }
        });
    }

    private void Update() {
        String MaintenPerson = "";
        String Pictures = "";
        if (people_list.contains("")) {
            people_list.remove("");
        }
        if (picture_list.contains("")) {
            picture_list.remove("");
        }
        for (int i = 0; i < picture_list.size(); i++) {
            if (i != picture_list.size() - 1) {
                Pictures = Pictures + picture_list.get(i) + ",";
            } else {
                Pictures = Pictures + picture_list.get(i);
            }
        }

        for (int i = 0; i < people_list.size(); i++) {
            if (i != people_list.size() - 1) {
                MaintenPerson = MaintenPerson + people_list.get(i) + ",";
            } else {
                MaintenPerson = MaintenPerson + people_list.get(i);
            }
        }
        JSONObject object = new JSONObject();
        try {
            object.put("MaintenPerson", MaintenPerson);
            object.put("Summary", summary.getText().toString());
            object.put("Pictures", Pictures);
            object.put("SignFile", Record.getSignFile());
            object.put("Id", Record.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.logWrite("Update", object.toString());
        postJson(Config.Update, object.toString(), Contants.Update);
    }

    //上传图片
    private void UploadFile(File file) {
        UploadFile(Config.UploadFile, file, Contants.UploadFile);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.confirm:
                Update();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
