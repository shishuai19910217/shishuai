/**
 * 
 */
package com.ido85.frame.common.restful;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * 分页资源
 * @author rongxj
 *
 */
public class PagedResources<T> extends ResourceSupport{

	public final static PagedResources<?> NO_PAGE = new PagedResources<Object>();

	private final Paged<T> data;
	
	
	/**
	 * 
	 */
	protected PagedResources() {
		this(new ArrayList<T>(), new PageMetadata());
	}
	
	/**
	 * 分页资源<br/>
	 * @param content
	 * 数据列表
	 * @param metadata
	 * 页码数据
	 */
	public PagedResources(Iterable<T> content, PageMetadata metadata){
		Assert.notNull(content, "列表不能为Null");
		Assert.notNull(metadata, "分页元数据为Null");
		this.data = new Paged<T>(content, metadata);
	}
	
	public PagedResources(ProcessStatus result){
		if(result.getRetCode() == -3 || result.getRetCode() == -5){
			result = ProcessStatus.COMMON_SUCCESS;
		}
		this.setResult(result);
		this.data = null;
	}
	
	@JsonProperty(value="data", index = -1)
	public Paged<T> getData(){
		return data;
	}
	
	/**
	 * 数据列表
	 * @param list
	 */
	public void setList(Iterable<T> list){
		Assert.notNull(list, "列表不能为Null");
		data.setList(list);
	}
	
	/**
	 * 获取数据列表
	 * @return
	 */
	@JsonIgnore
	public Collection<T> getList(){
		return data.getList();
	}
	
	public void setPageMetadata(int count, int pageNo, int pageSize){
		data.setPage(new PageMetadata(count, pageNo, pageSize));
	}
	
	public void setPageMetadata(PageMetadata pagemeta){
		Assert.notNull(pagemeta, "列表不能为Null");
		data.setPage(pagemeta);
	}
	
	@JsonIgnore
	public PageMetadata getPageMetadata(){
		return data.getPage();
	}
	
	@JsonPropertyOrder(value={"list","count","pageNo","pageSize"})
	private static class Paged<T>{
		private Collection<T> list = new ArrayList<T>();
		@JsonUnwrapped
		private PageMetadata metadata;
		
		public Paged(Iterable<T> content, PageMetadata metadata){
			this.metadata = metadata;
			for (T element : content) {
				this.list.add(element);
			}
		}
		
		@JsonProperty("list")
		@JsonInclude(value=Include.ALWAYS)
		public Collection<T> getList(){
			return list;
		}
		
		public void setList(Iterable<T> content){
			for (T element : content) {
				this.list.add(element);
			}
		}
		
		@JsonUnwrapped
		public PageMetadata getPage(){
			return metadata;
		}
		
		public void setPage(PageMetadata pagemeta){
			this.metadata = pagemeta;
		}
	}
	
	public static class PageMetadata{
		
		@XmlAttribute @JsonProperty private int count;
		@XmlAttribute @JsonProperty private int pageNo;
		@XmlAttribute @JsonProperty private int pageSize;
		
		public PageMetadata(){
			
		}
		
		public PageMetadata(int count, int pageNo, int pageSize){
			this.count = count;
			this.pageNo = pageNo;
			this.pageSize = pageSize;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getPageNo() {
			return pageNo;
		}

		public void setPageNo(int pageNo) {
			this.pageNo = pageNo;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		
	}
}
