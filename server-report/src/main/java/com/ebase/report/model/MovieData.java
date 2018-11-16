package com.ebase.report.model;

import com.ebase.report.common.ExcelAttribute;

import java.math.BigDecimal;

/**
 * demo对象
 * @Auther: wangyu
 */
public class MovieData { //字段既有可能是度量值 又有可能是分组

    @ExcelAttribute(name = "pri_id")
    private Long priId;

    @ExcelAttribute(name = "movie_code")
    private String movieCode;  // 1

    @ExcelAttribute(name = "movie_title")
    private String movieTitle;

    @ExcelAttribute(name = "movie_area_id")
    private Integer movieAreaId;   // 1

    @ExcelAttribute(name = "movie_area")
    private String movieArea;

    @ExcelAttribute(name = "movie_date")
    private Integer movieDate;  // 1

    @ExcelAttribute(name = "movie_type_id")
    private Integer movieTypeId;

    @ExcelAttribute(name = "movie_type")
    private String movieType;

    @ExcelAttribute(name = "movie_emotion_id")
    private Integer movieEmotionId;

    @ExcelAttribute(name = "movie_emotion")
    private String movieEmotion;

    @ExcelAttribute(name = "movie_age_id")
    private Integer movieAgeId;

    @ExcelAttribute(name = "movie_age")
    private String movieAge;

    @ExcelAttribute(name = "movie_score")
    private BigDecimal movieScore;   // 1


    public Long getPriId() {
        return priId;
    }

    public void setPriId(Long priId) {
        this.priId = priId;
    }

    public String getMovieCode() {
        return movieCode;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Integer getMovieAreaId() {
        return movieAreaId;
    }

    public void setMovieAreaId(Integer movieAreaId) {
        this.movieAreaId = movieAreaId;
    }

    public String getMovieArea() {
        return movieArea;
    }

    public void setMovieArea(String movieArea) {
        this.movieArea = movieArea;
    }

    public Integer getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(Integer movieDate) {
        this.movieDate = movieDate;
    }

    public Integer getMovieTypeId() {
        return movieTypeId;
    }

    public void setMovieTypeId(Integer movieTypeId) {
        this.movieTypeId = movieTypeId;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public Integer getMovieEmotionId() {
        return movieEmotionId;
    }

    public void setMovieEmotionId(Integer movieEmotionId) {
        this.movieEmotionId = movieEmotionId;
    }

    public String getMovieEmotion() {
        return movieEmotion;
    }

    public void setMovieEmotion(String movieEmotion) {
        this.movieEmotion = movieEmotion;
    }

    public Integer getMovieAgeId() {
        return movieAgeId;
    }

    public void setMovieAgeId(Integer movieAgeId) {
        this.movieAgeId = movieAgeId;
    }

    public String getMovieAge() {
        return movieAge;
    }

    public void setMovieAge(String movieAge) {
        this.movieAge = movieAge;
    }

    public BigDecimal getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(BigDecimal movieScore) {
        this.movieScore = movieScore;
    }
}
