package com.hw.service;

import com.hw.dao.DaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author panda.HuangWei.
 * @since 2017-06-23 00:17.
 */
@Service("jobAppService")
public class JobAppService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public void deleteRpStockNow() {
        try {
            dao.delete("JobAppMapper.deleteRpStockNow",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRpBreakBox() {
        try {
            dao.delete("JobAppMapper.deleteRpBreakBox",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveReceivOptHistory() {
        try {
            dao.save("JobAppMapper.saveReceivOptHistory",null);
            dao.delete("JobAppMapper.deleteReceivOpt",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
