package Esercizio17112023.Esercizio17112023.repository;

import Esercizio17112023.Esercizio17112023.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Integer> {
}
