package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.Registration;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("REGISTRATIONS.JPA")
@Transactional
public class RegistrationsRepositoryJpa extends AbstractEntityRepositoryJpa<Registration> {

    public RegistrationsRepositoryJpa() {
        super(Registration.class);
    }
}
