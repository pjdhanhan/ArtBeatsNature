package com.jzd.artbeatsnature.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jzd.artbeatsnature.Adapter.AddMainAdpter;
import com.jzd.artbeatsnature.Adapter.EditAdpter;
import com.jzd.artbeatsnature.Adapter.EditImageAdpter;
import com.jzd.artbeatsnature.Bean.ByBean;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMainTainActivity extends BaseActivity {
    List<String> people_list = new ArrayList<>();
    List<String> people_list2 = new ArrayList<>();
    List<String> picture_list = new ArrayList<>();
    private NoScrollGridView peoples, pictures;
    AddMainAdpter adpter;
    private EditImageAdpter editimage;
    private ImageView handsign;
    private ByBean CustomerDetail;
    private EditText summary;
    private String from, SignFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(R.color.mainColor);
        setContentView(R.layout.activity_add_main_tain);
        intView();
        if (getIntent().hasExtra("CustomerDetail")) {
            CustomerDetail = (ByBean) getIntent().getSerializableExtra("CustomerDetail");
        }
        JobNumberList();
    }

    @Override
    public void analysisErr(String e) {
        ToastUtils.disPlayShort(AddMainTainActivity.this, e);
    }

    @Override
    public <T> void analysis(T result, int flag) {
        switch (flag) {
            case Contants.Keep_Add:
                LogUtil.logWrite("Update", result.toString());
                if (result.toString().equals("0")) {
                    ToastUtils.disPlayShort(AddMainTainActivity.this, "添加失败");
                } else {
                    setResult(10);
                    finish();
                }
                break;
            case Contants.UploadFile:
                LogUtil.logWrite("UploadFile", "analysis:" + result.toString());
                switch (from) {
                    case "EditAdpter":
                        picture_list.remove("");
                        picture_list.add(result.toString());
                        picture_list.add("");
                        LogUtil.logWrite("UploadFile", "picture_list:" + picture_list.toString());
                        editimage.notifyDataSetChanged();
                        break;
                    case "handsign":
                        SignFile = result.toString();
                        handsign.setImageBitmap(HandSignActivity.bmp);
                        break;
                }
                break;
            case Contants.TakePhone:
                LogUtil.logWrite("TakePhone", result.toString());
                File file = (File) result;
                UploadFile(file);
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

    //获取工号列表
    private void JobNumberList() {
        Map map = new HashMap();
        get(Config.JobNumberList, map, Contants.JobNumberList);
    }

//    Id 保养记录ID
//    MaintenTime 保养时间
//    MaintenPerson 保养人（工号）
//    Summary 客户情况总结
//    Pictures 图片
//    SignFile 手签图


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

        if (MaintenPerson.equals("")) {
            ToastUtils.disPlayShort(AddMainTainActivity.this, "请选择保养人员");
            return;
        }
        if ("".equals(SignFile)||SignFile==null) {

            ToastUtils.disPlayShort(AddMainTainActivity.this, "请上传手签图");
            return;
        }
        JSONObject object = new JSONObject();
        try {
            object.put("MaintenPerson", MaintenPerson);
            object.put("Summary", summary.getText().toString());
            object.put("Pictures", Pictures);
            object.put("SignFile", SignFile);
            object.put("Cid", CustomerDetail.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.logWrite("Update", object.toString());
        postJson(Config.Keep_Add, object.toString(), Contants.Keep_Add);
    }

    //上传图片
    private void UploadFile(File file) {
        UploadFile(Config.UploadFile, file, Contants.UploadFile);
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

    private void intView() {
        findViewsetlisten(R.id.confirm);
        findViewsetlisten(R.id.tv_back);
        handsign = (ImageView) findViewsetlisten(R.id.handsign);
        people_list.add("");
        summary = (EditText) findViewById(R.id.summary);
        peoples = (NoScrollGridView) findViewById(R.id.peoples);
        adpter = new AddMainAdpter(this, people_list);
        peoples.setAdapter(adpter);
        adpter.OnItemClick(new AddMainAdpter.OnItemClick() {
            @Override
            public void OnItemClick(int position, String type) {
                switch (type) {
                    case EditAdpter.DELETE:
                        people_list.remove(position);
                        adpter.notifyDataSetChanged();
                        break;
                    case EditAdpter.ADD:
                        Dialoglist dialoglist = new Dialoglist(people_list2, AddMainTainActivity.this, peoples.getChildAt(position));
                        dialoglist.showWindow(handler);
                        break;
                }
            }
        });

        picture_list.add("");
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
                        from = "EditAdpter";
                        TakePhotoSettings(findViewById(R.id.view), Contants.TakePhone);
                        break;
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.confirm:
                Update();
                break;
            case R.id.handsign:
                from = "handsign";
                startActivityForResult(new Intent(AddMainTainActivity.this, HandSignActivity.class), 10);
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == 10) {
                    UploadFile(saveBitmapToSDCard());
                }
                break;
        }
    }

    // 将用户手绘的DrawableView转化为图片保存到本地系统默认的图片库中。
    private File saveBitmapToSDCard() {
        File parent_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File f = new File(parent_path.getAbsoluteFile(), "myDrawableView.png");
        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            HandSignActivity.bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

}
