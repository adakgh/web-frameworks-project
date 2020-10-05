package app.repositories;

import app.models.AEvent;

import java.util.List;

public interface AEventsRepository {
    List<AEvent> findAll();

    AEvent findById(int id);

    AEvent save(AEvent aEvent);

    void deleteById(int id);
}
