package com.routon.idr.compare;

import com.routon.idr.rabbitmq.FACE_COMPARE_IMG_JOB_RESULT;

/**
 * 回调接口
 * @author wangxiwei93
 *
 */
public interface INetInterfaceCallBack {
	
	public void onCompareFinished(FACE_COMPARE_IMG_JOB_RESULT result);
}
