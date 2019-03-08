package com.ido85.master.keyword.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



import com.ido85.seo.common.BaseEntity;

/**
 * keyword 字典
 * @author fire
 *
 */
@Entity
@Table(name = "tf_f_seo_keyword")
@NamedQuery(name = "Keyword.findAll", query = "select u from Keyword u where delFlag = '0'") 
public class Keyword extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="keyword_id")
	private Integer keywordId;
	
	@Column(name="keyword_name")
	private String keywordName;
	
	@Column(name="difficuly_score")
	private String difficulyScore;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="end_date")
	private Date endDate;
	
	
	public Keyword() {
		super();
	}
	public Keyword(Integer keywordId,String keywordName, String delFlag, Date createDate) {
		super();
		this.keywordId = keywordId;
		this.keywordName = keywordName;
		this.delFlag = delFlag;
		this.createDate = createDate;
	}
	public String getDifficulyScore() {
		return difficulyScore;
	}
	public void setDifficulyScore(String difficulyScore) {
		this.difficulyScore = difficulyScore;
	}
	public String getKeywordId() {
		return keywordId == null ? null : keywordId.toString();
	}
	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}
	public String getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
