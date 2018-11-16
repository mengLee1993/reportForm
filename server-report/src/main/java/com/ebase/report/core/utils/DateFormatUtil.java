package com.ebase.report.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date getStartDateByStr(String dateStr) {
        if (null != dateStr && !"".equals(dateStr)) {
            try {
                return simpleDateFormat.parse(dateStr + " 00:00:00");
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    public static String getStartDateStr(Date date) {
        if (null != date) {
            try {
                String dateStr = DateUtil.formatDate(date, DateUtil.DATE_PATTERN);
                return dateStr + " 00:00:00";
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static Date getEndDateByStr(String dateStr) {
        if (null != dateStr && !"".equals(dateStr)) {
            try {
                return simpleDateFormat.parse(dateStr + " 23:59:59");
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    public static String getEndDateStr(Date date) {
        if (null != date) {
            try {
                String dateStr = DateUtil.formatDate(date, DateUtil.DATE_PATTERN);
                return dateStr + " 23:59:59";
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
