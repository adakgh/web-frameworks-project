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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDateTime;
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
    public List<AEvent> getAllEvents(@RequestParam(name = "title", required = false) String title,
                                     @RequestParam(name = "status", required = false) String status,
                                     @RequestParam(name = "minRegistrations", required = false) Integer minRegistration) {

        int param = (title != null ? 1 : 0) + (minRegistration != null ? 1 : 0) + (status != null ? 1 : 0);
        if (param > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only handle one request parameter title=, status= or minRegistration=");
        }

        if (title != null) {
            return aEventsRepository.findByQuery("AEvent_find_by_title", '%' + title + '%');
        }

        if (status != null) {
            for (AEvent.AEventStatus value : AEvent.AEventStatus.values()) {
                if (value.name().equals(status)) {
                    return aEventsRepository.findByQuery("AEvent_find_by_status", value);
                }
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status=" + status + " is not a valid aEvent status value");
        }

        if (minRegistration != null) {
            return aEventsRepository.findByQuery("AEvent_find_by_minRegistrations", minRegistration);
        }

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
    @PutMapping(path = "/aevents/{id}")
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
    @Transactional
    public ResponseEntity<Registration> addRegistration(@PathVariable long id, @RequestBody LocalDateTime localDateTime) {
        AEvent aEvent = aEventsRepository.findById(id);
        if (!aEvent.getStatus().equals(AEvent.AEventStatus.PUBLISHED)) {
            throw new PreConditionFailed("AEvent-id=" + aEvent.getId() + " is not published.");
        } else if (aEvent.getNumberOfRegistrations() > aEvent.getMaxParticipants() && aEvent.getMaxParticipants() != 0) {
            throw new PreConditionFailed("AEvent-id=" + aEvent.getId() + " has exceeded the number of participants.");
        }

        Registration registration = aEvent.createNewRegistration(localDateTime);
        registrationRepository.save(registration);

        URI location = getLocationURI(aEvent.getId());
        return ResponseEntity.created(location).body(registration);
    }
}

