package Esercizio17112023.Esercizio17112023.controller;

import Esercizio17112023.Esercizio17112023.entities.Evento;
import Esercizio17112023.Esercizio17112023.entities.User;
import Esercizio17112023.Esercizio17112023.exceptions.BadRequest;
import Esercizio17112023.Esercizio17112023.payload.EventoModificaPayload;
import Esercizio17112023.Esercizio17112023.payload.EventoPayload;
import Esercizio17112023.Esercizio17112023.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<Evento> getAllEventi(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "5")int size, @RequestParam(defaultValue = "id") String order){
        return eventoService.getAllEventi(page,size>20?5:size,order);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Evento getSingleEvento(@PathVariable int id){
        return eventoService.getSingleEvento(id);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento creaEvento(@RequestBody @Validated EventoPayload eventoPayload, BindingResult validation){

        if(validation.hasErrors()){
            throw new BadRequest(validation.getAllErrors());
        }
        return eventoService.createEvento(eventoPayload);

    }

    @PutMapping("/modifica/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Evento modificaEvento(@RequestBody @Validated EventoModificaPayload body, BindingResult validation, @PathVariable int id){
        if(validation.hasErrors())  throw new BadRequest(validation.getAllErrors());

        return eventoService.modificaEvento(body,id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSingleEvento(@PathVariable int id){
   eventoService.deleteEvento(id);
    }


    @PostMapping("/upload_img/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Evento upload_img(@PathVariable int id, @RequestParam("immagine_evento")MultipartFile file) throws IOException {
        return eventoService.upload_img(file,id);
    }

    @GetMapping("/me")
    public List<Evento> getAllEventsPerUser(@AuthenticationPrincipal User u){
        return eventoService.findByUserId(u);
    }

    @DeleteMapping("delete_prenotazione/me/{id}")
    public Evento deletePrenotazione(@AuthenticationPrincipal User u, @PathVariable int id){
        return eventoService.deletePrenotazione(u,id);
    }
}
