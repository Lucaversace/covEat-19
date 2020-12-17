package fr.coveat.app.service;

import fr.coveat.app.model.Restaurant;
import fr.coveat.app.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface SecurityService {
    public default boolean checkConnected(HttpServletRequest request, String value){
        HttpSession session = request.getSession();
        if (session.getAttribute(value) != null){
            switch (value){
                case "restaurant":
                    Restaurant restaurant_session = (Restaurant) session.getAttribute(value);
                    return !restaurant_session.getEmail().isEmpty();
                case "user":
                    User user_session = (User) session.getAttribute(value);
                    return !user_session.getEmail().isEmpty();
                case "default":
                    return false;
            }
        }
        return false;
    }
}
