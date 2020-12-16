package fr.coveat.app.repository;

import fr.coveat.app.model.Dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DishRepository extends JpaRepository<Dish,Long> {

}

