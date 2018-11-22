package com.ebase.report.dao;

import com.ebase.report.model.MovieData10w;

import java.util.List;

public interface MovieData10wMapper {
    int deleteByPrimaryKey(Long priId);

    int insert(MovieData10w record);

    int insertSelective(MovieData10w record);

    MovieData10w selectByPrimaryKey(Long priId);

    int updateByPrimaryKeySelective(MovieData10w record);

    int updateByPrimaryKey(MovieData10w record);

    List<MovieData10w> select(MovieData10w record);

    List<MovieData10w> selectALl();

}