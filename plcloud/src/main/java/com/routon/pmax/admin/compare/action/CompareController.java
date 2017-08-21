package com.routon.pmax.admin.compare.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import com.routon.pmax.admin.compare.bean.JsonData;
import com.routon.pmax.admin.compare.bean.JsonResult;
import com.routon.pmax.admin.compare.bean.ResultStatus;

/**
 * 人脸1:1比对控制器
 * @author wangxiwei93
 *
 */
@Controller
public class CompareController{
	
	/*private final String RMPATH = "/compare/";*/
	
	static Logger logger = Logger.getLogger(CompareController.class);
	
	private static ConcurrentHashMap<Integer,FACE_COMPARE_IMG_JOB_RESULT> replyMap = new ConcurrentHashMap<Integer,FACE_COMPARE_IMG_JOB_RESULT>();
	
	//private final Object mLockInputBean = new Object();

	@Resource(name = "CompareProducerBean")
	private CompareProducer compareProducer;
	
	@Resource(name = "CompareConsumerBean")
	private CompareConsumer compareConsumer;
	
/*	@RequestMapping(value = RMPATH + "show")
	public String compare1V1(){
		return "compare/compareResult";
	}
	
	@RequestMapping(value = RMPATH + "vitalSignsDetection")
	public String vitalSignsDetection(){
		return "compare/VitalSignsDetection";
	}*/
	
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
				JsonData data = new JsonData(bak.version, jobId, 0, 0);
				result = new JsonResult(ResultStatus.FAIL_nodata_recevied, data);
			} else{
				String kv = bak.version+";"+ jobId +";" + reply.job_id+";"+ reply.score + ";" + reply.comp_result;
				logger.info("return result:" + kv);
				if(bak.comp_result == COMP_RESULT.fail_data_null){
					JsonData data = new JsonData(bak.version, jobId, 0, 0);
					result = new JsonResult(ResultStatus.FAIL_nodata_recevied, data);
					return result;
				}
				JsonData data = new JsonData(bak.version, jobId, reply.job_id, reply.score);
				result = new JsonResult(ResultStatus.SUCCESS, data);
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
	JsonResult faceComapre(HttpServletRequest request, 
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
	JsonResult faceComaprev2(HttpServletRequest request, 
			@RequestParam(value = "imageA", required = false) MultipartFile fileA,
			@RequestParam(value = "imageB", required = false) MultipartFile fileB,
			@RequestParam(value = "images", required = false) MultipartFile[] file){
		
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
		try {
			BufferedImage i1= ImageIO.read(compareA.getInputStream());
			BufferedImage i2 = ImageIO.read(compareB.getInputStream());
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
				JsonData data = new JsonData(bak.version, jobId, 0, 0);
				result = new JsonResult(ResultStatus.FAIL_nodata_recevied, data);
			} else{
				String kv = bak.version+";"+ jobId +";" + reply.job_id+";"+ reply.score + ";" + reply.comp_result;
				logger.info("return result:" + kv);
				if(bak.comp_result == COMP_RESULT.fail_data_null){
					JsonData data = new JsonData(bak.version, jobId, 0, 0);
					result = new JsonResult(ResultStatus.FAIL_nodata_recevied, data);
					return result;
				}
				JsonData data = new JsonData(bak.version, jobId, reply.job_id, reply.score);
				result = new JsonResult(ResultStatus.SUCCESS, data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@RequestMapping(value = "/v2/facecompare1v1Test")
	public String HTMLtest(){
		return "compare/compareTest";
	}
}
