package com.server.pool.handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class ProcessHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            String receiveMsg = new String(buf.array(), Charset.forName("UTF-8"));
            ctx.writeAndFlush(receiveMsg + "_word");
        } else {
            ctx.writeAndFlush("word");
        }

    }
}
