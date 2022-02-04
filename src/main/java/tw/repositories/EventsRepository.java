package tw.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tw.models.Events;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface EventsRepository extends CrudRepository<Events,Integer> {

    @Query(value = "select * from events where date(data)= ?1", nativeQuery = true)
    ArrayList<Events> findByData(Timestamp date);

    @Query(value = "select * from events where date(data)= ?1", nativeQuery = true)
    Page<Events> findByData(Timestamp date,Pageable pageable);

    @Query(value = "select * from events where date(data)> ?1", nativeQuery = true)
    ArrayList<Events> findByDataAfter(Timestamp date);

    @Query(value = "select * from events where date(data)> ?1", nativeQuery = true)
    Page<Events> findByDataAfter(Timestamp date,Pageable pageable);

    @Query(value = "select * from events where date(data)< ?1", nativeQuery = true)
    ArrayList<Events> findByDataBefore(Timestamp date);

    @Query(value = "select * from events where date(data)< ?1", nativeQuery = true)
    Page<Events> findByDataBefore(Timestamp date,Pageable pageable);

    @Query(value = "select * from events where date(data)>= ?1", nativeQuery = true)
    Page<Events> findByDataNow(Timestamp date,Pageable pageable);

    @Query(value = "select * from events where date(data)>= ?1", nativeQuery = true)
    ArrayList<Events> findByDataNow(Timestamp date);

    ArrayList<Events> findByNome(String nome);

    Page<Events> findByNome(String nome,Pageable pageable);

    ArrayList<Events> findAll();
    Page<Events> findAll(Pageable pageable);
    Events findById(int id);




}