package com.routon.pmax.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.pmax.common.UserProfile;
import com.routon.pmax.common.constant.SystemBuzFunctionModule;
import com.routon.pmax.common.model.OpLog;
import com.routon.pmax.common.persistence.OpLogMapper;
import com.routon.pmax.common.persistence.UserMapper;

@Service
public class PrivilegeServiceLog {
	@Autowired
	private OpLogMapper opLogMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	public void userLogin(Long userId,String username, String password, String loginIP) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(19));
		opLog.setType(19);
		opLog.setTime(new Date());
		opLog.setUserId(userId);
		opLog.setIp(loginIP);
		String log = opLog.getObject()
				+"username :"+username;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void userChangePassword(long userId, String oldPwd, String newPwd,
			String newPwdConfirm, UserProfile optUser) throws Exception {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(20));
		opLog.setType(20);
		opLog.setTime(new Date());
		opLog.setUserId(userId);
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"username :"+optUser.getCurrentUserLoginName() +"修改密码为："+newPwdConfirm;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
			
}
