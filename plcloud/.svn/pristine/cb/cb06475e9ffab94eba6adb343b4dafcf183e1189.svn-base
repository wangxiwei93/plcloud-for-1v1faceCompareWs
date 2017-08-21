package com.routon.pmax.common.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.routon.pmax.common.model.Menu;
import com.routon.pmax.common.model.Role;



public interface MenuMapper extends PageMapper<Menu> {
	
	@Select("${sql} limit #{offset} , #{rowCount}")
	List<Menu> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<Menu> selectBySql(@Param("sql") String sql);
	
	@Select("select * from menu")
	List<Menu> findAllMainMenu();
	
	@Select("SELECT * FROM menu WHERE id =#{menuID}")
	List<Menu> selectByMenuID(long menuID);
}
