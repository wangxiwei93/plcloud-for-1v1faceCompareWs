/**
 * 
 */
package com.routon.pmax.admin.privilege.service;

import java.util.List;
import java.util.Map;

import com.routon.pmax.common.UserProfile;
import com.routon.pmax.common.model.Menu;



/**
 * <p>
 * Title: PrivilegeService
 * </p>
 * <p>
 * Description: 定义用户权限管理接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Routon Electronic Co.,Ltd.
 * </p>
 * <p>
 * Date: 2013-5-2
 * </p>
 * 
 * @author 
 * @version 1.0
 */
public interface PrivilegeService {
	/**
	 * 用户登录逻辑
	 * 
	 * @param username
	 *            当前登录用户名称
	 * @param password
	 *            当前登录用户密码
	 * @param loginIP
	 *            登录IP
	 * @return 登录成功返回当前用户的Profile,反之返回Null
	 */
	UserProfile userLogin(String username, String password, String loginIP);

	/**
	 * 用户更换登录密码逻辑
	 * 
	 * @param userId
	 *            当前更换密码的用户ID
	 * @param oldPwd
	 *            当前用户的旧密码
	 * @param newPwd
	 *            当前用户的新密码
	 * @param newPwdConfirm
	 *            当前用户再次输入的新密码
	 * @param optUser
	 *            当前操作用户
	 * @return 更换成功返回True，反之返回False
	 * @throws Exception
	 *             记录用户修改密码产生的异常信息
	 */
	boolean userChangePassword(long userId, String oldPwd, String newPwd, String newPwdConfirm,
			UserProfile optUser) throws Exception;

	/**
	 * 构建用户的权限Map
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户的权限Map
	 */
	Map<String, Menu> buildUserPrivilege(Long userId);

	/**
	 * 检索当前用户的一级主菜单
	 * 
	 * @param userPrivilege
	 *            当前登录的用户的功能权限
	 * @return 如传入值为空返回所有一级主菜单集合，反之指定用户的一级主菜单
	 */
	List<Menu> retrieveCurrentUserMainMenu(Map<String, Menu> userPrivilege);
}
