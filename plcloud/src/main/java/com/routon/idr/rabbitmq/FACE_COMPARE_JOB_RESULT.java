package com.routon.idr.rabbitmq;

import java.util.List;

import com.routon.idr.idrconst.COMP_RESULT;
import com.routon.idr.idrconst.FACE_COMPARE_SCORE;

/**
 * 
 * @author wangxiwei93
 *
 */
public class FACE_COMPARE_JOB_RESULT {
	public static int JOB_STATE_PENDING = 0;              // ���������״̬
	public static int JOB_STATE_PROCESSING = 1;               // ��������״̬
	public static int JOB_STATE_READY = 2;                    // ���������״̬
	public static int JOB_STATE_EXCEPTION = 3;                // �������쳣״̬
	public static int JOB_STATE_BUFFER_OVERFLOW = 4;           // ���������
	public static int WANTED_MIN_DATA_LEN = 16;
	public COMP_RESULT comp_result;
	public int version = 0;
	public int job_id;
	public int job_state;
	public int photo_result_num;  //���ս����
	public int idcard_result_num; //���֤�ս����
	public List<FACE_COMPARE_SCORE> score_lists;
	public int job_id_req;

	public void reset(){
		comp_result = COMP_RESULT.unknown;
		job_id = 0;
		score_lists = null;
		job_id_req = 0;
	}
}
