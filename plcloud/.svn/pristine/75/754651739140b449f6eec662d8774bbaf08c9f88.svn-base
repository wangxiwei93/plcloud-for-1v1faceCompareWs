package com.routon.pmax.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.pmax.common.UserProfile;
import com.routon.pmax.common.constant.SystemBuzFunctionModule;
import com.routon.pmax.common.model.Group;
import com.routon.pmax.common.model.OpLog;
import com.routon.pmax.common.persistence.OpLogMapper;

@Service
public class GroupServiceLog {
	
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void add(Group group, UserProfile optUser) throws Exception {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(16));
		opLog.setType(16);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"group Name:"+group.getName()
				+",group Pid:"+group.getPid();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void edit(Group group, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(17));
		opLog.setType(17);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"group id:"+group.getId()
				+"group Name:"+group.getName()
				+",group Pid:"+group.getPid();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void delete(String ids, UserProfile optUser) throws Exception {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(18));
		opLog.setType(18);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"group ids:"+ids;
				
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
}
