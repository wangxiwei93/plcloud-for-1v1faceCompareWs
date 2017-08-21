package com.routon.pmax.common.utils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/** 
 * <p>
 * Title: StringUtil
 * </p>
 * <p>
 * Description: 用于字符串处理的工具类
 * 
 * @version 1.0
 */

public class StringUtil
{
    /**
     * 当前页面的URL
     */
    @SuppressWarnings("unused")
    private String             currentUrl;

    /**
     * 风格
     */
    @SuppressWarnings("unused")
    private String             style;

    /**
     * 在"上一页"两边是否加上"["和"]"
     */
    @SuppressWarnings("unused")
    private boolean            useSquareBrackets;

    /**
     * 客户端请求
     */
    @SuppressWarnings("unused")
    private HttpServletRequest request;

    /**
     * 页面输出对象
     */
    @SuppressWarnings("unused")
    private JspWriter          out;

    /**
     * 
     */
    private static HashMap     cache = new HashMap();

    /**
     * 构造函数
     */
    public StringUtil()
    {
    }

    /**
     * 构造函数
     * 
     * @param request
     *            客户端请求
     * @param out
     *            页面输出对象
     */
    public StringUtil(HttpServletRequest request, JspWriter out)
    {
        this.request = request;
        this.out = out;
        currentUrl = request.getRequestURL().toString();
    }

