package com.jzd.artbeatsnature.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneNumberUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzd.artbeatsnature.Adapter.CustomerAdapter;
import com.jzd.artbeatsnature.Adapter.PhoneAdapter;
import com.jzd.artbeatsnature.Bean.ByBean;
import com.jzd.artbeatsnature.Bean.KhInforationt;
import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.Comment.Contants;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.Utils.Dialog.ActionSheetDialog;
import com.liuzhen.mylibrary.Utils.Dialog.BounceEnter.BounceTopEnter;
import com.liuzhen.mylibrary.Utils.Dialog.NormalDialog;
import com.liuzhen.mylibrary.Utils.Dialog.SlideExit.SlideBottomExit;
import com.liuzhen.mylibrary.Utils.Dialog.listener.OnBtnClickL;
import com.liuzhen.mylibrary.Utils.Dialog.listener.OnOperItemClickL;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.NoScrollView.NoScrollListView;
import com.liuzhen.mylibrary.Utils.Regular.Regular;
import com.liuzhen.mylibrary.Utils.SmartScrollView.SmartScrollView;
import com.liuzhen.mylibrary.Utils.ToastUtils.ToastUtils;
import com.liuzhen.mylibrary.Utils._BaseSwipeRefreshLayout.BaseSwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDetailsActivity extends BaseActivity implements BaseSwipeRefreshLayout.OnLoadListener, BaseSwipeRefreshLayout.OnRefreshListener {
    // ListView listview;
    NoScrollListView listview;
    ByBean CustomerDetail;
    private List<KhInforationt> list = new ArrayList<>();
    private String address = "";
    private int page = 1;
    private CustomerAdapter adapter;
    //   private BaseSwipeRefreshLayout refresh;
    private SmartScrollView smartscrollview;
    private TextView maintain_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(R.color.mainColor);
        setContentView(R.layout.activity_customer_details);
        if (getIntent().hasExtra("CustomerDetail")) {
            CustomerDetail = (ByBean) getIntent().getSerializableExtra("CustomerDetail");
            address = CustomerDetail.getShipAddress();
        }
        setView();
        KhInforationt();
    }

    private void KhInforationt() {
        Map map = new HashMap();
        map.put("page", page + "");
        map.put("Id", CustomerDetail.getId());
        LogUtil.logWrite("result", map.toString());
        get(Config.KhInforationt, map, Contants.KhInforationt);
    }


    @Override
    public void analysisErr(String e) {
        ToastUtils.disPlayShort(CustomerDetailsActivity.this, e);
        cancleProgressBar();
        //  refresh.setRefreshing(false);
    }

    @Override
    public <T> void analysis(T result, int flag) {
        switch (flag) {
            case Contants.KhInforationt:
                LogUtil.logWrite("result", result.toString());
                cancleProgressBar();
                //  refresh.setRefreshing(false);
                try {
                    JSONArray array = new JSONArray(result.toString());
                    List<KhInforationt> khinforationt = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        KhInforationt khinfor = new Gson().fromJson(array.getJSONObject(i).toString(), KhInforationt.class);
                        if (khinfor.getSalesman() != null) {
                            khinforationt.add(khinfor);
                        }
                    }
                    if (khinforationt.size() > 0) {
                        list.addAll(khinforationt);
                    } else {
                        if (page > 1) {
                            page--;
                        }
                        return;
                    }
                    listview.setAdapter(adapter);
                    if (adapter == null) {
                        adapter = new CustomerAdapter(this, list);
                        listview.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    listview.smoothScrollToPosition(list.size() - khinforationt.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
        }
    }

    private void setView() {
        findViewsetlisten(R.id.tv_back);
        findViewsetlisten(R.id.map);
        findViewsetlisten(R.id.phone);
        findViewsetlisten(R.id.message);
        findViewsetlisten(R.id.addcustomer);
        findViewsetlisten(R.id.maintain_details);
        // refresh = (BaseSwipeRefreshLayout) findViewById(R.id.refresh);
        smartscrollview = (SmartScrollView) findViewById(R.id.smartscrollview);
//        refresh.setOnLoadListener(this);
//        refresh.setOnRefreshListener(this);
        //   listview = (ListView) findViewById(R.id.listview);
        maintain_customer= (TextView) findViewById(R.id.maintain_customer);
        maintain_customer.setText(CustomerDetail.getAbbreviation());
        listview = (NoScrollListView) findViewById(R.id.listview);
        smartscrollview.ScrolledToBottom(new SmartScrollView.ScrolledToBottom() {
            @Override
            public void ScrolledToBottom(boolean bottom) {
                if (bottom) {
                    page++;
                    KhInforationt();
                }
            }
        });
    }


    @Override
    public void onRefresh() {
        page = 1;
        list.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        KhInforationt();
    }

    @Override
    public void onLoad() {
        page++;
        KhInforationt();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.phone:
                //用intent启动拨打电话
                if (Regular.isMobile(CustomerDetail.getContactNumber())) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + CustomerDetail.getContactNumber()));
                    if (ActivityCompat.checkSelfPermission(CustomerDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                } else {
                    String[] telPhone=CustomerDetail.getContactNumber().split("/");
                    PopPhone(telPhone);
                    if (telPhone.length==0){
                        ToastUtils.disPlayLongCenter(CustomerDetailsActivity.this, getString(R.string.phone_null));
                    }
                   // ToastUtils.disPlayLongCenter(CustomerDetailsActivity.this, getString(R.string.phone_null));
                }
                break;
            case R.id.message:
                if (Regular.isMobile(CustomerDetail.getContactNumber())) {
                    doSendSMSTo(CustomerDetail.getContactNumber());
                } else {
                    ToastUtils.disPlayLongCenter(CustomerDetailsActivity.this, getString(R.string.phone_null));
                }
                break;
            case R.id.map:
                ShowDiaogAnimChoose();
                break;
            case R.id.addcustomer:
                startActivityForResult(new Intent(CustomerDetailsActivity.this, AddMainTainActivity.class)
                        .putExtra("CustomerDetail", CustomerDetail), 10);
                break;
            case R.id.maintain_details:
                startActivity(new Intent(CustomerDetailsActivity.this, MainRtainDetailsActivity.class)
                        .putExtra("CustomerDetail", CustomerDetail));
                break;
        }
    }

    private void PopPhone(final String[] datas) {
        View popupView =this.getLayoutInflater().inflate(R.layout.popupwindow, null);
        final ListView lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);
        List<String> list=new ArrayList<>();
        for (int i=0;i<datas.length;i++){
            list.add(datas[i]);
        }
        lsvMore.setAdapter(new PhoneAdapter(CustomerDetailsActivity.this, list));
        PopupWindow window = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //  window.setAnimationStyle(R.style.popup_window_anim);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        window.showAtLocation(smartscrollview, Gravity.CENTER,0,0);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        lsvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + datas[position]));
                if (ActivityCompat.checkSelfPermission(CustomerDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
    }
    private void ShowDiaogAnimChoose() {
        ArrayList<String> itemList = new ArrayList<String>();
        itemList.add(getResources().getString(R.string.tencent));
        itemList.add(getResources().getString(R.string.gould));
        itemList.add(getResources().getString(R.string.baidu));
        final String[] contents = new String[itemList.size()];
        final ActionSheetDialog dialog = new ActionSheetDialog(this, //
                itemList.toArray(contents), null);
        dialog.titleTextSize_SP(14.5f)//
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    switch (position) {
                        case 0:
                            if (isAvilible(CustomerDetailsActivity.this, "com.tencent.map")) {
                                String baseUrl = "qqmap://map/";
                                String tencnetUri = baseUrl + "search?keyword=" + address + "&referer=myapp"
                                        + getResources().getString(R.string.app_name);
                                Intent intent;
                                intent = Intent.parseUri(tencnetUri, 0);
                                startActivity(intent);
                            } else {
                                NormalDialogStyleTwo(0, "com.tencent.map");
                            }
                            break;
                        case 1:
                            if (isAvilible(CustomerDetailsActivity.this, "com.autonavi.minimap")) {
                                Intent intent = new Intent("android.intent.action.VIEW",
                                        android.net.Uri.parse("androidamap://poi?sourceApplication=" +
                                                getResources().getString(R.string.app_name) +
                                                "&keywords=" + address + "&dev=0"));
                                intent.setPackage("com.autonavi.minimap");
                                startActivity(intent);
//
                            } else {
                                NormalDialogStyleTwo(1, "com.autonavi.minimap");
                            }
                            break;
                        case 2:
                            if (isAvilible(CustomerDetailsActivity.this, "com.baidu.BaiduMap")) {
                                Intent intent = new Intent();
                                intent.setData(Uri.parse("baidumap://map/geocoder?src=openApiDemo&address=" + address));
                                startActivity(intent); //启动调用
                            } else {
                                NormalDialogStyleTwo(2, "com.baidu.BaiduMap");
                            }
                            break;
                    }
                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    private boolean isAvilible(Context context, String packageName) {
        boolean hasapp = false;
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                if (packName.equals(packageName)) {
                    hasapp = true;
                    break;
                }
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return hasapp;
    }

    private void NormalDialogStyleTwo(int position, final String packname) {
        String title = "";
        if (position == 0) {
            title = getResources().getString(R.string.loadtencent);
        } else if (position == 1) {
            title = getResources().getString(R.string.loadgould);
        } else {
            title = getResources().getString(R.string.loadbaidu);
        }
        BounceTopEnter bas_in = new BounceTopEnter();
        SlideBottomExit bas_out = new SlideBottomExit();
        final NormalDialog dialog = new NormalDialog(this);
        dialog.content(title)//
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(16)//
                .titleTextColor(R.color.textColor)
                .titleLineColor(R.color.textColor)
                .contentTextSize(14)
                .title(getResources().getString(R.string.prompt))
                .showAnim(bas_in)//
                .dismissAnim(bas_out)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick(String content) {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick(String content) {
                        Uri uri = Uri.parse("market://details?id=" + packname);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

    }


    /* 调起系统发短信功能
* @param phoneNumber
* @param message
*/
    public void doSendSMSTo(String phoneNumber) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", "");
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == 10) {
                    setResult(10);
                    page = 1;
                    list.clear();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    KhInforationt();
                }
                break;
        }
    }


}
