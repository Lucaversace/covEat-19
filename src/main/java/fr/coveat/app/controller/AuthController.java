package fr.coveat.app.controller;

import fr.coveat.app.form.RestorerForm;
import fr.coveat.app.form.LoginFormUser;
import fr.coveat.app.form.UserForm;
import fr.coveat.app.model.Address;
import fr.coveat.app.model.User;
import fr.coveat.app.model.Restaurant;
import fr.coveat.app.repository.AddressRepository;
import fr.coveat.app.repository.UserRepository;
import fr.coveat.app.repository.RestorerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthController {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private RestorerRepository restorerRepository;
	private static Matcher matcherImage_url;
    private static Pattern patternStreet = Pattern.compile("^(\\d+) [a-zA-Z0-9\\s]+(.)? [a-zA-Z]+(.)?$");
    private static Matcher matcherStreet;
    private static Pattern patternZipCode = Pattern.compile("^((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}$");
    private static Matcher matcherZipCode;
    private static Pattern patternEmail = Pattern.compile("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$");
    private static Matcher matcherEmail;
    private static Pattern patternPassword = Pattern.compile("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$");
    private static Matcher matcherPassword;

    AuthController(UserRepository userRepository, AddressRepository addressRepository, RestorerRepository restorerRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.restorerRepository = restorerRepository;
    }

    @RequestMapping(value = {"login"}, method = RequestMethod.GET )
    public String showLogin(Model model) {

        LoginFormUser loginFormUser = new LoginFormUser();
        model.addAttribute("loginFormUser", loginFormUser);

        return "login";
    }

    @RequestMapping(value = {"login"}, method = RequestMethod.POST )
    public String login(Model model, HttpServletRequest request,
                        @ModelAttribute("loginFormUser") LoginFormUser loginFormUser) {
        String email = loginFormUser.getEmail();
        String password = loginFormUser.getPassword();

        User user = userRepository.findByEmail(email);
        if (user != null){
            String hashPass = user.getPassword();

            if (BCrypt.checkpw(password, hashPass)){
                HttpSession session = request.getSession();
                session.setAttribute("user", user );
    //            Object user_session = session.getAttribute("user");
    //            System.out.println(user_session);
                return "redirect:/";
            }
        }
        return "login";
    }

    @RequestMapping(value = {"logout"}, method = RequestMethod.GET )
    public String logout(HttpServletRequest request) {

        request.getSession().invalidate();
        System.out.println("déconnecté");
        return "redirect:/login";
    }

    @Value("${error.zipcode}")
    private String errorZipCode;

    @Value("${error.street}")
    private String errorStreet;

    @RequestMapping(value = {"register"}, method = RequestMethod.GET )
    public String showRegister(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        model.addAttribute("errorZipCode", errorZipCode);
        return "register";
    }

    @RequestMapping(value = { "register" }, method = RequestMethod.POST)
    public String saveUser(Model model,
                @ModelAttribute("userForm") UserForm userForm) {

        String street = userForm.getStreet();
        String zipCode = userForm.getZipCode();
        String city = userForm.getCity();

        String lastname = userForm.getLastname();
        String firstname = userForm.getFirstname();
        String email = userForm.getEmail();
        String password = userForm.getPassword();
        String confPassword = userForm.getConfPassword();

        matcherStreet = patternStreet.matcher(street);
        matcherZipCode = patternZipCode.matcher(zipCode);
        matcherEmail = patternEmail.matcher(email);
        matcherPassword = patternPassword.matcher(password);

        if (!street.isEmpty() && matcherStreet.find()){
            if (!zipCode.isEmpty() && matcherZipCode.find()){
                if (!city.isEmpty() && city.length() > 1){
                    Address address = new Address();
                    address.setStreet(street);
                    address.setZipCode(zipCode);
                    address.setCity(city);
                    if (!lastname.isEmpty() && lastname.length() > 1){
                        if (!firstname.isEmpty() && firstname.length() > 1){
                            if (!email.isEmpty() && matcherEmail.find()){
                                if (!password.isEmpty() && matcherPassword.find()){
                                    if (password.equals(confPassword)){
                                        String pwHash = BCrypt.hashpw(password, BCrypt.gensalt());
                                        User newUser = new User();
                                        newUser.setLastname(lastname);
                                        newUser.setFirstname(firstname);
                                        newUser.setEmail(email);
                                        newUser.setPassword(pwHash);
                                        newUser.setAddress(address);
                                        addressRepository.save(address);
                                        userRepository.save(newUser);
                                        System.out.print("compte créé");
                                        return "redirect:/login";
                                    }
                                    else{
                                        System.out.print("password différent");
                                        System.out.print(password);
                                        System.out.print(confPassword);
                                    }
                                }
                                else{
                                    System.out.print("password non valide");
                                    System.out.print("password" + password);
                                    System.out.print("confpassword" + confPassword);
                                }
                            } else{
                                System.out.print("email non valide");
                            }
                        } else{
                            System.out.print("firstname non valide");
                        }
                    } else{
                        System.out.print("lastname non valide");
                    }

                } else{
                    System.out.print("city non valide");
                }
            } else{
                System.out.print("zipcode non valide");
            }
        }
        else {
            System.out.print("street non valide");
            model.addAttribute("errorStreet", errorStreet);
        }
        return "redirect:/register";
    }



///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
/////////////////////////////RESTORER REGISTER/////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////



    @RequestMapping(value = {"login_restorer"}, method = RequestMethod.GET )
    public String showLoginRestorer(Model model) {

        LoginFormUser loginForm = new LoginFormUser();
        model.addAttribute("loginForm", loginForm);

        return "restorer/login_restorer";
    }

    @RequestMapping(value = {"login_restorer"}, method = RequestMethod.POST )
    public String loginRestorer(Model model, HttpServletRequest request, @ModelAttribute("loginForm") LoginFormUser loginForm) {

        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        Restaurant restaurant = restorerRepository.findByEmail(email);
        if (restaurant != null){
            String hashPass = restaurant.getPassword();

            if (BCrypt.checkpw(password, hashPass)){
                HttpSession session = request.getSession();
                session.setAttribute("restaurant", restaurant );
                Object restaurant_session = session.getAttribute("restaurant");
                return "redirect:/restorer";
            }
        }
        return "redirect:/login_restorer";
    }

    @RequestMapping(value = {"register_restorer"}, method = RequestMethod.GET )
    public String showRegisterRestorer(Model model) {
    	RestorerForm restorerForm = new RestorerForm();
    	model.addAttribute("restorerForm", restorerForm);
        model.addAttribute("errorZipCode", errorZipCode);
        return "restorer/register_restorer";
    }

    @RequestMapping(value = { "register_restorer" }, method = RequestMethod.POST)
    public String saveRestorer(Model model,
                @ModelAttribute("restorerForm") RestorerForm restorerForm) {

        String street = restorerForm.getStreet();
        String zipCode = restorerForm.getZipCode();
        String city = restorerForm.getCity();

        String name = restorerForm.getName();
        String email = restorerForm.getEmail();
        String image_url = restorerForm.getImage_url();
        String password = restorerForm.getPassword();
        String conf_password = restorerForm.getConfPassword();

        matcherStreet = patternStreet.matcher(street);
        matcherZipCode = patternZipCode.matcher(zipCode);
        matcherEmail = patternEmail.matcher(email);
        matcherPassword = patternPassword.matcher(password);

        if (!street.isEmpty() && matcherStreet.find()){
            if (!zipCode.isEmpty() && matcherZipCode.find()){
                if (!city.isEmpty() && city.length() > 1){
                    Address address = new Address();
                    address.setStreet(street);
                    address.setZipCode(zipCode);
                    address.setCity(city);
                    if (!name.isEmpty() && name.length() > 1){
                        if (!email.isEmpty() && matcherEmail.find()){
                        	if (!image_url.isEmpty()){
	                            if (!password.isEmpty() && matcherPassword.find()){
	                                if (password.equals(conf_password)){
	                                    String pwHash = BCrypt.hashpw(password, BCrypt.gensalt());
	                                    Restaurant newRestorer = new Restaurant();
                                        newRestorer.setName(name);
                                        newRestorer.setEmail(email);
                                        newRestorer.setPassword(pwHash);
                                        newRestorer.setAddress(address);
                                        newRestorer.setImage_url(image_url);
                                        addressRepository.save(address);
	                                    restorerRepository.save(newRestorer);
	                                    System.out.print("compte crée");
                                        return "redirect:/login_restorer";
	                                }
	                                else{
	                                    System.out.print("password différent");
	                                    System.out.print(password);
	                                    System.out.print(conf_password);
	                                }
	                            }
	                            else{
	                                System.out.print("password non valide");
	                                System.out.print("password" + password);
	                                System.out.print("conf_password" + conf_password);
	                            }
	                        } else{
	                            System.out.print("email non valide");
	                        }
                        }else{
                             System.out.print("Image non valide");
                         }
                    } else{
                        System.out.print("name non valide");
                    }
                } else{
                    System.out.print("city non valide");
                }
            } else{
                System.out.print("zipcode non valide");
            }
    	}else{
            System.out.print("street non valide");
            model.addAttribute("errorStreet", errorStreet);
        }
        return "redirect:/register_restorer";
    }
}
