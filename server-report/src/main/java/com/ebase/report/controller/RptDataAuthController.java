package com.ebase.report.controller;


import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.RptDataAuth;
import com.ebase.report.model.RptDatasource;
import com.ebase.report.service.RptDataAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//数据授权
@RestController
@RequestMapping("/dataAuth")
public class RptDataAuthController {

    private static Logger LOG = LoggerFactory.getLogger(RptDataAuthController.class);

    @Autowired
    private RptDataAuthService rptDataAuthService;


    //获得类目树
    @RequestMapping("/dataAuthTree")
    public JsonResponse<List<RptDatasource>> dataAuthTree(@RequestBody JsonRequest<AcctInfo> jsonRequest){
        JsonResponse<List<RptDatasource>> jsonResponse = new JsonResponse<>();

        try{

            AcctInfo reqBody = jsonRequest.getReqBody();

            List<RptDatasource> list = rptDataAuthService.dataAuthTree(reqBody);

            jsonResponse.setRspBody(list);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }


    //系统授权
    @RequestMapping("/keepDataAuth")
    public JsonResponse<Boolean> keepDataAuth(@RequestBody JsonRequest<RptDataAuth> jsonRequest){
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();

        try{

            RptDataAuth rptDataAuth = jsonRequest.getReqBody();

            Boolean aBoolean = rptDataAuthService.keepDataAuth(rptDataAuth);

            jsonResponse.setRspBody(aBoolean);
        }catch (Exception e){
            LOG.error("error = {}",e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }


}
