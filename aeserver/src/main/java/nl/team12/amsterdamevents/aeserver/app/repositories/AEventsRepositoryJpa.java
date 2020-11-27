package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.AEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("AEVENTS.JPA")
@Primary
@Transactional
public class AEventsRepositoryJpa extends AbstractEntityRepositoryJpa<AEvent> {

    public AEventsRepositoryJpa() {
        super(AEvent.class);
    }
}
