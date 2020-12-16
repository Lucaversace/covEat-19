package fr.coveat.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.coveat.app.model.Restaurant;


@Repository
public interface RestorerRepository extends JpaRepository<Restaurant , Long> {

}
