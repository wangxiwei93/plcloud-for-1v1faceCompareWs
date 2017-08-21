package com.routon.pmax.common.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.pmax.common.model.OpLog;


public interface OpLogMapper extends PageMapper<OpLog> {
	//@Select("${sql} limit #{offset} , #{rowCount}")
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<OpLog> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<OpLog> selectBySql(@Param("sql") String sql);

	@Insert("INSERT INTO oplog (object,type,log,time,userId,ip) "
			+ "VALUES (#{object},#{type},#{log},#{time},#{userId},#{ip})")
	/*mysql*/
	//@SelectKey(statement="SELECT LAST_INSERT_ID() AS id", keyProperty="id", before=false, resultType=long.class) 
	/*postgresql*/
	@SelectKey(statement="SELECT currval('oplog_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(OpLog opLog);

	
}
