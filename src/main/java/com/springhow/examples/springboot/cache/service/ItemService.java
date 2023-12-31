package com.springhow.examples.springboot.cache.service;

import com.springhow.examples.springboot.cache.entities.Item;
import com.springhow.examples.springboot.cache.entities.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> items() {
        return itemRepository.findAll();
    }

    @Cacheable(value = "items", key = "#id")
    public Item getItem(Integer id) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Item item = itemRepository.findById(id).orElseThrow(RuntimeException::new);
        logger.info("Loading data from DB {}", item);
        return item;
    }

    public Item createItem(Item item) {

        return itemRepository.save(item);
    }

    @CacheEvict(value = "items", key = "#id")
    public Item updateItem(Integer id, Item request) {
        Item item = getItem(id);
        item.setHashKey(request.getHashKey());
        item.setCount(request.getCount());
        return itemRepository.save(item);
    }
}
