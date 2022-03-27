package com.tmonet.kms.mgmt.partition.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.hsmgroup.vo.HsmGroupInfoVo;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;

@Repository
public class PartitionMapper {

	@Resource
	private SqlSession sqlSession;

	private static final String NAMESPACE = PartitionMapper.class.getName() + ".";

	public PartitionInfoVo selectPartitionInfo(PartitionInfoVo partition) {
		return sqlSession.selectOne((NAMESPACE + "selectPartitionInfo"), partition);
	}

	public int insertPartitionInfo(PartitionInfoVo vo) {
		return sqlSession.insert(NAMESPACE + "insertPartitionInfo", vo);
	}

	public List<PartitionInfoVo> selectAllPartitionInfo() {
		return sqlSession.selectList(NAMESPACE + "selectAllPartitionInfo");
	}

	public int updatePartitionInfo(PartitionInfoVo vo) {
		return sqlSession.update(NAMESPACE + "updatePartitionInfo", vo);
	}

	public int deletePartitionInfo(PartitionInfoVo vo) {
		return sqlSession.delete(NAMESPACE + "deletePartitionInfo", vo);
	}

	public int insertHsmHistory(HsmHistoryVo hsmHistory) {
		return sqlSession.insert(NAMESPACE + "insertHsmHistory", hsmHistory);
	}

	public int countMngAuthInfo(String serviceId) {
		return sqlSession.selectOne(NAMESPACE + "countMngAuthInfo", serviceId);
	}

	public int countCliAuthInfo(String serviceId) {
		return sqlSession.selectOne(NAMESPACE + "countCliAuthInfo", serviceId);
	}

	public int countKeyList(PartitionInfoVo partition) {
		return sqlSession.selectOne(NAMESPACE + "countKeyList", partition);
	}

	public HsmGroupInfoVo selectHsmGroupInfo(String hsmGroupId) {
		return sqlSession.selectOne(NAMESPACE + "selectHsmGroupInfo", hsmGroupId);
	}

	public List<PartitionInfoVo> selectAllPatOfGroup(String hsmGroupId) {
		return sqlSession.selectList(NAMESPACE + "selectAllPatOfGroup", hsmGroupId);
	}

}
