package com.springhow.examples.springboot.cache.controller;

import com.springhow.examples.springboot.cache.entities.Item;
import com.springhow.examples.springboot.cache.service.ItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/items/")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Integer id)  {
        return itemService.getItem(id);
    }

    @PostMapping("/")
    public Item createItem(@RequestBody Item request) {
        Item item = new Item();
        item.setCount(request.getCount());
        item.setHashKey(request.getHashKey());
        item.setCount(request.getCount());

        return itemService.createItem(item);
    }

    @PutMapping("/{id}")
    public Item createItem(@PathVariable Integer id, @RequestBody Item request)  {
        return itemService.updateItem(id, request);
    }

    @GetMapping("/custom-trace")
    public String customTrace(
            @RequestHeader(name = "Custom-Trace-Id", required = false) String customTraceId,
            @RequestHeader(name = "Custom-Span-Id", required = false) String customSpanId) {
        // Use customTraceId and customSpanId as needed

        return "Custom Trace and Span Ids Set";
    }
}
