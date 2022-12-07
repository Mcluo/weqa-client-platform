package com.netease.vcloud.qa;

import com.netease.vcloud.qa.dao.WeqaYunxinUserinfoDAO;
import com.netease.vcloud.qa.model.WeqaYunxinUserInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/30 21:40
 */
@Service
public class UserInfoService {

    @Autowired
    private WeqaYunxinUserinfoDAO weqaYunxinUserinfoDAO ;

    /**
     * 批量查询用户信息
     * @param emailSet
     * @return
     */
    public Map<String,UserInfoBO> queryUserInfoBOMap(Set<String> emailSet){
        Map<String,UserInfoBO> userInfoMap = new HashMap<String, UserInfoBO>() ;
        UserInfoBO systemUserInfoBO = new UserInfoBO();
        systemUserInfoBO.setUserName("system");
        systemUserInfoBO.setUserNick("system");
        systemUserInfoBO.setEmail("system");
        userInfoMap.put("system",systemUserInfoBO) ;
        if (CollectionUtils.isEmpty(emailSet)){
            return userInfoMap ;
        }
        List<WeqaYunxinUserInfoDO> weqaYunxinUserInfoDOList = weqaYunxinUserinfoDAO.queryUserInfoByEmailSet(emailSet) ;
        if (weqaYunxinUserInfoDOList!=null){
            for (WeqaYunxinUserInfoDO weqaYunxinUserInfoDO : weqaYunxinUserInfoDOList){
                UserInfoBO userInfoBO = this.buildUserInfoBOByDO(weqaYunxinUserInfoDO) ;
                if (userInfoBO != null){
                    userInfoMap.put(userInfoBO.getEmail(), userInfoBO) ;
                }
            }
        }
        return userInfoMap ;
    }

    /**
     * 单个查询用户信息
     * (不要连续调用)
     * @param email
     * @return
     */
    public UserInfoBO getUserInfoByEmail(String email){
        if (StringUtils.isBlank(email)){
            return null ;
        }
        if (StringUtils.equalsIgnoreCase(email,"system")){
            UserInfoBO userInfoBO = new UserInfoBO();
            userInfoBO.setUserName("system");
            userInfoBO.setUserNick("system");
            userInfoBO.setEmail("system");
            return userInfoBO;
        }else {
            WeqaYunxinUserInfoDO weqaYunxinUserInfoDO = weqaYunxinUserinfoDAO.getUserInfoByEmail(email);
            return this.buildUserInfoBOByDO(weqaYunxinUserInfoDO);
        }
    }

    private UserInfoBO buildUserInfoBOByDO( WeqaYunxinUserInfoDO weqaYunxinUserInfoDO) {
        if (weqaYunxinUserInfoDO == null) {
            return null;
        }
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setUserName(weqaYunxinUserInfoDO.getUserName());
        userInfoBO.setUserNick(weqaYunxinUserInfoDO.getNickName());
        userInfoBO.setEmail(weqaYunxinUserInfoDO.getJobEmail());

        return userInfoBO;
    }

}
