package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.AEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("AEVENTS.JPA")
@Primary
@Transactional
public class AEventsRepositoryJpa extends AbstractEntityRepositoryJpa<AEvent> {

    @PersistenceContext
    private EntityManager entityManager;

    public AEventsRepositoryJpa() {
        super(AEvent.class);
    }

    @Override
    public List<AEvent> findAll() {
        TypedQuery<AEvent> query =
                this.entityManager.createQuery("select e from AEvent e", AEvent.class);

        return query.getResultList();
    }

    @Override
    public AEvent findById(long id) {
        return this.entityManager.find(AEvent.class, id);
    }

    @Override
    public AEvent save(AEvent aEvent) {
        if (aEvent.getId() == 0) {
            entityManager.persist(aEvent);
        } else {
            entityManager.merge(aEvent);
        }
        return aEvent;
    }

    @Override
    public boolean deleteById(long id) {
        // first find the a-event
        AEvent aEvent = findById(id);
        // if a-event found then remove it and turn true
        if (aEvent != null) {
            this.entityManager.remove(aEvent);
            return true;
        }
        return false;
    }
}
