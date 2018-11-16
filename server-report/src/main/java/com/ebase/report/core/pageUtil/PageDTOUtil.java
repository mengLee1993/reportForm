package com.ebase.report.core.pageUtil;



import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.ReflectUtil;

import java.util.List;

/**
 * <p>
 * PageHelper 插件的处理
 * </p>
 *
 * @project core
 * @class PageDTOUtil
 * @author a@panly.me
 * @date 2017年8月8日下午3:26:28
 */
public class PageDTOUtil {

	public static void startPage(Object params){

		int pageNum = (int) ReflectUtil.getFieldValue(params, "pageNum");
		int pageSize = (int) ReflectUtil.getFieldValue(params, "pageSize");
		PageDTO<?> pageDTO = new PageDTO<>(pageNum, pageSize);

		PageThreadLocal.init(pageDTO);
	}

	public static void startPage(int pageNum,int pageSize){
		PageDTO<?> pageDTO = new PageDTO<>(pageNum, pageSize);
		PageThreadLocal.init(pageDTO);
	}

	public static void endPage(){
		PageThreadLocal.remove();
	}

	public static PageDTO<?> getCurrent(){
		return PageThreadLocal.get();
	}


	/**
	 * 在 page startPage 和 endPage 方法之间的查询语句返回的List对象进行转换，
	 * @param list
	 * @return
	 */
	public static <T> PageDTO<T> transform(List<?> list, Class<T> clazz) {
		if(PageDTOUtil.getCurrent()==null){
			throw new RuntimeException("请对在 startPage和 endPage之间的dao查询结果进行转换");
		}
		PageDTO<T> pageDTO = new PageDTO<>(PageDTOUtil.getCurrent().getPageNum(), PageDTOUtil.getCurrent().getPageSize());
		pageDTO.setTotal( PageDTOUtil.getCurrent().getTotal());
		List<T> resultData = BeanCopyUtil.copyList( PageDTOUtil.getCurrent().getResultData(), clazz);
		pageDTO.setResultData(resultData);
		return pageDTO;
	}

	/**
	 * 在 page startPage 和 endPage 方法之间的查询语句返回的List对象进行转换，
	 * @param list
	 * @return
	 **/
	public static <T> PageDTO<T> transform(List<T> list) {
		if(PageDTOUtil.getCurrent()==null){
			throw new RuntimeException("请对在 startPage和 endPage之间的dao查询结果进行转换");
		}
		PageDTO<T> pageDTO = new PageDTO<>(PageDTOUtil.getCurrent().getPageNum(), PageDTOUtil.getCurrent().getPageSize());
		pageDTO.setTotal(PageDTOUtil.getCurrent().getTotal());
		pageDTO.setResultData(list);
		return pageDTO;
	}

}
