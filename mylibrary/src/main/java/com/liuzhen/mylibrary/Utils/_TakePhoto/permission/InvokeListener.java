package com.liuzhen.mylibrary.Utils._TakePhoto.permission;


import com.liuzhen.mylibrary.Utils._TakePhoto.model.InvokeParam;

/**
 * 授权管理回调
 */
public interface InvokeListener {
    PermissionManager.TPermissionType invoke(InvokeParam invokeParam);
}
