package com.ebase.report.service.impl;

import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.dao.RptPersonalSubjectTableMapper;
import com.ebase.report.model.RptPersonalSubjectTable;
import com.ebase.report.service.RptPersonalSubjectTableService;
import com.ebase.report.vo.RptPersonalSubjectTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:RptPersonalSubjectTable
 * @author 
 * @date 2018-10-25
 */
@Service
@Transactional
public class RptPersonalSubjectTableServiceImpl implements RptPersonalSubjectTableService{

    @Autowired
    private RptPersonalSubjectTableMapper rptPersonalSubjectTableMapper;

	@Override
    public List<RptPersonalSubjectTableVO> findAll() {
        List<RptPersonalSubjectTable> rptPersonalSubjectTables = rptPersonalSubjectTableMapper.selectAll();
        List<RptPersonalSubjectTableVO> rptPersonalSubjectTableVOs = BeanCopyUtil.copyList(rptPersonalSubjectTables, RptPersonalSubjectTableVO.class);
        return rptPersonalSubjectTableVOs;
    }

	@Override
    public List<RptPersonalSubjectTableVO> findSelective(RptPersonalSubjectTableVO record){
		RptPersonalSubjectTable model = BeanCopyUtil.copy(record, RptPersonalSubjectTable.class);
		List<RptPersonalSubjectTable> rptPersonalSubjectTables = rptPersonalSubjectTableMapper.select(model);
		List<RptPersonalSubjectTableVO> rptPersonalSubjectTableVOs = BeanCopyUtil.copyList(rptPersonalSubjectTables, RptPersonalSubjectTableVO.class);
		return rptPersonalSubjectTableVOs;
    }

	@Override
    public RptPersonalSubjectTableVO getByPrimaryKey(Long key){
    	RptPersonalSubjectTable rptPersonalSubjectTable = rptPersonalSubjectTableMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(rptPersonalSubjectTable, RptPersonalSubjectTableVO.class);
    }

	@Override
    public Integer insert(RptPersonalSubjectTableVO record){
    	RptPersonalSubjectTable rptPersonalSubjectTable = BeanCopyUtil.copy(record, RptPersonalSubjectTable.class);
        return rptPersonalSubjectTableMapper.insert(rptPersonalSubjectTable);

    }

	@Override
    public Integer insertSelective(RptPersonalSubjectTableVO record){
        RptPersonalSubjectTable rptPersonalSubjectTable = BeanCopyUtil.copy(record, RptPersonalSubjectTable.class);
        return rptPersonalSubjectTableMapper.insertSelective(rptPersonalSubjectTable);
    }
    
    @Override
    public Integer updateByPrimaryKey(RptPersonalSubjectTableVO record){
    	RptPersonalSubjectTable rptPersonalSubjectTable = BeanCopyUtil.copy(record, RptPersonalSubjectTable.class);
        return rptPersonalSubjectTableMapper.updateByPrimaryKey(rptPersonalSubjectTable);
    }

	@Override
    public Integer updateByPrimaryKeySelective(RptPersonalSubjectTableVO record){
        RptPersonalSubjectTable rptPersonalSubjectTable = BeanCopyUtil.copy(record, RptPersonalSubjectTable.class);
        return rptPersonalSubjectTableMapper.updateByPrimaryKeySelective(rptPersonalSubjectTable);
    }

	@Override
    public Integer deleteByPrimaryKey(Long key){
        return rptPersonalSubjectTableMapper.deleteByPrimaryKey(key);
    }
    
    /**
	 *  IN
	 *	<foreach collection="keys" open="(" close=")" item="key" separator=",">
	 *		{key}
	 *	</foreach>
	 */
    @Override
    public Integer deleteByPrimaryKeys(Set<Long> keys){
    	//return rptPersonalSubjectTableMapper.deleteByPrimaryKeys(keys);
        return null;
    }
    
    /*@Override
	public Integer keep(List<RptPersonalSubjectTableVO> rptPersonalSubjectTableVOs) {
		int result = 0;
//		Set<Long> keys = new HashSet<>();
		for (RptPersonalSubjectTableVO rptPersonalSubjectTableVO : rptPersonalSubjectTableVOs) {
			String opt = rptPersonalSubjectTableVO.getOpt();
			if (ParamType.DELETE.getMsg().equals(opt)) {
//				keys.add(rptPersonalSubjectTableVO.getId());
				int i = deleteByPrimaryKey(rptPersonalSubjectTableVO.getId());
				if (i > 0) {
					result++;
				}
			} else if (ParamType.UPDATE.getMsg().equals(opt)) {
				int i = updateByPrimaryKeySelective(rptPersonalSubjectTableVO);
				if (i > 0) {
					result++;
				}
			} else if (ParamType.INSERT.getMsg().equals(opt)) {
				int i = insertSelective(rptPersonalSubjectTableVO);
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