package com.dgcheshang.cheji.netty.init;

import com.dgcheshang.cheji.netty.util.ByteUtil;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 解码器
 * @author Administrator
 *
 */
public class MyDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                          List<Object> out) throws Exception {
		byte[] bs=new byte[0];
		byte[] temp=new byte[1];
		while (in.isReadable()) {
			temp[0]=in.readByte();
			bs= ByteUtil.byteMerger(bs, temp);
		}
		String hs= ByteUtil.bytesToHexString(bs);
		out.add(hs);
	}
}
