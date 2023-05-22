package DomainModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Lesson {
    private int lessonId;
    private Arena arena;
    private ArrayList<Rider> riders = new ArrayList<Rider>();
    private Trainer trainer;
    LocalDate date;
    LocalTime time;

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

    public ArrayList<Rider> getRiders() {
        return riders;
    }

    public void addRider(Rider r){
        riders.add(r);
    }

    public void removeRider(Rider r){
        riders.remove(r);
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getAvailableSlot (){
        int size = riders.size();
        return 3-size;
    }
}
