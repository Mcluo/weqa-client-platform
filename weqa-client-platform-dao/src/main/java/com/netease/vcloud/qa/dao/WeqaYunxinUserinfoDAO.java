package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.WeqaYunxinUserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/30 21:24
 */
@Mapper
public interface WeqaYunxinUserinfoDAO {

    List<WeqaYunxinUserInfoDO> queryUserInfoByEmailSet(@Param("emailSet")Set<String> emailSet) ;

    WeqaYunxinUserInfoDO getUserInfoByEmail(@Param("email")String email) ;

}
