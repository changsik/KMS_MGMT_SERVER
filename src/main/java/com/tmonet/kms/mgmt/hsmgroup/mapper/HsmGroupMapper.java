package com.tmonet.kms.mgmt.hsmgroup.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.hsmgroup.vo.HsmGroupInfoVo;

@Repository
public class HsmGroupMapper {

	@Resource
	private SqlSession sqlSession;

	private static final String NAMESPACE = HsmGroupMapper.class.getName() + ".";

	public String insertHsmGroupInfo(HsmGroupInfoVo vo) {
		sqlSession.insert(NAMESPACE + "insertHsmGroupInfo", vo);
		return vo.getGROUP_ID();
	}

	public List<HsmGroupInfoVo> selectAllHsmGroupInfo() {
		return sqlSession.selectList(NAMESPACE + "selectAllHsmGroupInfo");
	}
	
	public HsmGroupInfoVo selectHsmGroupInfo(String groupId) {
		return sqlSession.selectOne(NAMESPACE + "selectHsmGroupInfo", groupId);
	}

	public int updateHsmGroupInfo(HsmGroupInfoVo vo) {
		return sqlSession.update(NAMESPACE + "updateHsmGroupInfo", vo);
	}

	public int deleteHsmGroupInfo(String groupId) {
		return sqlSession.delete(NAMESPACE + "deleteHsmGroupInfo", groupId);
	}
	
	public int insertHsmHistory(HsmHistoryVo vo) {
		return sqlSession.insert(NAMESPACE + "insertHsmHistory", vo);
	}
}
