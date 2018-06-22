package com.galaxydl.rSystem.persistence.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SignalListDAO {

    @Select("select id from r_system_list")
    List<Integer> getList();

    @Insert("insert into r_system_list(id) value(#{id})")
    void add(int id);
}
