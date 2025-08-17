package repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {
    T create(T t);
    List<T> findAll();
    Optional<T> findById(ID id);
    void update(T t);
    void delete(T t);
}
