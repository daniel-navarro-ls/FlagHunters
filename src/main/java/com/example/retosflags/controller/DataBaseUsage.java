package com.example.retosflags.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import com.example.retosflags.model.User;
import com.example.retosflags.model.Reto;
import com.example.retosflags.service.UserService;
import com.example.retosflags.repository.UserRepository;
import com.example.retosflags.repository.RetoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Component
public class DataBaseUsage implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RetoRepository retoRepository;

    /*@Autowired
	private PasswordEncoder passwordEncoder;*/
    @Override
    public void run(String... args) throws Exception {
        // Seed users if empty
        if (userRepository.count() == 0) {
            userService.addUser(new User("admin", "password"));
            userService.addUser(new User("alice", "alice"));
            userService.addUser(new User("bob", "bob"));
        }

        // Seed retos if empty
        if (retoRepository.count() == 0) {
            Optional<User> adminUser = userRepository.findByUsername("admin");
            Optional<User> aliceUser = userRepository.findByUsername("alice");
            Optional<User> bobUser = userRepository.findByUsername("bob");
            List<Reto> retos = List.of(
                new Reto(null, "Forense básico", "Descifra un archivo oculto", "https://ejemplo.ctf/forense1", "FLAG{FORENSE1}", adminUser.get()),
                new Reto(null, "Cripto I", "ROMPE un cifrado César", "https://ejemplo.ctf/crypto1", "FLAG{CESAR}", aliceUser.get()),
                new Reto(null, "Web SQLi", "Inyección en login vulnerable", "https://ejemplo.ctf/web1", "FLAG{SQLI}", bobUser.get()),
                new Reto(null, "Pwn warmup", "Desbordamiento simple", "https://ejemplo.ctf/pwn1", "FLAG{PWN1}", adminUser.get()),
                new Reto(null, "OSINT tweet", "Encuentra coordenadas en redes", "https://ejemplo.ctf/osint1", "FLAG{OSINT}", aliceUser.get()),
                new Reto(null, "Stego PNG", "Mensaje en imagen", "https://ejemplo.ctf/stego1", "FLAG{STEGO}", bobUser.get())
            );
            retoRepository.saveAll(retos);
        }
    }
}
