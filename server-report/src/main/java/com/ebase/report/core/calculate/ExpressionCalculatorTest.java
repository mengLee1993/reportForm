package com.ebase.report.core.calculate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class ExpressionCalculatorTest {
    public static void main(String[] arr) throws Exception{
//        System.out.println(ExpressionCalculator.cal("+(12.3 - 2.3 * 1) - 10/3 +- 2 "));
        //结果：4.6667

//        System.out.println(ExpressionCalculator.cal("-pow(MIN(1 + 2, 2),-1 * abs(MAX(1,1/-4+1/4)))"));
        //结果：-0.5

//        List<BigDecimal> param1List = new ArrayList<BigDecimal>();
//        param1List.add(new BigDecimal("1"));
//        param1List.add(new BigDecimal("1.2"));
//
//        List<BigDecimal> param2List = new ArrayList<BigDecimal>();
//        param2List.add(new BigDecimal("-2"));
//        param2List.add(new BigDecimal("1"));
//
//        List<BigDecimal> param3List = new ArrayList<BigDecimal>();
//        param3List.add(new BigDecimal("2"));
//        param3List.add(new BigDecimal("2"));
//        System.out.println(ExpressionCalculator.cal4Arr("pow($,$) * abs($)", param1List, param2List, param3List));
        //结果：[2.0000, 2.4000]

        System.out.println(ExpressionCalculator.cal("4*(-2)+8"));
    }
}
