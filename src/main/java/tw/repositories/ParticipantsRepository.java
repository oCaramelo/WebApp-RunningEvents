package tw.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tw.models.CompositeKeyParticipants;
import tw.models.Participants;

import java.util.ArrayList;

public interface ParticipantsRepository extends CrudRepository<Participants, CompositeKeyParticipants> {
    @Query(value = "select * from participants where idevent=?1 order by posgeral = 0 nulls last, posgeral", nativeQuery = true)
    ArrayList<Participants> findByIdEventOrderByPosGeral(int idEvent);

    ArrayList<Participants> findByIdEvent(int idEvent);

    ArrayList<Participants> findByIdEventAndLastSeenOrderByMilliseconds(int idEvent, String lastSeen);

    ArrayList<Participants> findByIdEventAndGeneroAndLastSeenOrderByMilliseconds(int idEvent, String gender, String lastSeen);

    ArrayList<Participants> findByIdEventAndNomeAndLastSeenOrderByMilliseconds (int idEvent, String nome, String lastSeen);

    ArrayList<Participants> findByIdEventAndNomeAndGeneroAndLastSeenOrderByMilliseconds (int idEvent, String nome, String genero, String lastSeen);

    ArrayList<Participants> findByIdEventAndEscalaoAndLastSeenOrderByMilliseconds (int idEvent, String escalao, String lastSeen);

    ArrayList<Participants> findByIdEventAndEscalaoAndGeneroAndLastSeenOrderByMilliseconds (int idEvent, String escalao, String genero, String lastSeen);

    ArrayList<Participants> findByIdEventAndNomeOrderByMilliseconds(int idEvent, String nome);

    ArrayList<Participants> findByIdEventOrderByDorsal(int idEvent);

    ArrayList<Participants> findByUsernameOrderByReferencia(String username);

    ArrayList<Participants> findByIdEventAndGeneroOrderByMilliseconds(int idEvent, String gender);

    ArrayList<Participants> findByIdEventAndGenero(int idEvent, String gender);

    Participants findByIdEventAndDorsal(int idEvent, int dorsal);

    int countByIdEvent(int idEvent);

    ArrayList<Participants> findByUsername(String username);

    Participants findByReferencia(String referencia);
}
