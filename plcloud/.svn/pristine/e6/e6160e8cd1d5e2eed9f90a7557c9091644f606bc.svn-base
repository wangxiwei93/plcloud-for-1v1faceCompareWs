package com.routon.pmax.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Title: DateUtil
 * </p>
 * <p>
 * Description: 日期类型操作工具类
 *  
 * @version 1.0
 */

public class DateUtil
{
    /**
     * 获得当前日期分串
     * 
     * @param formatString
     *            日期的格式串，例如：yyyy-MM-dd HH:mm:ss
     * @return 日期串
     */
    public final static String getFormatCurrentDate(String formatString)
    {
        Date currentDate = new Date();
        SimpleDateFormat dateFormatCurrentYear = new SimpleDateFormat(formatString);

        return dateFormatCurrentYear.format(currentDate);
    }

    /**
     * 获得当前日期延迟多少天的日期串
     * 
     * @param formatString
     *            日期的格式串，例如：yyyy-MM-dd HH:mm:ss
     * @param days
     *            延迟的天数
     * @return 日期串
     */
    public final static String getFormatCurrentDate(String formatString, int days)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, days);

        return sdf.format(cal.getTime());
    }

    /**
     * 获得指定日期分串
     * 
     * @param date
     *            指定的日期
     * @param formatString
     *            日期的格式串，例如：yyyy-MM-dd HH:mm:ss
     * @return 日期串
     */
    public final static String getFormatDate(Date date, String formatString)
    {
    	if (date == null)
    		return "";
    	
        SimpleDateFormat dateFormatCurrentYear = new SimpleDateFormat(formatString);

        return dateFormatCurrentYear.format(date);
    }

    /**
     * 根据传入串获得指定日期对
     * 
     * @param inputDate
     *            日期串
     * @param formatString
     *            日期的格式串，例如：yyyy-MM-dd HH:mm:ss
     * @return 日期
     * @throws BadInputException
     */
    public final static java.sql.Timestamp getDateTimestamp(String inputDate, String formatString)
        throws BadInputException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);

        try
        {
            java.util.Date date = dateFormat.parse(inputDate);

            return new java.sql.Timestamp(date.getTime());
        }
        catch (Exception e)
        {
            throw new BadInputException("DateUtil.getFormatDate 传入日期格式串非法");
        }
    }
    
    /**
     * 根据传入串获得指定日期对
     * 
     * @param inputDate
     *            日期串
     * @param formatString
     *            日期的格式串，例如：yyyy-MM-dd HH:mm:ss
     * @return 日期
     * @throws BadInputException
     */
    public final static java.sql.Timestamp getDateTime(String inputDate, String formatString)
    throws BadInputException
    {
	    SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
	    java.sql.Timestamp time = null;
	    
	    try
	    {
	        java.util.Date date = dateFormat.parse(inputDate);
	
	          time = new java.sql.Timestamp(date.getTime());
	    }
	    catch (Exception e)
	    {
	    	time = new java.sql.Timestamp(new Date().getTime());
	        //throw new BadInputException("DateUtil.getFormatDate 传入日期格式串非法");
	        
	    }
	    return time;
    }

    /**
     * <p>
     * 功能: 判断输入年份是否是闰/p>
     * <p>
     * 参数: String year:传入年份
     * </p>
     * <p>
     * 时间: 2004-01-13 pm2
     * </p> @
     */
    public static void isLeapYear(String year) throws BadInputException
    {
        try
        {
            int yearInt = StringUtil.getInteger(year, ">", 0);

            if ((yearInt % 4 == 0 && yearInt % 100 != 0) || (yearInt % 400 == 0))
            {
                return;
            }
            else
            {
                throw new BadInputException("传入年份非法!");
            }
        }
        catch (BadInputException ex)
        {
            throw new BadInputException("传入年份非法!");
        }
    }

    /**
     * <p>
     * 功能: 检查是否合法日/p>
     * <p>
     * 参数: String year:传入年份字符/p>
     * <p>
     * 参数: String month:传入月份字符/p>
     * <p>
     * 参数: String day:传入日期字符/p>
     * <p>
     * 时间: 2004-01-13 pm2
     * </p> @
     */
    public static void isDateValid(String year, String month, String day) throws BadInputException
    {
        try
        {
            int yearInt = StringUtil.getInteger(year, ">", 0);
            int monthInt = StringUtil.getInteger(month, ">", 0);
            int dayInt = StringUtil.getInteger(day, ">", 0);

            Calendar calendar1 = Calendar.getInstance();

            try
            {
                calendar1.set(yearInt, monthInt - 1, dayInt, 0, 0, 0);

                if (monthInt >= 1 && monthInt <= 12 && dayInt >= 1 && dayInt <= 31)
                {
                    if ((monthInt == 2 || monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt == 11)
                        && monthInt == 31)
                    {
                        throw new BadInputException("日期输入非法!");
                    }
                    else
                    {
                        try
                        {
                            isLeapYear(year);

                            if (monthInt == 2 && dayInt >= 30)
                            {
                                throw new BadInputException("日期输入非法!");
                            }
                        }
                        catch (BadInputException ex)
                        {
                            if (monthInt == 2 && dayInt >= 29)
                            {
                                throw new BadInputException("日期输入非法!");
                            }
                        }
                    }
                }
                else
                {
                    throw new BadInputException("日期输入非法!");
                }
            }
            catch (Exception e)
            {
                throw new BadInputException("日期输入非法!");
            }
        }
        catch (BadInputException ex)
        {
            throw new BadInputException("日期输入非法!");
        }

    }

    /**
     * <p>
     * 功能: 检查起始日期是否小于截止日/p>
     * <p>
     * 参数: String startYear:传入起始年份字符/p>
     * <p>
     * 参数: String startMonth:传入起始月份字符/p>
     * <p>
     * 参数: String startDay:传入起始日期字符/p>
     * <p>
     * 参数: String endYear:传入截止年份字符/p>
     * <p>
     * 参数: String endMonth:传入截止月份字符/p>
     * <p>
     * 参数: String endDay:传入截止日期字符/p>
     * <p>
     * 时间: 2004-01-14 am8
     * </p>
     */
    public static void isStartDateBeforeEndDate(String startYear, String startMonth,
        String startDay, String endYear, String endMonth, String endDay) throws BadInputException
    {

        try
        {
            isDateValid(startYear, startMonth, startDay);
            isDateValid(endYear, endMonth, endDay);
        }
        catch (BadInputException ex)
        {
            throw new BadInputException("日期输入非法!");
        }
        try
        {
            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar3 = Calendar.getInstance();

            calendar2.set(Integer.parseInt(startYear), Integer.parseInt(startMonth) - 1, Integer
                .parseInt(startDay), 0, 0, 0);
            calendar3.set(Integer.parseInt(endYear), Integer.parseInt(endMonth) - 1, Integer
                .parseInt(endDay), 0, 0, 0);

            if (calendar3.before(calendar2))
            {
                throw new BadInputException("日期输入非法!");
            }
        }
        catch (Exception e)
        {
            throw new BadInputException("日期输入非法!");
        }
    }

    /**
     * 返回日期间隔天数
     * 
     * @param g1
     *            开始日期
     * @param g2
     *            结束日期
     * @return 间隔的天数
     */
    public static int getDays(GregorianCalendar g1, GregorianCalendar g2)
    {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;

        if (g2.after(g1))
        {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        }
        else
        {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }

        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        gc1.clear(Calendar.MINUTE);
        gc1.clear(Calendar.HOUR_OF_DAY);

        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        gc2.clear(Calendar.MINUTE);
        gc2.clear(Calendar.HOUR_OF_DAY);

        while (gc1.before(gc2))
        {
            gc1.add(Calendar.DATE, 1);
            elapsed++;
        }

        return elapsed;
    }

    /**
     * 返回日期间隔月数
     * 
     * @param g1
     *            开始日期
     * @param g2
     *            结束日期
     * @return 间隔的月数
     */
    public static int getMonths(GregorianCalendar g1, GregorianCalendar g2)
    {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;

        if (g2.after(g1))
        {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        }
        else
        {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }

        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        gc1.clear(Calendar.MINUTE);
        gc1.clear(Calendar.HOUR_OF_DAY);
        gc1.clear(Calendar.DATE);

        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        gc2.clear(Calendar.MINUTE);
        gc2.clear(Calendar.HOUR_OF_DAY);
        gc2.clear(Calendar.DATE);

        while (gc1.before(gc2))
        {
            gc1.add(Calendar.MONTH, 1);
            elapsed++;
        }

        return elapsed;
    }

    /**
     * 返回日期加减后的新日期
     * 
     * @param date
     *            输入日期
     * @param formatString
     *            加减项
     * @param num
     *            加减数
     * @return 日期串
     */
    public static String getNewDate(Date date, String formatString, int num)
    {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        int i = 5;

        if (formatString.equals("Y"))
        {
            i = 1; //
        }
        if (formatString.equals("M"))
        {
            i = 2; //
        }
        if (formatString.equals("W"))
        {
            i = 3; //
        }
        if (formatString.equals("D"))
        {
            i = 5; //
        }

        gc.setTime(date);
        gc.add(i, num);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));

        return sf.format(gc.getTime());
    }

    /**
     * 获取给定日期的时间串格式为HH:mm:ss
     * 
     * @param date
     *            给定的日期
     * @return 时间串
     */
    public static String getTimeString(Date date)
    {
        DateFormat timestampFormatter = new SimpleDateFormat("HH:mm:ss");

        if (date == null)
        {
            return "";
        }

        return timestampFormatter.format(date);
    }

    /**
     * 获取给定日期的yyyy-MM-dd HH:mm:ss字符串表示
     * 
     * @param date
     *            给定的日期
     * @return 日期串
     */
    public static String getDateString(Date date)
    {
        DateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (date == null)
        {
            return "";
        }

        return timestampFormatter.format(date);
    }

    /**
     * 获取给定日期的yyyy-MM-dd/yyyy-MM-dd HH:mm:ss字符串表示
     * 
     * @param date
     *            给定的日期
     * @param showTime
     *            ture 表示显示日期和时间串，false 表示只显示日期串
     * @return 日期串
     */
    public static String getDateString(Date date, boolean showTime)
    {
    	if (date == null)
    		return "";
    	
        String strDateFormat = getDateString(date);

        if (!showTime)
        {
            strDateFormat = strDateFormat.substring(0, 10);
        }

        return strDateFormat;
    }

    /**
     * 获取给定日期的yyyyMMdd字符串表示
     * 
     * @param date
     *            给定的日期
     * @return 日期串
     */
    public static String getDateSpecialStr(Date date)
    {
        DateFormat timestampFormatter = new SimpleDateFormat("yyyyMMdd");

        if (date == null)
        {
            return "";
        }

        return timestampFormatter.format(date);
    }
    
    /**
     * 
     * @param sec
     * @return
     */
    public static String getZHtimelengthBySec(int sec){
    	String returnvalue = "";
    	int h = 0;
	 	int m = 0; 
	 	int s = 0;
	 	h = sec/3600;
	 	m = sec%3600/60;
	 	s = sec%3600%60;
	 	
	 	if(h != 0){
	 		returnvalue += h + "小时";
	 	}
	 	if(m != 0){
	 		returnvalue += m + "分钟";
	 	}
	 	if(s != 0){
	 		returnvalue += s + "秒";
	 	}
	 	if(!StringUtils.isNotBlank(returnvalue)){
	 		returnvalue = "0秒";
	 	}
	 	
	 	return returnvalue;
    }
    
    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeek(Date date) {
     // 再转换为时间
     
     Calendar c = Calendar.getInstance();
     c.setTime(date);
     // int hour=c.get(Calendar.DAY_OF_WEEK);
     // hour中存的就是星期几了，其范围 1~7
     // 1=星期日 7=星期六，其他类推
     return new SimpleDateFormat("EEEE").format(c.getTime());
    }
    public static String getWeekStr(Date date){
     String str = "";
     str = getWeek(date);
     if("1".equals(str) || "sunday".equalsIgnoreCase(str)){
      str = "星期日";
     }else if("2".equals(str)|| "Monday".equalsIgnoreCase(str)){
      str = "星期一";
     }else if("3".equals(str)|| "Tuesday".equalsIgnoreCase(str)){
      str = "星期二";
     }else if("4".equals(str)|| "Wednesday".equalsIgnoreCase(str)){
      str = "星期三";
     }else if("5".equals(str)|| "Thursday".equalsIgnoreCase(str)){
      str = "星期四";
     }else if("6".equals(str)|| "Friday".equalsIgnoreCase(str)){
      str = "星期五";
     }else if("7".equals(str)|| "Saturday".equalsIgnoreCase(str)){
      str = "星期六";
     }
     return str;
    } 
    
    /**
     * 两个时间之间的间隔天数
     * @param startday
     * @param endday
     * @return
     */
    public static int getIntervalDays(Date startday,Date endday){
    	
    	if(startday.after(endday)){
    		Date cal = startday;
    		startday = endday;
    		endday = cal;
    	}
    	
    	long sl = startday.getTime();
    	long el = endday.getTime();
    	long ei = el-sl;
    	
    	return (int)(ei/(1000*60*60*24));
    }	
    
    /**
     * 取得两个日期之间的时间差
     * @param time1  日期一
     * @param time2  日期二
     * @param field  类型,比如年月日时分秒
     * @return 返回间隔
     */
    public static int fieldDifference(long time1, long time2, int field) {   
        if (time1 == time2) {   
            return 0;   
        } else if (time1 > time2) {   
            // 确保time1比time2小   
            return fieldDifference(time2, time1, field);   
        }   
        // 下面清除不要参与比较的内容   
        Calendar cal1 = Calendar.getInstance();   
        cal1.setLenient(false);   
        cal1.setTimeInMillis(time1);   
        Calendar cal2 = Calendar.getInstance();   
        cal2.setLenient(false);   
        cal2.setTimeInMillis(time2);   
        for (int x = 0; x < Calendar.FIELD_COUNT; x++) {   
            if (x > field) {   
                cal1.clear(x);   
                cal2.clear(x);   
            }   
        }   
        // 重新设定初始值   
        time1 = cal1.getTimeInMillis();   
        time2 = cal2.getTimeInMillis();   
        long ms = 0;   
        int min = 0;// 下限,从0开始   
        int max = 1;// 每次所加的值,第一次加1   
        while (true) {   
            // 因为max的值都是相对time1而言,故每次都是从time1开始而不是ms   
            cal1.setTimeInMillis(time1);   
            cal1.add(field, max);// 将field对应的字段加上max的值   
            ms = cal1.getTimeInMillis();   
            if (ms == time2) {   
                // 两个时间之间相差的值为max   
                min = max;   
                break;   
            } else if (ms > time2) {   
                // 超过后,则差值肯定小于max   
                break;   
            } else {   
                // 仍然小于time2,继续增大max,直到ms>=time2为止   
                max <<= 1;   
            }   
        }   
        // 上面的操作中没有找到准确的值,接下来使用二分查找以准确找出差值   
        while (max > min) {   
            cal1.setTimeInMillis(time1);   
            int t = (min + max) >>> 1;   
            cal1.add(field, t);   
            ms = cal1.getTimeInMillis();   
            if (ms == time2) {   
                min = t;   
                break;   
            } else if (ms > time2) {   
                max = t;   
            } else {   
                min = t;   
            }   
        }   
        return min;   
    }
    /**
     * 功能：判断输入年份是否为闰年<br>
     * @param year
     * @return 是：true 否：false
     * @author pure
     */
   public static boolean leapYear(int year) {  
       boolean leap;  
       if (year % 4 == 0) {  
           if (year % 100 == 0) {  
               if (year % 400 == 0) leap = true;  
                   else leap = false;  
               }  
           else leap = true;  
       }  
       else leap = false;  
       return leap;  
   }   
    /**
     * 功能：得到当前月份月底 格式为：xxxx-yy-zz (eg: 2007-12-31)
     * @return String
     * @author pure
     **/
   public static String thisMonthEnd(Date date) {  
       String strY = null;  
       String strZ = null;  
       Calendar localTime = Calendar.getInstance();
       localTime.setTime(date);  
       boolean leap = false;  
       int x = localTime.get(Calendar.YEAR);  
       int y = localTime.get(Calendar.MONTH) + 1;  
       if (y == 1 || y == 3 || y == 5 || y == 7 || y == 8 || y == 10 || y == 12) {  
           strZ = "31";  
       }  
       if (y == 4 || y == 6 || y == 9 || y == 11) {  
           strZ = "30";  
       }  
       if (y == 2) {  
           leap = leapYear(x);  
           if (leap) {  
               strZ = "29";  
           }else {  
               strZ = "28";  
           }  
       }  
       strY = y >= 10 ? String.valueOf(y) : ("0" + y);  
       
       return x + "-" + strY + "-" + strZ;  
   }
   /**
    * 功能：得到当前月份下一个月的月初 格式为：xxxx-yy-zz (eg: 2007-12-1)
    * @return String
    * @author pure
    **/
  public static String thisMonthNextMonthHead(Date date) {  
      String strY = null;  
      Calendar localTime = Calendar.getInstance();
      localTime.setTime(date);  
      int x = localTime.get(Calendar.YEAR);  
      int y = localTime.get(Calendar.MONTH) + 2;  
      strY = y >= 10 ? String.valueOf(y) : ("0" + y);
      return x + "-" + strY + "-01";   
  }
    public static void main(String[] args) {
        try {
            //System.out.println(fieldDifference(getDateTime("1985-12-25 08:12:12", "yyyy-MM-dd HH:mm:ss").getTime(),getDateTime("1985-12-25 12:12:12", "yyyy-MM-dd HH:mm:ss").getTime(),Calendar.HOUR));
        System.out.println(thisMonthNextMonthHead(new Date()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
