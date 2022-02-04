package tw.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tw.models.Participants;
import tw.repositories.EventsRepository;
import tw.repositories.ParticipantsRepository;
import tw.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class ParticipantsService {
    @Autowired
    ParticipantsRepository participantsRepository;

    @Autowired
    EventsRepository eventsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Async
    public CompletableFuture<ArrayList<Participants>> findAllParticipants(int idEvent) {
        ArrayList<Participants> participants = participantsRepository.findByIdEvent(idEvent);
        return CompletableFuture.completedFuture(participants);
    }

    public synchronized void updateGeneralClassification(int idEvent) {
        ArrayList<Participants> sortedList =
                participantsRepository.findByIdEventAndLastSeenOrderByMilliseconds(idEvent, "finish");
        int pos =1;
        for (Participants participant : sortedList) {
            participant.setPosGeral(pos);
            participantsRepository.save(participant);
            pos++;
        }
    }

    public synchronized void updateInterClassification(int idEvent, String gender) {
        ArrayList<Participants> sortedList =
                participantsRepository.findByIdEventAndGeneroAndLastSeenOrderByMilliseconds(idEvent, gender,"finish");
        int pos = 1;
        for (Participants participant : sortedList) {
            participant.setPosInter(pos);
            participantsRepository.save(participant);
            pos++;
        }
    }

}
