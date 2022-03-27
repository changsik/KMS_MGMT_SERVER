package com.tmonet.kms.mgmt.hsm.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.hsm.vo.HsmInfoVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlListVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrHistoryVo;
import com.tmonet.kms.mgmt.hsmgroup.vo.HsmGroupInfoVo;

@Repository
public class HsmMapper {

	@Resource
	private SqlSession sqlSession;

	private static final String NAMESPACE = HsmMapper.class.getName() + ".";

	public int insertHsmInfo(HsmInfoVo vo) {
		return sqlSession.insert(NAMESPACE + "insertHsmInfo", vo);
	}

	public HsmInfoVo selectRegisteredHsmInfo() {
		return sqlSession.selectOne(NAMESPACE + "selectRegisteredHsmInfo");
	}

	public HsmInfoVo selectHsmInfo(HsmInfoVo hsm) {
		return sqlSession.selectOne(NAMESPACE + "selectHsmInfo", hsm);
	}

	public List<HsmInfoVo> selectAllHsmInfo() {
		return sqlSession.selectList(NAMESPACE + "selectAllHsmInfo");
	}

	public int updateHsmInfo(HsmInfoVo hsm) {
		return sqlSession.update(NAMESPACE + "updateHsmInfo", hsm);
	}

	public int deleteHsmInfo(String hsmId) {
		return sqlSession.delete(NAMESPACE + "deleteHsmInfo", hsmId);
	}

	public int insertHsmHistory(HsmHistoryVo hsmHistory) {
		return sqlSession.insert(NAMESPACE + "insertHsmHistory", hsmHistory);
	}

	public List<HsmInfoVo> selectAllHsmOfGroup(String hsmGroupId) {
		return sqlSession.selectList(NAMESPACE + "selectAllHsmOfGroup", hsmGroupId);
	}

	public List<HsmCtlListVo> selectAllHsmCtlList(String hsmId) {
		return sqlSession.selectList(NAMESPACE + "selectAllHsmCtlList", hsmId);
	}

	public int deleteAllHsmCtlList(String hsmId) {
		return sqlSession.delete(NAMESPACE + "deleteAllHsmCtlList", hsmId);
	}

	public int insertHsmCtlSvrHistory(HsmCtlSvrHistoryVo ctlHistory) {
		return sqlSession.insert(NAMESPACE + "insertHsmCtlSvrHistory", ctlHistory);
	}

	public HsmGroupInfoVo selectHsmGroupInfo(String hsmId) {
		return sqlSession.selectOne(NAMESPACE + "selectHsmGroupInfo", hsmId);
	}

}
