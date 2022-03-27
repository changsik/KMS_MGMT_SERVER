package com.tmonet.kms.mgmt.key.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.vo.KeyHistoryVo;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;
import com.tmonet.kms.mgmt.key.vo.KeyProfileInfoVo;

@Repository
public class KeyManagementMapper {
	private static final String NAMESPACE = KeyManagementMapper.class.getName() + ".";

	@Resource
	private SqlSession sqlSession;

	public KeyListVo selectKeyList(String KEY_ID) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "selectKeyList", KEY_ID);
	}

	public List<KeyAttrListVo> selectKeyAttrList(KeyListVo vo) throws Exception {
		return sqlSession.selectList(NAMESPACE + "selectKeyAttrList", vo);
	}

	public HsmServiceInfoVo selectHsmServiceInfo(String SERVICE_ID) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "selectHsmServiceInfo", SERVICE_ID);
	}

	public KeyProfileInfoVo selectKeyProfileInfo(String PROFILE_ID) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "selectKeyProfileInfo", PROFILE_ID);
	}

	public List<KeyProfileAttrListVo> selectKeyProfileAttrList(String PROFILE_ID) throws Exception {
		return sqlSession.selectList(NAMESPACE + "selectKeyProfileAttrList", PROFILE_ID);
	}

	public int insertKeyList(KeyListVo vo) {
		return sqlSession.insert(NAMESPACE + "insertKeyList", vo);
	}

	public int insertKeyAttrList(KeyAttrListVo vo) {
		return sqlSession.insert(NAMESPACE + "insertKeyAttrList", vo);
	}

	public int insertKeyHistory(KeyHistoryVo vo) {
		return sqlSession.insert(NAMESPACE + "insertKeyHistory", vo);
	}
	
	public int updateKeyList(KeyListVo vo) {
		return sqlSession.update(NAMESPACE + "updateKeyList", vo);
	}

	public int deleteKeyList(String KEY_ID) {
		return sqlSession.delete(NAMESPACE + "deleteKeyList", KEY_ID);
	}
	
	public int deleteKeyAttrList(String KEY_ID) {
		return sqlSession.delete(NAMESPACE + "deleteKeyAttrList", KEY_ID);
	}
	
}
