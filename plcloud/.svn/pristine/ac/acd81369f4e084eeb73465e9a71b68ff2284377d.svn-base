package com.routon.pmax.admin.oplog.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.pmax.common.PagingBean;
import com.routon.pmax.common.PagingSortDirection;
import com.routon.pmax.common.dao.mybatis.PagingDaoMybatis;
import com.routon.pmax.common.model.OpLog;
import com.routon.pmax.common.persistence.OpLogMapper;

@Service
public class OpLogServiceImpl implements OpLogService {

	@Autowired
	private OpLogMapper opLogMapper;
	
	@Resource(name = "pagingDaoMybatis")
	private PagingDaoMybatis pagingDao;
	
	@Override
	public PagingBean<OpLog> paging(int startIndex, int pageSize,
			String sortCriterion, String sortDirection, OpLog queryCondition,
			String in_Ids, String notin_Ids, Long loginUserId, boolean exportflag) {

		String pagingQueryLanguage = "SELECT DISTINCT a.*,b.username,b.realName FROM oplog a left join users b on a.userId = b.id WHERE 1=1   ";
		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);

		if (queryCondition.getType() > 0) {

			int type = queryCondition.getType();

			sbHQL.append(" and a.type = '");
			sbHQL.append(type);
			sbHQL.append("'");

		}

		if (StringUtils.isNotBlank(queryCondition.getStartDate_createTime())) {
			sbHQL.append(" and a.time >= '");
			if (queryCondition.getEndDate_createTime().length() > 11) {
				sbHQL.append(queryCondition.getStartDate_createTime());
			}
			else {
				sbHQL.append(queryCondition.getStartDate_createTime()
						+ " 23:59:59");
			}
			sbHQL.append("'");

		}

		if (StringUtils.isNotBlank(queryCondition.getEndDate_createTime())) {
			sbHQL.append(" and a.time <= '");
			if (queryCondition.getEndDate_createTime().length() > 11) {
				sbHQL.append(queryCondition.getEndDate_createTime());
			}
			else {
				sbHQL.append(queryCondition.getEndDate_createTime()
						+ " 23:59:59");
			}
			sbHQL.append("'");

		}

		if (in_Ids != null) {
			if (StringUtils.isNotBlank(in_Ids)) {
				sbHQL.append(" and a.id in (");
				sbHQL.append(in_Ids);
				sbHQL.append(")");

			}
			else {
				sbHQL.append(" and a.id in (");
				sbHQL.append("-1");
				sbHQL.append(")");
			}
		}

		if (StringUtils.isNotBlank(notin_Ids)) {
			sbHQL.append(" and a.id not in (");
			sbHQL.append(notin_Ids);
			sbHQL.append(")");

		}

		if (loginUserId != null) {
			sbHQL.append(" and (select count(DISTINCT bb.menuId) from rolemenu bb where bb.roleId in (select cc.roleId from userrole cc where cc.userId = a.userId)) ");
			sbHQL.append("-    (select count(DISTINCT bb.menuId) from rolemenu bb where bb.roleId in (select cc.roleId from userrole cc where cc.userId = a.userId) ");
			sbHQL.append("    and bb.menuId in (select cc.menuId FROM rolemenu cc where cc.roleId in (select dd.roleId from userrole dd where dd.userId ="
					+ loginUserId + " ))");
			sbHQL.append(") <= 0");

			sbHQL.append(" and a.userId NOT IN (");
			sbHQL.append("	SELECT DISTINCT userid FROM usergroup WHERE groupid NOT IN ( ");
			sbHQL.append("		SELECT g.id FROM groups g JOIN usergroup b ON g.link LIKE CONCAT(CONCAT('%,', b.groupid), ',%') AND b.userid = "
					+ loginUserId);
			sbHQL.append(")");
			sbHQL.append(")  ");

			// if (StringUtils.isNotBlank(queryCondition.getGroupIds())) {
			// sbHQL.append(" and (select count(DISTINCT cc.groupId) from usergroup cc where cc.userId = a.id) ");
			// sbHQL.append("-    (select count(DISTINCT cc.groupId) from usergroup cc where cc.userId = a.id ");
			// sbHQL.append("    and cc.groupId in (" +
			// queryCondition.getGroupIds()
			// + " ))");
			// sbHQL.append(" <= 0");
			//
			//
			// }

		}
		
		
		

		if (StringUtils.isBlank(sortCriterion)){
			sortCriterion = "a.time";
		}
		String[] sortCriterions = null;
		if (sortCriterion != null) {
			sortCriterions = new String[] {sortCriterion };
		}
		PagingSortDirection[] sortDirections = null;
		if(StringUtils.isBlank(sortDirection)){
			sortDirection = "desc";
		}
		if (sortDirection != null) {
			sortDirections = new PagingSortDirection[] { "desc"
					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
					: PagingSortDirection.ASC };
		}

		PagingBean<OpLog> paging = pagingDao.query(opLogMapper,
				sbHQL.toString(), sortCriterions, sortDirections, startIndex,
				pageSize,  exportflag);

		

		return paging;

	
	}



}
