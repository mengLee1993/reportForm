package com.ebase.report.core.pageUtil;

import org.apache.poi.ss.formula.functions.T;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

public class Paging {

    private static Integer defaultSize = 20;
    private long total;
    private List<T> data;
    private Map<String, Object> searchMap;
    private Object search;
    private Integer pageNo;
    private Integer size;
    private T totalColum; //add by zhaomeinan

    private Paging() {
    }

    private void init() {
        total = 0L;
        data = new ArrayList<T>();
        searchMap = new HashMap<String, Object>();
    }


    public Paging(Integer pageNo) {
        init();
        setPageNo(pageNo);
        setSize(defaultSize);
    }

    public Paging(Integer pageNo, Integer size) {
        init();
        setPageNo(pageNo);
        if (size == null) {
            setSize(defaultSize);
        } else {
            this.setSize(size);
        }
    }

    public Paging(long total, List<T> rows) {
        this.total = total;
        this.data = rows;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public Map<String, Object> getSearchMap() {
        return searchMap;
    }



    public Object getSearch() {
        return search;
    }

    public void setSearch(Object search) {
        if (search == null) {
            return;
        }
        Field[] fields = search.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), search.getClass());
                Object value = propertyDescriptor.getReadMethod().invoke(search);
                if (value != null) {
                    searchMap.put(field.getName(), propertyDescriptor.getReadMethod().invoke(search));
                }
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
        this.search = search;
    }

    public void setSize(Integer size) {
        this.size = size;
        searchMap.put("offset", (this.pageNo - 1) * size);
        searchMap.put("limit", size);
        searchMap.put("end", this.pageNo * size);
    }

    public Integer getSize() {
        return this.size;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo == null ? 1 : pageNo;
    }

    public Integer getPageNo() {
        return pageNo;
    }



    public T getTotalColum() {
        return totalColum;
    }

    public void setTotalColum(T totalColum) {
        this.totalColum = totalColum;
    }



}
