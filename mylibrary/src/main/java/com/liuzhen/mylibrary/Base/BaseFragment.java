package com.liuzhen.mylibrary.Base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;

import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.Utils._TakePhoto.model.CustomProgress;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/19.
 */
public class BaseFragment extends Fragment {
    private ProgressDialog progressDialog;
    private Dialog progress;
    private RequestQueue requestQueue;
    //创建okHttpClient对象
    OkHttpClient mOkHttpClient = new OkHttpClient();


    public void setMaginTop(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, BaseActivity.getStatusBarHeight(getActivity()), 0, 0);
            view.setLayoutParams(params);
        }
    }


    protected void showProgressBar() {
        if (!getActivity().isFinishing()) {
            progress = CustomProgress.show(getActivity(), "", true, null);
        }
    }

    /**
     * 取消加载progressBar
     */
    protected void cancleProgressBar() {
        if (progress == null) {
            return;
        }
        progress.dismiss();
    }

    public void closeInput() {
        /**隐藏软键盘**/
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static final int SUCCESS = 1;
    public static final int FAIL = -1;


    //get请求
    protected void get(String url, Map params, final int flag) {
        url = url + "?";
        Iterator<Map.Entry<Integer, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            url = url + "&" + entry.getKey() + "=" + entry.getValue();
        }
        if (url.contains("?&")) {
            url = url.replace("?&", "?");
        }
        //创建一个Request
        final com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                final String htmlStr = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        analysis(htmlStr, flag);
                    }
                });
            }
        });
    }

    public <T> void analysis(T result, int flag) {

    }
}
