package nl.team12.amsterdamevents.aeserver.app.models;

import com.fasterxml.jackson.annotation.JsonView;
import nl.team12.amsterdamevents.aeserver.app.views.AEventView;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.ThreadLocalRandom;

public class AEvent {
    private static int eventid = 20001;
    public long id;
    @JsonView(AEventView.SummaryView.class)
    public String title;
    public LocalDate start;
    public LocalDate end;
    public String description;
    @JsonView(AEventView.SummaryView.class)
    public AEventStatus status;
    public double participationFee;
    public int maxParticipants;
    private boolean isTicketed;

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

    public static AEvent createRandomAEvent() {
        AEvent aEvent = new AEvent();

        aEvent.id = eventid++;
        aEvent.title = "Backend Fantastic Event-" + aEvent.id;

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
            aEvent.maxParticipants = (int) Math.floor((Math.random() + 1) * 6 * 100);
        }

        return aEvent;
    }

    public static LocalDate randomDate(LocalDate start, LocalDate end) {
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

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

    @JsonView(AEventView.SummaryView.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum AEventStatus {
        DRAFT, PUBLISHED, CANCELED;
    }
}
