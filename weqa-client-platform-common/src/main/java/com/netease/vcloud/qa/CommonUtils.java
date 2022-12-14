package com.netease.vcloud.qa;

import com.netease.vcloud.qa.result.view.UserInfoVO;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/12 10:35
 */
public class CommonUtils {
    public static UserInfoVO buildUserInfoVOByBO(UserInfoBO userInfoBO){
        if (userInfoBO == null){
            return null ;
        }
        UserInfoVO userInfoVO = new UserInfoVO() ;
        userInfoVO.setName(userInfoBO.getUserName());
        userInfoVO.setNick(userInfoBO.getUserNick());
        userInfoVO.setEmail(userInfoBO.getEmail());
        return userInfoVO ;
    }
}
