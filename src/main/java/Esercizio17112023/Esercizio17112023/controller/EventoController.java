package Esercizio17112023.Esercizio17112023.controller;

import Esercizio17112023.Esercizio17112023.entities.Evento;
import Esercizio17112023.Esercizio17112023.exceptions.BadRequest;
import Esercizio17112023.Esercizio17112023.payload.EventoModificaPayload;
import Esercizio17112023.Esercizio17112023.payload.EventoPayload;
import Esercizio17112023.Esercizio17112023.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventi")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN','USER')")
    public Page<Evento> getAllUsers(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "5")int size, @RequestParam(defaultValue = "id") String order){
        return eventoService.getAllEventi(page,size>20?5:size,order);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN','USER')")
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
    public void deleteSingleUser(@PathVariable int id){
   eventoService.deleteEvento(id);
    }


}
