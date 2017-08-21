package com.routon.pmax.admin.privilege.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routon.pmax.admin.privilege.model.TreeBean;
import com.routon.pmax.admin.privilege.service.log.RoleServiceLog;
import com.routon.pmax.common.PagingBean;
import com.routon.pmax.common.PagingSortDirection;
import com.routon.pmax.common.UserProfile;
import com.routon.pmax.common.dao.mybatis.PagingDaoMybatis;
import com.routon.pmax.common.model.Menu;
import com.routon.pmax.common.model.Role;
import com.routon.pmax.common.model.RoleMenu;
import com.routon.pmax.common.model.UserRole;
import com.routon.pmax.common.persistence.MenuMapper;
import com.routon.pmax.common.persistence.RoleMapper;
import com.routon.pmax.common.persistence.RoleMenuMapper;
import com.routon.pmax.common.persistence.UserRoleMapper;

@Service
public class RoleServiceImpl implements RoleService {
	private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Resource(name = "pagingDaoMybatis")
    private PagingDaoMybatis pagingDao;	
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private RoleServiceLog roleServiceLog;
	
	
	/**
	 * 
	 * @param systemrole
	 * @return -2 角色名已经存在;-1 失败; 大于0 角色id;
	 */
	private Long saveRole(Role role) {
		String roleName = role.getName();
		boolean isExist = roleNameExist(roleName, null);

		if (isExist) {
			logger.info("新增角色时,角色名已经存在");
			return -2l;
		}

		role.setCreateTime(new Date());
		role.setModifyTime(new Date());

		long roleid = roleMapper.insert(role);
		roleid = role.getId();
		if (roleid > 0) {
			return roleid;
		}

		return -1l;
	}
	
	private RoleMenu saveRoleMenu(RoleMenu roleMenu) {
		
		long id = roleMenuMapper.insert(roleMenu);
		roleMenu.setId(id);
		return roleMenu;
	}
	
	/**
	 * 
	 * @param systemrole
	 * @return
	 */
	private Long updateRole(Role role) {

		boolean isExist = roleNameExist(role.getName(), role.getId());

		if (isExist) {
			logger.info("更新角色时,角色名已经存在");
			return -2L;
		}

		List<Role> tem_roles = roleMapper.selectById(role.getId());
		Role tem_role = tem_roles.get(0);
		tem_role.setName(role.getName());
		tem_role.setRemark(role.getRemark());
		tem_role.setModifyTime(new Date());
		roleMapper.update(tem_role);

		return role.getId();
	}
	
