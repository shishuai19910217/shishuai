package com.ido85.frame.common.restful;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 列表资源
 * 
 * @author rongxj
 *
 */
public class Resources<T> extends ResourceSupport implements Iterable<T>{

	private Collection<T> data;
	
	/**
	 * 空数据列表资源 <br/>
	 * 处理结果 默认 result:{retCode:0, retMsg:"已处理"}
	 */
	protected Resources(){
		this((Iterable<T> )null);
	}
	
	/**
	 * 根据列表数据创建资源<br/>
	 * @param content
	 * 	列表数据 <br/>
	 *  处理结果 默认 result:{retCode:0, retMsg:"已处理"}
	 */
	public Resources(Iterable<T> content){
		if(content!=null){
			this.data = new ArrayList<T>();
			for (T element : content) {
				this.data.add(element);
			}
		}else {
			this.data = null;
		}
		
	}
	
	/**
	 * 根据列表数据创建资源<br/>
	 * @param content
	 * 列表数据
	 * @param result
	 * 处理结果
	 */
	public Resources(Iterable<T> content, ProcessStatus result){
		if(result.getRetCode() == -3 || result.getRetCode() == -5){
			result = ProcessStatus.COMMON_SUCCESS;
		}
		this.setResult(result);
		if(null!=content){
			this.data = new ArrayList<T>();
			
			for (T element : content) {
				this.data.add(element);
			}
		}else {
			this.data = null;
		}
		
	}
	/**
	 * 根据列表数据创建资源<br/>
	 * @param content
	 * 列表数据
	 * @param result
	 * 处理结果
	 */
	public Resources(ProcessStatus result){		
		Assert.notNull(result, "处理结果状态不能为Null");
		if(result.getRetCode() == -3 || result.getRetCode() == -5){
			result = ProcessStatus.COMMON_SUCCESS;
		}
		this.setResult(result);
		this.data = null;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T extends Resource<S>, S> Resources<T> wrap(Iterable<S> content) {

		
		
		ArrayList<T> resources = new ArrayList<T>();
		if(null!=content){
			for (S element : content) {
				resources.add((T) new Resource<S>(element));
			}
			return new Resources<T>(resources);
		}else {
			return null;
		}
		

		
	}
	
	/**
	 *  返回结果数据
	 */
	@XmlAnyElement
	@XmlElementWrapper
	@JsonProperty("data")
	@JsonInclude(value=Include.ALWAYS)
	public Collection<T> getData() {
		if(null==this.data)
			return null;
		return Collections.unmodifiableCollection(data);
	}
	
	/**
	 * 返回结果大小
	 * @return int
	 */
	@JsonProperty(value="count", index=-1)
	public int getCount(){
		if(null==this.data)
			return 0;
		return data.size();
	}
	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return data.iterator();
	}
	
	@Override
	public String toString() {
		return String.format("Resources { data: %s, %s }", getData(), super.toString());
	}
	

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}

		Resources<?> that = (Resources<?>) obj;

		boolean contentEqual = this.data == null ? that.data == null : this.data.equals(that.data);
		return contentEqual ? super.equals(obj) : false;
	}


	@Override
	public int hashCode() {

		int result = super.hashCode();
		result += data == null ? 0 : 17 * data.hashCode();

		return result;
	}
}

	