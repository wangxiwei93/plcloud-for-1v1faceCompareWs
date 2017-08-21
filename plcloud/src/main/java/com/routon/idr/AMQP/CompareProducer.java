package com.routon.idr.AMQP;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.routon.idr.idrconst.FACE_IMAGE2_DATA;
import com.routon.idr.rabbitmq.FACE_COMPARE_IMG_JOB_RESULT;

/**
 * 
 * @author wangxiwei93
 *
 */
public class CompareProducer implements INetInterfaceCallBack{
	
	static Logger logger = Logger.getLogger(CompareProducer.class);
	
	// 人脸1:1比对图片MQ交换机定义
	private final String FACE_IMAGE2_DATA_EXCHANGE = "img2.face.data";
	private final String FACE_IMAGE2_DATA_EXCHANGE_KEY = "img2.face.data.key";
	
	/*private FACE_COMPARE_IMG_JOB_RESULT finalresult = null;*/
	private static ConcurrentHashMap<Integer,FACE_COMPARE_IMG_JOB_RESULT> finalresult = new ConcurrentHashMap<Integer, FACE_COMPARE_IMG_JOB_RESULT>();
	
	/*private volatile boolean flag = false;*/
	private static ConcurrentHashMap<Integer,Boolean> finishedStatus = new ConcurrentHashMap<Integer, Boolean>();
	
	public static String mQueueName1V1 = "";
	
	
	@Resource(name = "rabbitAdmin")
	private RabbitAdmin rabbitAdmin;
	
/*	@Resource(name = "directExchange")
	private DirectExchange exchange;
	
	@Resource(name = "queue")
	private Queue queue;*/
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	public CompareProducer(){
		mQueueName1V1 = "47276010";
	}
	
	@Override
	public void onCompareFinished(int jobId, FACE_COMPARE_IMG_JOB_RESULT result) {
		/*finalresult = result;*/
		finalresult.put(jobId, result);
		/*flag = true;*/
		finishedStatus.put(result.job_id, true);
	}

	public FACE_COMPARE_IMG_JOB_RESULT sendMessage(FACE_IMAGE2_DATA face_image2_data) throws Exception{
		
/*		//queue
		rabbitAdmin.declareQueue(queue);
		//exchange 
	    rabbitAdmin.declareExchange(exchange);
	    //binding
	    rabbitAdmin.declareBinding(
	        BindingBuilder.bind(queue).to(exchange).with("47276012"));*/
		int i = 0;
		int postJobId = face_image2_data.job_id;
		finishedStatus.put(postJobId, false);
		logger.info("send Jobid:" + face_image2_data.job_id);
		logger.info("queueName and routingKey for received:" + mQueueName1V1);
		String PUBLISH_EXCHANGE = "";
        String BINGING_KEY = "";
        PUBLISH_EXCHANGE = FACE_IMAGE2_DATA_EXCHANGE;
        BINGING_KEY = FACE_IMAGE2_DATA_EXCHANGE_KEY;
        ByteBuffer msg = generate1v1PostMsgV100(face_image2_data);
        logger.info("publish exchange = "+ FACE_IMAGE2_DATA_EXCHANGE + ", routingKey = " + FACE_IMAGE2_DATA_EXCHANGE_KEY);
		rabbitTemplate.convertAndSend(PUBLISH_EXCHANGE, BINGING_KEY, msg.array());
		boolean flag = finishedStatus.get(postJobId);
		while(!flag){
			Thread.sleep(50);
			try{
				flag = finishedStatus.get(postJobId);
			} catch (NullPointerException e){
				logger.debug("出现错误的postId：" + postJobId);
			    i++;
			    logger.debug("空指针异常的情况在while中循环了" + i + "次");
				e.printStackTrace();
			}
		}
		finishedStatus.remove(postJobId);
		/*flag = false;*/
		return finalresult.get(postJobId);
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
		long time = System.nanoTime();
		Random random = new Random(time);
		int randomInt = random.nextInt(89999999) + 10000000;
		logger.info("generateQueueName:" + randomInt);
		return randomInt;
	}
}
