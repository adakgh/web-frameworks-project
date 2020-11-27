package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public abstract class AbstractEntityRepositoryJpa<E extends Identifiable> implements EntityRepository<E> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<E> theEntityClass;

    public AbstractEntityRepositoryJpa(Class<E> entityClass) {
        this.theEntityClass = entityClass;
        System.out.println("Created " + this.getClass().getName() +
                "<" + this.theEntityClass.getSimpleName() + ">");
    }

    @Override
    public List<E> findAll() {
        TypedQuery<E> query =
                this.entityManager.createQuery("select e from " + this.theEntityClass.getSimpleName() + " e",
                        theEntityClass);;

        return query.getResultList();
    }

    @Override
    public E findById(long id) {
        TypedQuery<E> query =
                this.entityManager.createQuery(
                        "select e from " + this.theEntityClass.getSimpleName() + " e where e.id =" + id,
                        theEntityClass);
        return query.getSingleResult();
    }

    @Override
    public E save(E e) {
        if (e.getId() == 0) {
            this.entityManager.persist(e);
        }
        return this.entityManager.merge(e);
    }

    @Override
    public boolean deleteById(long id) {
        // first find
        E e = findById(id);
        // then remove it and turn true
        if (e != null) {
            this.entityManager.remove(e);
            return true;
        }
        return false;
    }

    @Override
    public List<E> findByQuery(String jpqlName, Object... params) {
        return null;
    }
}
