package test.java.domainModel.membership;
import main.java.DomainModel.Membership.BoxPack;
import main.java.DomainModel.Membership.LessonsPack;
import main.java.DomainModel.Membership.Membership;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoxPackTest {
    @Test
    public void LessonPack_test() throws Exception {
        Membership m = new BoxPack();
        Membership l = new LessonsPack(m);
        Assertions.assertEquals(10, l.getNumLessons());
    }
    @Test
    public void LessonPack_test2() throws Exception {
        Membership m = new BoxPack();
        Membership l = new LessonsPack(m);
        Assertions.assertEquals(10, l.getNumLessons());
        l.setNumLessons(5);
        Assertions.assertEquals(5, l.getNumLessons());
    }
}
