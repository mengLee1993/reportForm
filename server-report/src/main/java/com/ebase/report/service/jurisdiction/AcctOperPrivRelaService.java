package com.ebase.report.service.jurisdiction;



import com.ebase.report.vo.jurisdiction.AcctOperPrivRelaVO;

/**
 * @Auther: zhaoyuhang
 */
public interface AcctOperPrivRelaService {

    Integer delAcctOperPrivRela(AcctOperPrivRelaVO jsonRequest);

    Integer addAcctOperPrivRela(AcctOperPrivRelaVO jsonRequest);
}
