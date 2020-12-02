package fr.coveat.app.repository;

import fr.coveat.app.model.Dish;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DishRepository extends CrudRepository<Dish,Long> {

}

