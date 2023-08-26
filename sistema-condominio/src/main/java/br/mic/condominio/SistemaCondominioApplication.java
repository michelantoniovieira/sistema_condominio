package br.mic.condominio;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import br.mic.condominio.model.Agendamento;

@SpringBootApplication
@Controller
public class SistemaCondominioApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SistemaCondominioApplication.class, args);
	}
}
