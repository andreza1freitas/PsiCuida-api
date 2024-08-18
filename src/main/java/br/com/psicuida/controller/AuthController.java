package br.com.psicuida.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.psicuida.entity.Paciente;
import br.com.psicuida.repository.PacienteRepository;
import jakarta.servlet.http.HttpSession;
import lombok.Data;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PacienteRepository pacienteRepository;

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
	    Optional<Paciente> paciente = pacienteRepository.findByEmail(loginRequest.getEmail());

	    if (paciente.isPresent() && paciente.get().getSenha().equals(loginRequest.getSenha())) {

	        Map<String, Object> response = new HashMap<>();
	        response.put("userId", paciente.get().getId());
	        response.put("userName", paciente.get().getNome());

	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	    }
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate(); // Invalida a sessão atual
		return ResponseEntity.ok("Logged out successfully");
	}

	@Data
	public static class LoginRequest {
        private String email;
        private String senha;
	}
}
