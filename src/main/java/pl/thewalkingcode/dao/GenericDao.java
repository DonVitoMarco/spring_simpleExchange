package pl.thewalkingcode.dao;


public interface GenericDao<T, PK> {

    void create(T t);

    void update(T t);

    T read(PK pk);

}
