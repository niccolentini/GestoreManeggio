package DomainModel.Membership;

public interface Membership {
    public int getNumLessons();
    public void setNumLessons(int numLessons);
    public String getType();

    public float getPrice();

    public String getDescription();

}

//todo: nel membership DAO farai un metodo per creare il tipo di membership richiesto tramite il passaggio di parole chiave
//todo: quindi box = pacchetto box, lezioni = pacchetto lezioni , groom = pacchetto groom. Lavorerà tramite uno switch