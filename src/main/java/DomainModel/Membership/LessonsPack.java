package main.java.DomainModel.Membership;

public class LessonsPack extends MembershipDecorator{
    public String getType(){
        return "LessonsPack";
    }

    public LessonsPack(Membership membership) {
        super(membership);
        super.setNumLessons(10);
    }
    @Override
    public int getNumLessons() {
        return super.getNumLessons();
    }

    @Override
    public float getPrice() {
        return super.getPrice()+150;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " Pacchetto lezioni, ottieni 10 lezioni da utilizzare con l'istruttore che preferisci.";
    }

    public void useLesson(){
        super.setNumLessons(super.getNumLessons()-1);
    }

    public void earnLesson(){
        super.setNumLessons(super.getNumLessons()+1);
    }
}
