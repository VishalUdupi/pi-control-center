package com.vishal.picontrolcentre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PiControlCentreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiControlCentreApplication.class, args);
    }

}
