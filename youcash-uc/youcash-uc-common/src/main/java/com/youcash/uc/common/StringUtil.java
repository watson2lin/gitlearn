package com.youcash.uc.common;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @title: 工具类
 * @description: String类型的工具类
 * @company: youcash
 * @className: StringUtil.java
 * @author: hansheng.lin
 * @createDate: 2013-5-31
 * @updateUser: hansheng.lin
 * @version: 1.0
 */
public class StringUtil {

	/**
	 * @description:字符串替换函数。
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param value
	 *            需要查找替换的字符串
	 * @param key
	 *            被替换的值
	 * @param replaceValue
	 *            替换的值
	 * @return:String
	 */
	public static String replace(String value, String key, String replaceValue) {
		if (value != null && key != null && replaceValue != null) {
			int pos = value.indexOf(key);
			if (pos >= 0) {
				int length = value.length();
				int start = pos;
				int end = pos + key.length();

				if (length == key.length()) {
					value = replaceValue;
				} else if (end == length) {
					value = value.substring(0, start) + replaceValue;
				} else {
					value = value.substring(0, start) + replaceValue
							+ replace(value.substring(end), key, replaceValue);
				}
			}
		}
		return value;
	}

	/**
	 * @description:将双浮点的数字转成字符串
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param doubles
	 *            双浮点的数字
	 * @return:String
	 */
	public static String convertDoubleToString(Double doubles) {
		DecimalFormat to = new DecimalFormat("0.00");
		return String.valueOf(to.format(doubles));
	}

	/**
	 * @description:判断字符串对象是否为空字符串
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param value
	 * @return:String
	 */
	public static boolean isBlank(String value) {
		boolean ret = false;
		if (value != null && value.equals("")) {
			ret = true;
		}
		return ret;
	}

	/**
	 * @description:判断字符串是否为null
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param value
	 * @return:boolean
	 */
	public static boolean isNull(String value) {
		return value == null ? true : false;
	}

	/**
	 * @description:判断字符串是否为空字符串或者null
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param value
	 * @return:boolean
	 */
	public static boolean isNullOrBlank(String value) {
		return isNull(value) || isBlank(value);
	}

	/**
	 * @description:字符串编码函数
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param str
	 * @param srcCode
	 * @param targetCode
	 * @return:String
	 */
	public static String encodeStr(String str, String srcCode, String targetCode) {
		try {
			if (str == null) {
				return null;
			}

			byte[] bytesStr = str.getBytes(srcCode);
			return new String(bytesStr, targetCode);
		} catch (Exception ex) {
			return str;
		}
	}

	/**
	 * @description: 字符串编码函数
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param str
	 * @param targetCode
	 * @return:String
	 */
	public static String encodeStr(String str, String targetCode) {
		try {
			if (str == null) {
				return null;
			}

			byte[] bytesStr = str.getBytes();
			return new String(bytesStr, targetCode);
		} catch (Exception ex) {
			return str;
		}
	}

	/**
	 * @description:把NULL字符串转换成空字符串
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param str
	 * @return:String
	 */
	public static String convertNullToBlank(String str) {
		return str == null ? "" : str;
	}

	/**
	 * @description:从左起取字符串前n位
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param str
	 * @param length
	 * @return:String
	 */
	public static String left(String str, int length) {
		if (str == null) {
			throw new IllegalArgumentException("字符串参数值不能为null");
		}
		if (length < 0) {
			throw new IllegalArgumentException("整型参数长度不能小于0");
		}
		if (str.length() < length) {
			throw new IllegalArgumentException("字符串参数长度不能小于" + length);
		}

		return str.substring(0, length);
	}

	/**
	 * @description:从右起取字符串后n位
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param str
	 * @param length
	 * @return:String
	 */
	public static String right(String str, int length) {
		if (str == null) {
			throw new IllegalArgumentException("字符串参数值不能为null");
		}
		if (length < 0) {
			throw new IllegalArgumentException("整型参数长度不能小于0");
		}
		if (str.length() < length) {
			throw new IllegalArgumentException("字符串参数长度不能小于" + length);
		}

		return str.substring(str.length() - length);
	}

