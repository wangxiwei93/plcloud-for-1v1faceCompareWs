package com.routon.idr.compare;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.routon.idr.idrconst.COMP_RESULT;
import com.routon.idr.idrconst.CompareType;
import com.routon.idr.idrconst.FACE_IMAGE2_DATA;
import com.routon.idr.idrconst.FACE_IMAGE_INFO;
import com.routon.idr.rabbitmq.FACE_COMPARE_IMG_JOB_RESULT;
import com.routon.idr.rabbitmq.InputBean;
import com.routon.idr.rabbitmq.iDRConnectionFactory;
import com.routon.idr.tools.ConvertBGR;


/**
 * 发送接收回调方法
 * @author wangxiwei93
 *
 */
public class RecvMessageTaskForCallback{
	
	private iDRConnectionFactory factory = null;
	private Connection conn = null;
	private Channel channel = null;
	private final String FACE_IMAGE2_DATA_EXCHANGE = "img2.face.data";
	private final String FACE_IMAGE2_DATA_EXCHANGE_KEY = "img2.face.data.key";
	public String mQueueName1V1 = "801"; // 在构造函数中生成
	CompareType mCompType = CompareType.net_1v1_v100;
	private static int mConnTimeout = 1000;
	final static BlockingQueue<FACE_COMPARE_IMG_JOB_RESULT> response = new ArrayBlockingQueue<FACE_COMPARE_IMG_JOB_RESULT>(1);

	public RecvMessageTaskForCallback() throws IOException {
		super();
		mQueueName1V1 = "47376010"/*generateQueueName()*/;
	}
	
	public FACE_COMPARE_IMG_JOB_RESULT SendAndCallback(FACE_IMAGE2_DATA face_image2_data) throws IOException, InterruptedException{
		
		factory = new iDRConnectionFactory();
		factory.setHost("172.16.42.23");
		factory.setPort(5672);
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setConnectionTimeout(mConnTimeout);
		factory.setRequestedHeartbeat(10);
		conn = factory.newConnection();
		channel = conn.createChannel();
		String PUBLISH_EXCHANGE = "";
        String BINGING_KEY = "";
        
        ByteBuffer msg = generate1v1PostMsgV100(face_image2_data);
        
        PUBLISH_EXCHANGE = FACE_IMAGE2_DATA_EXCHANGE;
        BINGING_KEY = FACE_IMAGE2_DATA_EXCHANGE_KEY;
		final InputBean inputBean = new InputBean();
		inputBean.jobId = face_image2_data.job_id;
		inputBean.msg = generate1v1PostMsgV100(face_image2_data);
		inputBean.compType = CompareType.net_1v1_v100;
		
		final int COMM_PROTO_VER = 0x100;
				
		try {
			//将当前信道设置成了confirm模式
			channel.confirmSelect();
			System.out.println("publish .........");
			channel.basicPublish(PUBLISH_EXCHANGE, BINGING_KEY, null, msg.array());
			
			if(channel.waitForConfirms()){
				System.out.println("send messgage[jobid = " + inputBean.jobId+ "]");
			}
			
			//Enables publisher acknowledgements on this channel.
			channel.confirmSelect();
			// 设置限速。在多个消费者共享一个队列的案例中，明确指定在收到下一个确认回执前每个消费者一次可以接受多少条消息
			channel.basicQos(1);
			channel.exchangeDeclare("routon.face.compare2.job.result" , "direct");
			boolean durable = true;
			boolean exclusive = false;
			boolean autoDelete = true;
			DeclareOk q = channel.queueDeclare(
					mQueueName1V1, durable, exclusive, autoDelete, null);
			channel.queueBind(q.getQueue(), "routon.face.compare2.job.result", q.getQueue());

			boolean autoAck = false;
			
			channel.basicConsume(mQueueName1V1, autoAck, new DefaultConsumer(channel) {
	            @Override
	            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	            	FACE_COMPARE_IMG_JOB_RESULT m1v1Result = new FACE_COMPARE_IMG_JOB_RESULT();
						// 确认消息，已经收到
						channel.basicAck(envelope
								.getDeliveryTag(), false);
						if (body != null){
							if(mCompType == CompareType.net_1v1_v100){
								//m1v1Result = new FACE_COMPARE_IMG_JOB_RESULT();
								m1v1Result.comp_result = GetSimilarProcFor1V1(
										body, m1v1Result, false);
								m1v1Result.version = COMM_PROTO_VER;
								response.offer(m1v1Result);

							}
						}
	            }
	        });
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.take();
		
	}
	
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
	
	private static int generateQueueName() {
		long time = System.currentTimeMillis();
		Random random = new Random(time);
		int randomInt = random.nextInt(89999999) + 10000000;
		return randomInt;
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
		System.out.println("Actual received value:" + faceCompareImgResult.job_id + "," + faceCompareImgResult.comp_result + 
				"," + faceCompareImgResult.score);
		return commRes;
	}
	
	public static void main(String[] args) throws IOException{
		BufferedImage i1= ImageIO.read(new FileInputStream("C:\\Users\\wangxiwei93\\Desktop\\3.jpg"));
		BufferedImage i2 = ImageIO.read(new FileInputStream("C:\\Users\\wangxiwei93\\Desktop\\4.jpg"));
		int jobId = generateQueueName();
		FACE_IMAGE_INFO image1 = new FACE_IMAGE_INFO();
		image1.height = i1.getHeight();
		image1.width = i1.getWidth();
		image1.data = ConvertBGR.getMatrixBGR(i1);
		FACE_IMAGE_INFO image2 = new FACE_IMAGE_INFO();
		image2.height = i2.getHeight();
		image2.width = i2.getWidth();
		image2.data = ConvertBGR.getMatrixBGR(i2);
		FACE_IMAGE2_DATA face_image2_data = new FACE_IMAGE2_DATA();
		face_image2_data.job_id = jobId;
		face_image2_data.image_info_first = image1;
		face_image2_data.image_info_second = image2;
		RecvMessageTaskForCallback commonCompare = new RecvMessageTaskForCallback();
		try {
			FACE_COMPARE_IMG_JOB_RESULT r = commonCompare.SendAndCallback(face_image2_data);
			System.out.println("return message[job_id = " + r.job_id + ",status = " + r.comp_result + ",scores = " + r.score + "]");
			System.out.println("-----end------");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
