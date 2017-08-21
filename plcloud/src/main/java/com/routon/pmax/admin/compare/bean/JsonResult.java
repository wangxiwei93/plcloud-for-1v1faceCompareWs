package com.routon.pmax.admin.compare.bean;

/**
 * @Title: 系统公用组件
 * @Description: 用户AJAX操作返回信息
 * @author: wangxiwei

 */

public class JsonResult {
	
	
	/**
	 * @Description:默认构造函数
	 * @param :status结果状态值
	 * @return
	 * @throws Exception
	 */
	public JsonResult(ResultStatus status){
		this.status = status.getStatus();
		this.text = status.getText();
	}
	
	 
	/**
	 * @Description:默认构造函数
	 * @param :status结果状态值
	 * @param :text	使用自定义描述
	 * @return
	 * @throws Exception
	 */
	public JsonResult(ResultStatus status, JsonData data){
		this.status = status.getStatus();
		this.text = status.getText();
		this.data = data;
	}
	/**
	 * 状态码
	 */
	private int status;
	
	/**
	 * 返回信息
	 */
	private String text;
	
	/**
	 * 比对分数
	 */
	private JsonData data;
	
	public JsonData getdata() {
		return data;
	}

	public int getStatus() {
		return status;
	}
	public String getText() {
		return text;
	}
}
