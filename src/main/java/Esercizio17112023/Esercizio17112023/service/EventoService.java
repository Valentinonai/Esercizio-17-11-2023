package Esercizio17112023.Esercizio17112023.service;

import Esercizio17112023.Esercizio17112023.entities.Evento;
import Esercizio17112023.Esercizio17112023.entities.User;
import Esercizio17112023.Esercizio17112023.exceptions.EventoCompletoException;
import Esercizio17112023.Esercizio17112023.exceptions.NotFound;
import Esercizio17112023.Esercizio17112023.payload.EventoModificaPayload;
import Esercizio17112023.Esercizio17112023.payload.EventoPayload;
import Esercizio17112023.Esercizio17112023.payload.PrenotaEventoPayload;
import Esercizio17112023.Esercizio17112023.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private UserService userService;
    public Page<Evento> getAllEventi(int page, int size, String order){
        Pageable p= PageRequest.of(page,size, Sort.by(order));
        return eventoRepository.findAll(p);
    }

    public Evento getSingleEvento(int id){
     Evento e=eventoRepository.findById(id).orElseThrow(()->new NotFound("elemento non trovato"));
     return e;
    }

    public Evento createEvento(EventoPayload eventoPayload){
        Evento e =Evento.builder().titolo(eventoPayload.titolo()).descrizione(eventoPayload.descrizione()).data_evento(eventoPayload.data()).luogo(eventoPayload.luogo()).posti_disponibili(eventoPayload.posti_disponibili()).url_image("https://picsum.photos/200/300").build();
        eventoRepository.save(e);
        return e;
    }


    public void deleteEvento(int id){
        Evento e = getSingleEvento(id);
        if(!e.getUrl_image().equals("https://picsum.photos/200/300")){
            cloudinaryService.deleteImageByUrl(e.getUrl_image());
        }
        eventoRepository.delete(e);
    }


    public Evento modificaEvento(EventoModificaPayload body, int id) {
        Evento e =getSingleEvento(id);
        e.setTitolo(body.titolo()==null?e.getTitolo():body.titolo());
        e.setData_evento(body.data()==null?e.getData_evento():body.data());
        e.setLuogo(body.luogo()==null?e.getLuogo():body.luogo());
        e.setDescrizione(body.descrizione()==null?e.getDescrizione():body.descrizione());
        e.setPosti_disponibili(body.posti_disponibili()==0?e.getPosti_disponibili(): body.posti_disponibili());
        eventoRepository.save(e);
        return e;

    }

    public Evento prenotaEvento(User u, PrenotaEventoPayload prenotaEventoPayload) throws Exception {
        Evento e=getSingleEvento(prenotaEventoPayload.id());
        if(e.getPosti_disponibili()>0&& e.getData_evento().isAfter(LocalDate.now())){
            e.setUsers(u);
            e.setPosti_disponibili(e.getPosti_disponibili()-1);
            eventoRepository.save(e);
            return e;
        }
        else throw new EventoCompletoException("Evento al completo o scaduto");


    }
}
