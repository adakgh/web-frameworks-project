package nl.team12.amsterdamevents.aeserver.app.rest;

import nl.team12.amsterdamevents.aeserver.app.models.AEvent;
import nl.team12.amsterdamevents.aeserver.app.repositories.AEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// mapped naar de '/aevents' end-point
@RequestMapping("/aevents")
public class AEventsController {
    private AEventsRepository aEventsRepository;

    @Autowired
    public AEventsController(AEventsRepository aEventsRepository) {
        this.aEventsRepository = aEventsRepository;
    }

    @GetMapping("")
    public List<AEvent> getAllEvents() {
        return aEventsRepository.findAll();
    }
}
