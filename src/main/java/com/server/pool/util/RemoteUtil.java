package com.server.pool.util;


import io.netty.channel.Channel;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class RemoteUtil {

    public static String getHost(Channel channel) {
        SocketAddress address = channel.remoteAddress();
        if (address != null) {
            if (address instanceof InetSocketAddress) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
                return inetSocketAddress.getAddress().getHostAddress();
            }
        }
        return "";
    }
}
