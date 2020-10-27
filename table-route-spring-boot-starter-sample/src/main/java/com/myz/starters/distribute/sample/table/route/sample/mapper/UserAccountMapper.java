package com.myz.starters.distribute.sample.table.route.sample.mapper;

import com.myz.starters.distribute.sample.table.route.autoconfigure.annotation.TableRoutingAnnotation;
import com.myz.starters.distribute.sample.table.route.sample.model.UserAccount;
import com.myz.starters.distribute.sample.table.route.sample.util.TableRoutUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
@TableRoutingAnnotation(tableRouter = TableRoutUtils.class)
public interface UserAccountMapper {


	int insert(Map<String, Object> params);

	int insertACID(Map<String, Object> params);

	UserAccount findByGUserId(Map<String, Object> params);

	int updateAccount(Map<String, Object> params);

	List<UserAccount> queryAccountByGUserId(Map<String, Object> params);

	// 其它case
	List<UserAccount> findByUserId(String gUserId);

	List<UserAccount> findByModel(UserAccount userAccount);
}
 