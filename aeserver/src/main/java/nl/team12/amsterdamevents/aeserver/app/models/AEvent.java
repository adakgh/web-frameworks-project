package nl.team12.amsterdamevents.aeserver.app.models;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.ThreadLocalRandom;

public class AEvent {
    private static int eventid = 20001;
    public int id;
    public String title;
    public LocalDate start;
    public LocalDate end;
    public String description;
    public AEventStatus status;
    public double participationFee;
    public int maxParticipants;
    private boolean isTicketed;

    public AEvent(int id, String title, LocalDate start, LocalDate end, String description, AEventStatus status, boolean isTicketed, double participationFee, int maxParticipants) {
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
        aEvent.title = "The Fantastic Event-" + aEvent.id;

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

    @Override
    public String toString() {
        return "AEvent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", isTicketed=" + isTicketed +
                ", participationFee=" + participationFee +
                ", maxParticipants=" + maxParticipants +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }

    public AEventStatus getStatus() {
        return status;
    }

    public boolean isTicketed() {
        return isTicketed;
    }

    public double getParticipationFee() {
        return participationFee;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public boolean equals(AEvent e) {
        return this.title.equals(e.title) &&
                this.description.equals(e.description) &&
                this.start == e.start &&
                this.end == e.end &&
                this.status == e.status &&
                this.maxParticipants == e.maxParticipants &&
                this.participationFee == e.participationFee;
    }

    public enum AEventStatus {
        DRAFT, PUBLISHED, CANCELED;
    }
}
