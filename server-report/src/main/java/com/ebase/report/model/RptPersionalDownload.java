package com.ebase.report.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class RptPersionalDownload implements Serializable {
    /**
     * Database Column Remarks:
     *   下载记录ID
     */
    private Long downloadId;

    /**
     * Database Column Remarks:
     *   用户ID
     */
    private String userId;

    /**
     * Database Column Remarks:
     *   下载数据sql
     */
    private String downloadSql;

    /**
     * Database Column Remarks:
     *   文件名称
     */
    private String fileName;

    /**
     * Database Column Remarks:
     *   文件类型
     */
    private String fileType;

    /**
     * Database Column Remarks:
     *   文件路径
     */
    private String filePath;

    /**
     * Database Column Remarks:
     *   文件描述
     */
    private String fileDesc;

    /**
     * Database Column Remarks:
     *   下载时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date downloadTime;

    /**
     * 生成时间
     */
    private Date crateTime;

    private int pageSize = 10;

    private Integer startRow;
    /**
     */
    private static final long serialVersionUID = 1L;



    public Long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(Long downloadId) {
        this.downloadId = downloadId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDownloadSql() {
        return downloadSql;
    }

    public void setDownloadSql(String downloadSql) {
        this.downloadSql = downloadSql == null ? null : downloadSql.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc == null ? null : fileDesc.trim();
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", downloadId=").append(downloadId);
        sb.append(", userId=").append(userId);
        sb.append(", downloadSql=").append(downloadSql);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileType=").append(fileType);
        sb.append(", filePath=").append(filePath);
        sb.append(", fileDesc=").append(fileDesc);
        sb.append(", downloadTime=").append(downloadTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }


    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }
}