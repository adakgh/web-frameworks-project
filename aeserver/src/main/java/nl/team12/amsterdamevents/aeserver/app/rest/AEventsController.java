package nl.team12.amsterdamevents.aeserver.app.rest;

import nl.team12.amsterdamevents.aeserver.app.models.AEvent;
import nl.team12.amsterdamevents.aeserver.app.repositories.AEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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


    @GetMapping(path = "/aevents/{id}")
    @JsonView(DataView.DynamicFilter.class)
    public AEvent getAEventById(@PathVariable Long id) throws AEventNotFoundException {
        AEvent aEvent = aEventsRepository.findById(id);
        if (aEvent == null) {
            throw new AEventNotFoundException("Avent-id= " + id + " Not found.");
        } else {
            return aEvent;
        }

        @JsonView(DataView.DynamicFilter.class)
        @PostMapping(path = "/aevents")
        public ResponseEntity<AEvent> createAEvent (@RequestBody AEvent aEvent){
            AEvent savedAEvent = aEventsRepository.save(aEvent);

            URI location = ServletUriComponentsBuilder.
                    fromCurrentRequest().path("/aevents/{id}").
                    buildAndExpand(savedAEvent.getId()).toUri();

            return ResponseEntity.created(location)
                    .body(savedAEvent);
        }
        @JsonView(DataView.DynamicFilter.class)
        @PutMapping(path = "/aevents/{id}")
        public boolean putAEvent (@RequestBody AEvent aEvent,@PathVariable long id){
            boolean status = false;
            AEvent ae = aEventsRepository.findById(id);
            if (ae == null) {
                throw new AEventNotFoundException("AEvent-id= " + id + " does not match path parameter= " + id);

            }
            if (ae != null) {
                ae.setTitle(aEvent.getTitel());
                ae.setStart(aEvent.getStart());
                ae.setEnd(aEvent.getEnd());
                ae.setStatus(aEvent.getStatus());
                ae.setParticipationFee(aEvent.getParticipationFee());
                ae.setMaxParticipants(aEvent.getMaxParticipants());
                ae.setDescription(aEvent.getDescription());
                status = true;

            } else {
                aEventsRepository.save(aEvent);
                status = false;
            }
            return status;

        }
        @JsonView(DataView.DynamicFilter.class)
        @DeleteMapping(path = "/aevents/{id}")
        public boolean deletAEvent (@PathVariable Long id) throws AEventNotFoundException {
            boolean status = false;

            if (!aEventsRepository.deletById(id)) {
                throw new AEventNotFoundException("AEvent-id= " + id + " does not match path parameter= " + id + ".");
            } else {

                aEventsRepository.deleteById(id);
                status = true;
            }
            return status;
        }
    }

