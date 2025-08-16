package repository;

import java.util.List;

public interface BaseRepository<T> {
    void create(T t);
    List<T> findAll();
    T findById();
    void update(T t);
    void delete(T t);
}
