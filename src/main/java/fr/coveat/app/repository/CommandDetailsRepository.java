package fr.coveat.app.repository;

import fr.coveat.app.model.Command;
import fr.coveat.app.model.CommandDetails;
import fr.coveat.app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandDetailsRepository extends JpaRepository<CommandDetails, Long> {

    @Query("SELECT c FROM CommandDetails c WHERE c.command=:command")
    List<CommandDetails> findByCommand(Command command);
}
