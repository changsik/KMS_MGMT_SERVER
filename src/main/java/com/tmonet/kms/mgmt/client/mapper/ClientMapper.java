package com.tmonet.kms.mgmt.client.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tmonet.kms.mgmt.client.vo.ClientKeyListVo;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;
import com.tmonet.kms.mgmt.client.vo.CliAuthHistoryVo;
import com.tmonet.kms.mgmt.client.vo.CliAuthInfoVo;
import com.tmonet.kms.mgmt.client.vo.ClientHistoryVo;
import com.tmonet.kms.mgmt.client.vo.ClientInfoVo;

@Repository
public class ClientMapper {

	@Resource
	private SqlSession sqlSession;

	private static final String NAMESPACE = ClientMapper.class.getName() + ".";

	public int insertClientInfo(ClientInfoVo client) {
		return sqlSession.insert(NAMESPACE + "insertClientInfo", client);
	}

	public ClientInfoVo selectClientInfo(String clientIp) {
		return sqlSession.selectOne(NAMESPACE + "selectClientInfo", clientIp);
	}

	public int updateClientInfo(ClientInfoVo client) {
		return sqlSession.update(NAMESPACE + "updateClientInfo", client);
	}

	public int deleteClientInfo(String clientIp) {
		return sqlSession.delete(NAMESPACE + "deleteClientInfo", clientIp);
	}

	public int insertClientKeyList(ClientKeyListVo vo) {
		return sqlSession.insert(NAMESPACE + "insertClientKeyList", vo);
	}

	public int updateClientKeyList(ClientKeyListVo vo) {
		return sqlSession.update(NAMESPACE + "updateClientKeyList", vo);
	}

	public ClientKeyListVo selectClientKeyList(String keyId) {
		return sqlSession.selectOne(NAMESPACE + "selectClientKeyList", keyId);
	}

	public String selectClientKeyListIp(String clientIp) {
		return sqlSession.selectOne(NAMESPACE + "selectClientKeyListIp", clientIp);
	}

	public int updateClientInfoKey(ClientInfoVo client) {
		return sqlSession.update(NAMESPACE + "updateClientInfoKey", client);
	}

	public int insertClientHistory(ClientHistoryVo history) {
		return sqlSession.insert(NAMESPACE + "insertClientHistory", history);
	}

	public List<ClientInfoVo> selectAllClientInfo() {
		return sqlSession.selectList(NAMESPACE + "selectAllClientInfo");
	}

	public int deleteAllClientKey(String clientIp) {
		return sqlSession.delete(NAMESPACE + "deleteAllClientKey", clientIp);
	}

	public int deleteClientKey(String clientKeyId) {
		return sqlSession.delete(NAMESPACE + "deleteClientKey", clientKeyId);
	}

	public int deleteCliAuthInfo(CliAuthInfoVo cliAuth) {
		return sqlSession.delete(NAMESPACE + "deleteCliAuthInfo", cliAuth);
	}

	public List<CliAuthInfoVo> selectAllCliAuthInfo(String clientIp) {
		return sqlSession.selectList(NAMESPACE + "selectAllCliAuthInfo", clientIp);
	}

	public int insertCliAuthHistory(CliAuthHistoryVo history) {
		return sqlSession.insert(NAMESPACE + "insertCliAuthHistory", history);
	}

	public List<CliAuthInfoVo> selectAllClientKey(String clientIp) {
		return sqlSession.selectList(NAMESPACE + "selectAllClientKey", clientIp);
	}

	public String selectCliKeyId() {
		return sqlSession.selectOne(NAMESPACE + "selectCliKeyId");
	}

	public List<ClientKeyListVo> selectAllClientKeyList(ClientKeyListVo cliKey) {
		return sqlSession.selectList(NAMESPACE + "selectAllClientKeyList", cliKey);
	}

	public PartitionInfoVo selectPartitionInfo(String serviceId) {
		return sqlSession.selectOne(NAMESPACE + "selectPartitionInfo", serviceId);
	}

	public CliAuthInfoVo selectCliAuthInfo(CliAuthInfoVo cliAuth) {
		return sqlSession.selectOne(NAMESPACE + "selectCliAuthInfo", cliAuth);
	}

	public int insertCliAuthInfo(CliAuthInfoVo cliAuth) {
		return sqlSession.insert(NAMESPACE + "insertCliAuthInfo", cliAuth);
	}

	public int updateCliAuthInfo(CliAuthInfoVo cliAuth) {
		return sqlSession.update(NAMESPACE + "updateCliAuthInfo", cliAuth);
	}

	public String selectClientKeyValue(ClientKeyListVo clientKey) {
		return sqlSession.selectOne(NAMESPACE + "selectClientKeyValue", clientKey);
	}
	
}
