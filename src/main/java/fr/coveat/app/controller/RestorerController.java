package fr.coveat.app.controller;

import fr.coveat.app.form.DishForm;
import fr.coveat.app.model.Command;
import fr.coveat.app.model.Dish;
import fr.coveat.app.model.Restaurant;
import fr.coveat.app.repository.*;
import fr.coveat.app.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Controller
public class RestorerController implements SecurityService {
    private DishRepository dishRepository;
    private RestorerRepository restorerRepository;
    private CommandRepository commandRepository;
    private CommandDetailsRepository commandDetailsRepository;

    RestorerController(
            CommandRepository commandRepository,
            DishRepository dishRepository,
            RestorerRepository restorerRepository,
            CommandDetailsRepository commandDetailsRepository
    ) {
        this.commandRepository = commandRepository;
        this.dishRepository = dishRepository;
        this.restorerRepository = restorerRepository;
        this.commandDetailsRepository = commandDetailsRepository;
    }


    @RequestMapping(value = {"/restorer/","/restorer"}, method = RequestMethod.GET )
    public String getDish(Model model, HttpServletRequest request) {
        if(!checkConnected(request, "restaurant")){return "redirect:/login_restorer";}
        model.addAttribute("dishes",
            dishRepository.findByRestaurant(
                (Restaurant) request.getSession().getAttribute("restaurant")
            )
        );
        model.addAttribute("commands",
                commandRepository.findByRestaurant(
                        (Restaurant) request.getSession().getAttribute("restaurant")
                )
        );
        return "restorer/dish_list";
    }

    @RequestMapping(value = {"/restorer/command/{id}"}, method = RequestMethod.GET )
    public String getCommand(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        if(!checkConnected(request, "restaurant")){return "redirect:/login_restorer";}
        if (commandRepository.existsById(id)) {
            Command command = commandRepository.getOne(id);
            if(((Restaurant) request.getSession().getAttribute("restaurant")).getEmail().equals(command.getRestaurant().getEmail())) {
                model.addAttribute("commands_details", commandDetailsRepository.findByCommand(command));
                return "restorer/command";
            }
        }
        return "redirect:/restorer/";
    }

    @RequestMapping(value = {"/restorer/add_dish"}, method = RequestMethod.GET )
    public String createDish(Model model, HttpServletRequest request) {
        if(!checkConnected(request, "restaurant")){return "redirect:/login_restorer";}

        model.addAttribute("dishForm", new Dish());
        return "restorer/add_dish";
    }

    @RequestMapping(value = {"/restorer/add_dish"}, method = RequestMethod.POST )
    public String postCreateDish(@ModelAttribute("dish") Dish dish, @RequestParam(value = "photo", required = false) MultipartFile multipartFile, HttpServletRequest request) {
        if(!checkConnected(request, "restaurant")){return "redirect:/login_restorer";}
        if(dish.getName() != null && dish.getPrice() != null && dish.getDescription() != null && dish.getImageUrl() != null){
            String name = dish.getName();
            Double price = dish.getPrice();
            String description = dish.getDescription();
            String imageUrl = dish.getImageUrl();

            Restaurant restaurant = (Restaurant) request.getSession().getAttribute("restaurant");

            if(!name.isEmpty() && !price.isNaN() && !description.isEmpty() && !imageUrl.isEmpty()){
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                if(fileName.contains(".."))
                {
                    System.out.println("not a a valid file");
                }
                try {
                    dish.setImageUrl(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dish.setRestaurant(restaurant);
                dishRepository.save(dish);
            }
        }

        return "redirect:/restorer/";
    }

    @RequestMapping(value = {"/restorer/{id}/edit_dish"}, method = RequestMethod.GET )
    public String editDish(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        if(!checkConnected(request, "restaurant")){return "redirect:/login_restorer";}
        if (dishRepository.existsById(id)) {
            Dish dish = dishRepository.getOne(id);
            if(((Restaurant) request.getSession().getAttribute("restaurant")).getEmail().equals(dish.getRestaurant().getEmail())){
                model.addAttribute("dishForm", dish);
                return "restorer/edit_dish";
            }
        }
        return "redirect:/restorer/";
    }

    @RequestMapping(value = {"/restorer/{id}/edit_dish"}, method = RequestMethod.POST )
    public String editDish(@PathVariable("id") Long id, @ModelAttribute("dishForm") DishForm dishForm, @RequestParam(value = "photo", required = false) MultipartFile multipartFile, HttpServletRequest request) {
        if(!checkConnected(request, "restaurant")){return "redirect:/login_restorer";}
        if (dishRepository.existsById(id)) {
            Dish dish = dishRepository.getOne(id);
            if(((Restaurant) request.getSession().getAttribute("restaurant")).getEmail().equals(dish.getRestaurant().getEmail())){
                if(dishForm.getName() != null && dishForm.getPrice() != null && dishForm.getDescription() != null && dishForm.getImageUrl() != null){
                    String name = dishForm.getName();
                    Double price = dishForm.getPrice();
                    String description = dishForm.getDescription();
                    String imageUrl = dishForm.getImageUrl();

                    if(!name.isEmpty() && !price.isNaN() && !description.isEmpty() && !imageUrl.isEmpty()){

                        dish.setName(name);
                        dish.setPrice(price);
                        dish.setDescription(description);

                        if(!dish.getImageUrl().equals(imageUrl)){
                            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                            if(fileName.contains(".."))
                            {
                                System.out.println("not a a valid file");
                            }
                            try {
                                dish.setImageUrl(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        dishRepository.save(dish);
                    }
                }
            }
        }

        return "redirect:/restorer/";
    }

    @RequestMapping(value = {"/restorer/{id}/delete_dish"}, method = RequestMethod.GET )
    public String deleteDish(@PathVariable("id") Long id, HttpServletRequest request) {
        if(!checkConnected(request, "restaurant")){return "redirect:/login_restorer";}
        if (dishRepository.existsById(id)) {
            if(((Restaurant) request.getSession().getAttribute("restaurant")).getEmail().equals(dishRepository.getOne(id).getRestaurant().getEmail())){
                dishRepository.deleteById(id);
            }
        }
        return "redirect:/restorer/";
    }
}
