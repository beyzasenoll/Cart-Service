package com.ecommerce.application.controller;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public Item createItem(@RequestParam int itemId,
                           @RequestParam int categoryId,
                           @RequestParam int sellerId,
                           @RequestParam double price,
                           @RequestParam int quantity) {
        return itemService.createItem(itemId, categoryId, sellerId, price, quantity);
    }
}
