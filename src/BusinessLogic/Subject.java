package BusinessLogic;

import DomainModel.Lesson;

import java.util.ArrayList;

public class Subject {
        private ArrayList<Observer> observers = new ArrayList<>();

        public void attach(Observer observer) {
            observers.add(observer);
        }

        public void detach(Observer observer) {
            observers.remove(observer);
        }

        public void notifyObservers(Lesson l) {
            for (Observer observer : observers) {
                observer.update(l);
            }
        }
}