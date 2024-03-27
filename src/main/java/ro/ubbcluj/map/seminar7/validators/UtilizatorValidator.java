package ro.ubbcluj.map.seminar7.validators;

import ro.ubbcluj.map.seminar7.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String exceptions = new String();
        if(entity.getLastName().equals("") || entity.getLastName().matches(".*[0-9].*") || !(Character.isUpperCase(entity.getLastName().charAt(0))))
            exceptions = exceptions + "Lastname incorrect!\n";
        if(entity.getFirstName().equals("") || entity.getFirstName().matches(".*[0-9].*") || !(Character.isUpperCase(entity.getFirstName().charAt(0))))
            exceptions += "Firstname incorrect!\n";
        if(exceptions.length() != 0)
            throw new ValidationException(exceptions);
    }
}

