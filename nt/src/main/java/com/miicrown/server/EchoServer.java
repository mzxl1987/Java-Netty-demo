package com.miicrown.server;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

public class EchoServer {
	
	private static final Logger log = LoggerFactory.getLogger(EchoServer.class);
	
	public static ConcurrentHashMap<ChannelId, Channel> dic_channel = new ConcurrentHashMap<ChannelId, Channel>();
	
	private final Configuration configuration;
	private final Configuration configCopy;
	
	private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
	
	public EchoServer(Configuration configuration){
		this.configuration = configuration;
		this.configCopy = Configuration.cloneInstance(configuration);
	}
	
	public void start(){
		
		startAsync().syncUninterruptibly();
		
	}
	
	public Future<Void> startAsync(){
		
		initGroups();
			
		Class<? extends ServerChannel> channel = configCopy.isUseLinuxNativeEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
		
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
		.channel(channel)
		.childHandler(new ServerInitializer());
		
		InetSocketAddress addr = new InetSocketAddress(configCopy.getPort());
		if(configCopy.getHost() != null){
			addr = new InetSocketAddress(configCopy.getHost(), configCopy.getPort());
		}
		
		b.localAddress(addr);
		
		return b.bind(addr).addListener(new FutureListener<Void>() {
			public void operationComplete(Future<Void> future) throws Exception {
				if(future.isSuccess()){
					log.info("VVVV启动成功VVVV, 端口{}",configCopy.getPort());
				}else{
					log.info("XXXX启动失败XXXX, 端口{}",configCopy.getPort());
				}
			}
		}) ;
		
	}
	
	public void stop(){
		
		bossGroup.shutdownGracefully().syncUninterruptibly();
		workerGroup.shutdownGracefully().syncUninterruptibly();
		
	}
	
	protected void initGroups() {
        
		if (configCopy.isUseLinuxNativeEpoll()) {
            bossGroup = new EpollEventLoopGroup(configCopy.getBossThreads());
            workerGroup = new EpollEventLoopGroup(configCopy.getWorkerThreads());
        } else {
            bossGroup = new NioEventLoopGroup(configCopy.getBossThreads());
            workerGroup = new NioEventLoopGroup(configCopy.getWorkerThreads());
        }
		
    }
	
	
}
