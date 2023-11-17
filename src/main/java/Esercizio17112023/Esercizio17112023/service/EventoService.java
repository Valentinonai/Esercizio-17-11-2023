package Esercizio17112023.Esercizio17112023.service;

import Esercizio17112023.Esercizio17112023.entities.Evento;
import Esercizio17112023.Esercizio17112023.entities.User;
import Esercizio17112023.Esercizio17112023.exceptions.NotFound;
import Esercizio17112023.Esercizio17112023.payload.EventoPayload;
import Esercizio17112023.Esercizio17112023.payload.UserPayload;
import Esercizio17112023.Esercizio17112023.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
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
        return e;
    }


    public void deleteUser(int id){
        Evento e = getSingleEvento(id);
        if(!e.getImageUrl().equals("https://picsum.photos/200/300")){
            cloudinaryService.deleteImageByUrl(u.getImageUrl());
        }
        eventoRepository.delete(e);
    }



    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new NotFound("User selezionato inesistente"));
    }
}