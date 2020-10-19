package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.AEvent;

import java.util.List;

public interface AEventsRepository {
    List<AEvent> findAll();

    AEvent findById(long id);

    AEvent save(AEvent aEvent);

    boolean deleteById(long id);
}
