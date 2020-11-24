package nl.team12.amsterdamevents.aeserver.app.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import nl.team12.amsterdamevents.aeserver.app.views.AEventView;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class AEvent implements Identifiable {
    private static int eventid = 20001;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AEvent_id")
    @SequenceGenerator(name = "AEvent_id", initialValue = 20001, allocationSize = 8)
    public long id;
    @JsonView(AEventView.SummaryView.class)
    public String title;
    public LocalDate start;
    public LocalDate end;
    public String description;
    @JsonView(AEventView.SummaryView.class)
    @Enumerated(EnumType.STRING)
    public AEventStatus status;
    public double participationFee;
    public int maxParticipants;
    public boolean isTicketed;
    @OneToMany(mappedBy = "aEvent", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    public List<Registration> registrations = new ArrayList<>();

    public AEvent(long id, String title, LocalDate start, LocalDate end, String description, AEventStatus status, boolean isTicketed, double participationFee, int maxParticipants) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.description = description;
        this.status = status;
        this.isTicketed = isTicketed;
        this.participationFee = participationFee;
        this.maxParticipants = maxParticipants;
    }

    public AEvent() {
    }

    /**
     * A method to create a random AEvent.
     *
     * @return the created AEvent.
     */
    public static AEvent createRandomAEvent() {
        AEvent aEvent = new AEvent();

        aEvent.title = "Backend Fantastic Event-" + eventid++;

        aEvent.start = randomDate(LocalDate.of(2020, Month.OCTOBER, 8), LocalDate.of(2020, Month.OCTOBER, 18));
        aEvent.end = randomDate(aEvent.start, LocalDate.of(2021, Month.FEBRUARY, 1));

        aEvent.description = "a fantastic description";

        aEvent.status = getRandomStatus();

        aEvent.participationFee = Math.round((Math.random() * 50) * 100.0) / 100.0;

        aEvent.isTicketed = Math.random() < 0.5;

        if (!aEvent.isTicketed) {
            aEvent.participationFee = 0;
            aEvent.maxParticipants = 0;
        } else {
            aEvent.maxParticipants = (int) Math.floor((Math.random() * 51));
        }

        return aEvent;
    }

    /**
     * A method to generate a random date between two dates.
     *
     * @param start the minimum date.
     * @param end   the maximum date.
     * @return the random date created.
     */
    public static LocalDate randomDate(LocalDate start, LocalDate end) {
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    /**
     * A method to select a random status.
     *
     * @return the selected status.
     */
    public static AEventStatus getRandomStatus() {
        var key = Math.floor(Math.random() * Math.floor(3) + 1);
        if (key == 1) {
            return AEventStatus.PUBLISHED;
        }
        if (key == 2) {
            return AEventStatus.CANCELED;
        }
        if (key == 3) {
            return AEventStatus.DRAFT;
        } else {
            return null;
        }
    }

    /**
     * A method to add a registration to an AEvent and to the registrations list.
     *
     * @param registration the registration that is going to be added.
     */
    public void addRegistration(Registration registration) {
        registration.setaEvent(this);
        this.registrations.add(registration);
    }

    /**
     * A method that creates and adds new registration for this aevent
     * checks whether this.status == PUBLISHED and
     * maxParticipants has not been exceeded
     *
     * @param submissionDateTime the date and time of submission of the registration.
     * @return the created registration or null.
     */
    public Registration createNewRegistration(LocalDateTime submissionDateTime) {
        LocalDateTime randomSubmissionDateTime = Registration.randomDate(submissionDateTime, this.end.atStartOfDay());
        if (this.getStatus().equals(AEvent.AEventStatus.PUBLISHED) && this.getMaxParticipants() >= this.getNumberOfRegistrations() || (this.getStatus().equals(AEventStatus.PUBLISHED) && this.getMaxParticipants() == 0)) {
            // create the registration
            Registration registration = Registration.createRandomRegistration();
            // set the submissionDateTime
            registration.submissionDate = randomSubmissionDateTime;
            // set the ticketCode
            registration.ticketCode = "tc-" + Registration.registrationid++;
            // add the registration
            this.addRegistration(registration);
            return registration;
        } else {
            return null;
        }
    }

    /**
     * A method to keep count of the number of registrations per AEvent.
     *
     * @return the number of registrations of an AEvent.
     */
    public int getNumberOfRegistrations() {
        int count = 0;
        for (Registration registration : registrations) {
            if (this == registration.getaEvent()) {
                count++;
            }
        }
        return count;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public LocalDate getStart() {
        return start;
    }

    @JsonView(AEventView.SummaryView.class)
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public AEventStatus getStatus() {
        return status;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AEvent aEvent = (AEvent) o;
        return id == aEvent.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum AEventStatus {
        DRAFT, PUBLISHED, CANCELED;
    }
}
