package ro.ubbcluj.map.seminar7.service;

import ro.ubbcluj.map.seminar7.domain.Account;
import ro.ubbcluj.map.seminar7.domain.Utilizator;
import ro.ubbcluj.map.seminar7.repository.OptionalRepository;
import ro.ubbcluj.map.seminar7.repository.Repository;

public class ServiceAccount {
    private OptionalRepository repo_accounts;

    public ServiceAccount(OptionalRepository repo_accounts) {
        this.repo_accounts = repo_accounts;
    }
    public boolean exists(String username){
        if(findAccount(username) == null)
            return false;
        return true;
    }
    public Account findAccount(String username){
        return repo_accounts.findOne(username).isEmpty()? null : (Account) repo_accounts.findOne(username).get();
    }
    public Account addAccount(String username, String password, Long id){
        Account account = new Account(id, password);
        account.setId(username);
        return repo_accounts.save(account).isEmpty()? null : account;
    }
}
