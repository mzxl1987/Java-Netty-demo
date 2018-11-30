package com.miicrown.nt;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

import com.miicrown.protocol.ProtocolDecoder;
import com.miicrown.protocol.ProtocolEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

	public static ConcurrentHashMap<ChannelId, Channel> dic_channel = new ConcurrentHashMap<ChannelId, Channel>();
	
	private final int port;
	
	public EchoServer(int port){
		this.port = port;
	}
	
	public void start() throws Exception{
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try{
			
			ServerBootstrap b = new ServerBootstrap();
			b.group(group)
			.channel(NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(port))
			.childHandler(new ServerInitializer());
			
			ChannelFuture f = b.bind().sync();
			System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
			f.channel().closeFuture().sync();
			
		}catch(Exception e){
			
		}finally {
			group.shutdownGracefully().sync();
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		new EchoServer(8005).start(); 

	}

}
