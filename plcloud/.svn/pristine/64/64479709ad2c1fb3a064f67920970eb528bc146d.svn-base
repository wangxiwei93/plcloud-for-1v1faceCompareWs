package com.routon.pmax.common.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.pmax.common.model.RoleMenu;
import com.routon.pmax.common.model.UserGroup;



public interface UserGroupMapper extends PageMapper<UserGroup> {
	
	@Select("${sql} limit #{offset} , #{rowCount}")
	List<UserGroup> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<UserGroup> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO usergroup (userId,groupId) "
			+ " VALUES (#{userId},#{groupId})")
	@SelectKey(statement="SELECT currval('usergroup_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(UserGroup userGroup);
	
	@Delete("DELETE FROM rolemenu WHERE id =#{id}")
	void deleteById(long id);
	
	@Delete("DELETE FROM usergroup WHERE userId =#{userId}")
	int deleteByUserId(long userId);
}
