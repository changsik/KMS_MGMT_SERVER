package com.tmonet.kms.mgmt.hsmctl.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.hsm.vo.HsmInfoVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlListVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrHistoryVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@Repository
public class HsmCtlMapper {

	@Resource
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = HsmCtlMapper.class.getName() + ".";
	
	public HsmCtlSvrInfoVo selectHsmCtlSvrInfo(String hsmCtlSvrId) {
		return sqlSession.selectOne(NAMESPACE + "selectHsmCtlSvrInfo" , hsmCtlSvrId);
	}

	public int insertHsmCtlSvrInfo(HsmCtlSvrInfoVo ctlSvr) {
		return sqlSession.insert(NAMESPACE + "insertHsmCtlSvrInfo", ctlSvr);
	}

	public int insertHsmCtlSvrHistory(HsmCtlSvrHistoryVo history) {
		return sqlSession.insert(NAMESPACE + "insertHsmCtlSvrHistory", history);
	}

	public List<HsmCtlSvrInfoVo> selectAllHsmCtlSvrInfo() {
		return sqlSession.selectList(NAMESPACE + "selectAllHsmCtlSvrInfo");
	}

	public int updateHsmCtlSvrInfo(HsmCtlSvrInfoVo ctlSvr) {
		return sqlSession.update(NAMESPACE + "updateHsmCtlSvrInfo", ctlSvr);
	}

	public int deleteAllHsmCtlList(String hsmCtlSvrId) {
		return sqlSession.delete(NAMESPACE + "deleteAllHsmCtlList", hsmCtlSvrId);
	}

	public int deleteHsmCtlSvrInfo(String hsmCtlSvrId) {
		return sqlSession.delete(NAMESPACE + "deleteHsmCtlSvrInfo", hsmCtlSvrId);
	}

	public List<HsmCtlListVo> selectAllHsmCtlList(String hsmCtlSvrId) {
		return sqlSession.selectList(NAMESPACE + "selectAllHsmCtlList", hsmCtlSvrId);
	}

	public HsmCtlListVo selectHsmCtlList(HsmCtlListVo hsmCtl) {
		return sqlSession.selectOne(NAMESPACE + "selectHsmCtlList", hsmCtl);
	}

	public int insertHsmCtlList(HsmCtlListVo hsmCtl) {
		return sqlSession.insert(NAMESPACE + "insertHsmCtlList", hsmCtl);
	}

	public int updateHsmCtlList(HsmCtlListVo hsmCtl) {
		return sqlSession.update(NAMESPACE + "updateHsmCtlList", hsmCtl);
	}

	public int deleteHsmCtlList(HsmCtlListVo hsmCtl) {
		return sqlSession.delete(NAMESPACE + "deleteHsmCtlList", hsmCtl);
	}

	public HsmInfoVo selectHsmInfo(String hsmId) {
		return sqlSession.selectOne(NAMESPACE + "selectHsmInfo", hsmId);
	}

}
