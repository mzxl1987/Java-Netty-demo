package com.miicrown.server;

public class Configuration {

	private String host = "localhost";
	private int port = -1;
	
	private boolean useLinuxNativeEpoll;
	
	private int bossThreads = 0; // 0 = current_processors_amount * 2
    private int workerThreads = 0; // 0 = current_processors_amount * 2
	
	private Configuration() {

	}
	
	/**
	 * 创建配置对象
	 * @return
	 */
	public static Configuration createInstance(){
		return new Configuration();
	}
	
	/**
	 * 拷贝配置对象
	 * @param source
	 * @return
	 */
	public static Configuration cloneInstance(Configuration source){
		
		Configuration dest = new Configuration();
		
		dest.setHost(source.getHost());
		dest.setPort(source.getPort());
		
		return dest;
		
	}

	/**************************    get/set    ****************************************/

	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}

	public boolean isUseLinuxNativeEpoll() {
		return useLinuxNativeEpoll;
	}

	public void setUseLinuxNativeEpoll(boolean useLinuxNativeEpoll) {
		this.useLinuxNativeEpoll = useLinuxNativeEpoll;
	}

	public int getBossThreads() {
		return bossThreads;
	}

	public void setBossThreads(int bossThreads) {
		this.bossThreads = bossThreads;
	}

	public int getWorkerThreads() {
		return workerThreads;
	}

	public void setWorkerThreads(int workerThreads) {
		this.workerThreads = workerThreads;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
