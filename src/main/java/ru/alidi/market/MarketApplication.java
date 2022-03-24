package ru.alidi.market;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MarketApplication {
    private static final Logger logger = LoggerFactory.getLogger(MarketApplication.class);

    public static void main(String[] args) {
        logger.info("Market application is running");
        SpringApplication.run(MarketApplication.class, args);
    }
}
