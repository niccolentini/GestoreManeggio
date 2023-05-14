package Controller;

import DomainModel.Arena;
import DomainModel.Lesson;
import DomainModel.Trainer;

import java.util.ArrayList;
import java.util.Objects;

public class LessonsController {
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();

    public void addLesson (int lessonId, Arena arena, Trainer trainer){
        Lesson l = new Lesson(lessonId, arena, trainer);
        lessons.add(l);
    }

    public void removeLesson(int lessonId) {
        for (Lesson l : lessons){
            if(l.getLessonId() == lessonId){
                lessons.remove(l);
                System.out.println("Lezione rimossa!");
            }
        }
        System.out.println("L'ID inserito non corrisponde a nessuna lezione");
    }

    public ArrayList<Lesson> getLessonsForTrainer(String fisCod) {
        ArrayList<Lesson> lft = new ArrayList<Lesson>();
        for(Lesson l : lessons){
            if(Objects.equals(l.getTrainer().getFiscalCod(), fisCod)){
                lft.add(l);
            }
        }
        return lft;
    }

    public ArrayList<Lesson> getAllLessons(){
        return this.lessons;
    }


}
