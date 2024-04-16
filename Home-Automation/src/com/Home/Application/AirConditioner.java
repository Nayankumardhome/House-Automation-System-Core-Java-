package com.Home.Application;

import java.time.Duration;
import java.time.LocalDateTime;

public class AirConditioner implements Device {

    private boolean status;
    private LocalDateTime lastOnTimestamp;
    private LocalDateTime lastOffTimestamp;
    private Duration totalOnDuration;
    private Duration totalOffDuration;

    // Constructor
    public AirConditioner() {
        this.status = false;
        this.lastOnTimestamp = null;
        this.lastOffTimestamp = null;
        this.totalOnDuration = Duration.ZERO;
        this.totalOffDuration = Duration.ZERO;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public void turnOn() {
        if (!status) {
            status = true;
            lastOnTimestamp = LocalDateTime.now();
            if (lastOffTimestamp != null) {
                totalOffDuration = totalOffDuration.plus(Duration.between(lastOffTimestamp, lastOnTimestamp));
            }
            System.out.println("AirConditioner turn on");
        }
    }

    @Override
    public void turnOff() {
        if (status) {
            status = false;
            lastOffTimestamp = LocalDateTime.now();
            totalOnDuration = totalOnDuration.plus(Duration.between(lastOnTimestamp, lastOffTimestamp));
            System.out.println("AirConditioner turn off");
        }
    }

    @Override
    public String getDeviceType() {
        return "AirConditioner";
    }

    public void overAllStatus() {
        System.out.println("Device Name : " + this.getDeviceType());
        System.out.println("Status : " + (status ? "On" : "Off"));
        if (status) {
            Duration currentOnDuration = Duration.between(lastOnTimestamp, LocalDateTime.now());
            System.out.println("Current On Duration: " + formatDuration(currentOnDuration));
        } else if(lastOffTimestamp != null) {
        	Duration currentOnDuration = Duration.between(lastOnTimestamp, lastOffTimestamp);
            System.out.println("Current On Duration: " + formatDuration(currentOnDuration));
            Duration currentOffDuration = Duration.between(lastOffTimestamp, LocalDateTime.now());
            System.out.println("Current Off Duration: " + formatDuration(currentOffDuration));
        }else {
            System.out.println("Total Time On: " + formatDuration(totalOnDuration));
            System.out.println("Total Time Off: " + formatDuration(totalOffDuration));
        }
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        long millis = duration.toMillisPart();
        return String.format("%d hours %d minutes %d seconds %d milliseconds", hours, minutes, seconds, millis);
    }
}
