package com.routon.idr.rabbitmq;

import java.io.IOException;
import java.net.Socket;

import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * @author wangxiwei93
 *
 */
public class iDRConnectionFactory extends ConnectionFactory {

	@Override
	protected void configureSocket(Socket socket) throws IOException {
		socket.setKeepAlive(true);
		socket.setSoTimeout(5000);
		super.configureSocket(socket);
	}
	
}
