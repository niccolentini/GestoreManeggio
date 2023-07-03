package DomainModel;

public class Rider extends User{
    private String horse;

    public Rider(String fiscalCod, String firstName, String lastName, String horse) {
        super(fiscalCod, firstName, lastName);
        this.horse = horse;
    }

    public String getHorse() {
        return horse;
    }

}
