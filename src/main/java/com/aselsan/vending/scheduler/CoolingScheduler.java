package com.aselsan.vending.scheduler;


import com.aselsan.vending.response.HearthBeat;
import com.aselsan.vending.service.MachineService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@EnableScheduling
@Component
public class CoolingScheduler {

    private MachineService machineService;
    private double temperature;
    private boolean isCooling;
    private Random random;
    public CoolingScheduler(MachineService machineService){
        this.machineService = machineService;
        this.temperature = 20;
        this.isCooling = false;
        this.random = new Random();
    }


    @Scheduled(fixedRate = 100)
    public void run(){
        double randomTemperature = random.nextDouble() / 20;
        temperature += randomTemperature * (isCooling ? -1 : 1);

        if (temperature > 7.5) isCooling = true;
        if (temperature < 5) isCooling = false;

        machineService.setHearthBeat(new HearthBeat("ok",isCooling, temperature));
    }
}
