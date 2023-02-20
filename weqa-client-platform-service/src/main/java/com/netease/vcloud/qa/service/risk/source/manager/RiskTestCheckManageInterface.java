package com.netease.vcloud.qa.service.risk.source.manager;

import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/17 16:41
 */
public interface RiskTestCheckManageInterface<T  extends CheckInfoStructInterface> {

    /**
     * 同步数据
     */
    void asyncDate(RiskCheckRange rangeType , Long rangeId) ;

    /**
     * 获取当前数据
     * @return
     */
    String getCurrentData(RiskCheckRange rangeType , Long rangeId) ;


    T buildCheckInfo(String  checkInfoStructStr) ;
    /**
     * 当前是否存在风险
     * @param t
     * @return
     */
    boolean hasRisk(T t, String currentData ) ;

}
