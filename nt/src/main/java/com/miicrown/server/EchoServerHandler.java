package com.miicrown.server;

import java.util.concurrent.atomic.AtomicInteger;

import com.miicrown.protocol.ProtocalMsg;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	
	private AtomicInteger ai = new AtomicInteger(0);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		
		if(!EchoServer.dic_channel.containsKey(ctx.channel().id()))
			EchoServer.dic_channel.put(ctx.channel().id(), ctx.channel());
		
		System.out.println("Dic size:" + EchoServer.dic_channel.size());
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		System.out.println(ctx.channel().id().asLongText() + " , Server received: " + msg + ", Count:" + ai.incrementAndGet());
		
		ProtocalMsg pm = ProtocalMsg.createInstance();
		pm.setHead(ProtocalMsg.HEAD);
		pm.setType(ProtocalMsg.Type.Response.getType());
		byte[] data = new byte[]{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09}; 
		pm.setData(data);
		pm.setLength(data.length);
		pm.setTail(ProtocalMsg.TAIL);
		
		ctx.writeAndFlush(pm);
		
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
//		.addListener(ChannelFutureListener.CLOSE);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		System.out.println("cause Exception!");
		EchoServer.dic_channel.remove(ctx.channel().id());
		System.out.println("Dic size:" + EchoServer.dic_channel.size());
		ctx.close();
	}
	
}
