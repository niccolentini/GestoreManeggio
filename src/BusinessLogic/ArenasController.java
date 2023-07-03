package BusinessLogic;
import DAO.ArenaDAO;
import DAO.LessonDAO;
import DomainModel.Arena;
import java.util.ArrayList;

public class ArenasController {
    ArenaDAO arenaDAO;
    LessonDAO lessonDAO;

    public ArenasController(ArenaDAO arenaDAO, LessonDAO lessonDAO){
        this.arenaDAO = arenaDAO;
        this.lessonDAO = lessonDAO;
    }

    public void addArena(Arena arena) throws Exception{
        arenaDAO.add(arena);
    }

    public void disableArena (int idArena) throws Exception{
        //è possibile disabilitare solo arene che non hanno prenotazioni
        Arena arena = arenaDAO.get(idArena);
        if(arena==null) {throw new Exception("L'arena selezionata non esiste.");}

        if(arena.isAvailable()) { throw new Exception("L'arena selezionata risulta già disabilitata.");}
        if(isBooked(idArena)) { throw new Exception("L'arena selezionata non può essere disabilitata in quanto ci sono lezioni prenotate.");}
        arenaDAO.disable(idArena);
    }


    public void enableArena(int idArena){

    }


    public boolean isBooked(int idArena) throws Exception{
        return lessonDAO.isArenaBookedForLesson(idArena);
    }

    //TODO fai metodo che restituisce tutte le arene disponibili

}
