package DomainModel.Membership;

public class LessonsPack extends MembershipDecorator{
    private int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType(){
        return "LessonsPack";
    }
    @Override
    public int getID() {
        return ID;
    }

    private int lessons = 10;
    public LessonsPack(Membership membership) {
        super(membership);
    }

    @Override
    public int getNumLessons() {
        return lessons;
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
        lessons = lessons - 1;
    }

    public void earnLesson(){
        lessons = lessons + 1;
    }
}
