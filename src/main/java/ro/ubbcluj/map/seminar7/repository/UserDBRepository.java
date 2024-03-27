package ro.ubbcluj.map.seminar7.repository;

import ro.ubbcluj.map.seminar7.domain.Utilizator;
import ro.ubbcluj.map.seminar7.validators.ValidationException;
import ro.ubbcluj.map.seminar7.validators.Validator;

import java.sql.*;
import java.util.*;

public class UserDBRepository implements OptionalRepository<Long, Utilizator>{
    protected String url;
    protected String username;
    protected String password;
    private Validator<Utilizator> validator;
    protected Map<Long, Utilizator> users;

    public UserDBRepository(String url, String username, String password, Validator<Utilizator> val) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = val;
        users = new HashMap<>();
        this.loadFromDB();
    }

    @Override
    public Optional<Utilizator> findOne(Long id) throws RuntimeException{
        if(id == null)
            throw new ValidationException("ID-ul nu trebuie sa fie null");
        return Optional.ofNullable(users.get(id));
    }



    @Override
    public void loadFromDB() {
        Set<Utilizator> users = new HashSet<>();

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from users");
            ResultSet set = statement.executeQuery();
        )
        {
            while(set.next()){
                Long id = set.getLong("id");
                String firstname = set.getString("prenume");
                String lastname = set.getString("nume");
                Utilizator user = new Utilizator(firstname, lastname);
                user.setId(id);
                users.add(user);
            }
            this.users.clear();
            users.forEach(x ->{
                this.users.put(x.getId(), x);
            });
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Utilizator> findAll() throws RuntimeException{
        return this.users.values();
    }

    @Override
    public Optional<Utilizator> save(Utilizator entity) throws ValidationException, RuntimeException{
        if(entity == null)
            throw new ValidationException("Utilizatorul nu trebuie sa fie null");
        validator.validate(entity);
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users(nume, prenume) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            )
        {
            //statement.setLong(1, entity.getId());
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Long lastInsertedId = generatedKeys.getLong(1); // ID-ul entității adăugate
                    entity.setId(lastInsertedId);
                }
            }
            this.loadFromDB();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<Utilizator> delete(Long uid) {
        Optional<Utilizator> user = this.findOne(uid);
        if(user.isEmpty())
            throw new ValidationException("Utilizatorul nu exista");
        if(uid == null)
            throw new ValidationException("ID-ul nu trebuie sa fie null");
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
        )
        {
            statement.setLong(1, uid);
            if(statement.executeUpdate() > 0) {
                loadFromDB();
                return user;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
    @Override
    public Optional<Utilizator> update(Utilizator entity) {
        this.validator.validate(entity);
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET nume = ?, prenume = ? WHERE id = ?");
        )
        {
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
            return Optional.ofNullable(entity);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