    /**
     * 返回定长字符串,不足用指定字符前补足
     * 
     * @param inputstring
     *            传入字符串
     * @param length
     *            指定返回长度
     * @param complementarityChar
     *            补足字符
     * @return 处理后字符串
     * @throws BadInputException
     */
    public static String getRestrictLengthString(String inputstring, int length,
        char complementarityChar) throws BadInputException
    {
        String retValue = "";
        String complementarityString = "";
        int complementarityLength = -1;
      
        try
        {
            if (length < inputstring.length())
            {
                throw new BadInputException("输入长度非法!");
            }

            complementarityLength = length - inputstring.length();

            for (int i = 1; i <= complementarityLength; i++)
            {
                complementarityString += complementarityChar;
            }

            retValue = complementarityString + inputstring;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return retValue;
    }

    /**
     * 判断字符串是否为空(静态方法)<br>
     * 
     * @param 字符串
     * @return true or false
     * @author 林涛
     * @version 1.0
     */
    public static boolean isSpace(String s)
    {
        if (s == null)
        {
            return true;
        }

        return s.trim().equals("");
    }

    /**
     * 字符串转换(静态方法)<br>
     * 将null转换为空字符串<br>
     * 去掉字符串首尾的空格
     * 
     * @param 待转换的字符串
     * @return 转换后的字符串
     * @author 林涛
     * @version 1.0
     */
    public static String convNull(String s)
    {
        if (s == null)
        {
            return "";
        }

        return s.trim();
    }
    
    /**
     * 字符串转义(静态方法)<br>
     * 对sql查询语句中的敏感符号进行转义<br>
     * 去掉字符串首尾的空格
     * 
     * @param 待转义的字符串
     * @return 转义后的字符串
     * @author 张犇
     * @version 1.0
     */
    public static String tranMean(String s)
    {
    	StringBuffer sb = new StringBuffer();
        if (s == null){
            return "";
        }else{
        	Pattern p = Pattern.compile("(\\%)|(\\')|(\")");
			Matcher m = p.matcher(s.trim());
			while (m.find()) {
				m.appendReplacement(sb,"\\\\" + m.group());
			}
			m.appendTail(sb);
        }
        return sb.toString();
    }

    /**
     * 四舍五入
     * 
     * @param double
     *            待转换的浮点数
     * @param int
     *            保留的小数点位数
     * @return double 转换后的浮点数
     * @author 林涛
     * @version 1.0
     */
    public static double round(double d, int i)
    {
        double doPow = Math.pow(10, i);

        return ((double) Math.round(d * doPow)) / doPow;
    }

    /**
     * 得到指定数目的HTML空格
     * 
     * @param 指定的空格数目
     * @return 指定数目的HTML空格
     * @author 林涛
     * @version 1.0
     */
    public static String space(int i)
    {
        String s = "";

        for (int j = 0; j < i; j++)
        {
            s += "&nbsp;";
        }

        return s;
    }

    /**
     * 在字符串右边填充空格
     * 
     * @param s
     *            待填充的字符串
     * @param i
     *            字符串总长度
     * @return 填充后的字符串
     * @author 林涛
     * @version 1.0
     */
    public static String fillRight(String s, int i, String attach)
    {
        if (s == null)
        {
            return "";
        }
        
        if (s.equals(""))
        {
            return space(i);
        }
        
        if (s.getBytes().length >= i)
        {
            return s;
        }

        return s + attach + space(i - s.getBytes().length);
    }

    /**
     * 在字符串左边填充空格
     * 
     * @param s
     *            待填充的字符串
     * @param i
     *            字符串总长度
     * @return 填充后的字符串
     * @author 林涛
     * @version 1.0
     */
    public static String fillLeft(String s, int i)
    {
        if (s == null)
        {
            return "";
        }
        
        if (s.equals(""))
        {
            return space(i);
        }
        
        if (s.getBytes().length >= i)
        {
            return s;
        }

        return space(i - s.getBytes().length) + s;
    }

    /**
     * 检查某个字符串是否为数字
     * 
     * @param s
     *            待检查的字符串
     * @return true or false
     * @author 林涛
     * @version 1.0
     */
    @SuppressWarnings("unused")
    public static boolean isNumeric(String s)
    {
        try
        {
            double doNumber = Double.parseDouble(s);
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    /**
     * 根据行号列号获取其背景色
     * 
     * @param rownum
     *            行号
     * @param colnum
     *            列号
     * @return 某行列的背景色
     */
    public static String strSetColor(int rownum, int colnum)
    {
        String setcolor = "";

        if ((rownum % 2) != 0 && (colnum % 2) != 0)
        { // 单数行、单数列
            setcolor = "#f0f0ff";
        }
        else if ((rownum % 2) != 0 && (colnum % 2) == 0)
        { // 单数行、双数列
            setcolor = "#ececff";
        }
        else if ((rownum % 2) == 0 && (colnum % 2) != 0)
        { // 双数行、单数列
            setcolor = "#eeeeee";
        }
        else
        { // 双数行、双数列
            setcolor = "#e4e4e4";
        }

        return setcolor;
    }

    /**
     * 字符串转换为数字转换(静态方法)<br>
     * 将null转换为0<br>
     * 去掉字符串首尾的空格
     * 
     * @param 待转换的字符串
     * @return 转换后的字符串
     * @author 潘伟
     * @version 1.0
     */
    public static int convNullNum(String s)
    {
        if (s == null)
        {
            return 0;
        }

        return Integer.parseInt(s.trim());
    }

    /**
     * 重新对字符进行编码
     * 
     * @param str
     *            待编码串
     * @return 已编码串
     */
    public static String getGB(String str)
    {
        try
        {
            byte array[] = str.getBytes("ISO-8859-1");
            
            str = new String(array);
        }
        catch (Exception e)
        {
        }

        return str;
    }

    /**
     * 从URL中获取参数
     * 
     * @param: request 客户端请求
     * @param: exceptionParamNames 排除在外的参数
     */
    public static String getParamsFromCurrentURL(HttpServletRequest request,
        String exceptionParamNames)
    {
        String params = "";
        
        Enumeration e = request.getParameterNames();

        outer: while (e.hasMoreElements())
        {
            String key = (String) e.nextElement();
            StringTokenizer st = new StringTokenizer(exceptionParamNames, ",");

            while (st.hasMoreTokens())
            {
                String exceptionName = st.nextToken();
                
                if (key.equals(exceptionName))
                {
                    continue outer;
                }
            }
            
            params += "&" + key + "=" + request.getParameter(key);
        }

        return params;
    }

    /**
     * 从URL中获取参数(多参数不显示处理)
     * 
     * @param: request 客户端请求
     * @param: exceptionParamNames 排除在外的参数
     */
    @SuppressWarnings("unused")
    public static String getParamsFromCurrentURLMore(HttpServletRequest request,
        String exceptionParamNames)
    {
        String params = "";
        String[] exceptionParamNamesList; // 终端ID序列
        String value = null;
        Enumeration e = request.getParameterNames();
        
        exceptionParamNamesList = exceptionParamNames.split(",");

        outer: while (e.hasMoreElements())
        {
            String key = (String) e.nextElement();
            StringTokenizer st = new StringTokenizer(exceptionParamNames, ",");

            while (st.hasMoreTokens())
            {
                String exceptionName = st.nextToken();
                
                for (int i = 0; i < exceptionParamNamesList.length; i++)
                {
                    if (key.equals(exceptionParamNamesList[i]))
                    {
                        continue outer;
                    }
                }
            }

            value = request.getParameter(key);

            // params += "&" + key + "=" + value; //* by jiangyl @2007.11.30
            try
            {
                params += "&" + key + "=" + URLEncoder.encode(value, "utf-8");
            }
            catch (Exception ex)
            {
                System.out.println("URLEncoder.encode@getParamsFromCurrentURLMore Exception:"
                    + ex.getMessage() + ", value:" + value);
                params += "&" + key + "=" + value;
            }
        }

        return params;
    }

    /**
     * 获得金额串,小数点后2位
     * 
     * @param price
     *            待处理金额
     * @return 处理后金额
     */
    public static String getPrice(String price)
    {
        String returnVal = price;

        if (price.indexOf(".") != -1)
        {
            if (price.length() - 1 - price.indexOf(".") == 1)
            {
                returnVal += "0";
            }
        }

        return returnVal;
    }

    /**
     * 判断输入是否双精度数
     * 
     * @param inputString
     *            传入字符串
     * @param round
     *            四舍五入保留位数
     * @return 双精度数
     * @throws BadInputException
     */
    public static double getDoubleRound(String inputString, int round) throws BadInputException
    {
        double retVal = -1.0;
        double doPow = -1.0;

        try
        {
            retVal = Double.parseDouble(inputString);
            doPow = Math.pow(10, round);
            retVal = ((double) Math.round(retVal * doPow)) / doPow;
        }
        catch (Exception e)
        {
            throw new BadInputException("传入串'" + inputString + "'转换非法!");
        }

        return retVal;
    }

    /**
     * 判断输入是否符合条件的整数
     * 
     * @param inputString
     *            传入字符串
     * @param condition
     *            逻辑判断条件
     * @param boundary
     *            边界值
     * @return 符合条件的整数
     * @throws BadInputException
     */
    public static int getInteger(String inputString, String condition, int boundary)
        throws BadInputException
    {
        int retVal = -1;

        try
        {
            retVal = Integer.parseInt(inputString);

            if (condition.equals(">"))
            {
                if (!(retVal > boundary))
                {
                    throw new BadInputException("传入串'" + inputString + "'转换非法!");
                }
            }
            else if (condition.equals("<"))
            {
                if (!(retVal < boundary))
                {
                    throw new BadInputException("传入串'" + inputString + "'转换非法!");
                }
            }
            else if (condition.equals(">="))
            {
                if (!(retVal >= boundary))
                {
                    throw new BadInputException("传入串'" + inputString + "'转换非法!");
                }
            }
            else if (condition.equals("<="))
            {
                if (!(retVal <= boundary))
                {
                    throw new BadInputException("传入串'" + inputString + "'转换非法!");
                }
            }
        }
        catch (Exception e)
        {
            throw new BadInputException("传入串'" + inputString + "'转换非法!");
        }

        return retVal;
    }

    /**
     * 判断url请求是否应该被过滤, true: 需要过滤; false: 不需要过滤
     * 
     * @param url
     * @param excludeRegExp
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean needFilter(String url, String excludeRegExp, String name)
    {
    	String[] excludes = null;
    	
    	if (excludeRegExp == null || excludeRegExp.equals(""))
        {
            return true;
        }

        // 如果是去iport,则不应该过滤
        excludes = (String[]) cache.get(name);
        
        if (excludes == null)
        {
            excludes = excludeRegExp.split("\\|");
            cache.put(name, excludes);
        }

        for (int i = 0; i < excludes.length; i++)
        {
            if (url.indexOf(excludes[i]) >= 0)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 把分转为元
     * 
     * @param fen
     *            以分为单位的值
     * @return 以元为单位的精确值
     */
    public static String fen2yuan(String fen)
    {
        BigDecimal money = new BigDecimal(fen);
        
        // return money.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString();
        return money.movePointLeft(2).toString();
    }

    /**
     * 把分转为元
     * 
     * @param yuan
     *            以元为单位的值
     * @return 以分为单位的精确值
     */
    public static BigDecimal fen2yuan(BigDecimal fen)
    {
        if (fen == null)
        {
            fen = new BigDecimal(0);
        }

        return fen.movePointLeft(2);
    }

    /**
     * 把分转为元
     * 
     * @param yuan
     *            以元为单位的值
     * @return 以分为单位的精确值
     */
    public static String yuan2fen(String yuan)
    {
        BigDecimal money = new BigDecimal(yuan);
        
        // BigDecimal b2 = new BigDecimal("100");
        return money.movePointRight(2).toString();
    }

    /**
     * 把分转为元
     * 
     * @param yuan
     *            以元为单位的值
     * @return 以分为单位的精确值
     */
    public static BigDecimal yuan2fen(BigDecimal yuan)
    {
        if (yuan == null)
        {
            yuan = new BigDecimal(0);
        }
        
        // BigDecimal b2 = new BigDecimal("100");
        return yuan.movePointRight(2);
    }
    
    /**
     * 把字符串截取成固定长度子串的数组
     * 
     * @param s 字符串
     * @param num 固定长度
     * @return 数组
     */
    public static String [] stringToStringArray(String s, int num) {
    	System.out.println(s.length());
    	int residue  = s.length() % num;
    	int count = 0;
    	String[] ss = null;
    	
    	if (residue == 0){
    		count = s.length() / num ;
    	}
    	else {
    		count = s.length() / num + 1;
    	}
    	 
    	ss = new String[count];

    	for (int i= 0; i < count; i++){
	    	if (residue != 0 && i == count - 1){
	    		ss[i] = new String(s.toCharArray(), num*i, residue); 
	    	}
	    	else {
	    		ss[i] = new String(s.toCharArray(), num*i, num);
	    	}
    	} 
    	
    	return ss;
    }
    
	
	/** *//**
     * 判断一个字符串中是否包含一个子串
     * @param str String
     * @param subString String
     * @return boolean
     */
   public static boolean  isIncludeSubString(String str, String subString){
        
	   boolean result = false;
        
	   if (str == null || subString == null){
            
		   return false;
        
	   }
        
	   int strLength = str.length(); 
	   int subStrLength = subString.length();   
	   String tmpStr = null;
        
	   for (int i = 0; i < strLength; i++) {       
		   
		   if (strLength - i < subStrLength) {
                
			   return false;
            
		   }
            
		   tmpStr = str.substring(i, subStrLength + i);
            
		   if (tmpStr.endsWith(subString)){
                
			   return true;
            
		   }
        
	   }
        
	   return result;
    }
   
   	/**
   	 * 拼接
   	 * @param args
   	 * @return
   	 */
   	public static String concat(String...args) {
   		StringBuffer buf = new StringBuffer();
   		if (args != null){   		   		
       		for (String arg : args){
       			buf.append(arg);
       		}
   		}
   		
   		return buf.toString();
   	} 

   	public static String concat(Object...args) {
   		StringBuffer buf = new StringBuffer();
   		if (args != null){   		   		
       		for (Object arg : args){
       			buf.append(arg);
       		}
   		}
   		
   		return buf.toString();
   	}
   	
   	
   	public static String replace(String strSource, String strFrom, String strTo) {    
   	    if (strSource == null) {        
   	    	return null;    
   	    }  
   	    
   	    int i = 0;
   	    if ((i = strSource.indexOf(strFrom, i)) >= 0) {
   	    	char[] cSrc = strSource.toCharArray();
   	    	char[] cTo = strTo.toCharArray();
   	    	int len = strFrom.length();  
   	    	StringBuffer buf = new StringBuffer(cSrc.length);  
   	    	buf.append(cSrc, 0, i).append(cTo);
   	    	i += len;    
   	    	int j = i;       
   	    	while ((i = strSource.indexOf(strFrom, i)) > 0) {  
   	    		buf.append(cSrc, j, i - j).append(cTo);   
   	    		i += len;  
   	    		j = i;        
   	      
   	    	}        
   	    	buf.append(cSrc, j, cSrc.length - j); 
   	      
   	    	return buf.toString(); 
   	    }
   	    
   	    return strSource;
   	 
   	} 

    
    public static void main(String[] args) {
    	String s = "你的访dsffds客数据342654";
    	@SuppressWarnings("unused")
		String ss [] = stringToStringArray(s, 4);
    	System.out.println("xxx");
	}
}
