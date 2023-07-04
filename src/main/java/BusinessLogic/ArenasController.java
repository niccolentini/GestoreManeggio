package main.java.BusinessLogic;
import main.java.DAO.ArenaDAO;
import main.java.DAO.LessonDAO;
import main.java.DomainModel.Arena;

public class ArenasController {
    ArenaDAO arenaDAO;
    LessonDAO lessonDAO;

    public ArenasController(ArenaDAO arenaDAO, LessonDAO lessonDAO){
        this.arenaDAO = arenaDAO;
        this.lessonDAO = lessonDAO;
    }

    public Arena getArena(int idArena) throws Exception{
        return arenaDAO.get(idArena);
    }

    public void addArena(String name) throws Exception{
        Arena arena = new Arena(name, arenaDAO.getNextId());
        arenaDAO.add(arena);
    }

    public void disableArena (int idArena) throws Exception{
        //è possibile disabilitare solo arene che non hanno prenotazioni
        Arena arena = arenaDAO.get(idArena);
        if(arena==null) {throw new Exception("L'arena selezionata non esiste.");}

        if(!arena.isAvailable()) { throw new Exception("L'arena selezionata risulta già disabilitata.");}
        if(lessonDAO.isArenaBookedForLesson(idArena)) { throw new Exception("L'arena selezionata non può essere disabilitata in quanto ci sono lezioni prenotate.");}
        arena.setAvailable(false);
        arenaDAO.update(arena);
    }

    public void enableArena(int idArena) throws Exception{
        Arena arena = arenaDAO.get(idArena);
        if(arena==null) {throw new IllegalArgumentException("L'arena selezionata non esiste.");}

        if(arena.isAvailable()) { throw new IllegalArgumentException("L'arena selezionata risulta già abilitata.");}
        arena.setAvailable(true);
        arenaDAO.update(arena);
    }


}
