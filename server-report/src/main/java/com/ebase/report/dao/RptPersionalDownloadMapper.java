package com.ebase.report.dao;

import com.ebase.report.model.RptPersionalDownload;
import com.ebase.report.vo.RptPersionalDownloadVO;

import java.util.List;

public interface RptPersionalDownloadMapper {
    int deleteByPrimaryKey(Long downloadId);

    int insert(RptPersionalDownload record);

    int insertSelective(RptPersionalDownload record);

    RptPersionalDownload selectByPrimaryKey(Long downloadId);

    int updateByPrimaryKeySelective(RptPersionalDownload record);

    int updateByPrimaryKey(RptPersionalDownload record);

    List<RptPersionalDownload> select(RptPersionalDownload record);

    Integer selectCount(RptPersionalDownloadVO record);

    int deleteByFilePath(String filePath);
}