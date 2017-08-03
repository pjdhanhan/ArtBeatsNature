package com.liuzhen.mylibrary.Utils.Md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {

    public String getMD5(String info) {

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("utf-8"));
            byte[] encryption = md5.digest();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    buffer.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    buffer.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            return null;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
}
