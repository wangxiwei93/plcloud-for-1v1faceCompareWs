package com.routon.idr.AMQP;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;

public class ConnectionListenerUtil implements ConnectionListener {

	static Logger logger = Logger.getLogger(ConnectionListener.class);
	
	@Override
	public void onCreate(Connection connection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose(Connection connection) {
		logger.error("connection disconnected!");
		System.out.println("connection disconnected!");
	}

}
