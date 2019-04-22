package repositories;

public interface Repository<T, Id> {
    public T findById(Id id);

    public void save(T t);
}
