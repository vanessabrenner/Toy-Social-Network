package ro.ubbcluj.map.seminar7.repository;

import ro.ubbcluj.map.seminar7.domain.Entity;
import ro.ubbcluj.map.seminar7.validators.ValidationException;
import ro.ubbcluj.map.seminar7.validators.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements OptionalRepository<ID,E> {
    private final Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    @Override
    public Optional<E> findOne(ID id) {
        if(id == null)
            throw new ValidationException("ID-ul nu trebuie sa fie null");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public void loadFromDB() {

    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) throws ValidationException {
        if (entity==null)
            throw new ValidationException("Entitatea nu trebuie sa fie null");
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<E> delete(ID id) {
        if(id == null)
            throw new ValidationException("ID-ul nu trebuie sa fie null");
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity) throws ValidationException{
        validator.validate(entity);
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
            return Optional.empty();
        }
        return Optional.of(entity);

    }
//    @Override
//    public E findOne(ID id) throws ValidationException{
//        if (id==null)
//            throw new ValidationException("id must be not null");
//        return entities.get(id);
//    }

//    @Override
//    public E save(E entity) throws ValidationException {
//        if (entity==null)
//            throw new ValidationException("entity must be not null");
//        validator.validate(entity);
//        if(entities.get(entity.getId()) != null) {
//            return entity;
//        }
//        else entities.put(entity.getId(),entity);
//        return null;
//    }

//    @Override
//    public E delete(ID id) {
//            E entity = this.findOne(id);
//            entities.remove(id);
//            return entity;
//    }

//    @Override
//    public E update(E entity) throws ValidationException{
//
//        if(entity == null)
//            throw new ValidationException("entity must be not null!");
//        validator.validate(entity);
//
//        entities.put(entity.getId(),entity);
//
//        if(entities.get(entity.getId()) != null) {
//            entities.put(entity.getId(),entity);
//            return null;
//        }
//        return entity;
//
//    }
}
