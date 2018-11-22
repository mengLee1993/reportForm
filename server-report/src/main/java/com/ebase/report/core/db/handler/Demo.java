package com.ebase.report.core.db.handler;

public class Demo {

    public static void main(String[] args) {
        String LIMIT = " limit ";
        Integer count = 30001;
        Integer LENGTH = 10000;
        String sql = "select COUNT(1) from movie_data_10w where 1 = 1 and movie_area in ( '加拿大', '法国', '中国', '美国', '俄罗斯' ) and movie_type in ( '西部', '战争', '犯罪', '历史', '体育', '科幻' )";
        int size = count / LENGTH;
        size = count % LENGTH == 0 ? size - 1 : size;


        for(int i = 0; i <= size ; i ++){
            String s = LIMIT + (i * LENGTH) + "," + LENGTH;

            String sqlDetail = sql + s;

            System.out.println(sqlDetail);
        }
    }
}
