package cn.cebest.framework.constant;


/**
 *  常量类
  * @author maming  
  * @date 2018年8月28日
 */
public class Constants {

	/**
	 * 定时任务状态:正常
	 */
	public static final Integer QUARTZ_STATUS_NOMAL = 0;
	/**
	 * 定时任务状态:暂停
	 */
	public static final Integer QUARTZ_STATUS_PUSH = 1;
	/**
	 * 手机号正则表达式
	 */
	public final static String REGEX_PHONE = "^1[3456789]\\d{9}$";
	/**
	* 正则表达式：验证邮箱
	*/
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	/**
	* 正则表达式：验证汉字
	*/
	public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
	/**
	* 正则表达式：验证身份证
	*/
	public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
	/**
	* 正则表达式：验证URL
	*/
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	/**
	* 正则表达式：验证IP地址
	*/
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
}
