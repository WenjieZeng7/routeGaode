package com.orange.amaplike.netty;

import android.util.Log;

import java.io.IOException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    private static final String TAG = "NettyClient";

    private String host; // 主机号
    private int port; // 端口号
    private ChannelFuture channelFuture; // 客户端与服务器的通信通道
    public NettyClient(String host,int port){
        this.host=host;
        this.port=port;
    }

    /**
     * 连接服务器
     * @throws IOException IO异常
     */
    public void start() throws IOException {
        // 创建一个事件循环组 这是是多态的一个用法 NioEventLoopGroup的父类实现了EventLoopGroup接口
        // 应该是相当于一个线程池(组)的概念
        // EventLoopGroup应该是一个死循环，不停的检测IO事件，处理IO事件，执行任务
        EventLoopGroup workGroup = new NioEventLoopGroup(1);
        try {
            // Bootstrap客户端专用，通过这个可以很方便的初始化客户端
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)  // 将注册的channel注册到EventLoopGroup上
                    .channel(NioSocketChannel.class) // 创建一个channel
                    .handler(new NettyClientInitializer()); // 装配一个流水线
            // 获得一个客户端与主机的连接，并开启监听
            // 在这里调用sync()同步方法阻塞直到绑定成功
            channelFuture = bootstrap.connect(host,port).sync();

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        Log.d(TAG,"连接成功");
                    } else {
                        Log.d(TAG,"连接失败");
                    }
                }
            });

            // closeFuture():当此通道关闭时将通知该通道。这个方法总是返回相同的Future实例。
            // 在这里应该是开启了一个channel的监听器，用来监听channel是否关闭的状态
            // 如果未来监听到channel关闭了，子线程才会释放
            channelFuture.channel().closeFuture().sync();
            Log.e(TAG, "start: 已从服务器断开");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            // 关闭EventLoopGroup，释放所有资源
            workGroup.shutdownGracefully();
        }
    }

    /**
     * 发送消息
     * @param
     */
    public void sendMsg(String str){
        // 打印要发送的消息
        Log.e(TAG, "要发送的消息:---->"+str);
        // 将消息写入到对于的channel连接的通道中(缓冲区)，并且刷新通道(缓冲区),将数据发送出去
        channelFuture.channel().writeAndFlush(str);
    }

    /**
     * 断开连接
     */
    public void stop(){
        channelFuture.channel().close();
    }
}
