package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.AEvent;

import java.util.List;

public interface AEventsRepository {
    List<AEvent> findAll();

    AEvent findById(int id);

    AEvent save(AEvent aEvent);

    void deleteById(int id);
}
