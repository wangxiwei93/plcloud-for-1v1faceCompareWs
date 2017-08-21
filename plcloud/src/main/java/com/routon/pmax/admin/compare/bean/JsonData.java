package com.routon.pmax.admin.compare.bean;

public class JsonData {
	
	public int version = 0;
	
	public int jobid = 0;
	
	public int replyJobId = 0;
	
	public double scores = 0.0;

	public JsonData(int version, int jobId, int job_id, double score) {
		this.version = version;
		this.jobid = jobId;
		this.replyJobId = job_id;
		this.scores = score;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getJobid() {
		return jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	public int getReplyJobId() {
		return replyJobId;
	}

	public void setReplyJobId(int replyJobId) {
		this.replyJobId = replyJobId;
	}

	public double getScores() {
		return scores;
	}

	public void setScores(double scores) {
		this.scores = scores;
	}

	
	
}
