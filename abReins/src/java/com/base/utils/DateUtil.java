package com.base.utils;



import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * 时间工具类 项目中与日期和时间有关的方法都可以在这个类中找到
 *
 * @author xianqin-bill
 */
public class DateUtil {
    public final static int TIME_DAY_MILLISECOND = 86400000;
    
    
    private static Logger logger=LogUtils.getConsoleLogIns();

    /**
     * 存储前端日期的关键字
     */
    public final static String FIST_DAY = "fist_day";

    /**
     * 存储前端月的关键字
     */
    public final static String FIST_MONTH = "fist_month";

    /**
     * 存储间隔年的关键字
     */
    public final static String SPLIT_YEAR = "split_year";

    /**
     * 存储后端月的关键字
     */
    public final static String LAST_MONTH = "last_month";

    /**
     * 存储后端日期的关键字
     */
    public final static String LAST_DAY = "last_day";


    /**
     * 格式化时间
     *
     * @param dateTime 要格式化的时间
     * @param pattern  格式化的样式
     * @return 已格式化的时间
     */
    public static String formatDateTime(Date dateTime, String pattern) {
        SimpleDateFormat dateFmt = new SimpleDateFormat(pattern);
        return dateTime == null ? "" : dateFmt.format(dateTime);
    }

    /**
     * 取得时间
     *
     * @param dateTime
     * @return 返回"2014-01-14 16:09"数据格式的字符串
     */
    public static String formateDate(Date dateTime) {
        return formatDateTime(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 取得日期
     *
     * @param dateTime
     * @return 数据格式的字符串 []
     */
    public static String shortTime(Date dateTime) {
        return formatDateTime(dateTime, "yyyy-MM-dd");
    }

    /**
     * 将毫秒数转换为时间对象
     *
     * @param nowMills
     * @return
     */
    public static Calendar calendar(long nowMills) {
        Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTimeInMillis(nowMills);
        return cal;
    }

    /**
     * 获取当前日期的当前开始日期
     *
     * @param nowTime
     * @return
     */
    public static Date getDayBegin(Timestamp timestamp) {
        return getDayBegin(timestamp.getTime());
    }

    /**
     * @param nowMills
     * @return
     */
    public static Date getDayBegin(long timeMills) {
        Calendar cal = calendar(timeMills);
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return cal.getTime();
    }

    public static Date getNextDayBegin(Timestamp timestamp) {
        return getNextDayBegin(timestamp.getTime());
    }

    /**
     * 获取当前日期后一天的开始日期对象实例
     *
     * @param nowMills
     * @return
     */
    public static Date getNextDayBegin(long timeMills) {
        Calendar cal = calendar(timeMills);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        ;
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return cal.getTime();
    }

    /**
     * 获取当前最后的时间对象
     *
     * @param now
     * @return
     */
    public static Date getDayEnd(Timestamp timestamp) {
        return getDayEnd(timestamp.getTime());

    }

    /**
     * 获取当前最后的时间对象
     *
     * @param nowMills
     * @return
     */
    public static Date getDayEnd(long timeMills) {
        Calendar cal = calendar(timeMills);
        cal.set(Calendar.HOUR_OF_DAY, 23);// H置零
        cal.set(Calendar.MINUTE, 59);// m置零
        cal.set(Calendar.SECOND, 59);// s置零
        cal.set(Calendar.MILLISECOND, 999);// S置零
        return cal.getTime();
    }

    /**
     * 本月第一天
     *
     * @param nowMills
     * @return
     */
    public static Date getMonthBegin(long timeMills) {
        Calendar cal = calendar(timeMills);
        cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return cal.getTime();
    }

    /**
     * 本月最后一天
     *
     * @param nowMills
     * @return
     */
    public static Date getMonthBegin(Timestamp timestamp) {
        return getMonthBegin(timestamp.getTime());
    }

    /**
     * @param nowTime
     * @return
     */
    public static Date getMonthEnd(Timestamp timestamp) {
        return getMonthEnd(timestamp.getTime());
    }

    /**
     * @param nowMills
     * @return
     */
    public static Date getMonthEnd(long nowMills) {

        Calendar cal = calendar(nowMills);
        cal.set(Calendar.DAY_OF_MONTH, 0); // M月置零
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
        cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
        return cal.getTime();
    }

    /**
     * 时间+-天数 :要得到的时间
     *
     * @param d      时间
     * @param offset 天数
     * @param bool   true天数加 , false天数减
     * @return
     */
    public static Timestamp changeDay(Timestamp d, int offset, boolean bool) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        if (bool) {
            calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) + offset));
        } else {
            calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) - offset));
        }
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static long moreThanTwoDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Timestamp time = new Timestamp(calendar.getTimeInMillis());
        return time.getTime();
    }

    /**
     * 毫秒转为日期
     *
     * @param timeMills
     * @return
     */
    public static Date getDate(long timeMills) {
        Calendar cal = calendar(timeMills);
        return cal.getTime();
    }

    /**
     * 毫秒转为日期
     *
     * @param timeMills
     * @return
     */
    public static Timestamp getTDateEnd(long timeMills) {
        Calendar cal = calendar(timeMills);
        // cal.set(Calendar.HOUR_OF_DAY, 23);// H置零
        // cal.set(Calendar.MINUTE, 59);// m置零
        // cal.set(Calendar.SECOND, 59);// s置零
        // cal.set(Calendar.MILLISECOND, 14);// S置零
        Date date = cal.getTime();
        Timestamp ts = new Timestamp(date.getTime());
        return ts;
    }

    /**
     * 停留时间字符串转换成时间
     *
     * @param 193:06:04 转为 8天1时6分4秒
     */
    public static String getStaytime(String stayTime) {
        String[] temp = stayTime.split(":");
        int day = Integer.parseInt(temp[0]) / 24;
        int hour = Integer.parseInt(temp[0]) % 24;
        int minutes = Integer.parseInt(temp[1]);
        int second = Integer.parseInt(temp[2]);
        String formatStaytime = "";
        if (day > 0) {
            formatStaytime = "<font color=\"red\">" + day + "</font>天<font color=\"red\">" + hour
                    + "</font>时<font color=\"red\">" + minutes + "</font>分<font color=\"red\">" + second + "</font>秒";
        } else if (hour > 0) {
            formatStaytime = "<font color=\"red\">" + hour + "</font>时<font color=\"red\">" + minutes
                    + "</font>分<font color=\"red\">" + second + "</font>秒";
        } else if (minutes > 0) {
            formatStaytime = "<font color=\"red\">" + minutes + "</font>分<font color=\"red\">" + second + "</font>秒";
        } else {
            formatStaytime = "<font color=\"red\">" + second + "</font>秒";
        }
        return formatStaytime;
    }

    /**
     * 取得时间的第一天时间 如2014-06-01 00：00：00
     *
     * @param date
     * @return
     */
    public static Timestamp getCustomStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return null;

    }

    /**
     * 取得下一个月的时间 如2014-07-01 00：00：00
     *
     * @param date
     * @return
     */
    public static Timestamp getCustomEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return null;
    }

    /**
     * 将Timestamp对象实例转换为带有日期格式的字符串输出 格式化为 yyyy-MM-dd HH:mm:ss
     *
     * @param time Timestamp对象
     * @return 经过日期的字符串形式
     */
    public static String formatTimestampToTime(Timestamp time) {
        Date d = new Date(time.getTime());
        return DateUtil.formatDateTime(d, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将类似 "2014-11-12 12:12:12"的字符串转换成Date类型对象
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseStringToDate(String date) throws ParseException {
        Date result = null;
        String parse = date;
        parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");
        DateFormat format = new SimpleDateFormat(parse);
        result = format.parse(date);
        return result;
    }

    /**
     * 获取当前系统时间，以Timestamp对象实例形式返回
     *
     * @return 当前系统时间的Timestamp对象实例
     */
    public static Timestamp getCurrnetTimestampIns() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 将字符串转换为Calendar实例 如果字符串与格式字符串不匹配，将抛出异常，请调用方法自行处理
     *
     * @param dateStr   日期类型格式的字符串，如2017-01-01
     * @param formatStr 与日期格式对应的格式字符串 如yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Calendar dateStr2Calendar(String dateStr, String formatStr) throws ParseException {
        // logger.debug(formatStr);
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取两个时间的相差秒数
     *
     * @param brforeTimeStr 较小的时间
     * @param lastTimeStr   较大的时间
     * @return
     * @throws ParseException
     */
    public static long getDifference(String brforeTimeStr, String lastTimeStr) throws ParseException {
        Date lastDate = parseStringToDate(lastTimeStr);
        Date beforeDate = parseStringToDate(brforeTimeStr);
        return (lastDate.getTime() - beforeDate.getTime()) / 1000;
    }

    /**
     * 将秒数转换为小时，分钟，秒的字符串描述进行输出
     *
     * @param secondLong
     * @return
     */
    public static String getDescBySecond(long secondLong) {
        String returnStr = "";
        long h = secondLong / 3600;
        long m = (secondLong % 3600) / 60;
        long s = (secondLong % 3600) % 60;
        if (h > 0) {
            returnStr += h + "小时";
        }
        if (m > 0) {
            returnStr += m + "分钟";
        }
        if (s > 0) {
            returnStr += s + "秒";
        }
        return returnStr;
    }


    /**
     * 计算日期分隔，将日期拆分为前端日、前端间隔月、中端隔年、后端间隔月以及后端日
     * 前端日：开始日期到月底的日期值
     * 前间隔月：开始日期到结束日期到年底的间隔月数
     * 中间间隔年：开始日期到结束日期的中间间隔整年数
     * 后端间隔月：开始日期到结束日期之间，最后端日期与当年第一天的间隔整月数
     * 后端日：结束日期到当月的第一天整日数
     * 如果结束日期大于当前的日期，则结束日期为当前日期(参数splitFalg=true)
     * 参数splitFalg=false，则结束日期等于参数endDate
     *
     * @param startDate 起始日期 使用yyyy-MM-dd格式
     * @param endDate   结束日期 使用yyyy-MM-dd格式
     * @param splitFalg 分隔标识
     *                  如果splitFalg=true，endDate的取值为当前时间与endDate日期的最小值
     *                  如果splitFalg=false endDate保持不变
     * @return 一个包含了前端日、前端月、中间间隔年、后端间隔月以及后端日的数据结构，含义参见本类的'FIST_DAY'、'FIST_MONTH'、'SPLIT_YEAR'、'LAST_MONTH'、'LAST_DAY'、
     * 如果结束日期大于开始日期，则返回一个null
     */
    public static Map<String, List<Integer>> spliteDate(String startDateStr, String endDateStr, boolean splitFalg) throws ParseException {
        List<Integer> splitYearList;
        List<Integer> startIntervalMonth;
        List<Integer> endIntervalMonth;
        List<Integer> startIntervalDay;
        List<Integer> endIntervalDay;
        Date startDate = parseStringToDate(startDateStr);
        Date endDate = parseStringToDate(endDateStr);
        Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.setTime(startDate);
        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.setTime(endDate);
        Map<String, List<Integer>> returnMap = new HashMap<String, List<Integer>>(5);
        splitYearList = getIntervalYear(startDateCalendar, endDateCalendar);
        int startYear = startDateCalendar.get(Calendar.YEAR);
        int endYear = endDateCalendar.get(Calendar.YEAR);
        int interval = endYear - startYear;
        if (splitYearList != null && splitYearList.size() > 0) {
//            printList(splitYearList);
            logger.debug("间隔年份:");
            printList(splitYearList);
            returnMap.put(SPLIT_YEAR, splitYearList);
        }
        if (interval == 0) {
            //同年分日期
            //获取起始间隔月
            startIntervalMonth = getIntervalMonth(startDateCalendar, endDateCalendar);
            if (startIntervalMonth != null && startIntervalMonth.size() > 0) {
                logger.debug("起始间隔月份");
                returnMap.put(FIST_MONTH, startIntervalMonth);
                printList(startIntervalMonth);
            }
            int startMonth = startDateCalendar.get(Calendar.MONTH);
            int endMonth = endDateCalendar.get(Calendar.MONTH);
            if (startMonth - endMonth == 0) {// 属于同一个月
                startIntervalDay = getIntervalDay(startDateCalendar, endDateCalendar);
                returnMap.put(FIST_DAY, startIntervalDay);
                logger.debug("前端日");
                printList(startIntervalDay);
            } else { //不属于同一个月
                startIntervalDay = getIntervalDay(startDateCalendar, getLastDateCalendarByYearAndMonth(startYear, startMonth + 1));
                returnMap.put(FIST_DAY, startIntervalDay);
                logger.debug("前端日");
                printList(startIntervalDay);
                endIntervalDay = getIntervalDay(getFirstDateCalendarByYearAndMonth(startYear, endMonth + 1), endDateCalendar);
                returnMap.put(LAST_DAY, endIntervalDay);
                logger.debug("后端日");
                printList(endIntervalDay);
            }

        } else {
            //不同年份日期
            //获取起始间隔月份
            startIntervalMonth = getIntervalMonth(startDateCalendar, getFirstDateCalendarFromYear(startYear));
            if (startIntervalMonth != null && startIntervalMonth.size() > 0) {
                logger.debug("起始间隔月份");
                returnMap.put(FIST_MONTH, startIntervalMonth);
                printList(startIntervalMonth);
            }
            //获取后端间隔月份
            endIntervalMonth = getIntervalMonth(getLastDateCalendarFromYear(endYear), endDateCalendar);
            if (endIntervalMonth != null && endIntervalMonth.size() > 0) {
                logger.debug("后端间隔月份");
                returnMap.put(LAST_MONTH, endIntervalMonth);
                printList(endIntervalMonth);
            }
            //计算间隔日
            startIntervalDay = getIntervalDay(startDateCalendar, getLastDateCalendarByYearAndMonth(startYear, startDateCalendar.get(Calendar.MONTH) + 1));
            logger.debug("前端日");
            printList(startIntervalDay);
            returnMap.put(FIST_DAY, startIntervalDay);
            endIntervalDay = getIntervalDay(getFirstDateCalendarByYearAndMonth(endYear, endDateCalendar.get(Calendar.MONTH) + 1), endDateCalendar);
            returnMap.put(LAST_DAY, endIntervalDay);
            logger.debug("后端日");
            printList(endIntervalDay);
        }

        return returnMap;
    }


    /**
     * 获取当天年份的最后一个日期对象实例
     *
     * @param year 年份，如2017
     * @return 当天年份的最后一个日期对象实例
     */
    private static Calendar getFirstDateCalendarFromYear(int year) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date lastDate = parseStringToDate(String.valueOf(year) + "-12-31");
        calendar.setTime(lastDate);
        return calendar;
    }

    /**
     * 获取当天年份的一个日期对象实例
     *
     * @param year 年份，如2017
     * @return 当天年份的第一一个日期对象实例
     */
    private static Calendar getLastDateCalendarFromYear(int year) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date firstDate = parseStringToDate(String.valueOf(year) + "-01-01");
        calendar.setTime(firstDate);
        return calendar;
    }

    /**
     * 根据年份和月份信息获取当年当月的第一天日期对象实例
     *
     * @param year  年份信息
     * @param month 月份信息
     * @return 年份和月份信息获取当年当月的第一天日期对象实例
     * @throws ParseException
     */
    public static Calendar getFirstDateCalendarByYearAndMonth(int year, int month) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date firstDate = parseStringToDate(String.valueOf(year) + "-" + String.valueOf(month) + "-01");
        calendar.setTime(firstDate);
        return calendar;
    }

    /**
     * 根据年份和月份信息获取当年当月的最后一天日期对象实例
     *
     * @param year  年份信息
     * @param month 月份信息
     * @return 年份和月份信息获取当年当月的最后日期对象实例
     * @throws ParseException
     */
    public static Calendar getLastDateCalendarByYearAndMonth(int year, int month) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date firstDate = parseStringToDate(String.valueOf(year) + "-" + String.valueOf(month) + "-01");
        calendar.setTime(firstDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar;
    }

    private static void printList(List<Integer> listIns) {
        if (listIns != null && listIns.size() > 0) {
            for (int i = 0; i < listIns.size(); i++) {
                logger.debug(listIns.get(i));
            }
        }
    }

    /**
     * 计算间隔年
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 返回两个日期相隔的整数年份值
     */
    private static List<Integer> getIntervalYear(Calendar startDate, Calendar endDate) {
        List<Integer> splitYearList = null;
        //计算间隔年 这里不考虑特殊情况：起始日期就是当年的1月1日，终止日期是当年的12月31日的情况，这两种情况将会在其他地方进行判断
        int startYear = startDate.get(Calendar.YEAR);   //起始日期年份
        int endYear = endDate.get(Calendar.YEAR);       //截止日期年份
        int interval = endYear - startYear;
        int startYearOfDay = startDate.get(Calendar.DAY_OF_YEAR);   //起始日期是当年的第几天
        int endYearOfDay = endDate.get(Calendar.DAY_OF_YEAR);       //截止日期是当年的第几天
        if (interval == 0) {
            //这种情况，为开始日期为年初第一天，结束日期为最后一天
            if (startYearOfDay == 1 && endYearOfDay >= 365) {
                splitYearList = new ArrayList<Integer>(1);
                //如果差值等等于0，则开始日期和结束日期在同一年
                splitYearList.add(startDate.get(Calendar.YEAR));
                //不用判断月份间隔了，直接退出
                return splitYearList;
            }
        } else if (interval == 1) {
            splitYearList = new ArrayList<Integer>(2);
            if (startYearOfDay == 1) {
                //startYearOfDay == 1 则起始日期为当年的第一天
                splitYearList.add(startDate.get(Calendar.YEAR));
            }
            if (endYearOfDay >= 365) {
                //endYearOfDay>=365 则结束日期为当年的最后一天
                splitYearList.add(endDate.get(Calendar.YEAR));
            }
            return splitYearList;
        } else {
            splitYearList = new ArrayList<Integer>();
            if (startYearOfDay == 1) {
                splitYearList.add(startDate.get(Calendar.YEAR));
            }
            for (; interval > 1; interval--) {
                ++startYear;
                splitYearList.add(startYear);
            }
            if (endYearOfDay >= 365) {
                splitYearList.add(endDate.get(Calendar.YEAR));//1111111111111111111111111111111111111111111111
            }

        }
        return splitYearList;
    }

    /**
     * 计算间隔月
     * 起始日期与结束日期必须为同一年
     * 否则返回空
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 返回两个日期相隔的整数月份值
     */
    public static List<Integer> getIntervalMonth(Calendar startDate, Calendar endDate) {
        List<Integer> interalMonthList = null;
        int startDateMonth = startDate.get(Calendar.MONTH);
        int endDateMonth = endDate.get(Calendar.MONTH);
        int interval = endDateMonth - startDateMonth;
        int startYearOfDay = startDate.get(Calendar.DAY_OF_YEAR);
        int endYearOfDay = endDate.get(Calendar.DAY_OF_YEAR);

        boolean startDateIsLastDayOfMonth = isFirstDayOfMonth(startDate);
        boolean endDateIsLastDayOfMonth = isLastDayOfMonth(endDate);
        if (startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) {
            //如果起始时间是当年第一天并且结束时间为当年的最后一天，则计算日期间隔没有含义，
            if (startYearOfDay == 1 && endYearOfDay >= 365) {
                return null;
            } else {
                if (interval == 0) {//月份差值等于0，证明在同一个月，直接返回空，因为不存在日期间隔 则需要判断其实日期是否为当月的第一天并且结束日期为当月的最后一天
                    if (startDateIsLastDayOfMonth && endDateIsLastDayOfMonth) {
                        interalMonthList = new ArrayList<Integer>(1);
                        interalMonthList.add(startDate.get(Calendar.MONTH) + 1);//月份下标从0开始，所以此处返回时+1进行修正
                    }
                    return null;
                } else { //月份差值大于0，证明日期至少分布在两个月
                    interalMonthList = new ArrayList<Integer>();
                    if (startDateIsLastDayOfMonth) {
                        interalMonthList.add(startDate.get(Calendar.MONTH) + 1);//如果起始日期为月份的第一天，则为整月，可以直接加一个月
                    }
                    for (; interval > 1; interval--) {
                        ++startDateMonth;
                        interalMonthList.add(startDateMonth + 1);
                    }
                    if (endDateIsLastDayOfMonth) {
                        interalMonthList.add(endDate.get(Calendar.MONTH) + 1);//如果结束日期为月份的第一天，则为整月，可以直接加一个月
                    }
                }
            }
        }
        return interalMonthList;
    }

    /**
     * 判断日期是否为月初
     *
     * @param dateIns 待判断日期实例
     * @return true-月初  false-不是月初
     */
    public static boolean isFirstDayOfMonth(Calendar dateIns) {
        if (dateIns.get(Calendar.DATE) == dateIns.getActualMinimum(Calendar.DATE)) {
            return true;
        }
        return false;
    }

    /**
     * 判断日期是否为月末
     *
     * @param dateIns 待判断日期实例
     * @return true-月末  false-不是月末
     */
    public static boolean isLastDayOfMonth(Calendar dateIns) {
        if (dateIns.get(Calendar.DATE) == dateIns.getActualMaximum(Calendar.DATE)) {
            return true;
        }
        return false;
    }

    /**
     * 计算间隔日
     * 开始日期与结束日期为同一个月
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 返回两个日期相隔的整数日期值
     */
    public static List<Integer> getIntervalDay(Calendar startDate, Calendar endDate) {
        List<Integer> dateList = null;
        int startDateMonthIndex = startDate.get(Calendar.MONTH);
        int endDateMonthIndex = endDate.get(Calendar.MONTH);
        int startDateYearIndex = startDate.get(Calendar.YEAR);
        int endDateYearIndex = endDate.get(Calendar.YEAR);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        logger.debug("getIntervalDay startDate="+format.format(startDate.getTime()));
        logger.debug("getIntervalDay endDate="+format.format(endDate.getTime()));
        //开始日期和结束日期必须是同年同月
        if (startDateMonthIndex == endDateMonthIndex && startDateYearIndex == endDateYearIndex) {
            boolean startDateIsLast = isFirstDayOfMonth(startDate);
            boolean endDateIsFirst = isLastDayOfMonth(endDate);
            //如果开始时间是月初并且结束时间的月末，则返回null

            if (startDateIsLast && endDateIsFirst) {
                return null;
            }
            int startDateDayOfMonthIndex = startDate.get(Calendar.DAY_OF_MONTH);
            int endDateDayOfMonthIndex = endDate.get(Calendar.DAY_OF_MONTH);
            dateList = new ArrayList<Integer>();
            //计算两个日期的差值
            int interval = endDateDayOfMonthIndex - startDateDayOfMonthIndex;
            if (interval == 0) {
                dateList = new ArrayList<Integer>(1);
                dateList.add(startDateDayOfMonthIndex);
            } else {
                for (; startDateDayOfMonthIndex <= endDateDayOfMonthIndex; startDateDayOfMonthIndex++) {
                    dateList.add(startDateDayOfMonthIndex);
                }
            }

        }
        return dateList;
    }


    public static void main(String[] args) throws ParseException {
        String startDateStr = "2017-07-05";
        String endDateStr = "2020-01-01";
        DateUtil.spliteDate(startDateStr, endDateStr, false);

//        String dateStr = "2016-02-28";
//        Date date = DateUtil.parseStringToDate(dateStr);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        logger.debug(calendar.get(Calendar.DAY_OF_MONTH));

//        String startDateStr = "2016-01-02";
//        String endDateStr = "2016-12-31";
//        Calendar startCalendar = Calendar.getInstance();
//        Calendar endCalendar = Calendar.getInstance();
//        Date startDate = parseStringToDate(startDateStr);
//        Date endDate = parseStringToDate(endDateStr);
//        startCalendar.setTime(startDate);
//        endCalendar.setTime(endDate);
//        DateUtil.getIntervalMonth(startCalendar,endCalendar);

//        int year=2017;
//        int month =2;
//        Calendar start = DateUtil.getFirstDateCalendarByYearAndMonth(year,month);
//        Calendar end = DateUtil.getLastDateCalendarByYearAndMonth(year,month);
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        logger.debug(format.format(start.getTime()));
//        logger.debug(format.format(end.getTime()));

//        String startDateStr = "2015-02-02";
//        String endDateStr = "2016-02-29";
//        Calendar startCalendar = Calendar.getInstance();
//        Calendar endCalendar = Calendar.getInstance();
//        Date startDate = parseStringToDate(startDateStr);
//        Date endDate = parseStringToDate(endDateStr);
//        startCalendar.setTime(startDate);
//        endCalendar.setTime(endDate);
//        List<Integer> list = DateUtil.getIntervalDay(startCalendar,endCalendar);
//        for(int i=0;i<list.size();i++){
//            logger.debug(list.get(i));
//        }

//        Calendar testCalendar = DateUtil.getLastDateCalendarByYearAndMonth(2015, 2);
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        logger.debug(format.format(testCalendar.getTime()));
//        testCalendar = DateUtil.getLastDateCalendarByYearAndMonth(2015, testCalendar.get(Calendar.MONTH) + 1);
//        logger.debug(format.format(testCalendar.getTime()));
//        logger.debug(DateUtil.isLastDayOfMonth(testCalendar));
    }

}