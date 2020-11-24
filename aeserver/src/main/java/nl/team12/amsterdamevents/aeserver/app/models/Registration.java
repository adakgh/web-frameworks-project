package nl.team12.amsterdamevents.aeserver.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class Registration implements Identifiable {
    public static int registrationid = 12345;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Registration_id")
    @SequenceGenerator(name = "Registration_id", initialValue = 10001, allocationSize = 8)
    public long id;
    public String ticketCode;
    public boolean paid;
    public LocalDateTime submissionDate;
    @ManyToOne
    @JsonBackReference
    public AEvent aEvent;

    public Registration(long id, String ticketCode, boolean paid, LocalDateTime submissionDate) {
        this.id = id;
        this.ticketCode = ticketCode;
        this.paid = paid;
        this.submissionDate = submissionDate;
    }

    public Registration() {
    }

    /**
     * A method to create a random registration.
     *
     * @return the created registration.
     */
    public static Registration createRandomRegistration() {
        Registration registration = new Registration();
        registration.paid = getRandomIsPaid();
        return registration;
    }

    /**
     * A method to generate a random boolean.
     *
     * @return true or false.
     */
    public static boolean getRandomIsPaid() {
        return Math.random() < 0.5;
    }

    /**
     * A method to generate a random date and time between two dates.
     *
     * @param start the minimum date.
     * @param end   the maximum date.
     * @return the random date and time created.
     */
    public static LocalDateTime randomDate(LocalDateTime start, LocalDateTime end) {
        long startEpochDay = start.toLocalDate().toEpochDay();
        long endEpochDay = end.toLocalDate().toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);
        LocalTime randomTime = LocalTime.of((int) Math.floor(Math.random() * 23) + 1, (int) Math.floor(Math.random() * 59) + 1, (int) Math.floor(Math.random() * 59) + 1);

        return LocalDate.ofEpochDay(randomDay).atTime(randomTime);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public AEvent getaEvent() {
        return aEvent;
    }

    public void setaEvent(AEvent aEvent) {
        this.aEvent = aEvent;
    }
}
