package repositories;

import exceptions.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Repository<T, Id> {
    public T findById(Id id) throws SQLException, NotFoundException;

    public void save(T t) throws SQLException;
    public T toDomainModel(ResultSet rs) throws SQLException;
}
