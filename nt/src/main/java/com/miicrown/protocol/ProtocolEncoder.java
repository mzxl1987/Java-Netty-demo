package com.miicrown.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocolEncoder extends MessageToByteEncoder<ProtocalMsg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ProtocalMsg pm, ByteBuf out) throws Exception {
		
		out.writeShort(pm.getHead());
		out.writeByte(pm.getType());
		out.writeShort(pm.getLength());
		out.writeBytes(pm.getData());
		out.writeShort(pm.getTail());
	}

}
