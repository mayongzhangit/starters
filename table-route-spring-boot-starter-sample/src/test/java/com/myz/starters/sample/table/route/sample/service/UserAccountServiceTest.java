package com.myz.starters.sample.table.route.sample.service;

import com.myz.starters.sample.table.route.sample.TableRouteSampleStartApp;
import com.myz.starters.sample.table.route.sample.mapper.UserAccountMapper;
import com.myz.starters.sample.table.route.sample.model.UserAccount;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 17:43
 * @email 2641007740@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TableRouteSampleStartApp.class)
public class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserAccountMapper userAccountMapper;

    /**
     * 测试多个参数的场景，mybatis内部自动回包装成map类型{@link org.apache.ibatis.reflection.ParamNameResolver}
     */
    @Test
    public void testParamMap(){
        Map<String, Object> params = new HashMap<>(3);
        params.put("gUserId","2KVH9XN");
//        params.put("route", TableRoutUtils.getTableRout(gUserId));
        List<UserAccount> userAccounts = userAccountMapper.queryAccountByGUserId(params);
        System.out.println(userAccounts);
    }

    /**
     * 测试只有一个参数，且类型在{@link TypeHandlerRegistry}白名单类型场景
     */
    @Test
    public void testSingleParam(){
        List<UserAccount> userAccounts = userAccountMapper.findByUserId("2KVH9XN");
        System.out.println(userAccounts);
    }

    /**
     * 测试仅有一个参数，且为JavaBean类型场景
     */
    @Test
    public void testSingParamJavaBean(){
        UserAccount userAccount = new UserAccount();
        userAccount.setGUserId("2KVH9XN");
        List<UserAccount> userAccounts = userAccountMapper.findByModel(userAccount);
        System.out.println(userAccounts);
    }
}
