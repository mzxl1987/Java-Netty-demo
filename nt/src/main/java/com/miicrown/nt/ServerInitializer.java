package com.miicrown.nt;

import com.miicrown.protocol.ProtocolDecoder;
import com.miicrown.protocol.ProtocolEncoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class ServerInitializer extends ChannelInitializer<Channel> {
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline()
		.addLast(new ProtocolDecoder())
		.addLast(new ProtocolEncoder())
		.addLast(new EchoServerHandler());

	}

}
