package com.routon.idr.idrconst;

/**
 * 
 * @author wangxiwei93
 *
 */
public enum CompareType {
	net_1vn(0),	 	//网络1:N
	net_1v1(1),  	//网络1:1
	local_1vn(2),	//本地1:N
	local_1v1(3),  	//本地1:1
	net_detect_req(4), //网络检测请求
	net_compare_req(5), //网络比对请求
	net_detect_ack(6), //网络检测应答
	net_compare_ack(7), //网络比对应答
	net_cmd_data_updt(8), //来自网络的数据更新命令
	net_1vn_v100(9),	//网络1:N V100版本
	net_1v1_v100(10),  	//网络1:1 V100版本
	unknown(255); 	//未知
	
	private int value;
	private CompareType(int value)
	{
		this.value = value;
	}
	public int getValue() {  
        return this.value;  
    }
}
