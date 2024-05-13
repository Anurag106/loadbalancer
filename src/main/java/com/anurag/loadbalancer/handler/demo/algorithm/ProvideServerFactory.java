package com.anurag.loadbalancer.handler.demo.algorithm;

import com.anurag.loadbalancer.handler.demo.IProvideServer;

public class ProvideServerFactory {
    public static IProvideServer provider(String str){
        if (str.equals("rr")) {
            return new RandomSelection();
        }
        return new RoundRobin();
    }
}
