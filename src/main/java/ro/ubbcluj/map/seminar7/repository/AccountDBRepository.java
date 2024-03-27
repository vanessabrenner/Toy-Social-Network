package ro.ubbcluj.map.seminar7.repository;

import ro.ubbcluj.map.seminar7.domain.Account;
import ro.ubbcluj.map.seminar7.utils.Criptare;
import ro.ubbcluj.map.seminar7.validators.AccountValidator;
import ro.ubbcluj.map.seminar7.validators.ValidationException;
import ro.ubbcluj.map.seminar7.validators.Validator;

import java.sql.*;
import java.util.*;

public class AccountDBRepository implements OptionalRepository<String, Account> {
    protected String url;
    protected String username;
    protected String password;
    private Validator<Account> validator;
    protected Map<String, Account> accounts;

    Criptare criptare;

    public AccountDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = new AccountValidator();
        accounts = new HashMap<>();
        criptare = new Criptare();
        this.loadFromDB();
    }

    @Override
    public Optional<Account> findOne(String username) {
        if(username == null)
            throw new ValidationException("Username-ul nu trebuie sa fie null");
        return Optional.ofNullable(accounts.get(username));
    }

    @Override
    public Iterable<Account> findAll() {
        return this.accounts.values();
    }

    @Override
    public Optional<Account> save(Account entity) throws ValidationException, RuntimeException{
        if(entity == null)
            throw new ValidationException("Contul nu trebuie sa fie null");
        validator.validate(entity);
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts(id, username, password) VALUES(?,?,?)");
        )
        {
            statement.setLong(1, entity.getIdUser());
            statement.setString(2, entity.getId());
            statement.setString(3, this.criptare.criptareParola(entity.getPassword()));
            statement.executeUpdate();
            this.loadFromDB();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<Account> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> update(Account entity) {
        return Optional.empty();
    }

    @Override
    public void loadFromDB() {
        Set<Account> accounts = new HashSet<>();

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from accounts");
            ResultSet set = statement.executeQuery();
        )
        {
            while(set.next()){
                Long id = set.getLong("id");
                String username = set.getString("username");
                String password = this.criptare.decriptareParola(set.getString("password"));
                Account account = new Account(id, password);
                account.setId(username);
                accounts.add(account);
            }
            this.accounts.clear();
            accounts.forEach(x ->{
                this.accounts.put(x.getId(), x);
            });
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
