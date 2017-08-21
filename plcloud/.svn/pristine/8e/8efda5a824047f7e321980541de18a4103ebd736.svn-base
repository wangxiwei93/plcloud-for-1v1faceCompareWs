package com.routon.pmax.admin.privilege.service;

import java.util.List;

import com.routon.pmax.admin.privilege.model.TreeBean;
import com.routon.pmax.common.PagingBean;
import com.routon.pmax.common.UserProfile;
import com.routon.pmax.common.model.Role;



/**
 * 
 * <p>
 * Title: RoleService
 * </p>
 * <p>
 * Description: 定义系统角色业务接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Routon Electronic Co.,Ltd.
 * </p>
 * <p>
 * Date: 2013-5-14
 * </p>
 * 
 * @author 
 * @version 1.0
 */
public interface RoleService {

	/**
	 * 获取菜单树
	 * 
	 * @param userId
	 *            当前使用用户id
	 * @param roleId
	 *            角色Id 可NULL
	 * @return 菜单树集合
	 */
	public List<TreeBean> getMenuTrees(Long userId, Long roleId);

	/**
	 * 判断角色名使用存在
	 * 
	 * @param roleName
	 *            角色名
	 * @param roleId
	 *            (可null,当null时 只通过roleName来检查; 当不为null时,roleId和roleName 同时校验)
	 * @return 存在返回true，反之返回false
	 */
	public boolean roleNameExist(String roleName, Long roleId);

	/**
	 * 新增角色
	 * 
	 * @param systemrole
	 *            用户角色
	 * @param menuIds
	 *            角色的菜单ID集合
	 * @param optUser
	 *            当前操作用户
	 * @return -2 角色名已经存在;-1 失败; 大于0 角色id;
	 */
	public Long add(Role systemrole, String menuIds, UserProfile optUser);

	/**
	 * 编辑角色
	 * 
	 * @param systemrole
	 *            用户角色
	 * @param menuIds
	 *            角色的菜单ID集合
	 * @param optUser
	 *            当前操作用户
	 * @return 已编辑角色的ID
	 */
	public Long edit(Role systemrole, String menuIds, UserProfile optUser);

	/**
	 * 删除角色
	 * 
	 * @param roleIds
	 *            角色的ID集合
	 * @param optUser
	 *            当前操作用户
	 * @return -1 所有的删除失败; -2 部分删除成功 用户有关联不能删除; 1 全部删除成功;
	 */
	public int delete(String roleIds, UserProfile optUser);

	/**
	 * 通过角色ID 获取角色
	 * 
	 * @param roleId
	 *            角色的ID
	 * @return 系统角色
	 */
	public Role getRoleByRoleId(Long roleId);

	/**
	 * 根据loginUserId 获取其权限所见的所有系统角色
	 * 
	 * @param loginUserId
	 *            当前登录用户ID
	 * 
	 * @return loginUserId为空返回所有的系统角色集合，反之其权限所见系统角色集合
	 */
	public List<Role> getRoles(Long loginUserId);

	/**
	 * 获取角色列表的分页查询
	 * 
	 * @param startIndex
	 *            起始数据索引，从0开始
	 * @param pageSize
	 *            页大小
	 * @param sortCriterion
	 *            用于排序的字段
	 * @param sortDirection
	 *            排序方向
	 * @param queryCondition
	 *            查询条件
	 * @param in_roleIds
	 * @param notin_roleIds
	 * @param loginUserId
	 *            当前登录用户ID
	 * @return 已分页角色集合
	 */
	public PagingBean<Role> paging(int startIndex, int pageSize, String sortCriterion,
			String sortDirection, Role queryCondition, String in_roleIds,
			String notin_roleIds, Long loginUserId, boolean exportflag);
}
