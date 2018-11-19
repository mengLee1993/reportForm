package com.ebase.report.service.jurisdiction;

import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.model.jurisdiction.RoleInfo;
import com.ebase.report.vo.jurisdiction.RoleInfoVO;

import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
public interface RoleInfoService {

    PageInfo<RoleInfoVO> roleInfoList(RoleInfoVO jsonRequest);

    List<RoleInfoVO> roleInfoAll(RoleInfoVO jsonRequest);

    List<RoleInfoVO> orgQuoteRoleInfo(RoleInfoVO jsonRequest);

    List<RoleInfoVO> roleInfoAllLike(RoleInfoVO jsonRequest);

    List<RoleInfoVO> roleRoleAcctInfo(RoleInfoVO jsonRequest);

    List<RoleInfoVO> roleInfoTree(RoleInfoVO jsonRequest);

    List<RoleInfoVO> verificationIsTtitleRole(RoleInfoVO jsonRequest);

    RoleInfoVO keepRoleInfo(RoleInfoVO jsonRequest);

    RoleInfoVO saveCopyRole(RoleInfoVO jsonRequest);

    String verificationDeleteRoelInfo(RoleInfoVO jsonRequest);

    String verQuoteRoleTitle(RoleInfoVO jsonRequest);

    String verQuoteRoleIds(RoleInfoVO jsonRequest);

    List<RoleInfoVO> queryForList(RoleInfoVO vo);

    RoleInfo getRoleById(Long x);
}
