package com.anurag.loadbalancer.handler.demo.monitoring;

import com.anurag.loadbalancer.handler.demo.config.Config;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MonitorServer extends Thread{
    private final String host;
    private final int port;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> pingFuture;
    public MonitorServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startPinging() {
        scheduler = Executors.newScheduledThreadPool(1);
        pingFuture = scheduler.scheduleAtFixedRate(this::pingServer, 30, 30, TimeUnit.SECONDS);
    }

    private void pingServer() {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Server " + port + " is alive.");
        } catch (IOException e) {
            System.out.println("Server " + port + " is not alive.");
            stopPinging();
        }
    }

    private void stopPinging() {
        Config.removeUsedPort(port);
        if (pingFuture != null) {
            pingFuture.cancel(false);
        }
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}
