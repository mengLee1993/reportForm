package com.ebase.report.core.utils;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * 复制bean 工具类
 * </p>
 *
 * @project core
 * @class CopyUtil.java
 */
public class BeanCopyUtil {

	private final static Logger log = LoggerFactory.getLogger(BeanCopyUtil.class);

	/**
	 * 复制 obj 并创建一个新的对象 若是obj 为空， 则返回null
	 * 
	 * @param obj
	 * @param clazz
	 * @return
	 * @exception 不抛出错误
	 */
	public static <T> T copy(Object src, Class<T> clazz) {
		T t = null;
		try {
			if (src != null) {
				t = clazz.newInstance();
				BeanCopier copier = BeanCopier.create(src.getClass(), clazz, false);
				copier.copy(src, t, null);
			}
		} catch (Exception e) {
			log.error("copy object error", e);
		}
		return t;
	}

	/**
	 * 复制 List<obj> 则返回一个ArrayList <br>
	 * 若是src =null 则返回一个ArrayList size=0
	 * 
	 * @param src
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> copyList(List<?> src, Class<T> clazz) {
		if (src != null) {
			List<T> list = new ArrayList<T>();
			for (Object obj : src) {
				T t = copy(obj, clazz);
				list.add(t);
			}
			return list;
		} else {
			return new ArrayList<T>();
		}
	}

	/**
	 * 将src对象中属性复制到to对象中，相同则覆盖
	 * 
	 * @param src
	 * @param to
	 */
	public static void copy(Object src, Object to) {
		if (src == null || to == null) {
			return;
		} else {
			BeanCopier copier = BeanCopier.create(src.getClass(), to.getClass(), false);
			try {
				copier.copy(src, to, null);
			} catch (Exception e) {
				log.error("copy object error", e);
			}
		}
	}

	public static Map<String, Object> getObjectMapProperties(Object src) {
		if (src == null) {
			return null;
		} else {
			Map<String, Object> map = new HashMap<>();
			try {
				Class<?> clazz = src.getClass();
				Set<String> props = new HashSet<>();
				for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
					Field[] fs = clazz.getDeclaredFields();
					for (int i = 0; i < fs.length; i++) {
						props.add(fs[i].getName());
					}
				}
				for (String key : props) {
					map.put(key, ReflectUtil.forceGetProperty(src, key));
				}
				return map;
			} catch (Exception e) {
				log.error("get ObjectMap Properties error", e);
				return map;
			}

		}
	}
	

    public static void copyProperties(Object source, Object target) {
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }

    public static void copyProperties(Object source, Object target, Converter converter) {
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), true);
        copier.copy(source, target, converter);
    }
	
	 /**
     * 批量拷贝集合的方法
     * kim
     * @param sourceList
     * @param targetList
     * @param clasz
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static <T, V> List<V> copyPropertieses(List<T> sourceList, List<V> targetList, Class<?> clasz) {
    	if (sourceList != null && sourceList.size() >= 1) {
	    	for (T t : sourceList) {
	    			V instance = null;
					try {
						instance = (V)clasz.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					copyProperties(t, instance);
					targetList.add(instance);
	    	}
	    	return targetList;
    	}
    	return targetList;
    }
}
