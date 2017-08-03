package com.liuzhen.mylibrary;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.liuzhen.mylibrary.Base.C;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.NetWorkUtils.NetWorkUtils;
import com.liuzhen.mylibrary.Utils.ToastUtils.ToastUtils;
import com.liuzhen.mylibrary.Utils._NotificationBarColorSettings.SystemBarTintManager;
import com.liuzhen.mylibrary.Utils._TakePhoto.NewImgCropPicker;
import com.liuzhen.mylibrary.Utils._TakePhoto.app.TakePhoto;
import com.liuzhen.mylibrary.Utils._TakePhoto.app.TakePhotoActivity;
import com.liuzhen.mylibrary.Utils._TakePhoto.compress.CompressConfig;
import com.liuzhen.mylibrary.Utils._TakePhoto.model.CropOptions;
import com.liuzhen.mylibrary.Utils._TakePhoto.model.CustomProgress;
import com.liuzhen.mylibrary.Utils._TakePhoto.model.TResult;
import com.liuzhen.mylibrary.Utils._TakePhoto.model.TakePhotoOptions;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/24.
 */

public class BaseActivity extends TakePhotoActivity implements View.OnClickListener {

    public final static int SUCCESS = 1;

    public static final int FAIL = -1;
    public final static int SUCCESS_TakePhoto = -2;
    public final static int Fail_TakePhoto = -3;
    private CustomProgress progress;
    private RequestQueue requestQueue;
    protected Context mContext = this;
    private CompressConfig compressConfig;
    private NewImgCropPicker newimgCropPicker;
    private Bitmap bitmap;
    //创建okHttpClient对象
    OkHttpClient mOkHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static int flag;
  //  private Handler mhandler;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState, persistentState);
    }


    protected void setThemeColor(@DrawableRes int resid) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(resid);//通知栏所需颜色
        }
    }


    public View findViewsetlisten(@IdRes int id) {
        findViewById(id).setOnClickListener(this);
        return findViewById(id);
    }

    protected boolean isNotEmpty(View view) {
        if (view instanceof TextView) {
            if (((TextView) view).getText().toString().equals("")) {
                ToastUtils.disPlayLongCenter(this, ((TextView) view).getHint().toString());
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * 隐藏软键盘
     */
    public void closeInput() {
        View view = this.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//land
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//port
        }
    }

    protected void showProgressBar() {
        if (!((BaseActivity) mContext).isFinishing()) {
            progress = CustomProgress.show(mContext, "", true, null);
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


    /**
     * 状态栏设置
     *
     * @param on
     */
    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //上传文件
    public void UploadFile(String url, File file, final int flag1) {
        flag = flag1;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(url)//地址
                .post(requestBody)//添加请求体
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    // OkHttp json请求
    public void postJson(String url, String json, final int flag1) {
        flag = flag1;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    // OkHttp post请求
    public void post(String url, RequestBody formBody, final int flag1) {
        flag = flag1;
//
//        RequestBody formBody = new FormBody.Builder()
//                .add("Phone", "17806270376")
//                .add("Pwd", "123456")
//                .build();
//
        Request requestPost = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        mOkHttpClient.newCall(requestPost).enqueue(callback);
    }


    //get请求
    protected void get(String url, Map params, final int flag1) {
        flag = flag1;
        url = url + "?";
        Iterator<Map.Entry<Integer, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            url = url + "&" + entry.getKey() + "=" + entry.getValue();
        }
        if (url.contains("?&")) {
            url = url.replace("?&", "?");
        }


        LogUtil.logWrite("url", url);
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(callback);
    }

    Callback callback = new Callback() {

        @Override
        public void onFailure(Call call, IOException e) {
            cancleProgressBar();
            try {
                if (NetWorkUtils.isNetworkConnect(BaseActivity.this)) {
                    if (e.getCause().equals(SocketTimeoutException.class)) {
                        UIToast("连接超时");
                    } else {
                        UIToast("服务器异常");
                    }
                } else {
                    UIToast("请检查网络连接");
                }

            } catch (Exception e1) {
                analysisErr(e1.toString());
            }

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                final String htmlStr = response.body().string();
                runOnUiThread(new Runnable() {
                    public void run() {
                        analysis(htmlStr, flag);
                    }
                });
            } else {
                UIToast(response.body().string());
            }
        }
    };

    private void UIToast(final String ss) {
        runOnUiThread(new Runnable() {
            public void run() {
                analysisErr(ss);
                ToastUtils.disPlayShort(BaseActivity.this, ss);
            }
        });
    }




    /*
       7.0权限拍照
     */
    //裁剪
    public void TakePhotoSettings(final int width, final int height, View view, Handler handler) {
       // this.mhandler = handler;
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);
        newimgCropPicker = new NewImgCropPicker(this, view, null);
        newimgCropPicker.show();

        //从拍照裁剪
        configCompress(getTakePhoto());
        configTakePhotoOption(getTakePhoto());

        newimgCropPicker.SetOnChoose(new NewImgCropPicker.OnChooseListen() {
            @Override
            public void Choose(String type) {
                switch (type) {
                    case NewImgCropPicker.TAKE_PHOTO:
                        //拍照
                        getTakePhoto().onPickFromCaptureWithCrop(imageUri, getCropOptions(width, height));
                        break;
                    case NewImgCropPicker.FROM_ALBUM:
                        //相册 裁剪
                        getTakePhoto().onPickFromDocumentsWithCrop(imageUri, getCropOptions(width, height));
                        break;
                }
            }
        });

    }

    //不裁剪
    public void TakePhotoSettings(View view,final int flag1) {
        flag = flag1;
       // this.mhandler = handler;

        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);
        //从拍照裁剪
        configCompress(getTakePhoto());
        configTakePhotoOption(getTakePhoto());
        newimgCropPicker = new NewImgCropPicker(this, view, null);
        newimgCropPicker.show();

        newimgCropPicker.SetOnChoose(new NewImgCropPicker.OnChooseListen() {
            @Override
            public void Choose(String type) {
                switch (type) {
                    case NewImgCropPicker.TAKE_PHOTO:
                        // 从拍照不裁剪
                        getTakePhoto().onPickFromCapture(imageUri);
                        break;
                    case NewImgCropPicker.FROM_ALBUM:
                        //相册不裁剪
                        getTakePhoto().onPickFromDocuments();
                        break;
                }
            }
        });

    }


    private void configCompress(TakePhoto takePhoto) {
        takePhoto.onEnableCompress(null, true);

        int maxSize = 102400;
        //压缩宽高
        int width = 800;
        int height = 800;
        boolean showProgressBar = true;
        //拍照压缩后是否保存原图：
        boolean enableRawFile = false;
        CompressConfig config;
//压缩工具
        config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)

                .create();
        //压缩工具
//        LubanOptions option=new LubanOptions.Builder()
//                .setMaxHeight(height)
//                .setMaxWidth(width)
//                .setMaxSize(maxSize)
//                .create();
//        config= CompressConfig.ofLuban(option);
//        config.enableReserveRaw(enableRawFile);
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        // 使用TakePhoto自带相册：
        builder.setWithOwnGallery(true);
        //    纠正拍照的照片旋转角度：
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    private CropOptions getCropOptions(int width, int height) {
        ///裁剪 宽高
        //裁剪工具是否使用TAKEPHOTO
        boolean withWonCrop = false;
        CropOptions.Builder builder = new CropOptions.Builder();
        //宽x高  宽/高
        builder.setAspectX(width).setAspectY(height);
        // builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                closeInput();
                finish();

        }
    }*/

    @Override
    public void takeCancel() {
        super.takeCancel();
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
       /* Message message = new Message();
        message.what = SUCCESS;
        message.arg1 = Fail_TakePhoto;
        message.obj = msg;
        mhandler.sendMessage(message);*/
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
       /* Message message = new Message();
        message.what = SUCCESS;
        message.arg1 = SUCCESS_TakePhoto;
        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
        message.obj = bitmap;
        mhandler.sendMessage(message);*/
        showImg(result.getImage().getCompressPath());
    }
    private void showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 0;
        bitmap = BitmapFactory.decodeFile(imagePath, option);
//        //将处理过的图片保存到本地
//        ImageTools.savePhotoToSDCard(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
//        //将保存到本地的图片取出
//        ImageTools.getFilePath();
        LogUtil.logWrite("FilePath", "imagePath:" + imagePath);
        File file = new File(imagePath);
        analysis(file, flag);
    }
