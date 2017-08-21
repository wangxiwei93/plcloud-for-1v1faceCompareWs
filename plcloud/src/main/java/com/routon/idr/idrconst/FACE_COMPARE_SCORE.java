package com.routon.idr.idrconst;

/**
 * 
 * @author wangxiwei93
 *
 */
public class FACE_COMPARE_SCORE {
	private int attribute; // ���ԣ�0-Ա����1-�ÿ� 
	private int face_id;
	private double score;
	
	public static int WANTED_DATA_LENGTH = 16;
	
	public void setAttribute(int attribute){
		this.attribute = attribute;
	}
	public int getAttribute(){
		return this.attribute;
	}
	
	public void setFaceId(int face_id){
		this.face_id = face_id;
	}
	public int getFaceId(){
		return this.face_id;
	}
	
	public void setScore(double score){
		this.score = score;
	}
	public double getScore(){
		return this.score;
	}
}
