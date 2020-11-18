package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.Registration;

import java.util.List;

public interface RegistrationsRepository {
    List<Registration> findAll();

    Registration findById(long id);

    Registration save(Registration registration);

    boolean deleteById(long id);
}
