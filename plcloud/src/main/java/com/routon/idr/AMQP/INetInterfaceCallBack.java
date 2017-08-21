package com.routon.idr.AMQP;

import com.routon.idr.rabbitmq.FACE_COMPARE_IMG_JOB_RESULT;

/**
 * 回调接口
 * @author wangxiwei93
 *
 */
public interface INetInterfaceCallBack {
	
	public void onCompareFinished(int jobId, FACE_COMPARE_IMG_JOB_RESULT result);
}
