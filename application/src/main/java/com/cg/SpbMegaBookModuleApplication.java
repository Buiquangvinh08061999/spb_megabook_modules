package com.cg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpbMegaBookModuleApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpbMegaBookModuleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpbMegaBookModuleApplication.class, args);
        logger.info("MegaBook Application Started........");
    }

}
