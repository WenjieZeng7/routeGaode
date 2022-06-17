package com.orange.amaplike.netty;

import android.util.Log;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static final String TAG = "NettyClientHandler";
    /**
     * 在channel注册好后，激活channel，即将channel所对于的客户端与服务器进行连接
     * @param ctx ChannelHandlerContext类型
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG,"channelActive");
        ctx.channel().writeAndFlush("hello服务端");
    }

    /**
     * 当服务器发送消息过来时，即channel读取到数据时
     * @param ctx
     * @param msg 读取到的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Log.d(TAG,"服务端返回信息"+ msg.toString());
    }

    /**
     * 捕获异常，关闭channel
     * @param ctx
     * @param cause 异常的类型
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 获得当前的channel
        Channel newchannel = ctx.channel();
        Log.e(TAG, "exceptionCaught: "+"["+newchannel.remoteAddress()+"]：通讯异常");
        Log.e(TAG, "exceptionCaught: "+cause.getMessage());
        newchannel.close();
    }
}
