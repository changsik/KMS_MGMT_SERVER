package com.tmonet.kms.mgmt.manager.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.manager.vo.ManagerHistoryVo;
import com.tmonet.kms.mgmt.manager.vo.ManagerInfoVo;
import com.tmonet.kms.mgmt.manager.vo.MngAuthHistoryVo;
import com.tmonet.kms.mgmt.manager.vo.MngAuthInfoVo;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;

@Repository
public class ManagerMapper {

	@Resource
	private SqlSession sqlSession;

	private static final String NAMESPACE = ManagerMapper.class.getName() + ".";

	public ManagerInfoVo selectManagerInfo(String managerId) {
		return sqlSession.selectOne(NAMESPACE + "selectManagerInfo", managerId);
	}

	public int insertManagerInfo(ManagerInfoVo manager) {
		return sqlSession.insert(NAMESPACE + "insertManagerInfo", manager);
	}

	public int insertManagerHistory(ManagerHistoryVo history) {
		return sqlSession.insert(NAMESPACE + "insertManagerHistory", history);
	}

	public List<ManagerInfoVo> selectAllManagerInfo() {
		return sqlSession.selectList(NAMESPACE + "selectAllManagerInfo");
	}

	public int updateManagerInfo(ManagerInfoVo manager) {
		return sqlSession.update(NAMESPACE + "updateManagerInfo", manager);
	}

	public int deleteManagerInfo(String managerId) {
		return sqlSession.delete(NAMESPACE + "deleteManagerInfo", managerId);
	}

	public int deleteAllMngAuthInfo(String managerId) {
		return sqlSession.delete(NAMESPACE + "deleteAllMngAuthInfo", managerId);
	}

	public List<MngAuthInfoVo> selectAllMngAuthInfo(String managerId) {
		return sqlSession.selectList(NAMESPACE + "selectAllMngAuthInfo", managerId);
	}

	public int insertMngAuthHistory(MngAuthHistoryVo mngAuthHistory) {
		return sqlSession.insert(NAMESPACE + "insertMngAuthHistory", mngAuthHistory);
	}

	public PartitionInfoVo selectPartitionInfo(String serviceId) {
		return sqlSession.selectOne(NAMESPACE + "selectPartitionInfo", serviceId);
	}

	public MngAuthInfoVo selectMngAuthInfo(MngAuthInfoVo mngAuth) {
		return sqlSession.selectOne(NAMESPACE + "selectMngAuthInfo", mngAuth);
	}

	public int insertMngAuthInfo(MngAuthInfoVo mngAuth) {
		return sqlSession.insert(NAMESPACE + "insertMngAuthInfo", mngAuth);
	}

	public int updateMngAuthInfo(MngAuthInfoVo mngAuth) {
		return sqlSession.update(NAMESPACE + "updateMngAuthInfo", mngAuth);
	}

	public int deleteMngAuthInfo(MngAuthInfoVo mngAuth) {
		return sqlSession.delete(NAMESPACE + "deleteMngAuthInfo", mngAuth);
	}

	public String selectManagerPw(String managerId) {
		return sqlSession.selectOne(NAMESPACE + "selectManagerPw", managerId);
	}

}
