package com.dmadev.sim;

import com.dmadev.sim.service.SimulationService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimApplication {
    private SimulationService simulationService;

    public static void main(String[] args) {
        SpringApplication.run(SimApplication.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(SimulationService simulationService) {
        return args -> simulationService.Run();
    }

//    @PostConstruct
//    public void init(){
//        simulationService.Run();
//    }
}
