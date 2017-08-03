package com.liuzhen.mylibrary.Utils._TakePhoto.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.liuzhen.mylibrary.R;
import com.liuzhen.mylibrary.Utils._TakePhoto.model.InvokeParam;
import com.liuzhen.mylibrary.Utils._TakePhoto.model.TContextWrap;
import com.liuzhen.mylibrary.Utils._TakePhoto.model.TException;
import com.liuzhen.mylibrary.Utils._TakePhoto.model.TResult;
import com.liuzhen.mylibrary.Utils._TakePhoto.permission.InvokeListener;
import com.liuzhen.mylibrary.Utils._TakePhoto.permission.PermissionManager;
import com.liuzhen.mylibrary.Utils._TakePhoto.permission.TakePhotoInvocationHandler;

/**
 * 继承这个类来让Activity获取拍照的能力<br>
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:3.0.0
 * 技术博文：http://www.cboy.me
 * GitHub:https://github.com/crazycodeboy
 * Eamil:crazycodeboy@gmail.com
 */
public class TakePhotoActivity extends AppCompatActivity implements TakePhoto.TakeResultListener,InvokeListener {
    private static final String TAG = TakePhotoActivity.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            getTakePhoto().onActivityResult(requestCode, resultCode, data);
        } catch (TException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public  TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= ( TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }
    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());
    }
    @Override
    public void takeFail(TResult result,String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }



    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {

        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }
}
