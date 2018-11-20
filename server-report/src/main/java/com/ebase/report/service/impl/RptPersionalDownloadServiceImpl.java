package com.ebase.report.service.impl;

import com.ebase.report.core.ZipUtils;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.ReportExportUtil;
import com.ebase.report.dao.RptPersionalDownloadMapper;
import com.ebase.report.model.RptPersionalDownload;
import com.ebase.report.service.RptPersionalDownloadService;
import com.ebase.report.vo.RptPersionalDownloadVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * dal Interface:RptPersionalDownload
 * @author 
 * @date 2018-11-1
 */
@Service
@Transactional
public class RptPersionalDownloadServiceImpl implements RptPersionalDownloadService {

    private static Logger LOG = LoggerFactory.getLogger(RptPersionalDownloadServiceImpl.class);

    @Autowired
    private RptPersionalDownloadMapper rptPersionalDownloadMapper;


    @Value("${report.download.file.path}")
    private String file_path;

	@Override
    public PageDTO<RptPersionalDownloadVO> findSelective(RptPersionalDownloadVO record){
		RptPersionalDownload model = BeanCopyUtil.copy(record, RptPersionalDownload.class);

		PageDTO<RptPersionalDownloadVO> page = new PageDTO<>(record.getPageNum(),record.getPageSize());
        Integer count = rptPersionalDownloadMapper.selectCount(record);
        page.setTotal(count);
        model.setStartRow(page.getStartRow());

		List<RptPersionalDownload> rptPersionalDownloads = rptPersionalDownloadMapper.select(model);
		List<RptPersionalDownloadVO> rptPersionalDownloadVOs = BeanCopyUtil.copyList(rptPersionalDownloads, RptPersionalDownloadVO.class);

        page.setResultData(rptPersionalDownloadVOs);
		return page;
    }

	@Override
    public RptPersionalDownloadVO getByPrimaryKey(Long key){
    	RptPersionalDownload rptPersionalDownload = rptPersionalDownloadMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(rptPersionalDownload, RptPersionalDownloadVO.class);
    }

	@Override
    public Integer insert(RptPersionalDownloadVO record){
    	RptPersionalDownload rptPersionalDownload = BeanCopyUtil.copy(record, RptPersionalDownload.class);
        return rptPersionalDownloadMapper.insert(rptPersionalDownload);

    }

	@Override
    public Integer insertSelective(RptPersionalDownloadVO record){
        RptPersionalDownload rptPersionalDownload = BeanCopyUtil.copy(record, RptPersionalDownload.class);
        return rptPersionalDownloadMapper.insertSelective(rptPersionalDownload);
    }
    
    @Override
    public Integer updateByPrimaryKey(RptPersionalDownloadVO record){
    	RptPersionalDownload rptPersionalDownload = BeanCopyUtil.copy(record, RptPersionalDownload.class);
        return rptPersionalDownloadMapper.updateByPrimaryKey(rptPersionalDownload);
    }

	@Override
    public Integer updateByPrimaryKeySelective(RptPersionalDownloadVO record){
        RptPersionalDownload rptPersionalDownload = BeanCopyUtil.copy(record, RptPersionalDownload.class);
        return rptPersionalDownloadMapper.updateByPrimaryKeySelective(rptPersionalDownload);
    }

	@Override
    public Integer deleteByPrimaryKey(Long key){
        return rptPersionalDownloadMapper.deleteByPrimaryKey(key);
    }

    @Override
    public Boolean downloadReportFile(String filePath) {

	    String path = file_path + "/" + filePath;

	    try{
            //解压一下
            List<String> paths = ZipUtils.decompressZip(path, file_path);

            //下载文件
            ZipUtils.downLoadZipFile(paths,filePath);
        }catch (Exception e){
	        e.printStackTrace();
        }

        return true;
    }


}