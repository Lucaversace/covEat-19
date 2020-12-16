package fr.coveat.app.repository;

import fr.coveat.app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestorerRepository extends JpaRepository<Restaurant , Long> {

    @Query("SELECT r FROM Restaurant r WHERE r.email=:email")
    Restaurant findByEmail(String email);
}
