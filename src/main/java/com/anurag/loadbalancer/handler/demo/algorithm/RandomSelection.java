package com.anurag.loadbalancer.handler.demo.algorithm;

import com.anurag.loadbalancer.handler.demo.IProvideServer;
import com.anurag.loadbalancer.handler.demo.config.Config;

import java.util.Random;

public class RandomSelection implements IProvideServer {
    @Override
    public int provideServer() {
        Random random = new Random();
        if(Config.getUsedPortsList().isEmpty())
            return 0;
        int randomIndex = random.nextInt(Config.getUsedPortsList().size());
        return Config.getUsedPortsList().get(randomIndex);
    }
}
