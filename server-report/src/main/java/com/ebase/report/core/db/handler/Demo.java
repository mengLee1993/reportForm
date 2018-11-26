//package com.ebase.report.core.db.handler;
//
//import com.ebase.report.core.pageUtil.PageDTO;
//import com.ebase.report.core.pageUtil.PageReportDetail;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Demo {
//
//
//    public static void main(String[] args) {
////
//
//        List<String> list = new ArrayList<>();
//
//        list.add("1");
//        list.add("1");
//        list.add("1");
//        list.add("2");
//        list.add("2");
//        list.add("2");
//        list.add("3");
//        list.add("3");
//        list.add("3");
////        list.add("3");
//
//
//        List<List<String>> lists = averageAssign(list, 2);
////
////        System.out.println(lists);
//
////        System.out.println(8 % 2);
//
////        System.out.println(10 % 3 == 0 ? 10 / 3 :10 / 3 + 1);
//
//        List<List<String>> split = split(list, 2);
//
//        System.out.println(lists);
//    }
//
//    /**
//     * 拆分集合
//     * @param <T>
//     * @param resList  要拆分的集合
//     * @param count    每个集合的元素个数
//     * @return  返回拆分后的各个集合
//     */
//    public static  <T> List<List<T>> split(List<T> resList,int count){
//
//        if(resList==null ||count<1)
//            return  null ;
//        List<List<T>> ret=new ArrayList<List<T>>();
//        int size=resList.size();
//        if(size<=count){ //数据量不足count指定的大小
//            ret.add(resList);
//        }else{
//            int pre=size/count;
//            int last=size%count;
//            //前面pre个集合，每个大小都是count个元素
//            for(int i=0;i<pre;i++){
//                List<T> itemList=new ArrayList<T>();
//                for(int j=0;j<count;j++){
//                    itemList.add(resList.get(i*count+j));
//                }
//                ret.add(itemList);
//            }
//            //last的进行处理
//            if(last>0){
//                List<T> itemList=new ArrayList<T>();
//                for(int i=0;i<last;i++){
//                    itemList.add(resList.get(pre*count+i));
//                }
//                ret.add(itemList);
//            }
//        }
//        return ret;
//
//    }
//    /**
//     * 将一个list均分成n个list
//     * @param source
//     * @return
//     */
//    public static <T> List<List<T>> averageAssign(List<T> source,int n){
//        List<List<T>> result=new ArrayList<List<T>>();
//
//        if(source.size() < n){
//            List<T> list = new ArrayList<>();
//            for(T t:source){
//                list.add(t);
//            }
//            result.add(list);
//            return result;
//        }
//
//        int remaider=source.size()%n;  //(先计算出余数)
//        int number=source.size()/n;  //然后是商
//        int offset=0;//偏移量
//        for(int i=0;i<n;i++){
//            List<T> value=null;
//            if(remaider>0){
//                value=source.subList(i*number+offset, (i+1)*number+offset+1);
//                remaider--;
//                offset++;
//            }else{
//                value=source.subList(i*number+offset, (i+1)*number+offset);
//            }
//            result.add(value);
//        }
//        return result;
//    }
//
//
//}
