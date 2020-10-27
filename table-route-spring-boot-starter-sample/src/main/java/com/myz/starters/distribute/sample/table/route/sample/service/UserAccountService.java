package com.myz.starters.distribute.sample.table.route.sample.service;

import com.myz.starters.distribute.sample.table.route.sample.mapper.UserAccountMapper;
import com.myz.starters.distribute.sample.table.route.sample.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 17:44
 * @email 2641007740@qq.com
 */
@Service
public class UserAccountService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    public List<UserAccount> queryAccountByGUserId(String gUserId){
        Map<String, Object> params = new HashMap<>(3);
        params.put("gUserId",gUserId);
//        params.put("route", TableRoutUtils.getTableRout(gUserId));
        return userAccountMapper.queryAccountByGUserId(params);
    }

}
