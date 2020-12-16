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
        this.cart = ((ArrayList<Long>) session.getAttribute("cart"));
        System.out.println(this.cart);

        if(this.cart == null){
            this.cart = new ArrayList<Long>();
            session.setAttribute("cart", this.cart);
        }
        model.addAttribute("cartEmpty",true);

        if(this.cart != null && !this.cart.isEmpty()){
            Collections.sort(this.cart);
            List<Integer> quantities = new ArrayList<Integer>();
            List<Long> id_passed = new ArrayList<Long>();
            for (Long id2: this.cart){
                if(!id_passed.contains(id2)){
                    id_passed.add(id2);
                    quantities.add(Collections.frequency(this.cart, id2));
//                        System.out.println("id:"+id2 + ":Q" + Collections.frequency(this.cart, id2));
                }

            }
            System.out.println("Quantity:"+quantities);
            session.setAttribute("quantities", quantities);
            System.out.println(dishRepository.findAllById(this.cart));
            model.addAttribute("cartEmpty",false);
            model.addAttribute("cart", dishRepository.findAllById(this.cart));
            model.addAttribute("quantities", session.getAttribute("quantities"));
        }
        return "/cart/home_cart";
    }

    @RequestMapping(value={"/add/{id}"}, method = RequestMethod.GET)
    public String addDish(@PathVariable("id") Long id, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (dishRepository.existsById(id) && session.getAttribute("cart") != null) {
            this.cart = (ArrayList<Long>) session.getAttribute("cart");
            if (this.cart != null){
                this.cart.add(id);
                session.setAttribute("cart", this.cart);

            }
        }
        return "redirect:/cart";
    }

    @RequestMapping(value={"/remove/{id}"}, method = RequestMethod.GET)
    public String removeDish(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (dishRepository.existsById(id) && session.getAttribute("cart") != null) {
            this.cart = (ArrayList<Long>) session.getAttribute("cart");
//            List<Integer> quantities = (List<Integer>) session.getAttribute("quantities");
            if (this.cart != null && this.cart.contains(id)){
                this.cart.remove(id);
            }
            session.setAttribute("cart", this.cart);
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