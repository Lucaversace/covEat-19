package fr.coveat.app.controller;


import fr.coveat.app.model.Dish;
import fr.coveat.app.service.SecurityService;
import fr.coveat.app.repository.DishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController implements SecurityService{

    private DishRepository dishRepository;
    List<Long> cart = new ArrayList<Long>();

    CartController(DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }

    @RequestMapping(value={"","/"}, method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) {
        if(!checkConnected(request, "user")){return "redirect:/login";}
        HttpSession session = request.getSession();
        this.cart = ((ArrayList<Long>) session.getAttribute("cart"));

        if(this.cart == null){
            this.cart = new ArrayList<Long>();
            session.setAttribute("cart", this.cart);
        }
        model.addAttribute("cartEmpty",true);

        if(this.cart != null && !this.cart.isEmpty()){
            Collections.sort(this.cart);
            List<Integer> quantities = new ArrayList<Integer>();
            List<Long> id_passed = new ArrayList<Long>();
            double total = 0.0;
            for (Long dishId: this.cart){
                if(!id_passed.contains(dishId)){
                    id_passed.add(dishId);
                    quantities.add(Collections.frequency(this.cart, dishId));
                    total += Collections.frequency(this.cart, dishId)*dishRepository.getOne(dishId).getPrice();
                }
            }
//            System.out.println("Quantity:"+quantities);
            session.setAttribute("quantities", quantities);
//            System.out.println(dishRepository.findAllById(this.cart));
            model.addAttribute("cartEmpty",false);
            model.addAttribute("cart", dishRepository.findAllById(this.cart));
            model.addAttribute("quantities", session.getAttribute("quantities"));
            model.addAttribute("total", total);
        }
        return "/cart/home_cart";
    }

    @RequestMapping(value={"/add/{id}"}, method = RequestMethod.GET)
    public String addDish(@PathVariable("id") Long id, HttpServletRequest request){
        if(!checkConnected(request, "user")){return "redirect:/login";}
        HttpSession session = request.getSession();
        if (dishRepository.existsById(id) && session.getAttribute("cart") != null) {
            this.cart = (ArrayList<Long>) session.getAttribute("cart");
            Dish dish = this.dishRepository.getOne(id);
            if(session.getAttribute("restaurant_id") != dish.getRestaurant().getId()){
                this.cart.clear();
            }
            if (this.cart != null){
                this.cart.add(id);
                session.setAttribute("cart", this.cart);
                session.setAttribute("restaurant_id", dish.getRestaurant().getId());
            }
        }
        return "redirect:/cart";
    }

    @RequestMapping(value={"/remove/{id}"}, method = RequestMethod.GET)
    public String removeDish(@PathVariable("id") Long id, HttpServletRequest request) {
        if(!checkConnected(request, "user")){return "redirect:/login";}
        HttpSession session = request.getSession();
        if(this.cart == null){
            this.cart = new ArrayList<Long>();
            session.setAttribute("cart", this.cart);
        }
        if (dishRepository.existsById(id) && session.getAttribute("cart") != null) {
            this.cart = (ArrayList<Long>) session.getAttribute("cart");
            if (this.cart != null && this.cart.contains(id)){
                this.cart.remove(id);
            }
            session.setAttribute("cart", this.cart);
        }
        return "redirect:/cart";
    }
    @RequestMapping(value={"/delete/{id}"}, method = RequestMethod.GET)
    public String deleteDish(@PathVariable("id") Long id, HttpServletRequest request) {
        if(!checkConnected(request, "user")){return "redirect:/login";}
        HttpSession session = request.getSession();
        if (dishRepository.existsById(id) && session.getAttribute("cart") != null) {
            this.cart = (ArrayList<Long>) session.getAttribute("cart");
            if (this.cart != null && this.cart.contains(id)){
                while(this.cart.contains(id)){
                    this.cart.remove(id);
                }
            }
            session.setAttribute("cart", this.cart);
        }
        return "redirect:/cart";
    }
}