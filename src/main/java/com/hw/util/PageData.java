/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:49
 */
public class PageData  extends HashMap implements Map{

    private static final long serialVersionUID = 1L;

    Map map = null;
    HttpServletRequest request;

    public PageData(HttpServletRequest request){
        this.request = request;
        Map properties = request.getParameterMap();
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            value = value==null?"":value.trim();
            returnMap.put(name, value);
        }
        map = returnMap;
    }

    public PageData() {
        map = new HashMap();
    }

    public PageData(PageData entity) {
        this.map = entity.map;
        this.request = entity.request;
    }

    public PageData(PageData entity,boolean flag) {
        this.map = new HashMap(entity.map);
        this.request = entity.request;
    }
    @Override
    public Object get(Object key) {
        Object obj = null;
        if(map.get(key) instanceof Object[]) {
            Object[] arr = (Object[])map.get(key);
            obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
        } else {
            obj = map.get(key);
        }
        return obj;
    }

    public String getString(Object key) {
        Object o = get(key);
        if (o instanceof String) {
            return (String)o;
        } else if(o instanceof Integer) {
            return String.valueOf(o);
        }else if(o instanceof Double) {
            return String.valueOf(o);
        }
        return (String)get(key);
    }

    /**
     * 如果字符串为空值，返回0
     * @param key key
     * @return rs or 0
     */
    public String getStringNot4Zero(Object key) {
       String rs = getString(key);
        return StringUtil.isEmpty(rs)?Const.ZERO_CH:rs;
    }

    /**
     * 如果字符串为空值，返回""
     * @param key key
     * @return rs or ""
     */
    public String getStringNot4EmpyCh(Object key) {
        String rs = getString(key);
        return StringUtil.isEmpty(rs)?Const.EMPTY_CH:rs;
    }
    /**
     * 如果字符串为空值，返回"Q"
     * @param key key
     * @return rs or "Q"
     */
    public String getStringEmpty2QCh(Object key) {
        String rs = getString(key);
        return StringUtil.isEmpty(rs)?Const.PREFIX_QTY:rs;
    }

    public java.util.Date getUtilDate(Object key) {
        Object o = get(key);
        try {
            return DateUtil.fomatTime(o.toString());
        } catch (Exception e) {
            return DateUtil.getTm();
        }
    }

    public Integer getInteger(Object key) {
        Object o = get(key);
        try{
            return StringUtil.parseInt(o.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getList(Object key) {
        Object o = get(key);
        String s = (String)o;
        if (StringUtil.isEmpty(s)) {
            return new ArrayList<>();
        }

        if (s.contains(Const.SPLIT_SEMI)) {
            String[] arr = StringUtils.split(s,Const.SPLIT_SEMI);
            return Arrays.asList(arr);
        }
        String[] arr = StringUtils.split(s,Const.SPLIT_COMMA);
        return Arrays.asList(arr);
    }

    public Set<String> getSet(Object key) {
        Object o = get(key);
        String s = (String)o;
        if (StringUtil.isEmpty(s)) {
            return new HashSet<>();
        }

        String[] arr = StringUtils.split(s,Const.SPLIT_COMMA);
        return new HashSet<>(Arrays.asList(arr));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return map.containsValue(value);
    }

    public Set entrySet() {
        // TODO Auto-generated method stub
        return map.entrySet();
    }

    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return map.isEmpty();
    }

    public Set keySet() {
        // TODO Auto-generated method stub
        return map.keySet();
    }

    @SuppressWarnings("unchecked")
    public void putAll(Map t) {
        // TODO Auto-generated method stub
        map.putAll(t);
    }

    public int size() {
        // TODO Auto-generated method stub
        return map.size();
    }

    public Collection values() {
        // TODO Auto-generated method stub
        return map.values();
    }
}
