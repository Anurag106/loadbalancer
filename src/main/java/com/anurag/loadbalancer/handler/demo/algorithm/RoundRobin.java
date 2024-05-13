package com.anurag.loadbalancer.handler.demo.algorithm;

import com.anurag.loadbalancer.handler.demo.IProvideServer;
import com.anurag.loadbalancer.handler.demo.config.Config;

public class RoundRobin implements IProvideServer {
    @Override
    public int provideServer() {
        int index = 0;
        if(Config.getUsedPortsList().isEmpty())
            return 0;
        if(Config.getLastServerIndex() >= Config.getUsedPortsList().size())
            Config.setLastServerIndex(0);
        else {
            index = Config.getLastServerIndex();
        }
        Config.incrementLastServerIndex();
        return Config.getUsedPortsList().get(index);
    }
}
