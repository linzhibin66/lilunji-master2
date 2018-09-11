package com.dgcheshang.cheji.netty.util;

import android.util.Log;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Tdata;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class GatewayService {
	private static Map<String, Object> map = new ConcurrentHashMap();
	public static void addGatewayChannel(String id, ChannelHandlerContext gateway_ctx){
		map.put(id, gateway_ctx);
	}

	public static Object getGatewayChannel(String id){
		return map.get(id);
	}

	public static void removeGatewayChannel(String id){
		map.remove(id);
	}

	/*
     * 发送数据给服务器；
     */
	public static boolean sendHexMsgToServer(String ip,String msg){
		boolean fg=true;
		try{
			if(ZdUtil.pdNetwork()) {
				Object o = GatewayService.getGatewayChannel(ip);
				if (o == null) {
					fg = false;
				} else {
					if(NettyConf.debug){
						Log.e("TAG",msg);
					}
					ChannelHandlerContext clientChannel = (ChannelHandlerContext) o;
					clientChannel.writeAndFlush(msg);
				}
			}else{
				fg=false;
			}
		}catch(Exception e){
			e.printStackTrace();
			fg=false;
		}
		return fg;
	}

	/*
     * 发送数据给服务器；
     */
	public static boolean sendHexMsgToServer(String ip,List<Tdata> tdatas){
		boolean fg=true;
		try{
			if(ZdUtil.pdNetwork()) {
				Object o = GatewayService.getGatewayChannel(ip);
				if (o == null) {
					fg = false;
				} else {
					for(Tdata tdata:tdatas){
						String msg=tdata.getData();
						if(NettyConf.debug){
							Log.e("TAG",msg);
						}
						ChannelHandlerContext clientChannel = (ChannelHandlerContext) o;
						clientChannel.writeAndFlush(msg);
					}
				}
			}else{
				fg=false;
			}
		}catch(Exception e){
			e.printStackTrace();
			fg=false;
		}
		return fg;
	}
}
