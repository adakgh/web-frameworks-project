package nl.team12.amsterdamevents.aeserver.app.rest;

import com.fasterxml.jackson.annotation.JsonView;
import nl.team12.amsterdamevents.aeserver.app.exceptions.PreConditionFailed;
import nl.team12.amsterdamevents.aeserver.app.exceptions.ResourceNotFoundException;
import nl.team12.amsterdamevents.aeserver.app.models.AEvent;
import nl.team12.amsterdamevents.aeserver.app.repositories.AEventsRepository;
import nl.team12.amsterdamevents.aeserver.app.views.AEventView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    public URI getLocationURI(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    // GET mapping which gets all the aEvents
    @GetMapping("/aevents")
    public List<AEvent> getAllEvents() {
        return aEventsRepository.findAll();
    }

    // GET mapping of the summary of the aEvents
    @GetMapping("/aevents/summary")
    @JsonView(value = AEventView.SummaryView.class)
    public List<AEvent> getAEventsSummary() {
        return aEventsRepository.findAll();
    }

    // GET mapping which uses the AEvent id to deliver the aEvent that is identified by the specified path variable
    // Exception if request consists of a non-existing id
    @GetMapping("/aevents/{id}")
    public AEvent getAEventById(@PathVariable Long id) {
        AEvent aEvent = aEventsRepository.findById(id);
        if (aEvent == null) {
            throw new ResourceNotFoundException("AEvent id=" + id + " not found");
        }
        return aEvent;
    }

    // POST mapping which adds a new aEvent to the repository
    // Exception if id is different from the id that is provided with the aEvent in the request body
    // works without @RequestBody?
    @PostMapping("/aevents")
    public ResponseEntity<AEvent> saveAEvent(AEvent aEvent) {
        AEvent savedAEvent = aEventsRepository.save(aEvent);

        URI location = getLocationURI(savedAEvent.getId());
        return ResponseEntity.created(location).body(savedAEvent);
    }

    // PUT mapping which updates/replaces the stored aEvent identified by id
    // Exception if id is different from the id that is provided with the aEvent in the request body
    // works without @RequestBody?
    @PutMapping("/aevents/{id}")
    public ResponseEntity<AEvent> updateAEvent(@PathVariable Long id, AEvent aEvent) {
        if (id.equals(aEvent.getId())) {
            AEvent savedAEvent = aEventsRepository.save(aEvent);
            return ResponseEntity.accepted().body(savedAEvent);
        }
        throw new PreConditionFailed("AEvent-id=" + aEvent.getId() + " does not match path parameter=" + id);
    }

    // DELETE mapping which uses the id to remove the identified aEvent from the repository
    // Exception if request consists of a non-existing id
    @DeleteMapping("/aevents/{id}")
    public ResponseEntity deleteAEventById(@PathVariable Long id) {
        if (aEventsRepository.findById(id) == null) {
            throw new ResourceNotFoundException("AEvent id=" + id + " not found");
        } else {
            aEventsRepository.deleteById(id);
            return new ResponseEntity(id, HttpStatus.NO_CONTENT);
        }
    }
}

