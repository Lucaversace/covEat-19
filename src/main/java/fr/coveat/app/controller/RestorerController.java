package fr.coveat.app.controller;

import fr.coveat.app.form.DishForm;
import fr.coveat.app.model.Dish;
import fr.coveat.app.model.Restaurant;
import fr.coveat.app.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Controller
public class RestorerController {
    @Autowired
    private DishRepository dishRepository;

    @RequestMapping(value = {"/restorer/","/restorer"}, method = RequestMethod.GET )
    public String getDish(Model model) {
        model.addAttribute("dishes", dishRepository.findAll(Sort.by("id").descending()));
        return "restorer/dish_list";
    }

    @RequestMapping(value = {"/restorer/add_dish"}, method = RequestMethod.GET )
    public String createDish(Model model) {

        model.addAttribute("dishForm", new Dish());
        return "restorer/add_dish";
    }

    @RequestMapping(value = {"/restorer/add_dish"}, method = RequestMethod.POST )
    public String postCreateDish(@ModelAttribute("dish") Dish dish, @RequestParam(value = "photo", required = false) MultipartFile multipartFile){
        if(dish.getName() != null && dish.getPrice() != null && dish.getDescription() != null && dish.getImageUrl() != null){
            String name = dish.getName();
            Double price = dish.getPrice();
            String description = dish.getDescription();
            String imageUrl = dish.getImageUrl();

    	// TODO : A MODIFIER APRES LES SESSIONS
            Restaurant restaurant = new Restaurant();
            restaurant.setId(1L);

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
    public String editDish(@PathVariable("id") Long id, Model model) {
        if (dishRepository.existsById(id)) {
            model.addAttribute("dishForm", dishRepository.getOne(id));
            return "restorer/edit_dish";
        }
        return "redirect:/restorer/";
    }

    @RequestMapping(value = {"/restorer/{id}/edit_dish"}, method = RequestMethod.POST )
    public String editDish(@PathVariable("id") Long id, @ModelAttribute("dishForm") DishForm dishForm, @RequestParam(value = "photo", required = false) MultipartFile multipartFile){
        if (dishRepository.existsById(id)) {
            Dish dish = dishRepository.getOne(id);

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

        return "redirect:/restorer/";
    }

    @RequestMapping(value = {"/restorer/{id}/delete_dish"}, method = RequestMethod.GET )
    public String deleteDish(@PathVariable("id") Long id) {
        if (dishRepository.existsById(id)) {
            dishRepository.deleteById(id);
        }
        return "redirect:/restorer/";
    }
}
