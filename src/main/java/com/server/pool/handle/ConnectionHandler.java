package com.server.pool.handle;

import com.server.pool.util.RemoteUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionHandler extends ChannelDuplexHandler {

    private Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.info("[{}] register ", RemoteUtil.getHost(ctx.channel()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("[{}] active ", RemoteUtil.getHost(ctx.channel()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("[{}] in_active ", RemoteUtil.getHost(ctx.channel()));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        logger.info("[{}] un_register ", RemoteUtil.getHost(ctx.channel()));
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
        logger.info("[{}] close ", RemoteUtil.getHost(ctx.channel()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.info("[{}] exception ", RemoteUtil.getHost(ctx.channel()), cause);
    }
}
