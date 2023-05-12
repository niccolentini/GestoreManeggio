package DomainModel;

public class Horse {
    private int HorseId;
    private String name;
    private String info;

    public Horse(int horseId, String name, String info) {
        HorseId = horseId;
        this.name = name;
        this.info = info;
    }

    public int getHorseId() {
        return HorseId;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}
