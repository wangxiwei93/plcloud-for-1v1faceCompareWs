package com.routon.pmax.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.pmax.common.UserProfile;
import com.routon.pmax.common.constant.SystemBuzFunctionModule;
import com.routon.pmax.common.model.OpLog;
import com.routon.pmax.common.model.User;
import com.routon.pmax.common.persistence.OpLogMapper;

@Service
public class UserServiceLog {
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void add(User systemuser, String groupIds, String roleIds,
			UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(24));
		opLog.setType(24);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" User Name :"+systemuser.getUserName()
				+";User RealName:"+systemuser.getRealName()
				+";User Company:"+systemuser.getCompany()
				+";User Phone:"+systemuser.getPhone()
				+";User groupIds:"+groupIds
				+";User roleIds:"+roleIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void edit(User systemuser, String groupIds, String roleIds,
			UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(25));
		opLog.setType(25);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" User id :"+systemuser.getId()
				+";User Name :"+systemuser.getUserName()
				+";User RealName:"+systemuser.getRealName()
				+";User Company:"+systemuser.getCompany()
				+";User Phone:"+systemuser.getPhone()
				+";User groupIds:"+groupIds
				+";User roleIds:"+roleIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
		
	}
	
	public void delete(String userIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(26));
		opLog.setType(26);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" userIds :"+userIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	
	public void resetPwd(String userIds, UserProfile optUser) throws Exception {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(27));
		opLog.setType(27);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" userIds :"+userIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
}
