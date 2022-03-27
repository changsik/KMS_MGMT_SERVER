package com.tmonet.kms.mgmt.keyprofile.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyAttributeId;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.keyprofile.mapper.KeyProfileMapper;
import com.tmonet.kms.mgmt.keyprofile.model.UpdateKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileAttrListVo;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileHistoryVo;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileInfoVo;

@Service
public class UpdateKeyProfileService {

	private static final Logger logger = LoggerFactory.getLogger(UpdateKeyProfileService.class);

	@Autowired
	private KeyProfileMapper mapper;

	public void checkParams(String keyProfileId, UpdateKeyProfileRequest request) {
		try {
			KeyProfileInfoVo keyProfile = mapper.selectKeyProfileInfo(keyProfileId);
			if (keyProfile == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			// 입력한 속성아이디가 존재하는 속성아이디인지 확인
			if (request.getListKeyAttribute().size() != 0) {
				for (KeyAttribute keyAttr : request.getListKeyAttribute()) {
					boolean checkKeyAttr = false;
					for (EnumKeyAttributeId attrId : EnumKeyAttributeId.values()) {
						if (attrId.getKey().equals(keyAttr.getKeyAttributeId())) {
							checkKeyAttr = true;
							break;
						}
					}
					if (!checkKeyAttr) {
						throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
					}
				}
			}

			// 입력한 상태값이 존재하는 상태값인지 확인
			if (request.getUsageStatus() != null) {
				boolean checkStatus = false;
				for (EnumUsageStatus status : EnumUsageStatus.values()) {
					if (status.getKey().equals(request.getUsageStatus())) {
						checkStatus = true;
					}
				}
				if (!checkStatus) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}

	}

	@Transactional(rollbackFor = { KMSException.class })
	public void updateKeyProfileInfo(String keyProfileId, UpdateKeyProfileRequest request) {
		try {
			if(request.getListKeyAttribute().size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}
			
			// 변경 값 유무 확인
			KeyProfileInfoVo profileBeforeUpdate = mapper.selectKeyProfileInfo(keyProfileId);
			boolean dataIsChanged = false;

			KeyProfileHistoryVo profileHistory = new KeyProfileHistoryVo();
			String updatedData = "";
			if (request.getKeyProfileName() != null) {
				if (!request.getKeyProfileName().equals(profileBeforeUpdate.getNAME())) {
					updatedData += "NAME:" + request.getKeyProfileName() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getDescription() != null) {
				if (!request.getDescription().equals(profileBeforeUpdate.getDESCRIPTION())) {
					updatedData += "DESCRIPTION:" + request.getDescription() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getUsageStatus() != null) {
				if (!request.getUsageStatus().equals(profileBeforeUpdate.getSTATUS())) {
					updatedData += "STATUS:" + request.getUsageStatus() + " ";
					dataIsChanged = true;
				}
			}

			// 키 프로파일 정보 수정
			if (dataIsChanged) {
				KeyProfileInfoVo keyProfile = new KeyProfileInfoVo();
				keyProfile.setPROFILE_ID(keyProfileId);
				keyProfile.setNAME(request.getKeyProfileName());
				keyProfile.setDESCRIPTION(request.getDescription());
				keyProfile.setSTATUS(request.getUsageStatus());
				keyProfile.setUPT_USER("ADMIN");
				if (mapper.updateKeyProfileInfo(keyProfile) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
				}

				// 키 프로파일 정보 수정 이력 등록
				profileHistory.setPROFILE_ID(keyProfileId);
				profileHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_KEY_PROFILE.getKey());
				profileHistory.setPARAM(updatedData);
				profileHistory.setHMAC("");
				profileHistory.setREG_USER("ADMIN");
				if (mapper.insertKeyProfileHistory(profileHistory) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
				}
			}

			// 키 프로파일 속성 목록 수정
			// 해당 키 프로파일로 생성한 키가 존재하면 속성 목록 수정 불가
			int countKeyMadeOfProfile = -1;
			countKeyMadeOfProfile = mapper.countKeyId(keyProfileId);
			if (countKeyMadeOfProfile < 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			} else if (countKeyMadeOfProfile < 1) {
				List<KeyAttribute> allAttrList = mapper.selectAllKeyProfileAttrList(keyProfileId);
				List<KeyAttribute> attrToDelete = new ArrayList<KeyAttribute>();
				for (KeyAttribute keyAttr : allAttrList) {
					boolean dataIsExist = false;
					for (KeyAttribute reqData : request.getListKeyAttribute()) {
						if (reqData.getKeyAttributeId().equals(keyAttr.getKeyAttributeId())) {
							dataIsExist = true;
						}
					}
					if (!dataIsExist) {
						attrToDelete.add(keyAttr);
					}
				}

				// 입력되지 않은 기존 속성 삭제, 삭제 이력 추가
				for (KeyAttribute keyAttr : attrToDelete) {
					KeyProfileAttrListVo attrListVo = new KeyProfileAttrListVo();
					attrListVo.setPROFILE_ID(keyProfileId);
					attrListVo.setATTR_ID(keyAttr.getKeyAttributeId());
					if (mapper.deleteKeyProfileAttrList(attrListVo) < 1) {
						throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
					}

					KeyProfileHistoryVo attrDeleteHistory = new KeyProfileHistoryVo();
					attrDeleteHistory.setPROFILE_ID(keyProfileId);
					attrDeleteHistory.setATTR_ID(keyAttr.getKeyAttributeId());
					attrDeleteHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_KEY_PROFILE_ATTR.getKey());
					attrDeleteHistory.setHMAC("");
					attrDeleteHistory.setREG_USER("ADMIN");
					if (mapper.insertKeyProfileHistory(attrDeleteHistory) < 1) {
						throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
					}

				}

				if (request.getListKeyAttribute().size() > 0) {
					for (KeyAttribute keyAttr : request.getListKeyAttribute()) {
						KeyProfileAttrListVo attrListVo = new KeyProfileAttrListVo();
						attrListVo.setPROFILE_ID(keyProfileId);
						attrListVo.setATTR_ID(keyAttr.getKeyAttributeId());
						attrListVo.setATTR_DEFAULT(keyAttr.getKeyAttributeValue());

						updatedData = "";

						// 기존에 존재하지 않았으면 등록
						KeyProfileAttrListVo attrBeforeUpdate = mapper.selectKeyProfileAttrList(attrListVo);
						if (attrBeforeUpdate == null) {
							attrListVo.setREG_USER("ADMIN");
							if (mapper.insertToUpdateAttr(attrListVo) < 1) {
								throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
							}

							// 키 프로파일 속성 목록 생성 이력 등록
							profileHistory.setPROFILE_ID(keyProfileId);
							profileHistory.setATTR_ID(keyAttr.getKeyAttributeId());
							profileHistory
									.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_KEY_PROFILE_ATTR.getKey());
							profileHistory.setPARAM(null);
							profileHistory.setHMAC("");
							profileHistory.setREG_USER("ADMIN");
							if (mapper.insertKeyProfileHistory(profileHistory) < 1) {
								throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
							}

							// 기존에 존재했고 속성값이 변경됐으면 수정
						} else {
							if (!keyAttr.getKeyAttributeValue().equals(attrBeforeUpdate.getATTR_DEFAULT())) {
								attrListVo.setUPT_USER("ADMIN");
								if (mapper.updateKeyAttrList(attrListVo) < 1) {
									throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
								}

								// 키 프로파일 속성 목록 수정 이력 등록
								if (keyAttr.getKeyAttributeValue() != null) {
									if (!keyAttr.getKeyAttributeValue().equals(attrBeforeUpdate.getATTR_DEFAULT())) {
										updatedData = "ATTR_DEFAULT:" + keyAttr.getKeyAttributeValue();
									}
								}
								profileHistory.setPROFILE_ID(keyProfileId);
								profileHistory.setATTR_ID(keyAttr.getKeyAttributeId());
								profileHistory.setTYPE(
										EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_KEY_PROFILE_ATTR.getKey());
								profileHistory.setPARAM(updatedData);
								profileHistory.setHMAC("");
								profileHistory.setREG_USER("ADMIN");
								if (mapper.insertKeyProfileHistory(profileHistory) < 1) {
									throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
								}
							}
						}
					}
				}
			}

			return;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}

	}

}
