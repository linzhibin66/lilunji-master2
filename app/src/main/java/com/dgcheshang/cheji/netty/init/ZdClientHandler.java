package com.dgcheshang.cheji.netty.init;

import android.util.Log;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.timer.ConTimer;
import com.dgcheshang.cheji.netty.util.GatewayService;
import com.dgcheshang.cheji.netty.util.MsgUtil;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.Timer;
import java.util.TimerTask;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ZdClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Log.e("TAG","连接异常！");
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.e("TAG","终端连接服务器激活成功");
		GatewayService.addGatewayChannel("serverChannel",ctx);
		NettyConf.constate=1;

		if(ZdClient.conTimer!=null){
			ZdClient.conTimer.cancel();
			ZdClient.conTimer=null;
		}

		if(NettyConf.zcstate==0){
			//没注册过注册
			ZdUtil.sendZdzc();
		}else if(NettyConf.zcstate==1&NettyConf.jqstate==0){
			//发送终端鉴权
			ZdUtil.sendZdjqHex();
		}

		super.channelActive(ctx);


	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE){
				System.out.println("read idle");

			}
			else if (event.state() == IdleState.WRITER_IDLE){
					String hexmsg= MsgUtil.getXthf(NettyConf.mobile);
					if(StringUtils.isNotEmpty(hexmsg)){
						ctx.writeAndFlush(hexmsg);

					}
			}
			else if (event.state() == IdleState.ALL_IDLE){
				System.out.println("all idle");
			}

		}
	}


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		// 收到消息直接打印输出
		if(NettyConf.debug) {
			Log.e("TAG", ctx.channel().remoteAddress() + " server say: " + msg);
		}
		if(StringUtils.isEmpty(msg)){
			//消息为空直接丢弃
		}else{
			ZdHandleMsg zm=new ZdHandleMsg(msg);
			Thread th=new Thread(zm);
			th.start();
		}

	}


	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		GatewayService.removeGatewayChannel("serverChannel");
		NettyConf.constate=0;
		NettyConf.jqstate=0;
		Log.e("TAG","断开连接重连！");
		if(ZdClient.conTimer==null){
			Timer timer=new Timer();
			TimerTask timerTask=new ConTimer();
			timer.schedule(timerTask,1000,35*1000);
			ZdClient.conTimer=timer;//记录下来
		}

		/*final EventLoop eventLoop = ctx.channel().eventLoop();

		eventLoop.schedule(new Runnable() {

			@Override

			public void run() {

				ZdClient.doConnect();

			}

		}, 1L, TimeUnit.SECONDS);*/
		super.channelInactive(ctx);
	}



}
