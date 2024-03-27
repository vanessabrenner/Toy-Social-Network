package ro.ubbcluj.map.seminar7.validators;

import ro.ubbcluj.map.seminar7.domain.Account;

public class AccountValidator implements Validator<Account> {
    @Override
    public void validate(Account entity) throws ValidationException {
        String exceptions = new String();
        if(entity.getId().equals(""))
            exceptions = exceptions + "Username incorect!\n";
        if(entity.getPassword().equals(""))
            exceptions = exceptions + "Parola incorecta!\n";
        if(exceptions.length() != 0)
            throw new ValidationException(exceptions);
    }
}
