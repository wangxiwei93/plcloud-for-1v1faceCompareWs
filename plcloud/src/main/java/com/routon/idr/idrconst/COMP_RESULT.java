package com.routon.idr.idrconst;

/**
 * 
 * @author wangxiwei93
 *
 */
public enum COMP_RESULT {
	success, // 成功
	fail_is_comparing,
	fail_dont_receive_data,
	fail_connect_exception,
	fail_publish_timeout,
	fail_received_data_len_error,
	fail_delivery_is_null_after_retry,
	fail_user_cancelled, // 用户主动取消
	fail_io_exception, // IO异常
	fail_diff_jobid, //Jobid不同,不是预期的应答
	fail_recv_timeout,//接收超时
	fail_task_proc,	
	fail_gen_feature,
	fail_detect_face,
	fail_data_null,//数据为空
	fail_data_length,
	unknown // 未知֪
}
