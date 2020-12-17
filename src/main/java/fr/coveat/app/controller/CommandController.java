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


}

