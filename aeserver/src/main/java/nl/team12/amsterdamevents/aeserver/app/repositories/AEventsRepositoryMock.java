package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.AEvent;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class AEventsRepositoryMock implements AEventsRepository {
    private final List<AEvent> aEvents;
    private long startId = 20008;

    public AEventsRepositoryMock() {
        this.aEvents = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            this.aEvents.add(AEvent.createRandomAEvent());
        }
    }

    // generating new ids starting from id 20008
    private long getNextUniqueId() {
        this.startId++;
        return this.startId;
    }

    // retrieving the next AEvent by id
    private int getAEventIndexById(long id) {
        try {
            int max = (int) aEvents.stream().count();

            return IntStream.range(0, max)
                    .filter(idx ->
                    {
                        return aEvents.get(idx).getId().equals(id);
                    })
                    .findFirst().getAsInt();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "AEvent with this index is not found!");
        }
    }

    // CRUD operations
    // retrieving the list of all a-events
    @Override
    public List<AEvent> findAll() {
        return this.aEvents;
    }

    // retrieving one a-event, identified by a given id
    @Override
    public AEvent findById(long id) {
        return aEvents.get(getAEventIndexById(id));
    }

    // saving an updated or new a-event and returning the previous instance with the same id, or null
    // if no such a-event exists yet
    @Override
    public AEvent save(AEvent aEvent) {
        if (aEvent.getId().equals(0L)) {
            aEvent.setId(getNextUniqueId());
            aEvents.add(aEvent);
        } else {
            int index = getAEventIndexById(aEvent.getId());
            aEvents.set(index, aEvent);
        }
        return aEvent;
    }

    // deleting the a-event identified by the given id,
    // and returning the a-event that was deleted, or null if none existed
    @Override
    public boolean deleteById(long id) {
        AEvent aEvent = findById(id);
        return aEvents.remove(aEvent);
    }
}
