package com.gageshan.mchat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * Create by gageshan on 2020/5/1 22:24
 */
@Component
public class WSServer {
    private static class SingletionWSServer {
        static final WSServer instance = new WSServer();
    }
    public static WSServer getInstance() {
        return SingletionWSServer.instance;
    }
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    public WSServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitialzer());
    }
    public void start() throws Exception{
        channelFuture = serverBootstrap.bind(8888).sync();
        System.err.println("netty websocket server 启动完毕...");
    }

}
