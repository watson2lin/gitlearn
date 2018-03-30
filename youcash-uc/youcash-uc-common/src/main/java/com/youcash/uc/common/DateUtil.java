/**
 * @(#)FltChgInfoCacheServerImpl.java
 * 
 * Copyright youcash.All rights reserved.
 * COD System.
 * 
 * @Version:V1.6.0
 * @JDK:jdk 1.6.0_27
 * @Module:cod-td-fltop
 */
/*- 			History
 ***********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2013年4月9日     	   xiaoj       Created
 *  2     2014年11月3日     	   leisy       edit
 ***********************************************
 */
package com.youcash.uc.common;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Title: 日期转化类
 * @Desription:
 * @Company:youcash
 * @ClassName:FltChgInfoCacheServerImpl
 * @Author:leisy
 * @CreateDate:2013年4月9日
 * @UpdateUser:xiaoj
 * @Version:1.0
 * 
 */
public class DateUtil {
	/**
	 * 时间转换模式
	 */
	public static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static String PATTERN1 = "yyyyMMddHHmmss";
	public static String PATTERN2 = "yyyyMMdd HHmmss";
	public static String PATTERN3 = "yyyy-MM-ddHH:mm:ss";
	public static String PATTERN4 = "yyyyMMddHHmmssSSS";
	public static String PATTERN5 = "yyyy";
	public static String PATTERN6 = "yyyyMM";
	public static String PATTERN7 = "yyyy-MM-dd";
	public static String PATTERN8 = "yyMMdd";
	public static String PATTERN9 = "HH:mm:ss";
	public static String PATTERN10 = "yyyy-MM-dd HH:mm";
	public static String PATTERN11 = "ddMMMyyHHmm";
	public static String PATTERN12 = "yyyyMMdd";
	public static String PATTERN13 = "yyyyMMddHHmm";

	/**
	 * 格式化日期，日期格式化为字符串
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String date2Str(String pattern, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == date)
			throw new RuntimeException("转换时间为空！");
		sdf.applyPattern(pattern);
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * @description:格式化日期字符串
	 * @author: chenyanzhi
	 * @createDate: 2013-6-20
	 * @param dateStr
	 * @param pattern
	 * @return:
	 */
	public static String strFormant(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 格式化日期，日期格式化为字符串
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String date2Str(String pattern, Date date, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
		if (null == date)
			throw new RuntimeException("转换时间为空！");
		sdf.applyPattern(pattern);
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 格式化日期，用默认的模式转化
	 * 
	 * @param date
	 * @return
	 */
	public static String date2StrDefaul(Date date) {
		return date2Str(DEFAULT_PATTERN, date);
	}

	public static Date datestr2DateDefault(String datestr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(datestr);
	}

	public static Date str2Date(String str, String partten) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(partten);
		return sdf.parse(str);
	}

	public static Date str2Date(String str, String partten, Locale locale) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(partten, locale);
		return sdf.parse(str);
	}

	public static String getYear(Date date) {
		return date2Str(PATTERN5, date);
	}

