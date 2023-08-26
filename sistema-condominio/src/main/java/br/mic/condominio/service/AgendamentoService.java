package br.mic.condominio.service;

import br.mic.condominio.model.Agendamento;
import br.mic.condominio.model.Horario;
import br.mic.condominio.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService
{
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public boolean horarioJaAgendado(Horario horario) {
        List<Agendamento> agendamentos = agendamentoRepository.findByHorarioId(horario.getId());
        return !agendamentos.isEmpty();
    }
}
