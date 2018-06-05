package com.routon.pmax.admin.compare.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.routon.idr.AMQP.CompareConsumer;
import com.routon.idr.AMQP.CompareProducer;
import com.routon.idr.idrconst.COMP_RESULT;
import com.routon.idr.idrconst.FACE_IMAGE2_DATA;
import com.routon.idr.idrconst.FACE_IMAGE_INFO;
import com.routon.idr.rabbitmq.FACE_COMPARE_IMG_JOB_RESULT;
import com.routon.idr.tools.ConvertBGR;
import com.routon.pmax.admin.compare.bean.JsonResult;
import com.routon.pmax.admin.compare.bean.ResultStatus;

import Decoder.BASE64Decoder;

/**
 * 人脸1:1比对控制器
 * @author wangxiwei93
 *
 */
@Controller
public class CompareController{
	
	/*private final String RMPATH = "/compare/";*/
	
	static Logger logger = Logger.getLogger(CompareController.class);
	
	public static FACE_IMAGE2_DATA face_image2_data = null;
	
	private static ConcurrentHashMap<Integer,FACE_COMPARE_IMG_JOB_RESULT> replyMap = new ConcurrentHashMap<Integer,FACE_COMPARE_IMG_JOB_RESULT>();
	
	public final static String APP_ID = "43A6AA5473AE4D8BB05ED89E8C692B90";
	
	public final static String APP_SECRET = "87839397994A45DC8A9F350D838A8270";
	
	//private final Object mLockInputBean = new Object();

	@Resource(name = "CompareProducerBean")
	private CompareProducer compareProducer;
	
	@Resource(name = "CompareConsumerBean")
	private CompareConsumer compareConsumer;
	
