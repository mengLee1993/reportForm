package com.ebase.report;

import com.ebase.report.model.dynamic.ReportDimension;

import java.util.ArrayList;
import java.util.List;

public class Demo {

    private  volatile String name = "";

    public static void main(String[] args) {
        List<ReportDimension> reportDimensions = new ArrayList<>();
        ReportDimension reportDimension = new ReportDimension();
//        reportDimension.setIsChecked((byte)0);
        reportDimensions.add(reportDimension);
        reportDimensions.stream().filter(x -> x.getIsChecked() == (byte)1).forEach(z -> {
            System.out.println("yes!");
        });
    }
}
