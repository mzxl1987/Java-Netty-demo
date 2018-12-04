package com.miicrown.protocol;

import io.netty.buffer.ByteBuf;

/**
 * 协议框架： 消息头 + 消息类型 + 消息体 + crc校验 + 消息尾 
 * 消息头的格式为 : 0x68 0x00 0x01 0x68 , 0x68为通用格式, 0x0001为消息体的长度       4bytes
 * 消息类型：用户自定义消息类型                           1byte
 * 消息体：用户自定义内容，                                   长度不定                   
 * crc校验：[消息体] 的CRC校验,             2bytes
 * 帧尾：0x16                              1byte 
 * @author Administrator
 *
 */
abstract class Protocol {
	
	public static final byte HEAD = 0x68;
	public static final byte TAIL = 0x16;
	
	private int length;
	private final int type;
	private byte[] content;
	private byte[] crc;
	
	Protocol(int type) {
		this.type = type;
	}
	
	abstract Protocol createInstance(int type);  //创建对象
	abstract void decode();                      //解析数据
	abstract ByteBuf encode();                   //拼装数据

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * @return the crc
	 */
	public byte[] getCrc() {
		return crc;
	}

	/**
	 * @param crc the crc to set
	 */
	public void setCrc(byte[] crc) {
		this.crc = crc;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	
	
	
}
