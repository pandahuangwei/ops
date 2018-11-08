package com.hw.entity.base;

/**
 * Created by Panda.HuangWei on 2016/10/22.
 */
public class Select {
    private String group;
    private String code;
    private String value;
    private String id;
    private int sort;

    public Select(){}

    public Select(String id,String value){
        this.id = id;
        this.value = value;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
