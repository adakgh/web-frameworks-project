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
    private List<AEvent> aEvents = new ArrayList<>();
    private int idIncrement = 20001;

    public AEventsRepositoryMock() {
        for (int i = 0; i < 8; i++) {
            this.aEvents.add(AEvent.createRandomAEvent());
        }
    }

    private long getNextUniqueId() {
        return Long.parseLong(String.format("2020-%05d", idIncrement++));
    }

    // CRUD operations
    // retrieving the list of all a-events
    @Override
    public List<AEvent> findAll() {
        return this.aEvents;
    }

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!");
        }
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

//        for (int i = 0; i < aEvents.size(); i++) {
//            if (aEvents.get(i).getId() == aEvent.getId()) {
//                aEvents.set(i, aEvent);
//                return aEvent;
//            }
//        }
//        aEvents.add(aEvent);
        return aEvent;
    }

    // deleting the a-event identified by the given id,
    // and returning the a-event that was deleted, or null if none existed
    @Override
    public boolean deleteById(long id) {
        AEvent aEvent = this.findById(id);
        this.aEvents.remove(aEvent);
        return true;
    }
}
