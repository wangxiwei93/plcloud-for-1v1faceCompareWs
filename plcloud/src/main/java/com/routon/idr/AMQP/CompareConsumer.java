package com.routon.idr.AMQP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;
import com.routon.idr.idrconst.AckState;
import com.routon.idr.idrconst.COMP_RESULT;
import com.routon.idr.rabbitmq.FACE_COMPARE_IMG_JOB_RESULT;

/**
 * 消费者
 * @author wangxiwei93
 *
 */
public class CompareConsumer implements /*MessageListener*/ ChannelAwareMessageListener {
	
	static Logger logger = Logger.getLogger(CompareConsumer.class);
	
	private int COMM_PROTO_VER = 0;
	
	private INetInterfaceCallBack mCompareCallback;
	
	private final Object mLockCallBack = new Object();
	
	public void setCallback(INetInterfaceCallBack cb){
		this.mCompareCallback = cb;
	}
	
	/**
	 * original version
	 */
/*	@Override
	public void onMessage(Message message) {
		//test in 2017/12/15 by wangxiwei begin
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// ----------end
		FACE_COMPARE_IMG_JOB_RESULT m1v1Result = null;
		byte[] data_recved = message.getBody();
		try{
			if (data_recved != null){
				m1v1Result = new FACE_COMPARE_IMG_JOB_RESULT();
				m1v1Result.comp_result = GetSimilarProcFor1V1(
						data_recved, m1v1Result, false);
				m1v1Result.version = COMM_PROTO_VER;
				logger.info("received message[job_id = " + m1v1Result.job_id + ", status = " + m1v1Result.comp_result
						+ ", scores = " + m1v1Result.score + "]");
				System.out.println("received message[job_id = " + m1v1Result.job_id + ", status = " + m1v1Result.comp_result
						+ ", scores = " + m1v1Result.score + "]");
				//modified in 2017.7.10 by wxw
				synchronized (mLockCallBack){
					//通过回调返回m1v1Result
					mCompareCallback.onCompareFinished(m1v1Result.job_id, m1v1Result);
				}
			}
		} catch(Exception e){
			logger.error("Consumer error!");
			logger.error(e.getMessage(), e);
		}
	}*/
   
	/**
	 * modified in GUANGAN Hanzhou 2017/12/17 by wangxiwei
	 */
/*    @Override
	public void onMessage(Message message, Channel channel) {
		FACE_COMPARE_IMG_JOB_RESULT m1v1Result = null;
		byte[] data_recved = null;
		try {
			data_recved = message.getBody();
			//手动ACK
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			try {
				//如果发生异常，再次ACK提示RabbitMQ不再重发此消息
				channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e1) {
				logger.error(e.getMessage(), e1);
				e1.printStackTrace();
			}
			logger.error("Consumer error!");
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		if (data_recved != null){
				m1v1Result = new FACE_COMPARE_IMG_JOB_RESULT();
				m1v1Result.comp_result = GetSimilarProcFor1V1(
						data_recved, m1v1Result, false);
				m1v1Result.version = COMM_PROTO_VER;
				logger.info("received message[job_id = " + m1v1Result.job_id + ", status = " + m1v1Result.comp_result
						+ ", scores = " + m1v1Result.score + "]");
				System.out.println("received message[job_id = " + m1v1Result.job_id + ", status = " + m1v1Result.comp_result
						+ ", scores = " + m1v1Result.score + "]");
				//modified in 2017.7.10 by wxw
				synchronized (mLockCallBack){
					//通过回调返回m1v1Result
					mCompareCallback.onCompareFinished(m1v1Result.job_id, m1v1Result);
				}
		}
	}*/
	
