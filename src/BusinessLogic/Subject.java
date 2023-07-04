package BusinessLogic;

import DomainModel.Lesson;

import java.util.ArrayList;

public interface Subject {
        //non è necessaria la lista di observer in quanto facciamo riderimento al database per sapere gli observer di una lezione
        public void notifyObservers(int lessonId) throws Exception;
}
