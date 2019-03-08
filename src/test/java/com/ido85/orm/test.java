/**
 * 
 */
package com.ido85.orm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Locale.Category;
import java.util.zip.ZipEntry;

import org.springframework.util.Assert;

import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.mapper.JsonMapper;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.MathUtil;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.utils.JsonUtil;

/**
 * @author IBM
 *
 */
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("123");
		list.add("1234");
		list.add("1235");
		list.add("1236");
		
		System.out.println(Math.pow(10d, 0.3d));
		
		
		
		BusinessException e = new BusinessException("fdfdfdfd");
		System.out.println(JsonMapper.toJsonString(e));
		Object o = null;
		Assert.notNull(o, "list is null");
	}

}
