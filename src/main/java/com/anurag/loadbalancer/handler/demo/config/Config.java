package com.anurag.loadbalancer.handler.demo.config;

import java.util.*;

public class Config {
    private static String algo = "rr";
    private static int lastServerIndex = 0;

    private static List<Integer> availablePorts = new ArrayList<>(Arrays.asList(8081, 8082, 8083, 8084, 8085));
    private static List<Integer> usedPorts = new ArrayList<>();

    public synchronized static int getUnusedPort(){
        int port = 0;
        if(!availablePorts.isEmpty()){
            port = availablePorts.remove(availablePorts.size() - 1);
            usedPorts.add(port);
        }
            return port;
    }

    public synchronized  static boolean removeUsedPort(Integer port){
        boolean flag = usedPorts.remove(port);
        availablePorts.add(port);
        return flag;
    }

    public synchronized  static List<Integer> getUsedPortsList(){
        return usedPorts;
    }

    public synchronized  static void incrementLastServerIndex(){
        lastServerIndex++;
    }

    public synchronized  static int getLastServerIndex(){
        return lastServerIndex;
    }

    public synchronized static String getAlgo(){
        return algo;
    }

    public synchronized static void setAlgo(String str){
        algo = str.toLowerCase();
    }

    public synchronized static List<Integer> getAvailablePorts() {
        return Config.availablePorts;
    }

    public synchronized static void setLastServerIndex(int index){
        lastServerIndex = index;
    }

}
