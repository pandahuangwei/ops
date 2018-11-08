package com.hw.util;

import com.hw.service.outstock.AssignOutService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * 单据流水号生成
 * @author Panda.HuangWei.
 * @version 1.0 .
 * @since 2016.12.20 12:31.
 */
public class PickNoUtil {

    /**
     * 拣货单单号生成
     * @return 拣货单单号
     */
    public static String getPickOne() {
        String str = Tools.readTxtFile(Const.PICKONE);
        String[] arr = StringUtils.split(str,Const.SPLIT_COMMA);
        String yymmdd = DateUtil.getYYMMDD();
        String serNo = StringUtil.lpadTwo(arr[1]);
       return yymmdd+Const.SPLIT_LINE+ serNo;
    }

    /**
     * 设值下一张拣货单的单号
     * @return
     */
    public static void setPickOne(String billNo) {
        String str = Tools.readTxtFile(Const.PICKONE);
        String[] arr = StringUtils.split(str,Const.SPLIT_COMMA);
         //已经使用的单号
        String[] arrUsed = StringUtils.split(billNo,Const.SPLIT_LINE);
        String yymm = arr[0];
        if (!arr[0].equals(arrUsed[0])) {
            yymm = arrUsed[0];
        }

        String serNo = String.valueOf(Integer.parseInt(arrUsed[1])+1);
        String newNo = yymm+Const.SPLIT_COMMA+ serNo;
        Tools.writeFile(Const.PICKONE,newNo);
    }

}
