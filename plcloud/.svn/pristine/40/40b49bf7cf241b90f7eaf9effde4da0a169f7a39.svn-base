package com.routon.pmax.common;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import com.routon.pmax.common.constant.CVal;

/**
 * <p>
 * Title: PagingBean
 * </p>
 * <p>
 * Description: 定义用于分页的数据Bean
 * </p>
 * <p>
 * Date: Mar 21, 2011
 * </p>
 * @version 1.0
 */
public class PagingBean<T> implements Serializable , PaginatedList{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8909716669054482158L;

	/**
     * 起始记录数索引（用于客户端绑定）
     */
    private Integer start;

    /**
     * 页大小（用于客户端绑定）
     */
    private Integer limit;

    /**
     * 排序方向（用与客户端绑定）
     */
    private String  dir;

    /**
     * 排序属性（用于客户端绑定）
     */
    private String  sort;

    /**
     * 总记录数
     */
    private Integer totalCount;

    /**
     * 数据集合
     */
    private List<T> datas;
    
    private int status = CVal.Success;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    

    //-------------PaginatedList-----------------------
	@SuppressWarnings("rawtypes")
	@Override
	@JsonIgnore
	public List getList() {

		return datas;
	}

	@Override
	public int getPageNumber() {
		if (this.limit == 0 ) {
			return 1;
		}
		
		
		return 1 + this.start/this.limit;
	}

	@Override
	public int getObjectsPerPage() {

		return limit;
	}

	@Override
	public int getFullListSize() {

		return this.totalCount;
	}

	@Override
	public String getSortCriterion() {

		return this.sort;
	}

	@Override
	public SortOrderEnum getSortDirection() {
		if ("desc".equalsIgnoreCase(this.dir)){
			return SortOrderEnum.DESCENDING;
		}
		else if ("asc".equalsIgnoreCase(this.dir)){
			return SortOrderEnum.ASCENDING;
		}
		
		return null;
	}

	@Override
	public String getSearchId() {

		return null;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
