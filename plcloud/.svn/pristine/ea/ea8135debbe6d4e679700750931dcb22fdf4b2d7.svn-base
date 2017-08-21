package com.routon.pmax.admin.oplog.service;

import com.routon.pmax.common.PagingBean;
import com.routon.pmax.common.model.OpLog;


public interface OpLogService {
	public PagingBean<OpLog> paging(int startIndex, int pageSize,
			String sortCriterion, String sortDirection,
			OpLog queryCondition, String in_Ids, String notin_Ids,
			Long loginUserId, boolean exportflag);
}
