package com.routon.idr.compare;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.routon.idr.idrconst.COMP_RESULT;
import com.routon.idr.idrconst.CompareType;
import com.routon.idr.idrconst.RabbitMQNetBean;
import com.routon.idr.rabbitmq.FACE_COMPARE_IMG_JOB_RESULT;

/**
 * 人脸1：1消息接收
 * @author wangxiwei93
 *
 */

public class RecvMessageTask/* extends ConnectionFactoryAbstract */implements Runnable{

	private final String FACE_COMPARE2_JOB_RESULT_EXCHANGE = "routon.face.compare2.job.result";
	
	private static String mServ_addr = null;
	private static int mServ_port = 0;
	private static String mServ_username = null;
	private static String mServ_password = null;
	private static int mConnTimeout = 1000; // ms
	//private static int RECONN_DELAY = 100; // ms
	private int mTimeoutDelivery = 5000; // ms
	//private static int RECONN_TIMES = 3;
/*	private iDRConnectionFactory factory = null;*/
	private static Connection conn = null;
	private Channel channel = null;
	private INetInterfaceCallBack mCompareCallback = null;
	
	private final String EXCHANGE_TYPE = "direct";
	CompareType mCompType = CompareType.net_1v1_v100;
	
	@Resource(name = "connectionFactory")
	private CachingConnectionFactory factory;
	
	public void setCallback(INetInterfaceCallBack cb){
		this.mCompareCallback = cb;
	}
	
	@Override
	public void run() {
		System.out.println("启动接收线程:"+ Thread.currentThread().getName());
		COMP_RESULT commRes = COMP_RESULT.unknown;
		String CONSUME_EXCHANGE = FACE_COMPARE2_JOB_RESULT_EXCHANGE;
		QueueingConsumer consumer = null;
		FACE_COMPARE_IMG_JOB_RESULT m1v1Result = null;
		String BINGING_KEY = "";
		int COMM_PROTO_VER = 0x100;
		BINGING_KEY = "47276010";
		
		try{
		/*	factory = new iDRConnectionFactory();*/
	/*		setFactoryParam(factory);*/
			QueueingConsumer.Delivery delivery = null;		
				if(conn == null){
					/*conn = rabbitMQConnect(factory);*/
					conn = factory.createConnection();
				}
				if (conn != null && conn.isOpen()){
					/*if(channel == null){*/
						channel = conn.createChannel(false);
						if(channel != null){
							//Enables publisher acknowledgements on this channel.
							channel.confirmSelect();
							// 设置限速。在多个消费者共享一个队列的案例中，明确指定在收到下一个确认回执前每个消费者一次可以接受多少条消息
							channel.basicQos(1);
							channel.exchangeDeclare(CONSUME_EXCHANGE,EXCHANGE_TYPE);
							boolean durable = true;
							boolean exclusive = false;
							boolean autoDelete = true;
							DeclareOk q = channel.queueDeclare(
									BINGING_KEY/* mQueueName */, durable, exclusive, autoDelete, null);
							channel.queueBind(q.getQueue(), CONSUME_EXCHANGE, q.getQueue());
							consumer = new QueueingConsumer(channel);

							boolean autoAck = false;
							channel.basicConsume(q.getQueue(),
									autoAck, consumer);
						} else{
							commRes = COMP_RESULT.fail_connect_exception;
						}
						if(consumer != null){
							try {
								delivery = consumer.nextDelivery(/*mTimeoutDelivery*/);
								if (delivery != null){
									byte[] data_recved = delivery.getBody();
									// 确认消息，已经收到
									channel.basicAck(delivery.getEnvelope()
											.getDeliveryTag(), false);
									if (data_recved != null){
										if(mCompType == CompareType.net_1v1_v100){
											m1v1Result = new FACE_COMPARE_IMG_JOB_RESULT();
											m1v1Result.comp_result = GetSimilarProcFor1V1(
													data_recved, m1v1Result, false);
											m1v1Result.version = COMM_PROTO_VER;
											System.out.println("received message[job_id = " + m1v1Result.job_id + ",bindkey = "+ BINGING_KEY +",scores = " + m1v1Result.score + "]");
											//通过回调返回m1v1Result
											mCompareCallback.onCompareFinished(m1v1Result);
/*											channel.close();
											conn.close();*/
										}
									}
								} else {
									System.out.println("not received message in " + mTimeoutDelivery + " ms, delivery timeout , not blocking anymore...");
									m1v1Result = new FACE_COMPARE_IMG_JOB_RESULT();
									m1v1Result.comp_result = COMP_RESULT.fail_data_null;
									//通过回调返回m1v1Result
									mCompareCallback.onCompareFinished(m1v1Result);
/*									channel.close();
									conn.close();*/
								}
							} catch (ShutdownSignalException e) {
								e.printStackTrace();
								commRes = COMP_RESULT.fail_connect_exception;
				
							} catch (ConsumerCancelledException e) {
								e.printStackTrace();
								commRes = COMP_RESULT.fail_connect_exception;
			
							} catch (InterruptedException e) {
								e.printStackTrace();
								commRes = COMP_RESULT.fail_connect_exception;
		
							}
						}
				
/*				}
				else {
					commRes = COMP_RESULT.fail_connect_exception;
			 } */
			}
		}
			catch(IOException e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void setFactoryParam(ConnectionFactory factory) {
		if (factory != null) {
			factory.setHost(mServ_addr);
			factory.setPort(mServ_port);
			factory.setUsername(mServ_username);
			factory.setPassword(mServ_password);
			factory.setConnectionTimeout(mConnTimeout);
			factory.setRequestedHeartbeat(10); // Enabling Heartbeats 120->10
		}
	}
	
/*	protected static Connection rabbitMQConnect(ConnectionFactory factory) {
		Connection conn = null;

		if ((mServ_addr != null) && (factory != null) {
			int retry_times = 0;
			while (retry_times < RECONN_TIMES) {
				try {
					conn = factory.newConnection();
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				retry_times++;
				try {
					Thread.sleep(RECONN_DELAY, 0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return conn;
	}*/
	
	public COMP_RESULT GetSimilarProcFor1V1(byte[] data_recved,
			FACE_COMPARE_IMG_JOB_RESULT faceCompareImgResult,
			boolean is_filter_diff_jobid) {

		COMP_RESULT commRes = COMP_RESULT.unknown;
		if (data_recved == null) {
			System.out.println("result is null.");
			commRes = COMP_RESULT.fail_dont_receive_data;
		} else {
			System.out.println("data_recved.length=" + data_recved.length);

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
	
	public void Init(CompareType compType, RabbitMQNetBean netBean) {
		if (netBean != null) {
			setParam(netBean.getServ_ip(), netBean.getServ_port(),
					netBean.getServ_username(), netBean.getServ_password());
			/*setConnTimeout(netBean.getConn_timeout_ms());
			setReceiveDataTimeout(netBean.getRecv_data_timeout_ms());
			setRetryDeliveryTimes(netBean.getRecv_retry_times());
			setPublishTimeout(netBean.getPublish_timeout());*/
	}
  }
	
	public void setParam(String addr, int port, String user, String pwd) {
		mServ_addr = addr;
		mServ_port = port;
		mServ_username = user;
		mServ_password = pwd;
	}
	
/*	public void close() throws IOException{
		conn.close();
	}*/
}
