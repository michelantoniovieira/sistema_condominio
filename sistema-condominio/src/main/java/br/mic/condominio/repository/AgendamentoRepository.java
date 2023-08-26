package br.mic.condominio.repository;

import java.time.LocalDate;
import java.util.List;

import br.mic.condominio.model.Horario;
import br.mic.condominio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.mic.condominio.model.Agendamento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends CrudRepository<Agendamento, Integer>
{

    List<Agendamento> findByHorarioId(Integer id);

    List<Agendamento> findByData(String data);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Agendamento a WHERE a.usuario = ?1 AND a.data = ?2")
    boolean existsByUsuarioAndData(Usuario usuario, String data);

    boolean existsByHorarioAndData(Horario horario, String data);

    List<Agendamento> findByUsuarioId(int idUsuario);
}
