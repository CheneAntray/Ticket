package com.xianqin.view.roleinfo;

import java.io.Serializable;

public class TreeView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer pId;
	private String name;
	/**
	 * 是否一级节点 false：否 true：是
	 */
	private Boolean open=false;
	/**
	 * 是否为勾选状态 false：否 true：是
	 */
	private Boolean checked=false;
	/**
	 * 是否可以随意勾选 false：否 true：是
	 */
	private Boolean doCheck=false;
	public Boolean getDoCheck() {
		return doCheck;
	}
	public void setDoCheck(Boolean doCheck) {
		this.doCheck = doCheck;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public TreeView(String id,String name,String pId){
		this.id=Integer.valueOf(id);
		this.name=name;
		if (pId!=null&&!"".equals(pId)) {
			this.pId=Integer.valueOf(pId);
		}else{
			this.pId=0;
		}
	}

}
