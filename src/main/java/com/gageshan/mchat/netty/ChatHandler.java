package com.gageshan.mchat.netty;

import com.gageshan.mchat.enums.MsgActionEnum;
import com.gageshan.mchat.service.UserService;
import com.gageshan.mchat.utils.JsonUtils;
import com.gageshan.mchat.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by gageshan on 2020/5/1 23:18
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        System.out.println("msg: " + msg);
        DataContent dataContent = JsonUtils.jsonToPojo(msg,DataContent.class);
        Channel currentChannel = channelHandlerContext.channel();
        Integer action = dataContent.getAction();

        if(action == MsgActionEnum.CONNECT.TYPE) {
            String senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId,currentChannel);
        } else if(action == MsgActionEnum.CHAT.TYPE) {
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgText = chatMsg.getMsg();
            String receiverId = chatMsg.getReceiverId();
            String senderId = chatMsg.getSenderId();
            UserService userService = (UserService)SpringUtil.getBean("userService");
            String msgId = userService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            Channel receiverChannel = UserChannelRel.get(receiverId);

            if(receiverChannel == null) {

            } else {
                Channel findChannel = users.find(receiverChannel.id());
                if(findChannel != null) {
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(
                            JsonUtils.objectToJson(chatMsg)
                    ));
                } else {

                }
            }
        } else if(action == MsgActionEnum.SIGNED.TYPE) {
            UserService userService = (UserService)SpringUtil.getBean("userService");
            String extend = dataContent.getExtend();
            String[] split = extend.split(",");
            List<String> msIdsList = new ArrayList<>();

            for (String s : split) {
                if(StringUtils.isNoneBlank(s)) {
                    msIdsList.add(s);
                }
            }

            System.out.println(msIdsList.toString());

            if(msIdsList != null && !msIdsList.isEmpty() && msIdsList.size() > 0) {
                userService.updateMsgSigned(msIdsList);
            }
        } else if(action == MsgActionEnum.KEEPALIVE.TYPE) {

        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
