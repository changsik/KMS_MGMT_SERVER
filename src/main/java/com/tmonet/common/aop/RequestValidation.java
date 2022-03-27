package com.tmonet.common.aop;

import java.net.Inet4Address;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tmonet.kms.hmac.sha256.HmacSha256;
import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.vo.ClientInfoVo;
import com.tmonet.kms.mgmt.client.vo.ClientKeyListVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumAuthenticationType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyLifecycleState;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.manager.mapper.ManagerMapper;
import com.tmonet.common.util.Converter;

@Aspect
@Component
@Order(value = 1)
public class RequestValidation {
	private static final Logger logger = LoggerFactory.getLogger(RequestValidation.class);

	@Autowired
	private ManagerMapper managerMapper;
	
	@Autowired
	private ClientMapper clientMapper;

	private String getIp(HttpServletRequest request) {
		
		String ip = request.getHeader("X-Forwaded-For");
	
		logger.info(">>>> X-FORWARDED-FOR : " + ip);
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.info(">>>> Proxy-Client-IP : " + ip);
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.info(">>>> WL-Proxy-Client-IP : " + ip);
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			logger.info(">>>> HTTP_CLIENT_IP : " + ip);
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			logger.info(">>>> HTTP_X_FORWARDED_FOR : " + ip);
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		logger.info(">>>> Result : IP Address : " + ip);
		
		return ip;
	}
	
	@Before("execution(* com.tmonet..controller.*Controller.*(..))")
	public void requestValidation(JoinPoint jp) throws Throwable {
		// 1. 요청 데이터 BaseRequest 처리
		HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		for (Object arg : jp.getArgs()) {
			if (arg instanceof BaseRequest) {
				BaseRequest baseRequest = (BaseRequest) arg;
				httpRequest.setAttribute("baseRequest", baseRequest);
			}
		}
		
		// 2. http 헤더 내에 필수 데이터(accessToken, timestamp) 포함여부 확인
		if (httpRequest.getHeader("userAccessToken") == null || httpRequest.getHeader("requestTimestamp") == null) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}
		
		// 3. id를 체크하여 관리자인지 여부 확인
		String key = "";
		/*
		if (httpRequest.getHeader("id") != null) {	// 관리자 
			key = managerMapper.selectManagerPw(httpRequest.getHeader("id"));
		} else {	// 일반 Client
			String ipAddress = getIp(httpRequest);
			if(ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
				ipAddress = Inet4Address.getLocalHost().getHostAddress();
			}
			ClientInfoVo client = clientMapper.selectClientInfo(ipAddress);
			if(client.getAUTH_TYPE().equals(EnumAuthenticationType.AUTH_TYPE_API_KEY.getKey())) {
				ClientKeyListVo clientKey = new ClientKeyListVo();
				clientKey.setIP_ADDR(ipAddress);
				clientKey.setSTATUS(EnumKeyLifecycleState.KEY_LC_ACTIVE.getKey());
				key = clientMapper.selectClientKeyValue(clientKey);
			}
		}
		*/
		
		// 4. 클라이언트 인증
		if(!key.equals("")) {
			String data = httpRequest.getMethod() + httpRequest.getRequestURL() + httpRequest.getHeader("requestTimestamp");
			
			logger.debug("httpRequest.getMethod() : {}", httpRequest.getMethod());
			logger.debug("httpRequest.getRequestURL() : {}", httpRequest.getRequestURL());
			logger.debug("httpRequest. requestTimestamp : {}", httpRequest.getHeader("requestTimestamp"));
			
			String hashData = Converter.byteArrayToHexString(HmacSha256.hmac(key, data));
			logger.debug("hashData : " + hashData);
			
			if (!hashData.equals(httpRequest.getHeader("userAccessToken"))) {
	//			throw new KMSException(KMSErrorCode.UNAUTHORIZED);
				logger.debug("hashData is not equal as userAccessToken");
			}else {
				logger.debug("hashData is equal as userAccessToken");
			}
		}

	}

}
