package com.server.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory {
    private AtomicInteger seq = new AtomicInteger(1);
    private String threadName;

    public DefaultThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "[" + threadName + "_" + seq.getAndIncrement() + "]");
    }
}
