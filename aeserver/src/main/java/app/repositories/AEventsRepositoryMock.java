package app.repositories;

import app.models.AEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AEventsRepositoryMock implements AEventsRepository {
    private List<AEvent> aEvents = new ArrayList<>();

    public AEventsRepositoryMock() {
        for (int i = 0; i < 9; i++) {
            this.aEvents.add(AEvent.createRandomAEvent());
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
    public AEvent findById(int id) {
        return aEvents.stream()
                .filter(user -> user.getId() == id)
                .findFirst().orElse(null);
    }

    // saving an updated or new a-event and returning the previous instance with the same id, or null
    // if no such a-event exists yet
    @Override
    public AEvent save(AEvent aEvent) {
        for (int i = 0; i < aEvents.size(); i++) {
            if (aEvents.get(i).getId() == aEvent.getId()) {
                aEvents.set(i, aEvent);
                return aEvent;
            }
        }
        aEvents.add(aEvent);
        return aEvent;
    }

    // deleting the a-event identified by the given id,
    // and returning the a-event that was deleted, or null if none existed
    @Override
    public void deleteById(int id) {
        AEvent aEvent = this.findById(id);
        this.aEvents.remove(aEvent);
    }
}
