package tw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.models.Events;
import tw.models.Participants;
import tw.models.Users;
import tw.repositories.EventsRepository;
import tw.repositories.ParticipantsRepository;
import tw.repositories.UsersRepository;
import tw.services.ParticipantsService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class SpringSecurityController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private ParticipantsRepository participantsRepository;

    @Autowired
    private ParticipantsService participantsService;

    static String getData(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String datastring = dtf.format(now);
        datastring = datastring.replace('/', '-');

        return datastring;
    }

    static Pageable getPageData(int page,int  size){
        return PageRequest.of(page, size, Sort.by("data"));
    }

    static String timeConverter(long milliseconds){

        String seconds= Integer.toString(((int)((milliseconds/1000)%60)));
        String minutes= Integer.toString((int)((milliseconds/(1000*60))%60));
        String hours= Integer.toString((int)((milliseconds/(1000*60*60))%24));

        if(seconds.length() == 1){
            seconds = "0" + seconds;
        }

        if(minutes.length() == 1){
            minutes = "0" + minutes;
        }

        if(hours.length() == 1){
            hours = "0" + hours;
        }

        return hours + ":" + minutes + ":" + seconds;

    }


    @RequestMapping ("/")
    public String mainPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false,defaultValue = "4") int size,
            Model model
    ){
        Page<Events> pagedResult = eventsRepository.findByDataNow(Timestamp.valueOf(getData()),getPageData(page,size));

        model.addAttribute("listAllEvents", eventsRepository.findAll());
        model.addAttribute("eventsList", pagedResult.getContent());
        model.addAttribute("pages", new int[pagedResult.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "homepage";
    }

    @PostMapping("/registo")
    public String register(@RequestParam String username,
                           @RequestParam String password1,
                           @RequestParam String password2,
                           @RequestParam String email1,
                           @RequestParam String email2,
                           Model model) {

        if (email1.equals(email2) && password1.equals(password2)) {
            String encodedPassword= new BCryptPasswordEncoder().encode(password1);
            Users u = new Users(username, encodedPassword, email1, "ROLE_atleta");
            usersRepository.save(u);

            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/registarEvento")
    public String addEvent(@RequestParam String nome,
                           @RequestParam String local,
                           @RequestParam String data,
                           @RequestParam String hora,
                           @RequestParam String descricao,
                           @RequestParam float valor,
                           Model model) {

        String str = data + " " + hora;
        Timestamp timestamp= Timestamp.valueOf(str);

        int id = (int) (eventsRepository.count() + 1);

        Events e = new Events(nome,descricao,local,timestamp,valor);
        e.setId(id);
        eventsRepository.save(e);

        return "redirect:/admin";
    }

    @GetMapping ("/eventos/pesquisa")
    public String searchByNameOrData( @RequestParam String valor,
                                      @RequestParam String tipo,
                                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(value = "size", required = false,defaultValue = "4") int size,
                                      Model model
    ){

        if(Objects.equals(tipo, "data")){

            valor += " 00:00:00";
            Timestamp timestamp= Timestamp.valueOf(valor);
            Page<Events> pagedResult = eventsRepository.findByData(timestamp,getPageData(page,size));

            model.addAttribute("listAllEvents", eventsRepository.findAll());
            model.addAttribute("eventsList", pagedResult.getContent());
            model.addAttribute("pages", new int[pagedResult.getTotalPages()]);
            model.addAttribute("currentPage", page);

        }else{
            Page<Events> pagedResult = eventsRepository.findByNome(valor,getPageData(page,size));
            model.addAttribute("listAllEvents", eventsRepository.findAll());
            model.addAttribute("eventsList", pagedResult.getContent());
            model.addAttribute("pages", new int[pagedResult.getTotalPages()]);
            model.addAttribute("currentPage", page);
        }
        return "homepage";
    }

    @GetMapping ("/eventos/mostrarEventosPassados")
    public String showPastEvents(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false,defaultValue = "4") int size,
            Model model
    ){
        Page<Events> pagedResult = eventsRepository.findByDataBefore(Timestamp.valueOf(getData()),getPageData(page,size));

        model.addAttribute("listAllEvents", eventsRepository.findAll());
        model.addAttribute("eventsList", pagedResult.getContent());
        model.addAttribute("pages", new int[pagedResult.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "homepage";
    }


    @GetMapping ("/eventos/mostrarEventosFuturos")
    public String showFutureEvents(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false,defaultValue = "4") int size,
            Model model
    ){

        Page<Events> pagedResult = eventsRepository.findByDataAfter(Timestamp.valueOf(getData()),getPageData(page,size));
        model.addAttribute("listAllEvents", eventsRepository.findAll());
        model.addAttribute("eventsList", pagedResult.getContent());
        model.addAttribute("pages", new int[pagedResult.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "homepage";
    }


    @GetMapping ("/eventos/mostrarEventosAtuais")
    public String showPresentEvents(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false,defaultValue = "4") int size,
            Model model
    ){

        Page<Events> pagedResult = eventsRepository.findByData(Timestamp.valueOf(getData()),getPageData(page,size));
        model.addAttribute("listAllEvents", eventsRepository.findAll());
        model.addAttribute("eventsList", pagedResult.getContent());
        model.addAttribute("pages", new int[pagedResult.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "homepage";
    }

    @GetMapping("/addParticipant")
    public String addEventsToList(Model model){
        ArrayList<Events> listEvents = eventsRepository.findByDataNow(Timestamp.valueOf(getData()));

        model.addAttribute("listEvents", listEvents);

        return "addParticipant";
    }

    @PostMapping ("/adicionarParticipante")
    public String addParticipant(@RequestParam int idEvent,
                           @RequestParam String nome,
                           @RequestParam String genero,
                           @RequestParam String escalao,
                           @RequestParam String entidade,
                           @RequestParam String referencia,
                           @RequestParam float valor,
                           Principal principal,
                           Model model) {

        int dorsal = participantsRepository.countByIdEvent(idEvent) + 1;
        String nomeEvento = eventsRepository.findById(idEvent).getNome();

        Participants p = new Participants(idEvent, principal.getName(), nome,genero,escalao);
        p.setDorsal(dorsal);
        p.setEntidade(entidade);
        p.setReferencia(referencia);
        p.setValor(valor);
        p.setNameEvent(nomeEvento);
        participantsRepository.save(p);

        model.addAttribute("confirmMessage", "Inscrito com sucesso");

        return "redirect:/user";

    }

    @GetMapping ("/eventList")
    public String eventlistsearch(Principal principal,
                                  Model model){

        String username = principal.getName();
        ArrayList<Participants> listParticipants = participantsRepository.findByUsernameOrderByReferencia(username);

        model.addAttribute("EventsSigned", listParticipants);
        return "eventList";

    }

    @PostMapping ("/pagarReferencia")
    public String searchForReference(@RequestParam String reference) {

        Participants participant = participantsRepository.findByReferencia(reference);
        participant.setPago("Sim");
        participantsRepository.save(participant);

        return "redirect:/eventList";

    }

    @GetMapping("/addTime")
    public String addTimePageLoad(Model model){

        ArrayList<Events> events = eventsRepository.findAll();

        model.addAttribute("listEvents", events);

        return "addTime";
    }

    @GetMapping("/registarTempo")
    public String addTime(@RequestParam int idEvent,
                           @RequestParam String pontoEvent,
                           @RequestParam int dorsalEvent,
                           @RequestParam String dataEvent,
                           @RequestParam String horaEvent,
                           Model model) {

        String str = dataEvent + " " + horaEvent;
        Timestamp timestamp= Timestamp.valueOf(str);

        Participants participant = participantsRepository.findByIdEventAndDorsal(idEvent,dorsalEvent);
        long milliseconds = timestamp.getTime() - eventsRepository.findById(participant.getIdEvent()).getData().getTime();
        String tempo = timeConverter(milliseconds);

        participant.setTempo(tempo);
        participant.setMilliseconds(milliseconds);
        participant.setLastSeen(pontoEvent);
        participantsRepository.save(participant);
        participantsService.updateGeneralClassification(idEvent);
        participantsService.updateInterClassification(idEvent,participantsRepository.findByIdEventAndDorsal(idEvent,dorsalEvent).getGenero());

        return "redirect:/admin";
    }

    @PostMapping("/verParticipantes")
    public String showClassification (@RequestParam int idEvent,
                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                   @RequestParam(value = "size", required = false,defaultValue = "4") int size,
                                   Model model) {

        Page<Events> pagedResult = eventsRepository.findByDataNow(Timestamp.valueOf(getData()),getPageData(page,size));
        ArrayList<Participants> participants = participantsRepository.findByIdEventOrderByDorsal(idEvent);

        model.addAttribute("listParticipants", participants);
        model.addAttribute("listAllEvents", eventsRepository.findAll());
        model.addAttribute("eventsList", pagedResult.getContent());
        model.addAttribute("pages", new int[pagedResult.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "homepage";
    }

    @PostMapping("/verClassificacao")
    public String showParticipants(@RequestParam int idEvent,
                                   @RequestParam String classType,
                                   @RequestParam String pesquisa,
                                   @RequestParam String filter,
                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                   @RequestParam(value = "size", required = false,defaultValue = "4") int size,
                                   Model model) {


        Page<Events> pagedResult = eventsRepository.findByDataNow(Timestamp.valueOf(getData()),getPageData(page,size));
        ArrayList<Participants> classificationgeral = new ArrayList<>();
        ArrayList<Participants> classification = new ArrayList<>();
        String[] positions = {"finish", "P3", "P2", "P1", "start", "-----"};

        if(pesquisa.isEmpty() || filter.equals("semfiltro")){

            switch (classType){

                case "n":

                    for (String b: positions) {
                        classificationgeral.addAll(participantsRepository.findByIdEventAndLastSeenOrderByMilliseconds(idEvent,b));
                    }
                    model.addAttribute("listClassificationGeral", classificationgeral);
                    break;

                case "f":

                case "m":

                    for (String b: positions) {
                        classification.addAll(participantsRepository.findByIdEventAndGeneroAndLastSeenOrderByMilliseconds(idEvent,classType,b));
                    }
                    model.addAttribute("listClassification", classification);
                    break;
            }
        }else if(filter.equals("nome")){
            switch (classType){

                case "n":

                    for (String b: positions) {
                        classificationgeral.addAll(participantsRepository.findByIdEventAndNomeAndLastSeenOrderByMilliseconds(idEvent,pesquisa,b));
                    }
                    model.addAttribute("listClassificationGeral", classificationgeral);
                    break;

                case "f":

                case "m":

                    for (String b: positions) {
                        classification.addAll(participantsRepository.findByIdEventAndNomeAndGeneroAndLastSeenOrderByMilliseconds(idEvent,pesquisa,classType,b));
                    }
                    model.addAttribute("listClassification", classification);
                    break;
            }
        }else if(filter.equals("escalao")){
            switch (classType){

                case "n":

                    for (String b: positions) {
                        classificationgeral.addAll(participantsRepository.findByIdEventAndEscalaoAndLastSeenOrderByMilliseconds(idEvent,pesquisa,b));
                    }
                    model.addAttribute("listClassificationGeral", classificationgeral);
                    break;

                case "f":

                case "m":

                    for (String b: positions) {
                        classification.addAll(participantsRepository.findByIdEventAndEscalaoAndGeneroAndLastSeenOrderByMilliseconds(idEvent,pesquisa,classType,b));
                    }
                    model.addAttribute("listClassification", classification);
                    break;
            }
        }

        model.addAttribute("listAllEvents", eventsRepository.findAll());
        model.addAttribute("eventsList", pagedResult.getContent());
        model.addAttribute("pages", new int[pagedResult.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "homepage";
    }

    @PostMapping("/verParticipante")
    public String showParticipant(@RequestParam int idEvent,
                                   @RequestParam String pesquisa,
                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                   @RequestParam(value = "size", required = false,defaultValue = "4") int size,
                                   Model model) {

        Page<Events> pagedResult = eventsRepository.findByDataNow(Timestamp.valueOf(getData()),getPageData(page,size));
        ArrayList<Participants> participantList = participantsRepository.findByIdEventAndNomeOrderByMilliseconds(idEvent,pesquisa);

        model.addAttribute("listParticipant", participantList);
        model.addAttribute("listAllEvents", eventsRepository.findAll());
        model.addAttribute("eventsList", pagedResult.getContent());
        model.addAttribute("pages", new int[pagedResult.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "homepage";
    }
}
