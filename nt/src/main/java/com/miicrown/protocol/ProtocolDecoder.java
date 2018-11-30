package com.miicrown.protocol;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProtocolDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext chc, ByteBuf bb, List<Object> out) throws Exception {
		
		if(bb.readableBytes() < ProtocalMsg.MINLENGTH){
			return;
		}
		
		int head = bb.readUnsignedShort();
		if(head != ProtocalMsg.HEAD){
			bb.resetReaderIndex();
			return;
		}
		
		ProtocalMsg pm = ProtocalMsg.createInstance();
		
		pm.setHead(head);
		pm.setType(bb.readUnsignedByte());
		pm.setLength(bb.readUnsignedShort());
		
		if(bb.readableBytes() < pm.getLength()){
			bb.resetReaderIndex();
			return;
		}
		
		byte[] data = new byte[pm.getLength()];
		bb.readBytes(data);
		
		pm.setData(data);
		int tail = bb.readUnsignedShort();
		if(tail != ProtocalMsg.TAIL){
			bb.resetReaderIndex();
			return;
		}
		pm.setTail(tail);
		
//		System.out.println(pm.finish());
		
		out.add(pm);
		
	}

}
