package com.ebase.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 */
public class MovieData10w implements Serializable {
    /**
     * Database Column Remarks:
     *   id
     */
    private Long priId;

    /**
     * Database Column Remarks:
     *   电影编码 度量 avg
     */
    private String movieCode;

    /**
     * Database Column Remarks:
     *   电影标题
     */
    private String movieTitle;

    /**
     * Database Column Remarks:
     *   电影地区id 度量
     */
    private Integer movieAreaId;

    /**
     * Database Column Remarks:
     *   电影地区
     */
    private String movieArea;

    /**
     * Database Column Remarks:
     *   电影大小 度量 count
     */
    private Integer movieDate;

    /**
     * Database Column Remarks:
     *   电影类型id
     */
    private Integer movieTypeId;

    /**
     * Database Column Remarks:
     *   电影类型
     */
    private String movieType;

    /**
     * Database Column Remarks:
     *   电影情感id
     */
    private Integer movieEmotionId;

    /**
     * Database Column Remarks:
     *   电影情感
     */
    private String movieEmotion;

    /**
     * Database Column Remarks:
     *   电影时代id
     */
    private Integer movieAgeId;

    /**
     * Database Column Remarks:
     *   电影时代
     */
    private String movieAge;

    /**
     * Database Column Remarks:
     *   电影分数 度量 sum
     */
    private BigDecimal movieScore;

    /**
     */
    private static final long serialVersionUID = 1L;

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
        this.movieCode = movieCode == null ? null : movieCode.trim();
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle == null ? null : movieTitle.trim();
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
        this.movieArea = movieArea == null ? null : movieArea.trim();
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
        this.movieType = movieType == null ? null : movieType.trim();
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
        this.movieEmotion = movieEmotion == null ? null : movieEmotion.trim();
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
        this.movieAge = movieAge == null ? null : movieAge.trim();
    }

    public BigDecimal getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(BigDecimal movieScore) {
        this.movieScore = movieScore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", priId=").append(priId);
        sb.append(", movieCode=").append(movieCode);
        sb.append(", movieTitle=").append(movieTitle);
        sb.append(", movieAreaId=").append(movieAreaId);
        sb.append(", movieArea=").append(movieArea);
        sb.append(", movieDate=").append(movieDate);
        sb.append(", movieTypeId=").append(movieTypeId);
        sb.append(", movieType=").append(movieType);
        sb.append(", movieEmotionId=").append(movieEmotionId);
        sb.append(", movieEmotion=").append(movieEmotion);
        sb.append(", movieAgeId=").append(movieAgeId);
        sb.append(", movieAge=").append(movieAge);
        sb.append(", movieScore=").append(movieScore);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}