	/**
	 * 得到当前月的最后一天
	 * 
	 * @param sDate1
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastDayOfMonth(Date date) throws ParseException {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(date);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		String dateStr = date2Str(PATTERN7, lastDate);
		dateStr = dateStr + " " + "23:59:59";
		return datestr2DateDefault(dateStr);
	}

	/**
	 * 得到当前月的第一天
	 * 
	 * @param sDate1
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfMonth(Date date) throws ParseException {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(date);
		final int firstDay = cDay1.getActualMinimum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(firstDay);
		String dateStr = date2Str(PATTERN7, lastDate);
		dateStr = dateStr + " " + "00:00:00";
		return datestr2DateDefault(dateStr);
	}

	/**
	 * 日期操作，加amount天，为负数即减 lsp
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, amount);
		return calendar.getTime();
	}

	public static Date addHour(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, amount);
		return calendar.getTime();
	}

	/**
	 * 在指定的日期上加上指定的分钟数，得出新日期
	 * 
	 * @author Yangjinbo
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMinute(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, amount);
		return calendar.getTime();
	}

	public static Boolean compareDate(Date d1, Date d2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s1 = sdf.format(d1);
		String s2 = sdf.format(d2);
		if (s1.equals(s2)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断两个Date类型值是否相等
	 * @ClassName: DateUtil
	 * @Description: 若都为null则视为相等
	 * @author: Yangjinbo
	 * @date: 2014-1-15
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isDateEqual(Date d1, Date d2) {
		if ((null == d1) && (null == d2)) {
			return true;
		}
		if ((null != d1) && (null == d2)) {
			return false;
		}
		if ((null == d1) && (null != d2)) {
			return false;
		}
		if ((d1.compareTo(d2)) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到指定日期
	 * 
	 * @param strDate
	 *            日期
	 * @param dateFormat
	 *            日期格式
	 * @return 日期
	 * @author dengyb
	 */
	public static java.util.Date toUtilDateByPattern(String strDate) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			// 寰楀埌鏃ユ湡
			if (strDate != null && !strDate.equals("")) {
				if (strDate.split("/").length > 1) {
					dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				}
				if (strDate.split("-").length > 1) {
					dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				}
			}
			return dateFormat.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new java.util.Date(0);
	}

	/**
	 * 
	 * @ClassName: DateUtil.java
	 * @Description: 得到两日期相差几个月，精确到分
	 * @author: 陈军圣
	 * @date: 2013-7-24上午8:51:39
	 * @param startDate
	 * @param endDate
	 * @return 日期月份相差跨度数
	 */
	public static long getMonth(String startDate, String endDate) {
		Date startDate1 = toUtilDateByPattern(startDate);
		// 开始时间与今天相比较
		Date endDate1 = toUtilDateByPattern(endDate);
		return getMonth(startDate1, endDate1);
	}

	/**
	 * 
	 * @ClassName: DateUtil.java
	 * @Description: 得到两日期相差几个月，精确到分
	 * @author: 陈军圣
	 * @date: 2013-7-15
	 * @param startDate
	 * @param endDate
	 * @return 日期月份相差跨度数
	 */
	public static long getMonth(Date startDate, Date endDate) {
		long monthday;
		try {
			Calendar starCal = Calendar.getInstance();
			starCal.setTime(startDate);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);

			int startYear = starCal.get(Calendar.YEAR);
			int startMonth = starCal.get(Calendar.MONTH);
			int startDay = starCal.get(Calendar.DATE);

			int endYear = endCal.get(Calendar.YEAR);
			int endMonth = endCal.get(Calendar.MONTH);
			int endDay = endCal.get(Calendar.DATE);

			monthday = ((endYear - startYear) * 12 + (endMonth - startMonth));

			int startHour = starCal.get(Calendar.HOUR_OF_DAY);
			int startMi = starCal.get(Calendar.MINUTE);

			int endHour = endCal.get(Calendar.HOUR_OF_DAY);
			int endMi = endCal.get(Calendar.MINUTE);

			if (startHour < endHour) {
				endDay++;
			} else if (startHour == endHour) {
				if (startMi < endMi) {
					endDay++;
				}
			}

			if (startDay > endDay && monthday > 0) {
				monthday = monthday - 1;
			} else if (startDay < endDay) {
				monthday += 1;
			}
			return monthday;
		} catch (Exception e) {
			monthday = 0;
			e.printStackTrace();
		}
		return monthday;
	}

	/**
	 * 月份枚举 月份英文三字码
	 * 
	 * @author Yangjinbo
	 * @since 1.0
	 */
	public enum Month {
		JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;
		public static Month getMonth(String month) {
			return valueOf(month.toUpperCase());
		}
	}

	/**
	 * 把月份从三字码转换成数字 如一月：JAN->01
	 * 
	 * @param monthCode
	 * @return
	 */
	public static String monthCode2Num(String monthCode) {
		switch (Month.getMonth(monthCode)) {
		case JAN:
			monthCode = "01";
			break;
		case FEB:
			monthCode = "02";
			break;
		case MAR:
			monthCode = "03";
			break;
		case APR:
			monthCode = "04";
			break;
		case MAY:
			monthCode = "05";
			break;
		case JUN:
			monthCode = "06";
			break;
		case JUL:
			monthCode = "07";
			break;
		case AUG:
			monthCode = "08";
			break;
		case SEP:
			monthCode = "09";
			break;
		case OCT:
			monthCode = "10";
			break;
		case NOV:
			monthCode = "11";
			break;
		case DEC:
			monthCode = "12";
			break;
		default:
			break;
		}
		return monthCode;
	}

	/**
	 * 
	 * @ClassName: DateUtil
	 * @Description: 根据传入来的日期和小时分钟的字符串计算时差
	 * @author: chenjunsheng
	 * @date: 2013-8-7
	 * @param date
	 *            某个日期
	 * @param hhMM
	 *            日期加上或者减去小时分钟，格式是+hhMM或者-hhMM，默认是+
	 * @return
	 */
	public static Date calEquat(Date date, String hhMM) {
		String sign = hhMM.substring(0, 1);
		int hour = 0;
		int minute = 0;

		if ("-".equals(sign)) {
			hour = 0 - Integer.parseInt(hhMM.substring(1, 3));
			minute = 0 - Integer.parseInt(hhMM.substring(3, 5));
		} else if ("+".equals(sign)) {
			hour = Integer.parseInt(hhMM.substring(1, 3));
			minute = Integer.parseInt(hhMM.substring(3, 5));
		}
		return calEquat(date, hour, minute, 0);
	}

	/**
	 * 
	 * @ClassName: DateUtil.java
	 * @Description: 根据传入来的日期和小时分钟计算时差
	 * @author: chenjunsheng
	 * @date: 2013-8-7
	 * @param date
	 * @param hours
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date calEquat(Date date, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		calendar.add(Calendar.MINUTE, minute);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 
	 * @ClassName: DateUtil
	 * @Description: 获取当前日期（时间部分为0）
	 * @author: chenjunsheng
	 * @date: 2013-9-10
	 * @return
	 */
	public static Date nowDate() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * @description:格式化日期字符串
	 * @author: andy
	 * @createDate: 2016-10-20
	 * @param dateStr
	 * @param pattern
	 * @return:
	 */
	public static String strFormant(String dateStr, String pattern,String patternNew) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		SimpleDateFormat sdfNew = new SimpleDateFormat(patternNew);
		dateStr = sdfNew.format(date);
		return dateStr;
	}
	
	/**
	 * @description:格式化日期字符串
	 * @author: andy
	 * @createDate: 2016-10-20
	 * @param dateStr
	 * @param pattern
	 * @return:
	 */
	public static String strFormant(String dateStr, String pattern,String patternNew,Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		SimpleDateFormat sdfNew = new SimpleDateFormat(patternNew,locale);
		dateStr = sdfNew.format(date);
		return dateStr;
	}
}
