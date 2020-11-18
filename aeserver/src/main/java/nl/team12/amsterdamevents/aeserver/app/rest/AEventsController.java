package nl.team12.amsterdamevents.aeserver.app.rest;

import com.fasterxml.jackson.annotation.JsonView;
import nl.team12.amsterdamevents.aeserver.app.exceptions.PreConditionFailed;
import nl.team12.amsterdamevents.aeserver.app.exceptions.ResourceNotFoundException;
import nl.team12.amsterdamevents.aeserver.app.models.AEvent;
import nl.team12.amsterdamevents.aeserver.app.models.Registration;
import nl.team12.amsterdamevents.aeserver.app.repositories.EntityRepository;
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
@RequestMapping("")
public class AEventsController {
    @Autowired
    private EntityRepository<AEvent> aEventsRepository;

    @Autowired
    private EntityRepository<Registration> registrationRepository;

    public URI getLocationURI(long id) {
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
    public AEvent getAEventById(@PathVariable long id) {
        AEvent aEvent = aEventsRepository.findById(id);
        if (aEvent == null) {
            throw new ResourceNotFoundException("AEvent id=" + id + " not found");
        }
        return aEvent;
    }

    // POST mapping which adds a new aEvent to the repository
    // Exception if id is different from the id that is provided with the aEvent in the request body
    @PostMapping("/aevents")
    public ResponseEntity<Object> saveAEvent(@RequestBody AEvent aEvent) {
        AEvent savedAEvent = aEventsRepository.save(aEvent);

        URI location = getLocationURI(savedAEvent.getId());
        return ResponseEntity.created(location).body(aEvent);
    }

    // PUT mapping which updates/replaces the stored aEvent identified by id
    // Exception if id is different from the id that is provided with the aEvent in the request body
    @PutMapping("/aevents/{id}")
    public ResponseEntity<Object> updateAEvent(@PathVariable long id, @RequestBody AEvent aEvent) {
        if (id == aEvent.getId()) {
            AEvent savedAEvent = aEventsRepository.save(aEvent);
            return ResponseEntity.accepted().body(savedAEvent);
        }
        throw new PreConditionFailed("AEvent-id=" + aEvent.getId() + " does not match path parameter=" + id);
    }

    // DELETE mapping which uses the id to remove the identified aEvent from the repository
    // Exception if request consists of a non-existing id
    @DeleteMapping("/aevents/{id}")
    public ResponseEntity deleteAEventById(@PathVariable long id) {
        if (aEventsRepository.findById(id) == null) {
            throw new ResourceNotFoundException("AEvent id=" + id + " not found");
        } else {
            aEventsRepository.deleteById(id);
            return new ResponseEntity(id, HttpStatus.NO_CONTENT);
        }
    }

    // POST mapping which adds a new registration to an aEvent
    // Exception if the aEvent does not have status ‘PUBLISHED’
    // or the maximum number of participants have been registered already
    @PostMapping("/aevents/{id}/register")
    public ResponseEntity<AEvent> addRegistration(@PathVariable long id, @RequestBody Registration registration) {
        AEvent aEvent = aEventsRepository.findById(id);
        if (!aEvent.getStatus().equals(AEvent.AEventStatus.PUBLISHED)) {
            throw new PreConditionFailed("AEvent-id=" + aEvent.getId() + " is not published.");
        } else if (aEvent.getNumberOfRegistrations() >= aEvent.getMaxParticipants() && aEvent.getMaxParticipants() != 0) {
            throw new PreConditionFailed("AEvent-id=" + aEvent.getId() + " has exceeded the number of participants.");
        } else {
            aEvent.addRegistration(registration);
            registrationRepository.save(registration);
        }

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUri()
        ).body(aEvent);
    }
}

