/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:58
 */
public class StringUtil {
    /**
     * 将以逗号分隔的字符串转换成字符串数组
     * @param valStr
     * @return String[]
     */

    private static int TOW_PAD = 2;
    private static int LONG_PAD = 4;
    private static String PAD_CHAR = "0";
    private static Pattern pattern = Pattern.compile("[0-9]*");

    public static String[] StrList(String valStr){
        int i = 0;
        String TempStr = valStr;
        String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
        valStr = valStr + ",";
        while (valStr.indexOf(',') > 0)
        {
            returnStr[i] = valStr.substring(0, valStr.indexOf(','));
            valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());

            i++;
        }
        return returnStr;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.equals("")|| value.trim().equals("");
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static String completeBefore(int src) {
        return  String.format("%06d", src);
    }

    public static String lpad(String str){
        for(int i = str.length();i < LONG_PAD;i++){
            str = PAD_CHAR + str;
        }
        return str;
    }

    /**
     * 不够两位，前面补0
     * @param str 字符
     * @return 01-09
     */
    public static String lpadTwo(String str){
        for(int i = str.length();i < TOW_PAD;i++){
            str = PAD_CHAR + str;
        }
        return str;
    }

    /**
     * 去除字符串中的所有空格(包含前后、中间)
     * @param str 需要去除空格的字符串
     */
    public static  String trimSpace(String str) {
        if(isEmpty(str)) {
            return null;
        }
        return str.replaceAll(Const.EMPTY_SPACE,Const.EMPTY_CH);
    }
    /**
     * 去除字符串中的前后空格
     * @param str 需要去除空格的字符串
     */
    public static  String trimLRSpace(String str) {
        if(isBlank(str)) {
            return null;
        }
        return str.trim();
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if(cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNoBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isStr(String str) {
        if (isEmpty(str)) {
            return false;
        }
        char[] chars = str.toCharArray();
        boolean isStr = true;
        for (char c : chars) {
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                continue;
            } else {
                isStr = false;
                break;
            }
        }
        return isStr;
    }

    public static boolean isNumeric(String str){
        return pattern.matcher(str).matches();
    }

    public static Integer parseInt(String str) {
        if(StringUtil.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            try {
                return Integer.parseInt(str.substring(0, str.indexOf(Const.SPLIT_POINT)));
            } catch (NumberFormatException e1) {
                return 0;
            }
        }
    }

    /**
     * 将传入的多个字符串组成联合建
     * @param keys 字符串数组
     * @return str
     */
    public static String genKey(String...keys) {
        return genKeyUseChar(Const.SPLIT_LINE,keys);
    }


    /**
     * 构建字符串.
     * <p>
     * Examples:
     *
     * <pre>
     * splitChar="_",key="A","B","C" returns "A_B_C"
     * </pre>
     *
     *
     *
     * @param splitChar
     *            分隔符
     * @param keys
     *            字符串
     * @return
     */
    public static String genKeyUseChar(String splitChar, String... keys) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = keys.length; i < len; i++) {
            sb.append(keys[i]);
            if (i != len - 1) {
                sb.append(splitChar);
            }
        }
        return sb.toString();
    }

    public static String genCombIdKey(String...keys) {
        return genKeyCombId(Const.SPLIT_LINE,keys);
    }

    public static String genCombIdKeyUserUnderLine(String...keys) {
        return genKeyCombId(Const.UNDER_LINE,keys);
    }


    private static String genKeyCombId(String splitChar, String... keys) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = keys.length; i < len; i++) {
            sb.append(replaceEmptyByQ(keys[i]));
            if (i != len - 1) {
                sb.append(splitChar);
            }
        }
        return sb.toString();
    }

    /**
     * 根据传入的字符串与数字组成新的ID,(区别导入的收货)
     * @param timeId 字符串ID
     * @param index 数字
     * @return 新的ID字符串
     */
    public static String genId(String timeId,int index) {
        StringBuffer sb = new StringBuffer(32);
        sb.append(timeId).append(Const.TRUE).append(index);
        return sb.toString();
    }

    /**
     * 花费的时间字符串如：spent 4h,30m,33s
     *
     * @param startTm 开始时间，以纳秒为单位
     * @return 花费的时间字符串如：spent 4h,30m,33s
     */
    public static String getSpentTm(long startTm) {
        long spentTm = System.nanoTime() - startTm;
        return changeNanoTm2String(spentTm);
    }

    /**
     * 把纳秒时间转换为英文字符串
     *
     * @param nanoTm 纳秒
     * @return 字符串
     */
    public static String changeNanoTm2String(long nanoTm) {
        if (nanoTm == 0) {
            return " spent 0 millSec.";
        }
        long temp = nanoTm;
        long nanoSec = temp % 1000;
        temp = temp / 1000;
        long microSec = temp % 1000;
        temp = temp / 1000;
        long millSec = temp % 1000;
        temp = temp / 1000;
        long sec = temp % 60;
        temp = temp / 60;
        long min = temp % 60;
        temp = temp / 60;
        long hrs = temp % 12;
        temp = temp / 12;
        long days = temp % 365;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" spent ");
        if (days != 0) {
            stringBuilder.append(days + " days ");
        }
        if (hrs != 0) {
            stringBuilder.append(hrs + " hrs ");
        }
        if (min != 0) {
            stringBuilder.append(min + " min ");
        }
        if (sec != 0) {
            stringBuilder.append(sec + " sec ");
        }
        if (millSec != 0) {
            stringBuilder.append(millSec + " millSec ");
        }
        if (microSec != 0) {
            stringBuilder.append(microSec + " microSec ");
        }
        if (nanoSec != 0) {
            stringBuilder.append(nanoSec + " nanoSec");
        }
        stringBuilder.append(".");
        return stringBuilder.toString();
    }

    public static String genParamStr(int seq) {
        return (new StringBuffer(Const.RP_VAR).append(seq)).toString();
    }

    /**
     * 报表组合ID专用,如果字符串为null或'Q'则返回'',反则返回本身
     * @param str 入参
     * @return str or ''
     */
    public static String replaceRpId(String str) {
        return (StringUtil.isEmpty(str) || Const.PREFIX_QTY.equals(str))?Const.EMPTY_CH:str;
    }

    public static String replaceEmptyByQ(String str) {
        return StringUtil.isEmpty(str)?Const.PREFIX_QTY:str;
    }

    public static String replaceProperties2Empty(String str) {
        if(isEmpty(str)) {
            return  str;
        }
        String s = str.replaceAll(Const.AS, Const.EMPTY_CH);
        String[] split = StringUtils.split(s, ',');
        StringBuilder sb = new StringBuilder(512);
        for (int i= 0 ,len=split.length,last = len -1;i<len;i++) {
            String[] split1 = StringUtils.split(split[i], ' ');
            sb.append("'' "+split1[1]);
            if (i != last) {
                sb.append(',');
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
       // System.out.print(isEmpty(" "));
        //System.out.print(lpad("1"));
        //System.out.print(isAllChar("AA1AA"));
       // genKey("2","3");


        String s = "C2.TIME_ONE AS ITEM_0,C2.TIME_TWO AS ITEM_1,C2.TIME_THR AS ITEM_2";
        s = s.replaceAll(Const.AS, Const.EMPTY_CH);
        String[] split = StringUtils.split(s, ',');
        StringBuilder sb = new StringBuilder();
        for (int i= 0 ,len=split.length,last = len -1;i<len;i++) {
            String[] split1 = StringUtils.split(split[i], ' ');
            sb.append("'' "+split1[1]);
            if (i != last) {
                sb.append(',');
            }
        }
        System.out.print(sb.toString());
    }
}
