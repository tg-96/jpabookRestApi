package jpashop.jpabook.service;

import jpashop.jpabook.controller.BookForm;
import jpashop.jpabook.domain.item.Book;
import jpashop.jpabook.domain.item.Item;
import jpashop.jpabook.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
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

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }

    @Transactional
    public void updateItem(Long itemId, BookForm book) {
        Book item = (Book)itemRepository.findOne(itemId);
        item.updateBook(book);
    }

}
