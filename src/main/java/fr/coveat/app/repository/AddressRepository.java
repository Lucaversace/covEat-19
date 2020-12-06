package fr.coveat.app.repository;

import fr.coveat.app.model.Address;
import fr.coveat.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
