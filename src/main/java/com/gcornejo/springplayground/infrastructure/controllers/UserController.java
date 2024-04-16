package com.gcornejo.springplayground.infrastructure.controllers;

import com.gcornejo.springplayground.domain.models.User;
import com.gcornejo.springplayground.domain.models.dtos.UserCredentials;
import com.gcornejo.springplayground.domain.models.dtos.UserProfile;
import com.gcornejo.springplayground.domain.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO GENERAL: Mover toda la logica de negocio a useCases, en el controller solo se deberian manejar las request y response
    // TODO GENERAL: Hacer validaciones de campos: Campo email debe pasar por un regex, minimos y maximos de caracteres, requests con propiedades faltantes
    // TODO: Unitarias e implementacion Spring Security

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User newUser) {
        Optional<User> user = this.userRepository.findByEmail(newUser.getEmail());
        if (user.isPresent()) {
            return Collections.singletonMap("message", "User already exists");
        }
        // TODO: Encriptar password con algun algoritmo hash
        this.userRepository.save(newUser);
        return Collections.singletonMap("message", "User was created");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserCredentials userCredentials) {
        Optional<User> user = this.userRepository.findByEmail(userCredentials.getEmail());

        //TODO: Desencriptar password de la db y comparar con la password que pasa el usuario en el body del request
        if (user.isPresent() && user.get().getPassword().equals(userCredentials.getPassword())) {
            return Collections.singletonMap("message", "Login successfully");
        }
        return Collections.singletonMap("message", "Invalid credentials");
    }

    @GetMapping("/users/{id}")
    public Map<String, Object> getUserProfile(@PathVariable Long id) {
        Optional<User> user =  userRepository.findById(id);
        // TODO: Validar si el usuario cuenta con un token JWT valido y correspondiente al ID del usuario al que intenta acceder
        if (user.isPresent()) {
            return Collections.singletonMap("profile", new UserProfile(user.get().getUsername(), user.get().getCompany()));
        }
        return Collections.singletonMap("message", "No users where found with that ID");
    }

    @PutMapping("/users/{id}")
    public Map<String, Object> updateUserProfile(@PathVariable Long id, @RequestBody UserProfile updatedUserProfile) {
        Optional<User> user =  userRepository.findById(id);
        // TODO: Validar si el usuario cuenta con un token JWT valido y correspondiente al ID del usuario al que intenta acceder
        if (user.isPresent()) {
            user.get().setCompany(updatedUserProfile.getCompany());
            user.get().setUsername(updatedUserProfile.getUsername());
            return Collections.singletonMap("message", "User profile updated successfully");
        }
        return Collections.singletonMap("message", "User doesn't exist in the database.");
    }

    // NOTE: Este endpoint solo se creó con el fin de probar si se creaban/actualizaban los datos en la db. No va en el producto final
    @GetMapping("/users")
    public List<User> getUsers() {
        return (List<User>) this.userRepository.findAll();
    }
}
