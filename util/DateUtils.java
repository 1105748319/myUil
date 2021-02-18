/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hualongdata.dataasset.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 获取过去几天的日期
	 * @param days
	 * 天数
	 * @return
	 */
	public static String getDateString(int days) {
		SimpleDateFormat SDF=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar   cal   =   new   GregorianCalendar(); 
		cal.setTime(new   Date()); 
		cal.add(Calendar.DATE,   -days);
		Date d=cal.getTime();
		return SDF.format(d);
	}
	/**
	 * 获取过去几天的日期
	 * @param days
	 * 天数
	 * @return
	 */
	public static String getDateStringForDay(int days) {
		SimpleDateFormat SDF=new SimpleDateFormat("MM-dd");
		GregorianCalendar   cal   =   new   GregorianCalendar(); 
		cal.setTime(new   Date()); 
		cal.add(Calendar.DATE,   -days);
		Date d=cal.getTime();
		return SDF.format(d);
	}
	/**
	 * 获取过去几天的日期
	 * @param days
	 * 天数
	 * @return
	 */
	public static String getDateStringForDate(int days) {
		SimpleDateFormat SDF=new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar   cal   =   new   GregorianCalendar(); 
		cal.setTime(new   Date()); 
		cal.add(Calendar.DATE,   -days);
		Date d=cal.getTime();
		return SDF.format(d);
	}
	/**
	 * 传入生日获取年龄
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay){
		try {
			 Calendar cal = Calendar.getInstance();  
		        if (cal.before(birthDay)) {  
		            throw new IllegalArgumentException(  
		                    "The birthDay is before Now.It's unbelievable!");  
		        }  
		        int yearNow = cal.get(Calendar.YEAR);  
		        int monthNow = cal.get(Calendar.MONTH);  
		        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
		        cal.setTime(birthDay);  
		  
		        int yearBirth = cal.get(Calendar.YEAR);  
		        int monthBirth = cal.get(Calendar.MONTH);  
		        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
		  
		        int age = yearNow - yearBirth;  
		  
		        if (monthNow <= monthBirth) {  
		            if (monthNow == monthBirth) {  
		                if (dayOfMonthNow < dayOfMonthBirth) age--;  
		            }else{  
		                age--;  
		            }  
		        }  
		        return age;  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
    }
	/** 
     * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前，如果大于一天则用format规定的格式显示 
     *  
     * @author wxy 
     * @param ctime 
     *            时间 
     * @param format 
     *            格式 格式描述:例如:yyyy-MM-dd yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
	public static String showTime(Date ctime, String format) {
        String r = "";  
        if(ctime==null)return r;  
        if(format==null)format="MM-dd HH:mm";  
  
        long nowtimelong = System.currentTimeMillis();  
  
        long ctimelong = ctime.getTime();  
        long result = Math.abs(nowtimelong - ctimelong);  
  
        if(result < 60000){// 一分钟内  
            long seconds = result / 1000;  
            if(seconds == 0){  
                r = "刚刚";  
            }else{  
                r = seconds + "秒前";  
            }  
        }else if (result >= 60000 && result < 3600000){// 一小时内  
            long seconds = result / 60000;  
            r = seconds + "分钟前";  
        }else if (result >= 3600000 && result < 86400000){// 一天内  
            long seconds = result / 3600000;  
            r = seconds + "小时前";  
        }else if (result >= 86400000 && result < 1702967296){// 三十天内  
            long seconds = result / 86400000;  
            r = seconds + "天前";  
        }else{// 日期格式  
            format="MM-dd HH:mm";  
            SimpleDateFormat df = new SimpleDateFormat(format);  
            r = df.format(ctime).toString();  
        }  
        return r;  
    } 
	/**
	 * @param args
	 * @throws ParseException
	 */
	// public static void main(String[] args) throws ParseException {
	// 	System.out.println(formatDate(parseDate("2010/3/6")));
	// 	System.out.println(getDate("yyyy年MM月dd日 E"));
	// 	long time = new Date().getTime()-parseDate("2012-11-19").getTime();
	// 	System.out.println(time/(24*60*60*1000));
	// }
}
