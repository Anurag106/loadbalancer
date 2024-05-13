package com.anurag.loadbalancer.handler.demo.server;

import com.anurag.loadbalancer.handler.demo.config.Config;
import com.anurag.loadbalancer.handler.demo.monitoring.MonitorServer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ServerAction {
    public static void starter() {
        int port = Config.getUnusedPort();
        if(port == 0) {
            System.out.println("no available ports");
            return;
        }
        try {
            String currentDirectory = System.getProperty("user.dir");
            System.out.println("Current directory: " + currentDirectory);
            Process proc = Runtime.getRuntime().exec("java -jar "+currentDirectory+"\\src\\main\\java\\com\\anurag\\loadbalancer\\handler\\demo\\server\\build\\demoServer.jar " + String.valueOf(port));
            // Then retreive the process output
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();

            byte b[]=new byte[in.available()];
            in.read(b,0,b.length);
            System.out.println(new String(b));

            byte c[]=new byte[err.available()];
            err.read(c,0,c.length);
            System.out.println(new String(c));
            new MonitorServer("localhost", port).startPinging();
        } catch (IOException e) {
            // Handle IOException
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void stop(int port){
        WebClient.Builder webClientBuilder =  WebClient.builder();
        try {
            String output =  webClientBuilder.build()
                    .delete()
                    .uri("http://localhost:"+port+"/service/v1/orders")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            // Handle WebClientResponseException, which includes cases like server not reachable, server error, etc.
            System.err.println("Error accessing server: " + e.getStatusCode() + " " + e.getStatusText());

        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
