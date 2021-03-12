package com.server;

import com.server.pool.DefaultThreadFactory;
import com.server.pool.handle.ConnectionHandler;
import com.server.pool.handle.ProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer {
    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public static final int BOSS_THREAD_COUNT = 1;

    private ServerBootstrap bootstrap = new ServerBootstrap();
    private static EventLoopGroup bossGroup = null;
    private static EventLoopGroup workGroup = null;
    private ChannelFuture future;


    static {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("linux") && Epoll.isAvailable()) {
            bossGroup = new EpollEventLoopGroup(BOSS_THREAD_COUNT, new DefaultThreadFactory("BOSS_Thread"));
            workGroup = new EpollEventLoopGroup(BOSS_THREAD_COUNT, new DefaultThreadFactory("WORK_Thread"));
        } else {
            bossGroup = new NioEventLoopGroup(BOSS_THREAD_COUNT, new DefaultThreadFactory("BOSS_Thread"));
            workGroup = new NioEventLoopGroup(BOSS_THREAD_COUNT, new DefaultThreadFactory("WORK_Thread"));
        }

    }

    public void start() {
        bootstrap.group(bossGroup, workGroup).channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<ServerSocketChannel>() {
                    @Override
                    protected void initChannel(ServerSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ConnectionHandler()).addLast(new ProcessHandler());
                    }
                });
        try {
            future = bootstrap.bind(8080).sync();
        } catch (Exception e) {
        }


    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        server.start();
    }
}
