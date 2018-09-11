package com.dgcheshang.cheji.netty.init;

import android.util.Log;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.concurrent.TimeUnit;



public class ZdClient{

    public static boolean isstop=false;
    private static Bootstrap b;
    private static ChannelFutureListener channelFutureListener = null;
    public static Timer conTimer=null;//连接服务器的定时器
    public static Thread th=null;//连接服务器的线程

    /**
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        NettyConf nettyConf = new NettyConf();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ZdClientInitializer());
            //自动调整下一次缓冲区建立时分配的空间大小，避免内存的浪费
            b.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);
            //使用内存池
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // 连接服务端
            Channel ch = b.connect(NettyConf.host, NettyConf.port).sync().channel();

            // 控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    continue;
                }
                /*
                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
                 * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                 * */

               /*line=line.replace("7D", "7D01");
        		line=line.replace("7E", "7D02");
        		line="7E"+line+"7E";*/
                ch.writeAndFlush(line);
                //ch.writeAndFlush(line);
            }
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
    //  连接到服务端
    public static void doConnect() {

        Log.e("TAG","重连2");
        ChannelFuture future = null;
        try {
            Log.d("TAG",NettyConf.host+":"+NettyConf.port);
            future = b.connect(new InetSocketAddress(NettyConf.host, NettyConf.port));
            channelFutureListener = new ChannelFutureListener() {
                public void operationComplete(ChannelFuture f) throws Exception {
                    if (f.isSuccess()) {
                        Log.e("TAG","重新连接服务器成功");
                    } else {
                        f.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                ZdClient.doConnect();
                            }
                        }, 2, TimeUnit.SECONDS);

                        Log.e("TAG","重新连接服务器失败");
                    }
                }
            };
            future.addListener(channelFutureListener);
        } catch (Exception e) {
            e.printStackTrace();
            //future.addListener(channelFutureListener);
            System.out.println("关闭连接");
        }

    }


    public void run() throws Exception {
        Log.e("TAG","启动连接！");
        EventLoopGroup group = new NioEventLoopGroup(20);
        try {
            b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ZdClientInitializer());
            //自动调整下一次缓冲区建立时分配的空间大小，避免内存的浪费
            b.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);
            //使用内存池
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,30*1000);
            // 连接服务端

            Log.e("TAG",NettyConf.host+":"+NettyConf.port);

            ChannelFuture f = b.connect(NettyConf.host, NettyConf.port).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
}

