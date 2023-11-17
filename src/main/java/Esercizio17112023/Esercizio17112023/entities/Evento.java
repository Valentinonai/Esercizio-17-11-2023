package Esercizio17112023.Esercizio17112023.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setData_evento(LocalDate data_evento) {
        this.data_evento = data_evento;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setPosti_disponibili(int posti_disponibili) {
        this.posti_disponibili = posti_disponibili;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public void setUsers(User user) {
        this.users.add(user);
    }
}
