package com.ido85.frame.web.rest.utils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
 
/**
 * json 简单操作的工具类
 * @author shishuai
 *
 */
public class JsonUtil{
 
    private static Gson gson=null;
    static{
        if(gson==null){
            gson=new Gson();
        }
    }
    private JsonUtil(){}
    /**
     * 将对象转换成json格式
     * @param ts
     * @return
     */
    public static String objectToJson(Object ts){
        String jsonStr=null;
        if(gson!=null){
            jsonStr=gson.toJson(ts);
        }
        return jsonStr;
    }
    /**
     * 将对象转换成json格式(并自定义日期格式)
     * @param ts
     * @return
     */
    public static String objectToJsonDateSerializer(Object ts,final String dateformat){
        String jsonStr=null;
        gson=new GsonBuilder().registerTypeHierarchyAdapter(Date.class, new JsonSerializer<Date>() {
            public JsonElement serialize(Date src, Type typeOfSrc,
                    JsonSerializationContext context) {
                SimpleDateFormat format = new SimpleDateFormat(dateformat);
                return new JsonPrimitive(format.format(src));
            }
        }).setDateFormat(dateformat).create();
        if(gson!=null){
            jsonStr=gson.toJson(ts);
        }
        return jsonStr;
    }
    /**
     * 将json格式转换成list对象
     * @param jsonStr
     * @return
     */
    public static List<?> jsonToList(String jsonStr){
        List<?> objList=null;
        if(gson!=null){
            java.lang.reflect.Type type=new com.google.gson.reflect.TypeToken<List<?>>(){}.getType();
            objList=gson.fromJson(jsonStr, type);
        }
        return objList;
    }
    /**
     * 将json格式转换成map对象
     * @param jsonStr
     * @return
     */
    public static Map<?,?> jsonToMap(String jsonStr){
        Map<?,?> objMap=null;
        if(gson!=null){
            java.lang.reflect.Type type=new com.google.gson.reflect.TypeToken<Map<?,?>>(){}.getType();
            objMap=gson.fromJson(jsonStr, type);
        }
        return objMap;
    }
    /**
     * 将json转换成bean对象
     * @param jsonStr
     * @return
     */
    public static Object  jsonToBean(String jsonStr,Class<?> cl){
        Object obj=null;
        if(gson!=null){
            obj=gson.fromJson(jsonStr, cl);
        }
        return obj;
    }
    /**
     * 将json转换成bean对象
     * @param jsonStr
     * @param cl
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T  jsonToBeanDateSerializer(String jsonStr,Class<T> cl,final String pattern){
        Object obj=null;
        gson=new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context)
                    throws JsonParseException {
                    SimpleDateFormat format=new SimpleDateFormat(pattern);
                    String dateStr=json.getAsString();
                try {
                    return format.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).setDateFormat(pattern).create();
        if(gson!=null){
            obj=gson.fromJson(jsonStr, cl);
        }
        return (T)obj;
    }
    /**
     * 根据
     * @param jsonStr
     * @param key
     * @return
     */
    public static Object  getJsonValue(String jsonStr,String key){
        Object rulsObj=null;
        Map<?,?> rulsMap=jsonToMap(jsonStr);
        if(rulsMap!=null&&rulsMap.size()>0){
            rulsObj=rulsMap.get(key);
        }
        return rulsObj;
    }
/*    public static void main(String[] args) {
		String str = "{\"result\":{\"id\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proUrl\":\"wwww.xiangmu.com\",\"proName\":\"项目名称1\",\"proCode\":\"DD2015121700024432018\",\"proDesc\":\"项目简介，项目是用来提高知名度\",\"linkFrequency\":10,\"proCycle\":2,\"proState\":\"0\",\"proType\":\"0\",\"level0\":\"101000\",\"proBasePriceList\":[{\"id\":\"830c515401774bfd996549086bb202ae\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"brMin\":1,\"brMax\":3,\"pr\":3,\"price\":20},{\"id\":\"7e0969e4c7394a59a11c79d71776c3f5\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"brMin\":2,\"brMax\":4,\"pr\":2,\"price\":30},{\"id\":\"fe46040682aa43b49d8e647912f2ae6d\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"brMin\":5,\"brMax\":6,\"pr\":3,\"price\":40}],\"proAddPriceList\":[{\"id\":\"b4feca170f114438ae73729811535ad8\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"nr\":5,\"percentum\":130},{\"id\":\"a8f52b43805241e196d49a0c7bb33722\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"nr\":4,\"percentum\":115},{\"id\":\"24fe87b11d7d4fea8771ac40640a271c\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"nr\":3,\"percentum\":105},{\"id\":\"1c26845dc9dc41328291011290b90dc5\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"nr\":2,\"percentum\":70}],\"proUserList\":[{\"id\":\"cf6247f1d3164ae69a31eb795578bc6b\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"userId\":\"0db0dc91685346d1a0499c1b35f50f96\"},{\"id\":\"400bca67059d439093cac59daed7ab3d\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"userId\":\"266e849194704da09bb67ec4c99d6b42\"},{\"id\":\"33174fd740124872bf7125ea8e4b3d7f\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"userId\":\"2d9d9949088c44dd900491c344d7d16c\"}],\"proSiteList\":[{\"id\":\"9a37c64da8f441978b9ecb770948038a\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"siteUserRelId\":\"d6d64769480946f4a437e95f52e1e61c\",\"siteId\":\"07e7edc7e3a94e1da68dcd3428949381\",\"nr\":3,\"price\":200,\"bi\":1856623,\"br\":1,\"pr\":4,\"ex\":23},{\"id\":\"bedb31ee0ad3447998de73ae78a8bccc\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"siteUserRelId\":\"a4a0f55eec6f4146bf08092a4339d53c\",\"siteId\":\"1556c6adac65423985b94612199c08d7\",\"nr\":4,\"price\":400,\"bi\":130285,\"br\":5,\"pr\":3,\"ex\":0},{\"id\":\"fe7d9c870e42497688ea010570dd6810\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"siteUserRelId\":\"f2f3315b11e24bf0b6f6c8f0104d1083\",\"siteId\":\"170e9735b2924558814ca705c6e06530\",\"nr\":5,\"price\":500,\"bi\":67993,\"br\":2,\"pr\":4,\"ex\":18},{\"id\":\"4fffb7a70d4f43ebac3c3aa7ffa1ee77\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"siteUserRelId\":\"b8b6161401b94dce9a0a3157531a2b0d\",\"siteId\":\"17453bc33ac94ebe921054b094be6484\",\"nr\":2,\"price\":100,\"bi\":0,\"br\":1,\"pr\":3,\"ex\":22}],\"proAnchorList\":[{\"id\":\"faa3ae9f4c974697abd7edfed85e4dc0\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"anchorText\":\"锚文本1\",\"targetUrl\":\"着陆页1\",\"needList\":[{\"id\":\"072a8aebfe454c1b9688a660fd362695\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"anchorId\":\"faa3ae9f4c974697abd7edfed85e4dc0\",\"proBasicId\":\"fe46040682aa43b49d8e647912f2ae6d\",\"needNum\":5,\"distributNum\":2,\"distributNeedNum\":0,\"grabNeedNum\":3,\"brMin\":\"1\",\"brMax\":\"3\",\"pr\":\"3\",\"orderList\":[{\"id\":\"74183caf8b2647798a917f177aa91f85\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"orderCode\":\"DD2015121700025124448\",\"anchorId\":\"faa3ae9f4c974697abd7edfed85e4dc0\",\"shortlistedSiteId\":\"4fffb7a70d4f43ebac3c3aa7ffa1ee77\",\"anchorNeedId\":\"072a8aebfe454c1b9688a660fd362695\",\"orderFrom\":\"1\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\"}],\"siteIds\":[\"06a7d859f10b49a3a0f6efbe7f9c2b04\",\"17453bc33ac94ebe921054b094be6484\"]},{\"id\":\"40457cc6bc7345eab9d03f840a7bcd69\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"anchorId\":\"faa3ae9f4c974697abd7edfed85e4dc0\",\"proBasicId\":\"7e0969e4c7394a59a11c79d71776c3f5\",\"needNum\":5,\"distributNum\":0,\"distributNeedNum\":0,\"grabNeedNum\":5,\"brMin\":\"2\",\"brMax\":\"4\",\"pr\":\"2\"},{\"id\":\"3a6d3da9f996443698b8901ab4846172\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"anchorId\":\"faa3ae9f4c974697abd7edfed85e4dc0\",\"proBasicId\":\"fe46040682aa43b49d8e647912f2ae6d\",\"needNum\":5,\"distributNum\":0,\"distributNeedNum\":0,\"grabNeedNum\":5,\"brMin\":\"5\",\"brMax\":\"6\",\"pr\":\"3\"}]}],\"level0ss\":[\"101001\",\"101002\",\"101003\"],\"proTypeList\":[{\"id\":\"6ec8dfa7c02e485c81117de574c36aee\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"level0\":\"101000\",\"level1\":\"101001\"},{\"id\":\"e511ecf15208407796322c23e1cd3937\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"level0\":\"101000\",\"level1\":\"101002\"},{\"id\":\"df1cdd7631404ff18559036f40cd0b2c\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"level0\":\"101000\",\"level1\":\"101003\"}],\"proRenew\":{\"id\":\"f3d15182ea984b1ea79d47e10f309aec\",\"isNewRecord\":false,\"createDate\":\"2015-12-17 15:52:29\",\"updateDate\":\"2015-12-17 15:52:29\",\"proId\":\"5266a1cb4fe2447c8659ba21d17acc84\",\"proCycle\":2}}}";
		System.out.println(JsonUtil.jsonToMap(str));
		
	}*/
     
}