package com.routon.pmax.admin.privilege.model;

import java.util.Collection;

/**
 * 
 * <p>
 * Title: MenuTreeBean
 * </p>
 * <p>
 * Description: ext tree node for Menu
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Routon Electronic Co.,Ltd.
 * </p>
 * <p>
 * Date: 2013-5-14
 * </p>
 * 
 * @author ludunzhi
 * @version 1.0
 */
public class TreeBean {

	/**
	 * 节点ID
	 */
	private Long id = 1l;

	/**
	 * 父结点ID
	 */
	private Long pid = 1L;

	/**
	 * 节点名
	 */
	private String text = "";

	private String name = "";

	private boolean isParent;

	/**
	 * 是否check
	 */
	private boolean checked = false;

	private boolean nocheck = false;

	/**
	 * 子节点
	 */
	private Collection<TreeBean> children = null;

	/**
	 * 是否展开
	 */
	private boolean open = false;

	/**
	 * 是否为叶子节点
	 */
	private boolean leaf = false;
	
	private int userCount;
	private int terminalCount;
	private int resourceCount;
	private int noticeCount;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.setName(text);
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Collection<TreeBean> getChildren() {
		return children;
	}

	public void setChildren(Collection<TreeBean> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public boolean isIsParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	
	public int getUserCount() {
		return userCount;
	}

	
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	
	public int getTerminalCount() {
		return terminalCount;
	}

	
	public void setTerminalCount(int terminalCount) {
		this.terminalCount = terminalCount;
	}

	
	public int getResourceCount() {
		return resourceCount;
	}

	
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	
	public int getNoticeCount() {
		return noticeCount;
	}

	
	public void setNoticeCount(int noticeCount) {
		this.noticeCount = noticeCount;
	}

	
	public boolean isParent() {
		return isParent;
	}

}
