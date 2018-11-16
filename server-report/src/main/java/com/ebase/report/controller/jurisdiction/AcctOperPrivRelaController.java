package com.ebase.report.controller.jurisdiction;


import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.AcctOperPrivRelaService;
import com.ebase.report.vo.jurisdiction.AcctOperPrivRelaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统基础模块-  角色功能关联
 * @Auther: zhaoyuhang
 */
@RestController
@RequestMapping("/acctOperPrivRela")
public class AcctOperPrivRelaController {

    private static Logger LOG = LoggerFactory.getLogger(AcctOperPrivRelaController.class);

    @Autowired
    private AcctOperPrivRelaService acctOperPrivRelaService;


    /**
     * 系统功能管理 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/addAcctOperPrivRela")
    public JsonResponse<Integer> addAcctOperPrivRela(@RequestBody JsonRequest<AcctOperPrivRelaVO> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            Integer acctOperPrivRela = acctOperPrivRelaService.addAcctOperPrivRela(jsonRequest.getReqBody());
            jsonResponse.setRspBody(acctOperPrivRela);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * 系统功能管理 修改功能使用状态
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/delAcctOperPrivRela")
    public JsonResponse<Integer> delAcctOperPrivRela(@RequestBody JsonRequest<AcctOperPrivRelaVO> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            Integer acctOperPrivRela = acctOperPrivRelaService.delAcctOperPrivRela(jsonRequest.getReqBody());
            jsonResponse.setRspBody(acctOperPrivRela);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }
}
