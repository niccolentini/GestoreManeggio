package DomainModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Lesson {
    private int lessonId;
    private Arena arena;
    private Trainer trainer;
    LocalDate date;
    LocalTime time;

    private int numRider = 0;
    private int maxRiders = 3;

    public Lesson(int lessonId, Arena arena, Trainer trainer, LocalDate date, LocalTime time) {
        this.lessonId = lessonId;
        this.arena = arena;
        this.trainer = trainer;
        this.date = date;
        this.time = time;
    }

    public int getLessonId() {
        return lessonId;
    }

    public Arena getArena() {
        return arena;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getNumRider() {
        return numRider;
    }

    public int getMaxRiders() {
        return maxRiders;
    }
}
