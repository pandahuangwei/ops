package com.hw.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 集合与字符串转变工具类.
 * Created by Panda.HuangWei on 2016/11/14.
 */
public class VaryUtils {
    public static final String LINE_SIGN = "-";
    private static final char COMMA_SIGN = ',';
    private static Pattern p = Pattern.compile("\\(.*?\\)");

    /**
     * 将配载代码中的区域代码或城市代码转为set
     *
     * @param src 字符串(支持带(,))
     * @return 字符串转变的集合
     */
    public static Set<String> genSet(String src) {
        if (!StringUtils.isEmpty(src)) {
            String[] split = StringUtils.split(src, COMMA_SIGN);
            Set<String> set = new HashSet<String>(split.length);

            for (String cg : split) {
                set.add(cg);
            }
            // set.addAll(Arrays.asList(split));
            return set;
        }
        return new HashSet<String>(0);
    }

    /**
     * 将集合转换为字符串.
     *
     * @param set 集合.
     * @return 字符串
     */
    public static String genString(Set<String> set) {
        if (set == null || (set != null && set.isEmpty())) {
            return new String();
        }
        StringBuffer buffer = new StringBuffer(16);
        int count = 0;
        for (String str : set) {
            if (count != 0) {
                buffer.append(COMMA_SIGN);
            }
            buffer.append(str);
            count++;
        }
        return buffer.toString();
    }

    /**
     * 将配载代码中的区域代码或城市代码转为list
     *
     * @param src 字符串(支持带(,))
     * @return 字符串转变的集合
     */
    public static List<String> genList(String src) {
        List<String> list = new ArrayList<String>(512);
        if (!StringUtils.isEmpty(src)) {
            src = getElems(src, list);
            String[] split = StringUtils.split(src, COMMA_SIGN);
            for (String str : split) {
                list.add(str);
            }
            // list.addAll(Arrays.asList(split));
        }
        return list;
    }

    /**
     * 将配载代码中的区域代码或城市代码转为list
     *
     * @param src 字符串(支持带(,))
     * @return 字符串转变的集合
     */
    public static List<String> genListOrNull(String src) {
        List<String> list = new ArrayList<String>(512);
        if (!StringUtils.isEmpty(src)) {
            src = getElems(src, list);
            String[] split = StringUtils.split(src, COMMA_SIGN);
            for (String str : split) {
                list.add(str);
            }
            return list;
        }
        return null;
    }

    /**
     * 替归移除
     *
     * @param codes
     * @return
     */
    private static String getElems(String codes, List<String> list) {
        Matcher m = p.matcher(codes);
        String elem;
        while (m.find()) {
            elem = m.group().trim();
            codes = codes.replace(elem, ",");
            list.add(elem);
        }
        return codes;
    }

    /**
     * 将集合转换为字符串.
     *
     * @param list 集合.
     * @return 字符串
     */
    public static String genString(List<String> list) {
        if (list == null || (list != null && list.isEmpty())) {
            return new String();
        }
        StringBuffer buffer = new StringBuffer(16);
        int count = 0;
        for (String str : list) {
            if (count != 0) {
                buffer.append(COMMA_SIGN);
            }
            buffer.append(str);
            count++;
        }
        return buffer.toString();
    }

    public static Set<String> getRepeatElem(List<String> list) {
        if (list == null || (list != null && list.isEmpty())) {
            return new HashSet<>();
        }
        Set<String> rs = new HashSet<>();
        Set<String> rs2 = new HashSet<>();
        for (String str : list) {
            if (!rs.add(str)) {
                rs2.add(str);
            }
        }
        return rs2;
    }
}
