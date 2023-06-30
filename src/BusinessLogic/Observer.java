package BusinessLogic;

import DomainModel.Lesson;

public interface Observer {
    void update(Lesson lesson);
}
