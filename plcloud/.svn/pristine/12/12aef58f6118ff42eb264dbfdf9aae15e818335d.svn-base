package com.routon.pmax.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.pmax.common.UserProfile;
import com.routon.pmax.common.constant.SystemBuzFunctionModule;
import com.routon.pmax.common.model.OpLog;
import com.routon.pmax.common.model.Role;
import com.routon.pmax.common.persistence.OpLogMapper;

@Service
public class RoleServiceLog {
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void add(Role role, String menuIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(21));
		opLog.setType(21);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" role Name :"+role.getName()
				+";menuIds :"+menuIds;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void edit(Role role, String menuIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(22));
		opLog.setType(22);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" role id :"+role.getId()
				+" role Name :"+role.getName()
				+";menuIds :"+menuIds;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void delete(String roleIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(23));
		opLog.setType(23);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"  roleIds :"+roleIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
}
