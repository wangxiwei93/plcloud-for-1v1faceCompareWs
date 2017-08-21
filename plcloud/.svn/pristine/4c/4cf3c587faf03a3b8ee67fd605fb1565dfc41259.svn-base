package com.routon.idr.compare;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.routon.idr.rabbitmq.iDRConnectionFactory;

/**
 * 连接MQ抽象类，连接时继承该类
 * @author wangxiwei93
 *
 */
public abstract class ConnectionFactoryAbstract {
	protected Connection conn;
	protected Channel channel;
	protected iDRConnectionFactory factory;
	//protected String replyQueueName;
	protected static int mConnTimeout = 1000; // ms
	
	public ConnectionFactoryAbstract() throws IOException{
		factory = new iDRConnectionFactory();
		factory.setHost("172.16.42.125");
		factory.setPort(5672);
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setConnectionTimeout(mConnTimeout);
		factory.setRequestedHeartbeat(10);
		conn = factory.newConnection();
		channel = conn.createChannel();
//		replyQueueName = channel.queueDeclare().getQueue();
	}
	
	/**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     * @throws IOException
     */
     public void close() throws IOException{
         this.channel.close();
         this.conn.close();
     }
}
