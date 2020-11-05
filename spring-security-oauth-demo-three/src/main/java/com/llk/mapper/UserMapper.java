package com.llk.mapper;

import com.llk.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where yonghuming = #{yonghuming}")
    public User getByUsername(@Param("yonghuming") String yonghuming);
}
