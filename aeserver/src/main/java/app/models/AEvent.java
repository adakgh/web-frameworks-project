package app.models;

import java.util.Date;

public class AEvent {
    private static int eventid = 20001;
    public int id;
    public String title;
    public Date start;
    public Date end;
    public String description;
    public AEventStatus status;
    public boolean isTicketed;
    public double participationFee;
    public int maxParticipants;

    public AEvent(int id, String title, Date start, Date end, String description, AEventStatus status, boolean isTicketed, double participationFee, int maxParticipants) {
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

        aEvent.start = new Date();
        aEvent.end = new Date();

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

    public enum AEventStatus {
        DRAFT, PUBLISHED, CANCELED;
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

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
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
}
