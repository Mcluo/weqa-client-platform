package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.AutoTestResultDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AutoTestResultDAO {

    int insertIntoAutoTestResult(@Param("result") AutoTestResultDO autoTestResultDO) ;

    AutoTestResultDO getAutoTestResultById(@Param("id") Long id) ;
}
