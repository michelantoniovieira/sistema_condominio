package br.mic.condominio.repository;

import br.mic.condominio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>
{
    Usuario findByLogin(String login);
}
