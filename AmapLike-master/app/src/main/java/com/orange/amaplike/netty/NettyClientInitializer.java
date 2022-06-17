package com.orange.amaplike.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //建立管道 在初始化完成后,ChatClientInitializer会从channel的pipeline中移除
        ChannelPipeline pipeline = channel.pipeline();
        // 实际上建立客户端与服务器连接的步骤是在ChatClientHandler中完成的
        pipeline.addLast("decoder",new StringDecoder());
        pipeline.addLast("encoder",new StringEncoder());
        // 即在ChatClientHandler中完成业务处理
        pipeline.addLast("handler",new NettyClientHandler());
    }
}
