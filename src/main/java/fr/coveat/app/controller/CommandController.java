package fr.coveat.app.controller;

import fr.coveat.app.model.Command;
import fr.coveat.app.repository.CommandRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/commands")
public class CommandController {

    private CommandRepository commandRepository;

    CommandController(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @GetMapping(value= "")
    public List<Command> findAll(){
        return commandRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Command> findById(@PathVariable long id){
        return commandRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{id}")
    public Command create(@RequestBody Command command){
        return commandRepository.save(command);

    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Command> update(@PathVariable("id") long id, @RequestBody Command command){
        return commandRepository.findById(id)
            .map(record -> {
                record.setAddress(command.getAddress());
                record.setDate(command.getDate());
                record.setPrice_total(command.getPrice_total());
                record.setRestaurant(command.getRestaurant());
                record.setState(command.getState());
                record.setUser(command.getUser());
                Command updated = commandRepository.save(record);
                return ResponseEntity.ok().body(updated);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return commandRepository.findById(id)
            .map (record -> {
                commandRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }
}

