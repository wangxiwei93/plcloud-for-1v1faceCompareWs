package com.routon.pmax.admin.privilege.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routon.pmax.admin.privilege.model.TreeBean;
import com.routon.pmax.admin.privilege.service.log.GroupServiceLog;
import com.routon.pmax.common.PmaxException;
import com.routon.pmax.common.UserProfile;
import com.routon.pmax.common.model.Group;
import com.routon.pmax.common.model.UserGroup;
import com.routon.pmax.common.persistence.GroupMapper;
import com.routon.pmax.common.persistence.UserGroupMapper;

@Service
public class GroupServiceImpl implements GroupService {

	private Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Autowired
	private GroupMapper groupMapper;

	@Autowired
	private UserGroupMapper userGroupMapper;

	@Autowired
	private GroupServiceLog groupServiceLog;
	/**
	 * 
	 * @param Group
	 * @return -2 分组名已经存在;-1 失败; 大于0 分组id;
	 * @throws Exception
	 */
	private Long saveGroup(Group group) throws Exception {
		String groupName = group.getName();
		boolean isExist = groupNameExist(groupName, null);

		if (isExist) {
			logger.info("新增分组时,分组名已经存在");
			return -2l;
		}

		int i = groupMapper
				.selectCountByCondition("select count(*) from terminal where groupId="
						+ group.getPid());
		if (i > 0) {
			throw new PmaxException("该父分组下有终端,不能建子分组");
		}

		i = groupMapper
				.selectCountByCondition("select count(*) from resourcegroup where groupId="
						+ group.getPid());
		if (i > 0) {
			throw new PmaxException("该父分组下有资源文件,不能建子分组");
		}

		group.setCreatetime(new Date());
		group.setModifytime(new Date());

		groupMapper.insert(group);
		long id = group.getId();
		if (id > 0) {

			Group pgroup = groupMapper.selectById(group.getPid());

			group.setLink(pgroup.getLink() + id + ",");
			groupMapper.update(group);

			return id;
		}

		return -1l;
	}

