package br.mic.condominio.repository;

import br.mic.condominio.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
	@Query("SELECT h FROM Horario h ") // Add your JPQL query here
	List<Horario> findAllHorariosDisponiveis();

}

