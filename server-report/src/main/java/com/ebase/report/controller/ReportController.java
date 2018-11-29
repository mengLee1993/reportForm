package com.ebase.report.controller;

import com.ebase.report.common.FileTypeEnum;
import com.ebase.report.core.HTTPUtil;
import com.ebase.report.core.ZipUtils;
import com.ebase.report.core.db.handler.ReportHandler;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.core.utils.ReportExportUtil;
import com.ebase.report.core.utils.excel.ExportExcelUtils;
import com.ebase.report.cube.CubeTree;
import com.ebase.report.cube.charts.HighCharts;
import com.ebase.report.model.*;
import com.ebase.report.model.dynamic.*;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.RoleInfo;
import com.ebase.report.service.*;
import com.ebase.report.service.jurisdiction.AcctService;
import com.ebase.report.service.jurisdiction.RoleInfoService;
import com.ebase.report.vo.RptPersionalDownloadVO;
import com.ebase.report.vo.RptPersonalAnalysisVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    private static Logger LOG = LoggerFactory.getLogger(ReportController.class);

    @Resource
    private ReportHandler reportHandler;

    @Value("${juri.type}")
    private String type;

    @Value("${juri.auth.ip}")
    private String authIp;

    @Value("${report.download.file.path}")
    private String download;

    @Autowired
    private ReportService reportService;

    @Autowired
    private RptAnalyseLogService rptAnalyseLogService;

    //指标
    @Autowired
    private RptMeasuresService rptMeasuresService;

    //报表
    @Autowired
    private RptPersonalAnalysisService rptPersonalAnalysisService;

    //个人用户下
    @Autowired
    private RptPersionalDownloadService rptPersionalDownloadService;

    //主题表
    @Autowired
    private RptPersonalSubjectService rptPersonalSubjectService;

    //账号
    @Autowired
    private AcctService acctService;

    //角色
    @Autowired
    private RoleInfoService roleInfoService;


    @Value("${report.download.file.path}")
    private String file_path;

    /**
     * 报表核心接口  动态报表
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/reportCore")
    private JsonResponse<ReportResp> reportCore(@RequestBody JsonRequest<ReportDatasource> jsonRequest) {
        JsonResponse<ReportResp> jsonResponse = new JsonResponse<ReportResp>();
        ReportResp reportResp = new ReportResp();
        jsonResponse.setRspBody(reportResp);
        try{
            ReportDatasource reportDatasource = jsonRequest.getReqBody();
            //生成cubetree
            CubeTree cubeTree = reportService.reportCore(reportDatasource.getReportDynamicParam());

            cubeTree = reportHandler.report(reportDatasource, cubeTree);

            // 数据json处理
            cubeTree.toJson();

            reportResp.setAxisxData(cubeTree.getAxesxData());
            reportResp.setAxisyData(cubeTree.getAxesyData());
            reportResp.setCellList(cubeTree.getCellList());
            reportResp.setChartOptions(HighCharts.getDefChartOption(cubeTree));

        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);

        }

        return jsonResponse;
    }

    /**
     * 报表列表
     * @return
     */
    @RequestMapping("/reportCoreDetail")
    public JsonResponse<PageDTO<ReportRespDetail>> reportCoreDetail(@RequestBody JsonRequest<ReportDatasource> jsonRequest){
        JsonResponse<PageDTO<ReportRespDetail>> jsonResponse = new JsonResponse<PageDTO<ReportRespDetail>>();
        try{


            ReportDatasource reportDatasource = jsonRequest.getReqBody();

            //初始化cubeThree
            CubeTree cubeTree = reportService.reportCore(reportDatasource.getReportDynamicParam());


            PageDTO<ReportRespDetail> pageDTO = reportHandler.reportCoreDetail(reportDatasource, cubeTree);
            jsonResponse.setRspBody(pageDTO);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }



    /**
     * 报表核心接口  动态报表 导出类
     * @param name
     * @return
     *
     *
     */
    @RequestMapping("/reportCoreExcel")
    private JsonResponse<ReportResp> reportCoreExcel(@RequestParam("name")String name) {
        ReportDatasource reportDatasource = JsonUtil.fromJson(name, ReportDatasource.class);

        JsonResponse<ReportResp> jsonResponse = new JsonResponse<ReportResp>();
        try{

            ReportDynamicParam reportDynamicParam = reportDatasource.getReportDynamicParam();

            //生成cubetree
            CubeTree cubeTree = reportService.reportCore(reportDynamicParam);

            if(CollectionUtils.isEmpty(reportDynamicParam.getColumn())){ //列没值
                RptPersionalDownloadVO rptPersionalDownloadVO = new RptPersionalDownloadVO();

                //一万条查询一次,拼装在内存里，每10w条一个文件里，
                ReportRespDetail reportCoreDetailExcels = reportHandler.reportCoreDetailExcel(reportDatasource,cubeTree);

                //生成workbook
                Workbook workbook = ExportExcelUtils.createExcelWorkBook("数据报表","数据报表","数据报表",reportCoreDetailExcels.getHeaders(),reportCoreDetailExcels.getDataList());

                ReportExportUtil.OutPutWorkBookResponse("数据报表",workbook);

            }else{
                cubeTree = reportHandler.report(reportDatasource, cubeTree);

                // 数据json处理
                cubeTree.toJson();

                Workbook workbook = ReportExportUtil.createReportWorkbook(cubeTree);

                ReportExportUtil.OutPutWorkBookResponse("报表",workbook);
            }
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);

        }

        return jsonResponse;
    }



    /**
     * 初始化页面接口  可能也做报表的回显
     * @param jsonRequest  主题id /一期 都是单表的id   ！！！没参数呗
     * @return 数据库返回对象
     */
    @RequestMapping("/getReportForm")
    public JsonResponse<Map<String,List<ReportEchoBody>>> getReportForm(@RequestBody JsonRequest<RptPersonalAnalysis> jsonRequest){
        JsonResponse<Map<String,List<ReportEchoBody>>> jsonResponse = new JsonResponse<>();
        try{
            //当前操作人
//            Long createdBy = AssertContext.getAcctId();
            String createdBy = AssertContext.getReAcctId();
            if(createdBy == null){
                createdBy = "1";
            }
            Map<String,List<ReportEchoBody>> result = new HashMap<>(1);
            List<ReportEchoBody> reportEchoBody = new ArrayList<>();
            if(jsonRequest.getReqBody() != null && jsonRequest.getReqBody().getPersonalAnalysisId() != null){
                //传了报表ID 设置报表数据
                Long personalAnalysisId = jsonRequest.getReqBody().getPersonalAnalysisId();

                reportEchoBody = rptPersonalAnalysisService.getPersonalSubject(personalAnalysisId);
            }else if(jsonRequest.getReqBody() != null && jsonRequest.getReqBody().getPersonalSubjectId() != null){
                Long personalSubjectId = jsonRequest.getReqBody().getPersonalSubjectId();

                reportEchoBody = reportService.getThemeDataSourceByPersonalSubjectId(personalSubjectId);
            }else{
                reportEchoBody = reportService.getThemeDataSource(createdBy);
            }


            result.put("resultData",reportEchoBody);
            jsonResponse.setRspBody(result);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }


    /**
     * 保存自定义指标
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/addRptMeasures")
    public JsonResponse<Long> addRptMeasures(@RequestBody JsonRequest<RptMeasures> jsonRequest){
        JsonResponse<Long> jsonResponse = new JsonResponse<>();

        try{
            RptMeasures reqBody = jsonRequest.getReqBody();

            String acctId = AssertContext.getReAcctId();
            if(acctId == null){
                acctId = "1";
            }
            reqBody.setCreatedBy(acctId);

            //根据类型 和 主题ID 和 filed id查询表
            Integer count = rptMeasuresService.getRptMeasuresByService(reqBody);
            //如果重复
            if(count > 0){
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                jsonResponse.setRetDesc("指标名称重复");
                return jsonResponse;
            }

            Long boo = reportService.addRptMeasures(reqBody);

            jsonResponse.setRspBody(boo);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;

    }


    /**
     * 保存报表
     */
    @RequestMapping("/addCustomReport")
    public JsonResponse<Long> addCustomReport(@RequestBody JsonRequest<RptPersonalAnalysis> jsonRequest){

        JsonResponse<Long> jsonResponse = new JsonResponse<>();

        try{
            RptPersonalAnalysis reqBody = jsonRequest.getReqBody();

            String acctId = AssertContext.getReAcctId();
            if(acctId == null){
                acctId = "1";
            }
            reqBody.setUserId(acctId);
            reqBody.setCreatedBy(acctId);

            Integer count = rptPersonalAnalysisService.getReportByName(reqBody.getReportName(),acctId);

            if(count > 0){
                jsonResponse.setRetCode("500");
                jsonResponse.setRetDesc("报表名称重复");
                return jsonResponse;
            }

            Long boo = reportService.addCustomReport(reqBody);

            jsonResponse.setRspBody(boo);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     * 查询报表 信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/getCustomReport")
    public JsonResponse<RptPersonalAnalysis> getCustomReport(@RequestBody JsonRequest<RptPersonalAnalysis> jsonRequest){
        JsonResponse<RptPersonalAnalysis> jsonResponse = new JsonResponse<>();

        try{
            Long personalAnalysisId = jsonRequest.getReqBody().getPersonalAnalysisId(); //自定义分析报表id

            RptPersonalAnalysis rptPersonalAnalysis = reportService.getCustomReport(personalAnalysisId);

            jsonResponse.setRspBody(rptPersonalAnalysis);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    /**
     * 查询报表详细维度  必须点过保存之后 去掉groupby 所有聚合的函数 都去掉
     */
    @RequestMapping("/reportFromDetail")
    public JsonResponse<ReportDetailBody> reportFromDetail(@RequestBody JsonRequest<ReportDatasource> jsonRequest){
        JsonResponse<ReportDetailBody> jsonResponse = new JsonResponse<ReportDetailBody>();

        try{

            //直接返回
            Callable<Integer> callable = new Callable<Integer>() {
                @Override
                public Integer call() {
                   try{
                       //自定义报表id
//                       Long personalAnalysisId = jsonRequest.getReqBody().getPersonalAnalysisId();

//                       personalAnalysisId = 61l;
//                       RptPersonalAnalysis rptPersonalAnalysis = reportService.getCustomReport(personalAnalysisId);

//                       String configJson = rptPersonalAnalysis.getConfigJson();

                       if(jsonRequest != null){
                           ReportDatasource reportDatasource = jsonRequest.getReqBody();
//
                           //生成cubetree
                           CubeTree cubeTree = reportService.reportCore(reportDatasource.getReportDynamicParam());

                           RptPersionalDownloadVO rptPersionalDownloadVO = new RptPersionalDownloadVO();

                           //一万条查询一次,拼装在内存里，每10w条一个文件里，
                           List<ReportRespDetail> reportCoreDetailExcels = reportHandler.reportCoreDetailExcel(reportDatasource, cubeTree,rptPersionalDownloadVO);

                           int i = 0;
                           List<File> files = getFiles(reportCoreDetailExcels);

                           generateZip(rptPersionalDownloadVO, files);
                       }
                   }catch (Exception e){
                       LOG.error("异步生成文件失败 = {}",e);
                   }
                    return 1;
                }


            };
            FutureTask<Integer> futureTask = new FutureTask<>(callable);
            new Thread(futureTask).start();
//            Integer integer = futureTask.get();   //获取返回 会取消异步

        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    private List<File> getFiles(List<ReportRespDetail> reportCoreDetailExcels) throws IOException {
        int size = reportCoreDetailExcels.size();
        List<File> files = new ArrayList<>(size);
        for(ReportRespDetail x:reportCoreDetailExcels){
            //生成workbook
            Workbook workbook = ExportExcelUtils.createExcelWorkBook("数据报表","数据报表","数据报表",x.getHeaders(),x.getDataList());

            String fileName = System.getProperty("java.io.tmpdir")+"/" + new Date().getTime() + size + ".xls";

            File file = new File(fileName);
            FileOutputStream fout = new FileOutputStream(file);
            workbook.write(fout);

            files.add(file);
        }
        ;
        return files;
    }


    private void generateZip(RptPersionalDownloadVO rptPersionalDownloadVO, List<File> files) throws Exception {
        String acctId =  AssertContext.getReAcctId();
        String path = file_path ; // 配置
        String fileName = new Date().getTime() + files.size() + ".zip";
        FileOutputStream fos2 = new FileOutputStream(new File(path + "/" + fileName));
        ZipUtils.generateZip(fos2, files);

        //清空file
        for(File file:files){
            file.delete();
        }

        //插入
        rptPersionalDownloadVO.setUserId(acctId);
//                           rptPersionalDownloadVO.setDownloadSql();
        rptPersionalDownloadVO.setFileName(fileName);
        rptPersionalDownloadVO.setFileType(FileTypeEnum.EXCEL.getName());
        rptPersionalDownloadVO.setFilePath(fileName);
        rptPersionalDownloadVO.setFileDesc("描述 哦");
        rptPersionalDownloadVO.setCrateTime(new Date());
        rptPersionalDownloadService.insertSelective(rptPersionalDownloadVO);
    }

    /**
     *  获得最新执行的sql
     */
    @RequestMapping("/getReportSql")
    public JsonResponse<RptAnalyseLog> getReportSql(){
        JsonResponse<RptAnalyseLog> jsonResponse = new JsonResponse<>();

        try{
            //当前操作人最新一次的sql
            RptAnalyseLog rptAnalyseLog = rptAnalyseLogService.getReportSql();

            jsonResponse.setRspBody(rptAnalyseLog);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    /**
     * 所有的 指标字段
     */
    @RequestMapping("/getRptDataField")
    public JsonResponse<List<RptDataField>> getRptDataField(@RequestBody JsonRequest<RptPersonalSubject> jsonRequest){
        JsonResponse<List<RptDataField>> jsonResponse = new JsonResponse<>();

        try{
            Long personalSubjectId = jsonRequest.getReqBody().getPersonalSubjectId();

            List<RptDataField> rptDataFields = reportService.getRptDataField(personalSubjectId);

            jsonResponse.setRspBody(rptDataFields);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     * 当前主题下已经添加过 SUM，COUNT 的指标
     * @param jsonRequest
     */
    @RequestMapping("/getRptMeasuresSystem")
    public JsonResponse<Map<String,List<RptMeasures>>> getRptMeasuresSystem(@RequestBody JsonRequest<RptPersonalSubject> jsonRequest){
        JsonResponse<Map<String,List<RptMeasures>>> jsonResponse = new JsonResponse<>();

        try{
            Long personalSubjectId = jsonRequest.getReqBody().getPersonalSubjectId();

            List<RptMeasures> rptDataFields = reportService.getRptMeasuresSystem(personalSubjectId);

            Map<String,List<RptMeasures>> result = new HashMap<>(1);
            result.put("resultData",rptDataFields);
            jsonResponse.setRspBody(result);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    /**
     * 删除自定义指标
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/delRptMeasures")
    public JsonResponse<Integer> delRptMeasures(@RequestBody JsonRequest<RptMeasures> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();

        try{
            RptMeasures reqBody = jsonRequest.getReqBody();

            int boo = rptMeasuresService.delRptMeasures(reqBody);

            jsonResponse.setRspBody(boo);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;

    }


    /**
     *  获得 报表列表数据
     */
    @RequestMapping("/listReportForm")
    public JsonResponse<PageDTO<ReportTable>> listReportForm(@RequestBody JsonRequest<ReportTable> jsonRequest){
        JsonResponse<PageDTO<ReportTable>> jsonResponse = new JsonResponse<>();

        try{
            ReportTable reqBody = jsonRequest.getReqBody();

            PageDTO<ReportTable> pageDTO = rptPersonalAnalysisService.listReportForm(reqBody);

            jsonResponse.setRspBody(pageDTO);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }


    //   -----  分享功能


    //获得所有的角色




    /**
     * 获得所有的 用户 支持用户名成like 搜索
     * @param jsonRequest acctTitle账户名称
     * @return
     */
    @RequestMapping("/listShareReport")
    public JsonResponse<PageDTO<AcctInfo>> listShareReport(@RequestBody JsonRequest<AcctInfo> jsonRequest){

        JsonResponse<PageDTO<AcctInfo>> jsonResponse = new JsonResponse<>();

        try{
            AcctInfo acctInfo = jsonRequest.getReqBody();
            Long sysId = acctInfo.getSysId();
            if(type.equals("core")){
                PageDTO<AcctInfo> pageDTO = acctService.listShareReport(acctInfo);
                List<AcctInfo> resultData = pageDTO.getResultData();

                resultData.forEach(x -> {
                    x.setType((byte)0);
                    if(sysId != null){

                        //用loginid和报表id差
                        RptPersonalAnalysis rptPersonalAnalysis = rptPersonalAnalysisService.getByUserId(x.getAcctId().toString(),sysId);
                        if(rptPersonalAnalysis != null){
                            x.setType((byte)1);
                        }
                    }
                });



                jsonResponse.setRspBody(pageDTO);
            }else if (type.equals("report")) {

                //根据当前登陆人和报表id查询userid
                List<String> userIds = rptPersonalAnalysisService.getUserIdById(sysId);
                Byte type = acctInfo.getType();//状态判断要不要过滤自己
                if(type != null && type == (byte)1){
                    userIds.add(AssertContext.getReAcctId());
                }

                if(acctInfo.getAcctTitle()==null){
                    acctInfo.setAcctTitle("");
                }
                String url = "http://" + authIp + "/auth/ac/usage-survey/getUsageSurveyUsersByPage.action?_dc=1542681842909&page=" +
                        acctInfo.getPageNum() + "&limit=" + acctInfo.getPageSize()
                        + "&qm.projectId=ZDYBB&qm.userNameCn="+acctInfo.getAcctTitle();
                //String url="http://192.168.1.100:7070/auth/ac/login/dologin.action?username=liq0416&password=admin123";
                HashMap map = new HashMap();
                JSONObject json = JSON.parseObject(HTTPUtil.postParams(url, AssertContext.getToken(), map));
                System.out.println(json);
                String success = json.getString("success");
                if (success.equals("true") || success == "true") {
                    List<AcctInfo> acctInfos = new ArrayList<>();
                    PageDTO<AcctInfo> pages = new PageDTO();
                    if (JSON.parseObject(json.getString("items")).get("records") != null) {
                        JSONArray jsonObject1 = JSON.parseObject(json.getString("items")).getJSONArray("records");

                        for (int i = 0; i < jsonObject1.size(); i++) {
                            String loginId = JSON.parseObject(jsonObject1.getString(i)).getString("loginId");
                            AcctInfo acctInfo1=new AcctInfo();

                            //排除 已经添加过的和当前登陆人
                            if(userIds.contains(loginId)){
//                                continue; //不排除给他提示
                                acctInfo1.setTips("此账号不能分享哦！");
                            }



                            acctInfo1.setReAcctId(loginId);
                            acctInfo1.setName(JSON.parseObject(jsonObject1.getString(i)).getString("userNameCn"));
                            acctInfo1.setAcctTitle(JSON.parseObject(jsonObject1.getString(i)).getString("loginId"));
                            acctInfo1.setOrgId(JSON.parseObject(jsonObject1.getString(i)).getString("orgId"));

                            acctInfo1.setType((byte)0);
                            if(sysId != null){

                                //用loginid和报表id差
                                RptPersonalAnalysis rptPersonalAnalysis = rptPersonalAnalysisService.getByUserId(loginId,sysId);
                                if(rptPersonalAnalysis != null){
                                    acctInfo1.setType((byte)1);
                                }
                            }

                            acctInfos.add(acctInfo1);

                        }
                    }
                    pages.setResultData(acctInfos);
                    pages.setPageSize(acctInfo.getPageSize());
                    pages.setPageNum(acctInfo.getPageNum());
                    pages.setTotal(Integer.parseInt(JSON.parseObject(json.getString("items")).getString("total")));



                    jsonResponse.setRspBody(pages);
                }
            }
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }


    /**
     *  自定义报表  -分享数据
     */
    @RequestMapping("/addShareReport")
    public JsonResponse<Boolean>  addShareReport(@RequestBody JsonRequest<ReportShareBody> jsonRequest){
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();

        try{
            ReportShareBody reportShareBody = jsonRequest.getReqBody();

            //加校验，如果被分享 对这个人有权限才能分享 否则不能分享
            //根据类型 和ID 差寻主题表  如果存在 继续如果不存在 就提示

            Byte type = reportShareBody.getType();
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("type",type);
            for(String x:reportShareBody.getList()){
                tmp.put("id",x);
                Integer count = rptPersonalSubjectService.getSubjectByTypeId(tmp);
                if(count == 0){
                    //不存在没有权限
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                    if(type == 1){
                        //账号
                        AcctInfo acctInfo = acctService.getAcctById(x);
                        if(acctInfo != null){
                            jsonResponse.setRetDesc("账号[" + acctInfo.getAcctTitle() + "]没有权限");
                        }else{
                            jsonResponse.setRetDesc("账号[" + x + "]没有权限");
                        }

                    }else{
                        //角色
                        RoleInfo roleInfo = roleInfoService.getRoleById(x);
                        if(roleInfo != null){
                            jsonResponse.setRetDesc("角色[" + roleInfo.getRoleTitle() + "]没有权限");
                        }else{
                            jsonResponse.setRetDesc("角色[" + x + "]没有权限");
                        }

                    }

                    return jsonResponse;
                }
            };

            Boolean aBoolean = rptPersonalAnalysisService.addShareReport(reportShareBody);

            jsonResponse.setRspBody(aBoolean);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;

    }







}

