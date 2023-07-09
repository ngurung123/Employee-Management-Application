package com.example.springbootbackend.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootbackend.dto.AuthResponseDTO;
import com.example.springbootbackend.dto.LoginDto;
import com.example.springbootbackend.dto.RegisterDto;
import com.example.springbootbackend.jwtSecurity.JWTGenerator;
import com.example.springbootbackend.model.Role;
import com.example.springbootbackend.model.UserEntity;
import com.example.springbootbackend.repository.RoleRepository;
import com.example.springbootbackend.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JWTGenerator jwtGenerator;

	public AuthController() {

	}

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
	}

	@PostMapping(path = "/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		System.out.println("Auth login is called....");

		return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
	}

	@PostMapping(path = "/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		if (userRepository.existsByUsername(registerDto.getUsername()) == true) {
			return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
		}

		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(registerDto.getUsername());
		userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Role roles = roleRepository.findByName("USER").get();
		userEntity.setRoles(Collections.singletonList(roles));

		userRepository.save(userEntity);

		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

	}

}
