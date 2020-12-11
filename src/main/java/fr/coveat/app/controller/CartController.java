package sample.web.cart;


import fr.coveat.app.model.CommandDetails;
import fr.coveat.app.model.Dish;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<Dish> dishes = (List<Dish>) session.getAttribute("dishes");
        model.addAttribute("dishes", dishes!=null? dishes:new ArrayList<>());
        return "home";
    }

    @PostMapping("/addDish")
    public String add(@RequestParam("dish") Dish dish, HttpServletRequest request) {

        List<Dish> dishes = (List<Dish>) request.getSession().getAttribute("Dishes");
        if(dishes == null){
            dishes = new ArrayList<>();
            request.getSession().setAttribute("dishes", dishes);
        }

        dishes.add(dish);
        request.getSession().setAttribute("dishes", dishes);
        return "redirect:/cart";
    }

    @PostMapping
    public String updateAll(@Validated CartForm cartForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cart", cart);
            model.addAttribute("cartForm", cartForm);
            return "cart/list";
        }
        for (var entry : cartForm.getItems().entrySet()) {
            var itemId = entry.getKey();
            var cartItem = cart.getCartItem(itemId);
            if (cartItem == null) {
                continue;
            }
            var cartItemForm = entry.getValue();
            if (cartItemForm.getQuantity() < 1) {
                cart.removeItemById(itemId);
            } else {
                cart.setQuantityByItemId(itemId, cartItemForm.getQuantity());
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/item/{itemId}")
    public String addItem(@PathVariable String itemId, Model model) {
        if (cart.containsItemId(itemId)) {
            cart.incrementQuantityByItemId(itemId);
        } else {
            var isInStock = itemService.isItemInStock(itemId);
            var item = itemService.getItem(itemId);
            cart.addItem(item, isInStock);
        }
        model.addAttribute(cart);
        return "redirect:/cart";
    }

    @DeleteMapping("/item/{itemId}")
    public String removeItem(@PathVariable String itemId) {
        cart.removeItemById(itemId);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("cart", cart);
        return "cart/checkout";
    }

    @PostMapping("/checkout")
    public String checkout() {
        return "redirect:/order";
    }
}