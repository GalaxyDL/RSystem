package com.galaxydl.rSystem.persistence.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FilenameDAO {

    @Select("select filename from r_system_list where id=#{id}")
    String getFilename(int id);

    @Select("select filename from r_system_list")
    List<String> listFilename();

    @Update("update r_system_list set filename=#{filename} where id=#{id}")
    void setFilename(@Param("id") int id, @Param("filename") String filename);
}