	/**
	 * @description:取指定字符串中间n位，字符串的位置从1开始算起
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param str
	 *            - 指定字符串。
	 * @param beginIdex
	 *            - 开始位置（包含）。
	 * @param endIndex
	 *            - 结束位置（包含）。
	 * @return String
	 */
	public static String mid(String str, int beginIdex, int endIndex) {
		if (str == null) {
			throw new IllegalArgumentException("字符串参数值不能为null");
		}

		return str.substring(beginIdex - 1, endIndex);
	}

	/**
	 * @description:生成下一个顺序号
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param currSerialNo
	 *            - 数据库中当前最大的顺序号。
	 * @param length
	 *            - 顺序号的位数。
	 * @return String - 下一个顺序号。
	 */
	// public static String genNextSerialNo(String currSerialNo, int length) {
	// String result = "";
	//
	// // 如果currSerialNo不够位数，在前面加“0”。
	// if (currSerialNo.length() < length) {
	// StringBuffer zeroStr = new StringBuffer();
	// for (int i = length - currSerialNo.length(); i > 0; i--) {
	// zeroStr.append("0");
	// }
	// currSerialNo = zeroStr + currSerialNo;
	// }
	//
	// // 产生当前最大顺序号。
	// long currMaxSerialNo = Long.parseLong("1" + currSerialNo) + 1;
	//
	// // 去掉前面一位数字。
	// result = String.valueOf(currMaxSerialNo).substring(1);
	//
	// return result;
	// }

