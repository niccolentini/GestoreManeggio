package DomainModel;

public abstract class User {
    private String fiscalCod;
    private String firstName;
    private String lastName;

    public User(String fiscalCod, String firstName, String lastName) {
        this.fiscalCod = fiscalCod;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFiscalCod() {
        return fiscalCod;
    }

    public void setFiscalCod(String fiscalCod) {
        this.fiscalCod = fiscalCod;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
