package com.netease.vcloud.qa.service.perf.report;

import com.netease.vcloud.qa.service.perf.view.PerfBasePerfTaskInfoVO;

import java.util.List;

/**
 * 用于生成报告/基线的基础接口类
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 16:39
 */
public interface AutoPerfBaseReportInterface {

    List<PerfBasePerfTaskInfoVO> getBaseTaskInfoList(String query, int start, int limit) ;
}
