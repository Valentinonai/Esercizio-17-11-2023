package Esercizio17112023.Esercizio17112023.entities;

import jakarta.persistence.*;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "eventi")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titolo;
    private String descrizione;
    private LocalDate data_evento;
    private String luogo;
    private int posti_disponibili;
    private String url_image;
    @ManyToMany
    @JoinTable(name = "user_evento",joinColumns = @JoinColumn(name = "evento_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;
}
