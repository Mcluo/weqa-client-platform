package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientPerfFirstFrameDataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/9 17:41
 */
@Mapper
public interface ClientPerfFirstFrameDataDAO {

    int patchInsertFirstFrameData(@Param("list") List<ClientPerfFirstFrameDataDO> dataDOList) ;

}
