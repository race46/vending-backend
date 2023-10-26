package com.aselsan.vending.scheduler;

import com.aselsan.vending.service.MachineService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoolingSchedulerTest {

    @Test
    void itCheckCoolingWhenACOpen() {
        MachineService machineService = new MachineService(null, null);
        CoolingScheduler scheduler = new CoolingScheduler(machineService);

        scheduler.run();

        boolean isCooling = machineService.getHearthBeat().isCooling();
        double firstTemperature = machineService.getHearthBeat().getTemperature();

        scheduler.run();

        double secondTemperature = machineService.getHearthBeat().getTemperature();

        assertTrue(isCooling && firstTemperature >= secondTemperature);

    }

    @Test
    void itCheckCoolingIsClosedAfterFiveDegreeAndOpenUntilIt() {
        MachineService machineService = new MachineService(null, null);
        CoolingScheduler scheduler = new CoolingScheduler(machineService);

        scheduler.run();

        while (machineService.getHearthBeat().isCooling()){
            assertTrue(machineService.getHearthBeat().getTemperature() > 5);
            scheduler.run();
        }

        assertFalse(machineService.getHearthBeat().isCooling());

    }

}