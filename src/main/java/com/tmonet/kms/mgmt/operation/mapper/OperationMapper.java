package com.tmonet.kms.mgmt.operation.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.vo.KeyHistoryVo;

@Repository
public class OperationMapper {
	private static final String NAMESPACE = OperationMapper.class.getName() + ".";

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
	
	public int insertKeyHistory(KeyHistoryVo vo) {
		return sqlSession.insert(NAMESPACE + "insertKeyHistory", vo);
	}
	
}
