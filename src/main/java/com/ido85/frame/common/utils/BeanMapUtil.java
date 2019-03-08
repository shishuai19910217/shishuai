package com.ido85.frame.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/***
 * javaBean 与map 相互转化
 * 
 * @author shishuai
 *
 */
public class BeanMapUtil {
	// Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
	public static void transMap2Bean2(Map<String, Object> map, Object obj) {
		if (map == null || obj == null) {
			return;
		}
		try {
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			System.out.println("transMap2Bean2 Error " + e);
		}
	}

	/***
	 * 使用 内省
	 * 
	 * @param map
	 * @param obj
	 */
	// Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	public static void transMap2Bean(Map<String, Object> map, Object obj) {

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}

			}

		} catch (Exception e) {
			System.out.println("transMap2Bean Error " + e);
		}

		return;

	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

	public static Object transUnderLineMap2CamelBean(Map<String, String> map, Object obj) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = CamelUtil.camelToUnderline(property.getName());
			if (map.containsKey(key)) {
				Object value = map.get(key);
				// 得到property对应的setter方法
				Method setter = property.getWriteMethod();
				setter.invoke(obj, getObjectValue(property,value));
			}

		}
		return obj;
	}

	public static Object getObjectValue(PropertyDescriptor property, Object value) throws Exception {
		Object val=new Object();
		// 如果类型是String
		if (property.getPropertyType().toString().equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class

			val = (String) value;// 调用getter方法获取属性值
		}
		// 如果类型是Integer
		if (property.getPropertyType().toString().equals("class java.lang.Integer")) {
			val = (Integer) value;

		}
		// 如果类型是Integer
		if (property.getPropertyType().toString().equals("int")) {
			if (value!=null) {
				val = Integer.parseInt(value.toString()) ;
			}else{
				val=0;
			}
			

		}
		// 如果类型是Double
		if (property.getPropertyType().toString().equals("class java.lang.Double")) {
			val = (Double) value;

		}
		// 如果类型是Boolean 是封装类
		if (property.getPropertyType().toString().equals("class java.lang.Boolean")) {
			val = (Boolean) value;

		}
		// 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
		// 反射找不到getter的具体名
		if (property.getPropertyType().toString().equals("boolean")) {
			 val = (Boolean)value;
		
		}
		// 如果类型是Date
		if (property.getPropertyType().toString().equals("class java.util.Date")) {
			 val = DateUtils.parseDate(value);
		
		}
	
		return val;

	}
	
}