	/**
	 * lastest version, modified in Wuhan 2018/1/15 by wangxiwei。
	 * @throws IOException 
	 */
    @Override
	public void onMessage(Message message, Channel channel) throws IOException {
		FACE_COMPARE_IMG_JOB_RESULT m1v1Result = null;
		byte[] data_recved = null;
		AckState state = null;
		try {
			data_recved = message.getBody();
			if (data_recved != null){
				m1v1Result = new FACE_COMPARE_IMG_JOB_RESULT();
				m1v1Result.comp_result = GetSimilarProcFor1V1(
						data_recved, m1v1Result, false);
				m1v1Result.version = COMM_PROTO_VER;
				logger.info("received message[job_id = " + m1v1Result.job_id + ", status = " + m1v1Result.comp_result
						+ ", scores = " + m1v1Result.score + "]");
				System.out.println("received message[job_id = " + m1v1Result.job_id + ", status = " + m1v1Result.comp_result
						+ ", scores = " + m1v1Result.score + "]");
				//modified in 2017.7.10 by wxw
				synchronized (mLockCallBack){
					//通过回调返回m1v1Result
					mCompareCallback.onCompareFinished(m1v1Result.job_id, m1v1Result);
				}
			}
			// 如果成功完成则action=Action.ACCEPT
			state = AckState.ACCEPT;
		} catch (Exception e) {
			// 如果成功接收发生异常则action=Action.REJECT
			state = AckState.REJECT;
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} /*finally {
			  // 通过finally块来保证Ack/Nack会且只会执行一次  
			  if (state == AckState.ACCEPT) {  
			    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    System.out.println("["+m1v1Result.job_id + "] Acked");
			  } else if (state == AckState.REJECT) {  
			    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
			    System.out.println("["+m1v1Result.job_id + "] Nacked!!!");
			    logger.error("["+m1v1Result.job_id + "] Nacked!!!");
			  } else {  
			    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);  
			  }    
		}*/
	}

	public COMP_RESULT GetSimilarProcFor1V1(byte[] data_recved,
			FACE_COMPARE_IMG_JOB_RESULT faceCompareImgResult,
			boolean is_filter_diff_jobid) {

		COMP_RESULT commRes = COMP_RESULT.unknown;
		if (data_recved == null) {
			logger.info("result is null.");
			commRes = COMP_RESULT.fail_dont_receive_data;
		} else {
			logger.debug("data_recved.length=" + data_recved.length);

			if (faceCompareImgResult == null) {
				commRes = COMP_RESULT.fail_dont_receive_data;
			} else {
				commRes = COMP_RESULT.success;
				
				if(FACE_COMPARE_IMG_JOB_RESULT.WANTED_DATA_LEN==data_recved.length){
					ByteBuffer buff = ByteBuffer.wrap(data_recved, 0,
							data_recved.length);
					buff.order(ByteOrder.LITTLE_ENDIAN);
	
					faceCompareImgResult.job_id = buff.getInt();
	
					// 如果不是预期的job_id,视为失败
					if (is_filter_diff_jobid
							&& (faceCompareImgResult.job_id != faceCompareImgResult.job_id_req)) {
						commRes = COMP_RESULT.fail_diff_jobid;
					}
	
					if (commRes.equals(COMP_RESULT.success)) {
						faceCompareImgResult.comp_result = COMP_RESULT.success;
						faceCompareImgResult.job_state = buff.getInt();
						faceCompareImgResult.score = buff.getFloat();
						buff.getInt();// just for padding
					}
	
					faceCompareImgResult.comp_result = commRes;
				}else{
					commRes = COMP_RESULT.fail_data_length;
					faceCompareImgResult.comp_result = commRes;
				}
			}
		}

		return commRes;
	}
    
    public static byte[] toByteArray (String obj) {        
        byte[] bytes = null;        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();        
        try {          
            ObjectOutputStream oos = new ObjectOutputStream(bos);           
            oos.writeObject(obj);          
            oos.flush();           
            bytes = bos.toByteArray();        
            oos.close();           
            bos.close();          
        } catch (IOException ex) {          
            ex.printStackTrace();     
        }        
        return bytes;    
    }
}
