package com.springhow.examples.springboot.cache;

import com.springhow.examples.springboot.cache.entities.Item;
import com.springhow.examples.springboot.cache.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.math.BigDecimal;

@EnableCaching
@SpringBootApplication
public class SpringBootCacheExampleApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootCacheExampleApplication.class);

    @Autowired
    private ItemService itemService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCacheExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading test data.");
        itemService.createItem(new Item("xxyy", 1));
        itemService.createItem(new Item("xxxyyy", 2));
        itemService.createItem(new Item("xyz", 1));
    }
}
