package fr.coveat.app.repository;

import fr.coveat.app.model.CommandDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandDetailsRepository extends JpaRepository<CommandDetails, Long> {

}
