package com.tmonet.kms.mgmt.keylifecycle.mapper;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.client.vo.ClientKeyListVo;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.vo.KeyHistoryVo;

@Repository
public class KeyLifecycleMapper {

	@Resource
	private SqlSession sqlSession;

	private static final String NAMESPACE = KeyLifecycleMapper.class.getName() + ".";

	public int changeCliKeyStatus(ClientKeyListVo cliKey) {
		return sqlSession.update(NAMESPACE + "changeCliKeyStatus", cliKey);
	}

	public int changeKeyStatus(KeyListVo key) {
		return sqlSession.update(NAMESPACE + "changeKeyStatus", key);
	}

	public ClientKeyListVo selectClientKeyList(String keyName) {
		return sqlSession.selectOne(NAMESPACE + "selectClientKeyList", keyName);
	}

	public KeyListVo selectKeyList(KeyListVo key) {
		return sqlSession.selectOne(NAMESPACE + "selectKeyList", key);
	}

	public int insertKeyHistory(KeyHistoryVo history) {
		return sqlSession.insert(NAMESPACE + "insertKeyHistory", history);
	}

}
