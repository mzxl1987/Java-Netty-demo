package com.miicrown.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		EchoClient.Items.put(ctx.channel().id(), ctx);
		System.out.println("Items Size:" + EchoClient.Items.size());
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext chc, ByteBuf in) throws Exception {
		System.out.println("client received: " + ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		EchoClient.Items.remove(ctx.channel().id());
		System.out.println("Items Size:" + EchoClient.Items.size());
		
		ctx.close();
	}

}
