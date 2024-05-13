package com.anurag.loadbalancer.handler.demo;

import com.anurag.loadbalancer.handler.demo.algorithm.ProvideServerFactory;
import com.anurag.loadbalancer.handler.demo.config.Config;
import com.anurag.loadbalancer.handler.demo.server.ServerAction;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@RestController
@RequestMapping("/service/v1/demos")
public class Controller {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping
    public ResponseEntity<String> demoProvider() {
        int port = ProvideServerFactory.provider("").provideServer();
        if(port == 0)
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("No server is available");
        try {
            String output =  webClientBuilder.build()
                    .get()
                    .uri("http://localhost:"+port+"/service/v1/orders")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            // Return successful response with output
            return ResponseEntity.ok(output);
        } catch (WebClientResponseException e) {
            System.err.println("Error accessing server: " + e.getStatusCode() + " " + e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body("Error accessing server: " + e.getStatusCode() + " " + e.getStatusText());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    @PostMapping("/active/{activeNumber}")
    public Integer activeServer(@Positive @PathVariable int activeNumber){
        return activeNumber;
    }

    @DeleteMapping("/port/{portId}")
    public Integer deleteServer(@Positive @PathVariable int portId)
    {
        ServerAction.stop(portId);
        return portId;
    }

    @PutMapping("/algo/{algoName}")
    public void setAlgo(@PathVariable String algoName){
        Config.setAlgo(algoName);
    }

    @GetMapping("/server")
    public List<Integer> activeServers(@RequestParam(required = true) String status){
        if(status.equalsIgnoreCase("up"))
            return Config.getUsedPortsList();
        else
            return Config.getAvailablePorts();
    }
}
