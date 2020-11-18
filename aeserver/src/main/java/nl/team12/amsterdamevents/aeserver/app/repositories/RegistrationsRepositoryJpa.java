package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.Registration;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository("REGISTRATIONS.JPA")
@Transactional
public class RegistrationsRepositoryJpa extends AbstractEntityRepositoryJpa<Registration> {

    @PersistenceContext
    private EntityManager entityManager;

    public RegistrationsRepositoryJpa() {
        super(Registration.class);
    }

    @Override
    public List<Registration> findAll() {
        TypedQuery<Registration> query =
                this.entityManager.createQuery("select r from Registration r", Registration.class);

        return query.getResultList();
    }

    @Override
    public Registration findById(long id) {
        return this.entityManager.find(Registration.class, id);
    }

    @Override
    public Registration save(Registration registration) {
        if (registration.getId() == 0) {
            this.entityManager.persist(registration);
        }
        return this.entityManager.merge(registration);
    }

    @Override
    public boolean deleteById(long id) {
        // first find the registration
        Registration registration = findById(id);
        // if registration found then remove it and turn true
        if (registration != null) {
            this.entityManager.remove(registration);
            return true;
        }
        return false;
    }
}
