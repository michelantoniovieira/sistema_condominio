package br.mic.condominio.service;

import br.mic.condominio.model.Usuario;
import br.mic.condominio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean verificarCredenciais(String login, String senha) {
        // Consulta o usuário pelo nome de usuário (username)
        Usuario usuario = usuarioRepository.findByLogin(login);

        if (usuario != null) {
            // Verifica se a senha fornecida corresponde à senha armazenada no usuário
            return usuario.getSenha().equals(senha);
        }

        // Caso o usuário não seja encontrado, retorna false
        return false;
    }

    public Usuario buscarUsuarioPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}

