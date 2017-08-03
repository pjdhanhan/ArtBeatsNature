package com.liuzhen.mylibrary.Utils.Regular;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hjm on 2016/2/23.
 */
public class Regular {

    //6到16位数字或字母   [\\da-zA-Z]{6,16}
    //6位数字           \d{6}
    //6到16位数字       \d{6,16}
    //6到16位字母       [a-zA-Z]{6,16}
    //+表示1个或多个（如"3"或"225"），*表示0个或多个（[0-9]*）（如""或"1"或"22"），?表示0个或1个([0-9]?)(如""或"7")     [0-9]+
    public static boolean check(String content, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


    /**
     * 手机号校验 注：1.支持最新170手机号码 2.支持+86校验
     *
     * @param phoneNum 手机号码
     * @return 验证通过返回true
     */
    public static boolean isMobile(String phoneNum) {
        if (phoneNum == null)
            return false;
        // 如果手机中有+86则会自动替换掉
        return validation("^[1][3,4,5,7,8][0-9]{9}$",
                phoneNum.replace("+86", ""));
    }


    /**
     * 用户名校验,默认用户名长度至少3个字符，最大长度为15<br>
     * 可修改正则表达式以实现不同需求
     *
     * @param username 用户名
     * @return
     */
    public static boolean isUserName(String username) {
        /***
         * 正则表达式为：^[a-z0-9_-]{3,15}$ 各部分作用如下： [a-z0-9_-] -----------
         * 匹配列表中的字符，a-z,0–9,下划线，连字符 {3,15}-----------------长度至少3个字符，最大长度为15
         * 如果有不同需求的可以参考以上修改正则表达式
         */
        return validation("^[a-z0-9_-]{3,15}$", username);
    }

    /**
     * 密码校验
     * 要求6-16位数字和英文字母组合
     *
     * @param pwd
     * @return
     */
    public static boolean isPassword(String pwd) {
        /**
         * ^ 匹配一行的开头位置(?![0-9]+$) 预测该位置后面不全是数字
         * (?![a-zA-Z]+$) 预测该位置后面不全是字母
         * [0-9A-Za-z] {6,16} 由6-16位数字或这字母组成
         */
        return validation("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", pwd);
    }

    /**
     * 邮箱校验
     *
     * @param mail 邮箱字符串
     * @return 如果是邮箱则返回true，如果不是则返回false
     */
    public static boolean isEmail(String mail) {
        return validation(
                "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
                mail);
    }


    /**
     * 正则校验
     *
     * @param pattern 正则表达式
     * @param str     需要校验的字符串
     * @return 验证通过返回true
     */
    public static boolean validation(String pattern, String str) {
        if (str == null)
            return false;
        return Pattern.compile(pattern).matcher(str).matches();
    }
}
