package com.miicrown.server;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.miicrown.protocol.ProtocalMsg;
import com.miicrown.scheduler.CancelableScheduler;
import com.miicrown.scheduler.HashedWheelTimeoutScheduler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(EchoServerHandler.class);
	
	private final CancelableScheduler disconnectScheduler;
	
	public EchoServerHandler(CancelableScheduler scheduler) {
		this.disconnectScheduler = scheduler;
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		super.channelInactive(ctx);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		log.info(" 新的连接 {} ",ctx);
		
		super.channelActive(ctx);
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		log.info( "{} , Server received: {} ",ctx.channel().id().asLongText(),msg);
		
		ProtocalMsg pm = ProtocalMsg.createInstance();
		pm.setHead(ProtocalMsg.HEAD);
		pm.setType(1);
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
