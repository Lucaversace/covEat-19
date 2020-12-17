package fr.coveat.app.repository;

import fr.coveat.app.model.Command;
import fr.coveat.app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    @Query("SELECT c FROM Command c WHERE c.restaurant=:restaurant")
    List<Command> findByRestaurant(Restaurant restaurant);
}
