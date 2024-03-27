package ro.ubbcluj.map.seminar7.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}