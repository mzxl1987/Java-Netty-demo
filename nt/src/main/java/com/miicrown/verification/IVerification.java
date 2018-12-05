package com.miicrown.verification;

import io.netty.buffer.ByteBuf;

public interface IVerification {
	
	/**
	 * 截取校验数据
	 * @param bb
	 */
	void takeVerification(ByteBuf bb);
	
	/**
	 * 生成新的校验码
	 * @param data
	 * @return
	 */
	byte[] encodeVerification(byte[] data);
	
	/**
	 * 比较校验值
	 * @param data
	 * @param dest
	 * @return
	 */
	boolean compare(byte[] data, byte[] dest);

}
