package com.example.QueuingSystem.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class SystemInputs {
    @NotNull(message = "is mandatory")
    @Min(value = 0,message = "invalid input, must be greater than 0")
    private Double arrivalRate;

    @NotNull(message = "is mandatory")
    @Min(value = 0,message = "invalid input, must be greater than 0")
    private Double serviceRate;

    @NotNull(message = "is mandatory")
    @Min(value = 1,message = "enter at least 1 server")
    private Double numberOfServers;
    @Min(value = 1,message = "system capacity must be greater than 0")
    private Double systemCapacity;

    public SystemInputs() {
    }

    public SystemInputs(double arrivalRate, double serviceRate, double numberOfServers, double systemCapacity) {
        this.arrivalRate = arrivalRate;
        this.serviceRate = serviceRate;
        this.numberOfServers = numberOfServers;
        this.systemCapacity = systemCapacity;
    }

    public Double getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(Double arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Double getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(Double numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public Double getSystemCapacity() {
        return systemCapacity;
    }

    public void setSystemCapacity(Double systemCapacity) {
        this.systemCapacity = systemCapacity;
    }
}
