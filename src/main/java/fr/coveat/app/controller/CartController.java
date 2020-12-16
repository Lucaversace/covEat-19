package fr.coveat.app.controller;


import fr.coveat.app.model.Dish;
import fr.coveat.app.repository.DishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private DishRepository dishRepository;
    private List<List<String>> dishes;

    CartController(DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }

    @RequestMapping(value={""}, method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        session.getAttribute("dishes");
        dishes.add( );
        if(dishes == null){
            dishes = new ArrayList<Dish>();
        }
        model.addAttribute("dishes", dishes);
        return "/cart/home_cart";
    }

    @RequestMapping(value={"/add/{id}"}, method = RequestMethod.GET)
    public String add(@RequestParam() Long id, HttpSession session){
        List<List<String>> dishes = List<List> session.getAttribute("dishes");
        Dish dish = dishRepository.getOne(id);
        if(dishes.contains(dish)){
            dishes.add(dish);
        }
        session.setAttribute('dishes', dishes);
        return "redirect:/cart";
    }

//    @PostMapping("/addDish")
//    public String add(@RequestParam("dish") Dish dish, HttpServletRequest request) {
//
//        List<Dish> dishes = (List<Dish>) request.getSession().getAttribute("Dishes");
//        if(dishes == null){
//            dishes = new ArrayList<>();
//            request.getSession().setAttribute("dishes", dishes);
//        }
//
//        dishes.add(dish);
//        request.getSession().setAttribute("dishes", dishes);
//        return "redirect:/cart";
//    }
//
//    @PostMapping
//    public String updateAll(@Validated CartForm cartForm, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("cart", cart);
//            model.addAttribute("cartForm", cartForm);
//            return "cart/list";
//        }
//        for (var entry : cartForm.getItems().entrySet()) {
//            var itemId = entry.getKey();
//            var cartItem = cart.getCartItem(itemId);
//            if (cartItem == null) {
//                continue;
//            }
//            var cartItemForm = entry.getValue();
//            if (cartItemForm.getQuantity() < 1) {
//                cart.removeItemById(itemId);
//            } else {
//                cart.setQuantityByItemId(itemId, cartItemForm.getQuantity());
//            }
//        }
//        return "redirect:/cart";
//    }
//
//    @PostMapping("/item/{itemId}")
//    public String addItem(@PathVariable String itemId, Model model) {
//        if (cart.containsItemId(itemId)) {
//            cart.incrementQuantityByItemId(itemId);
//        } else {
//            var isInStock = itemService.isItemInStock(itemId);
//            var item = itemService.getItem(itemId);
//            cart.addItem(item, isInStock);
//        }
//        model.addAttribute(cart);
//        return "redirect:/cart";
//    }
//
//    @DeleteMapping("/item/{itemId}")
//    public String removeItem(@PathVariable String itemId) {
//        cart.removeItemById(itemId);
//        return "redirect:/cart";
//    }
//
//    @GetMapping("/checkout")
//    public String checkout(Model model) {
//        model.addAttribute("cart", cart);
//        return "cart/checkout";
//    }
//
//    @PostMapping("/checkout")
//    public String checkout() {
//        return "redirect:/order";
//    }
}