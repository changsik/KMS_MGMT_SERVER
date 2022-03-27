package com.tmonet.kms.mgmt.keyprofile.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileAttrListVo;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileHistoryVo;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileInfoVo;

@Repository
public class KeyProfileMapper {

	@Resource
	private SqlSession sqlSession;

	private static final String NAMESPACE = KeyProfileMapper.class.getName() + ".";

	public int existKeyProfileId(String keyProfileId) {
		return sqlSession.selectOne(NAMESPACE + "existKeyProfileId", keyProfileId);
	}

	public int insertKeyProfileInfo(KeyProfileInfoVo keyProfile) {
		return sqlSession.insert(NAMESPACE + "insertKeyProfileInfo", keyProfile);
	}

	public int insertKeyProfileAttr(KeyProfileAttrListVo attrList) {
		return sqlSession.insert(NAMESPACE + "insertKeyProfileAttr", attrList);
	}

	public int insertToUpdateAttr(KeyProfileAttrListVo attrList) {
		return sqlSession.insert(NAMESPACE + "insertToUpdateAttr", attrList);
	}

	public KeyProfileInfoVo selectKeyProfileInfo(String keyProfileId) {
		return sqlSession.selectOne(NAMESPACE + "selectKeyProfileInfo", keyProfileId);
	}

	public List<KeyProfileInfoVo> selectAllKeyProfileInfo() {
		return sqlSession.selectList(NAMESPACE + "selectAllKeyProfileInfo");
	}

	public KeyProfileAttrListVo selectKeyProfileAttrList(KeyProfileAttrListVo attrList) {
		return sqlSession.selectOne(NAMESPACE + "selectKeyProfileAttrList", attrList);
	}

	public List<KeyAttribute> selectAllKeyProfileAttrList(String keyProfileId) {
		return sqlSession.selectList(NAMESPACE + "selectAllKeyProfileAttrList", keyProfileId);
	}

	public int countKeyId(String keyProfileId) {
		return sqlSession.selectOne(NAMESPACE + "countKeyId", keyProfileId);
	}

	public int countAttrList(String keyProfileId) {
		return sqlSession.selectOne(NAMESPACE + "countAttrList", keyProfileId);
	}

	public KeyProfileInfoVo selectUpdatedKeyProfile(String keyProfileId) {
		return sqlSession.selectOne(NAMESPACE + "searchUpdatedKeyProfile", keyProfileId);
	}

	public int deleteKeyProfileAttrList(KeyProfileAttrListVo attrList) {
		return sqlSession.delete(NAMESPACE + "deleteKeyProfileAttrList", attrList);
	}

	public int deleteAllKeyProfileAttrList(String keyProfileId) {
		return sqlSession.delete(NAMESPACE + "deleteAllKeyProfileAttrList", keyProfileId);
	}

	public int deleteKeyProfileInfo(String keyProfileId) {
		return sqlSession.delete(NAMESPACE + "deleteKeyProfileInfo", keyProfileId);
	}

	public int updateKeyProfileInfo(KeyProfileInfoVo keyProfile) {
		return sqlSession.update(NAMESPACE + "updateKeyProfileInfo", keyProfile);
	}

	public int updateKeyAttrList(KeyProfileAttrListVo attrList) {
		return sqlSession.update(NAMESPACE + "updateKeyAttrList", attrList);
	}

	public KeyProfileInfoVo selectRegisteredKeyProfile() {
		return sqlSession.selectOne(NAMESPACE + "selectRegisteredKeyProfile");
	}

	public int insertKeyProfileHistory(KeyProfileHistoryVo profileHistory) {
		return sqlSession.insert(NAMESPACE + "insertKeyProfileHistory", profileHistory);
	}

}
