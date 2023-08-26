package br.mic.condominio.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Usuario
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="login")
	private String login;
	@Column(name="senha")
	private String senha;
	@Column(name="apartamento")
	private int apartamento;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getLogin()
	{
		return login;
	}
	public void setLogin(String login)
	{
		this.login = login;
	}
	public String getSenha()
	{
		return senha;
	}
	public void setSenha(String senha)
	{
		this.senha = senha;
	}
	public int getApartamento()
	{
		return apartamento;
	}
	public void setApartamento(int apartamento)
	{
		this.apartamento = apartamento;
	}
	
	
}
