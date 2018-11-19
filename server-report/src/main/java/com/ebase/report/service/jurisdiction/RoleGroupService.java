package com.ebase.report.service.jurisdiction;


import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.vo.jurisdiction.RoleGroupVO;

import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
public interface RoleGroupService {

    PageInfo<RoleGroupVO> roleGroupList(RoleGroupVO jsonRequest);

    List<RoleGroupVO> findAll(RoleGroupVO jsonRequest);

    RoleGroupVO keepRoleGroup(RoleGroupVO jsonRequest);

    String verificationDeleteRoleGroup(RoleGroupVO jsonRequest);
}