	public JsonResult getTwoImagePath(String imagepath, int jobId) throws Exception, IOException{
		JsonResult result = null;
		String[] args = imagepath.toString().split(";");
		logger.info("upload file number:" + args.length);
		try {
			BufferedImage i1= ImageIO.read(new FileInputStream(args[0]));
			BufferedImage i2 = ImageIO.read(new FileInputStream(args[1]));
			/*int jobId = CompareProducer.generateQueueName();*/
			
			logger.info("begin compare");
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
			//设置回调并发送1:1比对
			compareConsumer.setCallback(compareProducer);
			FACE_COMPARE_IMG_JOB_RESULT bak = compareProducer.sendMessage(face_image2_data);
		/*	synchronized (mLockInputBean) {*/
				replyMap.put(bak.job_id, bak);
			/*}*/
			FACE_COMPARE_IMG_JOB_RESULT reply = replyMap.get(jobId);
			replyMap.remove(jobId);
			if(reply == null){
				logger.info("no data received!!!");
				//JsonData data = new JsonData(bak.version, jobId, 0, 0);
				result = new JsonResult(ResultStatus.FAIL_nodata_recevied, 0.0);
			} else{
				String kv = bak.version+";"+ jobId +";" + reply.job_id+";"+ reply.score + ";" + reply.comp_result;
				logger.info("return result:" + kv);
				if(bak.comp_result == COMP_RESULT.fail_data_null){
					//JsonData data = new JsonData(bak.version, jobId, 0, 0);
					result = new JsonResult(ResultStatus.FAIL_nodata_recevied, 0.0);
					return result;
				}
				//JsonData data = new JsonData(bak.version, jobId, reply.job_id, reply.score);
				result = new JsonResult(ResultStatus.SUCCESS, reply.score);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 版本v1,先保存在本地，用于返回JSON数据
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/v1/facecompare1v1")
	public @ResponseBody
	JsonResult faceCompare(HttpServletRequest request, 
			@RequestParam(value = "imageA", required = false) MultipartFile fileA,
			@RequestParam(value = "imageB", required = false) MultipartFile fileB,
			@RequestParam(value = "images", required = false) MultipartFile[] file) throws IOException, Exception{
		
		ThreadLocal<Long> start = new ThreadLocal<Long>();
		ThreadLocal<Long> end = new ThreadLocal<Long>();
		JsonResult result = null;
		logger.info("begin---------------------------");
		StringBuffer twoImagePath = new StringBuffer();
		int jobId = CompareProducer.generateQueueName();
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/" + jobId + "/";
		
/*		if(fileA == null){
			result = new JsonResult(ResultStatus.IMAGEA_NOT_EXIST);
			return result;
		}
		if(fileB.isEmpty()){
			result = new JsonResult(ResultStatus.IMAGEB_NOT_EXIST);
			return result;
		}*/
		start.set(System.currentTimeMillis());
		if(fileA != null && fileB != null){
			String imageA = fileA.getOriginalFilename();
			File imageFileA = new File(filePath + imageA);
			String imageB = fileB.getOriginalFilename();
			File imageFileB = new File(filePath + imageB);
			twoImagePath.append(filePath + imageA).append(";").append(filePath + imageB);
			if(!imageFileA.exists()){
				imageFileA.mkdirs();
				logger.info("------uploading file:" + (filePath + imageA));
				fileA.transferTo(imageFileA);
			}
			if(!imageFileB.exists()){
				imageFileB.mkdirs();
				logger.info("------uploading file:" + (filePath + imageB));
				fileB.transferTo(imageFileB);
			}
			
		} 
		if(file.length == 2){
			for(int i = 0; i < file.length; i++){
				String filename = file[i].getOriginalFilename();
				File targetFile = new File(filePath + filename);
				twoImagePath.append(filePath + filename).append(";");
				if(!targetFile.exists()){  
		            targetFile.mkdirs();
		            try {
		            	logger.info("------uploading file:" + (filePath + filename));
						file[i].transferTo(targetFile);
						/*twoImagePath.append(filePath + filename).append(";");*/
					} catch (IllegalStateException e) {
						logger.error("upload failed!");
						e.printStackTrace();
					} catch (IOException e) {
						logger.error("upload failed!");
						e.printStackTrace();
					}
		        } else{
		        	logger.info(filename + " already in uploadfolder!!!");
		        }
			}
		}
		end.set(System.currentTimeMillis());
		logger.info("上传图片耗时：" + (end.get() - start.get()) + "ms");
		result = getTwoImagePath(twoImagePath.toString(), jobId);
		logger.info("end---------------------------");
		return result;
	}
	
	/**
	 * 版本v2,不保存本地，直接读取流，用于返回JSON数据
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/v2/facecompare1v1")
	public @ResponseBody
	JsonResult faceCompare2(HttpServletRequest request, 
			@RequestParam(value = "imageA", required = false) MultipartFile fileA,
			@RequestParam(value = "imageB", required = false) MultipartFile fileB,
			@RequestParam(value = "images", required = false) MultipartFile[] file){
		
		long bAllTime = System.currentTimeMillis();
		logger.info("step1:function has received data---");
		long begin = System.currentTimeMillis();
		JsonResult result = null;
		int jobId = CompareProducer.generateQueueName();
		MultipartFile compareA = null;
		MultipartFile compareB = null;
		if(fileA != null && fileB != null){
			compareA = fileA;
			compareB = fileB;
		}
		if(file.length == 2){
			compareA = file[0];
			compareB = file[1];
		}
		long end = System.currentTimeMillis();
		logger.info("step1 costs [" + (end - begin) + "]ms");
		try {
			logger.info("step2:begin calculate the time of stream to BufferedImage:");
			long bTime = System.currentTimeMillis();
			BufferedImage i1= ImageIO.read(compareA.getInputStream());
			BufferedImage i2 = ImageIO.read(compareB.getInputStream());
			long eTime = System.currentTimeMillis();
			logger.info("step2 costs [" + (eTime - bTime) + "]ms");
			/*int jobId = CompareProducer.generateQueueName();*/
			
			FACE_IMAGE_INFO image1 = new FACE_IMAGE_INFO();
			image1.height = i1.getHeight();
			image1.width = i1.getWidth();
			logger.info("step3:convert image into BGR:");
			long bBgrTime = System.currentTimeMillis();
			image1.data = ConvertBGR.getMatrixBGR(i1);
			FACE_IMAGE_INFO image2 = new FACE_IMAGE_INFO();
			image2.height = i2.getHeight();
			image2.width = i2.getWidth();
			image2.data = ConvertBGR.getMatrixBGR(i2);
			long eBgrTime = System.currentTimeMillis();
			//System.out.println(Arrays.toString(image1.data));
			logger.info("step3 costs [" + (eBgrTime - bBgrTime) + "]ms");
			FACE_IMAGE2_DATA face_image2_data = new FACE_IMAGE2_DATA();
			face_image2_data.job_id = jobId;
			face_image2_data.image_info_first = image1;
			face_image2_data.image_info_second = image2;
			logger.info("step4:begin compare:");
			long bCompareTime = System.currentTimeMillis();
			//设置回调并发送1:1比对
			compareConsumer.setCallback(compareProducer);
			FACE_COMPARE_IMG_JOB_RESULT bak = compareProducer.sendMessage(face_image2_data);
			long eCompareTime = System.currentTimeMillis();
			logger.info("step4 costs [" + (eCompareTime - bCompareTime) + "]ms");
			
			logger.info("step5:begin put into concurrentHashMap:");
			long bMapTime = System.currentTimeMillis();
		/*	synchronized (mLockInputBean) {*/
				replyMap.put(bak.job_id, bak);
			/*}*/
			FACE_COMPARE_IMG_JOB_RESULT reply = replyMap.get(jobId);
			replyMap.remove(jobId);
			if(reply == null){
				logger.info("no data received!!!");
				//JsonData data = new JsonData(bak.version, jobId, 0, 0);
				result = new JsonResult(ResultStatus.FAIL_nodata_recevied, 0.0);
			} else{
				String kv = bak.version+";"+ jobId +";" + reply.job_id+";"+ reply.score + ";" + reply.comp_result;
				logger.info("return result:" + kv);
				if(bak.comp_result == COMP_RESULT.fail_data_null){
					//JsonData data = new JsonData(bak.version, jobId, 0, 0);
					result = new JsonResult(ResultStatus.FAIL_nodata_recevied, 0.0);
					return result;
				}
				//JsonData data = new JsonData(bak.version, jobId, reply.job_id, reply.score);
				result = new JsonResult(ResultStatus.SUCCESS, reply.score);
			}
			long eMapTime = System.currentTimeMillis();
			logger.info("step5 costs [" + ( eMapTime -  bMapTime) + "]ms");

		} catch (Exception e) {
			e.printStackTrace();
		}
		long eAllTime = System.currentTimeMillis();
		logger.info("function costs [" + (eAllTime - bAllTime) + "]ms");
		return result;
		
	}
	
	@RequestMapping(value = "/v2/facecompare1v1NoBGR")
	public @ResponseBody
	JsonResult faceCompare2NoBGR(HttpServletRequest request){
		
		JsonResult result = null;
		int jobId = CompareProducer.generateQueueName();
		long bAllTime = System.currentTimeMillis();
		try {
			face_image2_data.job_id = jobId;
			logger.info("step4:begin compare:");
			long bCompareTime = System.currentTimeMillis();
			//设置回调并发送1:1比对
			compareConsumer.setCallback(compareProducer);
			FACE_COMPARE_IMG_JOB_RESULT bak = compareProducer.sendMessage(face_image2_data);
			long eCompareTime = System.currentTimeMillis();
			logger.info("step4 costs [" + (eCompareTime - bCompareTime) + "]ms");
			
			logger.info("step5:begin put into concurrentHashMap:");
			long bMapTime = System.currentTimeMillis();
		/*	synchronized (mLockInputBean) {*/
				replyMap.put(bak.job_id, bak);
			/*}*/
			FACE_COMPARE_IMG_JOB_RESULT reply = replyMap.get(jobId);
			replyMap.remove(jobId);
			if(reply == null){
				logger.info("no data received!!!");
				//JsonData data = new JsonData(bak.version, jobId, 0, 0);
				result = new JsonResult(ResultStatus.FAIL_nodata_recevied, 0.0);
			} else{
				String kv = bak.version+";"+ jobId +";" + reply.job_id+";"+ reply.score + ";" + reply.comp_result;
				logger.info("return result:" + kv);
				if(bak.comp_result == COMP_RESULT.fail_data_null){
					//JsonData data = new JsonData(bak.version, jobId, 0, 0);
					result = new JsonResult(ResultStatus.FAIL_nodata_recevied, 0.0);
					return result;
				}
				//JsonData data = new JsonData(bak.version, jobId, reply.job_id, reply.score);
				result = new JsonResult(ResultStatus.SUCCESS, reply.score);
			}
			long eMapTime = System.currentTimeMillis();
			logger.info("step5 costs [" + ( eMapTime -  bMapTime) + "]ms");

		} catch (Exception e) {
			e.printStackTrace();
		}
		long eAllTime = System.currentTimeMillis();
		logger.info("function costs [" + (eAllTime - bAllTime) + "]ms");
		return result;
		
	}
	
	/**
	 * 1v1测试页面
	 * @return
	 */
	@RequestMapping(value = "/v2/facecompare1v1Test")
	public String HTMLtest(){
		return "compare/compareTest";
	}
	
	
	/**
	 * 版本v3，接收比对图片的Base64信息
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value = /*"/v3/facecompare1v1"*/ "/face/tool/compare")
	public @ResponseBody
	JsonResult faceCompareOffical(HttpServletRequest request, 
			@RequestParam(value = "imgA") String fileA,
			@RequestParam(value = "imgB") String fileB,
			@RequestParam(value = "app_id") String app_id,
			@RequestParam(value = "app_secret") String app_secret){
		
		JsonResult result = null;
		
		if(fileA == null){
			result = new JsonResult(ResultStatus.IMAGEA_NOT_EXIST, 0.0);
		}
		if(fileB == null){
			result = new JsonResult(ResultStatus.IMAGEB_NOT_EXIST, 0.0);
		}
		if(app_id == null || !APP_ID.equals(app_id)){
			//JsonData data = new JsonData(1, 0, 0, 0);
			result = new JsonResult(ResultStatus.APPID_NOT_EXIST, 0.0);
			return result;
		}
		if(app_secret == null || !APP_SECRET.equals(app_secret)){
			//JsonData data = new JsonData(1, 0, 0, 0);
			result = new JsonResult(ResultStatus.APPSECRET_NOT_EXIST, 0.0);
			return result;
		}
		long btime = System.currentTimeMillis();
		int jobId = CompareProducer.generateQueueName();
		long Base64toISbTime = System.currentTimeMillis();
		InputStream isA = BaseToInputStream(fileA);
		InputStream isB = BaseToInputStream(fileB);
		try {
			if(isA.available() >= 3 * 1024 * 1024 || isB.available() >= 3 * 1024 * 1024){
				//JsonData data = new JsonData(1, 0, 0, 0);
				result = new JsonResult(ResultStatus.IMAGE_TOO_BIG, 0.0);
				return result;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage(), e1);
		}
		long Base64toISeTime = System.currentTimeMillis();
		try {
			long BufferBTime = System.currentTimeMillis();
			BufferedImage i1= ImageIO.read(isA);
			BufferedImage i2 = ImageIO.read(isB);
			long BufferETime = System.currentTimeMillis();
			logger.debug("base64toStream costs:[" + (Base64toISeTime - Base64toISbTime) + "]ms");
			logger.debug("readBuffer costs:[" + (BufferETime - BufferBTime) + "]ms");
			long assembleDataBTime = System.currentTimeMillis();
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
			long assembleDataETime = System.currentTimeMillis();
			logger.debug("assembleData costs:[" + (assembleDataETime - assembleDataBTime) + "]ms");
			long sendDataBtime = System.currentTimeMillis();
			//设置回调并发送1:1比对
			compareConsumer.setCallback(compareProducer);
			FACE_COMPARE_IMG_JOB_RESULT bak = null;
			try {
				bak = compareProducer.sendMessage(face_image2_data);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
			
			if(bak == null){
				return new JsonResult(ResultStatus.SYSTEM_BUSY, 0.0);
			}
			replyMap.put(bak.job_id, bak);

			FACE_COMPARE_IMG_JOB_RESULT reply = replyMap.get(jobId);
			replyMap.remove(jobId);
			
			if(reply == null){
				logger.info("no data received!!!");
				//JsonData data = new JsonData(bak.version, jobId, 0, 0);
				result = new JsonResult(ResultStatus.FAIL_nodata_recevied, 0.0);
			} else{
				String kv = bak.version+";"+ jobId +";" + reply.job_id+";"+ reply.score + ";" + reply.comp_result;
				logger.info("return result:" + kv);
				System.out.println("return result:" + kv);
				if(bak.comp_result == COMP_RESULT.fail_data_null){
					//JsonData data = new JsonData(bak.version, jobId, 0, 0);
					result = new JsonResult(ResultStatus.FAIL_nodata_recevied, 0.0);
					return result;
				}
				//JsonData data = new JsonData(bak.version, jobId, reply.job_id, reply.score);
				result = new JsonResult(ResultStatus.SUCCESS, reply.score);
			}
			
			long receivedDataEtime = System.currentTimeMillis();
			logger.debug("algorithm costs:[" + (receivedDataEtime - sendDataBtime) + "]ms");
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		long etime = System.currentTimeMillis();
		logger.debug("all costs:["+ (etime - btime) + "]ms");
		return result;
				
	}
	
	
	/**
	 * 忽略图片加载和传输时间，测试性能
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/v2/testBgr")
	public @ResponseBody
	String testNoTransferBGR(HttpServletRequest request,
			@RequestParam(value = "images", required = false) MultipartFile[] file){
		
		int jobId = CompareProducer.generateQueueName();
		MultipartFile compareA = null;
		MultipartFile compareB = null;
		if(file.length == 2){
			compareA = file[0];
			compareB = file[1];
		}
			logger.info("step2:begin calculate the time of stream to BufferedImage:");
			long bTime = System.currentTimeMillis();
			
		try {
				
			BufferedImage i1 = ImageIO.read(compareA.getInputStream());
			BufferedImage i2 = ImageIO.read(compareB.getInputStream());
			long eTime = System.currentTimeMillis();
			logger.info("step2 costs [" + (eTime - bTime) + "]ms");
			
			FACE_IMAGE_INFO image1 = new FACE_IMAGE_INFO();
			image1.height = i1.getHeight();
			image1.width = i1.getWidth();
			logger.info("step3:convert image into BGR:");
			long bBgrTime = System.currentTimeMillis();
			image1.data = ConvertBGR.getMatrixBGR(i1);
			FACE_IMAGE_INFO image2 = new FACE_IMAGE_INFO();
			image2.height = i2.getHeight();
			image2.width = i2.getWidth();
			image2.data = ConvertBGR.getMatrixBGR(i2);
			long eBgrTime = System.currentTimeMillis();
			logger.info("step3 costs [" + (eBgrTime - bBgrTime) + "]ms");
			FACE_IMAGE2_DATA face_image2_data = new FACE_IMAGE2_DATA();
			face_image2_data.job_id = jobId;
			face_image2_data.image_info_first = image1;
			face_image2_data.image_info_second = image2;
			CompareController.face_image2_data = face_image2_data;
			} 

			catch (IOException e) {
				e.printStackTrace();
			}
			return "success";
	}
	
	/**
     * base64字符串转文件
     * @param base64
     * @return
     */
	public static InputStream BaseToInputStream(String base64string){  
	    ByteArrayInputStream stream = null;	
	    BASE64Decoder decoder = new BASE64Decoder(); 
	    byte[] bytes1 = null;
		try {
			bytes1 = decoder.decodeBuffer(base64string);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	    stream = new ByteArrayInputStream(bytes1);  
	    return stream;  
	} 
}
