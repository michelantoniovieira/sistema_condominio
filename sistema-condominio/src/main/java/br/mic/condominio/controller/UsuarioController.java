package br.mic.condominio.controller;

import br.mic.condominio.model.Usuario;
import br.mic.condominio.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController
{

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Retorna o nome da página de login (login.html)
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("login") String login,
                               @RequestParam("senha") String senha,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {

        boolean autenticacaoValida = usuarioService.verificarCredenciais(login, senha);

        if (autenticacaoValida) {
            // Autenticação bem-sucedida
            Usuario usuario = usuarioService.buscarUsuarioPorLogin(login);

            // Define o ID do usuário na sessão para ser acessado posteriormente
            session.setAttribute("idUsuario", usuario.getId());
            session.setAttribute("loginUsuario", usuario.getLogin());
            session.setAttribute("apartamentoUsuario", usuario.getApartamento());

            // Define o ID do usuário no campo hidden do formulário
            redirectAttributes.addFlashAttribute("idUsuario", usuario.getId());

            // Redireciona para a página principal
            return "redirect:/index";
        } else {

            // Autenticação falhou, redireciona de volta para a página de login com uma mensagem de erro
            redirectAttributes.addFlashAttribute("erro", "Credenciais inválidas. Tente novamente.");
            return "redirect:/login";
        }
    }

}
