package fr.coveat.app.controller;

import fr.coveat.app.model.*;
import fr.coveat.app.repository.*;
import fr.coveat.app.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CommandController implements SecurityService {

    private CommandDetailsRepository commandDetailsRepository;
    private CommandRepository commandRepository;
    private DishRepository dishRepository;
    private RestorerRepository restorerRepository;
    private StateRepository stateRepository;
    List<Long> cart = new ArrayList<Long>();

    CommandController(
            CommandRepository commandRepository,
            CommandDetailsRepository commandDetailsRepository,
            DishRepository dishRepository,
            RestorerRepository restorerRepository,
            StateRepository stateRepository
    ) {
        this.commandRepository = commandRepository;
        this.dishRepository = dishRepository;
        this.restorerRepository = restorerRepository;
        this.stateRepository = stateRepository;
        this.commandDetailsRepository = commandDetailsRepository;
    }

    @RequestMapping(value={"/command"}, method = RequestMethod.GET)
    public String confirmCommand(HttpServletRequest request){
        if(!checkConnected(request, "user")){return "redirect:/login";}
        HttpSession session = request.getSession();
        this.cart = ((ArrayList<Long>) session.getAttribute("cart"));
        if(this.cart != null && !this.cart.isEmpty()){
            List<Integer> quantities = (List<Integer>) session.getAttribute("quantities");
            Long restaurant_id = (Long) session.getAttribute("restaurant_id");
            User user = (User) session.getAttribute("user");
            State state = this.stateRepository.getOne(1L);
            Restaurant restaurant = this.restorerRepository.getOne(restaurant_id);

            Command command = new Command();
            command.setDate(new Date());
            command.setRestaurant(restaurant);
            command.setState(state);
            command.setUser(user);
            command.setAddress(user.getAddress());
            command.setPrice_total(0);
            this.commandRepository.save(command);

            int index = 0;
            double total = 0.0;
            List<Long> id_passed = new ArrayList<Long>();
            for (Long id: this.cart) {
                if(!id_passed.contains(id)){
                    id_passed.add(id);
                    Dish dish = this.dishRepository.getOne(id);
                    double totalCommandDetails = (double) (quantities.get(index) * dish.getPrice());

                    CommandDetails commandDetails = new CommandDetails();
                    commandDetails.setDish(dish);
                    commandDetails.setPrice(totalCommandDetails);
                    commandDetails.setQuantity(quantities.get(index));
                    commandDetails.setCommand(command);
                    this.commandDetailsRepository.save(commandDetails);
                    total += totalCommandDetails;
                    index++;
                }


            }

            command.setPrice_total(total);
            commandRepository.save(command);

            session.removeAttribute("restaurant_id");
            session.removeAttribute("cart");
            session.removeAttribute("quantities");
        }

        return "redirect:/cart";
    }
}

