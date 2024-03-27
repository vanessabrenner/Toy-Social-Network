package ro.ubbcluj.map.seminar7.repository;

import ro.ubbcluj.map.seminar7.domain.Utilizator;
import ro.ubbcluj.map.seminar7.repository.paging.*;
import ro.ubbcluj.map.seminar7.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class UserDBPagingRepository extends UserDBRepository implements PagingRepository<Long, Utilizator> {
    public UserDBPagingRepository(String url, String username, String password, Validator<Utilizator> val) {
        super(url, username, password, val);
        //this.loadFromDB(new PageableImplementation(1,5));
    }

    @Override
    public void loadFromDB(Pageable pageable) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users limit ? offset ?");

        ) {
            statement.setInt(1,pageable.getPageSize());
            statement.setInt(2,pageable.getPageSize() * (pageable.getPageNumber()-1));
            ResultSet resultSet = statement.executeQuery();
            Set<Utilizator> users = new HashSet<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("prenume");
                String lastName = resultSet.getString("nume");

                Utilizator user = new Utilizator(firstName,lastName);
                user.setId(id);
                users.add(user);
            }
            super.users.clear();
            users.forEach(x ->{
                this.users.put(x.getId(), x);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Utilizator> findAll(Pageable pageable) {
        this.loadFromDB(pageable);
        return super.users.values();
    }
}
