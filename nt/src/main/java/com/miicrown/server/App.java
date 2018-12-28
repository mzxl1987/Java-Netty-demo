package com.miicrown.server;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Configuration configuration = Configuration.createInstance();
    	configuration.setHost("127.0.0.1");
    	configuration.setPort(7005);
    	
    	new EchoServer(configuration).start();
    }
}
