package com.hunantv.fw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final long DAY_SECONDS = 3600 * 24 * 1000L;

	/**
	 * convert a date string to a Date data with the format default format:
	 * yyyy-MM-dd
	 * 
	 * @param datestr
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date str2Date(String datestr, String format) {
		return str2Date(datestr, format, null);
	}

	public static Date str2Date(String datestr, String format, Date defaultDate) {
		if (datestr == null) {
			return defaultDate;
		}
		if (format == null)
			format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(datestr);
		} catch (ParseException e) {
			return defaultDate;
		}
	}

	public static String long2Datestr(long time, String format) {
		if (format == null || format.trim().equals(""))
			format = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}

	/**
	 * convert a date to a format string default format:yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null)
			return long2Datestr(date.getTime(), format);
		else
			return long2Datestr(new Date().getTime(), format);
	}

	/**
	 * 得到当天指定时分秒的毫秒数
	 * 
	 * @param hour
	 *            int
	 * @param min
	 *            int
	 * @return long
	 */
	public static long getTimeWithHourAndMin(int hour, int min) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(year, month, day, hour, min);
		return cal.getTimeInMillis();
	}

	public static String getCurrentTimeString() {
		return getCurrentTimeString("yyyyMMddhhmmss");
	}

	public static String getCurrentTimeString(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String currTime = sdf.format(new Date());
		return currTime;
	}

	/**
	 * 得到当天指定时分秒的毫秒数
	 * 
	 * @param HHMin
	 *            String 为格式串 HH:min
	 * @return long
	 */
	public static long getTimeWithHourAndMin(String HHMin) {
		HHMin = HHMin.trim();
		int h = Integer.valueOf(HHMin.substring(0, HHMin.indexOf(":")));
		int m = Integer.valueOf(HHMin.substring(HHMin.indexOf(":") + 1, HHMin.length()));
		return getTimeWithHourAndMin(h, m);
	}

	public static long getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static long getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static Date dateBefore(int days) {
		Date date = new Date();
		date.setTime(date.getTime() - days * DAY_SECONDS);
		return date;
	}

	public static Date dateAfter(int days) {
		Date date = new Date();
		date.setTime(date.getTime() + days * DAY_SECONDS);
		return date;
	}

	public static Date before(Date date, int days) {
		Date newDate = new Date();
		newDate.setTime(date.getTime() - days * DAY_SECONDS);
		return newDate;
	}

	public static Date after(Date date, int days) {
		Date newDate = new Date();
		newDate.setTime(date.getTime() + days * DAY_SECONDS);
		return newDate;
	}

	public static void main(String[] args) throws ParseException {

		long time = 1326956252000L;
		Date d = new Date();
		d.setTime(time);
		System.out.println(DateUtil.long2Datestr(time, null));
		System.out.println(DateUtil.date2Str(d, null));
		System.out.println("==============================");
		System.out.println(DateUtil.str2Date("2007-11-19 14:14:59", "yyyy-MM-dd HH:mm:ss").getTime());
	}
}
