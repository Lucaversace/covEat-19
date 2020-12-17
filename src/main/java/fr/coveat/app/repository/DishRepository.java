package fr.coveat.app.repository;

import fr.coveat.app.model.Dish;
import fr.coveat.app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish,Long> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant=:restaurant")
    List<Dish> findByRestaurant(Restaurant restaurant);
}