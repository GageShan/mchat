package com.gageshan.mchat.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * Create by gageshan on 2020/5/5 16:45
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;

            if(idleStateEvent.state() == IdleState.READER_IDLE) {
                //进入读空闲
            } else if(idleStateEvent.state() == IdleState.WRITER_IDLE) {
                //进入写空闲
            } else if(idleStateEvent.state() == IdleState.ALL_IDLE) {

                //进入读写空闲，关闭相应的channel
                Channel channel = ctx.channel();
                channel.close();
            }
        }
    }
}
