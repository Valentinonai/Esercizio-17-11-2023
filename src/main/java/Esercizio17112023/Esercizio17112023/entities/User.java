package Esercizio17112023.Esercizio17112023.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"password", "enabled", "accountNonExpired", "accountNonLocked", "authorities", "credentialsNonExpired","username"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Ruolo role;
    @ManyToMany
    @JoinTable(name = "user_evento",joinColumns =@JoinColumn(name = "user_id") ,inverseJoinColumns =@JoinColumn(name = "evento_id") )
    @JsonIgnore
    private Set<Evento> eventi;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Ruolo role) {
        this.role = role;
    }

    public void setEventi(Evento evento) {
        this.eventi.add(evento);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
