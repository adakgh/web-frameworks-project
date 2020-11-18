package nl.team12.amsterdamevents.aeserver.app.repositories;


import nl.team12.amsterdamevents.aeserver.app.models.Identifiable;

import java.util.List;

public interface EntityRepository<E extends Identifiable> {
    List<E> findAll();              // finds all available instances
    E findById(long id);            // finds one instance identified by id
                                    // returns null if the instance does not exist
    E save(E entity);               // updates or creates the instance matching entity.getId()
                                    // generates a new unique Id if entity.getId()==0
    boolean deleteById(long id);    // deletes the instance identified by entity.getId()
                                    // returns whether an existing instance has been deleted
}
