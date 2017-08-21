package com.routon.idr.compare;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownSignalException;
import com.routon.idr.idrconst.COMP_RESULT;
import com.routon.idr.idrconst.CompareType;
import com.routon.idr.idrconst.FACE_IMAGE2_DATA;
import com.routon.idr.idrconst.RabbitMQNetBean;
import com.routon.idr.rabbitmq.FACE_COMPARE_IMG_JOB_RESULT;
import com.routon.idr.rabbitmq.InputBean;

/**
 * 人脸比对Publisher
 * @author wangxiwei93
 *
 */
public class CommonCompare/* extends ConnectionFactoryAbstract */implements INetInterfaceCallBack{
	
	private static String mServ_addr = null;
	private static int mServ_port = 0;
	private static String mServ_username = null;
	private static String mServ_password = null;
	private static int mConnTimeout = 1000; // ms
/*	private static int RECONN_TIMES = 3;
	private static int RECONN_DELAY = 100; // ms
*/	//private int mTimeoutDelivery = 200; // ms
	
	public static String mQueueName1V1 = "801"; // 在构造函数中生成
//	private iDRConnectionFactory factory;
	private static Connection conn = null;
	private Channel channel;
	// 人脸1:1比对图片MQ交换机定义
	private final String FACE_IMAGE2_DATA_EXCHANGE = "img2.face.data";
	private final String FACE_IMAGE2_DATA_EXCHANGE_KEY = "img2.face.data.key";
	
	private boolean flag = false;
	private FACE_COMPARE_IMG_JOB_RESULT finalresult = null;
	
	@Resource(name = "connectionFactory")
	private CachingConnectionFactory factory;
	
	public CommonCompare(){
		mQueueName1V1 = "47276010"/*generateQueueName()*/;
	}
	
	@Override
	public synchronized void onCompareFinished(FACE_COMPARE_IMG_JOB_RESULT result) {
		finalresult = result;
		flag = true;
	}

	public FACE_COMPARE_IMG_JOB_RESULT Compare1v1FaceSyncProvider(FACE_IMAGE2_DATA face_image2_data) throws Exception {
		
		        String PUBLISH_EXCHANGE = "";
		        String BINGING_KEY = "";
		        
		        PUBLISH_EXCHANGE = FACE_IMAGE2_DATA_EXCHANGE;
		        BINGING_KEY = FACE_IMAGE2_DATA_EXCHANGE_KEY;
				InputBean inputBean = new InputBean();
				inputBean.jobId = face_image2_data.job_id;
				inputBean.msg = generate1v1PostMsgV100(face_image2_data);
				inputBean.compType = CompareType.net_1v1_v100;
				
				COMP_RESULT commRes = COMP_RESULT.unknown;
				
				try{
/*					factory = new iDRConnectionFactory();
					setFactoryParam(factory);*/
					conn = factory.createConnection();
					if(inputBean != null){
						/*conn = rabbitMQConnect(factory);*/
						
						if ((conn != null) && conn.isOpen()) {
							channel = conn.createChannel(false);
							if(channel != null){
								//将当前信道设置成了confirm模式
								channel.confirmSelect();
								ByteBuffer msg = inputBean.msg;
								if(msg != null){
									if ((conn == null) || (!conn.isOpen())
											|| (channel == null)
											|| (!channel.isOpen())) {
										commRes = COMP_RESULT.fail_connect_exception;
									} else {
										System.out.println("call basicPublish PUBLISH_EXCHANGE "
														+ PUBLISH_EXCHANGE
														+ " BINGING_KEY "
														+ BINGING_KEY + "......");

										channel.basicPublish(PUBLISH_EXCHANGE,
												BINGING_KEY, null, msg.array());
										//确保broker收到了消息，如果没有收到消息的话，不会打印以下信息
										if(channel.waitForConfirms()){
											commRes = COMP_RESULT.success;
											System.out.println("1比1比对发送结果：" + commRes);
										}
									}

								}
							  }
							}
					}
				} catch (IOException e) {
					e.printStackTrace();
					// connect 失败会报异常，此处能catch socketTimeout 异常
					commRes = COMP_RESULT.fail_connect_exception;
				} catch (ShutdownSignalException e1) {
					e1.printStackTrace();
					commRes = COMP_RESULT.fail_connect_exception;
				} 
				while(!flag){
					Thread.sleep(100);
				}
				flag = false;
				return finalresult;
			}
	
	/**
	 * 生成1:1图片ByteBuffer
	 * @param img1Info
	 * @param img2Info
	 * @param jobID
	 * @return
	 */
	private ByteBuffer generate1v1PostMsgV100(FACE_IMAGE2_DATA face_image2_data) {
		// Log.d(tag, "generate1v1PostMsg jobID is " + jobID);
		ByteBuffer buff = null;
		if ((face_image2_data != null)
				&& (face_image2_data.image_info_first != null)
				&& (face_image2_data.image_info_first.data != null)
				&& (face_image2_data.image_info_second != null)
				&& (face_image2_data.image_info_second.data != null)) {

			// version + job_id + client_uuid + img1Width + img1Height +
			// img2Width + img2Height + img1BGRbytes + img2BGRbytes
			final int POST_MSG_LEN = (4 + 4 + 32 + 4 + 4 + 4 + 4
					+ face_image2_data.image_info_first.data.length + face_image2_data.image_info_second.data.length);
			buff = ByteBuffer.allocate(POST_MSG_LEN);
			buff.order(ByteOrder.LITTLE_ENDIAN);

			buff.putInt(face_image2_data.version);
			buff.putInt(face_image2_data.job_id);
			byte[] clientUUID = new byte[32];
			byte[] queueNameByte = mQueueName1V1.getBytes();
			System.arraycopy(queueNameByte, 0, clientUUID, 0,
					queueNameByte.length > 32 ? 32 : queueNameByte.length);
			buff.put(clientUUID);

			buff.putInt(face_image2_data.image_info_first.width);
			buff.putInt(face_image2_data.image_info_first.height);
			buff.putInt(face_image2_data.image_info_second.width);
			buff.putInt(face_image2_data.image_info_second.height);
			buff.put(face_image2_data.image_info_first.data);
			buff.put(face_image2_data.image_info_second.data);
		}
		return buff;
	}
	
	public static int generateQueueName() {
		long time = System.currentTimeMillis();
		Random random = new Random(time);
		int randomInt = random.nextInt(89999999) + 10000000;
		return randomInt;
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
	
/*	private static Connection rabbitMQConnect(ConnectionFactory factory) {
		Connection conn = null;

		if ((mServ_addr != null) && factory != null) {
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
	
	public static byte[] image2byte(String path){
		byte[] data = null;
		FileImageInputStream input = null;
		try{
			
		input = new FileImageInputStream(new File(path));
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buf = new byte[1024];
	    int numBytesRead = 0;
	    while ((numBytesRead = input.read(buf)) != -1) {
	      output.write(buf, 0, numBytesRead);
	      }
	      data = output.toByteArray();
	      output.close();
	      input.close();
	    }
	    catch (FileNotFoundException ex1) {
	      ex1.printStackTrace();
	    }
	    catch (IOException ex1) {
	      ex1.printStackTrace();
	    }
	    return data;
	}
	
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
}
