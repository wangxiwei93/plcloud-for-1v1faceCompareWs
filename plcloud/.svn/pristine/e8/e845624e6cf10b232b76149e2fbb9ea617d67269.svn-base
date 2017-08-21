package com.routon.pmax.common.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.pmax.common.model.RoleMenu;
import com.routon.pmax.common.model.UserRole;



public interface RoleMenuMapper extends PageMapper<RoleMenu> {
	
	//@Select("${sql} limit #{offset} , #{rowCount}")
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<RoleMenu> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<RoleMenu> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO rolemenu (RoleID,MenuID,modifyTime) "
			+ " VALUES (#{roleID},#{menuID},#{modifyTime})")
	@SelectKey(statement="SELECT currval('rolemenu_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(RoleMenu roleMenu);
	
	@Delete("DELETE FROM rolemenu WHERE id =#{id}")
	void deleteById(long id);
	
	@Delete("DELETE FROM rolemenu WHERE RoleID =#{roleId}")
	void deleteByRoleId(long roleId);
	
	@Select("SELECT * FROM rolemenu WHERE RoleID =#{roleId}")
	List<RoleMenu> selectByRoleID(long roleId);
	
	
}
