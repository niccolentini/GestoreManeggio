package main.java.DomainModel.Membership;

public interface Membership {
    public int getNumLessons();
    public void setNumLessons(int numLessons);
    public String getType();
    public float getPrice();
    public String getDescription();
}