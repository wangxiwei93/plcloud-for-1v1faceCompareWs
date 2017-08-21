package com.routon.idr.idrconst;

/**
 * 
 * @author wangxiwei93
 *
 */
public class FACE_COMPARE_ACK_STARTED {
	public COMP_RESULT comp_result = COMP_RESULT.unknown;
	public int jobId = 0;
	
	public void reset(){
		comp_result = COMP_RESULT.unknown;
		jobId = 0;	
	}
}
