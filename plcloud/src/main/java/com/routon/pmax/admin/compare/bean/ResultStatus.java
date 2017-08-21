package com.routon.pmax.admin.compare.bean;

/**
 * @Title: 系统公用组件
 * @Description: json前台ajax查询返回结果枚举
 * @author: wangxiwei
 */
public enum ResultStatus {
	SUCCESS(200, "比对成功"),
	FAIL(500, "比对失败"),
	FAIL_nodata_recevied(0, "没有收到服务器返回的数据"),
	JOB_ID_DIF(-1, "收到结果的ID和发出的不符"),
	IMAGEA_NOT_EXIST(-2,"imageA不存在"),
	IMAGEB_NOT_EXIST(-3,"imageB不存在"),
	FORM_NO_IMAGE(-4,"表单中缺少照片");
	
	private int status;
	private String text;

	/**
	 * @Description:默认构造函数
	 * @param :status 状态吗
	 * @param :text 文字描述信息
	 * @return
	 * @throws Exception
	 */

	private ResultStatus(int status, String text) {
		this.status = status;
		this.text = text;
	}

	/**
	 * @Description:获取状态码
	 * @param :args
	 * @return 状态码
	 * @throws Exception
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * @Description:获取文字描述
	 * @param :args
	 * @return 文字描述
	 * @throws Exception
	 */
	public String getText() {
		return this.text;
	}
}
