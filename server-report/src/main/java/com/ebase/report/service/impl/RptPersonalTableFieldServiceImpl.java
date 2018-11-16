package com.ebase.report.service.impl;

import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.dao.RptPersonalTableFieldMapper;
import com.ebase.report.model.RptPersonalTableField;
import com.ebase.report.service.RptPersonalTableFieldService;
import com.ebase.report.vo.RptPersonalTableFieldVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:RptPersonalTableField
 * @author 
 * @date 2018-10-25
 */
@Service
@Transactional
public class RptPersonalTableFieldServiceImpl implements RptPersonalTableFieldService {

    @Autowired
    private RptPersonalTableFieldMapper rptPersonalTableFieldMapper;

	@Override
    public List<RptPersonalTableFieldVO> findAll() {
        List<RptPersonalTableField> rptPersonalTableFields = rptPersonalTableFieldMapper.selectAll();
        List<RptPersonalTableFieldVO> rptPersonalTableFieldVOs = BeanCopyUtil.copyList(rptPersonalTableFields, RptPersonalTableFieldVO.class);
        return rptPersonalTableFieldVOs;
    }

	@Override
    public List<RptPersonalTableFieldVO> findSelective(RptPersonalTableFieldVO record){
		RptPersonalTableField model = BeanCopyUtil.copy(record, RptPersonalTableField.class);
		List<RptPersonalTableField> rptPersonalTableFields = rptPersonalTableFieldMapper.select(model);
		List<RptPersonalTableFieldVO> rptPersonalTableFieldVOs = BeanCopyUtil.copyList(rptPersonalTableFields, RptPersonalTableFieldVO.class);
		return rptPersonalTableFieldVOs;
    }

	@Override
    public RptPersonalTableFieldVO getByPrimaryKey(Long key){
    	RptPersonalTableField rptPersonalTableField = rptPersonalTableFieldMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(rptPersonalTableField, RptPersonalTableFieldVO.class);
    }

	@Override
    public Integer insert(RptPersonalTableFieldVO record){
    	RptPersonalTableField rptPersonalTableField = BeanCopyUtil.copy(record, RptPersonalTableField.class);
        return rptPersonalTableFieldMapper.insert(rptPersonalTableField);

    }

	@Override
    public Integer insertSelective(RptPersonalTableFieldVO record){
        RptPersonalTableField rptPersonalTableField = BeanCopyUtil.copy(record, RptPersonalTableField.class);
        return rptPersonalTableFieldMapper.insertSelective(rptPersonalTableField);
    }
    
    @Override
    public Integer updateByPrimaryKey(RptPersonalTableFieldVO record){
    	RptPersonalTableField rptPersonalTableField = BeanCopyUtil.copy(record, RptPersonalTableField.class);
        return rptPersonalTableFieldMapper.updateByPrimaryKey(rptPersonalTableField);
    }

	@Override
    public Integer updateByPrimaryKeySelective(RptPersonalTableFieldVO record){
        RptPersonalTableField rptPersonalTableField = BeanCopyUtil.copy(record, RptPersonalTableField.class);
        return rptPersonalTableFieldMapper.updateByPrimaryKeySelective(rptPersonalTableField);
    }

	@Override
    public Integer deleteByPrimaryKey(Long key){
        return rptPersonalTableFieldMapper.deleteByPrimaryKey(key);
    }
    
    /**
	 *  IN
	 *	<foreach collection="keys" open="(" close=")" item="key" separator=",">
	 *		{key}
	 *	</foreach>
	 */
    @Override
    public Integer deleteByPrimaryKeys(Set<Long> keys){
    	//return rptPersonalTableFieldMapper.deleteByPrimaryKeys(keys);
        return null;
    }
  /*
    @Override
	public Integer keep(List<RptPersonalTableFieldVO> rptPersonalTableFieldVOs) {
		int result = 0;
//		Set<Long> keys = new HashSet<>();
		for (RptPersonalTableFieldVO rptPersonalTableFieldVO : rptPersonalTableFieldVOs) {
			String opt = rptPersonalTableFieldVO.getOpt();
			if (ParamType.DELETE.getMsg().equals(opt)) {
//				keys.add(rptPersonalTableFieldVO.getId());
				int i = deleteByPrimaryKey(rptPersonalTableFieldVO.getId());
				if (i > 0) {
					result++;
				}
			} else if (ParamType.UPDATE.getMsg().equals(opt)) {
				int i = updateByPrimaryKeySelective(rptPersonalTableFieldVO);
				if (i > 0) {
					result++;
				}
			} else if (ParamType.INSERT.getMsg().equals(opt)) {
				int i = insertSelective(rptPersonalTableFieldVO);
				if (i > 0) {
					result++;
				}
			}
		}
//		if(!keys.isEmpty())
//			result = result + deleteByPrimaryKeys(keys);
		return result;
	}*/
}