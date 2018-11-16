package com.ebase.report.service.impl;

import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.dao.RptPersonalSubjectTableReaMapper;
import com.ebase.report.model.RptPersonalSubjectTableRea;
import com.ebase.report.service.RptPersonalSubjectTableReaService;
import com.ebase.report.vo.RptPersonalSubjectTableReaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:RptPersonalSubjectTableRea
 * @author 
 * @date 2018-10-25
 */
@Service
@Transactional
public class RptPersonalSubjectTableReaServiceImpl implements RptPersonalSubjectTableReaService {

    @Autowired
    private RptPersonalSubjectTableReaMapper rptPersonalSubjectTableReaMapper;

	@Override
    public List<RptPersonalSubjectTableReaVO> findAll() {
        List<RptPersonalSubjectTableRea> rptPersonalSubjectTableReas = rptPersonalSubjectTableReaMapper.selectAll();
        List<RptPersonalSubjectTableReaVO> rptPersonalSubjectTableReaVOs = BeanCopyUtil.copyList(rptPersonalSubjectTableReas, RptPersonalSubjectTableReaVO.class);
        return rptPersonalSubjectTableReaVOs;
    }

	@Override
    public List<RptPersonalSubjectTableReaVO> findSelective(RptPersonalSubjectTableReaVO record){
		RptPersonalSubjectTableRea model = BeanCopyUtil.copy(record, RptPersonalSubjectTableRea.class);
		List<RptPersonalSubjectTableRea> rptPersonalSubjectTableReas = rptPersonalSubjectTableReaMapper.select(model);
		List<RptPersonalSubjectTableReaVO> rptPersonalSubjectTableReaVOs = BeanCopyUtil.copyList(rptPersonalSubjectTableReas, RptPersonalSubjectTableReaVO.class);
		return rptPersonalSubjectTableReaVOs;
    }

	@Override
    public RptPersonalSubjectTableReaVO getByPrimaryKey(Long key){
    	RptPersonalSubjectTableRea rptPersonalSubjectTableRea = rptPersonalSubjectTableReaMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(rptPersonalSubjectTableRea, RptPersonalSubjectTableReaVO.class);
    }

	@Override
    public Integer insert(RptPersonalSubjectTableReaVO record){
    	RptPersonalSubjectTableRea rptPersonalSubjectTableRea = BeanCopyUtil.copy(record, RptPersonalSubjectTableRea.class);
        return rptPersonalSubjectTableReaMapper.insert(rptPersonalSubjectTableRea);

    }

	@Override
    public Integer insertSelective(RptPersonalSubjectTableReaVO record){
        RptPersonalSubjectTableRea rptPersonalSubjectTableRea = BeanCopyUtil.copy(record, RptPersonalSubjectTableRea.class);
        return rptPersonalSubjectTableReaMapper.insertSelective(rptPersonalSubjectTableRea);
    }
    
    @Override
    public Integer updateByPrimaryKey(RptPersonalSubjectTableReaVO record){
    	RptPersonalSubjectTableRea rptPersonalSubjectTableRea = BeanCopyUtil.copy(record, RptPersonalSubjectTableRea.class);
        return rptPersonalSubjectTableReaMapper.updateByPrimaryKey(rptPersonalSubjectTableRea);
    }

	@Override
    public Integer updateByPrimaryKeySelective(RptPersonalSubjectTableReaVO record){
        RptPersonalSubjectTableRea rptPersonalSubjectTableRea = BeanCopyUtil.copy(record, RptPersonalSubjectTableRea.class);
        return rptPersonalSubjectTableReaMapper.updateByPrimaryKeySelective(rptPersonalSubjectTableRea);
    }

	@Override
    public Integer deleteByPrimaryKey(Long key){
        return rptPersonalSubjectTableReaMapper.deleteByPrimaryKey(key);
    }
    
    /**
	 *  IN
	 *	<foreach collection="keys" open="(" close=")" item="key" separator=",">
	 *		{key}
	 *	</foreach>
	 */
    @Override
    public Integer deleteByPrimaryKeys(Set<Long> keys){
    	//return rptPersonalSubjectTableReaMapper.deleteByPrimaryKeys(keys);
        return null;
    }
    
    /*@Override
	public Integer keep(List<RptPersonalSubjectTableReaVO> rptPersonalSubjectTableReaVOs) {
		int result = 0;
//		Set<Long> keys = new HashSet<>();
		for (RptPersonalSubjectTableReaVO rptPersonalSubjectTableReaVO : rptPersonalSubjectTableReaVOs) {
			String opt = rptPersonalSubjectTableReaVO.getOpt();
			if (ParamType.DELETE.getMsg().equals(opt)) {
//				keys.add(rptPersonalSubjectTableReaVO.getId());
				int i = deleteByPrimaryKey(rptPersonalSubjectTableReaVO.getId());
				if (i > 0) {
					result++;
				}
			} else if (ParamType.UPDATE.getMsg().equals(opt)) {
				int i = updateByPrimaryKeySelective(rptPersonalSubjectTableReaVO);
				if (i > 0) {
					result++;
				}
			} else if (ParamType.INSERT.getMsg().equals(opt)) {
				int i = insertSelective(rptPersonalSubjectTableReaVO);
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