package com.dgcheshang.cheji.netty.init;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.ByteUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class ZdClientInitializer extends ChannelInitializer<SocketChannel> {
    private final static int readerIdleTimeSeconds = 30;//读操作空闲30秒
    private final static int allIdleTimeSeconds = 100;//读写全部空闲100秒
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /*
         * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
         *
         * 解码和编码 我将会在下一张为大家详细的讲解。再次暂时不做详细的描述
         *
         * */
        ByteBuf delimiter = Unpooled.copiedBuffer(ByteUtil.hexStringToByte("7E"));
        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(81920, Delimiters.lineDelimiter()));
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(2048, delimiter));
        pipeline.addLast("decoder", new MyDecoder());
        pipeline.addLast("encoder", new MyEncoder());
        pipeline.addLast("idleStateHandler", new IdleStateHandler(readerIdleTimeSeconds,NettyConf.xtjg,allIdleTimeSeconds));

        // 客户端的逻辑
        pipeline.addLast("handler", new ZdClientHandler());
    }

}