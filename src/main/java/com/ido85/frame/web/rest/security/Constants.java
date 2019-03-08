package  com.ido85.frame.web.rest.security;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-26
 * <p>Version: 1.0
 */
public class Constants {
    public static final String PARAM_DIGEST = "digest";
    public static final String PARAM_USERNAME = "username";
    public static final String JWS_HEADER_NAME = "JWS";
    public static final String USER_MANAGE_URL = "http://192.168.10.210:8081/";
    public static final String USER_INFO_URI = "/trust/getuserinfo/";
    public static final String USER_URI = "/trust/getuserinfobyid/";
	public static String DICT_CACHE = "_DICT_CACHE_";
	public static String KEYWORD_CACHE_LIST = "_KEYWORD_CACHE_LIST_";
	public static String FOCUSSITEDICT_CACHE_LIST = "_FOCUSSITEDICT_CACHE_LIST_";
	public static String CHANNELDICT_CACHE_LIST = "_CHANNELDICT_CACHE_LIST_";
	public static String CHANNELDICT_CACHE = "CHANNELDICT_CACHE";
	public static String KEYWORD_CACHE = "_KEYWORD_CACHE_";
	public static String FOCUSSITEDICT_CACHE = "_FOCUSSITEDICT_CACHE_";
	
	/**
	 * 整站优化图标显示的列数  5个节点
	 */
	public static int SHOW_NUM = 5;
	/**
	 * 严重等级
	 */
	public static int ISSUES_LEVEL_HIGH = 0;
	/**
	 * 一般等级
	 */
	public static int ISSUES_LEVEL_MEDIUM = 1;
	/**
	 * 轻微等级
	 */
	public static int ISSUES_LEVEL_LOW = 2;
	
	/**
	 * 50~100万	100万~500万	500万~1000万	1000万~3000万	3000万~5000万	5000万~8000万	8000万以上
	 */
	public static int[] BAIDU_RECORD = {100000, 500000, 1000000, 5000000, 10000000, 30000000, 50000000, 80000000};
	/**
	 * 50以下	50~100	100~300	300~600	600~1000	1000~3000	3000~5000	5000~1万	 1万以上
	 */
	public static int[] BAIDU_INDEX = {50, 100, 300, 600, 1000, 3000, 5000, 10000};
	
}
