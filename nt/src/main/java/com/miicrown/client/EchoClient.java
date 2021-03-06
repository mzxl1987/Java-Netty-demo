package com.miicrown.client;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.miicrown.protocol.LoginProtocol;
import com.miicrown.protocol.Protocol;
import com.miicrown.protocol.ProtocolEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {

	public static ConcurrentHashMap<ChannelId, ChannelHandlerContext> Items = new ConcurrentHashMap<ChannelId, ChannelHandlerContext>();
	
	private final String host;
	private final int port;
	
	public static final Protocol login;
	public static final ByteBuf sendData;
	
	static{
		
		login = new LoginProtocol(LoginProtocol.TYPE);
		login.setLength(1);
		login.setType(LoginProtocol.TYPE);
		login.setContent(new byte[]{(byte)0xEE});
		login.encodeVerification(login.getContent());
		
		sendData = login.toByteBuf();
		
	}
	
	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
		
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
					.handler(new ChannelInitializer<Channel>() {

						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline()
							.addLast(new EchoClientHandler());
						}
					});

			ChannelFuture cf = b.connect().sync();
			cf.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}

	}

	public static void main(String[] args) throws Exception {
		
		for(int i =0,max=30; i < max; i++){
			new Thread(new Runnable() {
				public void run() {
					try {
						new EchoClient("127.0.0.1", 7005).start();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}).start();

		}
		
		new Thread(new Runnable() {
			
			public void run() {
				while(true){
					Iterator<ChannelHandlerContext> it = Items.values().iterator();
					while(it.hasNext()){
						
						ChannelHandlerContext ctx = it.next();
						
						ctx.writeAndFlush(login.toByteBuf());
						
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}).start();
		
	}

}