/*
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int result = msg.what;
            switch (result) {
                case C.PICK_FROM_CAPTURE:
                    getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCaptureWithCrop(imageUri, cropOptions);//从相机拍取照片裁剪
                    break;
                case C.PICK_FROM_ALBUM:
                    getTakePhoto().onEnableCompress(compressConfig, true).onPickFromGalleryWithCrop(imageUri, cropOptions);//从相册选择照片裁剪
                    break;
                case C.REQUEST_CHOOSE_ALBUM:
                    getTakePhoto().onEnableCompress(compressConfig, true).onPickFromGallery();//从相册选择照片不裁剪
                    break;
                case C.REQUEST_CHOOSE_CAPTURE:
                    getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCapture(imageUri);//从相机拍取照片不裁剪

                    break;
            }
        }
    };
*/

    /*------------------------------------------拍照--------------------------------------------------- */
/*
    public void TakePhotoSettings(View view, final int flag1) {
        flag = flag1;
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 222);
                return;
            } else {
                setTakePhotoSettings(view);
            }
        } else {
            setTakePhotoSettings(view);
        }
    }


    public void TakePhotoSettings(int cropWidth, int cropHeight, View view) {

        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 222);
                return;
            } else {
                setTakePhotoSettings(cropWidth, cropHeight, view);
            }
        } else {
            setTakePhotoSettings(cropWidth, cropHeight, view);
        }
    }


    private CropOptions cropOptions;
    private Uri imageUri;

    public void setTakePhotoSettings(int cropWidth, int cropHeight, View view) {

        if (ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);
        compressConfig = new CompressConfig.Builder().setMaxSize(1024 * 1024).setMaxPixel(800).create();
        //暂时不用takephoto自带的裁剪工具（找不到crop），受用第三方的
        cropOptions = new CropOptions.Builder().setAspectX(cropWidth).setAspectY(cropHeight).setWithOwnCrop(false).create();
        newimgCropPicker = new NewImgCropPicker(this, view, mHandler, null, null, true);
        newimgCropPicker.show();
    }


    public void setTakePhotoSettings(View view) {
        if (ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);
        compressConfig = new CompressConfig.Builder().setMaxSize(512 * 512).setMaxPixel(800).create();
        //暂时不用takephoto自带的裁剪工具（找不到crop），受用第三方的
        cropOptions = new CropOptions.Builder().setAspectX(0).setAspectY(0).setWithOwnCrop(false).create();
        newimgCropPicker = new NewImgCropPicker(this, view, mHandler, null, null, false);
        newimgCropPicker.show();
    }

    @Override
    public void takeCancel() {
        Toast.makeText(this, "您已取消图片获取", Toast.LENGTH_SHORT).show();
    }

  //  @Override
    public void takeFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

   // @Override
    public void takeSuccess(String imagePath) {
        LogUtil.logWrite("BaseActivity", "takeSuccess");
        showImg(imagePath);
    }

    private void showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 0;
        bitmap = BitmapFactory.decodeFile(imagePath, option);
//        //将处理过的图片保存到本地
//        ImageTools.savePhotoToSDCard(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
//        //将保存到本地的图片取出
//        ImageTools.getFilePath();
        LogUtil.logWrite("FilePath", "imagePath:" + imagePath);
        File file = new File(imagePath);
        analysis(file, flag);
    }
*/
    public <T> void analysis(T result, int flag) {

    }

    public void analysisErr(String e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(0, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {

    }
}
