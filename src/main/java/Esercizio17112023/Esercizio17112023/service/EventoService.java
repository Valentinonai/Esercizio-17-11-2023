package Esercizio17112023.Esercizio17112023.service;

import Esercizio17112023.Esercizio17112023.entities.Evento;
import Esercizio17112023.Esercizio17112023.entities.User;
import Esercizio17112023.Esercizio17112023.exceptions.AlreadyBookedEventException;
import Esercizio17112023.Esercizio17112023.exceptions.BadRequest;
import Esercizio17112023.Esercizio17112023.exceptions.EventoCompletoException;
import Esercizio17112023.Esercizio17112023.exceptions.NotFound;
import Esercizio17112023.Esercizio17112023.payload.EventoModificaPayload;
import Esercizio17112023.Esercizio17112023.payload.EventoPayload;
import Esercizio17112023.Esercizio17112023.payload.PrenotaEventoPayload;
import Esercizio17112023.Esercizio17112023.repository.EventoRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private UserService userService;
    @Autowired
    private Cloudinary cloudinary;
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
        Evento app=eventoRepository.findByIdAndUsersId(e.getId(),u.getId());
        if(app!=null)   throw new AlreadyBookedEventException("Hai già prenotato questo evento");
        else
        if(e.getPosti_disponibili()>0&& e.getData_evento().isAfter(LocalDate.now())){
            e.setUsers(u);
            e.setPosti_disponibili(e.getPosti_disponibili()-1);
            eventoRepository.save(e);
            return e;
        }
        else throw new EventoCompletoException("Evento al completo o scaduto");


    }

    public Evento upload_img(MultipartFile file, int id) throws IOException {
        Evento e=getSingleEvento(id);
        if(!e.getUrl_image().equals("https://picsum.photos/200/300"))
            cloudinaryService.deleteImageByUrl(e.getUrl_image());
        String url=(String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        e.setUrl_image(url);
        eventoRepository.save(e);
        return e;
    }

    public List<Evento> findByUserId(User u){
        return eventoRepository.findByUsersId(u.getId()).orElseThrow(()->new NotFound("Nessun elemento trovato"));
    }

    public Evento deletePrenotazione(User u, int id) {
        Evento e=getSingleEvento(id);
        User u1=e.getUsers().stream().filter(elem -> elem.getId() == u.getId()).findFirst().orElseThrow(()->new NotFound("Prenotazione non presente"));
        if(e.getUsers().remove(u1)) {
            e.setPosti_disponibili(e.getPosti_disponibili()+1);
            eventoRepository.save(e);
        }
      else throw new BadRequest("La tua prenotazione non è stata rimossa");
        return e;

    }
}
