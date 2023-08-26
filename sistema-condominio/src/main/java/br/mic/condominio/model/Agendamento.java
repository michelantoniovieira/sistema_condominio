package br.mic.condominio.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
@Table(name = "agendamentos")
public class Agendamento
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "data")
	private String data;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_horario")
	private Horario horario;

	public Agendamento() {
	}

	public Agendamento(String data, Usuario usuario, Horario horario) {
		this.data = data;
		this.usuario = usuario;
		this.horario = horario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData()
	{
		if (data != null && !data.isEmpty()) {
			LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		} else {
			return "";
		}
	}

	public String getDataFormatada() {
		if (data != null && !data.isEmpty()) {
			LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		} else {
			return "";
		}
	}


	public void setData(String data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}
}
