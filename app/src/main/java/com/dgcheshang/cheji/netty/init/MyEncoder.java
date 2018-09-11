package com.dgcheshang.cheji.netty.init;


import com.dgcheshang.cheji.netty.util.ByteUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 解码器
 * @author Administrator
 *
 */
public class MyEncoder extends MessageToByteEncoder<String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out)
			throws Exception {
		out.writeBytes(ByteUtil.hexStringToByte(msg));
	}


}
