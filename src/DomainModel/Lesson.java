package DomainModel;

import java.util.ArrayList;

public class Lesson {
    private int lessonId;
    private Arena arena;
    private ArrayList<Rider> riders = new ArrayList<Rider>();
    private Trainer trainer;

    //todo aggiungi la data

    public Lesson(int lessonId, Arena arena, Trainer trainer) {
        this.lessonId = lessonId;
        this.arena = arena;
        this.trainer = trainer;
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

    public int getAvailableSlot (){
        int size = riders.size();
        return 3-size;
    }
}
