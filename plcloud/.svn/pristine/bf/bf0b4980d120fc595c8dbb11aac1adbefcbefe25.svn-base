package com.routon.pmax.common.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.pmax.common.model.UserRole;



public interface UserRoleMapper extends PageMapper<UserRole> {
	@Select("${sql} limit #{offset} , #{rowCount}")
	List<UserRole> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<UserRole> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO userrole (userId,roleId,modifyTime) "
			+ " VALUES (#{userId},#{roleId},#{modifyTime})")
	@SelectKey(statement="SELECT currval('userrole_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(UserRole userRole);
	
	@Delete("DELETE FROM userrole WHERE id =#{id}")
	void deleteById(long id);
	
	@Delete("DELETE FROM userrole WHERE RoleID =#{roleId}")
	void deleteByRoleId(long roleId);
	
	@Delete("DELETE FROM userrole WHERE userId =#{userId}")
	void deleteByUserId(long userId);
	
	@Select("select * FROM userrole WHERE userId =#{userId}")
	List<UserRole> selectByUserId(long userId);
}
