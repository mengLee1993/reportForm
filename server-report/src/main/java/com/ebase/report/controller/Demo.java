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

        String s = "12313${demo3}23${demo2}e2dsda${demo}1231";

//        String aaa = s.replace("${demo}", "AAA");
        String[] strings1 = new String[]{"${demo3}","${demo2}","${demo}"};
        String[] strings2 = new String[]{"AAA","BBB","CCC"};
        String s1 = StringUtil.replaceEach(s, strings1, strings2);
        System.out.println(s1);
    }
}
