package com.safa.safaeventosbd.repositorios;

import com.safa.safaeventosbd.enumerados.CategoriaEvento;
import com.safa.safaeventosbd.modelos.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByOrganizadorId(Long idOrganizador);
    List<Evento> findByCategoria(CategoriaEvento categoria);
}
