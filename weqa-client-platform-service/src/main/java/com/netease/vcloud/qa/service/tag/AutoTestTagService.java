package com.netease.vcloud.qa.service.tag;

import com.netease.vcloud.qa.dao.ClientAutoTagBaseInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTagRelationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 13:52
 */
@Service
public class AutoTestTagService {

    @Autowired
    private ClientAutoTagRelationDAO clientAutoTagRelationDAO ;

    @Autowired
    private ClientAutoTagBaseInfoDAO clientAutoTagBaseInfoDAO ;



}
