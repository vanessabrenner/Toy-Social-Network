package ro.ubbcluj.map.seminar7.domain;

public class Account extends Entity<String>{
    private Long id;
    private String password;

    public Account(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getIdUser() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
