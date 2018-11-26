package com.ebase.report.core.db.handler;

import com.ebase.report.core.pageUtil.PageDTO;

import java.util.ArrayList;
import java.util.List;

public class Demo {


    public static void main(String[] args) {
//

        List<String> list = new ArrayList<>();

        list.add("1");
//        list.add("1");
//        list.add("1");
//        list.add("2");
//        list.add("2");
//        list.add("2");
//        list.add("3");
//        list.add("3");
        List<List<String>> lists = averageAssign(list, 3);

        System.out.println(lists);
    }

    /**
     * 将一个list均分成n个list
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }



}
