package com.lrin.project.service.item;

import com.lrin.project.entity.item.Item;
import com.lrin.project.repository.item.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();  // 모든 Item 객체를 반환
    }

}

