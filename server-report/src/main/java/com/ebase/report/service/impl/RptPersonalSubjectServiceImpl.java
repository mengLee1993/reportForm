package com.ebase.report.service.impl;

import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.dao.RptPersonalSubjectMapper;
import com.ebase.report.model.RptPersonalSubject;
import com.ebase.report.service.RptPersonalSubjectService;
import com.ebase.report.vo.RptPersonalSubjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * dal Interface:RptPersonalSubject
 * @author 
 * @date 2018-10-25
 */
@Service
@Transactional
public class RptPersonalSubjectServiceImpl implements RptPersonalSubjectService {

    @Autowired
    private RptPersonalSubjectMapper rptPersonalSubjectMapper;

	@Override
    public List<RptPersonalSubjectVO> findAll() {
        List<RptPersonalSubject> rptPersonalSubjects = rptPersonalSubjectMapper.selectAll();
        List<RptPersonalSubjectVO> rptPersonalSubjectVOs = BeanCopyUtil.copyList(rptPersonalSubjects, RptPersonalSubjectVO.class);
        return rptPersonalSubjectVOs;
    }

	@Override
    public List<RptPersonalSubjectVO> findSelective(RptPersonalSubjectVO record){
		RptPersonalSubject model = BeanCopyUtil.copy(record, RptPersonalSubject.class);
		List<RptPersonalSubject> rptPersonalSubjects = rptPersonalSubjectMapper.select(model);
		List<RptPersonalSubjectVO> rptPersonalSubjectVOs = BeanCopyUtil.copyList(rptPersonalSubjects, RptPersonalSubjectVO.class);
		return rptPersonalSubjectVOs;
    }

	@Override
    public RptPersonalSubjectVO getByPrimaryKey(Long key){
    	RptPersonalSubject rptPersonalSubject = rptPersonalSubjectMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(rptPersonalSubject, RptPersonalSubjectVO.class);
    }

	@Override
    public Integer insert(RptPersonalSubjectVO record){
    	RptPersonalSubject rptPersonalSubject = BeanCopyUtil.copy(record, RptPersonalSubject.class);
        return rptPersonalSubjectMapper.insert(rptPersonalSubject);

    }

	@Override
    public Integer insertSelective(RptPersonalSubjectVO record){
        RptPersonalSubject rptPersonalSubject = BeanCopyUtil.copy(record, RptPersonalSubject.class);
        return rptPersonalSubjectMapper.insertSelective(rptPersonalSubject);
    }
    
    @Override
    public Integer updateByPrimaryKey(RptPersonalSubjectVO record){
    	RptPersonalSubject rptPersonalSubject = BeanCopyUtil.copy(record, RptPersonalSubject.class);
        return rptPersonalSubjectMapper.updateByPrimaryKey(rptPersonalSubject);
    }

	@Override
    public Integer updateByPrimaryKeySelective(RptPersonalSubjectVO record){
        RptPersonalSubject rptPersonalSubject = BeanCopyUtil.copy(record, RptPersonalSubject.class);
        return rptPersonalSubjectMapper.updateByPrimaryKeySelective(rptPersonalSubject);
    }

	@Override
    public Integer deleteByPrimaryKey(Long key){
        return rptPersonalSubjectMapper.deleteByPrimaryKey(key);
    }
    
    /**
	 *  IN
	 *	<foreach collection="keys" open="(" close=")" item="key" separator=",">
	 *		{key}
	 *	</foreach>
	 */
    @Override
    public Integer deleteByPrimaryKeys(Set<Long> keys){
    	//return rptPersonalSubjectMapper.deleteByPrimaryKeys(keys);
        return null;
    }

    @Override
    public Integer getSubjectByTypeId(Map<String, Object> tmp) {

        return rptPersonalSubjectMapper.selectCountByTypeId(tmp);
    }
    
   /* @Override
	public Integer keep(List<RptPersonalSubjectVO> rptPersonalSubjectVOs) {
		int result = 0;
//		Set<Long> keys = new HashSet<>();
		for (RptPersonalSubjectVO rptPersonalSubjectVO : rptPersonalSubjectVOs) {
			String opt = rptPersonalSubjectVO.getOpt();
			if (ParamType.DELETE.getMsg().equals(opt)) {
//				keys.add(rptPersonalSubjectVO.getId());
				int i = deleteByPrimaryKey(rptPersonalSubjectVO.getId());
				if (i > 0) {
					result++;
				}
			} else if (ParamType.UPDATE.getMsg().equals(opt)) {
				int i = updateByPrimaryKeySelective(rptPersonalSubjectVO);
				if (i > 0) {
					result++;
				}
			} else if (ParamType.INSERT.getMsg().equals(opt)) {
				int i = insertSelective(rptPersonalSubjectVO);
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