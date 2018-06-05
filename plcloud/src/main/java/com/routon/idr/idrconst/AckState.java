package com.routon.idr.idrconst;

/**
 * 
 * @author wangxiwei
 *
 */
public enum AckState {
	ACCEPT,  //消息已成功接收
	REJECT,	 //系统内部发生异常，拒绝在接收此消息
	RETRY	 //系统内部发生异常，要求重收此消息
}
