package DomainModel.Membership;

public interface Membership {

    public int getNumLessons();
    public float getPrice();
    public String getDescription();
    public void useLesson();
    public void earnLesson();
}
