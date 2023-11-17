package Esercizio17112023.Esercizio17112023.repository;

import Esercizio17112023.Esercizio17112023.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Integer> {
    Optional<List<Evento>> findByUsersId(int id);
}
