/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.service.pic;

import java.util.List;

import javax.annotation.Resource;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;
import org.springframework.stereotype.Service;

/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 17:18
 */
@Service("picturesService")
public class PicturesService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
    * 新增
    */
    public void save(PageData pd)throws Exception{
        dao.save("PicturesMapper.save", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd)throws Exception{
        dao.delete("PicturesMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd)throws Exception{
        dao.update("PicturesMapper.edit", pd);
    }

    /*
    *列表
    */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("PicturesMapper.datalistPage", page);
    }

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("PicturesMapper.listAll", pd);
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("PicturesMapper.findById", pd);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("PicturesMapper.deleteAll", ArrayDATA_IDS);
    }

    /*
    * 批量获取
    */
    public List<PageData> getAllById(String[] ArrayDATA_IDS)throws Exception{
        return (List<PageData>)dao.findForList("PicturesMapper.getAllById", ArrayDATA_IDS);
    }

    /*
    * 删除图片
    */
    public void delTp(PageData pd)throws Exception{
        dao.update("PicturesMapper.delTp", pd);
    }

}
