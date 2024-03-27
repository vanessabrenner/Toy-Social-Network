package ro.ubbcluj.map.seminar7.repository.paging;



import ro.ubbcluj.map.seminar7.domain.Entity;
import ro.ubbcluj.map.seminar7.repository.OptionalRepository;


public interface PagingRepository<ID,
        E extends Entity<ID>>
        extends OptionalRepository<ID, E> {

    void loadFromDB(Pageable pageable);
    Iterable<E> findAll(Pageable pageable);
}
