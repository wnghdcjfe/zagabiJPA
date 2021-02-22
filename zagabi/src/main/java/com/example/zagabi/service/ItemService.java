package com.example.zagabi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.zagabi.domain.item.Item;
import com.example.zagabi.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;

	@Transactional
	public void saveItem(Item item){
		itemRepository.save(item);
	}
	public List<Item> findItems(){
		return itemRepository.findAll();
	}
	public Item findOne(Long itemId){
		return itemRepository.findOne(itemId);
	}

	public void updateItem(Long id, String name, int price) {
		Item item = itemRepository.findOne(id); item.setName(name); item.setPrice(price);
	}
}
