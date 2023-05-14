package DomainModel.Membership;

public abstract class MembershipDecorator implements Membership {
    @Override
    public int getNumLessons() {
        return 0;
    }

    @Override
    public float getPrice() {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void useLesson() {

    }

    @Override
    public void earnLesson() {

    }
}
