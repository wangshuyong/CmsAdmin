package com.sea.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_comments")
public class Comments {

	private int id;
	private String content;
	private int bodyId;
	private int commentsId;
	private int replyId;
	private Timestamp createTime;
	
	@Id
	@Column(updatable=false)
	@GeneratedValue
	public int getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public int getBodyId() {
		return bodyId;
	}
	public void setBodyId(int bodyId) {
		this.bodyId = bodyId;
	}
	public int getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(int commentsId) {
		this.commentsId = commentsId;
	}
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

}
