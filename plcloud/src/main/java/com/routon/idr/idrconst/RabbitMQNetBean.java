package com.routon.idr.idrconst;

/**
 * 
 * @author wangxiwei93
 *
 */
public class RabbitMQNetBean {
	private String serv_ip;			//比较服务器IP地址
	private int serv_port;			//比较服务器端口号
	private String serv_username;	//比较服务器用户名
	private String serv_password;   //比较服务器密码
	private int conn_timeout_ms; 	//连接超时时间,单位ms
	private int recv_data_timeout_ms; //接收数据超时时间,单位ms
	private int recv_retry_times;     //等应答重试次数	
	private int publish_timeout = 1000; //ms
	public RabbitMQNetBean(){  
        
    }	
	
	public String getServ_ip(){
		return serv_ip;
	}
	
	public void setServ_ip(String serv_ip){
		this.serv_ip = serv_ip;
	}
	
	public int getServ_port(){
		return serv_port;
	}
	
	public void setServ_port(int serv_port){
		this.serv_port = serv_port;
	}
	
	public String getServ_username(){
		return serv_username;
	}
	
	public void setServ_username(String serv_username){
		this.serv_username = serv_username;
	}
	
	public String getServ_password(){
		return serv_password;
	}
	
	public void setServ_password(String serv_password){
		this.serv_password = serv_password;
	}
	
	public int getConn_timeout_ms(){
		return conn_timeout_ms;
	}
	
	public void setConn_timeout_ms(int conn_timeout_ms){
		this.conn_timeout_ms = conn_timeout_ms;
	}
	
	public int getRecv_data_timeout_ms(){
		return recv_data_timeout_ms;
	}
	
	public void setRecv_data_timeout_ms(int recv_data_timeout_ms){
		this.recv_data_timeout_ms = recv_data_timeout_ms;
	}
	
	public int getRecv_retry_times() {
		return recv_retry_times;
	}
	
	public void setRecv_retry_times(int recv_retry_times) {
		this.recv_retry_times = recv_retry_times;
	}
	
	public int getPublish_timeout() {
		return this.publish_timeout;
	}
	public void setPublish_timeout(int publish_timeout){
		this.publish_timeout = publish_timeout;
	}
}
