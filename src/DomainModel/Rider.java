package DomainModel;

public class Rider extends User{
    private Horse horse;

    public Rider(String fiscalCod, String firstName, String lastName, Horse horse) {
        super(fiscalCod, firstName, lastName);
        this.horse = horse;
    }

}
