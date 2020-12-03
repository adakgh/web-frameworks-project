package nl.team12.amsterdamevents.aeserver.app;

import nl.team12.amsterdamevents.aeserver.app.models.AEvent;
import nl.team12.amsterdamevents.aeserver.app.models.Registration;
import nl.team12.amsterdamevents.aeserver.app.models.User;
import nl.team12.amsterdamevents.aeserver.app.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootApplication
public class AeserverApplication implements CommandLineRunner {

    @Autowired
    EntityRepository<AEvent> aEventsRepository;

    @Autowired
    EntityRepository<Registration> registrationsRepository;

    @Autowired
    EntityRepository<User> userRepository;

    public static void main(String[] args) {
        SpringApplication.run(AeserverApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) {
        System.out.println("Running CommandLine Startup");
        this.createInitialAEvents();
    }

    private void createInitialAEvents() {
        // check whether the repo is empty
        List<AEvent> aEvents = this.aEventsRepository.findAll();
        List<Registration> registrations = this.registrationsRepository.findAll();
        List<User> users = this.userRepository.findAll();
        if (aEvents.size() > 0 && registrations.size() > 0) return;
        if (registrations.size() > 0) return;
        System.out.println("Configuring some initial data");

        for (int i = 0; i < 9; i++) {
            // create and add new aEvents with random data
            AEvent aEvent = AEvent.createRandomAEvent();

            User user = new User(1, "sjonnie", "sjonnie@hva.nl", "sjonnie", false);

            // create and add new registrations with random data
            for (int j = 0; j < 3; j++) {
                Registration registration = aEvent.createNewRegistration(aEvent.getStart().atStartOfDay());
                if (registration != null) {
                    registration.setaEvent(aEvent);
                    this.registrationsRepository.save(registration);
                }
            }

            this.aEventsRepository.save(aEvent);
            this.userRepository.save(user);
        }

    }
}
