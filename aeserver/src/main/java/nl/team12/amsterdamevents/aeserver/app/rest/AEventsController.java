package nl.team12.amsterdamevents.aeserver.app.rest;

import com.fasterxml.jackson.annotation.JsonView;
import nl.team12.amsterdamevents.aeserver.app.models.AEvent;
import nl.team12.amsterdamevents.aeserver.app.repositories.AEventsRepository;
import nl.team12.amsterdamevents.aeserver.app.views.AEventView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
// mapped naar de '/aevents' end-point
@RequestMapping("")
public class AEventsController {
    @Autowired
    private final AEventsRepository aEventsRepository;

    public AEventsController(AEventsRepository aEventsRepository) {
        this.aEventsRepository = aEventsRepository;
    }

    private URI getLocationURI(Long id) {
        return ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").
                buildAndExpand(id).toUri();
    }

    // GET mapping which gets all the aEvents
    @GetMapping("/aevents")
    public List<AEvent> getAllEvents() {
        return aEventsRepository.findAll();
    }

    @GetMapping("/aevents/summary")
    @JsonView(value = AEventView.SummaryView.class)
    public List<AEvent> getAEventsSummary(){
        return aEventsRepository.findAll();
    }

    // GET mapping which uses the AEvent id to deliver the aEvent that is identified by the specified path variable
    @GetMapping("/aevents/{id}")
    public AEvent getAEventById(@PathVariable Long id) throws Exception {
        AEvent aEvent = aEventsRepository.findById(id);
        if (aEvent == null) {
            throw new Exception("AEvent id= " + id + " not found.");
        } else {
            return aEvent;
        }
    }

    // POST mapping which adds a new aEvent to the repository
    @PostMapping("/aevents")
    public ResponseEntity<AEvent> saveAEvent(@RequestBody AEvent aEvent) {
        AEvent savedAEvent = aEventsRepository.save(aEvent);

        URI location = getLocationURI(savedAEvent.getId());
        return ResponseEntity.created(location).body(savedAEvent);
    }

    // PUT mapping which updates/replaces the stored aEvent identified by id
    @PutMapping("/aevents/{id}")
    public ResponseEntity<AEvent> updateAEvent(@PathVariable Long id, @RequestBody AEvent aEvent) {
        if (id.equals(aEvent.getId())) {
            AEvent savedAEvent = aEventsRepository.save(aEvent);
            return ResponseEntity.accepted().body(savedAEvent);
        }
        throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "AEvent not found with id: " + id);
    }

    // DELETE mapping which uses the id to remove the identified aEvent from the repository
    @DeleteMapping("/aevents/{id}")
    public ResponseEntity<URI> deleteAEventById(@PathVariable Long id) {
        aEventsRepository.deleteById(id);
        return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUri());
    }
}