	@Transactional(readOnly = true)
	public boolean groupNameExist(String name, Long id) {
		String sql = "select a.* from groups a where a.name = '" + name + "'";

		if (id != null) {
			sql += " and a.id <>" + id;
		}

		List<Group> groups = groupMapper.selectBySql(sql);

		if (groups != null && groups.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 
	 * @param group
	 * @return
	 */
	private Long updateGroup(Group group) {

		boolean isExist = groupNameExist(group.getName(), group.getId());

		if (isExist) {
			logger.info("更新分组时,分组名已经存在");
			return -2L;
		}

		Group tem_group = groupMapper.selectById(group.getId());
		tem_group.setName(group.getName());

		tem_group.setModifytime(new Date());
		groupMapper.update(tem_group);
		
		return group.getId();
	}

	@Transactional
	public Long add(Group group, UserProfile optUser) throws Exception {

		Long groupId = saveGroup(group);
		if (groupId > 0) {
			if(group.getPid() ==1){
				UserGroup userGroup = new UserGroup();
				userGroup.setGroupId(groupId);
				userGroup.setUserId(optUser.getCurrentUserId());

				userGroupMapper.insert(userGroup);
			}
			groupServiceLog.add(group, optUser);
		}
		return groupId;
	}

	@Transactional
	public Long edit(Group group, UserProfile optUser) {
		Long groupId = updateGroup(group);
		if (groupId > 0) {
			groupServiceLog.edit(group, optUser);
		}
		return groupId;
	}

	@Transactional(rollbackFor = Exception.class)
	public int delete(String ids, UserProfile optUser) throws Exception {
		String id_array[] = ids.split(",");
		int del_succee_count = 0;

		for (String id : id_array) {

			int i = groupMapper
					.selectCountByCondition("select count(*) from usergroup where groupId="
							+ id);
			if (i > 0) {
				throw new PmaxException("该分组下有用户,请先取消与用户的关联");
			}

			i = groupMapper
					.selectCountByCondition("select count(*) from terminal where groupId="
							+ id);
			if (i > 0) {
				throw new PmaxException("该分组下有终端,请先将终端移至其他分组");
			}

			i = groupMapper
					.selectCountByCondition("select count(*) from resourcegroup where groupId="
							+ id);
			if (i > 0) {
				throw new PmaxException("该分组下有资源文件,请先取消与资源文件的关联");
			}

			i = groupMapper
					.selectCountByCondition("select count(*) from noticegroup where groupId="
							+ id);
			if (i > 0) {
				throw new PmaxException("该分组下有公告,请先取消与公告的关联");
			}

			groupMapper.deleteById(Long.parseLong(id));
			del_succee_count++;

		}

		if (del_succee_count == 0) {
			return -1;// 全部删除失败
		}
		else if (del_succee_count == id_array.length) {
			groupServiceLog.delete(ids, optUser);
			return 1;// 全部删除成功
		}
		else {
			return -2;// 部分删除 还有部分因用户引用关系不能删除
		}
	}

	public List<TreeBean> getGroupTreeByUserId(Long opuserId,
			Group queryCondition, Long userId, boolean onlyleafcheck,
			boolean showRelevanceCount) {

		// String usergroupsql =
		// "select m.id from groups m join usergroup on m.Link like concat(concat('%,', usergroup.GroupID), ',%') and usertgroup.UserID = "

		// String sql =
		// "select distinct a.* from groups a, (select DISTINCT m.id from groups m join usergroup on m.Link like concat(concat('%,', usergroup.GroupID), ',%') and usergroup.UserID ="+userId+") b where a.id = b.id ";
		String sql = "select DISTINCT m.* from groups m join usergroup on m.Link like concat(concat('%,', usergroup.GroupID), ',%') and usergroup.UserID ="
				+ opuserId;
		if (queryCondition != null
				&& StringUtils.isNotBlank(queryCondition.getName())) {
			sql += " and m.name like '%" + queryCondition.getName() + "%'";
		}
		List<Group> groups = groupMapper.selectBySql(sql);

		Set<Long> checkedGroupId = new HashSet<Long>();
		if (userId != null) {
			sql = "SELECT DISTINCT a.* FROM usergroup a where a.userID = "
					+ userId;
			List<UserGroup> checked_userGroups = userGroupMapper
					.selectBySql(sql);
			if (checked_userGroups != null) {

				for (UserGroup userGroup : checked_userGroups) {
					checkedGroupId.add(userGroup.getGroupId());
				}
			}
		}

		HashMap<Long, TreeBean> groupHashMaps = new HashMap<Long, TreeBean>();
		for (Group group : groups) {
			TreeBean treeBean = new TreeBean();
			treeBean.setId(group.getId());
			treeBean.setName(group.getName());
			treeBean.setPid(group.getPid());
			if (checkedGroupId.contains(group.getId())) {
				treeBean.setChecked(true);
			}
			if (group.getPid() < 0) {
				treeBean.setNocheck(true);
				treeBean.setOpen(true);
			}

			String[] temp = group.getLink().split(",");
			for (String str : temp) {
				if (StringUtils.isNotBlank(str)) {
					Long id = Long.parseLong(str);
					if (!groupHashMaps.containsKey(id)) {
						// List<Group> group_tems =
						Group group_tem = groupMapper.selectById(id);
						TreeBean treeBeantem = new TreeBean();
						treeBeantem.setId(group_tem.getId());
						treeBeantem.setName(group_tem.getName());
						treeBeantem.setPid(group_tem.getPid());
						groupHashMaps.put(id, treeBeantem);
					}

				}
			}

			if (queryCondition != null
					&& StringUtils.isNotBlank(queryCondition.getName())) {
				List<Group> childGroups = groupMapper
						.selectBySql("select * from groups where link like concat(concat('%,', "
								+ group.getId() + "), ',%') ");
				for (Group childGroup : childGroups) {
					if (!groupHashMaps.containsKey(childGroup.getId())) {
						TreeBean treeBeantem = new TreeBean();
						treeBeantem.setId(childGroup.getId());
						treeBeantem.setName(childGroup.getName());
						treeBeantem.setPid(childGroup.getPid());
						groupHashMaps.put(childGroup.getId(), treeBeantem);
					}
				}
			}

			groupHashMaps.put(group.getId(), treeBean);
		}

		Iterator<Long> iterator = groupHashMaps.keySet().iterator();
		Set<Long> removeIds = new HashSet<Long>();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			TreeBean treeBean = groupHashMaps.get(id);

			if (showRelevanceCount) {
				relevanceCount(treeBean);
			}

			TreeBean parentTreeBean = groupHashMaps.get(treeBean.getPid());

			if (parentTreeBean != null) {
				Collection<TreeBean> parentschild = parentTreeBean
						.getChildren();
				if (parentschild == null) {
					parentschild = new ArrayList<TreeBean>();
				}
				parentschild.add(treeBean);
				if (onlyleafcheck) {
					parentTreeBean.setNocheck(true);
				}
				parentTreeBean.setChildren(parentschild);
				removeIds.add(id);
				// iterator.remove();
			}
		}
		for (Long id : removeIds) {
			groupHashMaps.remove(id);
		}

		return new ArrayList<TreeBean>(groupHashMaps.values());

	}

	private void relevanceCount(TreeBean tree) {
		Long id = tree.getId();
		int i = groupMapper
				.selectCountByCondition("select count(*) from usergroup where groupId="
						+ id);
		if (i > 0) {
			tree.setUserCount(i);
		}

		i = groupMapper
				.selectCountByCondition("select count(*) from terminal where groupId="
						+ id);
		if (i > 0) {
			tree.setTerminalCount(i);
		}

		i = groupMapper
				.selectCountByCondition("select count(*) from resourcegroup where groupId="
						+ id);
		if (i > 0) {
			tree.setResourceCount(i);
		}

		i = groupMapper
				.selectCountByCondition("select count(*) from noticegroup where groupId="
						+ id);
		if (i > 0) {
			tree.setNoticeCount(i);
		}
	}

	public List<TreeBean> getGroupTreeByUserId(Long opuserId,
			Group queryCondition, Long userId) {
		return getGroupTreeByUserId(opuserId, queryCondition, userId, false,
				true);
	}

	@Override
	public Long move(Group group, UserProfile optUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TreeBean> getGroupTreeByUserId(Long opuserId,
			Group queryCondition, Long userId, boolean onlyleafcheck) {
		return getGroupTreeByUserId(opuserId, queryCondition, userId,
				onlyleafcheck, false);
	}

}
