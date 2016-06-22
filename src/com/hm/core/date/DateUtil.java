package com.hm.core.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author liyc
 * @time 2012-1-9 下午11:13:45
 * @annotation
 */
public class DateUtil {
	public static String DateFormat_1 = "yyyy-MM-dd hh:mm:ss";
	public static String DateFormat_24 = "yyyy-MM-dd HH:mm:ss";

	public static void main(String[] args) {
		System.out.println(getCurrentTime() + "-01 00:00:00");
	}


	public static String switchDay(int day) {
		String daystr = day + "";
		if (daystr.length() == 2) {
			return daystr;
		} else {
			return "0" + daystr;
		}
	}

	public static String convertDate(Date date, String format) {
		if (date != null) {
			DateFormat format1 = new SimpleDateFormat(format);
			String s = format1.format(date);
			return s;
		}
		return "";
	}

	public static String getCurrentTimeYM() {
		return convertDate(new Date(), "yyyy-MM");
	}

	public static String getCurrentTime() {
		return convertDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}


	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month)
	{
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static String getWeekNameByNum(int num)
	{	
		String name = null;
		switch (num) {
		case 2:
			name =  "一";
			break;
		case 3:
			name =  "二";
			break;
		case 4:
			name =  "三";
			break;
		case 5:
			name =  "四";
			break;
		case 6:
			name =  "五";
			break;
		case 7:
			name =  "六";
			break;
		case 1:
			name =  "日";
			break;
		default:
			name =  "";
			break;
		}
		return name;
	}
	
	/**
	 * 根据日期获取星期几字符
	 * @param d
	 * @return
	 */
	public static String getWeekNameByDate(Date d)
	{	
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		String name = null;
		switch (ca.get(Calendar.DAY_OF_WEEK)) {
		case 7:
			name =  "六";
			break;
		case 1:
			name =  "日";
			break;
		case 2:
			name =  "一";
			break;
		case 3:
			name =  "二";
			break;
		case 4:
			name =  "三";
			break;
		case 5:
			name =  "四";
			break;
		case 6:
			name =  "五";
			break;
		default:
			name =  "";
			break;
		}
		return name;
	}

	public static String Num2Haizi_Week(int day) {
		switch (day) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		case 0:
			return "星期日";
		default:
			return "";
		}
	}
	
	
	public static String Num2Eng_Week(int day) {
		switch (day) {
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "FriDay";
		case 6:
			return "Saturday";
		case 0:
			return "Sunday";
		default:
			return "";
		}
	}

	public static String Num2Haizi_Week_HTML_Color(int day) {
		switch (day) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "<font color=red>星期六</font>";
		case 0:
			return "<font color=red>星期日</font>";
		default:
			return "";
		}
	}

	public static Date convertString2Date(String str, String formatStr) {
		DateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	// 获取每一个日期，是星期几
	public static String getWeekByDate(Date date) {
		Calendar time = Calendar.getInstance();
		time.clear();
		time.setTime(date);
		int week = time.get(Calendar.DAY_OF_WEEK) - 1;
		return Num2Haizi_Week(week);
	}

	/**
	 * 获取英文日期
	 * @param date
	 * @return
	 */
	public static String  getEngWeekNumByDate(Date date){
		Calendar time = Calendar.getInstance();
		time.clear();
		time.setTime(date);
		int week = time.get(Calendar.DAY_OF_WEEK) - 1;
		return  Num2Eng_Week(week);
	}
	
	// 获取每一个日期，是星期几
	public static String getWeekByDateSingleChar(Date date) {
		Calendar time = Calendar.getInstance();
		time.clear();
		time.setTime(date);
		int week = time.get(Calendar.DAY_OF_WEEK) - 1;
		return getWeekNameByNum(week);
	}

	public static int getWeekByDateTime(Date date) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		return time.get(Calendar.DAY_OF_WEEK)-1;
	}

	/**
	 * 根据日期字符串获取星期
	 * @param dateStr 格式必须为yyyy-MM-dd
	 * @return
	 */
	public static String getWeekByDateStr(String dateStr){
		Calendar time = Calendar.getInstance();
		time.clear();
		time.setTime(convertString2Date(dateStr, "yyyy-MM-dd"));
		int week = time.get(Calendar.DAY_OF_WEEK) - 1;
		return Num2Haizi_Week(week);
	}

	// 获取每一个日期，是星期几
	public static String getWeekByDate_HTML_Color(Date date) {
		Calendar time = Calendar.getInstance();
		time.clear();
		time.setTime(date);
		int week = time.get(Calendar.DAY_OF_WEEK) - 1;
		return Num2Haizi_Week_HTML_Color(week);
	}

	//如将2012-08-15，生成星期三
	public static String getWeekByFormatedDateStr(String s)
	{
		Calendar time = Calendar.getInstance();
		String[] ss = s.split("-");
		time.set(Integer.parseInt(ss[0]), Integer.parseInt(ss[1])-1, Integer.parseInt(ss[2]));
		return getWeekByDate(time.getTime());
	}

	//如将2012-08-15，生成星期三
	public static String getWeekByFormatedDateStr_HTML_Color(String s)
	{
		Calendar time = Calendar.getInstance();
		String[] ss = s.split("-");
		time.set(Integer.parseInt(ss[0]), Integer.parseInt(ss[1])-1, Integer.parseInt(ss[2]));
		return getWeekByDate_HTML_Color(time.getTime());
	}

	// 获取当前日期天数
	public static int getDayNumsOfCurrentMonth() {
		Calendar time = Calendar.getInstance();
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);
		return day;
	}

	// 获取当前时间的星期几
	public static String getWeektimeOfCurrentTime() {
		Calendar time = Calendar.getInstance();
		int week = time.get(Calendar.DAY_OF_WEEK) - 1;
		return Num2Haizi_Week(week);
	}

	// 获取每个日期里，本月的天数
	public static int getDayNumsOfMonth(Date date) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);
		return day;
	}

	public static int getDaysOfYM(int year, int month) {
		Calendar time = Calendar.getInstance();
		time.clear();
		time.set(Calendar.YEAR, year);
		time.set(Calendar.MONTH, month - 1);
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);
		return day;
	}


	public static String AddZero(int i) {
		if (i >= 0 && i <= 9) {
			return "0" + i;
		}
		return String.valueOf(i);

	}

	public static String getFormatedDate(String strs,String tag)
	{
		String[] ss = strs.split(tag);
		String year = ss[0];
		String month = switchDay(Integer.parseInt(ss[1]));
		String day = switchDay(Integer.parseInt(ss[2]));
		return year+"-"+month+"-"+day;
	}

	/**
	 * 获取当前年月日,时分秒字符串
	 * 
	 * @param day
	 * @return
	 */
	public static String getTimeStrHanzi() {
		return convertDate(new Date(), "yyyy/MM/dd HH:mm:ss");
	}

	/**
	 *  获取当前年份总周数
	 * @return
	 */
	public static int getWeeksOfYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar cl = Calendar.getInstance(); 
		int year = cl.get(Calendar.YEAR); 
		try {
			cl.setTime(sdf.parse(year+"-12-31")); 
			int i=cl.get(Calendar.DAY_OF_WEEK);
			cl.setTime(sdf.parse(year+"-12-"+(31-i)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		int week = cl.get(Calendar.WEEK_OF_YEAR);
		return week;
	}
	/**
	 *  获取指定年份总周数
	 * @param year yyyy
	 * @return
	 */
	public static int getWeeksOfYear(int year) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar cl = Calendar.getInstance(); 
		try {
			cl.setTime(sdf.parse(year+"-12-31")); 
			int i=cl.get(Calendar.DAY_OF_WEEK);
			cl.setTime(sdf.parse(year+"-12-"+(31-i)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		int week = cl.get(Calendar.WEEK_OF_YEAR);
		return week;
	}

	public static String getListUpdateStr()
	{
		return new SimpleDateFormat("MM-dd HH:mm").format(new Date(System.currentTimeMillis()));
	}
}
