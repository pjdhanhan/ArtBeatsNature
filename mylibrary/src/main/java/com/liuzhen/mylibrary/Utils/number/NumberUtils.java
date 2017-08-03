package com.liuzhen.mylibrary.Utils.number;


import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by hjm on 2016/3/18.
 */
public class NumberUtils {

    /**
     * 四舍五入保留scale位小数
     *
     * @param d
     * @param scale
     * @return
     */
    public static String getScale(double d, int scale) {
        return getSysScale(d, scale);
    }

    public static String getSysScale(double a, int scale) {
        if (scale > 0) {
            String pattern = "0.";
            for (int i = 0; i < scale; i++) {
                pattern += "0";
            }
            DecimalFormat df = new DecimalFormat(pattern);
            return df.format(a).toString();
        } else {
            return Math.round(a) + "";
        }
    }

    /**
     * 四舍五入保留scale位小数
     *
     * @param num
     * @return num
     */
    public static double numberFormat(double num, int scale) {
        BigDecimal b = new BigDecimal(num);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将数字转换成千分位
     *
     * @param number
     * @return
     */
    public static String thousands(double number) {
        DecimalFormat d = new DecimalFormat("#,##0.00");
        String num = d.format(number);
        return num;
    }

    /**
     * 获取number的scale次幂
     *
     * @param number
     * @param scale
     * @return
     */
    public static double power(int number, int scale) {
        double result = 1;
        if (scale > 0) {
            for (int i = 0; i < scale; i++) {
                result *= number;
            }
            return result;
        } else if (scale == 0) {
            return 1;
        } else {
            for (int i = 0; i < -scale; i++) {
                result *= number;
            }
            return 1 / result;
        }
    }

    /**
     * 获取小数位数
     *
     * @param pattern
     * @return
     */
    public static int getScaleNumber(String pattern) {
        int index = pattern.indexOf(".");
        if (index == -1) {
            return 0;
        } else {
            String patterns = pattern.substring(index + 1);
            return patterns.length();
        }
    }

    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double addAccurate(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double subAccurate(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mulAccurate(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double divAccurate(double value1, double value2, int scale) throws IllegalAccessException {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).doubleValue();
    }


    /**
     * 提供加法计算的add方法
     */
    public static String add(double a, double b) {
        return utils(a, b, 1);
    }

    /**
     * 提供减法运算的sub方法
     *
     * @return 两个参数的差
     */
    public static String sub(double a, double b) {
        return utils(a, b, 2);
    }

    private static String utils(double a, double b, int type) {
        String patternA = a + "";
        String patternB = b + "";
        int index = 0;
        int indexA = getScaleNumber(patternA);
        int indexB = getScaleNumber(patternB);
        if (indexA > indexB) {
            index = indexA;
        } else {
            index = indexB;
        }
        double num = 0.00;
        switch (type) {
            case 1:
                num = a + b;
                break;
            case 2:
                num = a - b;
                break;
        }
        if (getScaleNumber(num + "") > index) {
            return getSysScale(num, index);
        }
        return num + "";
    }
}
