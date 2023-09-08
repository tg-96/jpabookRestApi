package jpashop.jpabook.controller;

import jpashop.jpabook.domain.item.Book;
import jpashop.jpabook.domain.item.Item;
import jpashop.jpabook.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("bookForm",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookForm form, BindingResult result){
        if(result.hasErrors()){
            return "items/createItemForm";
        }

        Book book = Book.createBook(form.getName(),form.getPrice(), form.getStockQuantity(), form.getAuthor(),form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "/items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book)itemService.findOne(itemId);
        BookForm form = new BookForm();
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setId(item.getId());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form",form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form,@PathVariable("itemId") Long itemId){
        itemService.updateItem(itemId,form);
        return "redirect:/items";
    }

}
