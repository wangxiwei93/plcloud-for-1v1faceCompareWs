package com.routon.idr.rabbitmq;

import java.nio.ByteBuffer;

import com.routon.idr.idrconst.CompareType;

/**
 * 
 * @author wangxiwei93
 *
 */
public class InputBean {
	public int jobId = 0;
	public ByteBuffer msg = null;
	public CompareType compType = CompareType.unknown;	
	public String client_uuid = null;
}
