package com.ebase.report.controller;

import com.ebase.report.core.utils.StringUtil;
import com.ebase.report.dao.MovieData10wMapper;
import com.ebase.report.model.MovieData10w;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Demo {

    @Autowired
    private MovieData10wMapper movieData10wMapper;

    @RequestMapping("/demod")
    public void Demo(){

        List<MovieData10w> movieData10wList = movieData10wMapper.selectALl();
//        List<MovieData10w> movieData10wList = new ArrayList<>();
//        movieData10wList.add(movieData10w);
        StringBuilder builder = new StringBuilder("INSERT INTO REPORT.MOVIE_DATA_10W\n" +
                "(PRI_ID, MOVIE_CODE, MOVIE_TITLE, MOVIE_AREA_ID, MOVIE_AREA, MOVIE_DATE, MOVIE_TYPE_ID, MOVIE_TYPE, MOVIE_EMOTION_ID, MOVIE_EMOTION, MOVIE_AGE_ID, MOVIE_AGE, MOVIE_SCORE)\n" +
                "VALUES");
        movieData10wList.stream().forEach(x -> {
            builder.append("(");
            builder.append(x.getPriId());
            builder.append(",");
            builder.append("'");
            builder.append(x.getMovieCode());
            builder.append("',");
            builder.append("'");
            builder.append(x.getMovieTitle());
            builder.append("',");
            builder.append(x.getMovieAreaId());
            builder.append(",'");
            builder.append(x.getMovieArea());
            builder.append("',");
            builder.append(x.getMovieDate());
            builder.append(",");
            builder.append(x.getMovieTypeId());
            builder.append(",'");
            builder.append(x.getMovieType());
            builder.append("',");
            builder.append(x.getMovieEmotionId());
            builder.append(",'");
            builder.append(x.getMovieEmotion());
            builder.append("',");
            builder.append(x.getMovieAgeId());
            builder.append(",'");
            builder.append(x.getMovieAge());
            builder.append("',");
            builder.append(x.getMovieScore());
            builder.append("),");
        });


        String substring = builder.substring(0, builder.lastIndexOf(","));

        substring += ";";

        System.out.println(substring);
    }


    public static void main(String[] args) {
        String sql = "select DATASOURCE_ID, DATASOURCE_NAME, DATABASE_TYPE, CONNPOOL_TYPE, DATASOURCE_URL, USER_NAME, PASSWORD, INITIAL_SIZE, MAX_ACTIVE, MAX_WAIT, MAX_IDLE, REMOVE_STATUS, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT, DATASOURCE_CHINESE_NAME from `rpt_datasource` where REMOVE_STATUS = 0 order by DATASOURCE_ID desc \n";


        StringBuilder s = new StringBuilder(sql);
        String selectCount = s.substring(s.lastIndexOf("from"), s.length() );
        selectCount = "select count(1) " + selectCount;
        System.out.println(selectCount);
    }
}