	/**
	 * @description:编码带有中文名称Url
	 * @author: hansheng.lin
	 * @createDate: 2013-5-31
	 * @param url
	 * @return:
	 */
	public static String encodeUrl(String url) {
		String encodeUrl = "";
		if (StringUtil.isNullOrBlank(url)) {
			return "";
		}

		// 编码并转换空格
		try {
			encodeUrl = StringUtil.replace(URLEncoder.encode(url, "GBK"), "+",
					"%20");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodeUrl;
	}

	/**
	 * @ClassName: StringUtil
	 * @Description: 判断字符串是否相等
	 * @author: Yangjinbo
	 * @date: 2013-12-19
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean isEqual(String s1, String s2) {
		if ((null == s1) && (null == s2)) {
			return true;
		} else if ((null != s1) && (null == s2)) {
			return false;
		} else if ((null == s1) && (null != s2)) {
			return false;
		} else if (s1.equalsIgnoreCase(s2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @description:判断字符串是否为空字符串或者null
	 * @author: zhangliang
	 * @createDate: 2015-08-05
	 * @param value
	 * @return:boolean
	 */
	public static boolean isNullOrBlankNew(String value) {
		return isNull(value) || isBlankNew(value);
	}

	/**
	 * @description:判断字符串对象是否为空字符串和存在空格
	 * @author: zhangliang
	 * @createDate: 2015-08-05
	 * @param value
	 * @return:String
	 */
	public static boolean isBlankNew(String value) {
		boolean ret = false;
		if (value == null || "".equals(value.trim())) {
			ret = true;
		}

		return ret;
	}

	/**
	 * 判断字符串是否是数字串(整数)
	 *
	 * @param value
	 * @return
	 */
	public static boolean isInteger(String value, int limit) {
		if (null == value || "".equals(value)) {
			return false;
		}
		Pattern pattern = limit > 0 ? Pattern.compile("^\\d{1," + limit + "}$")
				: Pattern.compile("^\\d+$");
		Matcher isNum = pattern.matcher(value);
		return isNum.matches();
	}

	/**
	 * 判断字符串是否是数字串(小数)
	 *
	 * @param value
	 * @param limit
	 * @param precision
	 * @return
	 */
	public static boolean isDecimal(String value, int limit, int precision) {
		if (null == value || "".equals(value)) {
			return false;
		}
		if (limit <= precision || precision < 0) {
			throw new RuntimeException(
					"limit 必须大于 precision 且 precision 必须大于 0");
		}
		if (value.contains(".")) {
			int pointPos = value.indexOf(".", 1);
			// 整数部分 && 小数部分
			return isInteger(value.substring(0, pointPos), limit - precision)
					&& isInteger(value.substring(pointPos + 1), precision);
		} else {
			return isInteger(value, limit - precision);
		}
	}

	/**
	 * 
	 * @Description: 检查字符串是否包含中文
	 * @param @param str
	 * @param @return
	 * @author LiZiyu
	 * @date 2017年10月30日
	 */
	public static String matcherChinese(String str) {
		Pattern chinesePattern = Pattern.compile("[\u4E00-\u9FA5]+");
		Matcher matcherResult = chinesePattern.matcher(str);
		String result = matcherResult.find() ? "包含中文" : "不包含中文";

		return result;
	}

	/**
	 * 
	 * @Description: 验证字符串是否是纯数字结构
	 * @param @param str
	 * @param @return
	 * @author LiZiyu
	 * @date 2017年10月30日
	 */
	public static boolean isNum(String str) {
		if (null == str) {
			return true;
		}
		// 返回true 是纯数字
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 
	 * @Description: 验证字符串是否是数字或者是带小数的数字等格式
	 * @param @param str
	 * @param @return
	 * @author LiZiyu
	 * @date 2017年10月30日
	 */
	public static boolean isDoubleNum(String str) {
		// 返回true 是数字或者是带小数的数字
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 
	 * @Description: 获取字符串长度，中文占3个字节
	 * @param @param str
	 * @param @return
	 * @author LiZiyu
	 * @date 2017年10月30日
	 */
	public static int getByteLen(String str) {
		if (null != str) {
			return str.getBytes().length;
		} else {
			return 0;
		}
	}
	/**
	 * 
	 * @Description: 对象转XML
	 * @param @param obj
	 * @param @return
	 * @param @throws JAXBException 
	 * @author LiZiyu
	 * @date 2017年10月30日
	 */
	public static String convertToXml(Object obj) throws JAXBException {
		// 创建输出流
		StringWriter sw = new StringWriter();
		// 利用jdk中自带的转换类实现
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		// 格式化xml输出的格式
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// 将对象转换成输出流形式的xml
		marshaller.marshal(obj, sw);
		return sw.toString();
	}
	/**
	 * 
	 * @Description: 将实体中所有的String字段去空格
	 * @param @param obj
	 * @param @return
	 * @author LiZiyu
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @date 2017年9月12日
	 */
	public static Object trimAll(Object obj) throws IllegalArgumentException,
			IllegalAccessException {
		if (null == obj) {
			return obj;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		String value = null;
		for (int i = 0; i < fields.length; i++) {
			if (!"java.lang.String".equals(fields[i].getType().getName())) {
				continue;
			}
			fields[i].setAccessible(true);
			if (null == fields[i].get(obj)) {
				continue;
			}
			value = fields[i].get(obj).toString().trim();
			fields[i].set(obj, value);
		}
		return obj;
	}
	/**
	 * 
	 * @Description: 将实体List中所有的String字段去空格
	 * @param @param obj
	 * @param @return
	 * @author LiZiyu
	 * @param <T>
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @date 2017年9月12日
	 */
	public static <T> List<T> trimAll(List<T> objList) throws IllegalArgumentException,
			IllegalAccessException {
		if (null == objList) {
			return objList;
		}
		Field[] fields = null;
		for (Object obj : objList) {
			fields = obj.getClass().getDeclaredFields();
			String value = null;
			for (int i = 0; i < fields.length; i++) {
				if (!"java.lang.String".equals(fields[i].getType().getName())) {
					continue;
				}
				fields[i].setAccessible(true);
				if (null == fields[i].get(obj)) {
					continue;
				}
				value = fields[i].get(obj).toString().trim();
				fields[i].set(obj, value);
			}
		}
		return objList;
	}
	
	public static void main(String[] args) {
		System.out.println("测试".length());
		byte[] b = "测试".getBytes();
		int len = b.length;
		System.out.println(len);
	}

}