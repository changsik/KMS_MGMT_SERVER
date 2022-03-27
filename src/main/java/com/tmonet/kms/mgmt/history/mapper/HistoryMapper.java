package com.tmonet.kms.mgmt.history.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.history.vo.HistoryVo;

@Repository
public class HistoryMapper {

	@Resource
	private SqlSession sqlSession;

	private static final String NAMESPACE = HistoryMapper.class.getName() + ".";

	public List<HistoryVo> selectHsmHistory(List<String> id) {
		return sqlSession.selectList(NAMESPACE + "selectHsmHistory", id);
	}

	public List<HistoryVo> selectHsmCtrlSvrHistory(List<String> id) {
		return sqlSession.selectList(NAMESPACE + "selectHsmCtrlSvrHistory", id);
	}

	public List<HistoryVo> selectKeyHistory(List<String> id) {
		return sqlSession.selectList(NAMESPACE + "selectKeyHistory", id);
	}

	public List<HistoryVo> selectKeyProfileHistory(List<String> id) {
		return sqlSession.selectList(NAMESPACE + "selectKeyProfileHistory", id);
	}

	public List<HistoryVo> selectClientHistory(List<String> id) {
		return sqlSession.selectList(NAMESPACE + "selectClientHistory", id);
	}

	public List<HistoryVo> selectManagerHistory(List<String> id) {
		return sqlSession.selectList(NAMESPACE + "selectManagerHistory", id);
	}
	
	public List<HistoryVo> selectClientAuthHistory(List<String> id) {
		return sqlSession.selectList(NAMESPACE + "selectClientAuthHistory", id);
	}
	
	public List<HistoryVo> selectManagerAuthHistory(List<String> id) {
		return sqlSession.selectList(NAMESPACE + "selectManagerAuthHistory", id);
	}
}
