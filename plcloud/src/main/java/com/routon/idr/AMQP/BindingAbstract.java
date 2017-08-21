package com.routon.idr.AMQP;

import javax.annotation.Resource;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

public class BindingAbstract {
	
	@Resource(name = "rabbitAdmin")
	private RabbitAdmin rabbitAdmin;
	
	public BindingAbstract(){
		Queue queue = new Queue("47276012", true, false, true);
		//queue
		rabbitAdmin.declareQueue(queue);
		//exchange 
	    DirectExchange exchange = new DirectExchange("routon.face.compare2.job.result", true, true);
	    rabbitAdmin.declareExchange(exchange);
	    //binding
	    rabbitAdmin.declareBinding(
	        BindingBuilder.bind(queue).to(exchange).with(""));
	}
}
