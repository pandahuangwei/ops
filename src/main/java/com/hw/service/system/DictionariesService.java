/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.service.system;
import java.util.List;

import javax.annotation.Resource;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;
import org.springframework.stereotype.Service;
/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 15:17
 */
@Service("dictionariesService")
public class DictionariesService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    //新增
    public void save(PageData pd)throws Exception{
        dao.save("DictionariesMapper.save", pd);
    }

    //修改
    public void edit(PageData pd)throws Exception{
        dao.update("DictionariesMapper.edit", pd);
    }

    //通过id获取数据
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("DictionariesMapper.findById", pd);
    }

    //查询总数
    public PageData findCount(PageData pd) throws Exception {
        return (PageData) dao.findForObject("DictionariesMapper.findCount", pd);
    }

    //查询某编码
    public PageData findBmCount(PageData pd) throws Exception {
        return (PageData) dao.findForObject("DictionariesMapper.findBmCount", pd);
    }

    //列出同一父类id下的数据
    public List<PageData> dictlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("DictionariesMapper.dictlistPage", page);

    }

    //删除
    public void delete(PageData pd) throws Exception {
        dao.delete("DictionariesMapper.delete", pd);

    }
}
