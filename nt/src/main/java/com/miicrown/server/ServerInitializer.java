package com.miicrown.server;

import com.miicrown.protocol.ProtocolDecoder;
import com.miicrown.protocol.ProtocolEncoder;
import com.miicrown.scheduler.CancelableScheduler;
import com.miicrown.scheduler.HashedWheelTimeoutScheduler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class ServerInitializer extends ChannelInitializer<Channel> {
	
	private CancelableScheduler scheduler = new HashedWheelTimeoutScheduler();
	
	private EchoServerHandler echoServerHandler;
	
	public void start(){
		echoServerHandler = new EchoServerHandler(scheduler);
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		
		ch.pipeline()
		.addLast(new ProtocolDecoder())
		.addLast(new ProtocolEncoder())
		.addLast(echoServerHandler);

	}

}
