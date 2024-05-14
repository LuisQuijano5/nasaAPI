package comprehensive.project.nasaapi.database.DAO;

import java.util.List;
import java.util.Optional;

public interface Dao<T>
{
    Optional<T> findById(int id, int br);
    List<T> findAll(int br);
}
