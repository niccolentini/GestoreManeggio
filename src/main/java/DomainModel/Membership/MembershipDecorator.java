package main.java.DomainModel.Membership;

public abstract class MembershipDecorator implements Membership {
    protected Membership membership;

    public MembershipDecorator(Membership membership) {
        this.membership = membership;
    }

    @Override
    public int getNumLessons() {
        return membership.getNumLessons();
    }

    @Override
    public void setNumLessons(int numLessons) {
        membership.setNumLessons(numLessons);
    }

    @Override
    public float getPrice() {
        return membership.getPrice();
    }

    @Override
    public String getDescription() {
        return membership.getDescription();
    }
}
