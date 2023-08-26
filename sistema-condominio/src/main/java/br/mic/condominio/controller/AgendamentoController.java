package br.mic.condominio.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.mic.condominio.model.Horario;
import br.mic.condominio.model.Usuario;
import br.mic.condominio.repository.UsuarioRepository;
import br.mic.condominio.service.AgendamentoService;
import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.mic.condominio.model.Agendamento;
import br.mic.condominio.repository.AgendamentoRepository;
import br.mic.condominio.repository.HorarioRepository;

@Controller
public class AgendamentoController
{
	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AgendamentoService agendamentoService;

	@Autowired
	private HttpSession session;


	@PostMapping("/agendar")
	public String agendar(@RequestParam("data") String data,
						  @RequestParam("id_horario") int idHorario,
						  HttpSession session,
						  Model model) {

		int idUsuario = (int) session.getAttribute("idUsuario");

		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		Horario horario = horarioRepository.findById(idHorario).orElse(null);

		boolean exists = agendamentoRepository.existsByUsuarioAndData(usuario, data);
		boolean horarioAlreadyBooked = agendamentoRepository.existsByHorarioAndData(horario, data);

		if (!exists && !horarioAlreadyBooked) {
			Agendamento a = new Agendamento(data, usuario, horario);
			agendamentoRepository.save(a);

			// Atualizar a lista de horários disponíveis para remover o horário agendado
			List<Horario> horariosDisponiveis = horarioRepository.findAll();
			horariosDisponiveis.removeIf(h -> h.getId() == idHorario);
			model.addAttribute("horariosDisponiveis", horariosDisponiveis);
		} else {
			// Lógica para lidar com a situação em que o usuário já agendou na mesma data
			// ou o horário já está agendado por outro usuário na mesma data
		}

		return "redirect:/index";
	}



	public boolean horarioJaAgendado(Integer horarioId) {
		List<Agendamento> agendamentos = agendamentoRepository.findByHorarioId(horarioId); // Substitua "findByHorarioId" pelo método correto para buscar agendamentos por horário

		// Verifique se há agendamentos para o horário
		return !agendamentos.isEmpty();
	}

	@GetMapping("/index")
	public String listar(Model model, HttpSession session) {
		int idUsuario = (int) session.getAttribute("idUsuario");

		List<Agendamento> agendamentosList = agendamentoRepository.findByUsuarioId(idUsuario);

		LocalDate dataSelecionada = LocalDate.now();  // Altere isso para a data desejada

		List<Horario> horariosDisponiveis = horarioRepository.findAll();
		List<Horario> horariosNaoAgendados = new ArrayList<>();

		for (Horario horario : horariosDisponiveis) {
			boolean agendado = false;

			for (Agendamento agendamento : agendamentosList) {
				LocalDate dataAgendamento = LocalDate.parse(agendamento.getDataFormatada(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

				if (dataAgendamento.equals(dataSelecionada) && horario.getId() == agendamento.getHorario().getId()) {
					agendado = true;
					break;
				}
			}

			if (!agendado) {
				horariosNaoAgendados.add(horario);
			}
		}

		model.addAttribute("horariosDisponiveis", horariosNaoAgendados);
		model.addAttribute("agendamentos", agendamentosList);

		return "index";


	}




	@GetMapping("/listarAgendamentosPorData")
	@ResponseBody
	public String listarAgendamentosPorData(@RequestParam(name = "data") String dataSelecionada) {
		List<Agendamento> agendamentos = agendamentoRepository.findByData(dataSelecionada);
		List<Horario> horariosDisponiveis = horarioRepository.findAll();

		// Filtra os horários disponíveis para excluir aqueles que já foram agendados
		List<Horario> horariosNaoAgendados = horariosDisponiveis.stream()
				.filter(horario -> agendamentos.stream()
						.noneMatch(agendamento -> agendamento.getHorario().getId() == horario.getId()))
				.collect(Collectors.toList());

		StringBuilder result = new StringBuilder();

		for (Horario horario : horariosNaoAgendados) {
			result.append(horario.getDescricao()).append(":00 / Disponível<br>");
		}

		return result.toString(); // Não é necessário remover espaços em branco no final
	}




	@PostMapping("/excluir")
	public String excluirAgendamento(@RequestParam Integer id)
	{
		agendamentoRepository.deleteById(id);
		return "redirect:/index"; // Redireciona de volta para a página index
	}

}
