package com.miicrown.protocol;

import io.netty.buffer.ByteBuf;

public class LoginProtocol extends Protocol{

	private LoginProtocol(int type) {
		super(type);
	}

	@Override
	Protocol createInstance(int type) {
		// TODO Auto-generated method stub
		return new LoginProtocol(type);
	}

	@Override
	void decode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	ByteBuf encode() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