	private boolean isUseRole(Long roleId) {

		String sql = "select a.* from userrole a where a.roleId = " + roleId;
		List<UserRole> roleUsers = userRoleMapper.selectBySql(sql);

		if (roleUsers != null && roleUsers.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private List<TreeBean> getMenuTree(Collection<Menu> systemmenus,
			Set<Long> userMenuId, Set<Long> checkedMenuId) {

		List<TreeBean> menuTreeBeans = new ArrayList<TreeBean>();
		for (Menu systemmenu : systemmenus) {
			TreeBean menuTreeBean = new TreeBean();
			menuTreeBean.setId(systemmenu.getId());
			menuTreeBean.setText(systemmenu.getName());

			if (checkedMenuId.contains(systemmenu.getId())) {
				menuTreeBean.setChecked(true);
				
			} else {
				menuTreeBean.setChecked(false);
			}
			
		
			if (systemmenu.getpId() > 0) {
				menuTreeBean.setPid(systemmenu.getpId());
			} else {
				menuTreeBean.setPid(0L);
			}

			List<Menu> childMenus = menuMapper.selectBySql("SELECT * FROM menu WHERE pId = " + systemmenu.getId());
			if (childMenus != null && childMenus.size() > 0) {

				
				menuTreeBean.setChildren(getMenuTree(childMenus, userMenuId, checkedMenuId));
				if (menuTreeBean.getChildren() == null || menuTreeBean.getChildren().size() == 0) {
					continue;
				}

			} else {
				if (userMenuId != null && userMenuId.size() > 0) {

					if (!userMenuId.contains(systemmenu.getId())) {
						continue;
					}

				}else {
					continue;
				}
			}

			if (menuTreeBean.getChildren() == null || menuTreeBean.getChildren().size() == 0) {
				menuTreeBean.setLeaf(true);
			} else {
				menuTreeBean.setLeaf(false);
			}

			menuTreeBeans.add(menuTreeBean);

		}

		return menuTreeBeans;
	}
	
	
	@Override
	public List<TreeBean> getMenuTrees(Long userId, Long roleId) {

		String queryString = "select a.*  from menu a where a.level = 1 order by a.order ";

		List<Menu> systemmenus = menuMapper.selectBySql(queryString);

		// List<Systemmenu> systemmenus =
		// systemMenuDaoHibernate.findByHql("select a  from Systemmenu a where a.menuId in (select b.id.menuId from Systemmenurole b where b.id.roleId in (select c.id.roleId from Systemuserrole c where c.id.userId = "
		// + userId + "))");

		Set<Long> userMenuId = new HashSet<Long>();
		
		if (userId == 1) {
			List<Menu> allMenu = menuMapper.selectBySql("select a.*  from menu a  ");
			if (allMenu != null) {
				for (Menu menu : allMenu) {
					userMenuId.add(menu.getId());
				}
			}
		}
		else {
			List<RoleMenu> user_systemmenuroles = roleMenuMapper.selectBySql("select b.* from rolemenu b where b.roleId in (select c.roleId from userrole c where c.userId = "
					+ userId + ")");
			if (user_systemmenuroles != null) {
				for (RoleMenu systemmenurole : user_systemmenuroles) {
					userMenuId.add(systemmenurole.getMenuID());
				}
			}
		}
		
		

		Set<Long> checkedMenuId = new HashSet<Long>();
		if (roleId != null) {
			List<RoleMenu> checked_systemmenuroles = roleMenuMapper
					.selectBySql("select b.* from rolemenu b where b.roleId =" + roleId);
	
			if (checked_systemmenuroles != null) {
	
				for (RoleMenu systemmenurole : checked_systemmenuroles) {
					checkedMenuId.add(systemmenurole.getMenuID());
				}
			}
		}

		List<TreeBean> menuTreeBeans = getMenuTree(systemmenus, userMenuId, checkedMenuId);

		// menuTreeBeans.get(0).getChildren();

		// List<MenuTreeBean> menuTreeBeans = new ArrayList<MenuTreeBean>();
		// menuTreeBeans.addAll(getGroups4Json(systemmenus, true));

		return menuTreeBeans;
	
	}

	@Transactional(readOnly = true)
	public boolean roleNameExist(String roleName, Long roleId) {
		String sql = "select a.* from role a where a.name = '"+roleName+"'";

		if (roleId != null) {
			sql += " and a.id <>" + roleId;
		}

		List<Role> roles = roleMapper.selectBySql(sql);

		if (roles != null && roles.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Long add(Role role, String menuIds, UserProfile optUser) {
		role.setCreateUserId(optUser.getCurrentUserId()); 
		Long roleId = saveRole(role);

		if (roleId > 0) {
			String menuId_array[] = menuIds.split(",");

			for (String menuId : menuId_array) {

				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setMenuID(Long
						.parseLong(menuId));
				roleMenu.setRoleID(Long.parseLong(roleId + ""));
				roleMenu.setModifyTime(new Date());

				saveRoleMenu(roleMenu);
			}

			roleServiceLog.add(role, menuIds, optUser);
			
			return roleId;

		} else {
			return roleId;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Long edit(Role role, String menuIds, UserProfile optUser) {
		
		Long roleId = updateRole(role);

		if (roleId > 0) {
			roleMenuMapper.deleteByRoleId(roleId);

			String menuId_array[] = menuIds.split(",");
			for (String menuId : menuId_array) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setMenuID(Long
						.parseLong(menuId));
				roleMenu.setRoleID(Long.parseLong(roleId + ""));
				roleMenu.setModifyTime(new Date());

				saveRoleMenu(roleMenu);
			}
			roleServiceLog.edit(role, menuIds, optUser);
			return roleId;
		} else {
			return roleId;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int delete(String roleIds, UserProfile optUser) {
		String roleId_array[] = roleIds.split(",");
		int del_succee_count = 0;
		String del_succee_roleIds =  "";
		for (String roleId : roleId_array) {

			if (isUseRole(Long.parseLong(roleId))) {
				logger.info("该角色被用户使用不能删除");
				continue;
			} else {
				Long roleid = Long.parseLong(roleId);
				roleMenuMapper.deleteByRoleId(roleid);
				
				roleMapper.deleteById(roleid);

				if(del_succee_roleIds.equals("")){
					del_succee_roleIds += roleid;
				}else {
					del_succee_roleIds += ",";
					del_succee_roleIds += roleid;
				}
				
				del_succee_count++;
			}
		}

		if (del_succee_count == 0) {
			return -1;// 全部删除失败
		} else if (del_succee_count == roleId_array.length) {
			roleServiceLog.delete(del_succee_roleIds, optUser);
			return 1;// 全部删除成功
		} else {
			roleServiceLog.delete(del_succee_roleIds, optUser);
			return -2;// 部分删除 还有部分因用户引用关系不能删除
		}

	}

	@Transactional(readOnly = true)
	public Role getRoleByRoleId(Long roleId) {
		List<Role> roles = roleMapper.selectById(roleId);
		
		return fillRole(roles.get(0));

	}

	@Transactional(readOnly = true)
	public List<Role> getRoles(Long loginUserId) {


		String queryString = "select   DISTINCT a.* from role a  where 1=1  ";
		StringBuilder sql = new StringBuilder(queryString);

		if (loginUserId != null) {

			sql.append(" and (select count(*) from rolemenu b where b.roleId = a.id) ");
			sql.append("- (select count(*) from rolemenu b where b.roleId = a.id ");
			sql.append("    and b.menuId IN (select c.menuId FROM rolemenu c where c.roleId in (select d.roleId from userrole d WHERE d.userId = "
					+ loginUserId + " ))");
			sql.append(") <= 0");
		}

		return roleMapper.selectBySql(sql.toString());
	}

	@Transactional(readOnly = true)
	public PagingBean<Role> paging(int startIndex, int pageSize,
			String sortCriterion, String sortDirection, Role queryCondition,
			String in_roleIds, String notin_roleIds, Long loginUserId, boolean exportflag) {

		String pagingQueryLanguage = " select a.* from role a  where 1=1  ";
		// String countpagingQueryLanguage =
		// "select count(DISTINCT a) from Systemrole a  where 1=1  ";
		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);
		// StringBuilder countsbHQL = new
		// StringBuilder(countpagingQueryLanguage);

		if (StringUtils.isNotBlank(queryCondition.getName())) {

			String roleName = queryCondition.getName();
			
			sbHQL.append(" and a.name like '%");
			sbHQL.append(roleName); 
			sbHQL.append("%'");
			 
		}

		

		if (in_roleIds != null) {

			if (StringUtils.isNotBlank(in_roleIds)) {
				  sbHQL.append(" and a.id in ("); 
				  sbHQL.append(in_roleIds);
				  sbHQL.append(")");
			} else {
				sbHQL.append(" and a.id in (");
				sbHQL.append("-1");
				sbHQL.append(")");
			}
		}

		if (StringUtils.isNotBlank(notin_roleIds)) {
			
			 sbHQL.append(" and a.id not in (");
			 sbHQL.append(notin_roleIds); 
			 sbHQL.append(")");
			
		}

		if (loginUserId != null) {
			
			sbHQL.append(" and (select count(*) from rolemenu b where b.roleId = a.id) ");
			sbHQL.append("- (select count(*) from rolemenu b where b.roleId = a.id ");
			sbHQL.append("    and b.menuId IN (select c.menuId FROM rolemenu c where c.roleId in (select d.roleId from userrole d WHERE d.userId = ");
			sbHQL.append(loginUserId);
			sbHQL.append("))");
			sbHQL.append(") <= 0");

	

		}
		
		String[] sortCriterions = null;
		if(sortCriterion != null){
			sortCriterions = new String[] { "a." + sortCriterion };
		}
		PagingSortDirection[] sortDirections =null;
		if(sortDirection != null){
			sortDirections = new PagingSortDirection[] { "desc"
					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
					: PagingSortDirection.ASC };
		}
		PagingBean<Role> pagingSystemrole = pagingDao.query(roleMapper,sbHQL.toString(), 
				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);

		return pagingSystemrole;
	
	}
	
	private Role fillRole(Role role){
		
		List<Menu> menus = menuMapper.selectBySql("select a.* from menu a,rolemenu b where a.id=b.menuId and b.roleId = "+role.getId());
		String menuIds = "";
		String menuNames = "";
		for (Menu menu:menus) {
			if (menuIds.length() == 0) {
				menuIds = menu.getId() +"";
				menuNames = menu.getName();
			}else {
				menuIds +=","+ menu.getId();
				menuNames +=","+ menu.getName();
			}
		}
		role.setMenuIds(menuIds);
		role.setMenuNames(menuNames);
		return role;
	}
	
	

}
