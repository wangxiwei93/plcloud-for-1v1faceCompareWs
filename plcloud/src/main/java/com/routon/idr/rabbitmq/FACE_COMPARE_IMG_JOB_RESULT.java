package com.routon.idr.rabbitmq;

import com.routon.idr.idrconst.COMP_RESULT;

/**
 * 
 * @author wangxiwei93
 *
 */
public class FACE_COMPARE_IMG_JOB_RESULT {
	public static int JOB_STATE_PENDING = 0;              // 任务待处理状态
	public static int JOB_STATE_PROCESSING = 1;           // 任务处理中状态
	public static int JOB_STATE_READY = 2;                // 任务处理完成状态
	public static int JOB_STATE_EXCEPTION = 3;            // 任务处理异常状态
	public static int JOB_STATE_BUFFER_OVERFLOW = 4;      // 任务队列满
	public static int WANTED_DATA_LEN = 16;
	public COMP_RESULT comp_result = COMP_RESULT.unknown;
	public int version = 0;
	public int job_id = 0;
	public int job_state;
	public byte[] clientUUID;
	public float score = 0f;
	public int job_id_req = 0;

	public void reset(){
		comp_result = COMP_RESULT.unknown;
		job_id = 0;
		score = 0;
		job_id_req = 0;
	}
}
