package com.lrin.project.config;

import com.lrin.project.entity.item.Item;
import com.lrin.project.repository.item.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final ItemRepository itemRepository;

    public DataInitializer(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            if (itemRepository.count() == 0) {
                itemRepository.save(new Item("Oven_Range", 6000));
                itemRepository.save(new Item("Air_Purifier", 7000));
                itemRepository.save(new Item("Refrigerator", 10000));
                itemRepository.save(new Item("Printer", 9000));
                itemRepository.save(new Item("Washing_Machine", 6000));
                itemRepository.save(new Item("Air_Conditioner", 10000));
                itemRepository.save(new Item("Audio", 7000));
                itemRepository.save(new Item("Heater", 5000));
                itemRepository.save(new Item("Desktop_Computer", 6000));
                itemRepository.save(new Item("Keyboard", 3000));
                itemRepository.save(new Item("Monitor", 7000));
                itemRepository.save(new Item("Rice_Cooker", 3000));
                itemRepository.save(new Item("Fan", 3000));
                itemRepository.save(new Item("Vacuum_Cleaner", 3000));
                itemRepository.save(new Item("Water_Purifier", 4000));
                itemRepository.save(new Item("Blender", 3000));
                itemRepository.save(new Item("Iron", 3000));
                itemRepository.save(new Item("Other", 3000));
            }
        };
    }
}