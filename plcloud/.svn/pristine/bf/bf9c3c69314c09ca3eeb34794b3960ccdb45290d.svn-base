/**
 * 
 */
package com.routon.pmax.common.dao;

import com.routon.pmax.common.PagingBean;
import com.routon.pmax.common.PagingSortDirection;
import com.routon.pmax.common.persistence.PageMapper;

/**
 * <p>
 * Title: PagingDao
 * </p>
 * <p>
 * Description: 定义分页用的数据访问接口
 * 
 * @version 1.0
 */
public interface PagingDao {

	/**
	 * 查询分页数据
	 * 
	 * @param sql
	 *            分页的结构化查询语句
	 * @param countSQL
	 *            分页的总记录数查询语句
	 * @param sortCriterion
	 *            查询结果排序的准则（列、属性名）
	 * @param sortDirection
	 *            查询结果排序的方向（逆序、顺序）
	 * @param startIndex
	 *            起始数据索引，从0开始
	 * @param pageSize
	 *            页大小
	 * @return 分页数据集合
	 */
	public <T> PagingBean<T> query(PageMapper<T> pageMapper, 
			String cond,			 			
			String[] sortCriterion, PagingSortDirection[] sortDirection,
			int startIndex, int pageSize, boolean exportflag);
	
}
