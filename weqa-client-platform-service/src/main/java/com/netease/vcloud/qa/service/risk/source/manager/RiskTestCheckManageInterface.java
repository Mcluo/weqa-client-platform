package com.netease.vcloud.qa.service.risk.source.manager;

import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/17 16:41
 */
public interface RiskTestCheckManageInterface<T extends RiskTestCheckManageInterface> {

    /**
     * 同步数据
     */
    void asyncDate() ;

    /**
     * 获取当前数据
     * @return
     */
    String getCurrentData() ;

    /**
     * 当前是否存在风险
     * @param t
     * @return
     */
    boolean hasRisk(T t) ;

}
