package com.ebase.report.dao.jurisdiction;

import com.ebase.report.model.jurisdiction.AcctOperPrivRela;
import com.ebase.report.model.jurisdiction.FunctionManage;
import com.ebase.report.model.jurisdiction.RoleInfo;

public interface AcctOperPrivRelaMapper {
    int deleteByPrimaryKey(Long relaId);

    int deleteRoleId(Long roleId);

    int deleteFunctionId(Long functionId);

    int deleteFunctionIdAll(FunctionManage functionManage);

    int insert(AcctOperPrivRela record);

    int insertSelective(AcctOperPrivRela record);

    AcctOperPrivRela selectByPrimaryKey(Long relaId);

    int updateByPrimaryKeySelective(AcctOperPrivRela record);

    int updateByPrimaryKey(AcctOperPrivRela record);

    int insertCopy(RoleInfo record);
}