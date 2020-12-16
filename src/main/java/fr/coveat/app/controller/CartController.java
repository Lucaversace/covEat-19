package fr.coveat.app.controller;


//import fr.coveat.app.model.CartLineInfo;
import fr.coveat.app.model.Dish;
import fr.coveat.app.repository.DishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private DishRepository dishRepository;
    List<Long> cart = new ArrayList<Long>();

    CartController(DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }

    @RequestMapping(value={"","/"}, method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("cart"));
        this.cart = ((ArrayList<Long>) session.getAttribute("cart"));
        if(this.cart == null){
            this.cart = new ArrayList<Long>();
//            cart.add(1L);
//            CartLineInfo cartline = new CartLineInfo(dishRepository.getOne(new Long(1)), 1);
//            System.out.println(cartline.getQuantity());
//            cart.add(new CartLineInfo(new Long(1), 1));
            System.out.println(this.cart);
            session.setAttribute("cart", cart);
        }
        if(this.cart != null && !this.cart.isEmpty()){
//            List<Dish> cartContent = new ArrayList<Dish>();
            model.addAttribute("cartEmpty",false);
//            this.cart = new ArrayList<Long>(){{
//                add(1L);
//                add(2L);
//                add(3L);
//            }};
//            model.addAttribute("cart", dishRepository.findAllById(this.cart));
            model.addAttribute("cart", dishRepository.findAll());
        }else{
            model.addAttribute("cartEmpty",true);
        }
        return "/cart/home_cart";
    }

    @RequestMapping(value={"/add/{id}"}, method = RequestMethod.GET)
    public String addDish(@PathVariable("id") Long id, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (dishRepository.existsById(id) && session.getAttribute("cart") != null) {
            this.cart = (ArrayList<Long>) session.getAttribute("cart");
            Dish dish = this.dishRepository.getOne(id);
            if (this.cart != null){
//                boolean flag_added_quantity = false;
//                for (CartLineInfo cartLine: this.cart) {
//                    if(dish.getId().equals(cartLine.getDishId())){
//                        cartLine.setQuantity(cartLine.getQuantity()+1);
//                        flag_added_quantity = true;
//                    }
//                }
//                if(!flag_added_quantity){
//                    this.cart.add(new CartLineInfo(1L, 1));
//                }
                this.cart.add(id);
                session.setAttribute("cart", this.cart);
                System.out.println(session.getAttribute("cart"));
            }
        }
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