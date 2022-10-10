package com.fzz.itcast.server.handler;

import com.fzz.itcast.message.GroupJoinRequestMessage;
import com.fzz.itcast.message.GroupJoinResponseMessage;
import com.fzz.itcast.server.session.Group;
import com.fzz.itcast.server.session.GroupSession;
import com.fzz.itcast.server.session.GroupSessionFactory;
import com.fzz.itcast.server.session.GroupSessionMemoryImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        Group group = GroupSessionFactory.getGroupSession().joinMember(msg.getGroupName(), msg.getUsername());
        GroupSession sessionMemory = GroupSessionFactory.getGroupSession();

        if (group != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群加入成功"));
            List<Channel> channels = sessionMemory.getMembersChannel(msg.getGroupName());
            for(Channel channel:channels){
                channel.writeAndFlush(new GroupJoinResponseMessage(true, msg.getUsername()+"加入群聊"));
            }
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群不存在"));
        }
    }
}
