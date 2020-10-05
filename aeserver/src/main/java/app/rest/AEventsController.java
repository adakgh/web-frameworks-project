package app.rest;

import app.models.AEvent;
import app.repositories.AEventsRepositoryMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// mapped naar de '/aevents' end-point
@RequestMapping("/aevents")
public class AEventsController {
    @Autowired
    private AEventsRepositoryMock aEventsRepository = new AEventsRepositoryMock();

    @GetMapping("")
    public List<AEvent> getAllEvents() {
        return aEventsRepository.findAll();
    }
}
