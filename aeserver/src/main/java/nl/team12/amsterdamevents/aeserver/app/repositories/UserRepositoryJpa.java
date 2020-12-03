package nl.team12.amsterdamevents.aeserver.app.repositories;

import nl.team12.amsterdamevents.aeserver.app.models.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("USERS.JPA")
@Transactional
public class UserRepositoryJpa extends AbstractEntityRepositoryJpa<User> {

    public UserRepositoryJpa() {
        super(User.class);
    }
}
